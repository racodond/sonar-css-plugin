/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
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
package org.sonar.css.model.pseudo.pseudoidentifier;

import org.junit.Test;
import org.sonar.css.model.pseudo.pseudoidentifier.standard.FirstLine;

import static org.junit.Assert.assertEquals;

public class StandardPseudoIdentifierFactoryTest {

  @Test
  public void should_return_a_valid_first_line_identifier_function_object() {
    StandardPseudoIdentifier identifier = StandardPseudoIdentifierFactory.getByName("first-line");
    assertEquals(FirstLine.class, identifier.getClass());
    assertEquals(identifier.getName(), "first-line");
    assertEquals(identifier.getLinks().size(), 1);
    assertEquals(identifier.getLinks().get(0), "https://drafts.csswg.org/css-pseudo-4/#first-line-pseudo");
    assertEquals(identifier.isExperimental(), false);
    assertEquals(identifier.isObsolete(), false);
  }

  @Test
  public void should_return_a_valid_first_line_pseudo_identifier_object_uppercase_test() {
    StandardPseudoIdentifier identifier = StandardPseudoIdentifierFactory.getByName("FIRST-LINE");
    assertEquals(FirstLine.class, identifier.getClass());
    assertEquals(identifier.getName(), "first-line");
    assertEquals(identifier.getLinks().size(), 1);
    assertEquals(identifier.getLinks().get(0), "https://drafts.csswg.org/css-pseudo-4/#first-line-pseudo");
    assertEquals(identifier.isExperimental(), false);
    assertEquals(identifier.isObsolete(), false);
  }

  @Test
  public void should_return_a_valid_first_line_pseudo_identifier_object_mix_uppercase_lowercase_test() {
    StandardPseudoIdentifier identifier = StandardPseudoIdentifierFactory.getByName("FIRSt-LINE");
    assertEquals(FirstLine.class, identifier.getClass());
    assertEquals(identifier.getName(), "first-line");
    assertEquals(identifier.getLinks().size(), 1);
    assertEquals(identifier.getLinks().get(0), "https://drafts.csswg.org/css-pseudo-4/#first-line-pseudo");
    assertEquals(identifier.isExperimental(), false);
    assertEquals(identifier.isObsolete(), false);
  }

  @Test
  public void number_of_standard_pseudo_identifiers() {
    assertEquals(54, StandardPseudoIdentifierFactory.getAll().size());
  }

  @Test
  public void number_of_obsolete_pseudo_functions() {
    assertEquals(0, StandardPseudoIdentifierFactory.getAll().stream().filter(StandardPseudoIdentifier::isObsolete).count());
  }

  @Test
  public void number_of_experimental_pseudo_identifiers() {
    assertEquals(27, StandardPseudoIdentifierFactory.getAll().stream().filter(StandardPseudoIdentifier::isExperimental).count());
  }

}
