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

import com.google.common.collect.Iterators;

import java.util.Iterator;
import java.util.List;

import org.sonar.plugins.css.api.tree.CompoundSelectorTree;
import org.sonar.plugins.css.api.tree.SimpleSelectorTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class CompoundSelectorTreeImpl extends CssTree implements CompoundSelectorTree {

  private final List<SimpleSelectorTree> selectors;

  public CompoundSelectorTreeImpl(List<SimpleSelectorTree> selectors) {
    this.selectors = selectors;
  }

  @Override
  public Kind getKind() {
    return Kind.COMPOUND_SELECTOR;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(selectors.iterator());
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitCompoundSelector(this);
  }

  @Override
  public List<SimpleSelectorTree> selectors() {
    return selectors;
  }

}
