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
package org.sonar.css.model.pseudo.pseudofunction;

import org.junit.Test;
import org.sonar.css.model.pseudo.pseudofunction.standard.Has;

import static org.junit.Assert.assertEquals;

public class StandardPseudoFunctionFactoryTest {

  @Test
  public void should_return_a_valid_has_pseudo_function_object() {
    StandardPseudoFunction function = StandardPseudoFunctionFactory.getByName("has");
    assertEquals(Has.class, function.getClass());
    assertEquals(function.getName(), "has");
    assertEquals(function.getLinks().size(), 1);
    assertEquals(function.getLinks().get(0), "https://drafts.csswg.org/selectors-4/#overview");
    assertEquals(function.isExperimental(), true);
    assertEquals(function.isObsolete(), false);
  }

  @Test
  public void should_return_a_valid_has_pseudo_function_object_uppercase_test() {
    StandardPseudoFunction function = StandardPseudoFunctionFactory.getByName("HAS");
    assertEquals(Has.class, function.getClass());
    assertEquals(function.getName(), "has");
    assertEquals(function.getLinks().size(), 1);
    assertEquals(function.getLinks().get(0), "https://drafts.csswg.org/selectors-4/#overview");
    assertEquals(function.isExperimental(), true);
    assertEquals(function.isObsolete(), false);
  }

  @Test
  public void should_return_a_valid_has_pseudo_function_object_mix_uppercase_lowercase_test() {
    StandardPseudoFunction function = StandardPseudoFunctionFactory.getByName("HaS");
    assertEquals(Has.class, function.getClass());
    assertEquals(function.getName(), "has");
    assertEquals(function.getLinks().size(), 1);
    assertEquals(function.getLinks().get(0), "https://drafts.csswg.org/selectors-4/#overview");
    assertEquals(function.isExperimental(), true);
    assertEquals(function.isObsolete(), false);
  }

  @Test
  public void number_of_standard_pseudo_functions() {
    assertEquals(14, StandardPseudoFunctionFactory.getAll().size());
  }

  @Test
  public void number_of_obsolete_pseudo_functions() {
    assertEquals(1, StandardPseudoFunctionFactory.getAll().stream().filter(StandardPseudoFunction::isObsolete).count());
  }

  @Test
  public void number_of_experimental_pseudo_functions() {
    assertEquals(7, StandardPseudoFunctionFactory.getAll().stream().filter(StandardPseudoFunction::isExperimental).count());
  }

}
