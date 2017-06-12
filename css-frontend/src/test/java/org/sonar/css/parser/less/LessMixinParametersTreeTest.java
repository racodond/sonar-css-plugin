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
package org.sonar.css.parser.less;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.less.LessMixinParametersTree;

import static org.fest.assertions.Assertions.assertThat;

public class LessMixinParametersTreeTest extends LessTreeTest {

  public LessMixinParametersTreeTest() {
    super(LexicalGrammar.LESS_MIXIN_PARAMETERS);
  }

  @Test
  public void lessMixinParameters() {
    LessMixinParametersTree tree;

    tree = checkParsed("()");
    assertThat(tree.parameters()).isNull();

    tree = checkParsed("( )");
    assertThat(tree.parameters()).isNull();

    tree = checkParsed(" ()");
    assertThat(tree.parameters()).isNull();

    tree = checkParsed(" ( )");
    assertThat(tree.parameters()).isNull();

    tree = checkParsed("(@var: blue)");
    assertThat(tree.parameters()).hasSize(1);

    tree = checkParsed("(@var: blue;)");
    assertThat(tree.parameters()).hasSize(1);

    tree = checkParsed("(@var: blue,)");
    assertThat(tree.parameters()).hasSize(1);

    tree = checkParsed("(@var: blue; @myvar: blue)");
    assertThat(tree.parameters()).hasSize(2);

    tree = checkParsed("(@var: blue; @blalba ; @myvar: blue)");
    assertThat(tree.parameters()).hasSize(3);

    tree = checkParsed("(@var: blue; \"blue\" ; @myvar: blue)");
    assertThat(tree.parameters()).hasSize(3);

    tree = checkParsed("(@var: blue; ...)");
    assertThat(tree.parameters()).hasSize(2);

    tree = checkParsed("(@var: blue; @myvar...)");
    assertThat(tree.parameters()).hasSize(2);

    tree = checkParsed("(@var: blue, @myvar: blue)");
    assertThat(tree.parameters()).hasSize(2);

    tree = checkParsed("(@var: blue, green, @myvar: blue)");
    assertThat(tree.parameters()).hasSize(3);

    // FIXME: Deal with semicolon-separated list of parameters
    // tree = checkParsed("(@var: blue, green; @myvar: blue)");
    // assertThat(tree.parameters()).hasSize(2);
  }

  @Test
  public void notLessMixinParameters() {
    checkNotParsed("(");
    checkNotParsed(")");
  }

  private LessMixinParametersTree checkParsed(String toParse) {
    LessMixinParametersTree tree = (LessMixinParametersTree) parser().parse(toParse);
    assertThat(tree.openParenthesis()).isNotNull();
    assertThat(tree.closeParenthesis()).isNotNull();
    return tree;
  }

}
