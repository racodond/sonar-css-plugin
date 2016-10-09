/*
 * SonarQube CSS / Less Plugin
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
package org.sonar.css.parser.embedded;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.embedded.CssInScriptTagTree;

import static org.fest.assertions.Assertions.assertThat;

public class CssInScriptTagTreeTest extends EmbeddedCssTreeTest {

  public CssInScriptTagTreeTest() {
    super(LexicalGrammar.CSS_IN_SCRIPT_TAG);
  }

  @Test
  public void cssInScriptTag() {
    CssInScriptTagTree tree;

    tree = checkParsed(
      "<script type=\"text/css\">\n"
        + "h1 { color: green;}\n"
        + "</script>");
    assertThat(tree.styleSheet().rulesets()).hasSize(1);

    tree = checkParsed(
      "<script type=\"text/css\">\n"
        + "@charset \"UTF-8\";\n"
        + "h1 { color: green;}\n"
        + "h2 { color: green;}\n"
        + "</script>");
    assertThat(tree.styleSheet().rulesets()).hasSize(2);
    assertThat(tree.styleSheet().atRules()).hasSize(1);

    tree = checkParsed(
      "<script type = \"text/css\">\n"
        + "</script >");
    assertThat(tree.styleSheet().rulesets()).hasSize(0);

  }

  @Test
  public void notCssInScriptTag() {
    checkNotParsed("<script></script>");
  }

  private CssInScriptTagTree checkParsed(String toParse) {
    CssInScriptTagTree tree = (CssInScriptTagTree) parser().parse(toParse);
    assertThat(tree.openingTag()).isNotNull();
    assertThat(tree.styleSheet()).isNotNull();
    assertThat(tree.closingTag()).isNotNull();
    return tree;
  }

}
