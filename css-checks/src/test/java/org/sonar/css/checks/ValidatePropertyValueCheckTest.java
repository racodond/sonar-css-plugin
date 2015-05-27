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
      .atLine(6).withMessage("Update the invalid value of property \"float\". Expected format: left | right | none | inherit").next()
      .atLine(21).withMessage("Update the invalid value of property \"word-spacing\". Expected format: normal | <length> | inherit").next()
      .atLine(22).withMessage("Update the invalid value of property \"word-spacing\". Expected format: normal | <length> | inherit").next()
      .atLine(36).withMessage("Update the invalid value of property \"z-index\". Expected format: auto | <integer> | inherit").next()
      .atLine(37).withMessage("Update the invalid value of property \"z-index\". Expected format: auto | <integer> | inherit").next()
      .atLine(38).withMessage("Update the invalid value of property \"z-index\". Expected format: auto | <integer> | inherit").next()
      .atLine(52).withMessage("Update the invalid value of property \"line-height\". Expected format: normal | <length> | <percentage> | <number> | inherit").next()
      .atLine(69).withMessage("Update the invalid value of property \"width\". Expected format: auto | <length> | <percentage> | inherit").next()
      .atLine(78).withMessage("Update the invalid value of property \"list-style-image\". Expected format: none | <uri> | inherit").next()
      .atLine(79).withMessage("Update the invalid value of property \"list-style-image\". Expected format: none | <uri> | inherit").noMore();
  }

}
