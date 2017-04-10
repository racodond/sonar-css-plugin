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
import org.sonar.plugins.css.api.tree.css.VariableTree;

import static org.fest.assertions.Assertions.assertThat;

public class VariableTreeTest extends CssTreeTest {

  public VariableTreeTest() {
    super(LexicalGrammar.VARIABLE);
  }

  @Test
  public void variable() {
    checkParsed("--abc", "abc");
    checkParsed(" --abc", "abc");
    checkParsed("--abc-def", "abc-def");
    checkParsed("--abc--def", "abc--def");
    checkParsed("--ABC", "ABC");
  }

  @Test
  public void notVariable() {
    checkNotParsed("abc");
    checkNotParsed("-abc");
  }

  private void checkParsed(String toParse, String expectedVariableName) {
    VariableTree tree = (VariableTree) parser().parse(toParse);
    assertThat(tree.variable()).isNotNull();
    assertThat(tree.variablePrefix()).isNotNull();
    assertThat(tree.variableName()).isEqualTo(expectedVariableName);
  }

}
