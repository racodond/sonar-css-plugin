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
package org.sonar.css.checks.utils.valueelements;

import com.sonar.sslr.api.AstNode;
import org.sonar.css.checks.utils.CssValueElement;

public class NumberValueElement extends CssValueElement {

  private final Double value;
  private final boolean isZero;
  private final boolean isInteger;

  public NumberValueElement(AstNode numberNode) {
    value = Double.valueOf(numberNode.getTokenValue());
    isZero = numberNode.getTokenValue().matches("([\\-\\+])?[0]*(\\.[0]+)?");
    isInteger = numberNode.getTokenValue().matches("[\\-\\+]{0,1}[0-9]+");
  }

  public Double getValue() {
    return value;
  }

  public boolean isZero() {
    return isZero;
  }

  public boolean isPositive() {
    return value >= 0;
  }

  public boolean isInteger() {
    return isInteger;
  }

}
