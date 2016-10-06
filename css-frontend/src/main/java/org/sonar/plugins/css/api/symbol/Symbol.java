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
package org.sonar.plugins.css.api.symbol;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.sonar.css.tree.symbol.Scope;
import org.sonar.css.tree.impl.css.IdentifierTreeImpl;

public class Symbol {

  public enum Kind {
    LESS_VARIABLE("less_variable");

    private final String value;

    Kind(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

  }

  private final String name;

  private Kind kind;
  private Scope scope;
  private List<Usage> usages = new LinkedList<>();

  public Symbol(String name, Kind kind, Scope scope) {
    this.name = name;
    this.kind = kind;
    this.scope = scope;
  }

  public void addUsage(Usage usage) {
    usages.add(usage);
    ((IdentifierTreeImpl) usage.identifierTree()).setSymbol(this);
  }

  public Collection<Usage> usages() {
    return Collections.unmodifiableList(usages);
  }

  public Scope scope() {
    return scope;
  }

  public String name() {
    return name;
  }

  public boolean is(Symbol.Kind kind) {
    return kind.equals(this.kind);
  }

  public Kind kind() {
    return kind;
  }

  public void setKind(Kind kind) {
    this.kind = kind;
  }

}
