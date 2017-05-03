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
package org.sonar.css.tree.impl.scss;

import com.google.common.collect.Iterators;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.scss.ScssVariableTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import java.util.Iterator;

public class ScssVariableTreeImpl extends TreeImpl implements ScssVariableTree {

  private final SyntaxToken prefix;
  private final IdentifierTree name;

  public ScssVariableTreeImpl(SyntaxToken variablePrefrix, IdentifierTree name) {
    this.name = name;
    this.prefix = variablePrefrix;
  }

  @Override
  public Kind getKind() {
    return Kind.SCSS_VARIABLE;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(prefix, name);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitScssVariable(this);
  }

  @Override
  public IdentifierTree name() {
    return name;
  }

  @Override
  public SyntaxToken prefix() {
    return prefix;
  }

}
