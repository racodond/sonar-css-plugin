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
package org.sonar.css.model.property.validator.property.border;

import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.css.model.Value;
import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.value.CssValueElement;
import org.sonar.css.model.value.valueelement.DelimiterValueElement;

public class BorderRadiusPropertyValidator implements ValueValidator {

  @Override
  public boolean isValid(@Nonnull Value value) {
    List<CssValueElement> valueElements = value.getValueElements();

    if (valueElements.size() > 9) {
      return false;
    }

    int numberDelimiters = 0;
    int positionDelimiter = 0;
    for (int i = 0; i < valueElements.size(); i++) {
      if (!ValidatorFactory.getPositivePercentageValidator().isValid(valueElements.get(i))
        && !ValidatorFactory.getPositiveLengthValidator().isValid(valueElements.get(i))
        && !(valueElements.get(i) instanceof DelimiterValueElement)) {
        return false;
      }
      if (valueElements.get(i) instanceof DelimiterValueElement) {
        if (!"/".equals(((DelimiterValueElement) valueElements.get(i)).getType())) {
          return false;
        }
        numberDelimiters++;
        positionDelimiter = i;
      }
    }

    if (numberDelimiters > 1
      || (numberDelimiters == 1 && (positionDelimiter == 0 || positionDelimiter == valueElements.size() - 1 || positionDelimiter > 4))
      || (numberDelimiters == 0 && valueElements.size() > 4)
      || (numberDelimiters == 1 && (valueElements.size() - positionDelimiter) > 5)) {
      return false;
    }

    return true;
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "[ <length>(>=0) | <percentage>(>=0) ]{1,4} [ / [ <length>(>=0) | <percentage>(>=0) ]{1,4} ]?";
  }

}
