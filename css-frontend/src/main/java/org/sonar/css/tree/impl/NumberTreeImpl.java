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
package org.sonar.css.tree.impl;

import org.sonar.plugins.css.api.tree.NumberTree;
import org.sonar.plugins.css.api.tree.SyntaxToken;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class NumberTreeImpl extends LiteralTreeImpl implements NumberTree {

  public NumberTreeImpl(SyntaxToken number) {
    super(number);
  }

  @Override
  public Kind getKind() {
    return Kind.NUMBER;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitNumber(this);
  }

  @Override
  public double doubleValue() {
    return Double.valueOf(text());
  }

  @Override
  public long integerValue() {
    return Math.round(Double.valueOf(text()));
  }

  @Override
  public boolean isZero() {
    return text().matches("([\\-\\+])?[0]*(\\.[0]+)?");
  }

  @Override
  public boolean isNotZero() {
    return !isZero();
  }

  @Override
  public boolean isInteger() {
    return text().matches("[\\-\\+]?[0-9]+");
  }

  @Override
  public boolean isPositive() {
    return doubleValue() >= 0;
  }

}
