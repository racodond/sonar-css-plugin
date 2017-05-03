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
package org.sonar.css.tree.impl.less;

import com.google.common.collect.Iterators;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.less.LessParentReferencingSelectorTree;
import org.sonar.plugins.css.api.tree.less.LessParentSelectorTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import java.util.Iterator;

public class LessParentReferencingSelectorTreeImpl extends TreeImpl implements LessParentReferencingSelectorTree {

  private final LessParentSelectorTree parent;
  private final IdentifierTree append;

  public LessParentReferencingSelectorTreeImpl(LessParentSelectorTree parent, IdentifierTree append) {
    this.parent = parent;
    this.append = append;
  }

  @Override
  public Kind getKind() {
    return Kind.LESS_PARENT_REFERENCING_SELECTOR;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(parent, append);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitLessParentReferencingSelector(this);
  }

  @Override
  public LessParentSelectorTree parent() {
    return parent;
  }

  @Override
  public IdentifierTree append() {
    return append;
  }

  @Override
  public String text() {
    return append.text();
  }

}
