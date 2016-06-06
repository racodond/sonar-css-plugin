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
package org.sonar.css.model.value.valueelement;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.GenericTokenType;
import org.sonar.css.model.value.CssValueElement;
import org.sonar.css.parser.CssGrammar;

public class DimensionValueElement implements CssValueElement {

  private final double value;
  private final String unit;
  private final boolean isZero;

  public DimensionValueElement(AstNode dimensionNode) {
    value = Double.valueOf(dimensionNode.getFirstChild(GenericTokenType.LITERAL).getTokenValue());
    unit = dimensionNode.getFirstChild(CssGrammar.unit).getTokenValue();
    isZero = dimensionNode.getFirstChild(GenericTokenType.LITERAL).getTokenValue().matches("([\\-\\+])?[0]*(\\.[0]+)?");
  }

  public String getUnit() {
    return unit;
  }

  public boolean isPositive() {
    return value >= 0;
  }

  public boolean isNotZero() {
    return !isZero;
  }

}
