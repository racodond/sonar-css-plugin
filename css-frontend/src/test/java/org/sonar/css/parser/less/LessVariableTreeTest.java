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
package org.sonar.css.parser.less;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.less.LessVariableTree;

import static org.fest.assertions.Assertions.assertThat;

public class LessVariableTreeTest extends LessTreeTest {

  public LessVariableTreeTest() {
    super(LexicalGrammar.LESS_VARIABLE);
  }

  @Test
  public void lessVariable() {
    checkParsed("@abc", "abc");
    checkParsed(" @abc", "abc");
    checkParsed("@abc-def", "abc-def");
    checkParsed("@abc--def", "abc--def");
    checkParsed("@ABC", "ABC");
  }

  @Test
  public void notLessVariable() {
    checkNotParsed("abc");
  }

  private void checkParsed(String toParse, String expectedVariableName) {
    LessVariableTree tree = (LessVariableTree) parser().parse(toParse);
    assertThat(tree.variable()).isNotNull();
    assertThat(tree.variablePrefix()).isNotNull();
    assertThat(tree.variableName()).isEqualTo(expectedVariableName);
  }

}
