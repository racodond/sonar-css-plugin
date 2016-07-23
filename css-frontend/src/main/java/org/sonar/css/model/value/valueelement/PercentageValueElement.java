/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013-2016 Tamas Kende and David RACODON
 * mailto: kende.tamas@gmail.com and david.racodon@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.css.model.value.valueelement;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.GenericTokenType;
import org.sonar.css.model.value.CssValueElement;

public class PercentageValueElement implements CssValueElement {

  private final double value;
  private final boolean isZero;

  public PercentageValueElement(AstNode astNode) {
    value = Double.valueOf(astNode.getFirstChild(GenericTokenType.LITERAL).getTokenValue());
    isZero = astNode.getFirstChild(GenericTokenType.LITERAL).getTokenValue().matches("([\\-\\+])?[0]*(\\.[0]+)?");
  }

  public boolean isPositive() {
    return value >= 0;
  }

  public boolean isNotZero() {
    return !isZero;
  }

}
