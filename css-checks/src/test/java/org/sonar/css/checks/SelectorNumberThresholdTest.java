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
import java.text.MessageFormat;

public class SelectorNumberThresholdTest {

  @Test
  public void test() {
    SelectorNumberThreshold check = new SelectorNumberThreshold();
    SourceFile file = CssAstScanner.scanSingleFile(new File(
      "src/test/resources/checks/S2732.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .withMessage(MessageFormat.format("Reduce the number of selectors. This sheet contains {0,number,integer} selectors, {1,number,integer} more than the {2,number,integer} maximum.", 4097, 2, 4095))
      .noMore();
  }

}
