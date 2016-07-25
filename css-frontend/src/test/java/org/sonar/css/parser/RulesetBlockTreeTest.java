/*
 * SonarQube CSS Plugin
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
package org.sonar.css.parser;

import org.junit.Test;
import org.sonar.plugins.css.api.tree.RulesetBlockTree;

import static org.fest.assertions.Assertions.assertThat;

public class RulesetBlockTreeTest extends TreeTest {

  public RulesetBlockTreeTest() {
    super(CssLexicalGrammar.RULESET_BLOCK);
  }

  @Test
  public void rulesetBlock() {
    RulesetBlockTree tree;

    tree = checkParsed("{}");
    assertThat(tree.declarations()).isNull();

    tree = checkParsed(" {}");
    assertThat(tree.declarations()).isNull();

    tree = checkParsed(" { }");
    assertThat(tree.declarations()).isNull();

    tree = checkParsed("{color:green}");
    assertThat(tree.declarations()).isNotNull();
    assertThat(tree.declarations().propertyDeclarations()).isNotNull();
    assertThat(tree.declarations().propertyDeclarations()).hasSize(1);
    assertThat(tree.declarations().variableDeclarations()).isNotNull();
    assertThat(tree.declarations().variableDeclarations()).isEmpty();

    tree = checkParsed(" { color : green }");
    assertThat(tree.declarations()).isNotNull();
    assertThat(tree.declarations().propertyDeclarations()).isNotNull();
    assertThat(tree.declarations().propertyDeclarations()).hasSize(1);
    assertThat(tree.declarations().variableDeclarations()).isNotNull();
    assertThat(tree.declarations().variableDeclarations()).isEmpty();

    tree = checkParsed(" { \ncolor : green\n }");
    assertThat(tree.declarations()).isNotNull();
    assertThat(tree.declarations().propertyDeclarations()).isNotNull();
    assertThat(tree.declarations().propertyDeclarations()).hasSize(1);
    assertThat(tree.declarations().variableDeclarations()).isNotNull();
    assertThat(tree.declarations().variableDeclarations()).isEmpty();

    tree = checkParsed(" { \ncolor : green;\ncolor: red;\n--myvar: blabla }");
    assertThat(tree.declarations()).isNotNull();
    assertThat(tree.declarations().propertyDeclarations()).isNotNull();
    assertThat(tree.declarations().propertyDeclarations()).hasSize(2);
    assertThat(tree.declarations().variableDeclarations()).isNotNull();
    assertThat(tree.declarations().variableDeclarations()).hasSize(1);

    tree = checkParsed(" { \ncolor : green;\ncolor: red;\n--myvar: blabla; }");
    assertThat(tree.declarations()).isNotNull();
    assertThat(tree.declarations().propertyDeclarations()).isNotNull();
    assertThat(tree.declarations().propertyDeclarations()).hasSize(2);
    assertThat(tree.declarations().variableDeclarations()).isNotNull();
    assertThat(tree.declarations().variableDeclarations()).hasSize(1);

    tree = checkParsed(" { \ncolor : green;\ncolor: red;\n--myvar: blabla;}");
    assertThat(tree.declarations()).isNotNull();
    assertThat(tree.declarations().propertyDeclarations()).isNotNull();
    assertThat(tree.declarations().propertyDeclarations()).hasSize(2);
    assertThat(tree.declarations().variableDeclarations()).isNotNull();
    assertThat(tree.declarations().variableDeclarations()).hasSize(1);
  }

  @Test
  public void notRulesetBlock() {
    checkNotParsed("{color}");
    checkNotParsed("{color:}");
  }

  private RulesetBlockTree checkParsed(String toParse) {
    RulesetBlockTree tree = (RulesetBlockTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.openCurlyBrace()).isNotNull();
    assertThat(tree.closeCurlyBrace()).isNotNull();
    return tree;
  }

}
