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
import org.sonar.plugins.css.api.tree.css.ValueTree;
import org.sonar.plugins.css.api.tree.scss.ScssParameterTree;
import org.sonar.plugins.css.api.tree.scss.ScssVariableArgumentTree;
import org.sonar.plugins.css.api.tree.scss.ScssVariableDeclarationTree;
import org.sonar.plugins.css.api.tree.scss.ScssVariableTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.Iterator;

public class ScssParameterTreeImpl extends TreeImpl implements ScssParameterTree {

  private ValueTree value = null;
  private ScssVariableTree variable = null;
  private ScssVariableArgumentTree variableArgument = null;
  private ScssVariableDeclarationTree variableDeclaration = null;

  public ScssParameterTreeImpl(Tree parameter) {
    if (parameter instanceof ValueTree) {
      value = (ValueTree) parameter;
    } else if (parameter instanceof ScssVariableTree) {
      variable = (ScssVariableTree) parameter;
    } else if (parameter instanceof ScssVariableArgumentTree) {
      variableArgument = (ScssVariableArgumentTree) parameter;
    } else if (parameter instanceof ScssVariableDeclarationTree) {
      variableDeclaration = (ScssVariableDeclarationTree) parameter;
    } else {
      throw new IllegalStateException("Unknown SCSS parameter type: " + parameter.getClass());
    }
  }

  @Override
  public Kind getKind() {
    return Kind.SCSS_PARAMETER;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(value, variable, variableArgument, variableDeclaration);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitScssParameter(this);
  }

  @Override
  @Nullable
  public ValueTree value() {
    return value;
  }

  @Override
  @Nullable
  public ScssVariableTree variable() {
    return variable;
  }

  @Override
  @Nullable
  public ScssVariableArgumentTree variableArgument() {
    return variableArgument;
  }

  @Override
  @Nullable
  public ScssVariableDeclarationTree variableDeclaration() {
    return variableDeclaration;
  }

}
