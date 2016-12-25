/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 Tamas Kende and David RACODON
 * mailto: kende.tamas@gmail.com and david.racodon@gmail.com
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

import com.google.common.base.Preconditions;

import java.io.File;

import junit.framework.Assert;
import org.junit.Test;
import org.sonar.css.checks.CheckTestUtils;
import org.sonar.css.checks.verifier.CssCheckVerifier;

public class ValidatePropertyValueCheckTest {

  private static final File TEST_DIRECTORY = CheckTestUtils.getCommonTestFile("properties");

  @Test
  public void test_validate_properties() {
    Preconditions.checkNotNull(TEST_DIRECTORY);
    for (File file : TEST_DIRECTORY.listFiles()) {
      CssCheckVerifier.verifyCssFile(new ValidatePropertyValueCheck(), file);
    }
  }

  @Test
  public void test_validate_properties_ignore_value_with_less_elements() {
    CssCheckVerifier.verifyLessFile(
      new ValidatePropertyValueCheck(),
      CheckTestUtils.getCommonTestFile("validatePropertyValue.less"));
  }

  @Test
  public void test_number_of_validated_properties() {
    Preconditions.checkNotNull(TEST_DIRECTORY);
    Assert.assertEquals(300, TEST_DIRECTORY.listFiles().length);
  }

}
