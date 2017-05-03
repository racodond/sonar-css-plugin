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
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.css.NumberTree;
import org.sonar.plugins.css.api.tree.scss.ScssMapTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssMapTreeTest extends ScssTreeTest {

  public ScssMapTreeTest() {
    super(LexicalGrammar.SCSS_MAP);
  }

  @Test
  public void scssMap() {
    ScssMapTree tree;

    tree = checkParsed("(a: 2)");
    assertThat(tree.entries()).hasSize(1);
    assertThat(tree.entries().get(0).key().valueElements().get(0)).isInstanceOf(IdentifierTree.class);
    assertThat(tree.entries().get(0).value().valueElements().get(0)).isInstanceOf(NumberTree.class);

    tree = checkParsed("(a: 2 + 3)");
    assertThat(tree.entries()).hasSize(1);

    tree = checkParsed("(a: (2 + 3),b: \"abc\")");
    assertThat(tree.entries()).hasSize(2);

    tree = checkParsed("(a: 2 3,b: \"abc\")");
    assertThat(tree.entries()).hasSize(2);

    tree = checkParsed("(a: 2 3,b: \"abc\", )");
    assertThat(tree.entries()).hasSize(2);

    tree = checkParsed("(\n" +
      "  background:#fff,\n" +
      "  border:#ccc,\n" +
      "  box-shadow:map-get($component,box-shadow))");
    assertThat(tree.entries()).hasSize(3);
  }

  @Test
  public void notScssMap() {
    checkNotParsed("()");
    checkNotParsed("(a)");
    checkNotParsed("(a:)");
  }

  private ScssMapTree checkParsed(String toParse) {
    ScssMapTree tree = (ScssMapTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.openParenthesis()).isNotNull();
    assertThat(tree.closeParenthesis()).isNotNull();
    return tree;
  }

}
