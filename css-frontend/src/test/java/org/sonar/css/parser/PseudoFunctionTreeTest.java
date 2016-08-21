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
import org.sonar.plugins.css.api.tree.PseudoFunctionTree;

import static org.fest.assertions.Assertions.assertThat;

public class PseudoFunctionTreeTest extends TreeTest {

  public PseudoFunctionTreeTest() {
    super(CssLexicalGrammar.PSEUDO_FUNCTION);
  }

  @Test
  public void pseudoFunction() {
    checkParsed(":lang(en)", ":", "lang");
    checkParsed(":nth-last-child(2)", ":", "nth-last-child");
    checkParsed(":nth-last-child( 2 )", ":", "nth-last-child");
  }

  @Test
  public void notPseudoFunction() {
    checkNotParsed(": abc()");
    checkNotParsed(" :abc()");
    checkNotParsed(": abc(param)");
    checkNotParsed(" :abc(param)");
    checkNotParsed(":: abc()");
    checkNotParsed(":: abc(param)");
    checkNotParsed(" ::abc(param)");
    checkNotParsed("::abc (param)");
  }

  private PseudoFunctionTree checkParsed(String toParse, String expectedPrefix, String expectedPseudoFunctionName) {
    PseudoFunctionTree tree = (PseudoFunctionTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.prefix()).isNotNull();
    assertThat(tree.prefix().text()).isEqualTo(expectedPrefix);
    assertThat(tree.pseudoFunctionName()).isNotNull();
    assertThat(tree.pseudoFunctionName().text()).isEqualTo(expectedPseudoFunctionName);
    return tree;
  }

}
