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

import static org.fest.assertions.Assertions.assertThat;

public class ExperimentalAtRuleCheckTest {

  @Test
  public void test_css() {
    CssCheckVerifier.verifyCssFile(new ExperimentalAtRuleCheck(), getTestFile("experimentalAtRuleUsage.css"));
  }

  @Test
  public void test_css_exclude_at_rules() {
    ExperimentalAtRuleCheck check = new ExperimentalAtRuleCheck();
    check.setAtRulesToExclude("custom-media|count.*");
    CssCheckVerifier.verifyCssFile(check, getTestFile("experimentalAtRuleUsageExcludeAtRules.css"));
  }

  @Test
  public void test_less() {
    CssCheckVerifier.verifyLessFile(new ExperimentalAtRuleCheck(), getTestFile("experimentalAtRuleUsage.less"));
  }

  @Test
  public void test_less_exclude_at_rules() {
    ExperimentalAtRuleCheck check = new ExperimentalAtRuleCheck();
    check.setAtRulesToExclude("custom-media|count.*");
    CssCheckVerifier.verifyLessFile(check, getTestFile("experimentalAtRuleUsageExcludeAtRules.less"));
  }

  @Test
  public void test_scss() {
    CssCheckVerifier.verifyScssFile(new ExperimentalAtRuleCheck(), getTestFile("experimentalAtRuleUsage.scss"));
  }

  @Test
  public void test_scss_exclude_at_rules() {
    ExperimentalAtRuleCheck check = new ExperimentalAtRuleCheck();
    check.setAtRulesToExclude("custom-media|count.*");
    CssCheckVerifier.verifyScssFile(check, getTestFile("experimentalAtRuleUsageExcludeAtRules.scss"));
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_atRulesToExclude_parameter_is_not_valid() {
    try {
      ExperimentalAtRuleCheck check = new ExperimentalAtRuleCheck();
      check.setAtRulesToExclude("(");
      CssCheckVerifier.issuesOnCssFile(check, getTestFile("experimentalAtRuleUsage.css")).noMore();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check css:experimental-atrule-usage (Experimental @-rules should not be used): "
        + "atRulesToExclude parameter \"(\" is not a valid regular expression.");
    }
  }

  private File getTestFile(String fileName) {
    return CheckTestUtils.getCommonTestFile("experimental-atrule-usage/" + fileName);
  }


}
