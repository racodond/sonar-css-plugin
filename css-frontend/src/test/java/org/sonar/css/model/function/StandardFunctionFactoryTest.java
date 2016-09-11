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
package org.sonar.css.model.function;

import org.junit.Test;
import org.sonar.css.model.function.standard.RepeatingLinearGradient;
import org.sonar.css.model.function.standard.Rotatex;

import static org.junit.Assert.assertEquals;

public class StandardFunctionFactoryTest {

  @Test
  public void should_return_a_valid_rotatex_function_object() {
    StandardFunction function = StandardFunctionFactory.getByName("rotatex");
    assertEquals(Rotatex.class, function.getClass());
    assertEquals(function.getName(), "rotatex");
    assertEquals(function.getLinks().size(), 1);
    assertEquals(function.getLinks().get(0), "https://drafts.csswg.org/css-transforms/#three-d-transform-functions");
    assertEquals(function.isExperimental(), false);
    assertEquals(function.isObsolete(), false);
  }

  @Test
  public void should_return_a_valid_rotatex_function_object_uppercase_test() {
    StandardFunction function = StandardFunctionFactory.getByName("ROTATEX");
    assertEquals(Rotatex.class, function.getClass());
    assertEquals(function.getName(), "rotatex");
    assertEquals(function.getLinks().size(), 1);
    assertEquals(function.getLinks().get(0), "https://drafts.csswg.org/css-transforms/#three-d-transform-functions");
    assertEquals(function.isExperimental(), false);
    assertEquals(function.isObsolete(), false);
  }

  @Test
  public void should_return_a_valid_rotatex_function_object_mix_uppercase_lowercase_test() {
    StandardFunction function = StandardFunctionFactory.getByName("rotateX");
    assertEquals(Rotatex.class, function.getClass());
    assertEquals(function.getName(), "rotatex");
    assertEquals(function.getLinks().size(), 1);
    assertEquals(function.getLinks().get(0), "https://drafts.csswg.org/css-transforms/#three-d-transform-functions");
    assertEquals(function.isExperimental(), false);
    assertEquals(function.isObsolete(), false);
  }

  @Test
  public void should_return_a_valid_repeating_linear_gradient_function_object() {
    StandardFunction function = StandardFunctionFactory.getByName("repeating-linear-gradient");
    assertEquals(RepeatingLinearGradient.class, function.getClass());
    assertEquals(function.getName(), "repeating-linear-gradient");
    assertEquals(function.getLinks().size(), 1);
    assertEquals(function.isExperimental(), false);
    assertEquals(function.isObsolete(), false);
  }

  @Test
  public void should_return_a_valid_repeating_linear_gradient_function_object_mix_uppercase_lowercase_test() {
    StandardFunction function = StandardFunctionFactory.getByName("repEating-Linear-gradient");
    assertEquals(RepeatingLinearGradient.class, function.getClass());
    assertEquals(function.getName(), "repeating-linear-gradient");
    assertEquals(function.getLinks().size(), 1);
    assertEquals(function.isExperimental(), false);
    assertEquals(function.isObsolete(), false);
  }

  @Test
  public void number_of_standard_functions() {
    assertEquals(154, StandardFunctionFactory.getAll().size());
  }

  @Test
  public void number_of_obsolete_functions() {
    assertEquals(70, StandardFunctionFactory.getAll().stream().filter(StandardFunction::isObsolete).count());
  }

  @Test
  public void number_of_experimental_functions() {
    assertEquals(15, StandardFunctionFactory.getAll().stream().filter(StandardFunction::isExperimental).count());
  }

}
