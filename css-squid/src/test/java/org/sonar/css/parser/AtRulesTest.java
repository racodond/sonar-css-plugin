/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende and David RACODON
 * kende.tamas@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.css.parser;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class AtRulesTest extends TestBase {

  private final LexerlessGrammar b = CssGrammar.createGrammar();

  @Test
  public void atRuleTest() {
    assertThat(b.rule(CssGrammar.AT_RULE))
      .matches("@import \"subs.css\";")
      .matches("@import \"print-main.css\" print;")
      .matches(
        code("@media print {"
          + "      body { font-size: 10pt }"
          + "    }"))
      .matches(
        code(
          "@font-face {",
          "font-family: 'MyFontFamily';",
          "src: url('myfont-webfont.eot') format('embedded-opentype'),",
          "    url('myfont-webfont.woff') format('woff'), ",
          "    url('myfont-webfont.ttf')  format('truetype'),",
          "    url('myfont-webfont.svg#svgFontName') format('svg');",
          "}"))
      .matches(code(
        "@media screen and -webkit-min-device-pixel-ratio 0{",
        ".form select {",
        "color: red;",
        "}}"))
      .matches(code(
        "@media screen and -webkit-min-device-pixel-ratio 0{",
        "@font-face {",
        "font-family:Icons;",
        "src:url(fonts/Icons.svg#Icons) format(svg);",
        "}",
        "",
        ".t {",
        "-webkit-transform:translateZ(0);",
        "-moz-transform:translateZ(0);",
        "-o-transform:translateZ(0);",
        "transform:translateZ(0);",
        "}",
        "}"))
      .matches("@font-face {;}")
      .matches("@font-face {;;}")
      .matches("@font-face {;font-family:Icons;}")
      .matches("@font-face {}");
  }

}
