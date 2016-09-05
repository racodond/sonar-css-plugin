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
import org.sonar.plugins.css.api.tree.css.FunctionTree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

import static org.fest.assertions.Assertions.assertThat;

public class ValueTreeTest extends TreeTest {

  public ValueTreeTest() {
    super(LexicalGrammar.VALUE);
  }

  @Test
  public void value() {
    ValueTree tree;

    tree = checkParsed("max(1, 3)");
    assertThat(tree.valueElements()).hasSize(1);
    assertThat(tree.sanitizedValueElements()).hasSize(1);
    assertThat(tree.sanitizedValueElements().get(0)).isInstanceOf(FunctionTree.class);

    tree = checkParsed("max(1, 3) min(1, 3)");
    assertThat(tree.valueElements()).hasSize(2);
    assertThat(tree.sanitizedValueElements()).hasSize(2);
    assertThat(tree.sanitizedValueElements().get(0)).isInstanceOf(FunctionTree.class);
    assertThat(tree.sanitizedValueElements().get(1)).isInstanceOf(FunctionTree.class);

    tree = checkParsed("max(1, 3) !important min(1, 3)");
    assertThat(tree.valueElements()).hasSize(3);
    assertThat(tree.sanitizedValueElements()).hasSize(2);
    assertThat(tree.sanitizedValueElements().get(0)).isInstanceOf(FunctionTree.class);
    assertThat(tree.sanitizedValueElements().get(1)).isInstanceOf(FunctionTree.class);
  }

  private ValueTree checkParsed(String toParse) {
    ValueTree tree = (ValueTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.sanitizedValueElements()).isNotNull();
    assertThat(tree.valueElements()).isNotNull();
    return tree;
  }

}
