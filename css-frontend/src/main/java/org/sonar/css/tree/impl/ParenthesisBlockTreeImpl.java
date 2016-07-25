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
import java.util.List;
import javax.annotation.Nullable;

import org.sonar.plugins.css.api.tree.ParenthesisBlockTree;
import org.sonar.plugins.css.api.tree.SyntaxToken;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class ParenthesisBlockTreeImpl extends CssTree implements ParenthesisBlockTree {

  private final SyntaxToken openParenthesis;
  private final SyntaxToken closeParenthesis;
  private final List<Tree> content;

  public ParenthesisBlockTreeImpl(SyntaxToken openParenthesis, @Nullable List<Tree> content, SyntaxToken closeParenthesis) {
    this.openParenthesis = openParenthesis;
    this.content = content;
    this.closeParenthesis = closeParenthesis;
  }

  @Override
  public Kind getKind() {
    return Kind.PARENTHESIS_BLOCK;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    if (content != null) {
      return Iterators.concat(
        Iterators.singletonIterator(openParenthesis),
        content.iterator(),
        Iterators.singletonIterator(closeParenthesis));
    } else {
      return Iterators.forArray(openParenthesis, closeParenthesis);
    }
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitParenthesisBlock(this);
  }

  @Override
  @Nullable
  public List<Tree> content() {
    return content;
  }

  @Override
  public SyntaxToken openParenthesis() {
    return openParenthesis;
  }

  @Override
  public SyntaxToken closeParenthesis() {
    return closeParenthesis;
  }

}
