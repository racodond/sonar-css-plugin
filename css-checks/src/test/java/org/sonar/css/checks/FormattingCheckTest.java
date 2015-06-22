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

import java.io.File;

import org.junit.Test;
import org.sonar.css.CssAstScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

public class FormattingCheckTest {

  private FormattingCheck check = new FormattingCheck();

  @Test
  public void block() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/formatting/block.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage("Move the opening curly brace to the previous line.").next()
      .atLine(10).withMessage("Move the code following the opening curly brace to the next line.").next()
      .atLine(11).withMessage("Move the code following the closing curly brace to the next line.").next()
      .atLine(16).withMessage("Move this closing curly brace to the next line.").noMore();
  }

  @Test
  public void declaration() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/formatting/declaration.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage("Leave only one whitespace between the colon and the value.").next()
      .atLine(10).withMessage("Leave only one whitespace between the colon and the value.").next()
      .atLine(14).withMessage("Remove the whitespaces between the property and the colon.").next()
      .atLine(18).withMessage("Remove the whitespaces between the property and the colon.").next()
      .atLine(22).withMessage("Move the property, colon and value to the same line.").next()
      .atLine(24).withMessage("Move the property, colon and value to the same line.").next()
      .atLine(26).withMessage("Move the property, colon and value to the same line.").noMore();
  }

  @Test
  public void important() {
    String MESSAGE = "Remove the whitespaces between \"!\" and \"important\".";
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/importantPosition.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(3).withMessage(MESSAGE).next()
      .atLine(4).withMessage(MESSAGE).noMore();
  }

}
