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

public class CssStylesheetTest extends TestBase {

  private LexerlessGrammar b = CssGrammar.createGrammar();

  @Test
  public void css() {
    assertThat(b.getRootRule())
        .matches(code("p {color:red;text-align:center;}"))
        .matches(code(
            "@import \"subs.css\";",
            "p {color:red;text-align:center;}"))
        .matches(code(
            "@import \"subs.css\";",
            "@import \"print-main.css\" print;",
            "@media print {",
            "body { font-size: 10pt }",
            "}",
            "h1 {color: blue }"))
        .matches(code(
            ".mybox {" +
              "  border: 1px solid black;" +
              "  padding: 5px;" +
              "  width: 100px;" +
              "  *width: 200px;" +
              "}"
            ));
  }
}
