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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.css.CompoundSelectorTree;
import org.sonar.plugins.css.api.tree.css.SelectorCombinatorTree;
import org.sonar.plugins.css.api.tree.css.SelectorTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.less.LessExtendTree;
import org.sonar.plugins.css.api.tree.less.LessMixinGuardTree;
import org.sonar.plugins.css.api.tree.less.LessMixinParametersTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class SelectorTreeImpl extends TreeImpl implements SelectorTree {

  private final SelectorCombinationList compoundSelectorSyntaxList;
  private final List<CompoundSelectorTree> compoundSelectors;
  private final List<Tree> allTrees;

  private final LessExtendTree lessExtend;
  private final LessMixinParametersTree lessMixinParameters;
  private final LessMixinGuardTree lessMixinGuard;
  private final SelectorCombinatorTree lessParentCombinator;

  public SelectorTreeImpl(@Nullable SelectorCombinationList compoundSelectorSyntaxList) {
    this(null, compoundSelectorSyntaxList, null, null, null);
  }

  public SelectorTreeImpl(@Nullable SelectorCombinatorTree lessParentCombinator, @Nullable SelectorCombinationList compoundSelectorSyntaxList, @Nullable LessExtendTree lessExtend,
    @Nullable LessMixinParametersTree lessMixinParameters, @Nullable LessMixinGuardTree lessMixinGuard) {
    this.compoundSelectorSyntaxList = compoundSelectorSyntaxList;
    this.lessExtend = lessExtend;
    this.lessMixinParameters = lessMixinParameters;
    this.lessMixinGuard = lessMixinGuard;
    this.lessParentCombinator = lessParentCombinator;
    this.compoundSelectors = buildCompoundSelectorList();
    this.allTrees = buildAllTreeList();
  }

  @Override
  public Kind getKind() {
    return Kind.SELECTOR;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    List<Tree> children = new ArrayList<>();
    if (lessParentCombinator != null) {
      children.add(lessParentCombinator);
    }
    children.addAll(allTrees);
    if (lessExtend != null) {
      children.add(lessExtend);
    }
    if (lessMixinParameters != null) {
      children.add(lessMixinParameters);
    }
    if (lessMixinGuard != null) {
      children.add(lessMixinGuard);
    }
    return children.iterator();
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

  @Override
  @Nullable
  public SelectorCombinatorTree lessParentCombinator() {
    return lessParentCombinator;
  }

  @Override
  @Nullable
  public LessExtendTree lessExtend() {
    return lessExtend;
  }

  @Override
  @Nullable
  public LessMixinParametersTree lessMixinParameters() {
    return lessMixinParameters;
  }

  @Override
  @Nullable
  public LessMixinGuardTree lessMixinGuard() {
    return lessMixinGuard;
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
