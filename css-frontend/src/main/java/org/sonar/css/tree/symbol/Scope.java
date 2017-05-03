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
package org.sonar.css.tree.symbol;

import com.google.common.collect.Maps;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

import org.sonar.plugins.css.api.symbol.Symbol;
import org.sonar.plugins.css.api.tree.Tree;

public class Scope {

  private Scope outer;
  private final Tree tree;
  protected Map<String, Symbol> symbols = Maps.newHashMap();

  /**
   *
   * @param outer parent scope
   * @param tree syntax tree holding this scope
   */
  public Scope(Scope outer, Tree tree) {
    this.outer = outer;
    this.tree = tree;
  }

  public Tree tree() {
    return tree;
  }

  public Scope outer() {
    return outer;
  }

  /**
   * @param kind of the symbols to look for
   * @return the symbols corresponding to the given kind
   */
  public List<Symbol> getSymbols(Symbol.Kind kind) {
    List<Symbol> result = new LinkedList<>();
    for (Symbol symbol : symbols.values()) {
      if (symbol.is(kind)) {
        result.add(symbol);
      }
    }
    return result;
  }

  public void addSymbol(Symbol symbol) {
    symbols.put(symbol.name(), symbol);
  }

  @Nullable
  public Symbol getSymbol(String name) {
    return symbols.get(name);
  }

}
