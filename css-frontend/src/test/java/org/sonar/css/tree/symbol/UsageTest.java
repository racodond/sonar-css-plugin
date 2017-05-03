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

import com.google.common.base.Charsets;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.sonar.css.parser.less.LessParser;
import org.sonar.css.tree.impl.css.StyleSheetTreeImpl;
import org.sonar.css.visitors.CssTreeVisitorContext;
import org.sonar.plugins.css.api.symbol.Symbol;
import org.sonar.plugins.css.api.symbol.SymbolModel;
import org.sonar.plugins.css.api.symbol.Usage;
import org.sonar.plugins.css.api.tree.Tree;

import static org.fest.assertions.Assertions.assertThat;

public class UsageTest {

  private final SymbolModel SYMBOL_MODEL = symbolModel(new File("src/test/resources/symbol/scope.less"));

  @Test
  public void number_of_scoped_symbols() throws Exception {
    assertThat(SYMBOL_MODEL.getSymbols().size()).isEqualTo(7);
  }

  @Test
  public void number_of_less_variable_scoped_symbols() throws Exception {
    assertThat(SYMBOL_MODEL.getSymbols(Symbol.Kind.LESS_VARIABLE).size()).isEqualTo(7);
  }

  @Test
  public void blaba_symbol() throws Exception {
    Set<Symbol> symbols = SYMBOL_MODEL.getSymbols("blabla");
    assertThat(SYMBOL_MODEL.getSymbols("blabla").size()).isEqualTo(1);

    Symbol b = null;
    for (Symbol symbol : symbols) {
      if (symbol.scope().tree().is(Tree.Kind.STATEMENT_BLOCK)) {
        b = symbol;
        break;
      }
    }
    assertThat(b).isNotNull();
    assertThat(b.usages()).hasSize(1);
    assertThat(b.usages().stream().map(Usage::kind).collect(Collectors.toSet())).containsOnly(Usage.Kind.DECLARATION);
  }

  @Test
  public void myvar1_symbol_in_statement_block() throws Exception {
    Set<Symbol> symbols = SYMBOL_MODEL.getSymbols("myvar1");

    Symbol b = null;
    for (Symbol symbol : symbols) {
      if (symbol.scope().tree().is(Tree.Kind.STATEMENT_BLOCK)) {
        b = symbol;
        break;
      }
    }
    assertThat(b).isNotNull();
    assertThat(b.usages()).hasSize(3);
    assertThat(b.usages().stream().map(Usage::kind).collect(Collectors.toSet())).containsOnly(Usage.Kind.READ, Usage.Kind.DECLARATION);
    assertThat(b.usages().stream().filter(u -> u.is(Usage.Kind.DECLARATION)).count()).isEqualTo(2);
    assertThat(b.usages().stream().filter(u -> u.is(Usage.Kind.READ)).count()).isEqualTo(1);
  }

  @Test
  public void myvar2_symbol_in_statement_block() throws Exception {
    Set<Symbol> symbols = SYMBOL_MODEL.getSymbols("myvar2");

    Symbol b = null;
    for (Symbol symbol : symbols) {
      if (symbol.scope().tree().is(Tree.Kind.STATEMENT_BLOCK)) {
        b = symbol;
        break;
      }
    }
    assertThat(b).isNotNull();
    assertThat(b.usages()).hasSize(1);
    assertThat(b.usages().stream().map(Usage::kind).collect(Collectors.toSet())).containsOnly(Usage.Kind.READ);
  }

  private SymbolModel symbolModel(File file) {
    StyleSheetTreeImpl tree = (StyleSheetTreeImpl) LessParser.createParser(Charsets.UTF_8).parse(file);
    return new CssTreeVisitorContext(tree, file, "less").getSymbolModel();
  }

}
