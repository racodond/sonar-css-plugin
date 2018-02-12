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
package org.sonar.css.model.pseudo.pseudofunction;

import org.junit.Test;
import org.sonar.css.model.pseudo.pseudofunction.standard.Has;

import static org.junit.Assert.*;

public class StandardPseudoFunctionFactoryTest {

  @Test
  public void should_return_a_valid_has_pseudo_function_object() {
    StandardPseudoFunction function = StandardPseudoFunctionFactory.getByName("has");
    assertEquals(Has.class, function.getClass());
    assertEquals("has", function.getName());
    assertEquals(1, function.getLinks().size());
    assertEquals("https://drafts.csswg.org/selectors-4/#overview", function.getLinks().get(0));
    assertTrue(function.isExperimental());
    assertFalse(function.isObsolete());
  }

  @Test
  public void should_return_a_valid_has_pseudo_function_object_uppercase_test() {
    StandardPseudoFunction function = StandardPseudoFunctionFactory.getByName("HAS");
    assertEquals(Has.class, function.getClass());
    assertEquals("has", function.getName());
    assertEquals(1, function.getLinks().size());
    assertEquals("https://drafts.csswg.org/selectors-4/#overview", function.getLinks().get(0));
    assertTrue(function.isExperimental());
    assertFalse(function.isObsolete());
  }

  @Test
  public void should_return_a_valid_has_pseudo_function_object_mix_uppercase_lowercase_test() {
    StandardPseudoFunction function = StandardPseudoFunctionFactory.getByName("HaS");
    assertEquals(Has.class, function.getClass());
    assertEquals("has", function.getName());
    assertEquals(1, function.getLinks().size());
    assertEquals("https://drafts.csswg.org/selectors-4/#overview", function.getLinks().get(0));
    assertTrue(function.isExperimental());
    assertFalse(function.isObsolete());
  }

  @Test
  public void number_of_standard_pseudo_functions() {
    assertEquals(19, StandardPseudoFunctionFactory.getAll().size());
  }

  @Test
  public void number_of_obsolete_pseudo_functions() {
    assertEquals(1, StandardPseudoFunctionFactory.getAll().stream().filter(StandardPseudoFunction::isObsolete).count());
  }

  @Test
  public void number_of_experimental_pseudo_functions() {
    assertEquals(10, StandardPseudoFunctionFactory.getAll().stream().filter(StandardPseudoFunction::isExperimental).count());
  }

}
