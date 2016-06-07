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
package org.sonar.css.parser.sass;

import org.junit.Test;
import org.sonar.css.parser.CssGrammar;
import org.sonar.css.parser.SassGrammar;
import org.sonar.css.parser.TestBase;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class LowLevelTest extends TestBase {

  private LexerlessGrammar b = SassGrammar.createGrammar();

  @Test
  public void variable() {
    assertThat(b.rule(SassGrammar.VARIABLE))
        .matches("$width");
  }

  @Test
  public void varDeclaration() {
    assertThat(b.rule(SassGrammar.VAR_DECLARATION))
        .matches("$width:5em;")
      .matches("$width : 5em ; ")
      .matches("$content: \"Second content?\" !default;")
        .matches("$translucent-red: rgba(255, 0, 0, 0.5);");
  }

  @Test
  public void varUsage() {
    assertThat(b.rule(CssGrammar.DECLARATION))
        .matches("width: $width/2");
  }

  @Test
  public void parentSelector() {
    assertThat(b.rule(SassGrammar.PARENT_SELECTOR))
        .matches("&")
        .matches("&:hover");

    assertThat(b.rule(CssGrammar.RULESET))
        .matches(code(
            "a {",
            "  font-weight: bold;",
            "  text-decoration: none;",
            "  &:hover { text-decoration: underline; }",
            "  body.firefox & { font-weight: normal; }",
            "}"))
        .matches(code(
            "#main {",
            "  color: black;",
            "  a {",
            "    font-weight: bold;",
            "    &:hover { color: red; }",
            "  }",
            "}"));
  }

  @Test
  public void parentSelector2() {
    assertThat(b.rule(CssGrammar.SELECTOR))
        .matches("body.firefox &");
  }

  // NEED TEST FOR PLACEHOLDER SELECTORS

  @Test
  public void comment() {
    assertThat(b.rule(CssGrammar.STYLESHEET))
        .matches(code(
            "/* This comment is",
            " * several lines long.",
            " * since it uses the CSS comment syntax,",
            " * it will appear in the CSS output. */",
            "body { color: black; }",
            "// These comments are only one line long each.",
            "// They won't appear in the CSS output,",
            "// since they use the single-line comment syntax.",
            "a { color: green; }"));
  }

  @Test
  public void arithmeticExpressions() {
    assertThat(b.rule(SassGrammar.EXPRESSION))
      // We cannot manage the difference now so it is an expression
      .matches("10px/8px")
        .matches("$width/2")
        .matches("(500px/2)")
        .matches("5px + (8px/2px) + 2")
        .matches("rgba(255, 0, 0, 0.75)+rgba(0, 255, 0, 0.75)")
      // We cannot manage the difference now so it is an expression
      .matches("10px / 8px")
        .matches("$width / 2")
        .matches("(500px / 2)")
        .matches("5px + (8px / 2px) + 2")
        .matches("5px + (8px / 2px / 5em * 3px) + 2")
      .matches("rgba(255, 0, 0, 0.75) + rgba(0, 255, 0, 0.75)")
        //not good yet
      .matches("5px + 8px/2px");
  }

  @Test
  public void stringOperations(){
    assertThat(b.rule(SassGrammar.STRING_EXP))
    .matches("e + -resize")
    .matches("\"Foo \" + Bar")
    .matches("sans- + \"serif\"");

  }

  @Test
  public void debugWarn() {
    assertThat(b.rule(CssGrammar.ANY))
      .matches("@debug e + -resize")
      .matches("@warn \"Foo \" + Bar")
      .matches("@debug sans- + \"serif\"")
      .matches("@debug 10px / 8px")
      .matches("@warn $width / 2")
      .matches("@debug (500px / 2)")
      .matches("@warn 5px + (8px / 2px) + 2");
  }

  @Test
  public void specialDeclarations() {
    assertThat(b.rule(CssGrammar.DECLARATION))
        .matches("content: \"I ate #{5 + 10} pies!\"")
        .matches("font-family: sans- + \"serif\"");
  }
}
