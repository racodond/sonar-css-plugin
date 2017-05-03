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
import org.sonar.plugins.css.api.tree.scss.ScssAtRootParametersTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import java.util.Iterator;
import java.util.List;

public class ScssAtRootParametersTreeImpl extends TreeImpl implements ScssAtRootParametersTree {

  private final SyntaxToken openParenthesis;
  private final SyntaxToken parameter;
  private final SyntaxToken colon;
  private final List<IdentifierTree> values;
  private final SyntaxToken closeParenthesis;

  public ScssAtRootParametersTreeImpl(SyntaxToken openParenthesis, SyntaxToken parameter, SyntaxToken colon, List<IdentifierTree> values, SyntaxToken closeParenthesis) {
    this.openParenthesis = openParenthesis;
    this.parameter = parameter;
    this.colon = colon;
    this.values = values;
    this.closeParenthesis = closeParenthesis;
  }

  @Override
  public Kind getKind() {
    return Kind.SCSS_AT_ROOT_PARAMETERS;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.forArray(openParenthesis, parameter, colon),
      values.iterator(),
      Iterators.singletonIterator(closeParenthesis));
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitScssAtRootParameters(this);
  }

  @Override
  public SyntaxToken openParenthesis() {
    return openParenthesis;
  }

  @Override
  public SyntaxToken parameter() {
    return parameter;
  }

  @Override
  public SyntaxToken colon() {
    return colon;
  }

  @Override
  public List<IdentifierTree> values() {
    return values;
  }

  @Override
  public SyntaxToken closeParenthesis() {
    return closeParenthesis;
  }

}
