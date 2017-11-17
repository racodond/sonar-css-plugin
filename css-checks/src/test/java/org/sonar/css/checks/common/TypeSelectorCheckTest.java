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
package org.sonar.css.checks.common;

import org.junit.Test;
import org.sonar.css.checks.CheckTestUtils;
import org.sonar.css.checks.verifier.CssCheckVerifier;

public class TypeSelectorCheckTest {

  @Test
  public void test_css_without_excludes() {
    CssCheckVerifier.verifyCssFile(new TypeSelectorCheck(), CheckTestUtils.getCommonTestFile("type-selector/typeSelector.css"));
  }

  @Test
  public void test_css_with_excludes() {
    TypeSelectorCheck check = new TypeSelectorCheck();
    check.setAllowedTypes("a, span");
    CssCheckVerifier.verifyCssFile(check, CheckTestUtils.getCommonTestFile("type-selector/typeSelectorExcluded.css"));
  }

  @Test
  public void test_css_with_excludes_case_insensitive() {
    TypeSelectorCheck check = new TypeSelectorCheck();
    check.setAllowedTypes("A, SpAn");
    CssCheckVerifier.verifyCssFile(check, CheckTestUtils.getCommonTestFile("type-selector/typeSelectorExcluded.css"));
  }

  @Test
  public void test_less_without_excludes() {
    CssCheckVerifier.verifyLessFile(new TypeSelectorCheck(), CheckTestUtils.getCommonTestFile("type-selector/typeSelector.less"));
  }

  @Test
  public void test_scss_without_excludes() {
    CssCheckVerifier.verifyScssFile(new TypeSelectorCheck(), CheckTestUtils.getCommonTestFile("type-selector/typeSelector.scss"));
  }

}
