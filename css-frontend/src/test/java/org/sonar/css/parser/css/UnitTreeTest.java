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
import org.sonar.css.parser.TreeTest;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.UnitTree;

import static org.fest.assertions.Assertions.assertThat;

public class UnitTreeTest extends TreeTest {

  public UnitTreeTest() {
    super(LexicalGrammar.UNIT);
  }

  @Test
  public void unit() {
    checkParsed("em");
    checkParsed("ex");
    checkParsed("ch");
    checkParsed("rem");
    checkParsed("vw");
    checkParsed("vh");
    checkParsed("vmin");
    checkParsed("vmax");
    checkParsed("cm");
    checkParsed("mm");
    checkParsed("in");
    checkParsed("px");
    checkParsed("pt");
    checkParsed("pc");
    checkParsed("ms");
    checkParsed("s");
    checkParsed("hz");
    checkParsed("dB");
    checkParsed("khz");
    checkParsed("deg");
    checkParsed("grad");
    checkParsed("rad");
    checkParsed("turn");
    checkParsed("dpi");
    checkParsed("dpcm");
    checkParsed("dppx");

    checkParsed("EM");
    checkParsed("EX");
    checkParsed("CH");
    checkParsed("REM");
    checkParsed("VW");
    checkParsed("VH");
    checkParsed("VMIN");
    checkParsed("VMAX");
    checkParsed("CM");
    checkParsed("MM");
    checkParsed("IN");
    checkParsed("PX");
    checkParsed("PT");
    checkParsed("PC");
    checkParsed("MS");
    checkParsed("S");
    checkParsed("HZ");
    checkParsed("DB");
    checkParsed("KHZ");
    checkParsed("DEG");
    checkParsed("GRAD");
    checkParsed("RAD");
    checkParsed("TURN");
    checkParsed("DPI");
    checkParsed("DPCM");
    checkParsed("DPPX");

    checkParsed("hz");
    checkParsed("dB");
    checkParsed("khz");
  }

  @Test
  public void notUnit() {
    checkNotParsed("abc");
    checkNotParsed("ABC");
    checkNotParsed("AbC");
    checkNotParsed(" abc");
    checkNotParsed("abc ");
  }

  private void checkParsed(String toParse) {
    UnitTree tree = (UnitTree) parser().parse(toParse);
    assertThat(tree.value()).isNotNull();
    assertThat(tree.text()).isEqualTo(toParse);
  }

}
