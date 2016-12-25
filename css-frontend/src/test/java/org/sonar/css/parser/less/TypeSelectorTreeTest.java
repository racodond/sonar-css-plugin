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
package org.sonar.css.parser.less;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.TypeSelectorTree;

import static org.fest.assertions.Assertions.assertThat;

public class TypeSelectorTreeTest extends LessTreeTest {

  public TypeSelectorTreeTest() {
    super(LexicalGrammar.TYPE_SELECTOR);
  }

  @Test
  public void typeSelector() {
    TypeSelectorTree tree;

    tree = checkParsed("div", "div", false);
    assertThat(tree.namespace()).isNull();

    tree = checkParsed("div", "div", false);
    assertThat(tree.namespace()).isNull();

    tree = checkParsed("*", "*", false);
    assertThat(tree.namespace()).isNull();

    tree = checkParsed("|div", "div", false);
    assertThat(tree.namespace()).isNotNull();
    assertThat(tree.namespace().pipe()).isNotNull();
    assertThat(tree.namespace().prefix()).isNull();

    tree = checkParsed("*|div", "div", false);
    assertThat(tree.namespace()).isNotNull();
    assertThat(tree.namespace().pipe()).isNotNull();
    assertThat(tree.namespace().prefix()).isNotNull();
    assertThat(tree.namespace().prefix().text()).isEqualTo("*");

    tree = checkParsed("d@{i}v", "d@{i}v", true);
    assertThat(tree.namespace()).isNull();

    tree = checkParsed("|d@{i}v", "d@{i}v", true);
    assertThat(tree.namespace()).isNotNull();
    assertThat(tree.namespace().pipe()).isNotNull();
    assertThat(tree.namespace().prefix()).isNull();

    tree = checkParsed("*|d@{i}v", "d@{i}v", true);
    assertThat(tree.namespace()).isNotNull();
    assertThat(tree.namespace().pipe()).isNotNull();
    assertThat(tree.namespace().prefix()).isNotNull();
    assertThat(tree.namespace().prefix().text()).isEqualTo("*");
  }

  private TypeSelectorTree checkParsed(String toParse, String expectedIdentifier, boolean isInterpolated) {
    TypeSelectorTree tree = (TypeSelectorTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.identifier()).isNotNull();
    assertThat(tree.identifier().text()).isEqualTo(expectedIdentifier);
    assertThat(tree.identifier().isLessInterpolated()).isEqualTo(isInterpolated);
    assertThat(tree.identifier().isInterpolated()).isEqualTo(isInterpolated);
    return tree;
  }

}
