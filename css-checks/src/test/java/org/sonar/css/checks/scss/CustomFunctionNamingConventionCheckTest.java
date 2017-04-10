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
package org.sonar.css.checks.scss;

import org.junit.Test;
import org.sonar.css.checks.CheckTestUtils;
import org.sonar.css.checks.verifier.CssCheckVerifier;

import static org.fest.assertions.Assertions.assertThat;

public class CustomFunctionNamingConventionCheckTest {

  private final CustomFunctionNamingConventionCheck check = new CustomFunctionNamingConventionCheck();

  @Test
  public void test_with_default_format() {
    check.setFormat("^[a-z][-a-z0-9]*$");
    CssCheckVerifier.verifyScssFile(check, CheckTestUtils.getScssTestFile("custom-function-naming-convention/customFunctionNamingConvention.scss"));
  }

  @Test
  public void test_with_custom_format() {
    check.setFormat("^[-a-z]+$");
    CssCheckVerifier.verifyScssFile(check, CheckTestUtils.getScssTestFile("custom-function-naming-convention/customFunctionNamingConventionCustomFormat.scss"));
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_format_parameter_is_not_valid() {
    try {
      check.setFormat("(");

      CssCheckVerifier.issuesOnScssFile(check, CheckTestUtils.getScssTestFile("custom-function-naming-convention/customFunctionNamingConvention.scss")).noMore();

    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check scss:custom-function-naming-convention (Custom functions should follow a naming convention): "
        + "format parameter \"(\" is not a valid regular expression.");
    }
  }

}
