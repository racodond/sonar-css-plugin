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
package org.sonar.css.parser.less;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.less.LessEscapingTree;

import static org.fest.assertions.Assertions.assertThat;

public class LessEscapingTreeTest extends LessTreeTest {

  public LessEscapingTreeTest() {
    super(LexicalGrammar.LESS_ESCAPING);
  }

  @Test
  public void lessEscaping() {
    checkParsed("~\"abc\"");
    checkParsed("~'abc'");
    checkParsed("~\"abc@{def}ghi\"");
  }

  @Test
  public void notLessEscaping() {
    checkNotParsed("~ \"abc\"");
    checkNotParsed("~ 'abc'");
    checkNotParsed("~abc");
  }

  private void checkParsed(String toParse) {
    LessEscapingTree tree = (LessEscapingTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.escapingSymbol()).isNotNull();
    assertThat(tree.string()).isNotNull();
  }

}
