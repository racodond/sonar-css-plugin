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
package org.sonar.css.tree.impl.css;

import com.google.common.collect.Iterators;

import java.util.Iterator;

import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.VariableTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class VariableTreeImpl extends TreeImpl implements VariableTree {

  private final SyntaxToken variablePrefix;
  private final IdentifierTree variable;

  public VariableTreeImpl(SyntaxToken variablePrefrix, IdentifierTree variable) {
    this.variable = variable;
    this.variablePrefix = variablePrefrix;
  }

  @Override
  public Tree.Kind getKind() {
    return Tree.Kind.VARIABLE;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(variablePrefix, variable);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitVariable(this);
  }

  @Override
  public IdentifierTree variable() {
    return variable;
  }

  @Override
  public SyntaxToken variablePrefix() {
    return variablePrefix;
  }

  @Override
  public String variableName() {
    return variable.text();
  }

}
