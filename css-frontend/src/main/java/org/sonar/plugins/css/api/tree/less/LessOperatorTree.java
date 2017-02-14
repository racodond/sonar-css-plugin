/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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
package org.sonar.plugins.css.api.tree.less;

import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;

import java.util.HashMap;
import java.util.Map;

public interface LessOperatorTree extends Tree {

  enum OPERATOR {
    PLUS("+"),
    MINUS("-"),
    TIMES("*"),
    DIV("/");

    private static final Map<String, OPERATOR> LOOKUP = new HashMap<>();

    static {
      for (OPERATOR operator : OPERATOR.values())
        LOOKUP.put(operator.getValue(), operator);
    }

    private String value;

    OPERATOR(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public static OPERATOR getType(String value) {
      return LOOKUP.get(value.trim());
    }
  }

  SyntaxToken operator();

  OPERATOR type();

  String text();

}
