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
package org.sonar.css.parser.scss;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.scss.ScssVariableDeclarationTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssVariableDeclarationTreeTest extends ScssTreeTest {

  public ScssVariableDeclarationTreeTest() {
    super(LexicalGrammar.SCSS_VARIABLE_DECLARATION);
  }

  @Test
  public void scssVariableDeclaration() {
    ScssVariableDeclarationTree tree;

    tree = checkParsed("$abc:def blue");
    assertThat(tree.variable().name().text()).isEqualTo("abc");
    assertThat(tree.value().sanitizedValueElements()).isNotNull();

    tree = checkParsed(" $abc : def");
    assertThat(tree.variable().name().text()).isEqualTo("abc");

    tree = checkParsed(" $abc : 10 !default");
    assertThat(tree.variable().name().text()).isEqualTo("abc");
    assertThat(tree.defaultFlag()).isNotNull();
    assertThat(tree.defaultFlag().text()).isEqualTo("!default");

    tree = checkParsed(" $abc : 10 !global");
    assertThat(tree.variable().name().text()).isEqualTo("abc");
    assertThat(tree.globalFlag()).isNotNull();
    assertThat(tree.globalFlag().text()).isEqualTo("!global");

    checkParsed(" $abc : \"def\"");
    checkParsed(" $abc : 123");
    checkParsed(" $abc : 123;");
    checkParsed(" $abc: 10, $def");

    checkParsed("$grid-gutter-widths: (\n" +
      "  xs: $grid-gutter-width-base,\n" +
      "  sm: $grid-gutter-width-base,\n" +
      "  md: $grid-gutter-width-base,\n" +
      "  lg: $grid-gutter-width-base,\n" +
      "  xl: $grid-gutter-width-base\n" +
      ") !default;");
  }

  @Test
  public void notScssVariableDeclaration() {
    checkNotParsed("$abc");
    checkNotParsed("$abc:");
    checkNotParsed("!default:");
  }

  private ScssVariableDeclarationTree checkParsed(String toParse) {
    ScssVariableDeclarationTree tree = (ScssVariableDeclarationTree) parser().parse(toParse);
    assertThat(tree.variable()).isNotNull();
    assertThat(tree.variable().prefix()).isNotNull();
    assertThat(tree.variable().prefix().text()).isEqualTo("$");
    assertThat(tree.colon()).isNotNull();
    assertThat(tree.value()).isNotNull();
    return tree;
  }

}
