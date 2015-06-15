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
package org.sonar.css.checks.validators.valueelement.numeric;

import org.sonar.css.checks.utils.CssValueElement;
import org.sonar.css.checks.utils.valueelements.NumberValueElement;
import org.sonar.css.checks.validators.ValueElementValidator;

import javax.annotation.Nonnull;

public class IntegerValidator implements ValueElementValidator {

  private final boolean positiveOnly;

  public IntegerValidator(boolean positiveOnly) {
    this.positiveOnly = positiveOnly;
  }

  public IntegerValidator() {
    positiveOnly = false;
  }

  @Override
  public boolean isValid(@Nonnull CssValueElement cssValueElement) {
    if (cssValueElement instanceof NumberValueElement && ((NumberValueElement) cssValueElement).isInteger()) {
      if (positiveOnly) {
        return ((NumberValueElement) cssValueElement).isPositive() ? true : false;
      }
      return true;
    }
    return false;
  }

  @Override
  @Nonnull
  public String getValidatorFormat() {
    return positiveOnly ? "<integer>(>=0)" : "<integer>";
  }

}
