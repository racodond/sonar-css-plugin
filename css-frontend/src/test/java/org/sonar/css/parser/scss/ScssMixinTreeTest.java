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
import org.sonar.plugins.css.api.tree.scss.ScssMixinTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssMixinTreeTest extends ScssTreeTest {

  public ScssMixinTreeTest() {
    super(LexicalGrammar.SCSS_MIXIN);
  }

  @Test
  public void scssMixin() {
    ScssMixinTree tree;

    tree = checkParsed("@mixin hello {}");
    assertThat(tree.parameters()).isNull();
    assertThat(tree.block().allStatements()).isEmpty();
    assertThat(tree.block().propertyDeclarations()).isEmpty();
    assertThat(tree.block().rulesets()).isEmpty();
    assertThat(tree.block().scssMixinIncludes()).isEmpty();

    tree = checkParsed("@mixin hello() {}");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().parameters()).isNull();

    tree = checkParsed("@mixin hello () {}");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().parameters()).isNull();

    tree = checkParsed("@mixin hello ($abc, $def: 10) {}");
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().parameters()).hasSize(2);

    tree = checkParsed("@mixin hello { color: green;}");
    assertThat(tree.block().allStatements()).hasSize(1);
    assertThat(tree.block().propertyDeclarations()).hasSize(1);
    assertThat(tree.block().rulesets()).isEmpty();
    assertThat(tree.block().scssMixinIncludes()).isEmpty();

    tree = checkParsed("@mixin hello { color: green;\n width: 10px}");
    assertThat(tree.block().allStatements()).hasSize(2);
    assertThat(tree.block().propertyDeclarations()).hasSize(2);
    assertThat(tree.block().rulesets()).isEmpty();
    assertThat(tree.block().scssMixinIncludes()).isEmpty();

    tree = checkParsed("@mixin hello { h1 { color:green; }}");
    assertThat(tree.block().allStatements()).hasSize(1);
    assertThat(tree.block().propertyDeclarations()).isEmpty();
    assertThat(tree.block().rulesets()).hasSize(1);
    assertThat(tree.block().scssMixinIncludes()).isEmpty();

    tree = checkParsed("@mixin hello { h1 { color:green; }\n p { color: blue; } }");
    assertThat(tree.block().allStatements()).hasSize(2);
    assertThat(tree.block().propertyDeclarations()).isEmpty();
    assertThat(tree.block().rulesets()).hasSize(2);
    assertThat(tree.block().scssMixinIncludes()).isEmpty();

    tree = checkParsed("@mixin hello { @include abc;}");
    assertThat(tree.block().allStatements()).hasSize(1);
    assertThat(tree.block().propertyDeclarations()).isEmpty();
    assertThat(tree.block().rulesets()).isEmpty();
    assertThat(tree.block().scssMixinIncludes()).hasSize(1);

    tree = checkParsed("@mixin hello { @include abc; @include def(10) }");
    assertThat(tree.block().allStatements()).hasSize(2);
    assertThat(tree.block().propertyDeclarations()).isEmpty();
    assertThat(tree.block().rulesets()).isEmpty();
    assertThat(tree.block().scssMixinIncludes()).hasSize(2);

    tree = checkParsed("@mixin hello { color: green; @include abc; h1 {color: green;} width: 10px; @include def(10); p {color: blue} }");
    assertThat(tree.block().allStatements()).hasSize(6);
    assertThat(tree.block().propertyDeclarations()).hasSize(2);
    assertThat(tree.block().rulesets()).hasSize(2);
    assertThat(tree.block().scssMixinIncludes()).hasSize(2);
  }

  @Test
  public void notScssMixin() {
    checkNotParsed("@mixin");
    checkNotParsed("@mixin()");
    checkNotParsed("@mixin{}");
    checkNotParsed("@mixin(){}");
  }

  private ScssMixinTree checkParsed(String toParse) {
    ScssMixinTree tree = (ScssMixinTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.directive()).isNotNull();
    assertThat(tree.directive().at()).isNotNull();
    assertThat(tree.directive().name().text()).isEqualTo("mixin");
    assertThat(tree.name()).isNotNull();
    assertThat(tree.block()).isNotNull();
    return tree;
  }

}
