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
import org.sonar.css.checks.utils.valueelements.PercentageValueElement;
import org.sonar.css.checks.validators.ValueElementValidator;

import javax.annotation.Nonnull;

public class PercentageValidator implements ValueElementValidator {

  private final boolean positiveOnly;

  public PercentageValidator(boolean positiveOnly) {
    this.positiveOnly = positiveOnly;
  }

  @Override
  public boolean isValid(@Nonnull CssValueElement cssValueElement) {
    if (cssValueElement instanceof PercentageValueElement) {
      if (positiveOnly) {
        return ((PercentageValueElement) cssValueElement).isPositive() ? true : false;
      }
      return true;
    }
    return false;
  }

  @Override
  @Nonnull
  public String getValidatorFormat() {
    return positiveOnly ? "<percentage>(>=0)" : "<percentage>";
  }

}
