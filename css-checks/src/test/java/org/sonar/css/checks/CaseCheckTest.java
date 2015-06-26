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

public class CaseCheckTest {

  private CaseCheck check = new CaseCheck();

  @Test
  public void should_contain_uppercase_properties_function_and_variables() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/case.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(3).withMessage("Write property \"COLOR\" in lowercase.").next()
      .atLine(4).withMessage("Write property \"Color\" in lowercase.").next()
      .atLine(5).withMessage("Write property \"CoLor\" in lowercase.").next()
      .atLine(7).withMessage("Write property \"Color\" in lowercase.").next()
      .atLine(9).withMessage("Write property \"Background-color\" in lowercase.").next()
      .atLine(11).withMessage("Write function \"RGB\" in lowercase.").next()
      .atLine(12).withMessage("Write variable \"ABC\" in lowercase.").next()
      .atLine(13).withMessage("Write variable \"abc-DEF\" in lowercase.").noMore();
  }

}
