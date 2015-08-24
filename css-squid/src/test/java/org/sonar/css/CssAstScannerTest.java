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
package org.sonar.css;

import java.io.File;

import com.google.common.base.Charsets;
import org.junit.Test;
import org.sonar.css.api.CssMetric;
import org.sonar.squidbridge.api.SourceFile;

import static org.fest.assertions.Assertions.assertThat;

public class CssAstScannerTest {

  // FIXME Drop the computation of the FILES metric
  @Test
  public void files() {
  }

  @Test
  public void comments() {
    SourceFile file = CssAstScanner.scanSingleFile(new File(
      "src/test/resources/metrics/metric.css"));
    assertThat(file.getInt(CssMetric.COMMENT_LINES)).isEqualTo(2);
    assertThat(file.getNoSonarTagLines()).contains(10);
    assertThat(file.getNoSonarTagLines().size()).isEqualTo(1);
  }

  @Test
  public void lines() {
    SourceFile file = CssAstScanner.scanSingleFile(new File(
      "src/test/resources/metrics/metric.css"));
    assertThat(file.getInt(CssMetric.LINES)).isEqualTo(24);
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

  @Test
  public void complexity_should_not_be_computed_when_complexity_computation_is_switched_off() {
    CssConfiguration cssConfiguration = new CssConfiguration(Charsets.UTF_8);
    cssConfiguration.setComputeComplexity(false);
    SourceFile file = CssAstScanner.scanSingleFileWithSpecificConfiguration(new File("src/test/resources/metrics/complexity.css"), cssConfiguration);
    // No way to assert that the metric is not computed (no getter for SourceFile.measures).
    // As a workaround, assert that the measure is 0.
    assertThat(file.getInt(CssMetric.COMPLEXITY)).isEqualTo(0);
  }

  @Test
  public void functions_should_not_be_computed_when_complexity_computation_is_switched_off() {
    CssConfiguration cssConfiguration = new CssConfiguration(Charsets.UTF_8);
    cssConfiguration.setComputeComplexity(false);
    SourceFile file = CssAstScanner.scanSingleFileWithSpecificConfiguration(new File("src/test/resources/metrics/metric.css"), cssConfiguration);
    // No way to assert that the metric is not computed (no getter for SourceFile.measures).
    // As a workaround, assert that the measure is 0.
    assertThat(file.getInt(CssMetric.FUNCTIONS)).isEqualTo(0);
  }

}
