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
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.scss.ScssParameterTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssCallParameterTreeTest extends ScssTreeTest {

  public ScssCallParameterTreeTest() {
    super(LexicalGrammar.SCSS_CALL_PARAMETER);
  }

  @Test
  public void scssCallParameter() {
    ScssParameterTree tree;

    tree = checkParsed("$abc: 10");
    assertThat(tree.value()).isNull();
    assertThat(tree.variableDeclaration()).isNotNull();
    assertThat(tree.variableDeclaration().variable().variableName()).isEqualTo("abc");
    assertThat(tree.variableDeclaration().value()).isNotNull();

    tree = checkParsed("10");
    assertThat(tree.value()).isNotNull();
    assertThat(tree.value().valueElements()).hasSize(1);
    assertThat(tree.value().valueElements().get(0).is(Tree.Kind.NUMBER)).isTrue();
    assertThat(tree.value().treeValue()).isEqualTo("10");
    assertThat(tree.variableDeclaration()).isNull();
  }

  private ScssParameterTree checkParsed(String toParse) {
    ScssParameterTree tree = (ScssParameterTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    return tree;
  }

}
