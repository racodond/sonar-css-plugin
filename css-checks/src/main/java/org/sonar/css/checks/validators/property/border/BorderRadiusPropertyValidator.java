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
package org.sonar.css.checks.validators.property.border;

import org.sonar.css.checks.utils.CssValue;
import org.sonar.css.checks.utils.CssValueElement;
import org.sonar.css.checks.utils.valueelements.Delimiter;
import org.sonar.css.checks.utils.valueelements.Dimension;
import org.sonar.css.checks.utils.valueelements.Percentage;
import org.sonar.css.checks.validators.PropertyValueValidator;
import org.sonar.css.checks.validators.valueelement.dimension.LengthValidator;
import org.sonar.css.checks.validators.valueelement.numeric.PercentageValidator;

import javax.annotation.Nonnull;

import java.util.List;

public class BorderRadiusPropertyValidator implements PropertyValueValidator {

  private final LengthValidator lengthValidator = new LengthValidator(true);
  private final PercentageValidator percentageValidator = new PercentageValidator(true);

  @Override
  public boolean isValid(@Nonnull CssValue value) {
    List<CssValueElement> valueElements = value.getValueElements();

    if (valueElements.size() > 9) {
      return false;
    }

    int numberDelimiters = 0;
    int positionDelimiter = 0;
    for (int i = 0; i < valueElements.size(); i++) {
      if (!(valueElements.get(i) instanceof Percentage)
        && !(valueElements.get(i) instanceof Dimension)
        && !(valueElements.get(i) instanceof Delimiter)) {
        return false;
      }
      if (valueElements.get(i) instanceof Percentage && !percentageValidator.isValid(valueElements.get(i))) {
        return false;
      }
      if (valueElements.get(i) instanceof Dimension && !lengthValidator.isValid(valueElements.get(i))) {
        return false;
      }
      if (valueElements.get(i) instanceof Delimiter) {
        if (!"/".equals(((Delimiter) valueElements.get(i)).getType())) {
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
