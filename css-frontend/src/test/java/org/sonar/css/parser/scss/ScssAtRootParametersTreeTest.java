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
package org.sonar.css.parser.scss;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.scss.ScssAtRootParametersTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssAtRootParametersTreeTest extends ScssTreeTest {

  public ScssAtRootParametersTreeTest() {
    super(LexicalGrammar.SCSS_AT_ROOT_PARAMETERS);
  }

  @Test
  public void scssAtRootParameters() {
    ScssAtRootParametersTree tree;

    tree = checkParsed("(with: abc)");
    assertThat(tree.parameter().text()).isEqualTo("with");
    assertThat(tree.values()).hasSize(1);

    tree = checkParsed(" ( with : abc ) ");
    assertThat(tree.parameter().text()).isEqualTo("with");
    assertThat(tree.values()).hasSize(1);

    tree = checkParsed(" ( without : abc ) ");
    assertThat(tree.parameter().text()).isEqualTo("without");
    assertThat(tree.values()).hasSize(1);

    tree = checkParsed(" ( without : abc def ) ");
    assertThat(tree.parameter().text()).isEqualTo("without");
    assertThat(tree.values()).hasSize(2);

    tree = checkParsed(" ( without : * ) ");
    assertThat(tree.parameter().text()).isEqualTo("without");
    assertThat(tree.values()).hasSize(1);
  }

  @Test
  public void notScssAtRootParameters() {
    checkNotParsed("()");
    checkNotParsed("(with:)");
  }

  private ScssAtRootParametersTree checkParsed(String toParse) {
    ScssAtRootParametersTree tree = (ScssAtRootParametersTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.openParenthesis()).isNotNull();
    assertThat(tree.parameter()).isNotNull();
    assertThat(tree.values()).isNotEmpty();
    assertThat(tree.colon()).isNotNull();
    assertThat(tree.closeParenthesis()).isNotNull();
    return tree;
  }

}
