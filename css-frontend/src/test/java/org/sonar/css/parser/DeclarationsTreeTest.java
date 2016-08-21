/*
 * SonarQube CSS Plugin
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
package org.sonar.css.parser;

import org.junit.Test;
import org.sonar.plugins.css.api.tree.DeclarationsTree;

import static org.fest.assertions.Assertions.assertThat;

public class DeclarationsTreeTest extends TreeTest {

  public DeclarationsTreeTest() {
    super(CssLexicalGrammar.DECLARATIONS);
  }

  @Test
  public void declarations() {
    DeclarationsTree tree;

    tree = checkParsed("color:green");
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyDeclarations()).isEmpty();

    tree = checkParsed(" color : green");
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyDeclarations()).isEmpty();

    tree = checkParsed("color : green;\ncolor: \"abc\"");
    assertThat(tree).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyDeclarations()).isEmpty();

    tree = checkParsed("color : green;\ncolor: \"abc\" \n;");
    assertThat(tree).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyDeclarations()).isEmpty();

    tree = checkParsed("ncolor : green;\ncolor: red;\n--myvar: blabla");
    assertThat(tree).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.variableDeclarations()).hasSize(1);
    assertThat(tree.emptyDeclarations()).isEmpty();

    tree = checkParsed("color : green;\ncolor: red;\n--myvar: blabla;");
    assertThat(tree).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.variableDeclarations()).hasSize(1);
    assertThat(tree.emptyDeclarations()).isEmpty();

    tree = checkParsed(";");
    assertThat(tree).isNotNull();
    assertThat(tree.propertyDeclarations()).isEmpty();
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyDeclarations()).hasSize(1);

    tree = checkParsed(";;;");
    assertThat(tree).isNotNull();
    assertThat(tree.propertyDeclarations()).isEmpty();
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyDeclarations()).hasSize(3);

    tree = checkParsed(" ; ; ; ");
    assertThat(tree).isNotNull();
    assertThat(tree.propertyDeclarations()).isEmpty();
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyDeclarations()).hasSize(3);

    tree = checkParsed(";;; color: green");
    assertThat(tree).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyDeclarations()).hasSize(3);

    tree = checkParsed(";;; color: green;");
    assertThat(tree).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyDeclarations()).hasSize(3);

    tree = checkParsed(";;; color: green;;");
    assertThat(tree).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyDeclarations()).hasSize(4);
  }

  @Test
  public void notDeclarations() {
    checkNotParsed("color");
    checkNotParsed("color:");
    checkNotParsed("color:;");
    checkNotParsed("color:;color:green");
  }

  private DeclarationsTree checkParsed(String toParse) {
    DeclarationsTree tree = (DeclarationsTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.propertyDeclarations()).isNotNull();
    assertThat(tree.variableDeclarations()).isNotNull();
    assertThat(tree.emptyDeclarations()).isNotNull();
    return tree;
  }

}
