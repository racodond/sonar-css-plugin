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
import org.sonar.plugins.css.api.tree.css.ClassSelectorTree;
import org.sonar.plugins.css.api.tree.css.PseudoSelectorTree;
import org.sonar.plugins.css.api.tree.css.TypeSelectorTree;
import org.sonar.plugins.css.api.tree.scss.ScssExtendTree;
import org.sonar.plugins.css.api.tree.scss.ScssPlaceholderSelectorTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssExtendTreeTest extends ScssTreeTest {

  public ScssExtendTreeTest() {
    super(LexicalGrammar.SCSS_EXTEND);
  }

  @Test
  public void scssExtend() {
    ScssExtendTree tree;

    tree = checkParsed("@extend a");
    assertThat(tree.selector().selectors()).hasSize(1);
    assertThat(tree.selector().selectors().get(0)).isInstanceOf(TypeSelectorTree.class);
    assertThat(tree.optionalFlag()).isNull();
    assertThat(tree.semicolon()).isNull();

    tree = checkParsed("@extend a;");
    assertThat(tree.selector().selectors()).hasSize(1);
    assertThat(tree.selector().selectors().get(0)).isInstanceOf(TypeSelectorTree.class);
    assertThat(tree.optionalFlag()).isNull();
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed("@extend .class;");
    assertThat(tree.selector().selectors()).hasSize(1);
    assertThat(tree.selector().selectors().get(0)).isInstanceOf(ClassSelectorTree.class);
    assertThat(tree.optionalFlag()).isNull();
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed("@extend %foo;");
    assertThat(tree.selector().selectors()).hasSize(1);
    assertThat(tree.selector().selectors().get(0)).isInstanceOf(ScssPlaceholderSelectorTree.class);
    assertThat(tree.optionalFlag()).isNull();
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed("@extend a:hover;");
    assertThat(tree.selector().selectors()).hasSize(2);
    assertThat(tree.selector().selectors().get(0)).isInstanceOf(TypeSelectorTree.class);
    assertThat(tree.selector().selectors().get(1)).isInstanceOf(PseudoSelectorTree.class);
    assertThat(tree.optionalFlag()).isNull();
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed("@extend a:hover !optional;");
    assertThat(tree.selector().selectors()).hasSize(2);
    assertThat(tree.selector().selectors().get(0)).isInstanceOf(TypeSelectorTree.class);
    assertThat(tree.selector().selectors().get(1)).isInstanceOf(PseudoSelectorTree.class);
    assertThat(tree.optionalFlag()).isNotNull();
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed("@extend a:hover !optional");
    assertThat(tree.selector().selectors()).hasSize(2);
    assertThat(tree.selector().selectors().get(0)).isInstanceOf(TypeSelectorTree.class);
    assertThat(tree.selector().selectors().get(1)).isInstanceOf(PseudoSelectorTree.class);
    assertThat(tree.optionalFlag()).isNotNull();
    assertThat(tree.semicolon()).isNull();
  }

  @Test
  public void notScssExtend() {
    checkNotParsed("@extend");
    checkNotParsed("@extend()");
    checkNotParsed("@extend;");
  }

  private ScssExtendTree checkParsed(String toParse) {
    ScssExtendTree tree = (ScssExtendTree) parser().parse(toParse);
    assertThat(tree.directive()).isNotNull();
    assertThat(tree.directive().at()).isNotNull();
    assertThat(tree.directive().name().text()).isEqualTo("extend");
    assertThat(tree.selector()).isNotNull();
    return tree;
  }

}
