/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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
package org.sonar.css.parser.embedded;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.embedded.CssInStyleTagTree;

import static org.fest.assertions.Assertions.assertThat;

public class CssInStyleTagTreeTest extends EmbeddedCssTreeTest {

  public CssInStyleTagTreeTest() {
    super(LexicalGrammar.CSS_IN_STYLE_TAG);
  }

  @Test
  public void cssInStyleTag() {
    CssInStyleTagTree tree;

    tree = checkParsed(
      "<style type=\"text/css\">\n"
        + "h1 { color: green;}\n"
        + "</style>");
    assertThat(tree.styleSheet().rulesets()).hasSize(1);

    tree = checkParsed(
      "<style type=\"text/css\">\n"
        + "@charset \"UTF-8\";\n"
        + "h1 { color: green;}\n"
        + "h2 { color: green;}\n"
        + "</style>");
    assertThat(tree.styleSheet().rulesets()).hasSize(2);
    assertThat(tree.styleSheet().atRules()).hasSize(1);

    tree = checkParsed(
      "<style type = \"text/css\">\n"
        + "</style >");
    assertThat(tree.styleSheet().rulesets()).isEmpty();

  }

  @Test
  public void notCssInStyleTag() {
    checkNotParsed("<style></style>");
  }

  private CssInStyleTagTree checkParsed(String toParse) {
    CssInStyleTagTree tree = (CssInStyleTagTree) parser().parse(toParse);
    assertThat(tree.openingTag()).isNotNull();
    assertThat(tree.styleSheet()).isNotNull();
    assertThat(tree.closingTag()).isNotNull();
    return tree;
  }

}
