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
package org.sonar.css.parser.css;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.ImportantFlagTree;

import static org.fest.assertions.Assertions.assertThat;

public class ImportantFlagTreeTest extends CssTreeTest {

  public ImportantFlagTreeTest() {
    super(LexicalGrammar.IMPORTANT_FLAG);
  }

  @Test
  public void importantFlag() {
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
  public void notImportantFlag() {
    checkNotParsed("important");
    checkNotParsed("!import");
  }

  private void checkParsed(String toParse, String expectedImportantString) {
    ImportantFlagTree tree = (ImportantFlagTree) parser().parse(toParse);
    assertThat(tree.exclamationMark()).isNotNull();
    assertThat(tree.exclamationMark().text()).isEqualTo("!");
    assertThat(tree.importantKeyword()).isNotNull();
    assertThat(tree.importantKeyword().text()).isEqualTo(expectedImportantString);
  }

}
