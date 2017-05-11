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

import junit.framework.Assert;
import org.junit.Test;
import org.sonar.css.checks.CheckTestUtils;
import org.sonar.css.checks.verifier.CssCheckVerifier;

import java.io.File;

public class ValidatePropertyValueCheckTest {

  private ValidatePropertyValueCheck check = new ValidatePropertyValueCheck();

  @Test
  public void test_css_validate_properties() {
    for (File file : CheckTestUtils.getCommonTestFile("properties/css").listFiles()) {
      CssCheckVerifier.verifyCssFile(check, file);
    }
  }

  @Test
  public void test_less_validate_properties() {
    for (File file : CheckTestUtils.getCommonTestFile("properties/less").listFiles()) {
      CssCheckVerifier.verifyLessFile(check, file);
    }
  }

  @Test
  public void test_scss_validate_properties() {
    for (File file : CheckTestUtils.getCommonTestFile("properties/scss").listFiles()) {
      CssCheckVerifier.verifyScssFile(check, file);
    }
  }

  @Test
  public void test_ignore_value_with_less_elements() {
    CssCheckVerifier.verifyLessFile(
      check,
      CheckTestUtils.getCommonTestFile("validate-property-value/validatePropertyValue.less"));
  }

  @Test
  public void test_ignore_value_with_scss_elements() {
    CssCheckVerifier.verifyScssFile(
      check,
      CheckTestUtils.getCommonTestFile("validate-property-value/validatePropertyValue.scss"));
  }

  @Test
  public void test_ignore_scss_nested_properties() {
    CssCheckVerifier.verifyScssFile(
      check,
      CheckTestUtils.getCommonTestFile("validate-property-value/validateNestedProperties.scss"));
  }

  @Test
  public void test_number_of_validated_properties() {
    Assert.assertEquals(309, CheckTestUtils.getCommonTestFile("properties/css").listFiles().length);
    Assert.assertEquals(309, CheckTestUtils.getCommonTestFile("properties/less").listFiles().length);
    Assert.assertEquals(309, CheckTestUtils.getCommonTestFile("properties/scss").listFiles().length);
  }

}
