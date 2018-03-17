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

public class FontFamilyNotEndingWithGenericFontFamilyCheckTest {

  @Test
  public void test_css() {
    FontFamilyNotEndingWithGenericFontFamilyCheck check = new FontFamilyNotEndingWithGenericFontFamilyCheck();
    CssCheckVerifier.verifyCssFile(check, getTestFile("fontFamilyNotEndingWithGenericFontFamily.css"));
  }

  @Test
  public void test_less() {
    FontFamilyNotEndingWithGenericFontFamilyCheck check = new FontFamilyNotEndingWithGenericFontFamilyCheck();
    CssCheckVerifier.verifyLessFile(check, getTestFile("fontFamilyNotEndingWithGenericFontFamily.less"));
  }

  @Test
  public void test_scss() {
    FontFamilyNotEndingWithGenericFontFamilyCheck check = new FontFamilyNotEndingWithGenericFontFamilyCheck();
    CssCheckVerifier.verifyScssFile(check, getTestFile("fontFamilyNotEndingWithGenericFontFamily.scss"));
  }

  @Test
  public void test_with_default_exclusions() {
    FontFamilyNotEndingWithGenericFontFamilyCheck check = new FontFamilyNotEndingWithGenericFontFamilyCheck();
    CssCheckVerifier.verifyCssFile(check, getTestFile("fontFamilyNotEndingWithGenericFontFamilyDefaultExclusions.css"));
  }

  @Test
  public void test_with_custom_format() {
    FontFamilyNotEndingWithGenericFontFamilyCheck check = new FontFamilyNotEndingWithGenericFontFamilyCheck();
    check.setExclusions("^My-.+$");
    CssCheckVerifier.verifyCssFile(check, getTestFile("fontFamilyNotEndingWithGenericFontFamilyCustomExclusions.css"));
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_exclusions_parameter_is_not_valid() {
    try {
      FontFamilyNotEndingWithGenericFontFamilyCheck check = new FontFamilyNotEndingWithGenericFontFamilyCheck();
      check.setExclusions("(");

      CssCheckVerifier.issuesOnCssFile(check, getTestFile("fontFamilyNotEndingWithGenericFontFamily.css")).noMore();

    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check css:font-family-not-ending-with-generic-font-family (font-family properties should end with a generic font family): "
        + "exclusions parameter \"(\" is not a valid regular expression.");
    }
  }

  private File getTestFile(String fileName) {
    return CheckTestUtils.getCommonTestFile("font-family-not-ending-with-generic-font-family/" + fileName);
  }

}
