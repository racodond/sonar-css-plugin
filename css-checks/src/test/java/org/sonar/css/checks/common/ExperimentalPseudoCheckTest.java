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

public class ExperimentalPseudoCheckTest {

  @Test
  public void test_css() {
    CssCheckVerifier.verifyCssFile(new ExperimentalPseudoCheck(), getTestFile("experimentalPseudoUsage.css"));
  }

  @Test
  public void test_css_exclude_pseudos() {
    ExperimentalPseudoCheck check = new ExperimentalPseudoCheck();
    check.setPseudosToExclude("any-link|cont.*");
    CssCheckVerifier.verifyCssFile(check, getTestFile("experimentalPseudoUsageExcludePseudos.css"));
  }

  @Test
  public void test_less() {
    CssCheckVerifier.verifyLessFile(new ExperimentalPseudoCheck(), getTestFile("experimentalPseudoUsage.less"));
  }

  @Test
  public void test_less_exclude_pseudos() {
    ExperimentalPseudoCheck check = new ExperimentalPseudoCheck();
    check.setPseudosToExclude("any-link|cont.*");
    CssCheckVerifier.verifyLessFile(check, getTestFile("experimentalPseudoUsageExcludePseudos.less"));
  }

  @Test
  public void test_scss() {
    CssCheckVerifier.verifyScssFile(new ExperimentalPseudoCheck(), getTestFile("experimentalPseudoUsage.scss"));
  }

  @Test
  public void test_scss_exclude_pseudos() {
    ExperimentalPseudoCheck check = new ExperimentalPseudoCheck();
    check.setPseudosToExclude("any-link|cont.*");
    CssCheckVerifier.verifyScssFile(check, getTestFile("experimentalPseudoUsageExcludePseudos.scss"));
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_pseudosToExclude_parameter_is_not_valid() {
    try {
      ExperimentalPseudoCheck check = new ExperimentalPseudoCheck();
      check.setPseudosToExclude("(");
      CssCheckVerifier.issuesOnCssFile(check, getTestFile("experimentalPseudoUsage.css")).noMore();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check css:experimental-pseudo-usage (Experimental pseudo-elements and pseudo-classes should not be used): "
        + "pseudosToExclude parameter \"(\" is not a valid regular expression.");
    }
  }

  private File getTestFile(String fileName) {
    return CheckTestUtils.getCommonTestFile("experimental-pseudo-usage/" + fileName);
  }

}
