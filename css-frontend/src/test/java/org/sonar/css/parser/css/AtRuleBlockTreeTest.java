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
package org.sonar.css.parser.css;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.StatementBlockTree;

import static org.fest.assertions.Assertions.assertThat;

public class AtRuleBlockTreeTest extends CssTreeTest {

  public AtRuleBlockTreeTest() {
    super(LexicalGrammar.AT_RULE_BLOCK);
  }

  @Test
  public void atRuleBlock() {
    StatementBlockTree tree;

    tree = checkParsed("{}");
    assertThat(tree.content()).isNull();

    tree = checkParsed(" {}");
    assertThat(tree.content()).isNull();

    tree = checkParsed(" { }");
    assertThat(tree.content()).isNull();

    tree = checkParsed("{color:green}");
    assertThat(tree.content()).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.rulesets()).isEmpty();

    tree = checkParsed(" { color : green }");
    assertThat(tree.content()).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();

    tree = checkParsed(" { \ncolor : green\n }");
    assertThat(tree.content()).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();

    tree = checkParsed(" { \ncolor : green;\ncolor: red;\n--myvar: blabla }");
    assertThat(tree.content()).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.variableDeclarations()).hasSize(1);
    assertThat(tree.rulesets()).isEmpty();

    tree = checkParsed(" { \ncolor : green;\ncolor: red;\n--myvar: blabla; }");
    assertThat(tree.content()).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.variableDeclarations()).hasSize(1);
    assertThat(tree.rulesets()).isEmpty();

    tree = checkParsed(" { \ncolor : green;\ncolor: red;\n--myvar: blabla;}");
    assertThat(tree.content()).isNotNull();
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.variableDeclarations()).hasSize(1);
    assertThat(tree.rulesets()).isEmpty();

    tree = checkParsed(" { @document{}\nh1 { color: green; } }");
    assertThat(tree.content()).isNotNull();
    assertThat(tree.propertyDeclarations()).isEmpty();
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.rulesets()).hasSize(1);
    assertThat(tree.atRules()).hasSize(1);
  }

  @Test
  public void notAtRuleBlock() {
    checkNotParsed("{color:}");
  }

  private StatementBlockTree checkParsed(String toParse) {
    StatementBlockTree tree = (StatementBlockTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.openCurlyBrace()).isNotNull();
    assertThat(tree.closeCurlyBrace()).isNotNull();
    assertThat(tree.variableDeclarations()).isNotNull();
    assertThat(tree.rulesets()).isNotNull();
    assertThat(tree.atRules()).isNotNull();

    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();

    assertThat(tree.scssVariableDeclarations()).isEmpty();
    assertThat(tree.scssMixinDefinitions()).isEmpty();
    assertThat(tree.scssMixinIncludes()).isEmpty();
    assertThat(tree.scssExtends()).isEmpty();
    assertThat(tree.scssAtRoots()).isEmpty();

    return tree;
  }

}
