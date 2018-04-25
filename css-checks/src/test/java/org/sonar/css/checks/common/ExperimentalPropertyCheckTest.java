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

public class ExperimentalPropertyCheckTest {

  @Test
  public void test_css() {
    CssCheckVerifier.verifyCssFile(new ExperimentalPropertyCheck(), getTestFile("experimentalPropertyUsage.css"));
  }

  @Test
  public void test_css_exclude_properties() {
    ExperimentalPropertyCheck check = new ExperimentalPropertyCheck();
    check.setPropertiesToExclude("user-select|voice.*");
    CssCheckVerifier.verifyCssFile(check, getTestFile("experimentalPropertyUsageExcludeProperties.css"));
  }

  @Test
  public void test_less() {
    CssCheckVerifier.verifyLessFile(new ExperimentalPropertyCheck(), getTestFile("experimentalPropertyUsage.less"));
  }

  @Test
  public void test_less_exclude_properties() {
    ExperimentalPropertyCheck check = new ExperimentalPropertyCheck();
    check.setPropertiesToExclude("user-select|voice.*");
    CssCheckVerifier.verifyCssFile(check, getTestFile("experimentalPropertyUsageExcludeProperties.less"));
  }

  @Test
  public void test_scss() {
    CssCheckVerifier.verifyScssFile(new ExperimentalPropertyCheck(), getTestFile("experimentalPropertyUsage.scss"));
  }

  @Test
  public void test_scss_exclude_properties() {
    ExperimentalPropertyCheck check = new ExperimentalPropertyCheck();
    check.setPropertiesToExclude("user-select|voice.*");
    CssCheckVerifier.verifyCssFile(check, getTestFile("experimentalPropertyUsageExcludeProperties.scss"));
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_propertiesToExclude_parameter_is_not_valid() {
    try {
      ExperimentalPropertyCheck check = new ExperimentalPropertyCheck();
      check.setPropertiesToExclude("(");
      CssCheckVerifier.issuesOnCssFile(check, getTestFile("experimentalPropertyUsage.css")).noMore();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check css:experimental-property-usage (Experimental properties should not be used): "
        + "propertiesToExclude parameter \"(\" is not a valid regular expression.");
    }
  }

  private File getTestFile(String fileName) {
    return CheckTestUtils.getCommonTestFile("experimental-property-usage/" + fileName);
  }

}
