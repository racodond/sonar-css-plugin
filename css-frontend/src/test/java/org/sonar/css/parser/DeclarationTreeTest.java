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
import org.sonar.plugins.css.api.tree.DeclarationTree;

import static org.fest.assertions.Assertions.assertThat;

public class DeclarationTreeTest extends TreeTest {

  public DeclarationTreeTest() {
    super(CssLexicalGrammar.DECLARATION);
  }

  @Test
  public void declaration() {
    checkParsed("color:green");
    checkParsed(" color : green");
    checkParsed(" color: \"def\"");
    checkParsed(" color : 123");

    checkParsed("--abc:def");
    checkParsed(" --abc : def");
    checkParsed(" --abc : \"def\"");
    checkParsed(" --abc : 123");
  }

  @Test
  public void notDeclaration() {
    checkNotParsed("color");
    checkNotParsed("color:");

    checkNotParsed("--abc");
    checkNotParsed("--abc:");
  }

  private void checkParsed(String toParse) {
    DeclarationTree tree = (DeclarationTree) parser().parse(toParse);
    assertThat(tree.colon()).isNotNull();
    assertThat(tree.value()).isNotNull();
  }

}
