/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2017 David RACODON
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
import org.sonar.plugins.css.api.tree.scss.ScssContentTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssContentTreeTest extends ScssTreeTest {

  public ScssContentTreeTest() {
    super(LexicalGrammar.SCSS_CONTENT);
  }

  @Test
  public void scssContent() {
    ScssContentTree tree;

    tree = checkParsed("@content");
    assertThat(tree.semicolon()).isNull();

    tree = checkParsed(" @content");
    assertThat(tree.semicolon()).isNull();

    tree = checkParsed("@content;");
    assertThat(tree.semicolon()).isNotNull();
  }

  @Test
  public void notScssContent() {
    checkNotParsed("@debug");
    checkNotParsed("@debug;");
  }

  private ScssContentTree checkParsed(String toParse) {
    ScssContentTree tree = (ScssContentTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.directive()).isNotNull();
    assertThat(tree.directive().at()).isNotNull();
    assertThat(tree.directive().name().text()).isEqualTo("content");
    return tree;
  }

}
