/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende and David RACODON
 * kende.tamas@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.css.checks.validators.property;

import org.sonar.css.checks.utils.CssValue;
import org.sonar.css.checks.utils.CssValueElement;
import org.sonar.css.checks.validators.ValueElementValidator;
import org.sonar.css.checks.validators.ValueValidator;
import org.sonar.css.checks.validators.valueelement.flex.FlexDirectionValidator;
import org.sonar.css.checks.validators.valueelement.flex.FlexWrapValidator;

import javax.annotation.Nonnull;

import java.util.List;

public class FlexFlowValidator implements ValueValidator {

  private final ValueElementValidator flexDirectionValidator = new FlexDirectionValidator();
  private final ValueElementValidator flexWrapValidator = new FlexWrapValidator();

  @Override
  public boolean isValid(@Nonnull CssValue value) {
    List<CssValueElement> valueElements = value.getValueElements();
    if (value.getNumberOfValueElements() > 2) {
      return false;
    }
    int countFlexWrap = 0;
    int countFlexDirection = 0;
    for (CssValueElement valueElement : valueElements) {
      if (!flexWrapValidator.isValid(valueElement)
        && !flexDirectionValidator.isValid(valueElement)) {
        return false;
      } else if (flexWrapValidator.isValid(valueElement)) {
        countFlexWrap++;
      } else if (flexDirectionValidator.isValid(valueElement)) {
        countFlexDirection++;
      }
    }
    if (countFlexDirection > 1 || countFlexWrap > 1) {
      return false;
    }
    return true;
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "<flex-direction> || <flex-wrap>";
  }

}
