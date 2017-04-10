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

import static org.fest.assertions.Assertions.assertThat;

public class FontFaceBrowserCompatibilityCheckTest {

  @Test
  public void basic() {
    CssCheckVerifier.verifyCssFile(new FontFaceBrowserCompatibilityCheck(), CheckTestUtils.getCommonTestFile("fontface/basic.css"));
  }

  @Test
  public void deep() {
    FontFaceBrowserCompatibilityCheck check = new FontFaceBrowserCompatibilityCheck();
    check.setBrowserSupportLevel("deep");
    CssCheckVerifier.verifyCssFile(check, CheckTestUtils.getCommonTestFile("fontface/deep.css"));
  }

  @Test
  public void deepest() {
    FontFaceBrowserCompatibilityCheck check = new FontFaceBrowserCompatibilityCheck();
    check.setBrowserSupportLevel("deepest");
    CssCheckVerifier.verifyCssFile(check, CheckTestUtils.getCommonTestFile("fontface/deepest.css"));
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_browserLevelSupport_parameter_is_not_valid() {
    try {
      FontFaceBrowserCompatibilityCheck check = new FontFaceBrowserCompatibilityCheck();
      check.setBrowserSupportLevel("blabla");

      CssCheckVerifier.issuesOnCssFile(check, CheckTestUtils.getCommonTestFile("fontface/deepest.css")).noMore();

    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check css:font-face-browser-compatibility (\"@font-face\" rule should "
        + "be made compatible with the required browsers): parameter value is not valid.\n"
        + "Actual: 'blabla'\n"
        + "Expected: 'basic' or 'deep' or 'deepest'");
    }
  }

}
