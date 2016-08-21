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
package org.sonar.css.checks;

import com.google.common.base.Charsets;
import org.junit.Test;
import org.sonar.css.checks.verifier.CssCheckVerifier;

public class BOMCheckTest {

  @Test
  public void should_find_that_the_UTF8_file_starts_with_a_BOM_and_raise_an_issue() {
    CssCheckVerifier.verify(new BOMCheck(), CheckTestUtils.getTestFile("utf8WithBom.css"));
  }

  @Test
  public void should_find_that_the_UTF8_file_does_not_start_with_a_BOM_and_not_raise_any_issue() {
    CssCheckVerifier.verify(new BOMCheck(), CheckTestUtils.getTestFile("utf8WithoutBom.css"));
  }

  @Test
  public void should_find_that_the_UTF16_files_start_with_a_BOM_but_not_raise_any_issue() {
    CssCheckVerifier.verify(
      new BOMCheck(),
      CheckTestUtils.getTestFile("utf16BE.css"),
      Charsets.UTF_16BE);

    CssCheckVerifier.verify(
      new BOMCheck(),
      CheckTestUtils.getTestFile("utf16LE.css"),
      Charsets.UTF_16LE);
  }

}
