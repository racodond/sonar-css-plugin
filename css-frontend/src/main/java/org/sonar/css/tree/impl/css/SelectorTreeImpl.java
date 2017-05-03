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
package org.sonar.css.tree.impl.css;

import com.google.common.collect.Iterators;
import org.sonar.css.tree.impl.SeparatedList;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.CompoundSelectorTree;
import org.sonar.plugins.css.api.tree.css.SelectorCombinatorTree;
import org.sonar.plugins.css.api.tree.css.SelectorTree;
import org.sonar.plugins.css.api.tree.less.LessExtendTree;
import org.sonar.plugins.css.api.tree.less.LessMixinGuardTree;
import org.sonar.plugins.css.api.tree.less.LessMixinParametersTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class SelectorTreeImpl extends TreeImpl implements SelectorTree {

  private final SeparatedList<CompoundSelectorTree, SelectorCombinatorTree> compoundSelectorsCombinationList;

  @Nullable
  private final LessExtendTree lessExtend;

  @Nullable
  private final LessMixinParametersTree lessMixinParameters;

  @Nullable
  private final LessMixinGuardTree lessMixinGuard;

  @Nullable
  private final SelectorCombinatorTree lessParentCombinator;

  @Nullable
  private final SelectorCombinatorTree scssParentCombinator;

  @Nullable
  private final SelectorCombinatorTree scssBlockCombinator;

  public SelectorTreeImpl(SeparatedList<CompoundSelectorTree, SelectorCombinatorTree> compoundSelectorsCombinationList) {
    this.compoundSelectorsCombinationList = compoundSelectorsCombinationList;
    this.scssParentCombinator = null;
    this.scssBlockCombinator = null;
    this.lessExtend = null;
    this.lessMixinParameters = null;
    this.lessMixinGuard = null;
    this.lessParentCombinator = null;
  }

  public SelectorTreeImpl(@Nullable SelectorCombinatorTree scssParentCombinator, SeparatedList<CompoundSelectorTree, SelectorCombinatorTree> compoundSelectorsCombinationList, @Nullable SelectorCombinatorTree scssBlockCombinator) {
    this.compoundSelectorsCombinationList = compoundSelectorsCombinationList;
    this.scssParentCombinator = scssParentCombinator;
    this.scssBlockCombinator = scssBlockCombinator;
    this.lessExtend = null;
    this.lessMixinParameters = null;
    this.lessMixinGuard = null;
    this.lessParentCombinator = null;
  }

  public SelectorTreeImpl(@Nullable SelectorCombinatorTree lessParentCombinator, SeparatedList<CompoundSelectorTree, SelectorCombinatorTree> compoundSelectorsCombinationList, @Nullable LessExtendTree lessExtend,
                          @Nullable LessMixinParametersTree lessMixinParameters, @Nullable LessMixinGuardTree lessMixinGuard) {
    this.compoundSelectorsCombinationList = compoundSelectorsCombinationList;
    this.lessExtend = lessExtend;
    this.lessMixinParameters = lessMixinParameters;
    this.lessMixinGuard = lessMixinGuard;
    this.lessParentCombinator = lessParentCombinator;
    this.scssParentCombinator = null;
    this.scssBlockCombinator = null;
  }

  @Override
  public Kind getKind() {
    return Kind.SELECTOR;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.forArray(lessParentCombinator, scssParentCombinator),
      compoundSelectorsCombinationList.elementsAndSeparators(Function.identity(), Function.identity()),
      Iterators.forArray(scssBlockCombinator, lessExtend, lessMixinParameters, lessMixinGuard));
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitSelector(this);
  }

  @Override
  public SeparatedList<CompoundSelectorTree, SelectorCombinatorTree> compoundSelectorsCombinationList() {
    return compoundSelectorsCombinationList;
  }

  @Override
  public List<CompoundSelectorTree> compoundSelectors() {
    return compoundSelectorsCombinationList;
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

  @Override
  @Nullable
  public SelectorCombinatorTree scssParentCombinator() {
    return scssParentCombinator;
  }

  @Override
  @Nullable
  public SelectorCombinatorTree scssBlockCombinator() {
    return scssBlockCombinator;
  }

}
