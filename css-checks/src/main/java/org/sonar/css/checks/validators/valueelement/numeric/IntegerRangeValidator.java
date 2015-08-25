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
package org.sonar.css.checks.validators.valueelement.numeric;

import org.sonar.css.checks.utils.CssValueElement;
import org.sonar.css.checks.utils.valueelements.NumberValueElement;

import javax.annotation.Nonnull;

public class IntegerRangeValidator extends IntegerValidator {

  private final int min;
  private final int max;

  public IntegerRangeValidator(int min, int max) {
    super(true);
    this.min = min;
    this.max = max;
  }

  @Override
  public boolean isValid(@Nonnull CssValueElement cssValueElement) {
    if (super.isValid(cssValueElement)
      && ((NumberValueElement) cssValueElement).getIntegerValue() >= min
      && ((NumberValueElement) cssValueElement).getIntegerValue() <= max) {
      return true;
    }
    return false;
  }

  @Override
  @Nonnull
  public String getValidatorFormat() {
    return "<number>(" + min + "," + max + ")";
  }

}
