/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2017 David RACODON
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
package org.sonar.css.checks.css;

import org.junit.Test;
import org.sonar.css.checks.CheckTestUtils;
import org.sonar.css.checks.verifier.CssCheckVerifier;

public class ImportFirstCheckTest {

  @Test
  public void test1() {
    CssCheckVerifier.verifyCssFile(new ImportFirstCheck(), CheckTestUtils.getCssTestFile("import/importFirst1.css"));
  }

  @Test
  public void test2() {
    CssCheckVerifier.verifyCssFile(new ImportFirstCheck(), CheckTestUtils.getCssTestFile("import/importFirst2.css"));
  }

  @Test
  public void test3() {
    CssCheckVerifier.verifyCssFile(new ImportFirstCheck(), CheckTestUtils.getCssTestFile("import/importFirst3.css"));
  }

  @Test
  public void test4() {
    CssCheckVerifier.verifyCssFile(new ImportFirstCheck(), CheckTestUtils.getCssTestFile("import/importFirst4.css"));
  }

  @Test
  public void test5() {
    CssCheckVerifier.verifyCssFile(new ImportFirstCheck(), CheckTestUtils.getCssTestFile("import/importFirstWithCharset.css"));
  }

  @Test
  public void test6() {
    CssCheckVerifier.verifyCssFile(new ImportFirstCheck(), CheckTestUtils.getCssTestFile("import/importNotFirst1.css"));
  }

  @Test
  public void test7() {
    CssCheckVerifier.verifyCssFile(new ImportFirstCheck(), CheckTestUtils.getCssTestFile("import/importNotFirst2.css"));
  }

}
