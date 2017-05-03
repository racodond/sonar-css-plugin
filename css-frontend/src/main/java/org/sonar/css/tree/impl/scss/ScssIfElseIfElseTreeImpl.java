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

import com.google.common.collect.Iterators;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.scss.ScssElseIfTree;
import org.sonar.plugins.css.api.tree.scss.ScssElseTree;
import org.sonar.plugins.css.api.tree.scss.ScssIfElseIfElseTree;
import org.sonar.plugins.css.api.tree.scss.ScssIfTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScssIfElseIfElseTreeImpl extends TreeImpl implements ScssIfElseIfElseTree {

  private final ScssIfTree ife;
  private final List<ScssElseIfTree> elseIf;

  @Nullable
  private final ScssElseTree elsee;

  public ScssIfElseIfElseTreeImpl(ScssIfTree ife, @Nullable List<ScssElseIfTree> elseIf, @Nullable ScssElseTree elsee) {
    this.ife = ife;
    this.elseIf = elseIf != null ? elseIf : new ArrayList<>();
    this.elsee = elsee;
  }

  @Override
  public Kind getKind() {
    return Kind.SCSS_IF_ELSE_IF_ELSE;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.singletonIterator(ife),
      elseIf.iterator(),
      Iterators.singletonIterator(elsee));
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitScssIfElseIfElse(this);
  }

  @Override
  public ScssIfTree ife() {
    return ife;
  }

  @Override
  public List<ScssElseIfTree> elseif() {
    return elseIf;
  }

  @Override
  @Nullable
  public ScssElseTree elsee() {
    return elsee;
  }

}
