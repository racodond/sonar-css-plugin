/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013-2016 Tamas Kende and David RACODON
 * mailto: kende.tamas@gmail.com and david.racodon@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.css.checks;

import java.io.File;

import org.junit.Test;
import org.sonar.api.utils.SonarException;
import org.sonar.css.CssAstScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

public class CommentRegularExpressionCheckTest {

  private static final File FILE = new File("src/test/resources/checks/commentRegularExpression.css");
  private final CommentRegularExpressionCheck check = new CommentRegularExpressionCheck();

  @Test
  public void should_match_some_comments_and_raise_issues() {
    String message = "Stop annotating lines with WTF! Detail what is wrong instead.";
    check.regularExpression = "(?i).*WTF.*";
    check.message = message;
    SourceFile file = CssAstScanner.scanSingleFile(FILE, check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(1).withMessage(message).next()
      .atLine(3).withMessage(message).next()
      .atLine(4).withMessage(message).noMore();
  }

  @Test
  public void should_not_match_any_comments_and_not_raise_any_issues() {
    check.regularExpression = "blabla";
    check.message = "blabla";
    SourceFile file = CssAstScanner.scanSingleFile(FILE, check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).noMore();
  }

  @Test(expected = SonarException.class)
  public void should_throw_an_illegal_state_exception_as_the_regular_expression_parameter_regular_expression_is_not_valid() {
    check.regularExpression = "(";
    check.message = "blabla";
    CssAstScanner.scanSingleFile(FILE, check);
  }

}
