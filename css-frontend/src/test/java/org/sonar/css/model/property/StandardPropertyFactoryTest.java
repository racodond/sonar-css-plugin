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
package org.sonar.css.model.property;

import org.junit.Test;
import org.sonar.css.model.Vendor;
import org.sonar.css.model.property.standard.Border;
import org.sonar.css.model.property.standard.BorderEnd;
import org.sonar.css.model.property.standard.ColumnCount;

import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class StandardPropertyFactoryTest {

  @Test
  public void should_return_a_valid_border_property_object() {
    StandardProperty property = StandardPropertyFactory.getByName("border");
    assertEquals(Border.class, property.getClass());
    assertEquals("border", property.getName());
    assertEquals(2, property.getLinks().size());
    assertEquals("https://www.w3.org/TR/CSS22/box.html#propdef-border", property.getLinks().get(0));
    assertEquals("https://drafts.csswg.org/css-backgrounds-3/#border", property.getLinks().get(1));
    assertEquals(0, property.getVendors().size());
    assertFalse(property.isObsolete());
  }

  @Test
  public void should_return_a_valid_border_property_object_uppercase_test() {
    StandardProperty property = StandardPropertyFactory.getByName("BORDER");
    assertEquals(Border.class, property.getClass());
    assertEquals("border", property.getName());
    assertEquals(2, property.getLinks().size());
    assertEquals("https://www.w3.org/TR/CSS22/box.html#propdef-border", property.getLinks().get(0));
    assertEquals("https://drafts.csswg.org/css-backgrounds-3/#border", property.getLinks().get(1));
    assertEquals(0, property.getVendors().size());
    assertFalse(property.isObsolete());
  }

  @Test
  public void should_return_a_valid_border_end_property_object() {
    StandardProperty property = StandardPropertyFactory.getByName("border-end");
    assertEquals(BorderEnd.class, property.getClass());
    assertEquals("border-end", property.getName());
    assertEquals(0, property.getLinks().size());
    assertEquals(0, property.getValidators().size());
    assertEquals(0, property.getVendors().size());
    assertTrue(property.isObsolete());
  }

  @Test
  public void should_return_a_valid_column_count_object() {
    StandardProperty property = StandardPropertyFactory.getByName("column-count");
    assertEquals(ColumnCount.class, property.getClass());
    assertEquals("column-count", property.getName());
    assertEquals(2, property.getLinks().size());
    assertEquals("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-count", property.getLinks().get(0));
    assertEquals("https://developer.mozilla.org/en-US/docs/Web/CSS/column-count", property.getLinks().get(1));
    assertEquals(1, property.getValidators().size());
    assertEquals(1, property.getVendors().size());
    assertTrue(property.getVendors().contains(Vendor.MOZILLA));
    assertFalse(property.getVendors().contains(Vendor.MICROSOFT));
    assertFalse(property.isObsolete());
  }

  @Test
  public void should_return_an_unknown_property_object() {
    StandardProperty property = StandardPropertyFactory.getByName("Bla-bla");
    assertEquals(UnknownProperty.class, property.getClass());
    assertEquals("bla-bla", property.getName());
    assertEquals(0, property.getLinks().size());
    assertEquals(0, property.getValidators().size());
    assertEquals(0, property.getVendors().size());
    assertFalse(property.isObsolete());
  }

  @Test
  public void number_of_standard_properties() {
    assertEquals(618, StandardPropertyFactory.getAll().size());
  }

  @Test
  public void number_of_experimental_properties() {
    assertEquals(
      332,
      StandardPropertyFactory
        .getAll()
        .stream()
        .filter(StandardProperty::isExperimental)
        .collect(Collectors.toList())
        .size());
  }

  @Test
  public void should_not_find_any_property_set_to_both_obsolete_and_experimental() {
    assertEquals(
      0,
      StandardPropertyFactory
        .getAll()
        .stream()
        .filter(p -> p.isExperimental() && p.isObsolete())
        .collect(Collectors.toList())
        .size());
  }

  @Test
  public void should_not_find_any_property_set_to_obsolete_with_vendors() {
    assertEquals(
      0,
      StandardPropertyFactory
        .getAll()
        .stream()
        .filter(p -> p.isObsolete() && p.hasVendors())
        .collect(Collectors.toList())
        .size());
  }

  @Test
  public void should_not_find_any_property_not_set_to_experimental_with_vendors() {
    assertEquals(
      0,
      StandardPropertyFactory
        .getAll()
        .stream()
        .filter(p -> !p.isExperimental() && p.hasVendors())
        .collect(Collectors.toList())
        .size());
  }

  @Test
  public void should_not_find_any_property_set_to_obsolete_with_validators() {
    assertEquals(
      0,
      StandardPropertyFactory
        .getAll()
        .stream()
        .filter(p -> p.isObsolete() && p.hasValidators())
        .collect(Collectors.toList())
        .size());
  }

  @Test
  public void should_not_find_any_property_set_to_obsolete_with_shorthand() {
    assertEquals(
      0,
      StandardPropertyFactory
        .getAll()
        .stream()
        .filter(p -> p.isObsolete() && p.isShorthand())
        .collect(Collectors.toList())
        .size());
  }

}
