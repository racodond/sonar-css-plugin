/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
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

import java.util.Iterator;
import java.util.List;

import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueTree;
import org.sonar.plugins.css.api.tree.less.LessExtendTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class LessExtendTreeImpl extends TreeImpl implements LessExtendTree {

  private final SyntaxToken extendKeyword;
  private final SyntaxToken openParenthesis;
  private final List<ValueTree> parameterElements;
  private final SyntaxToken closeParenthesis;

  public LessExtendTreeImpl(SyntaxToken extendKeyword, SyntaxToken openParenthesis, List<ValueTree> parameterElements, SyntaxToken closeParenthesis) {
    this.extendKeyword = extendKeyword;
    this.openParenthesis = openParenthesis;
    this.parameterElements = parameterElements;
    this.closeParenthesis = closeParenthesis;
  }

  @Override
  public Kind getKind() {
    return Kind.LESS_EXTEND;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.forArray(extendKeyword, openParenthesis),
      parameterElements.iterator(),
      Iterators.singletonIterator(closeParenthesis));
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitLessExtend(this);
  }

  @Override
  public SyntaxToken extendKeyword() {
    return extendKeyword;
  }

  @Override
  public SyntaxToken openParenthesis() {
    return openParenthesis;
  }

  @Override
  public SyntaxToken closeParenthesis() {
    return closeParenthesis;
  }

  @Override
  public List<ValueTree> parameterElements() {
    return parameterElements;
  }

}
