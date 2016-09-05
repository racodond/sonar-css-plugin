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
package org.sonar.css.tree.impl.css;

import com.google.common.collect.Iterators;

import java.util.Iterator;
import javax.annotation.Nullable;

import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.css.NamespaceTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.TypeSelectorTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class TypeSelectorTreeImpl extends TreeImpl implements TypeSelectorTree {

  private NamespaceTree namespace;
  private IdentifierTree identifier;

  public TypeSelectorTreeImpl(@Nullable NamespaceTree namespace, IdentifierTree identifier) {
    this.namespace = namespace;
    this.identifier = identifier;
  }

  @Override
  public Kind getKind() {
    return Kind.TYPE_SELECTOR;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(namespace, identifier);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitTypeSelector(this);
  }

  @Override
  public IdentifierTree identifier() {
    return identifier;
  }

  @Override
  @Nullable
  public NamespaceTree namespace() {
    return namespace;
  }

}
