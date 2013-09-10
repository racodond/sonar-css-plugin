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

import org.sonar.css.parser.SassGrammar;
import org.sonar.css.parser.TestBase;
import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;
import static org.sonar.sslr.tests.Assertions.assertThat;

public class LowLevelTest extends TestBase {

  private LexerlessGrammar b = SassGrammar.createGrammar();

  @Test
  public void variable() {
    assertThat(b.rule(SassGrammar.variable))
      .matches("$width");
  }

  @Test
  public void varDeclaration() {
    assertThat(b.rule(SassGrammar.varDeclaration))
      .matches("$width:5em;")
      .matches("$translucent-red: rgba(255, 0, 0, 0.5);");
  }

  @Test
  public void nestedVarDeclaration() {
    assertThat(b.rule(CssGrammar.ruleset))
      .matches("p {color:red;$width:5em;}")
      .matches(code(
        "p {",
        "color:red;",
        "$width:5em;",
        "}"));
  }

  @Test
  public void varUsage() {
    assertThat(b.rule(CssGrammar.declaration))
      .matches("width: $width/2");
  }

  @Test
  public void parentSelector() {
    assertThat(b.rule(SassGrammar.parentSelector))
      .matches("&")
      .matches("&:hover");
  }

  @Test
  public void parentSelector2() {
    assertThat(b.rule(CssGrammar.selector))
      .matches("body.firefox &");
  }


}
