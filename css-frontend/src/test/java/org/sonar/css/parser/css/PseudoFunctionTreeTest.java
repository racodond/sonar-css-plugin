/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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
import org.sonar.css.model.Vendor;
import org.sonar.css.model.pseudo.pseudofunction.UnknownPseudoFunction;
import org.sonar.css.model.pseudo.pseudofunction.standard.Lang;
import org.sonar.css.model.pseudo.pseudofunction.standard.NthLastChild;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.PseudoFunctionTree;

import static org.fest.assertions.Assertions.assertThat;

public class PseudoFunctionTreeTest extends CssTreeTest {

  public PseudoFunctionTreeTest() {
    super(LexicalGrammar.PSEUDO_FUNCTION);
  }

  @Test
  public void pseudoFunction() {
    PseudoFunctionTree tree;

    tree = checkParsed(":lang(en)", ":", "lang");
    assertThat(tree.isVendorPrefixed()).isFalse();
    assertThat(tree.parameters().parameters()).hasSize(1);
    assertThat(tree.standardFunction()).isInstanceOf(Lang.class);

    tree = checkParsed(":nth-last-child(2)", ":", "nth-last-child");
    assertThat(tree.isVendorPrefixed()).isFalse();
    assertThat(tree.parameters().parameters()).hasSize(1);
    assertThat(tree.standardFunction()).isInstanceOf(NthLastChild.class);

    tree = checkParsed(":nth-last-child( 2 )", ":", "nth-last-child");
    assertThat(tree.isVendorPrefixed()).isFalse();
    assertThat(tree.parameters().parameters()).hasSize(1);
    assertThat(tree.standardFunction()).isInstanceOf(NthLastChild.class);

    tree = checkParsed(":-moz-nth-last-child( 2 )", ":", "-moz-nth-last-child");
    assertThat(tree.isVendorPrefixed()).isTrue();
    assertThat(tree.vendor()).isEqualTo(Vendor.MOZILLA);
    assertThat(tree.parameters().parameters()).hasSize(1);
    assertThat(tree.standardFunction()).isInstanceOf(NthLastChild.class);

    tree = checkParsed(":abc( 2 )", ":", "abc");
    assertThat(tree.isVendorPrefixed()).isFalse();
    assertThat(tree.parameters().parameters()).hasSize(1);
    assertThat(tree.standardFunction()).isInstanceOf(UnknownPseudoFunction.class);
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
    checkNotParsed(":extend(param)");
    checkNotParsed("::extend(param)");
  }

  private PseudoFunctionTree checkParsed(String toParse, String expectedPrefix, String expectedPseudoFunctionName) {
    PseudoFunctionTree tree = (PseudoFunctionTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.prefix()).isNotNull();
    assertThat(tree.prefix().text()).isEqualTo(expectedPrefix);
    assertThat(tree.function()).isNotNull();
    assertThat(tree.standardFunction()).isNotNull();
    assertThat(tree.parameters().openParenthesis()).isNotNull();
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().closeParenthesis()).isNotNull();
    assertThat(tree.function().text()).isEqualTo(expectedPseudoFunctionName);
    return tree;
  }

}
