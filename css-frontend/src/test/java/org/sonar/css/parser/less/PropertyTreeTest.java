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
package org.sonar.css.parser.less;

import org.junit.Test;
import org.sonar.css.model.Vendor;
import org.sonar.css.model.property.UnknownProperty;
import org.sonar.css.model.property.standard.Color;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.PropertyTree;

import static org.fest.assertions.Assertions.assertThat;

public class PropertyTreeTest extends LessTreeTest {

  public PropertyTreeTest() {
    super(LexicalGrammar.PROPERTY);
  }

  @Test
  public void property() {
    PropertyTree tree;

    tree = checkParsed("color");
    assertThat(tree.property().text()).isEqualTo("color");
    assertThat(tree.standardProperty()).isInstanceOf(Color.class);
    assertThat(tree.isVendorPrefixed()).isFalse();
    assertThat(tree.isHacked()).isFalse();

    tree = checkParsed(" color");
    assertThat(tree.property().text()).isEqualTo("color");
    assertThat(tree.standardProperty()).isInstanceOf(Color.class);
    assertThat(tree.isVendorPrefixed()).isFalse();
    assertThat(tree.isHacked()).isFalse();

    tree = checkParsed(" Color");
    assertThat(tree.property().text()).isEqualTo("Color");
    assertThat(tree.standardProperty()).isInstanceOf(Color.class);
    assertThat(tree.isVendorPrefixed()).isFalse();
    assertThat(tree.isHacked()).isFalse();

    tree = checkParsed(" *color");
    assertThat(tree.property().text()).isEqualTo("*color");
    assertThat(tree.standardProperty()).isInstanceOf(Color.class);
    assertThat(tree.isVendorPrefixed()).isFalse();
    assertThat(tree.isHacked()).isTrue();
    assertThat(tree.hack()).isEqualTo("*");
    assertThat(tree.unhackedFullName()).isEqualTo("color");

    tree = checkParsed(" _color");
    assertThat(tree.property().text()).isEqualTo("_color");
    assertThat(tree.standardProperty()).isInstanceOf(Color.class);
    assertThat(tree.isVendorPrefixed()).isFalse();
    assertThat(tree.isHacked()).isTrue();
    assertThat(tree.hack()).isEqualTo("_");
    assertThat(tree.unhackedFullName()).isEqualTo("color");

    tree = checkParsed(" -ms-color");
    assertThat(tree.property().text()).isEqualTo("-ms-color");
    assertThat(tree.standardProperty()).isInstanceOf(Color.class);
    assertThat(tree.isVendorPrefixed()).isTrue();
    assertThat(tree.vendor()).isEqualTo(Vendor.MICROSOFT);
    assertThat(tree.isHacked()).isFalse();
    assertThat(tree.property().isLessInterpolated()).isFalse();
    assertThat(tree.property().isInterpolated()).isFalse();

    tree = checkParsed(" -ms-@{background}-color");
    assertThat(tree.property().text()).isEqualTo("-ms-@{background}-color");
    assertThat(tree.property().isLessInterpolated()).isTrue();
    assertThat(tree.property().isInterpolated()).isTrue();
    assertThat(tree.standardProperty()).isInstanceOf(UnknownProperty.class);
    assertThat(tree.isVendorPrefixed()).isTrue();
    assertThat(tree.vendor()).isEqualTo(Vendor.MICROSOFT);
    assertThat(tree.isHacked()).isFalse();

    tree = checkParsed("color+");
    assertThat(tree.isLessMerge()).isTrue();
    assertThat(tree.lessMerge().text()).isEqualTo("+");
    assertThat(tree.unhackedFullName()).isEqualTo("color");

    tree = checkParsed("color+_");
    assertThat(tree.isLessMerge()).isTrue();
    assertThat(tree.lessMerge().text()).isEqualTo("+_");
    assertThat(tree.unhackedFullName()).isEqualTo("color");
  }

  @Test
  public void notProperty() {
    checkNotParsed("$abc");
    checkNotParsed("\"abc\"");
  }

  private PropertyTree checkParsed(String toParse) {
    PropertyTree tree = (PropertyTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.property()).isNotNull();
    assertThat(tree.standardProperty()).isNotNull();
    return tree;
  }

}
