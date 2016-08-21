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
import org.sonar.plugins.css.api.tree.ImportantTree;

import static org.fest.assertions.Assertions.assertThat;

public class ImportantTreeTest extends TreeTest {

  public ImportantTreeTest() {
    super(CssLexicalGrammar.IMPORTANT);
  }

  @Test
  public void important() {
    checkParsed("!important", "important");
    checkParsed(" !important", "important");
    checkParsed("! important", "important");
    checkParsed(" ! important", "important");
    checkParsed("!  important", "important");
    checkParsed("!IMPORTANT", "IMPORTANT");
    checkParsed("! IMPORTANT", "IMPORTANT");
    checkParsed("!  IMPORTANT", "IMPORTANT");
    checkParsed("!Important", "Important");
    checkParsed("!ImporTant", "ImporTant");
  }

  @Test
  public void notImportant() {
    checkNotParsed("important");
    checkNotParsed("!import");
  }

  private void checkParsed(String toParse, String expectedImportantString) {
    ImportantTree tree = (ImportantTree) parser().parse(toParse);
    assertThat(tree.exclamationMark()).isNotNull();
    assertThat(tree.exclamationMark().text()).isEqualTo("!");
    assertThat(tree.importantKeyword()).isNotNull();
    assertThat(tree.importantKeyword().text()).isEqualTo(expectedImportantString);
  }

}
