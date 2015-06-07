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
package org.sonar.css.checks.validators.valueelement.function;

import com.google.common.collect.ImmutableList;
import org.sonar.css.checks.utils.CssValueElement;
import org.sonar.css.checks.utils.valueelements.FunctionValueElement;
import org.sonar.css.checks.validators.PropertyValueElementValidator;

import javax.annotation.Nonnull;

public class FunctionValidator implements PropertyValueElementValidator {

  private final ImmutableList<String> allowedFunctions;

  public FunctionValidator(ImmutableList<String> allowedFunctions) {
    this.allowedFunctions = allowedFunctions;
  }

  @Override
  public boolean isValid(@Nonnull CssValueElement cssValueElement) {
    return cssValueElement instanceof FunctionValueElement && allowedFunctions.contains(((FunctionValueElement) cssValueElement).getNameWithoutVendorPrefix());
  }

  @Override
  @Nonnull
  public String getValidatorFormat() {
    StringBuilder format = new StringBuilder("<function>(");
    for (String allowedValue : allowedFunctions) {
      if (format.length() > 11) {
        format.append(" | ");
      }
      format.append(allowedValue);
    }
    format.append(")");
    return format.toString();
  }

}
