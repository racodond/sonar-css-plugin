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

public class MaximumNumberOfRulesPerSheetCheckTest {

  private MaximumNumberOfRulesPerSheetCheck check = new MaximumNumberOfRulesPerSheetCheck();

  @Test
  public void should_contain_more_rules_than_the_allowed_number_and_raise_an_issue() {
    check.setMax(9);
    SourceFile testFile = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/maximumNumberOfRulesPerSheet.css"),
        check);
    CheckMessagesVerifier.verify(testFile.getCheckMessages()).next()
      .withMessage("Reduce the number of rules. This sheet contains 10 rules, 1 more than the 9 allowed rules.")
      .noMore();
  }

  @Test
  public void should_contain_fewer_rules_than_the_allowed_number_and_not_raise_an_issue() {
    check.setMax(10);
    SourceFile testFile = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/maximumNumberOfRulesPerSheet.css"), check);
    CheckMessagesVerifier.verify(testFile.getCheckMessages()).noMore();
  }

  @Test
  public void should_contain_the_same_number_of_rules_than_the_allowed_number_and_not_raise_an_issue() {
    check.setMax(11);
    SourceFile testFile = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/maximumNumberOfRulesPerSheet.css"), check);
    CheckMessagesVerifier.verify(testFile.getCheckMessages()).noMore();
  }

}
