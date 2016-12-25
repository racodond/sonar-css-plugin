/*
 * SonarQube CSS / SCSS / Less Analyzer
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
package org.sonar.css.parser.less;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.less.LessExtendTree;

import static org.fest.assertions.Assertions.assertThat;

public class LessExtendTreeTest extends LessTreeTest {

  public LessExtendTreeTest() {
    super(LexicalGrammar.LESS_EXTEND);
  }

  @Test
  public void lessExtend() {
    LessExtendTree tree;

    tree = checkParsed(":extend(div)");
    assertThat(tree.parameterElements()).hasSize(1);

    tree = checkParsed(" :extend(div)");
    assertThat(tree.parameterElements()).hasSize(1);

    tree = checkParsed(" :extend( .inline, a )");
    assertThat(tree.parameterElements()).hasSize(4);

    tree = checkParsed(" :extend( @{abc}, div )");
    assertThat(tree.parameterElements()).hasSize(3);
    assertThat(tree.parameterElements().get(0)).isInstanceOf(IdentifierTree.class);
    assertThat(((IdentifierTree) tree.parameterElements().get(0)).isLessInterpolated()).isTrue();
  }

  @Test
  public void notLessExtend() {
    checkNotParsed(":extend()");
    checkNotParsed("extend()");
    checkNotParsed("extend(div)");
    checkNotParsed(": extend(div)");
  }

  private LessExtendTree checkParsed(String toParse) {
    LessExtendTree tree = (LessExtendTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.extendKeyword()).isNotNull();
    assertThat(tree.extendKeyword().text()).isEqualTo(":extend");
    assertThat(tree.closeParenthesis()).isNotNull();
    assertThat(tree.parameterElements()).isNotNull();
    assertThat(tree.closeParenthesis()).isNotNull();
    return tree;
  }

}
