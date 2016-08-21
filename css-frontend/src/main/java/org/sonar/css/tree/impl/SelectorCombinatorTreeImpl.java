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

import org.sonar.plugins.css.api.tree.SelectorCombinatorTree;
import org.sonar.plugins.css.api.tree.SyntaxToken;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class SelectorCombinatorTreeImpl extends CssTree implements SelectorCombinatorTree {

  private SyntaxToken combinator;

  public SelectorCombinatorTreeImpl(SyntaxToken combinator) {
    this.combinator = combinator;
  }

  @Override
  public Kind getKind() {
    return Kind.SELECTOR_COMBINATOR;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.singletonIterator(combinator);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitSelectorCombinator(this);
  }

  @Override
  public SyntaxToken combinator() {
    return combinator;
  }

  @Override
  public COMBINATOR type() {
    return COMBINATOR.getType(combinator.text());
  }

  @Override
  public String value() {
    return combinator.text();
  }

}
