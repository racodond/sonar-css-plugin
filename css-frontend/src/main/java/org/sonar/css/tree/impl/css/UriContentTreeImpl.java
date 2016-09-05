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
import org.sonar.plugins.css.api.tree.css.StringTree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.UriContentTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class UriContentTreeImpl extends TreeImpl implements UriContentTree {

  private final StringTree string;
  private final SyntaxToken ident;

  public UriContentTreeImpl(StringTree string) {
    this.string = string;
    this.ident = null;
  }

  public UriContentTreeImpl(SyntaxToken ident) {
    this.string = null;
    this.ident = ident;
  }

  @Override
  public Kind getKind() {
    return Kind.URI_CONTENT;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    if (string != null) {
      return Iterators.singletonIterator(string);
    } else {
      return Iterators.singletonIterator(ident);
    }
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitUriContent(this);
  }

  @Override
  @Nullable
  public SyntaxToken ident() {
    return ident;
  }

  @Override
  @Nullable
  public StringTree string() {
    return string;
  }

  @Override
  public String text() {
    return ident != null ? ident.text() : string.actualText();
  }

}
