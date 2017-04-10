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
import org.sonar.plugins.css.api.tree.scss.ScssElseIfTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssElseIfTreeTest extends ScssTreeTest {

  public ScssElseIfTreeTest() {
    super(LexicalGrammar.SCSS_ELSE_IF);
  }

  @Test
  public void scssElseIf() {
    ScssElseIfTree tree;

    tree = checkParsed("@elseif $a == 1 {}");
    assertThat(tree.directive().name().text()).isEqualTo("elseif");
    assertThat(tree.block().content()).isEmpty();

    tree = checkParsed("@else if $a >= 1 and $b != 2 {}");
    assertThat(tree.directive().name().text()).isEqualTo("else if");
    assertThat(tree.block().content()).isEmpty();

    tree = checkParsed("@else  if $a == 1/2 {}");
    assertThat(tree.directive().name().text()).isEqualTo("else  if");
    assertThat(tree.block().content()).isEmpty();

    tree = checkParsed("@elseif $a == 1*6+1-2 { $abc: green; color: $abc;}");
    assertThat(tree.block().content()).hasSize(2);
    assertThat(tree.block().scssVariableDeclarations()).hasSize(1);
    assertThat(tree.block().propertyDeclarations()).hasSize(1);
    assertThat(tree.directive().name().text()).isEqualTo("elseif");
  }

  @Test
  public void notScssElseIf() {
    checkNotParsed("@else if");
    checkNotParsed("@else if {}");
    checkNotParsed("@else if;");
  }

  private ScssElseIfTree checkParsed(String toParse) {
    ScssElseIfTree tree = (ScssElseIfTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.directive()).isNotNull();
    assertThat(tree.directive().at()).isNotNull();
    assertThat(tree.condition()).isNotNull();
    assertThat(tree.block()).isNotNull();
    return tree;
  }

}
