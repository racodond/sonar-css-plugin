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

import javax.annotation.Nonnull;

public class NumberRangeValidator extends NumberValidator {

  private final double min;
  private final double max;

  public NumberRangeValidator(double min, double max) {
    super(false);
    this.min = min;
    this.max = max;
  }

  @Override
  public boolean isValid(@Nonnull CssValueElement cssValueElement) {
    if (super.isValid(cssValueElement)) {
      double value = ((NumberValueElement) cssValueElement).getValue();
      return value >= min && value <= max;
    }
    return false;
  }

  @Override
  @Nonnull
  public String getValidatorFormat() {
    return "<number>(" + String.format("%.1f", min) + "," + String.format("%.1f", max) + ")";
  }

}
