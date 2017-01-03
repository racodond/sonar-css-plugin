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
package org.sonar.css.parser.scss;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.scss.ScssParametersTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssCallParametersTreeTest extends ScssTreeTest {

  public ScssCallParametersTreeTest() {
    super(LexicalGrammar.SCSS_CALL_PARAMETERS);
  }

  @Test
  public void scssCallParameters() {
    ScssParametersTree tree;

    tree = checkParsed("()");
    assertThat(tree.parameters()).isNull();

    tree = checkParsed(" ()");
    assertThat(tree.parameters()).isNull();

    tree = checkParsed(" ( )");
    assertThat(tree.parameters()).isNull();

    tree = checkParsed("($abc: 10)");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters()).hasSize(1);
    assertThat(tree.parameters().get(0).variableDeclaration()).isNotNull();

    tree = checkParsed("($abc: 10, $def: \"string\")");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters()).hasSize(2);
    assertThat(tree.parameters().get(0).variableDeclaration()).isNotNull();
    assertThat(tree.parameters().get(1).variableDeclaration()).isNotNull();

    tree = checkParsed("($abc: 10, $def: 2 * 10)");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters()).hasSize(2);
    assertThat(tree.parameters().get(0).variableDeclaration()).isNotNull();
    assertThat(tree.parameters().get(1).variableDeclaration()).isNotNull();

    tree = checkParsed(" ( $abc )");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters()).hasSize(1);
    assertThat(tree.parameters().get(0).value()).isNotNull();

    tree = checkParsed(" ( $abc, $def )");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters()).hasSize(2);
    assertThat(tree.parameters().get(0).value()).isNotNull();
    assertThat(tree.parameters().get(1).value()).isNotNull();

    tree = checkParsed(" ( $abc, $def : 10)");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters()).hasSize(2);
    assertThat(tree.parameters().get(0).value()).isNotNull();
    assertThat(tree.parameters().get(1).variableDeclaration()).isNotNull();

    tree = checkParsed(" (abc, $def : 10)");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters()).hasSize(2);
    assertThat(tree.parameters().get(0).value()).isNotNull();
    assertThat(tree.parameters().get(1).variableDeclaration()).isNotNull();
  }

  private ScssParametersTree checkParsed(String toParse) {
    ScssParametersTree tree = (ScssParametersTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.openParenthesis()).isNotNull();
    assertThat(tree.closeParenthesis()).isNotNull();
    return tree;
  }

}
