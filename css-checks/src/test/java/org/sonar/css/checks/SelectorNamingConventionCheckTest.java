/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende and David RACODON
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
import java.text.MessageFormat;

public class SelectorNamingConventionCheckTest {

  private static final String MESSAGE = "Rename selector {0} to match the regular expression: {1}";
  private static final String FORMAT = "^[a-z][-a-z0-9]*$";
  private SelectorNamingConventionCheck check = new SelectorNamingConventionCheck();

  @Test
  public void should_find_some_badly_named_selectors_and_raise_issues() {
    check.setFormat(FORMAT);
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/selectorNamingConvention.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(10).withMessage(MessageFormat.format(MESSAGE, "MYbOx", FORMAT)).next()
      .atLine(13).withMessage(MessageFormat.format(MESSAGE, "ab_cd", FORMAT)).next()
      .atLine(22).withMessage(MessageFormat.format(MESSAGE, "-rrr", FORMAT)).noMore();
  }
}
