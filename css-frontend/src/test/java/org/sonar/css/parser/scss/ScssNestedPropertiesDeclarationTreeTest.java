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
import org.sonar.plugins.css.api.tree.scss.ScssNestedPropertiesDeclarationTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssNestedPropertiesDeclarationTreeTest extends ScssTreeTest {

  public ScssNestedPropertiesDeclarationTreeTest() {
    super(LexicalGrammar.SCSS_NESTED_PROPERTIES_DECLARATION);
  }

  @Test
  public void scssNestedPropertiesDeclaration() {
    ScssNestedPropertiesDeclarationTree tree;

    tree = checkParsed("font: {\n" +
      "    family: fantasy;\n" +
      "    size: 30em;\n" +
      "    weight: bold;\n" +
      "  }");
    assertThat(tree.namespace().property().text()).isEqualTo("font");
    assertThat(tree.propertyDeclarations()).hasSize(3);

    tree = checkParsed("fo#{$abc}nt: {\n" +
      "    fam#{$def}ily: fantasy;\n" +
      "    size: 30em;\n" +
      "    weight: bold;\n" +
      "  }");
    assertThat(tree.namespace().property().text()).isEqualTo("fo#{$abc}nt");
    assertThat(tree.propertyDeclarations()).hasSize(3);
  }

  @Test
  public void notScssNestedPropertiesDeclaration() {
    checkNotParsed("font: abc");
  }

  private ScssNestedPropertiesDeclarationTree checkParsed(String toParse) {
    ScssNestedPropertiesDeclarationTree tree = (ScssNestedPropertiesDeclarationTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.namespace()).isNotNull();
    assertThat(tree.colon()).isNotNull();
    assertThat(tree.propertyDeclarations()).isNotNull();
    assertThat(tree.semicolon()).isNull();
    return tree;
  }

}
