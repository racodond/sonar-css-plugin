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
package org.sonar.css.tree.impl.less;

import com.google.common.collect.Iterators;
import org.sonar.css.tree.impl.SeparatedList;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ParenthesisBlockTree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.less.LessMixinGuardTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class LessMixinGuardTreeImpl extends TreeImpl implements LessMixinGuardTree {

  private final SyntaxToken when;
  private final SyntaxToken not;
  private final SeparatedList<ParenthesisBlockTree, SyntaxToken> conditions;

  public LessMixinGuardTreeImpl(SyntaxToken when, @Nullable SyntaxToken not, SeparatedList<ParenthesisBlockTree, SyntaxToken> conditions) {
    this.when = when;
    this.not = not;
    this.conditions = conditions;
  }

  @Override
  public Kind getKind() {
    return Kind.LESS_MIXIN_GUARD;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.forArray(when, not),
      conditions.elementsAndSeparators(Function.identity(), Function.identity()));
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitLessMixinGuard(this);
  }

  @Override
  public SyntaxToken when() {
    return when;
  }

  @Override
  @Nullable
  public SyntaxToken not() {
    return not;
  }

  @Override
  public List<ParenthesisBlockTree> conditions() {
    return conditions;
  }

}
