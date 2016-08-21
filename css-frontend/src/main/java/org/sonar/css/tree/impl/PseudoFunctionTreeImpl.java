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

import com.google.common.collect.Iterators;

import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

import org.sonar.plugins.css.api.tree.IdentifierTree;
import org.sonar.plugins.css.api.tree.PseudoFunctionTree;
import org.sonar.plugins.css.api.tree.SyntaxToken;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class PseudoFunctionTreeImpl extends CssTree implements PseudoFunctionTree {

  private final SyntaxToken prefix;
  private final IdentifierTree pseudoFunctionName;
  private final SyntaxToken openParenthesis;
  private final List<Tree> parameterElements;
  private final SyntaxToken closeParenthesis;

  public PseudoFunctionTreeImpl(SyntaxToken prefix, IdentifierTree pseudoFunctionName, SyntaxToken openParenthesis, @Nullable List<Tree> parameterElements,
    SyntaxToken closeParenthesis) {
    this.prefix = prefix;
    this.pseudoFunctionName = pseudoFunctionName;
    this.openParenthesis = openParenthesis;
    this.parameterElements = parameterElements;
    this.closeParenthesis = closeParenthesis;
  }

  @Override
  public Kind getKind() {
    return Kind.PSEUDO_FUNCTION;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    if (parameterElements != null) {
      return Iterators.concat(
        Iterators.forArray(prefix, pseudoFunctionName, openParenthesis),
        parameterElements.iterator(),
        Iterators.singletonIterator(closeParenthesis));
    } else {
      return Iterators.forArray(prefix, pseudoFunctionName, openParenthesis, closeParenthesis);
    }
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitPseudoFunction(this);
  }

  @Override
  public SyntaxToken prefix() {
    return prefix;
  }

  @Override
  public IdentifierTree pseudoFunctionName() {
    return pseudoFunctionName;
  }

}
