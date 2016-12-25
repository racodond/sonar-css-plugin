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
package org.sonar.css.tree.impl.scss;

import com.google.common.collect.Iterators;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.StatementBlockTree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.css.ValueTree;
import org.sonar.plugins.css.api.tree.scss.ScssConditionalDirectiveTree;

import java.util.Iterator;

abstract class ScssConditionalDirectiveTreeImpl extends TreeImpl implements ScssConditionalDirectiveTree {

  private final SyntaxToken directive;
  private final ValueTree condition;
  private final StatementBlockTree block;

  public ScssConditionalDirectiveTreeImpl(SyntaxToken directive, ValueTree condition, StatementBlockTree block) {
    this.directive = directive;
    this.condition = condition;
    this.block = block;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(directive, condition, block);
  }

  @Override
  public SyntaxToken directive() {
    return directive;
  }

  @Override
  public ValueTree condition() {
    return condition;
  }

  @Override
  public StatementBlockTree block() {
    return block;
  }

}
