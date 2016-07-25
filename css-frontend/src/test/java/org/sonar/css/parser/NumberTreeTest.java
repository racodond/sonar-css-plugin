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
package org.sonar.css.parser;

import org.junit.Test;
import org.sonar.plugins.css.api.tree.NumberTree;

import static org.fest.assertions.Assertions.assertThat;

public class NumberTreeTest extends TreeTest {

  public NumberTreeTest() {
    super(CssLexicalGrammar.NUMBER);
  }

  @Test
  public void number() {
    NumberTree tree;

    tree = checkParsed("0");
    assertThat(tree.isZero()).isTrue();
    assertThat(tree.isNotZero()).isFalse();
    assertThat(tree.doubleValue()).isEqualTo(0);
    assertThat(tree.integerValue()).isEqualTo(0);
    assertThat(tree.isPositive()).isTrue();

    tree = checkParsed("+0");
    assertThat(tree.isZero()).isTrue();
    assertThat(tree.isNotZero()).isFalse();
    assertThat(tree.doubleValue()).isEqualTo(0.0);
    assertThat(tree.integerValue()).isEqualTo(0);
    assertThat(tree.isPositive()).isTrue();

    tree = checkParsed("-0");
    assertThat(tree.isZero()).isTrue();
    assertThat(tree.isNotZero()).isFalse();
    assertThat(tree.doubleValue()).isEqualTo(-0.0);
    assertThat(tree.integerValue()).isEqualTo(0);
    assertThat(tree.isPositive()).isTrue();

    tree = checkParsed("1");
    assertThat(tree.isZero()).isFalse();
    assertThat(tree.isNotZero()).isTrue();
    assertThat(tree.doubleValue()).isEqualTo(1.0);
    assertThat(tree.integerValue()).isEqualTo(1);
    assertThat(tree.isPositive()).isTrue();

    tree = checkParsed("+1");
    assertThat(tree.isZero()).isFalse();
    assertThat(tree.isNotZero()).isTrue();
    assertThat(tree.doubleValue()).isEqualTo(1.0);
    assertThat(tree.integerValue()).isEqualTo(1);
    assertThat(tree.isPositive()).isTrue();

    tree = checkParsed("-1");
    assertThat(tree.isZero()).isFalse();
    assertThat(tree.isNotZero()).isTrue();
    assertThat(tree.doubleValue()).isEqualTo(-1.0);
    assertThat(tree.integerValue()).isEqualTo(-1);
    assertThat(tree.isPositive()).isFalse();

    tree = checkParsed("1.1");
    assertThat(tree.isZero()).isFalse();
    assertThat(tree.isNotZero()).isTrue();
    assertThat(tree.doubleValue()).isEqualTo(1.1);
    assertThat(tree.integerValue()).isEqualTo(1);
    assertThat(tree.isPositive()).isTrue();

    tree = checkParsed("+1.1");
    assertThat(tree.isZero()).isFalse();
    assertThat(tree.isNotZero()).isTrue();
    assertThat(tree.doubleValue()).isEqualTo(1.1);
    assertThat(tree.integerValue()).isEqualTo(1);
    assertThat(tree.isPositive()).isTrue();

    tree = checkParsed("-1.1");
    assertThat(tree.isZero()).isFalse();
    assertThat(tree.isNotZero()).isTrue();
    assertThat(tree.doubleValue()).isEqualTo(-1.1);
    assertThat(tree.integerValue()).isEqualTo(-1);
    assertThat(tree.isPositive()).isFalse();

    checkParsed("1.11");
    checkParsed("+1.11");
    checkParsed("-1.11");
    checkParsed(".11");
    checkParsed("-.11");
    checkParsed("+.11");
    checkParsed("0.11");
    checkParsed("+0.11");
    checkParsed("-0.11");
  }

  @Test
  public void notNumber() {
    checkNotParsed("+");
    checkNotParsed("-");
    checkNotParsed("-.");
    checkNotParsed("+.");
    checkNotParsed("+a");
    checkNotParsed("-a");
    checkNotParsed("abc");
  }

  private NumberTree checkParsed(String toParse) {
    NumberTree tree = (NumberTree) parser().parse(toParse);
    assertThat(tree.value()).isNotNull();
    assertThat(tree.text()).isEqualTo(toParse);
    return tree;
  }

}
