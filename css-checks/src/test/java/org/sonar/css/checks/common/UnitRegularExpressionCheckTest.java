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
package org.sonar.css.checks.common;

import org.junit.Test;
import org.sonar.css.checks.CheckTestUtils;
import org.sonar.css.checks.verifier.CssCheckVerifier;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

public class UnitRegularExpressionCheckTest {

  private final UnitRegularExpressionCheck check = new UnitRegularExpressionCheck();

  @Test
  public void test_css() {
    check.setRegularExpression("(?i)(em|rad)");
    check.setMessage("Remove this unit...");

    CssCheckVerifier.verifyCssFile(check, getTestFile("unitRegularExpression.css"));
  }

  @Test
  public void test_less() {
    check.setRegularExpression("(?i)(em|rad)");
    check.setMessage("Remove this unit...");

    CssCheckVerifier.verifyLessFile(check, getTestFile("unitRegularExpression.less"));
  }

  @Test
  public void test_scss() {
    check.setRegularExpression("(?i)(em|rad)");
    check.setMessage("Remove this unit...");

    CssCheckVerifier.verifyScssFile(check, getTestFile("unitRegularExpression.scss"));
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_regular_expression_parameter_is_not_valid() {
    try {
      check.setRegularExpression("(");
      check.setMessage("blabla");

      CssCheckVerifier.issuesOnCssFile(check, getTestFile("unitRegularExpression.css")).noMore();

    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check css:unit-regular-expression (Regular expression on unit): "
        + "regularExpression parameter \"(\" is not a valid regular expression.");
    }
  }

  private File getTestFile(String fileName) {
    return CheckTestUtils.getCommonTestFile("unit-regular-expression/" + fileName);
  }

}
