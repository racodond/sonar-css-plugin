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
package org.sonar.css.parser.css;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.EmptyStatementTree;

import static org.fest.assertions.Assertions.assertThat;

public class EmptyStatementTreeTest extends CssTreeTest {

  public EmptyStatementTreeTest() {
    super(LexicalGrammar.EMPTY_STATEMENT);
  }

  @Test
  public void emptyStatement() {
    checkParsed(";");
    checkParsed(" ;");
    checkParsed("  ;");
  }

  @Test
  public void notEmptyStatement() {
    checkNotParsed("color: green;");
  }

  private void checkParsed(String toParse) {
    EmptyStatementTree tree = (EmptyStatementTree) parser().parse(toParse);
    assertThat(tree.semicolon()).isNotNull();
  }

}
