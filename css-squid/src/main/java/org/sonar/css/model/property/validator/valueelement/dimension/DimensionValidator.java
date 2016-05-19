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
package org.sonar.css.model.property.validator.valueelement.dimension;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nonnull;

import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.valueelement.function.FunctionValidator;
import org.sonar.css.model.value.CssValueElement;
import org.sonar.css.model.value.valueelement.DimensionValueElement;
import org.sonar.css.model.value.valueelement.NumberValueElement;

public abstract class DimensionValidator implements ValueElementValidator {

  private final boolean positiveOnly;
  private final ImmutableList<String> units;

  public DimensionValidator(boolean positiveOnly, ImmutableList<String> units) {
    this.positiveOnly = positiveOnly;
    this.units = units;
  }

  public boolean isPositiveOnly() {
    return positiveOnly;
  }

  @Override
  public boolean isValid(@Nonnull CssValueElement cssValueElement) {

    if (cssValueElement instanceof DimensionValueElement) {
      if (!units.contains(((DimensionValueElement) cssValueElement).getUnit())) {
        return false;
      }
      return isPositiveOnly() ? ((DimensionValueElement) cssValueElement).isPositive() : true;
    }

    if (new FunctionValidator(ImmutableList.of("calc")).isValid(cssValueElement)) {
      return true;
    }

    if (cssValueElement instanceof NumberValueElement) {
      return ((NumberValueElement) cssValueElement).isZero();
    }

    return false;
  }

}
