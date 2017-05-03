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
package org.sonar.plugins.css.api.tree.css;

import org.sonar.plugins.css.api.tree.Tree;

import java.util.HashMap;
import java.util.Map;

public interface SelectorCombinatorTree extends Tree {

  enum COMBINATOR {
    DESCENDANT(">>"),
    DESCENDANT_WS(""),
    CHILD(">"),
    NEXT_SIBLING("+"),
    FOLLOWING_SIBLING("~"),
    DEEP("/deep/"),
    DEEP_ALIAS(">>>"),
    COLUMN("||");

    private static final Map<String, COMBINATOR> LOOKUP = new HashMap<>();

    static {
      for (COMBINATOR combinator : COMBINATOR.values())
        LOOKUP.put(combinator.getValue(), combinator);
    }

    private String value;

    COMBINATOR(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public static COMBINATOR getType(String value) {
      return LOOKUP.get(value.trim());
    }
  }

  SyntaxToken combinator();

  COMBINATOR type();

  String value();

}
