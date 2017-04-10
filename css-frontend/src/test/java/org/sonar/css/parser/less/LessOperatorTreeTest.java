/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
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
import org.sonar.plugins.css.api.tree.less.LessOperatorTree;

import static org.fest.assertions.Assertions.assertThat;

public class LessOperatorTreeTest extends LessTreeTest {

  public LessOperatorTreeTest() {
    super(LexicalGrammar.LESS_OPERATOR);
  }

  @Test
  public void lessOperator() {
    LessOperatorTree tree;

    tree = checkParsed("+");
    assertThat(tree.type()).isEqualTo(LessOperatorTree.OPERATOR.PLUS);
    assertThat(tree.operator().text()).isEqualTo("+");

    tree = checkParsed("-");
    assertThat(tree.type()).isEqualTo(LessOperatorTree.OPERATOR.MINUS);
    assertThat(tree.operator().text()).isEqualTo("-");

    tree = checkParsed("*");
    assertThat(tree.type()).isEqualTo(LessOperatorTree.OPERATOR.TIMES);
    assertThat(tree.operator().text()).isEqualTo("*");

    tree = checkParsed("/");
    assertThat(tree.type()).isEqualTo(LessOperatorTree.OPERATOR.DIV);
    assertThat(tree.operator().text()).isEqualTo("/");
  }

  @Test
  public void notLessOperator() {
    checkNotParsed("=");
  }

  private LessOperatorTree checkParsed(String toParse) {
    LessOperatorTree tree = (LessOperatorTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.text()).isNotNull();
    return tree;
  }

}
