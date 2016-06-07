/*
 * SonarQube CSS Plugin
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
package org.sonar.css;

import java.io.File;

import org.junit.Test;
import org.sonar.css.api.CssMetric;
import org.sonar.squidbridge.api.SourceFile;

import static org.fest.assertions.Assertions.assertThat;

public class CssAstScannerTest {

  @Test
  public void comments() {
    SourceFile file = CssAstScanner.scanSingleFile(new File(
      "src/test/resources/metrics/metric.css"));
    assertThat(file.getInt(CssMetric.COMMENT_LINES)).isEqualTo(2);
    assertThat(file.getNoSonarTagLines()).contains(10);
    assertThat(file.getNoSonarTagLines().size()).isEqualTo(1);
  }

  @Test
  public void lines_of_code() {
    SourceFile file = CssAstScanner.scanSingleFile(new File(
      "src/test/resources/metrics/metric.css"));
    assertThat(file.getInt(CssMetric.LINES_OF_CODE)).isEqualTo(17);
  }

  @Test
  public void statements() {
    SourceFile file = CssAstScanner.scanSingleFile(new File(
      "src/test/resources/metrics/metric.css"));
    assertThat(file.getInt(CssMetric.STATEMENTS)).isEqualTo(11);
  }

  @Test
  public void ruleSets() {
    SourceFile file = CssAstScanner.scanSingleFile(new File(
      "src/test/resources/metrics/metric.css"));
    assertThat(file.getInt(CssMetric.RULE_SETS)).isEqualTo(5);
  }

  @Test
  public void atRules() {
    SourceFile file = CssAstScanner.scanSingleFile(new File(
      "src/test/resources/metrics/metric.css"));
    assertThat(file.getInt(CssMetric.AT_RULES)).isEqualTo(1);
  }

  @Test
  public void functions() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/metrics/metric.css"));
    assertThat(file.getInt(CssMetric.FUNCTIONS)).isEqualTo(6);
  }

  @Test
  public void complexity() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/metrics/complexity.css"));
    assertThat(file.getInt(CssMetric.COMPLEXITY)).isEqualTo(21);
  }

}
