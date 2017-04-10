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
package org.sonar.css.tree.impl.scss;

import com.google.common.collect.Iterators;
import org.sonar.css.tree.impl.SeparatedList;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.DelimiterTree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.scss.ScssParameterTree;
import org.sonar.plugins.css.api.tree.scss.ScssParametersTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Function;

public class ScssParametersTreeImpl extends TreeImpl implements ScssParametersTree {

  private final SyntaxToken openParenthesis;
  private final SyntaxToken closeParenthesis;
  private final SeparatedList<ScssParameterTree, DelimiterTree> parameters;

  public ScssParametersTreeImpl(SyntaxToken openParenthesis, @Nullable SeparatedList<ScssParameterTree, DelimiterTree> parameters, SyntaxToken closeParenthesis) {
    this.openParenthesis = openParenthesis;
    this.closeParenthesis = closeParenthesis;
    this.parameters = parameters;
  }

  @Override
  public Kind getKind() {
    return Kind.SCSS_PARAMETERS;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.singletonIterator(openParenthesis),
      parameters != null ? parameters.elementsAndSeparators(Function.identity(), Function.identity()) : new ArrayList<Tree>().iterator(),
      Iterators.singletonIterator(closeParenthesis));
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitScssParameters(this);
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
  @Nullable
  public SeparatedList<ScssParameterTree, DelimiterTree> parameters() {
    return parameters;
  }

}
