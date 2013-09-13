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

public class AllGradientDefinitionsTest {

  @Test
  public void test() {
    AllGradientDefinitions check = new AllGradientDefinitions();
    SourceFile file = CssAstScanner.scanSingleFile(new File(
        "src/test/resources/checks/gradientdefinition.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
    .atLine(2).withMessage("Missing gradient: -moz-(linear|radial)-gradient.*").next()
    .atLine(2).withMessage("Missing gradient: -ms-(linear|radial)-gradient.*").next()
    .atLine(2).withMessage("Missing gradient: -o-(linear|radial)-gradient.*").next()
    .atLine(8).withMessage("Missing gradient: -webkit-(linear|radial)-gradient.*").next()
    .atLine(8).withMessage("Missing gradient: -webkit-gradient.*")
    .noMore();
  }

}
