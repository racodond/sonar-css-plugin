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
package org.sonar.css.checks.common;

import org.junit.Test;
import org.sonar.css.checks.CheckTestUtils;
import org.sonar.css.checks.verifier.CssCheckVerifier;

import java.io.File;

public class MissingNewLineAtEndOfFileCheckTest {

  private MissingNewLineAtEndOfFileCheck check = new MissingNewLineAtEndOfFileCheck();

  @Test
  public void should_have_an_empty_new_line_at_the_end_of_the_file_and_not_raise_any_issue() {
    CssCheckVerifier.issuesOnCssFile(check, getTestFile("newLineEndOfFile.css"))
      .noMore();
  }

  @Test
  public void should_not_have_an_empty_new_line_at_the_end_of_the_file_and_raise_an_issue() {
    CssCheckVerifier.issuesOnCssFile(check, getTestFile("noNewLineEndOfFile.css"))
      .next().withMessage("Add an empty new line at the end of this file.")
      .noMore();
  }

  @Test
  public void should_not_raise_any_issue_on_empty_file() {
    CssCheckVerifier.issuesOnCssFile(check, getTestFile("noNewLineEndOfEmptyFile.css"))
      .noMore();
  }

  private File getTestFile(String fileName) {
    return CheckTestUtils.getCommonTestFile("empty-line-end-of-file/" + fileName);
  }

}
