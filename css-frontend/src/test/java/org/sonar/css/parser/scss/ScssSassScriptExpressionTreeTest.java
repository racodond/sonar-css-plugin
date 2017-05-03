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
import org.sonar.plugins.css.api.tree.css.ValueTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssSassScriptExpressionTreeTest extends ScssTreeTest {

  public ScssSassScriptExpressionTreeTest() {
    super(LexicalGrammar.SCSS_SASS_SCRIPT_EXPRESSION);
  }

  @Test
  public void scssSassScriptExpression() {
    checkParsed("1+2");
    checkParsed("1 + 2");
    checkParsed("(a: 2, b: 3)");
    checkParsed("abc((a: 2, b: 3))");
  }

  private ValueTree checkParsed(String toParse) {
    ValueTree tree = (ValueTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    return tree;
  }

}
