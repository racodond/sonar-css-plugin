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
import org.sonar.plugins.css.api.tree.BracketBlockTree;

import static org.fest.assertions.Assertions.assertThat;

public class BracketBlockTreeTest extends TreeTest {

  public BracketBlockTreeTest() {
    super(CssLexicalGrammar.BRACKET_BLOCK_TREE);
  }

  @Test
  public void bracketBlock() {
    checkParsed("[abc]");
    checkParsed(" [abc] ");
    checkParsed(" [ abc ] ");
    checkParsed(" [ abc, abc ] ");
  }

  @Test
  public void notBracketBlock() {
    checkNotParsed("[");
    checkNotParsed("[abc");
  }

  private void checkParsed(String toParse) {
    BracketBlockTree tree = (BracketBlockTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.openBracket()).isNotNull();
    assertThat(tree.content()).isNotNull();
    assertThat(tree.closeBracket()).isNotNull();
  }

}
