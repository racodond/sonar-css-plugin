/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende and David RACODON
 * kende.tamas@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.css.checks;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;
import org.sonar.css.checks.verifier.CssCheckVerifier;

public class ParsingErrorCheckTest {

  private ParsingErrorCheck check = new ParsingErrorCheck();

  @Test
  @Ignore
  // FIXME: how to test parsing error location?
  public void should_find_a_parsing_error() {
    CssCheckVerifier.verify(check, new File("src/test/resources/checks/parsingError.css"));
  }

  @Test
  public void should_not_find_any_parsing_error() {
    CssCheckVerifier.verify(check, new File("src/test/resources/checks/noParsingError.css"));
  }

}
