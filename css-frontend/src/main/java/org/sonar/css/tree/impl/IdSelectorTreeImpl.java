/*
 * SonarQube CSS Plugin
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
package org.sonar.css.tree.impl;

import com.google.common.collect.Iterators;

import java.util.Iterator;

import org.sonar.plugins.css.api.tree.IdSelectorTree;
import org.sonar.plugins.css.api.tree.IdentifierTree;
import org.sonar.plugins.css.api.tree.SyntaxToken;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class IdSelectorTreeImpl extends CssTree implements IdSelectorTree {

  private SyntaxToken hashSymbol;
  private IdentifierTree identifier;

  public IdSelectorTreeImpl(SyntaxToken hashSymbol, IdentifierTree identifier) {
    this.hashSymbol = hashSymbol;
    this.identifier = identifier;
  }

  @Override
  public Kind getKind() {
    return Kind.ID_SELECTOR;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(hashSymbol, identifier);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitIdSelector(this);
  }

  @Override
  public SyntaxToken hashSymbol() {
    return hashSymbol;
  }

  @Override
  public IdentifierTree identifier() {
    return identifier;
  }

  @Override
  public String text() {
    return identifier.text();
  }

}
