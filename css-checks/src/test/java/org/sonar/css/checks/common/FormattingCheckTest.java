/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
 * mailto: david.racodon@gmail.com
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
package org.sonar.css.checks.common;

import org.junit.Test;
import org.sonar.css.checks.CheckTestUtils;
import org.sonar.css.checks.verifier.CssCheckVerifier;

import java.io.File;

public class FormattingCheckTest {

  private FormattingCheck check = new FormattingCheck();

  @Test
  public void block() {
    CssCheckVerifier.verifyCssFile(check, getTestFile("block.css"));
  }

  @Test
  public void declaration_css() {
    CssCheckVerifier.verifyCssFile(check, getTestFile("declaration.css"));
  }

  @Test
  public void declaration_less() {
    CssCheckVerifier.verifyLessFile(check, getTestFile("declaration.less"));
  }

  @Test
  public void declaration_scss() {
    CssCheckVerifier.verifyScssFile(check, getTestFile("declaration.scss"));
  }

  @Test
  public void important() {
    CssCheckVerifier.verifyCssFile(check, getTestFile("important.css"));
  }

  @Test
  public void delimiter_separated_list_css() {
    CssCheckVerifier.verifyCssFile(check, getTestFile("delimiterSeparatedList.css"));
  }

  @Test
  public void delimiter_separated_list_less() {
    CssCheckVerifier.verifyLessFile(check, getTestFile("delimiterSeparatedList.less"));
  }

  @Test
  public void delimiter_separated_list_scss() {
    CssCheckVerifier.verifyScssFile(check, getTestFile("delimiterSeparatedList.scss"));
  }

  private File getTestFile(String fileName) {
    return CheckTestUtils.getCommonTestFile("formatting/" + fileName);
  }

}
