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

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.StyleSheetTree;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

public class StyleSheetTreeTest extends LessTreeTest {

  public StyleSheetTreeTest() {
    super(LexicalGrammar.STYLESHEET);
  }

  @Test
  public void stylesheet() throws Exception {
    StyleSheetTree tree;

    checkParsed("");
    checkParsed(" ");
    checkParsed("  ");
    checkParsed(" /* blabla */ ");
    checkParsed(" /* blabla\nblabla */ ");
    checkParsed(" <!-- blabla --> ");
    checkParsed(" <!-- blabla\nblabla --> ");
    checkParsed(" @var: blue; // blabla ");
    checkParsed(" // blabla ");
    checkParsed("\ufeff");
    checkParsed("\ufeff ");
    checkParsed("\ufeff   ");

    tree = checkParsed(new File("src/test/resources/normalize.css"));
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.rulesets()).hasSize(38);
    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();
    assertThat(tree.all()).hasSize(38);

    tree = checkParsed(new File("src/test/resources/animate.css"));
    assertThat(tree.atRules()).hasSize(221);
    assertThat(tree.rulesets()).hasSize(60);
    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();
    assertThat(tree.all()).hasSize(281);

    checkParsed(new File("src/test/resources/animate.min.css"));
    checkParsed(new File("src/test/resources/demo.autoprefixed.css"));
    checkParsed(new File("src/test/resources/effeckt.autoprefixed.css"));
    checkParsed(new File("src/test/resources/form-elements.css"));
    checkParsed(new File("src/test/resources/starting-with-bom.css"));

    tree = checkParsed(new File("src/test/resources/less/stylesheet.less"));
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.rulesets()).hasSize(2);
    assertThat(tree.lessVariableDeclarations()).hasSize(2);
    assertThat(tree.lessMixinCalls()).isEmpty();
    assertThat(tree.all()).hasSize(4);

    tree = checkParsed(";");
    assertThat(tree.emptyStatements()).hasSize(1);
    assertThat(tree.all()).hasSize(1);

    tree = checkParsed(" ;");
    assertThat(tree.emptyStatements()).hasSize(1);
    assertThat(tree.all()).hasSize(1);

    tree = checkParsed(" ; ");
    assertThat(tree.emptyStatements()).hasSize(1);
    assertThat(tree.all()).hasSize(1);

    tree = checkParsed(" ;;");
    assertThat(tree.emptyStatements()).hasSize(2);
    assertThat(tree.all()).hasSize(2);

    checkParsed(".mixin {" +
      "            @media only screen and (max-device-width: 480px) {" +
      "              select.input {\n" +
      "                padding-left: 10px;\n" +
      "              }" +
      "            }" +
      "          }");

    tree = checkParsed(new File("src/test/resources/less/stylesheet.less"));
    assertThat(tree.atRules()).isEmpty();

    tree = checkParsed(".mixin();");
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).hasSize(1);
    assertThat(tree.all()).hasSize(1);

    tree = checkParsed(".mixin();");
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).hasSize(1);
    assertThat(tree.all()).hasSize(1);

    tree = checkParsed(".mixin();\n.mixin2();");
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).hasSize(2);
    assertThat(tree.all()).hasSize(2);

    tree = checkParsed(".mixin()");
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).hasSize(1);
    assertThat(tree.all()).hasSize(1);

    tree = checkParsed(".mixin");
    assertThat(tree.atRules()).isEmpty();
    assertThat(tree.rulesets()).isEmpty();
    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).hasSize(1);
    assertThat(tree.all()).hasSize(1);

    checkParsed(".nav-top__icons { display: inline-block; margin-left: 10px; &-item { cursor: pointer; display: inline-block; padding: 10px 9px; } }");

    checkParsed(".background-opacity(@c, @alpha) when (iscolor(@c)) and not (@c=~\"transparent\") {\n" +
      "background: hsla(hue(@c), saturation(@c), lightness(@c), @alpha);\n" +
      "}");

    checkParsed(".sprites(@sprites, @i: 1) when (@i <= length(@sprites)) {\n" +
      "@sprite: extract(@sprites, @i);\n" +
      "@sprite-name: e(extract(@sprite, 10));\n" +
      ".@{sprite-name} {\n" +
      ".sprite(@sprite);\n" +
      "}\n" +
      ".sprites(@sprites, @i + 1);\n" +
      "}");
  }

  private StyleSheetTree checkParsed(String toParse) {
    return (StyleSheetTree) parser().parse(toParse);
  }

  private StyleSheetTree checkParsed(File file) throws Exception {
    StyleSheetTree tree = (StyleSheetTree) parser().parse(Files.toString(file, Charsets.UTF_8));
    assertThat(tree.all()).isNotNull();
    assertThat(tree.atRules()).isNotNull();
    assertThat(tree.rulesets()).isNotNull();
    assertThat(tree.lessVariableDeclarations()).isNotNull();
    assertThat(tree.lessMixinCalls()).isNotNull();

    assertThat(tree.scssVariableDeclarations()).isEmpty();
    assertThat(tree.scssMixinDefinitions()).isEmpty();
    assertThat(tree.scssMixinIncludes()).isEmpty();
    assertThat(tree.scssAtRoots()).isEmpty();

    return tree;
  }

}
