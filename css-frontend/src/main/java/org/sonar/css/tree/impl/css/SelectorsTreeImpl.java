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
package org.sonar.css.tree.impl.css;

import org.sonar.css.tree.impl.SeparatedList;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.DelimiterTree;
import org.sonar.plugins.css.api.tree.css.SelectorTree;
import org.sonar.plugins.css.api.tree.css.SelectorsTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import java.util.Iterator;
import java.util.function.Function;

public class SelectorsTreeImpl extends TreeImpl implements SelectorsTree {

  private final SeparatedList<SelectorTree, DelimiterTree> selectors;

  public SelectorsTreeImpl(SeparatedList<SelectorTree, DelimiterTree> selectors) {
    this.selectors = selectors;
  }

  @Override
  public Kind getKind() {
    return Kind.SELECTORS;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return selectors.elementsAndSeparators(Function.identity(), Function.identity());
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitSelectors(this);
  }

  @Override
  public SeparatedList<SelectorTree, DelimiterTree> selectors() {
    return selectors;
  }

  @Override
  public SelectorTree lastSelector() {
    return selectors.get(selectors.size() - 1);
  }

}
