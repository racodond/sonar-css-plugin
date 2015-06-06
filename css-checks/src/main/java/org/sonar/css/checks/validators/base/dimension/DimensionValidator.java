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
package org.sonar.css.checks.validators.base.dimension;

import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.AstNode;
import javax.annotation.Nonnull;
import org.sonar.css.checks.validators.base.EnumValidator;
import org.sonar.css.checks.validators.PropertyValueValidator;
import org.sonar.css.checks.validators.base.numeric.ZeroNumberValidator;
import org.sonar.css.parser.CssGrammar;

public abstract class DimensionValidator implements PropertyValueValidator {

  private final boolean positiveOnly;
  private final EnumValidator unitValidator;

  public DimensionValidator(boolean positiveOnly, ImmutableList<String> units) {
    this.positiveOnly = positiveOnly;
    unitValidator = new EnumValidator(units);
  }

  public boolean isPositiveOnly() {
    return positiveOnly;
  }

  @Override
  public boolean isPropertyValueValid(@Nonnull AstNode astNode) {
    if (astNode.getFirstChild(CssGrammar.DIMENSION) != null
      && astNode.getFirstChild(CssGrammar.DIMENSION).getFirstChild(CssGrammar.NUMBER) != null
      && astNode.getFirstChild(CssGrammar.DIMENSION).getFirstChild(CssGrammar.unit) != null) {
      if (positiveOnly) {
        return Double.valueOf(astNode.getFirstChild(CssGrammar.DIMENSION).getFirstChild(CssGrammar.NUMBER).getTokenValue()) >= 0
          && unitValidator.isPropertyValueValid(astNode.getFirstChild(CssGrammar.DIMENSION).getFirstChild(CssGrammar.unit));
      } else {
        return unitValidator.isPropertyValueValid(astNode.getFirstChild(CssGrammar.DIMENSION).getFirstChild(CssGrammar.unit));
      }
    }
    if (astNode.getFirstChild(CssGrammar.NUMBER) != null) {
      return new ZeroNumberValidator().isPropertyValueValid(astNode.getFirstChild(CssGrammar.NUMBER));
    }
    return false;
  }

}
