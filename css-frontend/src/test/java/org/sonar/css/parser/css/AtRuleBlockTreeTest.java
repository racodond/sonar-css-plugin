/*
 * SonarQube CSS / Less Plugin
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
import org.sonar.css.parser.TreeTest;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.AtRuleBlockTree;

import static org.fest.assertions.Assertions.assertThat;

public class AtRuleBlockTreeTest extends TreeTest {

  public AtRuleBlockTreeTest() {
    super(LexicalGrammar.AT_RULE_BLOCK);
  }

  @Test
  public void atRuleBlock() {
    AtRuleBlockTree tree;

    tree = checkParsed("{}");
    assertThat(tree.propertyDeclarations()).hasSize(0);
    assertThat(tree.variableDeclarations()).hasSize(0);
    assertThat(tree.rulesets()).hasSize(0);
    assertThat(tree.atRules()).hasSize(0);

    tree = checkParsed(" {}");
    assertThat(tree.propertyDeclarations()).hasSize(0);
    assertThat(tree.variableDeclarations()).hasSize(0);
    assertThat(tree.rulesets()).hasSize(0);
    assertThat(tree.atRules()).hasSize(0);

    tree = checkParsed(" { }");
    assertThat(tree.propertyDeclarations()).hasSize(0);
    assertThat(tree.variableDeclarations()).hasSize(0);
    assertThat(tree.rulesets()).hasSize(0);
    assertThat(tree.atRules()).hasSize(0);

    tree = checkParsed("{color:green}");
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).hasSize(0);
    assertThat(tree.rulesets()).hasSize(0);
    assertThat(tree.atRules()).hasSize(0);

    tree = checkParsed(" { color : green; }");
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).hasSize(0);
    assertThat(tree.rulesets()).hasSize(0);
    assertThat(tree.atRules()).hasSize(0);

    tree = checkParsed(" { color : green; --var: var; }");
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).hasSize(1);
    assertThat(tree.rulesets()).hasSize(0);
    assertThat(tree.atRules()).hasSize(0);

    tree = checkParsed(" { h1, h1 {color : green;} }");
    assertThat(tree.propertyDeclarations()).hasSize(0);
    assertThat(tree.variableDeclarations()).hasSize(0);
    assertThat(tree.rulesets()).hasSize(1);
    assertThat(tree.atRules()).hasSize(0);

    tree = checkParsed(" { @import; }");
    assertThat(tree.propertyDeclarations()).hasSize(0);
    assertThat(tree.variableDeclarations()).hasSize(0);
    assertThat(tree.rulesets()).hasSize(0);
    assertThat(tree.atRules()).hasSize(1);

    tree = checkParsed(" { @import url(\"fineprint.css\") print; }");
    assertThat(tree.propertyDeclarations()).hasSize(0);
    assertThat(tree.variableDeclarations()).hasSize(0);
    assertThat(tree.rulesets()).hasSize(0);
    assertThat(tree.atRules()).hasSize(1);

    tree = checkParsed("{"
      + "font-family: 'MyFontFamily';"
      + "src: url('myfont-webfont.eot') format('embedded-opentype'),"
      + "     url('myfont-webfont.woff') format('woff'), "
      + "     url('myfont-webfont.ttf')  format('truetype'),"
      + "     url('myfont-webfont.svg#svgFontName') format('svg');"
      + "}");
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.variableDeclarations()).hasSize(0);
    assertThat(tree.rulesets()).hasSize(0);
    assertThat(tree.atRules()).hasSize(0);

    tree = checkParsed("{ @styleset { nice-style: 4; } @styleset { nice-style: 4; } }");
    assertThat(tree.propertyDeclarations()).hasSize(0);
    assertThat(tree.variableDeclarations()).hasSize(0);
    assertThat(tree.rulesets()).hasSize(0);
    assertThat(tree.atRules()).hasSize(2);

    tree = checkParsed(" { h1, h1 {color : green;} h3 {} }");
    assertThat(tree.propertyDeclarations()).hasSize(0);
    assertThat(tree.variableDeclarations()).hasSize(0);
    assertThat(tree.rulesets()).hasSize(2);
    assertThat(tree.atRules()).hasSize(0);

    tree = checkParsed("{\n" +
      "  margin: 10%;\n" +
      "  --myvar: 10%;\n" +
      "\n" +
      "  @top-left {\n" +
      "    content: \"Hamlet\";\n" +
      "  }\n" +
      "  @top-right {\n" +
      "    content: \"Page \" counter(page);\n" +
      "  }\n" +
      "  size: 8.5in 11in;\n" +
      "h1 {color : green;}" +
      "}");
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.variableDeclarations()).hasSize(1);
    assertThat(tree.rulesets()).hasSize(1);
    assertThat(tree.atRules()).hasSize(2);
  }

  @Test
  public void notAtRuleBlock() {
    checkNotParsed("{color}");
    checkNotParsed("{color:}");
  }

  private AtRuleBlockTree checkParsed(String toParse) {
    AtRuleBlockTree tree = (AtRuleBlockTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.openCurlyBrace()).isNotNull();
    assertThat(tree.closeCurlyBrace()).isNotNull();
    assertThat(tree.propertyDeclarations()).isNotNull();
    assertThat(tree.variableDeclarations()).isNotNull();
    assertThat(tree.rulesets()).isNotNull();
    assertThat(tree.atRules()).isNotNull();
    return tree;
  }

}
