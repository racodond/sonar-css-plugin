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

import java.io.File;

import org.junit.Test;
import org.sonar.css.CssAstScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

public class FontFaceBrowserCompatibilityTest {

  @Test
  public void basic() {
    SourceFile file = CssAstScanner.scanSingleFile(
      new File("src/test/resources/checks/fontface/basic.css"),
      new FontFaceBrowserCompatibility());
    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().atLine(58).withMessage("URL for \"woff\" format is expected.")
      .next().atLine(63).withMessage("URL for \"woff2\" format is expected.")
      .next().atLine(68).withMessage("URL for \"woff2\" format is expected.")
      .next().atLine(73).withMessage("URL for \"woff2\" format is expected.")
      .next().atLine(80).withMessage("URL for \"woff\" format is expected.")
      .next().atLine(88).withMessage("URL for \"woff2\" format is expected.")
      .next().atLine(94).withMessage("URL for \"woff2\" format is expected.")
      .noMore();
  }

  @Test
  public void deep() {
    FontFaceBrowserCompatibility check = new FontFaceBrowserCompatibility();
    check.setBrowserSupportLevel("deep");
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/fontface/deep.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().atLine(52).withMessage("URL for \"woff\" format is expected.")
      .next().atLine(57).withMessage("URL for \"woff2\" format is expected.")
      .next().atLine(62).withMessage("URL for \"woff2\" format is expected.")
      .next().atLine(67).withMessage("URL for \"woff2\" format is expected.")
      .next().atLine(74).withMessage("URL for \"woff\" format is expected.")
      .next().atLine(82).withMessage("URL for \"woff2\" format is expected.")
      .next().atLine(89).withMessage("URL for \"ttf\" format is expected.")
      .next().atLine(96).withMessage("URL for \"woff2\" format is expected.")
      .noMore();
  }

  @Test
  public void deepest() {
    FontFaceBrowserCompatibility check = new FontFaceBrowserCompatibility();
    check.setBrowserSupportLevel("deepest");
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/fontface/deepest.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().atLine(21).withMessage("URL for \"eot\" format is expected.")
      .next().atLine(28).withMessage("URL for \"woff2\" format is expected.")
      .next().atLine(38).withMessage("URL for \"woff\" format is expected.")
      .next().atLine(48).withMessage("URL for \"ttf\" format is expected.")
      .next().atLine(54).withMessage("URL for \"svg\" format is expected.")
      .next().atLine(61).withMessage("Add an \"src\" property setting the URL for the \".eot\" font file (to support IE9 Compatibility Modes).")
      .next().atLine(72).withMessage("Set the URL for the \".eot\" file in this \"src\" property (to support IE9 Compatibility Modes).")
      .next().atLine(82).withMessage("Remove additional functions from this \"src\" property (to support IE9 Compatibility Modes).")
      .next().atLine(93).withMessage("URL for \"eot\" format is expected.")
      .noMore();
  }

}
