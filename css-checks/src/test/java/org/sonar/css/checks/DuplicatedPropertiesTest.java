/*
 * Sonar CSS Plugin
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

import org.sonar.css.CssAstScanner;

import com.sonar.sslr.squid.checks.CheckMessagesVerifier;
import org.junit.Test;
import org.sonar.squid.api.SourceFile;

import java.io.File;

public class DuplicatedPropertiesTest {

  @Test
  public void test() {
    DuplicateProperties check = new DuplicateProperties();
    SourceFile file = CssAstScanner.scanSingleFile(new File(
        "src/test/resources/checks/duplicatedProperties.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
        .atLine(7).withMessage("Duplicated property in the declarations").next()
        .atLine(20).withMessage("Duplicated property in the declarations").noMore();
  }

}
