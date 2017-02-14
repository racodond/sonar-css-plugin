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
package org.sonar.css.tree.impl.scss;

import com.google.common.collect.Iterators;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.css.ValueTree;
import org.sonar.plugins.css.api.tree.scss.ScssDefaultFlagTree;
import org.sonar.plugins.css.api.tree.scss.ScssGlobalFlagTree;
import org.sonar.plugins.css.api.tree.scss.ScssVariableDeclarationTree;
import org.sonar.plugins.css.api.tree.scss.ScssVariableTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Optional;

public class ScssVariableDeclarationTreeImpl extends TreeImpl implements ScssVariableDeclarationTree {

  private final ScssVariableTree variable;
  private final SyntaxToken colon;
  private final ValueTree value;
  private final SyntaxToken semicolon;

  public ScssVariableDeclarationTreeImpl(ScssVariableTree variable, SyntaxToken colon, ValueTree value, @Nullable SyntaxToken semicolon) {
    this.variable = variable;
    this.colon = colon;
    this.value = value;
    this.semicolon = semicolon;
  }

  @Override
  public Kind getKind() {
    return Kind.SCSS_VARIABLE_DECLARATION;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(variable, colon, value, semicolon);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitScssVariableDeclaration(this);
  }

  @Override
  public ScssVariableTree variable() {
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
  public ScssDefaultFlagTree defaultFlag() {
    Optional<ScssDefaultFlagTree> defaultFlag = value.firstValueElementOfType(ScssDefaultFlagTree.class);
    return defaultFlag.isPresent() ? defaultFlag.get() : null;
  }

  @Override
  @Nullable
  public ScssGlobalFlagTree globalFlag() {
    Optional<ScssGlobalFlagTree> globalFlag = value.firstValueElementOfType(ScssGlobalFlagTree.class);
    return globalFlag.isPresent() ? globalFlag.get() : null;
  }

  @Override
  @Nullable
  public SyntaxToken semicolon() {
    return semicolon;
  }

}
