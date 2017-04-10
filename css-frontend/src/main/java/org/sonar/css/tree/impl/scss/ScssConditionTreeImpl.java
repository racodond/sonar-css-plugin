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
package org.sonar.css.tree.impl.scss;

import com.google.common.collect.Iterators;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueTree;
import org.sonar.plugins.css.api.tree.scss.ScssConditionTree;
import org.sonar.plugins.css.api.tree.scss.ScssOperatorTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import java.util.Iterator;

public class ScssConditionTreeImpl extends TreeImpl implements ScssConditionTree {

  private final ValueTree condition;

  public ScssConditionTreeImpl(ValueTree condition) {
    this.condition = condition;
  }

  @Override
  public Kind getKind() {
    return Kind.SCSS_CONDITION;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(condition);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitScssCondition(this);
  }

  @Override
  public ValueTree condition() {
    return condition;
  }

  @Override
  public long complexity() {
    return 1 + condition.valueElementsOfType(ScssOperatorTree.class)
      .stream()
      .filter(o -> o.type() == ScssOperatorTree.OPERATOR.AND || o.type() == ScssOperatorTree.OPERATOR.OR)
      .count();
  }

}
