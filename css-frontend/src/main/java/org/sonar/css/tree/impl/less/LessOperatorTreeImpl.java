/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
 * mailto: david.racodon@gmail.com
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
package org.sonar.css.tree.impl.less;

import com.google.common.collect.Iterators;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.less.LessOperatorTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import java.util.Iterator;

public class LessOperatorTreeImpl extends TreeImpl implements LessOperatorTree {

  private final SyntaxToken operator;

  public LessOperatorTreeImpl(SyntaxToken operator) {
    this.operator = operator;
  }

  @Override
  public Kind getKind() {
    return Kind.LESS_OPERATOR;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.singletonIterator(operator);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitLessOperator(this);
  }

  @Override
  public SyntaxToken operator() {
    return operator;
  }

  @Override
  public OPERATOR type() {
    return OPERATOR.getType(operator.text());
  }

  @Override
  public String text() {
    return operator().text();
  }

}
