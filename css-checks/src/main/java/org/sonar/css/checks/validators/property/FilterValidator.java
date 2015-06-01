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
import com.sonar.sslr.api.AstNode;
import org.sonar.css.checks.validators.PropertyValueValidator;
import org.sonar.css.parser.CssGrammar;

import javax.annotation.Nonnull;

import java.util.List;

public class FilterValidator implements PropertyValueValidator {

  private final ImmutableList<String> allowedFunctions = ImmutableList
    .of("blur", "brightness", "contrast", "drop-shadow", "grayscale", "hue-rotate", "invert", "opacity", "saturate", "sepia");

  @Override
  public boolean isPropertyValueValid(@Nonnull AstNode astNode) {
    List<AstNode> values = astNode.getChildren();
    if (values == null || values.size() == 0) {
      return false;
    }
    if (values.get(0).is(CssGrammar.IDENT)) {
      return "none".equals(values.get(0).getTokenValue()) && values.size() == 1;
    }
    for (AstNode value : values) {
      if (!value.is(CssGrammar.FUNCTION) && !value.is(CssGrammar.URI)) {
        return false;
      }
      // TODO: Deal with IE static filters
      if (value.is(CssGrammar.FUNCTION) && !allowedFunctions.contains(value.getTokenValue())) {
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
