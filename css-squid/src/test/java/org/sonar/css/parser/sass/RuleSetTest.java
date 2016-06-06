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
package org.sonar.css.parser.sass;

import org.junit.Test;
import org.sonar.css.parser.CssGrammar;
import org.sonar.css.parser.SassGrammar;
import org.sonar.css.parser.TestBase;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class RuleSetTest extends TestBase {

  private final LexerlessGrammar b = SassGrammar.createGrammar();

  @Test
  public void nestedVarDeclaration() {
    assertThat(b.rule(CssGrammar.RULESET))
      .matches("p {color:red;$width:5em;}")
      .matches(code(
        "p {",
        "color:red;",
        "$width:5em;",
        "}"));
  }

  @Test
  public void nestedProperties() {
    assertThat(b.rule(CssGrammar.RULESET))
      .matches(
        code(
        ".funky {" +
          "  font: {" +
          "  family: fantasy;" +
          "    size: 30em;" +
          "    weight: bold;" +
          "  }" +
          "}"
        )
      )
      .matches(
        code(
        ".funky {" +
          "  font: 2px/3px {" +
          "  family: fantasy;" +
          "    size: 30em;" +
          "    weight: bold;" +
          "  }" +
          "}"
        )
      );
  }

  @Test
  public void nestedImport() {
    assertThat(b.rule(CssGrammar.RULESET))
      .matches(
        code(
        "#main {" +
          "  @import \"example\";" +
          "}"
        )
      ).matches(
        code(
        ".sidebar {" +
          "  width: 300px;" +
          "  @media screen and (orientation: landscape) {" +
          "    width: 500px;" +
          "  }" +
          "}"
        )
      );
  }

  @Test
  public void starHack() {
    assertThat(b.rule(CssGrammar.RULESET))
      .matches(code(
        ".mybox {" +
          "    border: 1px solid black;" +
          "    padding: 5px;" +
          "    width: 100px;" +
          "    *width: 200px;" +
          "}"
        ));
  }

  @Test
  public void nestedRules() {
    assertThat(b.rule(CssGrammar.STYLESHEET))
      .matches(code(
        "#main p {",
        "  color: #00ff00;",
        "  width: 97%;",
        "  .redbox {",
        "    background-color: #ff0000;",
        "    color: #000000;",
        "  }",
        "}"))
      .matches(code(
        "   #main {",
        "     width: 97%",
        "     p, div {",
        "       font-size: 2em;",
        "       a { font-weight: bold; }",
        "     }",
        "     pre { font-size: 3em; }",
        "   }"));
  }

  @Test
  public void extend() {
    assertThat(b.rule(CssGrammar.RULESET))
      .matches(code(".seriousError {",
        "@extend .error;",
        "@extend .notice !optional;",
        "border-width: 3px;",
        "}"));
    assertThat(b.rule(CssGrammar.STYLESHEET))
      .matches(code(
        "#fake-links .link {",
        "  @extend a;",
        "}",
        "",
        "a {",
        "  color: blue;",
        "  &:hover {",
        "    text-decoration: underline;",
        "  }",
        "}"

        ));
  }

}
