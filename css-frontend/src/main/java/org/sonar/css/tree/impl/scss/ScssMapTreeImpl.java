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
import org.sonar.plugins.css.api.tree.scss.ScssMapEntryTree;
import org.sonar.plugins.css.api.tree.scss.ScssMapTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import java.util.Iterator;
import java.util.function.Function;

public class ScssMapTreeImpl extends TreeImpl implements ScssMapTree {

  private final SyntaxToken openParenthesis;
  private final SyntaxToken closeParenthesis;
  private final SeparatedList<ScssMapEntryTree, DelimiterTree> entries;

  public ScssMapTreeImpl(SyntaxToken openParenthesis, SeparatedList<ScssMapEntryTree, DelimiterTree> entries, SyntaxToken closeParenthesis) {
    this.openParenthesis = openParenthesis;
    this.closeParenthesis = closeParenthesis;
    this.entries = entries;
  }

  @Override
  public Kind getKind() {
    return Kind.SCSS_MAP;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.singletonIterator(openParenthesis),
      entries.elementsAndSeparators(Function.identity(), Function.identity()),
      Iterators.singletonIterator(closeParenthesis));
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitScssMap(this);
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
  public SeparatedList<ScssMapEntryTree, DelimiterTree> entries() {
    return entries;
  }

}
