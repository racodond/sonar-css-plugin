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
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.tree.scss.ScssNestedPropertiesDeclarationTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import java.util.Iterator;
import java.util.List;

public class ScssNestedPropertiesDeclarationTreeImpl extends TreeImpl implements ScssNestedPropertiesDeclarationTree {

  private final PropertyTree namespace;
  private final SyntaxToken colon;
  private final StatementBlockTree block;

  public ScssNestedPropertiesDeclarationTreeImpl(PropertyTree namespace, SyntaxToken colon, StatementBlockTree block) {
    this.namespace = namespace;
    this.colon = colon;
    this.block = block;
  }

  @Override
  public Kind getKind() {
    return Kind.SCSS_NESTED_PROPERTIES_DECLARATION;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(namespace, colon, block);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitScssNestedPropertiesDeclaration(this);
  }

  @Override
  public PropertyTree namespace() {
    return namespace;
  }

  @Override
  public SyntaxToken colon() {
    return colon;
  }

  @Override
  public ValueTree value() {
    throw new IllegalStateException("Not yet implemented");
  }

  @Override
  public StatementBlockTree block() {
    return block;
  }

  @Override
  public SyntaxToken semicolon() {
    return null;
  }

  @Override
  public List<PropertyDeclarationTree> propertyDeclarations() {
    return block.propertyDeclarations();
  }

}
