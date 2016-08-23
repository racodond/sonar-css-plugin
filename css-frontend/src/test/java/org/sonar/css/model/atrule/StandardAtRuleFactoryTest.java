/*
 * SonarQube CSS Plugin
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
package org.sonar.css.model.atrule;

import java.util.stream.Collectors;

import org.junit.Test;
import org.sonar.css.model.atrule.standard.CharacterVariant;
import org.sonar.css.model.atrule.standard.Charset;
import org.sonar.css.model.atrule.standard.Keyframes;

import static org.junit.Assert.*;

public class StandardAtRuleFactoryTest {

  @Test
  public void should_return_a_valid_charset_at_rule_object() {
    StandardAtRule atRule = StandardAtRuleFactory.getByName("charset");
    assertEquals(Charset.class, atRule.getClass());
    assertEquals(atRule.getName(), "charset");
    assertEquals(atRule.getLinks().size(), 1);
    assertEquals(atRule.getLinks().get(0), "https://www.w3.org/TR/css-syntax-3/#charset-rule");
    assertEquals(atRule.isObsolete(), false);
    assertEquals(atRule.isExperimental(), false);
  }

  @Test
  public void should_return_a_valid_charset_at_rule_object_uppercase_test() {
    StandardAtRule atRule = StandardAtRuleFactory.getByName("CHARSET");
    assertEquals(Charset.class, atRule.getClass());
    assertEquals(atRule.getName(), "charset");
    assertEquals(atRule.getLinks().size(), 1);
    assertEquals(atRule.getLinks().get(0), "https://www.w3.org/TR/css-syntax-3/#charset-rule");
    assertEquals(atRule.isObsolete(), false);
    assertEquals(atRule.isExperimental(), false);
  }

  @Test
  public void should_return_a_valid_character_variant_at_rule_object() {
    StandardAtRule atRule = StandardAtRuleFactory.getByName("character-variant");
    assertEquals(CharacterVariant.class, atRule.getClass());
    assertEquals(atRule.getName(), "character-variant");
    assertEquals(atRule.getLinks().size(), 2);
    assertEquals(atRule.getLinks().get(0), "https://drafts.csswg.org/css-fonts-3/#font-feature-values");
    assertEquals(atRule.getLinks().get(1), "https://developer.mozilla.org/en-US/docs/Web/CSS/@font-feature-values");
    assertEquals(atRule.isObsolete(), false);
    assertEquals(atRule.isExperimental(), true);
  }

  @Test
  public void should_return_a_valid_keyframes_at_rule_object() {
    StandardAtRule atRule = StandardAtRuleFactory.getByName("keyframes");
    assertEquals(Keyframes.class, atRule.getClass());
    assertEquals(atRule.getName(), "keyframes");
    assertEquals(atRule.getLinks().size(), 1);
    assertFalse(atRule.isObsolete());
    assertTrue(atRule.isExperimental());
  }

  @Test
  public void number_of_standard_at_rules() {
    assertEquals(35, StandardAtRuleFactory.getAll().size());
  }

  @Test
  public void number_of_experimental_standard_at_rules() {
    assertEquals(
      28,
      StandardAtRuleFactory.getAll()
        .stream()
        .filter(StandardAtRule::isExperimental)
        .collect(Collectors.toList())
        .size());
  }

  @Test
  public void number_of_obsolete_standard_at_rules() {
    assertEquals(
      0,
      StandardAtRuleFactory.getAll()
        .stream()
        .filter(StandardAtRule::isObsolete)
        .collect(Collectors.toList())
        .size());
  }

}
