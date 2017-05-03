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
import org.sonar.plugins.css.api.tree.less.LessMixinGuardTree;

import static org.fest.assertions.Assertions.assertThat;

public class LessMixinGuardTreeTest extends LessTreeTest {

  public LessMixinGuardTreeTest() {
    super(LexicalGrammar.LESS_MIXIN_GUARD);
  }

  @Test
  public void lessMixinGuard() {
    LessMixinGuardTree tree;

    tree = checkParsed("when (@a > 2)");
    assertThat(tree.conditions()).hasSize(1);

    tree = checkParsed(" when (@a > 2) ");
    assertThat(tree.conditions()).hasSize(1);

    tree = checkParsed(" when not (@a > 2) ");
    assertThat(tree.not()).isNotNull();
    assertThat(tree.conditions()).hasSize(1);

    tree = checkParsed(" when (@a > 2), (@a = \"blue\"), (@b) ");
    assertThat(tree.conditions()).hasSize(3);

    tree = checkParsed("when (@type = width) and (@index > 0)");
    assertThat(tree.conditions()).hasSize(2);

    tree = checkParsed("when (@type = width) and (@index > 0),(@index > 1)");
    assertThat(tree.conditions()).hasSize(3);
  }

  @Test
  public void notLessMixinGuard() {
    checkNotParsed("when");
  }

  private LessMixinGuardTree checkParsed(String toParse) {
    LessMixinGuardTree tree = (LessMixinGuardTree) parser().parse(toParse);
    assertThat(tree.when()).isNotNull();
    assertThat(tree.conditions()).isNotNull();
    return tree;
  }

}
