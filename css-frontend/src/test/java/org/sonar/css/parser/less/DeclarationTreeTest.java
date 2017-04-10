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
import org.sonar.plugins.css.api.tree.css.DeclarationTree;
import org.sonar.plugins.css.api.tree.css.PropertyDeclarationTree;
import org.sonar.plugins.css.api.tree.css.VariableDeclarationTree;
import org.sonar.plugins.css.api.tree.less.LessVariableDeclarationTree;

import static org.fest.assertions.Assertions.assertThat;

public class DeclarationTreeTest extends LessTreeTest {

  public DeclarationTreeTest() {
    super(LexicalGrammar.DECLARATION);
  }

  @Test
  public void declaration() {
    DeclarationTree tree;

    tree = checkParsed("color:green");
    assertThat(tree).isInstanceOf(PropertyDeclarationTree.class);

    tree = checkParsed(" color : green");
    assertThat(tree).isInstanceOf(PropertyDeclarationTree.class);

    tree = checkParsed(" color: \"def\"");
    assertThat(tree).isInstanceOf(PropertyDeclarationTree.class);

    tree = checkParsed(" color : 123");
    assertThat(tree).isInstanceOf(PropertyDeclarationTree.class);

    tree = checkParsed("color:green;");
    assertThat(tree).isInstanceOf(PropertyDeclarationTree.class);
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed(" color : green ;");
    assertThat(tree).isInstanceOf(PropertyDeclarationTree.class);
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed(" color: \"def\";");
    assertThat(tree).isInstanceOf(PropertyDeclarationTree.class);
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed(" color : 123 ;");
    assertThat(tree).isInstanceOf(PropertyDeclarationTree.class);
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed("--abc:def");
    assertThat(tree).isInstanceOf(VariableDeclarationTree.class);

    tree = checkParsed(" --abc : def");
    assertThat(tree).isInstanceOf(VariableDeclarationTree.class);

    tree = checkParsed(" --abc : \"def\"");
    assertThat(tree).isInstanceOf(VariableDeclarationTree.class);

    tree = checkParsed(" --abc : 123");
    assertThat(tree).isInstanceOf(VariableDeclarationTree.class);

    tree = checkParsed("--abc:def;");
    assertThat(tree).isInstanceOf(VariableDeclarationTree.class);
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed(" --abc : def ;");
    assertThat(tree).isInstanceOf(VariableDeclarationTree.class);
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed(" --abc : \"def\";");
    assertThat(tree).isInstanceOf(VariableDeclarationTree.class);
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed(" --abc : 123 ;");
    assertThat(tree).isInstanceOf(VariableDeclarationTree.class);
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed("@abc: def");
    assertThat(tree).isInstanceOf(LessVariableDeclarationTree.class);

    tree = checkParsed(" @abc : def");
    assertThat(tree).isInstanceOf(LessVariableDeclarationTree.class);

    tree = checkParsed(" @abc : \"def\"");
    assertThat(tree).isInstanceOf(LessVariableDeclarationTree.class);

    tree = checkParsed(" @abc : 123");
    assertThat(tree).isInstanceOf(LessVariableDeclarationTree.class);

    tree = checkParsed("@abc: def;");
    assertThat(tree).isInstanceOf(LessVariableDeclarationTree.class);
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed(" @abc : def ;");
    assertThat(tree).isInstanceOf(LessVariableDeclarationTree.class);
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed(" @abc : \"def\";");
    assertThat(tree).isInstanceOf(LessVariableDeclarationTree.class);
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed(" @abc : 123 ;");
    assertThat(tree).isInstanceOf(LessVariableDeclarationTree.class);
    assertThat(tree.semicolon()).isNotNull();
  }

  @Test
  public void notDeclaration() {
    checkNotParsed("color");
    checkNotParsed("color:");

    checkNotParsed("--abc");
    checkNotParsed("--abc:");

    checkNotParsed("@abc");
    checkNotParsed("@abc:");

    checkNotParsed(";");
    checkNotParsed(" ;");
  }

  private DeclarationTree checkParsed(String toParse) {
    DeclarationTree tree = (DeclarationTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    return tree;
  }

}
