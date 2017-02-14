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
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.NamespaceTree;

import static org.fest.assertions.Assertions.assertThat;

public class NamespaceTreeTest extends CssTreeTest {

  public NamespaceTreeTest() {
    super(LexicalGrammar.NAMESPACE);
  }

  @Test
  public void namespace() {
    NamespaceTree tree;

    tree = checkParsed("|");
    assertThat(tree.prefix()).isNull();

    tree = checkParsed("*|");
    assertThat(tree.prefix()).isNotNull();
    assertThat(tree.prefix().text()).isEqualTo("*");

    tree = checkParsed("abc|");
    assertThat(tree.prefix()).isNotNull();
    assertThat(tree.prefix().text()).isEqualTo("abc");
  }

  @Test
  public void notNamespace() {
    checkNotParsed("=|");
    checkNotParsed(" |");
    checkNotParsed(" *|");
    checkNotParsed(" abc|");
    checkNotParsed("* |");
    checkNotParsed("abc |");
  }

  private NamespaceTree checkParsed(String toParse) {
    NamespaceTree tree = (NamespaceTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.pipe()).isNotNull();
    return tree;
  }

}
