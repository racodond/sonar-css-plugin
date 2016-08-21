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

import org.sonar.plugins.css.api.tree.CompoundSelectorTree;
import org.sonar.plugins.css.api.tree.SelectorTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class SelectorTreeImpl extends CssTree implements SelectorTree {

  private final SelectorCombinationList compoundSelectorSyntaxList;
  private final List<CompoundSelectorTree> compoundSelectors;
  private final List<Tree> allTrees;

  public SelectorTreeImpl(@Nullable SelectorCombinationList compoundSelectorSyntaxList) {
    this.compoundSelectorSyntaxList = compoundSelectorSyntaxList;
    this.compoundSelectors = buildCompoundSelectorList();
    this.allTrees = buildAllTreeList();
  }

  @Override
  public Kind getKind() {
    return Kind.SELECTOR;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return allTrees.iterator();
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitSelector(this);
  }

  @Override
  public SelectorCombinationList compoundSelectorSyntaxList() {
    return compoundSelectorSyntaxList;
  }

  @Override
  public List<CompoundSelectorTree> compoundSelectors() {
    return compoundSelectors;
  }

  private List<Tree> buildAllTreeList() {
    List<Tree> all = new ArrayList<>();

    if (compoundSelectorSyntaxList != null) {
      all.add(compoundSelectorSyntaxList.compoundSelector());
      if (compoundSelectorSyntaxList.combinator() != null) {
        all.add(compoundSelectorSyntaxList.combinator());
      }

      SelectorCombinationList nextSelectorCombinationList = compoundSelectorSyntaxList.next();
      while (nextSelectorCombinationList != null) {
        all.add(nextSelectorCombinationList.compoundSelector());
        if (nextSelectorCombinationList.combinator() != null) {
          all.add(nextSelectorCombinationList.combinator());
        }
        nextSelectorCombinationList = nextSelectorCombinationList.next();
      }
    }

    return all;
  }

  private List<CompoundSelectorTree> buildCompoundSelectorList() {
    List<CompoundSelectorTree> selectorList = new ArrayList<>();

    if (compoundSelectorSyntaxList != null) {
      selectorList.add(compoundSelectorSyntaxList.compoundSelector());

      SelectorCombinationList nextSelectorCombinationList = compoundSelectorSyntaxList.next();
      while (nextSelectorCombinationList != null) {
        selectorList.add(nextSelectorCombinationList.compoundSelector());
        nextSelectorCombinationList = nextSelectorCombinationList.next();
      }
    }

    return selectorList;
  }

}
