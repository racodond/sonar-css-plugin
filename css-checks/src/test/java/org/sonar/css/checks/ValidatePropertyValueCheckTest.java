/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende
 * kende.tamas@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.css.checks;

import org.junit.Test;
import org.sonar.css.CssAstScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

public class ValidatePropertyValueCheckTest {

  private ValidatePropertyValueCheck check = new ValidatePropertyValueCheck();

  @Test
  public void should_find_some_invalid_values_and_raise_issues() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/validatePropertyValue.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage("Replace the invalid value abc of property float").next()
      .atLine(21).withMessage("Replace the invalid value 10 of property word-spacing").next()
      .atLine(22).withMessage("Replace the invalid value abc of property word-spacing").next()
      .atLine(36).withMessage("Replace the invalid value abc of property z-index").next()
      .atLine(37).withMessage("Replace the invalid value 0.1 of property z-index").next()
      .atLine(38).withMessage("Replace the invalid value -50.1 of property z-index").next()
      .atLine(52).withMessage("Replace the invalid value abc of property line-height").next()
      .atLine(69).withMessage("Replace the invalid value abc of property width").next()
      .atLine(78).withMessage("Replace the invalid value http of property list-style-image").next()
      .atLine(79).withMessage("Replace the invalid value abc of property list-style-image").noMore();
  }

}
