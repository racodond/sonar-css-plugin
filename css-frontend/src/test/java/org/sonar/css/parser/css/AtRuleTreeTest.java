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
import org.sonar.css.model.Vendor;
import org.sonar.css.model.atrule.UnknownAtRule;
import org.sonar.css.model.atrule.standard.Import;
import org.sonar.css.model.atrule.standard.Page;
import org.sonar.css.parser.TreeTest;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.AtRuleTree;

import static org.fest.assertions.Assertions.assertThat;

public class AtRuleTreeTest extends TreeTest {

  public AtRuleTreeTest() {
    super(LexicalGrammar.AT_RULE);
  }

  @Test
  public void atRule() {
    AtRuleTree tree;

    tree = checkParsed("@import;");
    assertThat(tree.preludes()).isNull();
    assertThat(tree.block()).isNull();
    assertThat(tree.semicolon()).isNotNull();
    assertThat(tree.standardAtRule().getName()).isEqualTo("import");
    assertThat(tree.standardAtRule()).isInstanceOf(Import.class);
    assertThat(tree.isVendorPrefixed()).isFalse();

    tree = checkParsed(" @import;");
    assertThat(tree.preludes()).isNull();
    assertThat(tree.block()).isNull();
    assertThat(tree.semicolon()).isNotNull();
    assertThat(tree.standardAtRule().getName()).isEqualTo("import");
    assertThat(tree.standardAtRule()).isInstanceOf(Import.class);
    assertThat(tree.isVendorPrefixed()).isFalse();

    tree = checkParsed("@IMPORT;");
    assertThat(tree.preludes()).isNull();
    assertThat(tree.block()).isNull();
    assertThat(tree.semicolon()).isNotNull();
    assertThat(tree.standardAtRule().getName()).isEqualTo("import");
    assertThat(tree.standardAtRule()).isInstanceOf(Import.class);
    assertThat(tree.isVendorPrefixed()).isFalse();

    tree = checkParsed("@-ms-IMPORT;");
    assertThat(tree.preludes()).isNull();
    assertThat(tree.block()).isNull();
    assertThat(tree.semicolon()).isNotNull();
    assertThat(tree.standardAtRule().getName()).isEqualTo("import");
    assertThat(tree.standardAtRule()).isInstanceOf(Import.class);
    assertThat(tree.isVendorPrefixed()).isTrue();
    assertThat(tree.vendor()).isEqualTo(Vendor.MICROSOFT);

    tree = checkParsed("@test;");
    assertThat(tree.preludes()).isNull();
    assertThat(tree.block()).isNull();
    assertThat(tree.semicolon()).isNotNull();
    assertThat(tree.standardAtRule().getName()).isEqualTo("test");
    assertThat(tree.standardAtRule()).isInstanceOf(UnknownAtRule.class);

    tree = checkParsed("@import url(\"fineprint.css\") print;");
    assertThat(tree.preludes()).isNotNull();
    assertThat(tree.block()).isNull();
    assertThat(tree.semicolon()).isNotNull();
    assertThat(tree.preludes()).hasSize(2);

    tree = checkParsed("@charset \"UTF-8\";");
    assertThat(tree.preludes()).isNotNull();
    assertThat(tree.block()).isNull();
    assertThat(tree.semicolon()).isNotNull();
    assertThat(tree.preludes()).hasSize(1);

    tree = checkParsed("@media screen, print {\n" +
      "  body { line-height: 1.2 }\n" +
      "}");
    assertThat(tree.preludes()).isNotNull();
    assertThat(tree.block()).isNotNull();
    assertThat(tree.semicolon()).isNull();
    assertThat(tree.preludes()).hasSize(3);

    tree = checkParsed("@supports (--foo: green) {\n" +
      "  body {\n" +
      "    color: green;\n" +
      "  }\n" +
      "}");
    assertThat(tree.preludes()).isNotNull();
    assertThat(tree.block()).isNotNull();
    assertThat(tree.preludes()).hasSize(1);

    checkParsed("@document url(http://www.w3.org/),\n" +
      "               url-prefix(http://www.w3.org/Style/),\n" +
      "               domain(mozilla.org),\n" +
      "               regexp(\"https:.*\")\n" +
      "{\n" +
      "  /* CSS rules here apply to:\n" +
      "     - The page \"http://www.w3.org/\".\n" +
      "     - Any page whose URL begins with \"http://www.w3.org/Style/\"\n" +
      "     - Any page whose URL's host is \"mozilla.org\" or ends with\n" +
      "       \".mozilla.org\"\n" +
      "     - Any page whose URL starts with \"https:\" */\n" +
      "\n" +
      "  /* make the above-mentioned pages really ugly */\n" +
      "  body {\n" +
      "    color: purple;\n" +
      "    background: yellow;\n" +
      "  }\n" +
      "}");

    checkParsed("@font-face {"
      + "font-family: 'MyFontFamily';"
      + "src: url('myfont-webfont.eot') format('embedded-opentype'),"
      + "     url('myfont-webfont.woff') format('woff'), "
      + "     url('myfont-webfont.ttf')  format('truetype'),"
      + "     url('myfont-webfont.svg#svgFontName') format('svg');"
      + "}");

    checkParsed("@media screen and -webkit-min-device-pixel-ratio 0{"
      + ".form select {"
      + "color: red;"
      + "}}");

    checkParsed("@media screen and -webkit-min-device-pixel-ratio 0{"
      + "@font-face {"
      + "font-family:Icons;"
      + "src:url(fonts/Icons.svg#Icons) format(svg);"
      + "}"
      + "\n"
      + ".t {"
      + "-webkit-transform:translateZ(0);"
      + "-moz-transform:translateZ(0);"
      + "-o-transform:translateZ(0);"
      + "transform:translateZ(0);"
      + "}"
      + "}");
    checkParsed("@font-face {;}");
    checkParsed("@font-face {;;}");
    checkParsed("@font-face {;font-family:Icons;}");
    checkParsed("@font-face {}");

    tree = checkParsed("@page :first {}");
    assertThat(tree.preludes()).isNotNull();
    assertThat(tree.block()).isNotNull();
    assertThat(tree.semicolon()).isNull();
    assertThat(tree.standardAtRule().getName()).isEqualTo("page");
    assertThat(tree.standardAtRule()).isInstanceOf(Page.class);
    assertThat(tree.isVendorPrefixed()).isFalse();

    checkParsed("@page :blank {\n"
      + "@top-center { content: \"This page is intentionally left blank\" }\n"
      + "}");

    checkParsed("@import \"@{themes}/style.less\";");
  }

  @Test
  public void notAtRule() {
    checkNotParsed("{color}");
    checkNotParsed("{color:}");
    checkNotParsed("@");
  }

  private AtRuleTree checkParsed(String toParse) {
    AtRuleTree tree = (AtRuleTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.atKeyword()).isNotNull();
    assertThat(tree.standardAtRule()).isNotNull();
    return tree;
  }

}
