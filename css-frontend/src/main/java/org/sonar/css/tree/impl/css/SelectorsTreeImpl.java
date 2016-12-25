/*
 * SonarQube CSS / SCSS / Less Analyzer
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
package org.sonar.css.tree.impl.css;

import com.google.common.collect.Iterators;
import org.sonar.css.tree.impl.SeparatedList;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.SelectorTree;
import org.sonar.plugins.css.api.tree.css.SelectorsTree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class SelectorsTreeImpl extends TreeImpl implements SelectorsTree {

  private final SeparatedList<SelectorTree, SyntaxToken> selectors;
  private final SyntaxToken lessTrailingComma; // FIXME: integrate it to the above list instead

  public SelectorsTreeImpl(SeparatedList<SelectorTree, SyntaxToken> selectors) {
    this(selectors, null);
  }

  public SelectorsTreeImpl(SeparatedList<SelectorTree, SyntaxToken> selectors, @Nullable SyntaxToken lessTrailingComma) {
    this.selectors = selectors;
    this.lessTrailingComma = lessTrailingComma;
  }

  @Override
  public Kind getKind() {
    return Kind.SELECTORS;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      selectors.elementsAndSeparators(Function.identity(), Function.identity()),
      Iterators.singletonIterator(lessTrailingComma));
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitSelectors(this);
  }

  @Override
  public List<SelectorTree> selectors() {
    return selectors;
  }

  @Override
  public SelectorTree lastSelector() {
    return selectors.get(selectors.size() - 1);
  }

  @Override
  @Nullable
  public SyntaxToken lessTrailingComma() {
    return lessTrailingComma;
  }

}
