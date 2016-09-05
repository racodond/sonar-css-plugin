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
import java.util.List;
import javax.annotation.Nullable;

import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.css.BracketBlockTree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class BracketBlockTreeImpl extends TreeImpl implements BracketBlockTree {

  private final SyntaxToken openBracket;
  private final SyntaxToken closeBracket;
  private final List<Tree> content;

  public BracketBlockTreeImpl(SyntaxToken openBracket, @Nullable List<Tree> content, SyntaxToken closeBracket) {
    this.openBracket = openBracket;
    this.content = content;
    this.closeBracket = closeBracket;
  }

  @Override
  public Kind getKind() {
    return Kind.BRACKET_BLOCK;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    if (content != null) {
      return Iterators.concat(
        Iterators.singletonIterator(openBracket),
        content.iterator(),
        Iterators.singletonIterator(closeBracket));
    } else {
      return Iterators.forArray(openBracket, closeBracket);
    }
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitBracketBlock(this);
  }

  @Override
  @Nullable
  public List<Tree> content() {
    return content;
  }

  @Override
  public SyntaxToken openBracket() {
    return openBracket;
  }

  @Override
  public SyntaxToken closeBracket() {
    return closeBracket;
  }

}
