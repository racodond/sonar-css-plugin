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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

import org.sonar.plugins.css.api.tree.SelectorTree;
import org.sonar.plugins.css.api.tree.SelectorsTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class SelectorsTreeImpl extends CssTree implements SelectorsTree {

  private final SyntaxList<SelectorTree> selectorSyntaxList;
  private final List<SelectorTree> selectors;
  private final List<Tree> allTrees;

  public SelectorsTreeImpl(@Nullable SyntaxList<SelectorTree> selectorSyntaxList) {
    this.selectorSyntaxList = selectorSyntaxList;
    this.selectors = buildSelectorList();
    this.allTrees = buildAllTreeList();
  }

  @Override
  public Kind getKind() {
    return Kind.SELECTORS;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return allTrees.iterator();
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

  private List<Tree> buildAllTreeList() {
    List<Tree> all = new ArrayList<>();

    if (selectorSyntaxList != null) {
      all.add(selectorSyntaxList.element());
      if (selectorSyntaxList.separatorToken() != null) {
        all.add(selectorSyntaxList.separatorToken());
      }

      SyntaxList<Tree> nextSyntaxList = selectorSyntaxList.next();
      while (nextSyntaxList != null) {
        all.add(nextSyntaxList.element());
        if (nextSyntaxList.separatorToken() != null) {
          all.add(nextSyntaxList.separatorToken());
        }
        nextSyntaxList = nextSyntaxList.next();
      }
    }

    return all;
  }

  private List<SelectorTree> buildSelectorList() {
    List<SelectorTree> selectorList = new ArrayList<>();

    if (selectorSyntaxList != null) {
      selectorList.add(selectorSyntaxList.element());

      SyntaxList<SelectorTree> nextSyntaxList = selectorSyntaxList.next();
      while (nextSyntaxList != null) {
        selectorList.add(nextSyntaxList.element());
        nextSyntaxList = nextSyntaxList.next();
      }
    }

    return selectorList;
  }

}
