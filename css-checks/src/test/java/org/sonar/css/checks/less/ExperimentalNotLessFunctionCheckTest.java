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
package org.sonar.css.checks.less;

import org.junit.Test;
import org.sonar.css.checks.CheckTestUtils;
import org.sonar.css.checks.css.ExperimentalCssFunctionCheck;
import org.sonar.css.checks.verifier.CssCheckVerifier;

import static org.fest.assertions.Assertions.assertThat;

public class ExperimentalNotLessFunctionCheckTest {

  @Test
  public void test() {
    CssCheckVerifier.verifyLessFile(new ExperimentalNotLessFunctionCheck(), CheckTestUtils.getLessTestFile("experimentalFunctionUsage.less"));
  }

  @Test
  public void test_exclude_functions() {
    ExperimentalNotLessFunctionCheck check = new ExperimentalNotLessFunctionCheck();
    check.setFunctionsToExclude("conic-gradient|count.*");
    CssCheckVerifier.verifyLessFile(check, CheckTestUtils.getLessTestFile("experimentalFunctionUsageExcludeFunctions.less"));
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_functionsToExclude_parameter_is_not_valid() {
    try {
      ExperimentalNotLessFunctionCheck check = new ExperimentalNotLessFunctionCheck();
      check.setFunctionsToExclude("(");
      CssCheckVerifier.issuesOnLessFile(check, CheckTestUtils.getLessTestFile("experimentalFunctionUsage.less")).noMore();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check less:experimental-not-less-function-usage (Experimental functions should not be used): "
        + "functionsToExclude parameter \"(\" is not a valid regular expression.");
    }
  }

}
