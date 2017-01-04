/*
 * SonarQube CSS / SCSS / Less Analyzer
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
package org.sonar.css.checks.common;

import org.junit.Test;
import org.sonar.css.checks.CheckTestUtils;
import org.sonar.css.checks.verifier.CssCheckVerifier;

import java.io.File;

public class UnknownPseudoCheckTest {

  private UnknownPseudoCheck check = new UnknownPseudoCheck();

  @Test
  public void test_css() {
    CssCheckVerifier.verifyCssFile(check, getTestFile("unknownPseudo.css"));
  }

  @Test
  public void test_scss_interpolated_pseudo() {
    CssCheckVerifier.verifyScssFile(check, getTestFile("unknownPseudo.scss"));
  }

  @Test
  public void test_less_interpolated_pseudo() {
    CssCheckVerifier.verifyLessFile(check, getTestFile("unknownPseudo.less"));
  }

  private File getTestFile(String fileName) {
    return CheckTestUtils.getCommonTestFile("unknown-pseudo/" + fileName);
  }

}
