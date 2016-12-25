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
package org.sonar.css.parser.scss;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.css.StringTree;
import org.sonar.plugins.css.api.tree.scss.ScssVariableTree;
import org.sonar.plugins.css.api.tree.scss.ScssErrorTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssErrorTreeTest extends ScssTreeTest {

  public ScssErrorTreeTest() {
    super(LexicalGrammar.SCSS_ERROR);
  }

  @Test
  public void scssError() {
    ScssErrorTree tree;

    tree = checkParsed("@error a");
    assertThat(tree.value().valueElements()).hasSize(1);
    assertThat(tree.value().valueElements().get(0)).isInstanceOf(IdentifierTree.class);
    assertThat(tree.semicolon()).isNull();

    tree = checkParsed(" @error a");
    assertThat(tree.value().valueElements()).hasSize(1);
    assertThat(tree.value().valueElements().get(0)).isInstanceOf(IdentifierTree.class);
    assertThat(tree.semicolon()).isNull();

    tree = checkParsed("@error a;");
    assertThat(tree.value().valueElements()).hasSize(1);
    assertThat(tree.value().valueElements().get(0)).isInstanceOf(IdentifierTree.class);
    assertThat(tree.semicolon()).isNotNull();

    tree = checkParsed("@error $abc \"d#{$abc}ef\";");
    assertThat(tree.value().valueElements()).hasSize(2);
    assertThat(tree.value().valueElements().get(0)).isInstanceOf(ScssVariableTree.class);
    assertThat(tree.value().valueElements().get(1)).isInstanceOf(StringTree.class);
    assertThat(tree.semicolon()).isNotNull();
  }

  @Test
  public void notScssError() {
    checkNotParsed("@error");
    checkNotParsed("@error;");
  }

  private ScssErrorTree checkParsed(String toParse) {
    ScssErrorTree tree = (ScssErrorTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.directive()).isNotNull();
    assertThat(tree.value()).isNotNull();
    return tree;
  }

}
