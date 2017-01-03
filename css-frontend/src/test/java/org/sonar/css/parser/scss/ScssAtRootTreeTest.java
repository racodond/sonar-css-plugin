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
import org.sonar.plugins.css.api.tree.scss.ScssAtRootTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssAtRootTreeTest extends ScssTreeTest {

  public ScssAtRootTreeTest() {
    super(LexicalGrammar.SCSS_AT_ROOT);
  }

  @Test
  public void scssAtRoot() {
    ScssAtRootTree tree;

    tree = checkParsed("@at-root h1 { color: green; }");
    assertThat(tree.ruleset()).isNotNull();
    assertThat(tree.block()).isNull();

    tree = checkParsed("@at-root { color: green; }");
    assertThat(tree.ruleset()).isNull();
    assertThat(tree.block()).isNotNull();

    tree = checkParsed("@at-root(with: media support) { color: green; }");
    assertThat(tree.ruleset()).isNull();
    assertThat(tree.block()).isNotNull();
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().parameter().text()).isEqualTo("with");
    assertThat(tree.parameters().values()).hasSize(2);

    tree = checkParsed("@at-root ( without: media support ) { color: green; }");
    assertThat(tree.ruleset()).isNull();
    assertThat(tree.block()).isNotNull();
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().parameter().text()).isEqualTo("without");
    assertThat(tree.parameters().values()).hasSize(2);
  }

  @Test
  public void notScssAtRoot() {
    checkNotParsed("@at-root");
    checkNotParsed("@at-root()");
    checkNotParsed("@at-root(withe: abc) {}");
    checkNotParsed("@at-root;");
  }

  private ScssAtRootTree checkParsed(String toParse) {
    ScssAtRootTree tree = (ScssAtRootTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.directive()).isNotNull();
    assertThat(tree.directive().at()).isNotNull();
    assertThat(tree.directive().name().text()).isEqualTo("at-root");
    return tree;
  }

}
