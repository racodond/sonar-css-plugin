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

public class SemicolonDeclarationCheckTest {

  private final static String MESSAGE = "Add a semicolon at the end of this declaration";
  private SemicolonDeclarationCheck check = new SemicolonDeclarationCheck();

  @Test
  public void should_find_missing_semicolons_at_the_end_of_some_declarations() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/semicolonDeclaration.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(18).withMessage(MESSAGE).next()
      .atLine(24).withMessage(MESSAGE).next()
      .atLine(31).withMessage(MESSAGE).next()
      .atLine(46).withMessage(MESSAGE).noMore();
  }

}
