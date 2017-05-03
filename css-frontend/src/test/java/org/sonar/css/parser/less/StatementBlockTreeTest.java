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
package org.sonar.css.parser.less;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.StatementBlockTree;

import static org.fest.assertions.Assertions.assertThat;

public class StatementBlockTreeTest extends LessTreeTest {

  public StatementBlockTreeTest() {
    super(LexicalGrammar.STATEMENT_BLOCK);
  }

  @Test
  public void statementBlock() {
    StatementBlockTree tree;

    tree = checkParsed("{}");
    assertThat(tree.content()).isEmpty();
    assertThat(tree.propertyDeclarations()).isEmpty();
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.allDeclarations()).isEmpty();
    assertThat(tree.allStatements()).isEmpty();

    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();

    tree = checkParsed(" {}");
    assertThat(tree.content()).isEmpty();
    assertThat(tree.propertyDeclarations()).isEmpty();
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.allDeclarations()).isEmpty();
    assertThat(tree.allStatements()).isEmpty();

    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();

    tree = checkParsed(" { }");
    assertThat(tree.content()).isEmpty();
    assertThat(tree.propertyDeclarations()).isEmpty();
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.allDeclarations()).isEmpty();
    assertThat(tree.allStatements()).isEmpty();

    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();

    tree = checkParsed("{color:green}");
    assertThat(tree.content()).hasSize(1);
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.allDeclarations()).hasSize(1);
    assertThat(tree.allStatements()).hasSize(1);

    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();

    tree = checkParsed(" { color : green; }");
    assertThat(tree.content()).hasSize(1);
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.allDeclarations()).hasSize(1);
    assertThat(tree.allStatements()).hasSize(1);

    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();

    tree = checkParsed(" { color : green; --var: var; }");
    assertThat(tree.content()).hasSize(2);
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).hasSize(1);
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.allDeclarations()).hasSize(2);
    assertThat(tree.allStatements()).hasSize(2);

    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();

    tree = checkParsed(" { h1, h1 {color : green;} }");
    assertThat(tree.content()).hasSize(1);
    assertThat(tree.propertyDeclarations()).isEmpty();
    assertThat(tree.rulesets()).hasSize(1);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.allDeclarations()).isEmpty();
    assertThat(tree.allStatements()).hasSize(1);

    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();

    tree = checkParsed(" { @import; }");
    assertThat(tree.content()).hasSize(1);
    assertThat(tree.atRules()).hasSize(1);
    assertThat(tree.propertyDeclarations()).isEmpty();
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.allDeclarations()).isEmpty();
    assertThat(tree.allStatements()).hasSize(1);

    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();

    tree = checkParsed(" { @import url(\"fineprint.css\") print; }");
    assertThat(tree.content()).hasSize(1);
    assertThat(tree.atRules()).hasSize(1);
    assertThat(tree.propertyDeclarations()).isEmpty();
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.allDeclarations()).isEmpty();
    assertThat(tree.allStatements()).hasSize(1);

    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();

    tree = checkParsed("{"
      + "font-family: 'MyFontFamily';"
      + "src: url('myfont-webfont.eot') format('embedded-opentype'),"
      + "     url('myfont-webfont.woff') format('woff'), "
      + "     url('myfont-webfont.ttf')  format('truetype'),"
      + "     url('myfont-webfont.svg#svgFontName') format('svg');"
      + "}");
    assertThat(tree.content()).hasSize(2);
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.allDeclarations()).hasSize(2);
    assertThat(tree.allStatements()).hasSize(2);

    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();

    tree = checkParsed("{ @styleset { nice-style: 4; } @styleset { nice-style: 4; } }");
    assertThat(tree.content()).hasSize(2);
    assertThat(tree.atRules()).hasSize(2);
    assertThat(tree.propertyDeclarations()).isEmpty();
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.allDeclarations()).isEmpty();
    assertThat(tree.allStatements()).hasSize(2);

    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();

    tree = checkParsed(" { h1, h1 {color : green;} h3 {} }");
    assertThat(tree.content()).hasSize(2);
    assertThat(tree.rulesets()).hasSize(2);
    assertThat(tree.propertyDeclarations()).isEmpty();
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.allDeclarations()).isEmpty();
    assertThat(tree.allStatements()).hasSize(2);

    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();

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
    assertThat(tree.content()).hasSize(6);
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.variableDeclarations()).hasSize(1);
    assertThat(tree.rulesets()).hasSize(1);
    assertThat(tree.atRules()).hasSize(2);
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.allDeclarations()).hasSize(3);
    assertThat(tree.allStatements()).hasSize(6);

    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();

    tree = checkParsed(" { \ncolor : green;\ncolor: red;\n@myvar: blabla;}");
    assertThat(tree.content()).hasSize(3);
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.allDeclarations()).hasSize(3);
    assertThat(tree.allStatements()).hasSize(3);

    assertThat(tree.lessVariableDeclarations()).hasSize(1);
    assertThat(tree.lessMixinCalls()).isEmpty();

    tree = checkParsed(" { \ncolor : green;\nh1 {}\ncolor: red;\nh1 {}\n@myvar: blabla;}");
    assertThat(tree.content()).hasSize(5);
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.rulesets()).hasSize(2);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.allDeclarations()).hasSize(3);
    assertThat(tree.allStatements()).hasSize(5);

    assertThat(tree.lessVariableDeclarations()).hasSize(1);
    assertThat(tree.lessMixinCalls()).isEmpty();

    tree = checkParsed(" { \ncolor : green;\n#id();\n.class;\nh1 {}\ncolor: red;\nh1 {}\n@myvar: blabla;}");
    assertThat(tree.content()).hasSize(7);
    assertThat(tree.propertyDeclarations()).hasSize(2);
    assertThat(tree.rulesets()).hasSize(2);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.allDeclarations()).hasSize(3);
    assertThat(tree.allStatements()).hasSize(7);

    assertThat(tree.lessVariableDeclarations()).hasSize(1);
    assertThat(tree.lessMixinCalls()).hasSize(2);

    tree = checkParsed("{" +
      "                 @var: blabla;" +
      "                 color:green;" +
      "                 @media only screen and (max-device-width: 480px) {" +
      "                   select.input {\n" +
      "                     padding-left: 10px;\n" +
      "                   }" +
      "                 }" +
      "               }");
    assertThat(tree.content()).hasSize(3);
    assertThat(tree.propertyDeclarations()).hasSize(1);
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.atRules()).hasSize(1);
    assertThat(tree.allDeclarations()).hasSize(2);
    assertThat(tree.allStatements()).hasSize(3);

    assertThat(tree.lessVariableDeclarations()).hasSize(1);
    assertThat(tree.lessMixinCalls()).isEmpty();

    tree = checkParsed("{" +
      "                 @media only screen and (max-device-width: 480px) {" +
      "                   select.input {\n" +
      "                     padding-left: 10px;\n" +
      "                   }" +
      "                 }" +
      "               }");
    assertThat(tree.content()).hasSize(1);
    assertThat(tree.propertyDeclarations()).isEmpty();
    assertThat(tree.variableDeclarations()).isEmpty();
    assertThat(tree.emptyStatements()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.atRules()).hasSize(1);
    assertThat(tree.allDeclarations()).isEmpty();
    assertThat(tree.allStatements()).hasSize(1);

    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();
  }

  @Test
  public void notStatementBlock() {
    checkNotParsed("{color:}");
  }

  private StatementBlockTree checkParsed(String toParse) {
    StatementBlockTree tree = (StatementBlockTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.openCurlyBrace()).isNotNull();
    assertThat(tree.closeCurlyBrace()).isNotNull();
    assertThat(tree.propertyDeclarations()).isNotNull();
    assertThat(tree.variableDeclarations()).isNotNull();
    assertThat(tree.rulesets()).isNotNull();
    assertThat(tree.emptyStatements()).isNotNull();
    assertThat(tree.allDeclarations()).isNotNull();

    assertThat(tree.lessVariableDeclarations()).isNotNull();
    assertThat(tree.lessMixinCalls()).isNotNull();

    assertThat(tree.scssVariableDeclarations()).isEmpty();
    assertThat(tree.scssMixinDefinitions()).isEmpty();
    assertThat(tree.scssMixinIncludes()).isEmpty();
    assertThat(tree.scssExtends()).isEmpty();
    assertThat(tree.scssAtRoots()).isEmpty();

    return tree;
  }

}
