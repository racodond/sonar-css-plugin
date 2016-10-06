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
import java.util.stream.Collectors;
import javax.annotation.Nullable;

import org.sonar.plugins.css.api.tree.Tree;

public class TreeListUtils {

  private TreeListUtils() {
  }

  public static <T extends Tree> List<T> allElementsOfType(@Nullable List<Tree> trees, Class<T> treeType) {
    if (trees != null) {
      return trees.stream()
        .filter(e -> treeType.isAssignableFrom(e.getClass()))
        .map(treeType::cast)
        .collect(Collectors.toList());
    } else {
      return new ArrayList<>();
    }
  }

}
