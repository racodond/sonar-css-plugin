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
package org.sonar.css.tree.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;

public class SyntaxList<T extends Tree> {

  private final T element;
  private final SyntaxToken separatorToken;
  private final SyntaxList<T> next;

  public SyntaxList(@Nullable T element, @Nullable SyntaxToken separatorToken, @Nullable SyntaxList<T> next) {

    if (element == null && separatorToken == null) {
      throw new IllegalStateException("At least one parameter (element or separatorToken) must be non null.");
    } else if (separatorToken == null && next != null) {
      throw new IllegalStateException("separatorToken must be non null when next is set.");
    }

    this.element = element;
    this.separatorToken = separatorToken;
    this.next = next;
  }

  @Nullable
  public T element() {
    return element;
  }

  @Nullable
  public SyntaxToken separatorToken() {
    return separatorToken;
  }

  @Nullable
  public SyntaxList next() {
    return next;
  }

  public List<Tree> all() {
    List<Tree> all = new ArrayList<>();

    all.add(element);
    if (separatorToken != null) {
      all.add(separatorToken);
    }

    SyntaxList<T> nextSyntaxList = next;
    while (nextSyntaxList != null) {
      all.add(nextSyntaxList.element());
      if (nextSyntaxList.separatorToken() != null) {
        all.add(nextSyntaxList.separatorToken());
      }
      nextSyntaxList = nextSyntaxList.next();
    }

    return all;
  }

  public <T extends Tree> List<T> allElements(Class<T> treeType) {
    return TreeListUtils.allElementsOfType(all(), treeType);
  }

}
