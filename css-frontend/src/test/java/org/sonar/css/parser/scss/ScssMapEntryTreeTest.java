/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2017 David RACODON
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
package org.sonar.css.parser.scss;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.tree.scss.ScssMapEntryTree;
import org.sonar.plugins.css.api.tree.scss.ScssMapTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssMapEntryTreeTest extends ScssTreeTest {

  public ScssMapEntryTreeTest() {
    super(LexicalGrammar.SCSS_MAP_ENTRY);
  }

  @Test
  public void scssMapEntry() {
    ScssMapEntryTree tree;

    tree = checkParsed("a:2");
    assertThat(tree.key().valueElements()).hasSize(1);
    assertThat(tree.key().valueElements().get(0)).isInstanceOf(IdentifierTree.class);
    assertThat(tree.value().valueElements()).hasSize(1);
    assertThat(tree.value().valueElements().get(0)).isInstanceOf(NumberTree.class);

    tree = checkParsed("a : 2");
    assertThat(tree.key().valueElements()).hasSize(1);
    assertThat(tree.key().valueElements().get(0)).isInstanceOf(IdentifierTree.class);
    assertThat(tree.value().valueElements()).hasSize(1);
    assertThat(tree.value().valueElements().get(0)).isInstanceOf(NumberTree.class);

    tree = checkParsed("\"a\": (a:2, b:3)");
    assertThat(tree.key().valueElements()).hasSize(1);
    assertThat(tree.key().valueElements().get(0)).isInstanceOf(StringTree.class);
    assertThat(tree.value().valueElements()).hasSize(1);
    assertThat(tree.value().valueElements().get(0)).isInstanceOf(ScssMapTree.class);

    tree = checkParsed("h#{hell}o: (a:2, b:3)");
    assertThat(tree.key().valueElements()).hasSize(1);
    assertThat(tree.key().valueElements().get(0)).isInstanceOf(IdentifierTree.class);
    assertThat(((IdentifierTree)tree.key().valueElements().get(0)).isScssInterpolated()).isTrue();

    tree = checkParsed("box-shadow:map-get($component,box-shadow)");
    assertThat(tree.value().valueElements()).hasSize(1);
    assertThat(tree.value().valueElements().get(0)).isInstanceOf(FunctionTree.class);
  }

  private ScssMapEntryTree checkParsed(String toParse) {
    ScssMapEntryTree tree = (ScssMapEntryTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.key()).isNotNull();
    assertThat(tree.colon()).isNotNull();
    assertThat(tree.value()).isNotNull();
    return tree;
  }

}
