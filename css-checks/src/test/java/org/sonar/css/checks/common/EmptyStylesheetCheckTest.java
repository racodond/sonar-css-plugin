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

import java.io.File;

public class EmptyStylesheetCheckTest {

  private static final String MESSAGE = "Remove this empty stylesheet.";
  private EmptyStylesheetCheck check = new EmptyStylesheetCheck();

  @Test
  public void should_raise_an_issue_on_empty_css_stylesheet_with_comment() {
    CssCheckVerifier.issuesOnCssFile(check, getTestFile("emptyStylesheet.css"))
      .next().withMessage(MESSAGE)
      .noMore();
  }

  @Test
  public void should_raise_an_issue_on_empty_css_stylesheet_with_no_comment() {
    CssCheckVerifier.issuesOnCssFile(check, getTestFile("emptyStylesheetNoComment.css"))
      .next().withMessage(MESSAGE)
      .noMore();
  }

  @Test
  public void should_raise_some_issues_on_empty_css_embedded_in_html_style_tags() {
    CssCheckVerifier.verifyEmbeddedCssFile(check, getTestFile("emptyCssStyleTags.html"));
  }

  @Test
  public void should_raise_an_issue_on_empty_scss_stylesheet() {
    CssCheckVerifier.issuesOnScssFile(check, getTestFile("emptyStylesheet.scss"))
      .next().withMessage(MESSAGE)
      .noMore();
  }

  @Test
  public void should_raise_an_issue_on_empty_less_stylesheet() {
    CssCheckVerifier.issuesOnLessFile(check, getTestFile("emptyStylesheet.less"))
      .next().withMessage(MESSAGE)
      .noMore();
  }

  @Test
  public void should_not_raise_any_issue_on_non_empty_css_stylesheet() {
    CssCheckVerifier.verifyCssFile(check, getTestFile("nonEmptyStylesheet.css"));
  }

  @Test
  public void should_not_raise_any_issue_on_non_empty_css_embedded_in_html_style_tags() {
    CssCheckVerifier.verifyEmbeddedCssFile(check, getTestFile("nonEmptyCssStyleTags.html"));
  }

  @Test
  public void should_not_raise_any_issue_on_non_empty_scss_stylesheet() {
    CssCheckVerifier.verifyScssFile(check, getTestFile("nonEmptyStylesheet.scss"));
  }

  @Test
  public void should_not_raise_any_issue_on_non_empty_less_stylesheet() {
    CssCheckVerifier.verifyLessFile(check, getTestFile("nonEmptyStylesheet.less"));
  }

  private File getTestFile(String fileName) {
    return CheckTestUtils.getCommonTestFile("empty-stylesheet/" + fileName);
  }

}
