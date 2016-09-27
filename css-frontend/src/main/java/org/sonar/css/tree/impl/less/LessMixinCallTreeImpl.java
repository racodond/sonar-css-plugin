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
package org.sonar.css.tree.impl.less;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.tree.less.LessMixinCallTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class LessMixinCallTreeImpl extends TreeImpl implements LessMixinCallTree {

  private final SelectorTree selector;
  private final ImportantTree important;
  private final SyntaxToken semicolon;
  private final SelectorCombinatorTree parentCombinator;

  public LessMixinCallTreeImpl(@Nullable SelectorCombinatorTree parentCombinator, SelectorTree selector, @Nullable ImportantTree important, @Nullable SyntaxToken semicolon) {
    this.parentCombinator = parentCombinator;
    this.selector = selector;
    this.important = important;
    this.semicolon = semicolon;
  }

  @Override
  public Kind getKind() {
    return Kind.LESS_MIXIN_CALL;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    List<Tree> children = new ArrayList<>();
    if (parentCombinator != null) {
      children.add(parentCombinator);
    }
    children.add(selector);
    if (important != null) {
      children.add(important);
    }
    if (semicolon != null) {
      children.add(semicolon);
    }
    return children.iterator();
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitLessMixinCall(this);
  }

  @Override
  @Nullable
  public SelectorCombinatorTree parentCombinator() {
    return parentCombinator;
  }

  @Override
  public SelectorTree selector() {
    return selector;
  }

  @Override
  @Nullable
  public ImportantTree important() {
    return important;
  }

  @Override
  @Nullable
  public SyntaxToken semicolon() {
    return semicolon;
  }

}
