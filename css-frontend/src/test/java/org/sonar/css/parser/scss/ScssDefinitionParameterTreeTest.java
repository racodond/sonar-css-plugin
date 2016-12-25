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

public class ScssDefinitionParameterTreeTest extends ScssTreeTest {

  public ScssDefinitionParameterTreeTest() {
    super(LexicalGrammar.SCSS_DEFINITION_PARAMETER);
  }

  @Test
  public void scssDefinitionParameter() {
    ScssParameterTree tree;

    tree = checkParsed("$abc");
    assertThat(tree.variable()).isNotNull();
    assertThat(tree.variable().variableName()).isEqualTo("abc");
    assertThat(tree.variableDeclaration()).isNull();

    tree = checkParsed("$abc: 10");
    assertThat(tree.variable()).isNull();
    assertThat(tree.variableDeclaration()).isNotNull();
    assertThat(tree.variableDeclaration().variable()).isNotNull();
    assertThat(tree.variableDeclaration().variable().variableName()).isEqualTo("abc");
    assertThat(tree.variableDeclaration().value()).isNotNull();
    assertThat(tree.variableDeclaration().value().valueElements()).hasSize(1);
    assertThat(tree.variableDeclaration().value().valueElements().get(0).is(Tree.Kind.NUMBER)).isTrue();
    assertThat(tree.variableDeclaration().value().treeValue()).isEqualTo("10");

    tree = checkParsed("$abc...");
    assertThat(tree.value()).isNull();
    assertThat(tree.variableArgument()).isNotNull();
    assertThat(tree.variableArgument().variableName()).isEqualTo("abc");
  }

  @Test
  public void notScssMixinDefinitionParameter() {
    checkNotParsed("10");
  }

  private ScssParameterTree checkParsed(String toParse) {
    ScssParameterTree tree = (ScssParameterTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    return tree;
  }

}
