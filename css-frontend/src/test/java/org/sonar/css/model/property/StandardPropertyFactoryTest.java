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
import org.sonar.css.model.property.standard.TransitionProperty;

import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StandardPropertyFactoryTest {

  @Test
  public void should_return_a_valid_border_property_object() {
    StandardProperty property = StandardPropertyFactory.getByName("border");
    assertEquals(Border.class, property.getClass());
    assertEquals(property.getName(), "border");
    assertEquals(property.getLinks().size(), 2);
    assertEquals(property.getLinks().get(0), "https://www.w3.org/TR/CSS22/box.html#propdef-border");
    assertEquals(property.getLinks().get(1), "https://drafts.csswg.org/css-backgrounds-3/#border");
    assertEquals(property.getVendors().size(), 0);
    assertEquals(property.isObsolete(), false);
  }

  @Test
  public void should_return_a_valid_border_property_object_uppercase_test() {
    StandardProperty property = StandardPropertyFactory.getByName("BORDER");
    assertEquals(Border.class, property.getClass());
    assertEquals(property.getName(), "border");
    assertEquals(property.getLinks().size(), 2);
    assertEquals(property.getLinks().get(0), "https://www.w3.org/TR/CSS22/box.html#propdef-border");
    assertEquals(property.getLinks().get(1), "https://drafts.csswg.org/css-backgrounds-3/#border");
    assertEquals(property.getVendors().size(), 0);
    assertEquals(property.isObsolete(), false);
  }

  @Test
  public void should_return_a_valid_border_end_property_object() {
    StandardProperty property = StandardPropertyFactory.getByName("border-end");
    assertEquals(BorderEnd.class, property.getClass());
    assertEquals(property.getName(), "border-end");
    assertEquals(property.getLinks().size(), 0);
    assertEquals(property.getValidators().size(), 0);
    assertEquals(property.getVendors().size(), 0);
    assertTrue(property.isObsolete());
  }

  @Test
  public void should_return_a_valid_column_count_object() {
    StandardProperty property = StandardPropertyFactory.getByName("column-count");
    assertEquals(ColumnCount.class, property.getClass());
    assertEquals(property.getName(), "column-count");
    assertEquals(property.getLinks().size(), 2);
    assertEquals(property.getLinks().get(0), "http://dev.w3.org/csswg/css-multicol-1/#propdef-column-count");
    assertEquals(property.getLinks().get(1), "https://developer.mozilla.org/en-US/docs/Web/CSS/column-count");
    assertEquals(property.getValidators().size(), 1);
    assertEquals(property.getVendors().size(), 1);
    assertTrue(property.getVendors().contains(Vendor.MOZILLA));
    assertFalse(property.getVendors().contains(Vendor.MICROSOFT));
    assertFalse(property.isObsolete());
  }

  @Test
  public void should_return_an_unknown_property_object() {
    StandardProperty property = StandardPropertyFactory.getByName("Bla-bla");
    assertEquals(UnknownProperty.class, property.getClass());
    assertEquals("bla-bla", property.getName());
    assertEquals(property.getLinks().size(), 0);
    assertEquals(property.getValidators().size(), 0);
    assertEquals(property.getVendors().size(), 0);
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
