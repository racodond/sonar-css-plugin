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
import org.sonar.plugins.css.api.tree.scss.ScssElseIfTree;
import org.sonar.plugins.css.api.tree.scss.ScssElseTree;
import org.sonar.plugins.css.api.tree.scss.ScssIfConditionsTree;
import org.sonar.plugins.css.api.tree.scss.ScssIfTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScssIfConditionsTreeImpl extends TreeImpl implements ScssIfConditionsTree {

  private final ScssIfTree ifDirective;
  private final List<ScssElseIfTree> elseIfDirectives;

  @Nullable
  private final ScssElseTree elseDirective;

  public ScssIfConditionsTreeImpl(ScssIfTree ifDirective, @Nullable List<ScssElseIfTree> elseIfDirectives, @Nullable ScssElseTree elseDirective) {
    this.ifDirective = ifDirective;
    this.elseIfDirectives = elseIfDirectives != null ? elseIfDirectives : new ArrayList<>();
    this.elseDirective = elseDirective;
  }

  @Override
  public Kind getKind() {
    return Kind.SCSS_IF_CONDITIONS;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.singletonIterator(ifDirective),
      elseIfDirectives.iterator(),
      Iterators.singletonIterator(elseDirective));
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitScssIfConditions(this);
  }

  @Override
  public ScssIfTree ife() {
    return ifDirective;
  }

  @Override
  public List<ScssElseIfTree> elseif() {
    return elseIfDirectives;
  }

  @Override
  @Nullable
  public ScssElseTree elsee() {
    return elseDirective;
  }

}
