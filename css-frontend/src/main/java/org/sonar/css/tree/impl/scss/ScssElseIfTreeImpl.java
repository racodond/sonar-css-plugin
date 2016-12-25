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

import org.sonar.plugins.css.api.tree.css.StatementBlockTree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.css.ValueTree;
import org.sonar.plugins.css.api.tree.scss.ScssElseIfTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class ScssElseIfTreeImpl extends ScssConditionalDirectiveTreeImpl implements ScssElseIfTree {

  public ScssElseIfTreeImpl(SyntaxToken directive, ValueTree condition, StatementBlockTree block) {
    super(directive, condition, block);
  }

  @Override
  public Kind getKind() {
    return Kind.SCSS_ELSE_IF;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitScssElseIf(this);
  }

}
