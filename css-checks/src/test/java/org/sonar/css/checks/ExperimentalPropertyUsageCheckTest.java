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

public class ExperimentalPropertyUsageCheckTest {

  private final static String MESSAGE = "Remove the usage of this experimental property";
  private ExperimentalPropertyUsageCheck check = new ExperimentalPropertyUsageCheck();

  @Test
  public void should_contain_experimental_properties_and_raise_issues() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/experimentalPropertyUsage.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(2).withMessage(MESSAGE).next()
      .atLine(7).withMessage(MESSAGE).next()
      .atLine(11).withMessage(MESSAGE).next()
      .atLine(12).withMessage(MESSAGE).next()
      .atLine(13).withMessage(MESSAGE).next()
      .atLine(14).withMessage(MESSAGE).next()
      .atLine(15).withMessage(MESSAGE).next()
      .atLine(16).withMessage(MESSAGE).next()
      .atLine(17).withMessage(MESSAGE).next()
      .atLine(18).withMessage(MESSAGE).next()
      .atLine(19).withMessage(MESSAGE).next()
      .atLine(20).withMessage(MESSAGE).next()
      .atLine(21).withMessage(MESSAGE).next()
      .atLine(22).withMessage(MESSAGE).next()
      .atLine(23).withMessage(MESSAGE).next()
      .atLine(24).withMessage(MESSAGE).next()
      .atLine(25).withMessage(MESSAGE).next()
      .atLine(26).withMessage(MESSAGE).noMore();
  }

  @Test
  public void should_not_contain_experimental_properties_and_not_raise_issues() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/emptyRule.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).noMore();
  }

}
