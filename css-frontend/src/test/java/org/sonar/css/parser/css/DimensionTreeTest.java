/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
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
package org.sonar.css.parser.css;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.DimensionTree;

import static org.fest.assertions.Assertions.assertThat;

public class DimensionTreeTest extends CssTreeTest {

  public DimensionTreeTest() {
    super(LexicalGrammar.DIMENSION);
  }

  @Test
  public void dimension() {
    checkParsed("10px", "10", "px");
    checkParsed(" 10px", "10", "px");
    checkParsed("-10px", "-10", "px");
    checkParsed("+10px", "+10", "px");
    checkParsed("10.5px", "10.5", "px");
    checkParsed("-10.5px", "-10.5", "px");
    checkParsed("+10.5px", "+10.5", "px");
    checkParsed("0px", "0", "px");
    checkParsed("0.0px", "0.0", "px");
    checkParsed("-0.0px", "-0.0", "px");
    checkParsed("+0.0px", "+0.0", "px");
    checkParsed(".0px", ".0", "px");
    checkParsed(".5px", ".5", "px");
    checkParsed("-.5px", "-.5", "px");
    checkParsed("+.5px", "+.5", "px");
    checkParsed("10em", "10", "em");
    checkParsed("10ex", "10", "ex");
    checkParsed("10ch", "10", "ch");
    checkParsed("10rem", "10", "rem");
    checkParsed("10vw", "10", "vw");
    checkParsed("10vh", "10", "vh");
    checkParsed("10vmin", "10", "vmin");
    checkParsed("10vmax", "10", "vmax");
    checkParsed("10cm", "10", "cm");
    checkParsed("10mm", "10", "mm");
    checkParsed("10in", "10", "in");
    checkParsed("10pt", "10", "pt");
    checkParsed("10pc", "10", "pc");
    checkParsed("10px", "10", "px");
    checkParsed("10deg", "10", "deg");
    checkParsed("10rad", "10", "rad");
    checkParsed("10grad", "10", "grad");
    checkParsed("10turn", "10", "turn");
    checkParsed("10s", "10", "s");
    checkParsed("10ms", "10", "ms");
    checkParsed("10Hz", "10", "Hz");
    checkParsed("10kHz", "10", "kHz");
    checkParsed("10dpi", "10", "dpi");
    checkParsed("10dpcm", "10", "dpcm");
    checkParsed("10dppx", "10", "dppx");
  }

  @Test
  public void notDimension() {
    checkNotParsed("10abc");
    checkNotParsed("10 px");
    checkNotParsed("apx");
  }

  private void checkParsed(String toParse, String expectedNumber, String expectedUnit) {
    DimensionTree tree = (DimensionTree) parser().parse(toParse);
    assertThat(tree.value()).isNotNull();
    assertThat(tree.value().text()).isEqualTo(expectedNumber);
    assertThat(tree.unit()).isNotNull();
    assertThat(tree.unit().text()).isEqualTo(expectedUnit);
  }

}
