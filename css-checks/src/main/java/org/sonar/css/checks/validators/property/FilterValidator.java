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
package org.sonar.css.checks.validators.property;

import com.google.common.collect.ImmutableList;
import org.sonar.css.checks.utils.CssFunctions;
import org.sonar.css.checks.utils.CssValue;
import org.sonar.css.checks.utils.CssValueElement;
import org.sonar.css.checks.validators.PropertyValueValidator;
import org.sonar.css.checks.validators.valueelement.FunctionValidator;
import org.sonar.css.checks.validators.valueelement.IdentifierValidator;
import org.sonar.css.checks.validators.valueelement.UriValidator;

import javax.annotation.Nonnull;

import java.util.List;

public class FilterValidator implements PropertyValueValidator {

  private final ImmutableList<String> allowedFunctions = ImmutableList
    .of("blur", "brightness", "contrast", "drop-shadow", "grayscale", "hue-rotate", "invert", "opacity", "saturate", "sepia");

  @Override
  public boolean isValid(@Nonnull CssValue value) {
    List<CssValueElement> valueElements = value.getValueElements();
    int numberOfElements = value.getNumberOfValueElements();
    if (numberOfElements == 0) {
      return false;
    }
    if (new IdentifierValidator(ImmutableList.of("none")).isValid(valueElements.get(0)) && numberOfElements == 1) {
      return true;
    }
    for (CssValueElement valueElement : valueElements) {
      if (!new FunctionValidator(allowedFunctions).isValid(valueElement)
        && !new FunctionValidator(CssFunctions.IE_STATIC_FILTERS).isValid(valueElement)
        && !new UriValidator().isValid(valueElement)) {
        return false;
      }
    }
    return true;
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "none | [<uri> | <filter-function>]+";
  }

}
