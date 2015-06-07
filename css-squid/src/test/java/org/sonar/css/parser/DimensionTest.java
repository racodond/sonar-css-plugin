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

public class DimensionTest extends TestBase {

  private LexerlessGrammar b = CssGrammar.createGrammar();

  @Test
  public void should_be_dimensions() {
    assertThat(b.rule(CssGrammar.DIMENSION))
      .matches("10px")
      .matches("-10px")
      .matches("+10px")
      .matches("10.5px")
      .matches("-10.5px")
      .matches("+10.5px")
      .matches("0px")
      .matches("0.0px")
      .matches("-0.0px")
      .matches("+0.0px")
      .matches(".0px")
      .matches(".5px")
      .matches("-.5px")
      .matches("+.5px")
      .matches("10em")
      .matches("10ex")
      .matches("10ch")
      .matches("10rem")
      .matches("10vw")
      .matches("10vh")
      .matches("10vmin")
      .matches("10vmax")
      .matches("10vw")
      .matches("10vh")
      .matches("10vmin")
      .matches("10vmax")
      .matches("10cm")
      .matches("10mm")
      .matches("10in")
      .matches("10pt")
      .matches("10pc")
      .matches("10px")
      .matches("10deg")
      .matches("10rad")
      .matches("10grad")
      .matches("10turn")
      .matches("10s")
      .matches("10ms")
      .matches("10Hz")
      .matches("10kHz")
      .matches("10dpi")
      .matches("10dpcm")
      .matches("10dppx");
  }

  @Test
  public void should_not_be_dimensions() {
    assertThat(b.rule(CssGrammar.DIMENSION))
      .notMatches("10abc")
      .notMatches("10 px")
      .notMatches("10.5 px");
  }

}
