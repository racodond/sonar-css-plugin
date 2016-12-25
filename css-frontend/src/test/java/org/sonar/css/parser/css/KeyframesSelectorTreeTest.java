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
import org.sonar.plugins.css.api.tree.css.KeyframesSelectorTree;
import org.sonar.plugins.css.api.tree.css.PercentageTree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;

import static org.fest.assertions.Assertions.assertThat;

public class KeyframesSelectorTreeTest extends CssTreeTest {

  public KeyframesSelectorTreeTest() {
    super(LexicalGrammar.KEYFRAMES_SELECTOR);
  }

  @Test
  public void keyframesSelector() {
    KeyframesSelectorTree tree;

    tree = checkParsed("to");
    assertThat(tree.selector()).isInstanceOf(SyntaxToken.class);
    assertThat(((SyntaxToken) tree.selector()).text()).isEqualTo("to");

    tree = checkParsed(" to");
    assertThat(tree.selector()).isInstanceOf(SyntaxToken.class);
    assertThat(((SyntaxToken) tree.selector()).text()).isEqualTo("to");

    tree = checkParsed("from");
    assertThat(tree.selector()).isInstanceOf(SyntaxToken.class);
    assertThat(((SyntaxToken) tree.selector()).text()).isEqualTo("from");

    tree = checkParsed("10%");
    assertThat(tree.selector()).isInstanceOf(PercentageTree.class);
    assertThat(((PercentageTree) tree.selector()).value().text()).isEqualTo("10");
  }

  @Test
  public void notKeyframesSelector() {
    checkNotParsed("abc");
  }

  private KeyframesSelectorTree checkParsed(String toParse) {
    KeyframesSelectorTree tree = (KeyframesSelectorTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    return tree;
  }

}
