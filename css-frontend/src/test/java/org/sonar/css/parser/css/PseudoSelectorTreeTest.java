/*
 * SonarQube CSS / SCSS / Less Analyzer
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
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.PseudoFunctionTree;
import org.sonar.plugins.css.api.tree.css.PseudoIdentifierTree;
import org.sonar.plugins.css.api.tree.css.PseudoSelectorTree;

import static org.fest.assertions.Assertions.assertThat;

public class PseudoSelectorTreeTest extends CssTreeTest {

  public PseudoSelectorTreeTest() {
    super(LexicalGrammar.PSEUDO_SELECTOR);
  }

  @Test
  public void pseudoSelector() {
    PseudoSelectorTree tree;

    tree = checkParsed(":active");
    assertThat(tree.component()).isInstanceOf(PseudoIdentifierTree.class);
    assertThat(tree.component().prefix()).isNotNull();
    assertThat(tree.component().prefix().text()).isEqualTo(":");
    assertThat(((PseudoIdentifierTree) tree.component()).identifier()).isNotNull();
    assertThat(((PseudoIdentifierTree) tree.component()).identifier().text()).isEqualTo("active");

    tree = checkParsed("::after");
    assertThat(tree.component()).isInstanceOf(PseudoIdentifierTree.class);
    assertThat(tree.component().prefix()).isNotNull();
    assertThat(tree.component().prefix().text()).isEqualTo("::");
    assertThat(((PseudoIdentifierTree) tree.component()).identifier()).isNotNull();
    assertThat(((PseudoIdentifierTree) tree.component()).identifier().text()).isEqualTo("after");

    tree = checkParsed(":lang(en)");
    assertThat(tree.component()).isInstanceOf(PseudoFunctionTree.class);
    assertThat(tree.component().prefix()).isNotNull();
    assertThat(tree.component().prefix().text()).isEqualTo(":");
    assertThat(((PseudoFunctionTree) tree.component()).function()).isNotNull();
    assertThat(((PseudoFunctionTree) tree.component()).function().text()).isEqualTo("lang");

    tree = checkParsed(":nth-last-child(2)");
    assertThat(tree.component()).isInstanceOf(PseudoFunctionTree.class);
    assertThat(tree.component().prefix()).isNotNull();
    assertThat(tree.component().prefix().text()).isEqualTo(":");
    assertThat(((PseudoFunctionTree) tree.component()).function()).isNotNull();
    assertThat(((PseudoFunctionTree) tree.component()).function().text()).isEqualTo("nth-last-child");

    checkParsed(":nth-last-child( 2 )");
    checkParsed("::nth-last-child( 2 )");
  }

  @Test
  public void notPseudoSelector() {
    checkNotParsed(": abc");
    checkNotParsed(":: abc");

    checkNotParsed(": abc(param)");
    checkNotParsed(":: abc()");
    checkNotParsed(":: abc(param)");
  }

  private PseudoSelectorTree checkParsed(String toParse) {
    PseudoSelectorTree tree = (PseudoSelectorTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.component()).isNotNull();
    return tree;
  }

}
