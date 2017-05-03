/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2017 David RACODON
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

import org.sonar.plugins.css.api.tree.css.StatementBlockTree;
import org.sonar.plugins.css.api.tree.scss.ScssConditionTree;
import org.sonar.plugins.css.api.tree.scss.ScssDirectiveTree;
import org.sonar.plugins.css.api.tree.scss.ScssIfTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class ScssIfTreeImpl extends ScssDirectiveConditionBlockTreeImpl implements ScssIfTree {

  public ScssIfTreeImpl(ScssDirectiveTree directive, ScssConditionTree condition, StatementBlockTree block) {
    super(directive, condition, block);
  }

  @Override
  public Kind getKind() {
    return Kind.SCSS_IF;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitScssIf(this);
  }

}
