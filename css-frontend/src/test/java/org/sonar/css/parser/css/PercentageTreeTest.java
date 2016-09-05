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
import org.sonar.plugins.css.api.tree.css.PercentageTree;

import static org.fest.assertions.Assertions.assertThat;

public class PercentageTreeTest extends TreeTest {

  public PercentageTreeTest() {
    super(LexicalGrammar.PERCENTAGE);
  }

  @Test
  public void percentage() {
    checkParsed("0%", "0");
    checkParsed(" 0%", "0");
    checkParsed("+0%", "+0");
    checkParsed("-0%", "-0");
    checkParsed("1%", "1");
    checkParsed("+1%", "+1");
    checkParsed("-1%", "-1");
    checkParsed("1.1%", "1.1");
    checkParsed("+1.1%", "+1.1");
    checkParsed("-1.1%", "-1.1");
    checkParsed("1.11%", "1.11");
    checkParsed("+1.11%", "+1.11");
    checkParsed("-1.11%", "-1.11");
    checkParsed(".11%", ".11");
    checkParsed("-.11%", "-.11");
    checkParsed("+.11%", "+.11");
    checkParsed("0.11%", "0.11");
    checkParsed("+0.11%", "+0.11");
    checkParsed("-0.11%", "-0.11");
  }

  @Test
  public void notPercentage() {
    checkNotParsed("10 %");
    checkNotParsed("a%");
  }

  private void checkParsed(String toParse, String expectedNumber) {
    PercentageTree tree = (PercentageTree) parser().parse(toParse);
    assertThat(tree.value()).isNotNull();
    assertThat(tree.percentageSymbol()).isNotNull();
    assertThat(tree.percentageSymbol().text()).isEqualTo("%");
    assertThat(tree.value().text()).isEqualTo(expectedNumber);
  }

}
