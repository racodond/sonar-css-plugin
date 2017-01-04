/*
 * SonarQube CSS / SCSS / Less Analyzer
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
package org.sonar.css.tree.symbol;

import com.google.common.base.Charsets;
import org.junit.Test;
import org.sonar.css.parser.less.LessParser;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.css.tree.impl.css.StyleSheetTreeImpl;
import org.sonar.css.visitors.CssTreeVisitorContext;
import org.sonar.plugins.css.api.symbol.Symbol;
import org.sonar.plugins.css.api.symbol.SymbolModel;
import org.sonar.plugins.css.api.tree.Tree;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;

public class ScopeTest {

  private final SymbolModel SYMBOL_MODEL = symbolModel(new File("src/test/resources/symbol/scope.less"));

  @Test
  public void scopes_number() throws Exception {
    assertThat(SYMBOL_MODEL.getScopes()).hasSize(4);
  }

  @Test
  public void test_stylesheet_scope() throws Exception {
    Scope scope = scopeAtLine(1);

    assertThat(scope.tree().is(Tree.Kind.STYLESHEET)).isTrue();
    assertThat(symbols(scope)).containsOnly("myvar", "myvar2");
    assertThat(scope.getSymbols(Symbol.Kind.LESS_VARIABLE)).hasSize(2);
  }

  @Test
  public void test_at_rule_block_scope() throws Exception {
    Scope scope = scopeAtLine(20);

    assertThat(scope.tree().is(Tree.Kind.STATEMENT_BLOCK)).isTrue();
    assertThat(symbols(scope)).containsOnly("myvar");
    assertThat(scope.getSymbols(Symbol.Kind.LESS_VARIABLE)).hasSize(1);
  }

  @Test
  public void test_stylesheet_block_scope1() throws Exception {
    Scope scope = scopeAtLine(4);

    assertThat(scope.tree().is(Tree.Kind.STATEMENT_BLOCK)).isTrue();
    assertThat(symbols(scope)).containsOnly("myvar", "myvar1", "myvar2");
    assertThat(scope.getSymbols(Symbol.Kind.LESS_VARIABLE)).hasSize(3);
  }

  @Test
  public void test_stylesheet_block_scope2() throws Exception {
    Scope scope = scopeAtLine(11);

    assertThat(scope.tree().is(Tree.Kind.STATEMENT_BLOCK)).isTrue();
    assertThat(symbols(scope)).containsOnly("blabla");
    assertThat(scope.getSymbols(Symbol.Kind.LESS_VARIABLE)).hasSize(1);
  }

  private SymbolModel symbolModel(File file) {
    StyleSheetTreeImpl tree = (StyleSheetTreeImpl) LessParser.createParser(Charsets.UTF_8).parse(file);
    return new CssTreeVisitorContext(tree, file, "less").getSymbolModel();
  }

  private Scope scopeAtLine(int line) {
    for (Scope scope : SYMBOL_MODEL.getScopes()) {
      if (((TreeImpl) scope.tree()).getLine() == line) {
        return scope;
      }
    }
    throw new IllegalStateException();
  }

  private Set<String> symbols(Scope scope) {
    return scope.symbols.values()
      .stream()
      .map(Symbol::name)
      .collect(Collectors.toSet());
  }

}
