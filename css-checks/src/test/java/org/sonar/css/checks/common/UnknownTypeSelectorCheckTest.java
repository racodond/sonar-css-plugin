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

public class UnknownTypeSelectorCheckTest {

  @Test
  public void test_css() {
    CssCheckVerifier.verifyCssFile(new UnknownTypeSelectorCheck(), getTestFile("unknownTypeSelector.css"));
  }

  @Test
  public void test_less() {
    CssCheckVerifier.verifyLessFile(new UnknownTypeSelectorCheck(), getTestFile("unknownTypeSelector.less"));
  }

  @Test
  public void test_scss() {
    CssCheckVerifier.verifyScssFile(new UnknownTypeSelectorCheck(), getTestFile("unknownTypeSelector.scss"));
  }

  @Test
  public void test_css_custom_type_selector() {
    UnknownTypeSelectorCheck check = new UnknownTypeSelectorCheck();
    check.setExclusions("custom-.*");
    CssCheckVerifier.verifyCssFile(check, getTestFile("unknownTypeSelectorCustom.css"));
  }

  @Test
  public void test_less_custom_type_selector() {
    UnknownTypeSelectorCheck check = new UnknownTypeSelectorCheck();
    check.setExclusions("custom-.*");
    CssCheckVerifier.verifyLessFile(check, getTestFile("unknownTypeSelectorCustom.less"));
  }

  @Test
  public void test_scss_custom_type_selector() {
    UnknownTypeSelectorCheck check = new UnknownTypeSelectorCheck();
    check.setExclusions("custom-.*");
    CssCheckVerifier.verifyScssFile(check, getTestFile("unknownTypeSelectorCustom.scss"));
  }

  @Test
  public void should_throw_an_illegal_state_exception_as_the_exclusions_parameter_is_not_valid() {
    try {
      UnknownTypeSelectorCheck check = new UnknownTypeSelectorCheck();
      check.setExclusions("(");

      CssCheckVerifier.issuesOnCssFile(check, getTestFile("unknownTypeSelector.css")).noMore();

    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).isEqualTo("Check css:unknown-type-selector (Unknown type selectors "
        + "should be removed): exclusions parameter \"(\" is not a valid regular expression.");
    }
  }

  private File getTestFile(String fileName) {
    return CheckTestUtils.getCommonTestFile("unknown-type-selector/" + fileName);
  }

}
