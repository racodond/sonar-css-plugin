/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende
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

public class RuleSetTest extends TestBase {

  private LexerlessGrammar b = CssGrammar.createGrammar();

  @Test
  public void ruleSetTest() {
    assertThat(b.rule(CssGrammar.RULESET))
      .matches("p{color:red;}")
      .matches(
        code("h1 {", "font-weight: bold;", "font-size: 12px;",
          "font-family: Helvetica;",
          "font-variant: normal;", "}"))
      .matches(code("div" + "{" + "transform:rotate(30deg);" + "}"))
      .matches(
        code(".mybox {", "    border: 1px solid black;",
          "    padding: 5px;", "    width: 100px;",
          "    *width: 200px;", "}"))
      .matches(
        code(".visible-desktop {",
          "display: none !important ;", "}"))
      .matches(".mybox {;}")
      .matches(".mybox {;;}")
      .matches(".mybox {;color:green;}")
      .matches(".mybox {}")
      .matches("a {color:red}")
      .matches(code("a {", "color:red", "background:none", "}"));
  }
}
