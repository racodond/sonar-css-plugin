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

public class BulletproofFontFaceTest {

  @Test
  public void test() {
    BulletproofFontFace check = new BulletproofFontFace();
    SourceFile file = CssAstScanner.scanSingleFile(new File(
        "src/test/resources/checks/fontface.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
    .atLine(1).withMessage("First web font has missing query string or it is not eot").next()
    .atLine(19).withMessage("First web font has missing query string or it is not eot")
    .noMore();
  }

}
