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
package org.sonar.plugins.css.api.symbol;

import com.google.common.annotations.Beta;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;

@Beta
public class Usage {

  public enum Kind {
    DECLARATION,
    READ,
  }

  private Kind kind;
  private IdentifierTree identifierTree;

  private Usage(IdentifierTree identifierTree, Kind kind) {
    this.kind = kind;
    this.identifierTree = identifierTree;
  }

  public Symbol symbol() {
    return identifierTree.symbol();
  }

  public Kind kind() {
    return kind;
  }

  public boolean is(Usage.Kind kind) {
    return kind.equals(this.kind);
  }

  public IdentifierTree identifierTree() {
    return identifierTree;
  }

  public static Usage create(IdentifierTree symbolTree, Kind kind) {
    return new Usage(symbolTree, kind);
  }

}
