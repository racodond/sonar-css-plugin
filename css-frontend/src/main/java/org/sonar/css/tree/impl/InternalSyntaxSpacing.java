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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.sonar.plugins.css.api.tree.SyntaxSpacing;
import org.sonar.plugins.css.api.tree.SyntaxTrivia;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class InternalSyntaxSpacing extends CssTree implements SyntaxSpacing {

  public InternalSyntaxSpacing(int startIndex, int endIndex) {
  }

  @Override
  public Kind getKind() {
    return Kind.SPACING;
  }

  @Override
  public boolean isLeaf() {
    return true;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    // Do nothing at the moment. Spacings are dropped anyway.
  }

  @Override
  public int column() {
    return 0;
  }

  @Override
  public int line() {
    return 0;
  }

  @Override
  public int endColumn() {
    return 0;
  }

  @Override
  public int endLine() {
    return 0;
  }

  @Override
  public String text() {
    return "";
  }

  @Override
  public List<SyntaxTrivia> trivias() {
    return Collections.emptyList();
  }
}
