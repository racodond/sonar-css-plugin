/*
 * SonarQube CSS Plugin
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
package org.sonar.css.checks;

import com.google.common.base.Preconditions;

import java.io.File;

import junit.framework.Assert;
import org.junit.Test;
import org.sonar.css.checks.verifier.CssCheckVerifier;

public class ValidatePropertyValueCheckTest {

  @Test
  public void test_validate_properties() {
    File testDirectory = new File("src/test/resources/checks/properties");
    testDirectory = Preconditions.checkNotNull(testDirectory);
    for (File file : testDirectory.listFiles()) {
      CssCheckVerifier.verify(new ValidatePropertyValueCheck(), file);
    }
  }

  @Test
  public void test_number_of_validated_properties() {
    File testDirectory = new File("src/test/resources/checks/properties");
    testDirectory = Preconditions.checkNotNull(testDirectory);
    Assert.assertEquals(249, testDirectory.listFiles().length);
  }

}
