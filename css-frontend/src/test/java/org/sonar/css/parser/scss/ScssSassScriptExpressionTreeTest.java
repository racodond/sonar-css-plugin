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
package org.sonar.css.parser.scss;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.FunctionTree;
import org.sonar.plugins.css.api.tree.css.NumberTree;
import org.sonar.plugins.css.api.tree.css.ScssMapTree;
import org.sonar.plugins.css.api.tree.css.ValueTree;
import org.sonar.plugins.css.api.tree.scss.ScssOperatorTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssSassScriptExpressionTreeTest extends ScssTreeTest {

  public ScssSassScriptExpressionTreeTest() {
    super(LexicalGrammar.SCSS_SASS_SCRIPT_EXPRESSION);
  }

  @Test
  public void scssSassScriptExpression() {
    ValueTree tree;

    tree = checkParsed("1+2");
    assertThat(tree.valueElements()).hasSize(3);
    assertThat(tree.valueElements().get(0)).isInstanceOf(NumberTree.class);
    assertThat(tree.valueElements().get(1)).isInstanceOf(ScssOperatorTree.class);
    assertThat(tree.valueElements().get(2)).isInstanceOf(NumberTree.class);

    tree = checkParsed("1 + 2");
    assertThat(tree.valueElements()).hasSize(3);
    assertThat(tree.valueElements().get(0)).isInstanceOf(NumberTree.class);
    assertThat(tree.valueElements().get(1)).isInstanceOf(ScssOperatorTree.class);
    assertThat(tree.valueElements().get(2)).isInstanceOf(NumberTree.class);

    tree = checkParsed("(a: 2, b: 3)");
    assertThat(tree.valueElements()).hasSize(1);
    assertThat(tree.valueElements().get(0)).isInstanceOf(ScssMapTree.class);

    tree = checkParsed("abc((a: 2, b: 3))");
    assertThat(tree.valueElements()).hasSize(1);
    assertThat(tree.valueElements().get(0)).isInstanceOf(FunctionTree.class);
  }

  private ValueTree checkParsed(String toParse) {
    ValueTree tree = (ValueTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    return tree;
  }

}
