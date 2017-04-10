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
import org.sonar.plugins.css.api.tree.less.LessVariableDeclarationTree;

import static org.fest.assertions.Assertions.assertThat;

public class LessVariableDeclarationTreeTest extends LessTreeTest {

  public LessVariableDeclarationTreeTest() {
    super(LexicalGrammar.LESS_VARIABLE_DECLARATION);
  }

  @Test
  public void lessVariableDeclaration() {
    LessVariableDeclarationTree tree;

    tree = checkParsed("@abc:def");
    assertThat(tree.variable().variableName()).isEqualTo("abc");
    assertThat(tree.value().sanitizedValueElements()).isNotNull();

    tree = checkParsed(" @abc : def");
    assertThat(tree.variable().variableName()).isEqualTo("abc");

    tree = checkParsed(" @abc : @def");
    assertThat(tree.variable().variableName()).isEqualTo("abc");

    tree = checkParsed(" @abc : @@def");
    assertThat(tree.variable().variableName()).isEqualTo("abc");
    // TODO: to be improved because @@def is currently parsed as Delimiter + Less variable

    checkParsed(" @abc : \"def\"");
    checkParsed(" @abc : 123");

    checkParsed("@my-ruleset: {.my-selector { background-color: black; } };");
  }

  @Test
  public void notLessVariableDeclaration() {
    checkNotParsed("@abc");
    checkNotParsed("@abc:");
  }

  private LessVariableDeclarationTree checkParsed(String toParse) {
    LessVariableDeclarationTree tree = (LessVariableDeclarationTree) parser().parse(toParse);
    assertThat(tree.variable()).isNotNull();
    assertThat(tree.colon()).isNotNull();
    assertThat(tree.value()).isNotNull();
    return tree;
  }

}
