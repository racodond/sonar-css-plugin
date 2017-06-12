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
import org.sonar.plugins.css.api.tree.less.LessMixinCallTree;

import static org.fest.assertions.Assertions.assertThat;

public class LessMixinCallTreeTest extends LessTreeTest {

  public LessMixinCallTreeTest() {
    super(LexicalGrammar.LESS_MIXIN_CALL);
  }

  @Test
  public void lessMixinCall() {
    LessMixinCallTree tree;

    tree = checkParsed("#id");
    assertThat(tree.important()).isNull();
    assertThat(tree.semicolon()).isNull();

    tree = checkParsed(".class");
    assertThat(tree.important()).isNull();
    assertThat(tree.semicolon()).isNull();

    tree = checkParsed("#id();");
    assertThat(tree.important()).isNull();
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed(".class();");
    assertThat(tree.important()).isNull();
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed("#id () ;");
    assertThat(tree.important()).isNull();
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed(".class () ;");
    assertThat(tree.important()).isNull();
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed("#outer.inner;");
    assertThat(tree.important()).isNull();
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed("#outer.inner();");
    assertThat(tree.important()).isNull();
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed("#outer>.inner;");
    assertThat(tree.important()).isNull();
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed("#outer>.inner();");
    assertThat(tree.important()).isNull();
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed("#id !important");
    assertThat(tree.important()).isNotNull();
    assertThat(tree.semicolon()).isNull();

    tree = checkParsed(".class !important;");
    assertThat(tree.important()).isNotNull();
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed("#outer>.inner() !important ;");
    assertThat(tree.important()).isNotNull();
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed(" > #outer>.inner() !important ;");
    assertThat(tree.parentCombinator()).isNotNull();

    tree = checkParsed("> #outer>.inner() !important ;");
    assertThat(tree.parentCombinator()).isNotNull();

    checkParsed(".sprites(@sprites, @i + 1);");
  }

  @Test
  public void notLessMixinCall() {
    checkNotParsed("(");
    checkNotParsed(")");
  }

  private LessMixinCallTree checkParsed(String toParse) {
    LessMixinCallTree tree = (LessMixinCallTree) parser().parse(toParse);
    assertThat(tree.selector()).isNotNull();
    return tree;
  }

}
