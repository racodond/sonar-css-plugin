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
import org.sonar.plugins.css.api.tree.css.NumberTree;
import org.sonar.plugins.css.api.tree.css.ParametersTree;
import org.sonar.plugins.css.api.tree.css.StringTree;

import static org.fest.assertions.Assertions.assertThat;

public class ParametersTreeTest extends CssTreeTest {

  public ParametersTreeTest() {
    super(LexicalGrammar.FUNCTION_PARAMETERS);
  }

  @Test
  public void parameters() {
    ParametersTree tree;

    tree = checkParsed("()");
    assertThat(tree.parameters()).isNull();

    tree = checkParsed("(10)");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters()).hasSize(1);
    assertThat(tree.parameters().get(0).valueElements().get(0)).isInstanceOf(NumberTree.class);

    tree = checkParsed("(10, \"string\")");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters()).hasSize(2);
    assertThat(tree.parameters().get(0).valueElements().get(0)).isInstanceOf(NumberTree.class);
    assertThat(tree.parameters().get(1).valueElements().get(0)).isInstanceOf(StringTree.class);

    tree = checkParsed("(10 '10', \"string\")");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters()).hasSize(2);
    assertThat(tree.parameters().get(0).valueElements().get(0)).isInstanceOf(NumberTree.class);
    assertThat(tree.parameters().get(0).valueElements().get(1)).isInstanceOf(StringTree.class);
    assertThat(tree.parameters().get(1).valueElements().get(0)).isInstanceOf(StringTree.class);
  }

  @Test
  public void notParameters() {
    checkNotParsed("abc");
    checkNotParsed(" ()");
  }

  private ParametersTree checkParsed(String toParse) {
    ParametersTree tree = (ParametersTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.openParenthesis()).isNotNull();
    assertThat(tree.closeParenthesis()).isNotNull();
    return tree;
  }

}
