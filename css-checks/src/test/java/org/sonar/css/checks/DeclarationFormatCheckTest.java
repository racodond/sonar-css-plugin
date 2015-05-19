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

public class DeclarationFormatCheckTest {

  private final static String MESSAGE_ADD = "Add one whitespace between the property colon and its value.";
  private final static String MESSAGE_REMOVE = "Remove all whitespaces but one between the property colon and its value.";
  private final static String MESSAGE_SAME_LINE = "Define the property and its value on the same line.";
  private DeclarationFormatCheck check = new DeclarationFormatCheck();

  @Test
  public void should_raise_issues() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/declarationFormat.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage(MESSAGE_ADD).next()
      .atLine(10).withMessage(MESSAGE_ADD).next()
      .atLine(11).withMessage(MESSAGE_ADD).next()
      .atLine(15).withMessage(MESSAGE_REMOVE).next()
      .atLine(16).withMessage(MESSAGE_REMOVE).next()
      .atLine(20).withMessage(MESSAGE_SAME_LINE).next()
      .atLine(22).withMessage(MESSAGE_SAME_LINE).noMore();
  }

}
