/*
 * Sonar CSS Plugin
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
package org.sonar.css.parser.sass;

import org.sonar.css.parser.CssGrammar;

import org.junit.Test;
import org.sonar.css.parser.SassGrammar;
import org.sonar.css.parser.TestBase;
import org.sonar.sslr.parser.LexerlessGrammar;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class RuleSetTest extends TestBase {

  private LexerlessGrammar b = SassGrammar.createGrammar();

  @Test
  public void variable() {
    assertThat(b.rule(CssGrammar.ruleset))
        .matches(
            code(
            "#main p {" +
              "  color: #00ff00;" +
              "  width: 97%;" +
              "" +
              "  .redbox {" +
              "    background-color: #ff0000;" +
              "    color: #000000;" +
              "  }" +
              "}"
            ))
        .matches(
            code(
            "a {" +
              "  font-weight: bold;" +
              "  text-decoration: none;" +
              "  body.firefox & { font-weight: normal; };" +
              "  &:hover { text-decoration: underline; };" +
              "}"
            )
        );
  }

  @Test
  public void nestedProperties() {
    assertThat(b.rule(CssGrammar.ruleset))
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
    assertThat(b.rule(CssGrammar.ruleset))
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
}
