/*
 * SonarQube CSS / Less Plugin
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

import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

import org.sonar.css.tree.impl.SyntaxList;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.SelectorTree;
import org.sonar.plugins.css.api.tree.css.SelectorsTree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class SelectorsTreeImpl extends TreeImpl implements SelectorsTree {

  private final SyntaxList<SelectorTree> selectorSyntaxList;
  private final List<SelectorTree> selectors;
  private final SyntaxToken lessTrailingComma;

  public SelectorsTreeImpl(SyntaxList<SelectorTree> selectorSyntaxList) {
    this(selectorSyntaxList, null);
  }

  public SelectorsTreeImpl(SyntaxList<SelectorTree> selectorSyntaxList, @Nullable SyntaxToken lessTrailingComma) {
    this.selectorSyntaxList = selectorSyntaxList;
    this.lessTrailingComma = lessTrailingComma;
    this.selectors = selectorSyntaxList.allElements(SelectorTree.class);
  }

  @Override
  public Kind getKind() {
    return Kind.SELECTORS;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    if (lessTrailingComma != null) {
      return Iterators.concat(
        selectorSyntaxList.all().iterator(),
        Iterators.singletonIterator(lessTrailingComma));
    } else {
      return selectorSyntaxList.all().iterator();
    }
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
