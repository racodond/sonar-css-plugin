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
package org.sonar.css.tree.impl.css;

import org.sonar.css.model.Vendor;
import org.sonar.css.tree.symbol.Scope;
import org.sonar.plugins.css.api.symbol.Symbol;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import java.util.Locale;
import java.util.regex.Pattern;

public class IdentifierTreeImpl extends LiteralTreeImpl implements IdentifierTree {

  private static final Pattern LESS_INTERPOLATED_VARIABLE = Pattern.compile("@\\{[\\w-_]+\\}");
  private static final Pattern SCSS_INTERPOLATED_VARIABLE = Pattern.compile("#\\{.+\\}");
  private final Vendor vendor;
  private Scope scope;
  private Symbol symbol = null;

  public IdentifierTreeImpl(SyntaxToken ident) {
    super(ident);
    this.vendor = setVendorPrefix();
  }

  @Override
  public Kind getKind() {
    return Kind.IDENTIFIER;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitIdentifier(this);
  }

  @Override
  public boolean isVendorPrefixed() {
    return vendor != null;
  }

  @Override
  public boolean isLessInterpolated() {
    return LESS_INTERPOLATED_VARIABLE.matcher(text()).find();
  }

  @Override
  public boolean isScssInterpolated() {
    return SCSS_INTERPOLATED_VARIABLE.matcher(text()).find();
  }

  @Override
  public boolean isInterpolated() {
    return isLessInterpolated() || isScssInterpolated();
  }

  @Override
  public boolean isValidable() {
    return !isInterpolated() && !isVendorPrefixed();
  }

  @Override
  public Vendor vendor() {
    return vendor;
  }

  private Vendor setVendorPrefix() {
    for (Vendor v : Vendor.values()) {
      if (text().toLowerCase(Locale.ENGLISH).startsWith(v.getPrefix())) {
        return v;
      }
    }
    return null;
  }

  @Override
  public Scope scope() {
    return scope;
  }

  public void scope(Scope scope) {
    this.scope = scope;
  }

  @Override
  public Symbol symbol() {
    return symbol;
  }

  public void setSymbol(Symbol symbol) {
    this.symbol = symbol;
  }

}
