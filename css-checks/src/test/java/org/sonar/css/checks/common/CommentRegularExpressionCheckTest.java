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

import java.io.File;

import org.junit.Test;
import org.sonar.css.checks.CheckTestUtils;
import org.sonar.css.checks.verifier.CssCheckVerifier;

import static org.fest.assertions.Assertions.assertThat;

public class CommentRegularExpressionCheckTest {

  private final CommentRegularExpressionCheck check = new CommentRegularExpressionCheck();

  @Test
  public void should_match_some_comments_and_raise_issues() {
    String message = "Stop annotating lines with WTF! Detail what is wrong instead.";
    check.regularExpression = "(?i).*WTF.*";
    check.message = message;

    CssCheckVerifier.issuesOnCssFile(check, getTestFile("commentRegularExpression.css"))
      .next().atLine(1).withMessage(message)
      .next().atLine(3).withMessage(message)
      .next().atLine(4).withMessage(message)
      .noMore();
  }

  @Test
  public void should_not_match_any_comments_and_not_raise_any_issues() {
    check.regularExpression = "blabla";
    check.message = "blabla";

    CssCheckVerifier.issuesOnCssFile(check, getTestFile("commentRegularExpression.css")).noMore();
  }

  @Test
  public void should_match_some_comments_and_raise_issues_on_less_file() {
    String message = "Stop annotating lines with WTF! Detail what is wrong instead.";
    check.regularExpression = "(?i).*WTF.*";
    check.message = message;

    CssCheckVerifier.issuesOnLessFile(check, getTestFile("commentRegularExpression.less"))
      .next().atLine(1).withMessage(message)
      .next().atLine(3).withMessage(message)
      .next().atLine(4).withMessage(message)
      .next().atLine(5).withMessage(message)
      .noMore();
  }

  @Test
  public void should_match_some_comments_and_raise_issues_on_scss_file() {
    String message = "Stop annotating lines with WTF! Detail what is wrong instead.";
    check.regularExpression = "(?i).*WTF.*";
    check.message = message;

    CssCheckVerifier.issuesOnScssFile(check, getTestFile("commentRegularExpression.scss"))
      .next().atLine(1).withMessage(message)
      .next().atLine(3).withMessage(message)
      .next().atLine(4).withMessage(message)
      .next().atLine(5).withMessage(message)
      .noMore();
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_regular_expression_parameter_is_not_valid() {
    try {
      check.regularExpression = "(";
      check.message = "blabla";

      CssCheckVerifier.issuesOnCssFile(check, getTestFile("commentRegularExpression.css")).noMore();

    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check css:comment-regular-expression (Regular expression on comment): "
        + "regularExpression parameter \"(\" is not a valid regular expression.");
    }
  }

  private File getTestFile(String fileName) {
    return CheckTestUtils.getCommonTestFile("comment-regular-expression/" + fileName);
  }

}
