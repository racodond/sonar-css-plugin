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
import org.sonar.plugins.css.api.tree.css.PropertyDeclarationTree;

import static org.fest.assertions.Assertions.assertThat;

public class PropertyDeclarationTreeTest extends TreeTest {

  public PropertyDeclarationTreeTest() {
    super(LexicalGrammar.PROPERTY_DECLARATION);
  }

  @Test
  public void propertyDeclaration() {
    PropertyDeclarationTree tree;

    tree = checkParsed("color:green");
    assertThat(tree.property().standardProperty().getName()).isEqualTo("color");
    assertThat(tree.value().sanitizedValueElements()).isNotNull();

    tree = checkParsed(" color : green");
    assertThat(tree.property().standardProperty().getName()).isEqualTo("color");
    assertThat(tree.value().sanitizedValueElements()).isNotNull();

    tree = checkParsed(" *color : green");
    assertThat(tree.property().standardProperty().getName()).isEqualTo("color");
    assertThat(tree.property().isHacked()).isTrue();

    tree = checkParsed(" _color : green");
    assertThat(tree.property().standardProperty().getName()).isEqualTo("color");
    assertThat(tree.property().isHacked()).isTrue();

    tree = checkParsed(" -ms-color : green");
    assertThat(tree.property().standardProperty().getName()).isEqualTo("color");
    assertThat(tree.property().isVendorPrefixed()).isTrue();

    checkParsed(" color: \"def\"");
    checkParsed(" color : 123");
  }

  @Test
  public void notPropertyDeclaration() {
    checkNotParsed("color");
    checkNotParsed("color:");
  }

  private PropertyDeclarationTree checkParsed(String toParse) {
    PropertyDeclarationTree tree = (PropertyDeclarationTree) parser().parse(toParse);
    assertThat(tree.property()).isNotNull();
    assertThat(tree.colon()).isNotNull();
    assertThat(tree.value()).isNotNull();
    return tree;
  }

}
