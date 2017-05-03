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
import org.sonar.plugins.css.api.tree.css.IdentifierTree;

import static org.fest.assertions.Assertions.assertThat;

public class LessInterpolatedIdentifierNoWSTreeTest extends LessTreeTest {

  public LessInterpolatedIdentifierNoWSTreeTest() {
    super(LexicalGrammar.LESS_INTERPOLATED_IDENTIFIER_NO_WS);
  }

  @Test
  public void lessInterpolatedIdentifierNoWS() {
    checkParsed("@{class}");
    checkParsed("@{cla-ss}");
    checkParsed("@{cla_ss}");
    checkParsed("@{cla0ss}");
    checkParsed("abc@{class}");
    checkParsed("abc@{class}@{class1}def");
    checkParsed("-moz-abc@{class}e@{class1}def");
  }

  @Test
  public void notLessInterpolatedIdentifierNoWS() {
    checkNotParsed("*");
    checkNotParsed("abc");
    checkNotParsed(".");
    checkNotParsed(".class");
    checkNotParsed(".class.class");
    checkNotParsed("123");
    checkNotParsed("123px");
  }

  private void checkParsed(String toParse) {
    IdentifierTree tree = (IdentifierTree) parser().parse(toParse);
    assertThat(tree.value()).isNotNull();
    assertThat(tree.text()).isEqualTo(toParse);
    assertThat(tree.isLessInterpolated()).isTrue();
    assertThat(tree.isInterpolated()).isTrue();
  }

}
