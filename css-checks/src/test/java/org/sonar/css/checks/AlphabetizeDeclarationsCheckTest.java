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

public class AlphabetizeDeclarationsCheckTest {

  private final static String MESSAGE = "Alphabetically order these rule's properties";
  private AlphabetizeDeclarationsCheck check = new AlphabetizeDeclarationsCheck();

  @Test
  public void properties_should_not_be_properly_ordered() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/alphabetizeDeclarations.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(54).withMessage(MESSAGE).next()
      .atLine(61).withMessage(MESSAGE).next()
      .atLine(68).withMessage(MESSAGE).next()
      .atLine(75).withMessage(MESSAGE).noMore();
  }

}
