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
import org.sonar.plugins.css.api.tree.scss.ScssMixinIncludeTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssMixinIncludeTreeTest extends ScssTreeTest {

  public ScssMixinIncludeTreeTest() {
    super(LexicalGrammar.SCSS_MIXIN_INCLUDE);
  }

  @Test
  public void scssMixinInclude() {
    ScssMixinIncludeTree tree;

    tree = checkParsed("@include hello");
    assertThat(tree.parameters()).isNull();
    assertThat(tree.semicolon()).isNull();
    assertThat(tree.block()).isNull();

    tree = checkParsed("@include hello;");
    assertThat(tree.parameters()).isNull();
    assertThat(tree.semicolon()).isNotNull();
    assertThat(tree.block()).isNull();

    tree = checkParsed("@include hello();");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().parameters()).isNull();
    assertThat(tree.semicolon()).isNotNull();
    assertThat(tree.block()).isNull();

    tree = checkParsed("@include hello () ;");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().parameters()).isNull();
    assertThat(tree.semicolon()).isNotNull();
    assertThat(tree.block()).isNull();

    tree = checkParsed("@include hello (10) ;");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().parameters()).hasSize(1);
    assertThat(tree.parameters().parameters().get(0).value()).isNotNull();
    assertThat(tree.block()).isNull();

    tree = checkParsed("@include hello($abc: 10)");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().parameters()).hasSize(1);
    assertThat(tree.parameters().parameters().get(0).variableDeclaration()).isNotNull();
    assertThat(tree.block()).isNull();

    tree = checkParsed("@include hello($abc: 10, $def: \"string\")");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().parameters()).hasSize(2);
    assertThat(tree.parameters().parameters().get(0).variableDeclaration()).isNotNull();
    assertThat(tree.parameters().parameters().get(1).variableDeclaration()).isNotNull();
    assertThat(tree.block()).isNull();

    tree = checkParsed("@include hello($abc: 10, $def: 2 * 10)");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().parameters()).hasSize(2);
    assertThat(tree.parameters().parameters().get(0).variableDeclaration()).isNotNull();
    assertThat(tree.parameters().parameters().get(1).variableDeclaration()).isNotNull();
    assertThat(tree.block()).isNull();

    tree = checkParsed(" @include hello( $abc )");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().parameters()).hasSize(1);
    assertThat(tree.parameters().parameters().get(0).value()).isNotNull();
    assertThat(tree.block()).isNull();

    tree = checkParsed(" @include hello( $abc, $def )");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().parameters()).hasSize(2);
    assertThat(tree.parameters().parameters().get(0).value()).isNotNull();
    assertThat(tree.parameters().parameters().get(1).value()).isNotNull();
    assertThat(tree.block()).isNull();

    tree = checkParsed(" @include hello( $abc, $def : 10)");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().parameters()).hasSize(2);
    assertThat(tree.parameters().parameters().get(0).value()).isNotNull();
    assertThat(tree.parameters().parameters().get(1).variableDeclaration()).isNotNull();
    assertThat(tree.block()).isNull();

    tree = checkParsed(" @include hello( $abc, $def : 10) { color: green; h1 { color: green; }}");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().parameters()).hasSize(2);
    assertThat(tree.parameters().parameters().get(0).value()).isNotNull();
    assertThat(tree.parameters().parameters().get(1).variableDeclaration()).isNotNull();
    assertThat(tree.block()).isNotNull();
    assertThat(tree.block().propertyDeclarations()).hasSize(1);
    assertThat(tree.block().rulesets()).hasSize(1);

    tree = checkParsed("@include scut-ratio-box(16/9);");
    assertThat(tree.parameters().parameters()).hasSize(1);
  }

  @Test
  public void notScssMixinInclude() {
    checkNotParsed("@mixin");
    checkNotParsed("@mixin()");
    checkNotParsed("@mixin;");
    checkNotParsed("@mixin(10);");
  }

  private ScssMixinIncludeTree checkParsed(String toParse) {
    ScssMixinIncludeTree tree = (ScssMixinIncludeTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.directive()).isNotNull();
    assertThat(tree.name()).isNotNull();
    return tree;
  }

}
