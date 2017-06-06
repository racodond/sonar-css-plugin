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
package org.sonar.css.parser.scss;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.scss.ScssOperatorTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssOperatorTreeTest extends ScssTreeTest {

  public ScssOperatorTreeTest() {
    super(LexicalGrammar.SCSS_OPERATOR);
  }

  @Test
  public void scssOperator() {
    ScssOperatorTree tree;

    tree = checkParsed("/");
    assertThat(tree.type()).isEqualTo(ScssOperatorTree.OPERATOR.DIV);
    assertThat(tree.operator().text()).isEqualTo("/");

    tree = checkParsed("-");
    assertThat(tree.type()).isEqualTo(ScssOperatorTree.OPERATOR.MINUS);
    assertThat(tree.operator().text()).isEqualTo("-");

    tree = checkParsed("==");
    assertThat(tree.type()).isEqualTo(ScssOperatorTree.OPERATOR.DOUBLE_EQUALS);
    assertThat(tree.operator().text()).isEqualTo("==");

    tree = checkParsed("=");
    assertThat(tree.type()).isEqualTo(ScssOperatorTree.OPERATOR.EQUALS);
    assertThat(tree.operator().text()).isEqualTo("=");

    tree = checkParsed("and");
    assertThat(tree.type()).isEqualTo(ScssOperatorTree.OPERATOR.AND);
    assertThat(tree.operator().text()).isEqualTo("and");

    tree = checkParsed("or");
    assertThat(tree.type()).isEqualTo(ScssOperatorTree.OPERATOR.OR);
    assertThat(tree.operator().text()).isEqualTo("or");

    tree = checkParsed(" %");
    assertThat(tree.type()).isEqualTo(ScssOperatorTree.OPERATOR.MODULO);
    assertThat(tree.operator().text()).isEqualTo("%");
  }

  @Test
  public void notScssOperator() {
    checkNotParsed("a");
  }

  private ScssOperatorTree checkParsed(String toParse) {
    ScssOperatorTree tree = (ScssOperatorTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.text()).isNotNull();
    return tree;
  }

}
