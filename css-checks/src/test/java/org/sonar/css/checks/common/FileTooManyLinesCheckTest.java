/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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
package org.sonar.css.checks.common;

import org.junit.Test;
import org.sonar.css.checks.CheckTestUtils;
import org.sonar.css.checks.verifier.CssCheckVerifier;

public class FileTooManyLinesCheckTest {

  private FileTooManyLinesCheck check = new FileTooManyLinesCheck();

  @Test
  public void should_contain_more_lines_than_the_allowed_number_and_raise_an_issue() {
    check.setMax(9);
    CssCheckVerifier.verifyCssFile(check, CheckTestUtils.getCommonTestFile("file-too-many-lines/fileTooManyLines9.css"));
  }

  @Test
  public void should_contain_fewer_lines_than_the_allowed_number_and_not_raise_an_issue() {
    check.setMax(11);
    CssCheckVerifier.verifyCssFile(check, CheckTestUtils.getCommonTestFile("file-too-many-lines/fileTooManyLines10.css"));
  }

  @Test
  public void should_contain_the_same_number_of_lines_than_the_allowed_number_and_not_raise_an_issue() {
    check.setMax(10);
    CssCheckVerifier.verifyCssFile(check, CheckTestUtils.getCommonTestFile("file-too-many-lines/fileTooManyLines11.css"));
  }

}
