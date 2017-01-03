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
import org.sonar.plugins.css.api.tree.scss.ScssElseTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssElseTreeTest extends ScssTreeTest {

  public ScssElseTreeTest() {
    super(LexicalGrammar.SCSS_ELSE);
  }

  @Test
  public void scssElse() {
    ScssElseTree tree;

    tree = checkParsed("@else {}");
    assertThat(tree.block().content()).isNull();

    tree = checkParsed("@else { $abc: green; color: $abc;}");
    assertThat(tree.block().content()).isNotNull();
    assertThat(tree.block().scssVariableDeclarations()).hasSize(1);
    assertThat(tree.block().propertyDeclarations()).hasSize(1);
  }

  @Test
  public void notScssElse() {
    checkNotParsed("@else");
    checkNotParsed("@else a {}");
    checkNotParsed("@else;");
  }

  private ScssElseTree checkParsed(String toParse) {
    ScssElseTree tree = (ScssElseTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.directive()).isNotNull();
    assertThat(tree.directive().at()).isNotNull();
    assertThat(tree.directive().name().text()).isEqualTo("else");
    assertThat(tree.block()).isNotNull();
    return tree;
  }

}
