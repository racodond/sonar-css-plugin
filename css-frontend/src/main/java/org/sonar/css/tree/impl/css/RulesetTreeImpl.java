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
import javax.annotation.Nullable;

import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.css.RulesetBlockTree;
import org.sonar.plugins.css.api.tree.css.RulesetTree;
import org.sonar.plugins.css.api.tree.css.SelectorsTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class RulesetTreeImpl extends TreeImpl implements RulesetTree {

  private final SelectorsTree selectors;
  private final RulesetBlockTree block;

  public RulesetTreeImpl(@Nullable SelectorsTree selectors, RulesetBlockTree block) {
    this.selectors = selectors;
    this.block = block;
  }

  @Override
  public Kind getKind() {
    return Kind.RULESET;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(selectors, block);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitRuleset(this);
  }

  @Override
  @Nullable
  public SelectorsTree selectors() {
    return selectors;
  }

  @Override
  public RulesetBlockTree block() {
    return block;
  }

}
