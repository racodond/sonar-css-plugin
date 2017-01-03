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
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.css.ValueTree;
import org.sonar.plugins.css.api.tree.scss.ScssDirectiveTree;
import org.sonar.plugins.css.api.tree.scss.ScssSimpleDirectiveTree;

import javax.annotation.Nullable;
import java.util.Iterator;

abstract class ScssSimpleDirectiveTreeImpl extends TreeImpl implements ScssSimpleDirectiveTree {

  private final ScssDirectiveTree directive;
  private final ValueTree value;

  @Nullable
  private final SyntaxToken semicolon;

  public ScssSimpleDirectiveTreeImpl(ScssDirectiveTree directive, ValueTree value, @Nullable SyntaxToken semicolon) {
    this.directive = directive;
    this.value = value;
    this.semicolon = semicolon;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(directive, value, semicolon);
  }

  @Override
  public ScssDirectiveTree directive() {
    return directive;
  }

  @Override
  public ValueTree value() {
    return value;
  }

  @Override
  @Nullable
  public SyntaxToken semicolon() {
    return semicolon;
  }

}
