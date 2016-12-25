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
package org.sonar.css.tree.impl.css;

import com.google.common.collect.Iterators;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.css.UriContentTree;
import org.sonar.plugins.css.api.tree.css.UriTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.Iterator;

public class UriTreeImpl extends TreeImpl implements UriTree {

  private final SyntaxToken urlFunction;
  private final SyntaxToken openParenthesis;
  private final UriContentTree uriContent;
  private final SyntaxToken closeParenthesis;

  public UriTreeImpl(SyntaxToken urlFunction, SyntaxToken openParenthesis, @Nullable UriContentTree uriContent, SyntaxToken closeParenthesis) {
    this.urlFunction = urlFunction;
    this.openParenthesis = openParenthesis;
    this.uriContent = uriContent;
    this.closeParenthesis = closeParenthesis;
  }

  @Override
  public Kind getKind() {
    return Kind.URI;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(urlFunction, openParenthesis, uriContent, closeParenthesis);
  }

  @Override
  public SyntaxToken urlFunction() {
    return urlFunction;
  }

  @Override
  public SyntaxToken openParenthesis() {
    return openParenthesis;
  }

  @Override
  @Nullable
  public UriContentTree uriContent() {
    return uriContent;
  }

  @Override
  public SyntaxToken closeParenthesis() {
    return closeParenthesis;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitUri(this);
  }

}
