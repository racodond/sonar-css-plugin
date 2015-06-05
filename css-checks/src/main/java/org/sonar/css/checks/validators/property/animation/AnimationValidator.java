/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende
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
package org.sonar.css.checks.validators.property.animation;

import org.sonar.css.checks.utils.CssValue;
import org.sonar.css.checks.utils.CssValueElement;
import org.sonar.css.checks.validators.PropertyValueValidator;

import javax.annotation.Nonnull;

import java.util.List;

public class AnimationValidator implements PropertyValueValidator {

  @Override
  public boolean isValid(@Nonnull CssValue value) {
    List<CssValueElement> valueElements = value.getValueElements();
    int numberOfElements = value.getNumberOfValueElements();
    if (numberOfElements == 0 || numberOfElements > 8) {
      return false;
    }
    for (CssValueElement valueElement : valueElements) {
      if (!new AnimationDelayValidator().isValid(valueElement)
        && !new AnimationDirectionValidator().isValid(valueElement)
        && !new AnimationDurationValidator().isValid(valueElement)
        && !new AnimationFillModeValidator().isValid(valueElement)
        && !new AnimationIterationCountValidator().isValid(valueElement)
        && !new AnimationNameValidator().isValid(valueElement)
        && !new AnimationPlayStateValidator().isValid(valueElement)
        && !new AnimationTimingFunctionValidator().isValid(valueElement)) {
        return false;
      }
    }
    return true;
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "<time> || <single-timing-function> || <time> || <single-animation-iteration-count> || <single-animation-direction> || <single-animation-fill-mode> || <single-animation-play-state> || <single-animation-name>";
  }

}
