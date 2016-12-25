/*
 * SonarQube CSS / SCSS / Less Analyzer
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
package org.sonar.css.checks.common;

import org.junit.Test;
import org.sonar.css.checks.CheckTestUtils;
import org.sonar.css.checks.verifier.CssCheckVerifier;

public class LineLengthCheckTest {

  private final LineLengthCheck check = new LineLengthCheck();

  @Test
  public void should_not_find_any_line_longer_than_the_default_value_120() {
    CssCheckVerifier.verifyCssFile(check, CheckTestUtils.getCommonTestFile("lineLength.css"));
  }

  @Test
  public void should_find_one_line_longer_than_50_characters_and_raise_an_issue() {
    check.setMaximumLineLength(50);
    CssCheckVerifier.verifyCssFile(check, CheckTestUtils.getCommonTestFile("lineLength50.css"));
  }

  @Test
  public void should_find_two_lines_longer_than_30_characters_and_raise_issues() {
    check.setMaximumLineLength(30);
    CssCheckVerifier.verifyCssFile(check, CheckTestUtils.getCommonTestFile("lineLength30.css"));
  }

}
