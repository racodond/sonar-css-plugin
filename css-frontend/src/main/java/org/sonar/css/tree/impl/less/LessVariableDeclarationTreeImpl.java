/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2017 David RACODON
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

import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueTree;
import org.sonar.plugins.css.api.tree.less.LessVariableDeclarationTree;
import org.sonar.plugins.css.api.tree.less.LessVariableTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;

public class LessVariableDeclarationTreeImpl extends TreeImpl implements LessVariableDeclarationTree {

  private final LessVariableTree variable;
  private final SyntaxToken colon;
  private final ValueTree value;
  private final SyntaxToken semicolon;

  public LessVariableDeclarationTreeImpl(LessVariableTree variable, SyntaxToken colon, ValueTree value, @Nullable SyntaxToken semicolon) {
    this.variable = variable;
    this.colon = colon;
    this.value = value;
    this.semicolon = semicolon;
  }

  @Override
  public Kind getKind() {
    return Kind.LESS_VARIABLE_DECLARATION;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(variable, colon, value, semicolon);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitLessVariableDeclaration(this);
  }

  @Override
  public LessVariableTree variable() {
    return variable;
  }

  @Override
  public SyntaxToken colon() {
    return colon;
  }

  @Override
  public ValueTree value() {
    return value;
  }

  @Override
  @Nullable
  public SyntaxToken semicolon() {
    return semicolon;
  }

}
