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

public class LineLengthCheckTest {

  private final String PATH = "src/test/resources/checks/lineLength.css";
  private LineLengthCheck check = new LineLengthCheck();

  @Test
  public void should_not_find_any_line_longer_than_the_default_value_120() {
    SourceFile file = CssAstScanner.scanSingleFile(new File(PATH), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).noMore();
  }

  @Test
  public void should_find_one_line_longer_than_50_characters_and_raise_an_issue() {
    check.setMaximumLineLength(50);
    SourceFile file = CssAstScanner.scanSingleFile(new File(PATH), check);
    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().atLine(2).withMessage("The line contains 51 characters which is greater than 50 authorized.")
      .noMore();
  }

  @Test
  public void should_find_two_lines_longer_than_30_characters_and_raise_issues() {
    check.setMaximumLineLength(30);
    SourceFile file = CssAstScanner.scanSingleFile(new File(PATH), check);
    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().atLine(2).withMessage("The line contains 51 characters which is greater than 30 authorized.")
      .next().atLine(3).withMessage("The line contains 39 characters which is greater than 30 authorized.")
      .next().atLine(4).withMessage("The line contains 44 characters which is greater than 30 authorized.")
      .noMore();
  }

}
