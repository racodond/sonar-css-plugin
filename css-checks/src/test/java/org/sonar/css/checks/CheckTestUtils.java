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
package org.sonar.css.checks;

import java.io.File;

public class CheckTestUtils {

  private static final String TEST_FILE_BASE_DIRECTORY_PATH = "src/test/resources/checks/";

  private CheckTestUtils() {
  }

  public static File getCommonTestFile(String relativePath) {
    return getTestFile(relativePath, "common");
  }

  public static File getCssTestFile(String relativePath) {
    return getTestFile(relativePath, "css");
  }

  public static File getLessTestFile(String relativePath) {
    return getTestFile(relativePath, "less");
  }

  public static File getScssTestFile(String relativePath) {
    return getTestFile(relativePath, "scss");
  }

  private static File getTestFile(String relativePath, String languageDirectoryName) {
    return new File(TEST_FILE_BASE_DIRECTORY_PATH + languageDirectoryName + "/" + relativePath);
  }

}
