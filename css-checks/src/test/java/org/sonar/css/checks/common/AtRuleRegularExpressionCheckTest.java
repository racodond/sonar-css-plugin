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

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

public class AtRuleRegularExpressionCheckTest {

  private final AtRuleRegularExpressionCheck check = new AtRuleRegularExpressionCheck();

  @Test
  public void test_css() {
    check.setRegularExpression("(?i)(charset|font-face)");
    check.setMessage("Remove this @-rule...");

    CssCheckVerifier.verifyCssFile(check, getTestFile("atRuleRegularExpression.css"));
  }

  @Test
  public void test_less() {
    check.setRegularExpression("(?i)(charset|font-face)");
    check.setMessage("Remove this @-rule...");

    CssCheckVerifier.verifyLessFile(check, getTestFile("atRuleRegularExpression.less"));
  }

  @Test
  public void test_scss() {
    check.setRegularExpression("(?i)(charset|font-face)");
    check.setMessage("Remove this @-rule...");

    CssCheckVerifier.verifyScssFile(check, getTestFile("atRuleRegularExpression.scss"));
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_regular_expression_parameter_is_not_valid() {
    try {
      check.setRegularExpression("(");
      check.setMessage("blabla");

      CssCheckVerifier.issuesOnCssFile(check, getTestFile("atRuleRegularExpression.css")).noMore();

    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check css:at-rule-regular-expression (Regular expression on @-rule): "
        + "regularExpression parameter \"(\" is not a valid regular expression.");
    }
  }

  private File getTestFile(String fileName) {
    return CheckTestUtils.getCommonTestFile("at-rule-regular-expression/" + fileName);
  }

}
