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
package org.sonar.css;

import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.build.SonarScanner;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static org.sonar.css.Tests.getMeasureAsDouble;

public class ScssMetricsTest {

  @ClassRule
  public static Orchestrator orchestrator = Tests.ORCHESTRATOR;

  private static final String PROJECT_KEY = "scss-metrics";

  @BeforeClass
  public static void init() {
    orchestrator.resetData();

    SonarScanner build = Tests.createSonarScannerBuild()
      .setProjectDir(new File("../projects/scss-metrics/"))
      .setProjectKey(PROJECT_KEY)
      .setProjectName(PROJECT_KEY);

    orchestrator.getServer().provisionProject(PROJECT_KEY, PROJECT_KEY);
    Tests.setScssProfile("scss-empty-profile", PROJECT_KEY);
    orchestrator.executeBuild(build);
  }

  @Test
  public void project_measures() {
    assertThat(getProjectMeasure("lines")).isEqualTo(42);
    assertThat(getProjectMeasure("ncloc")).isEqualTo(29);
    assertThat(getProjectMeasure("classes")).isNull();
    assertThat(getProjectMeasure("functions")).isEqualTo(10);
    assertThat(getProjectMeasure("statements")).isEqualTo(19);
    assertThat(getProjectMeasure("files")).isEqualTo(2);
    assertThat(getProjectMeasure("directories")).isEqualTo(2);

    assertThat(getProjectMeasure("comment_lines")).isEqualTo(5);
    assertThat(getProjectMeasure("comment_lines_density")).isEqualTo(14.7);

    assertThat(getProjectMeasure("complexity")).isEqualTo(12);
    assertThat(getProjectMeasure("complexity_in_functions")).isEqualTo(12);
    assertThat(getProjectMeasure("function_complexity")).isEqualTo(1.2);
    assertThat(getProjectMeasure("function_complexity_distribution")).isNull();
    assertThat(getProjectMeasure("file_complexity")).isEqualTo(6);

    assertThat(getProjectMeasure("duplicated_lines")).isEqualTo(0);
    assertThat(getProjectMeasure("duplicated_files")).isEqualTo(0);
    assertThat(getProjectMeasure("duplicated_blocks")).isEqualTo(0);
    assertThat(getProjectMeasure("duplicated_lines_density")).isEqualTo(0.0);

    assertThat(getProjectMeasure("violations")).isEqualTo(0);
  }

  @Test
  public void dir_measures() {
    assertThat(getDirMeasure("lines")).isEqualTo(24);
    assertThat(getDirMeasure("ncloc")).isEqualTo(17);
    assertThat(getDirMeasure("classes")).isNull();
    assertThat(getDirMeasure("functions")).isEqualTo(6);
    assertThat(getDirMeasure("statements")).isEqualTo(11);
    assertThat(getDirMeasure("files")).isEqualTo(1);
    assertThat(getDirMeasure("directories")).isEqualTo(1);

    assertThat(getDirMeasure("comment_lines")).isEqualTo(3);
    assertThat(getDirMeasure("comment_lines_density")).isEqualTo(15.0);

    assertThat(getDirMeasure("complexity")).isEqualTo(7);
    assertThat(getDirMeasure("complexity_in_functions")).isEqualTo(7);
    assertThat(getDirMeasure("function_complexity")).isEqualTo(1.2);
    assertThat(getDirMeasure("function_complexity_distribution")).isNull();
    assertThat(getDirMeasure("file_complexity")).isEqualTo(7.0);

    assertThat(getDirMeasure("duplicated_lines")).isEqualTo(0);
    assertThat(getDirMeasure("duplicated_files")).isEqualTo(0);
    assertThat(getDirMeasure("duplicated_blocks")).isEqualTo(0);
    assertThat(getDirMeasure("duplicated_lines_density")).isEqualTo(0.0);

    assertThat(getDirMeasure("violations")).isEqualTo(0);
  }

  @Test
  public void file1_measures() {
    assertThat(getFile1Measure("lines")).isEqualTo(18);
    assertThat(getFile1Measure("ncloc")).isEqualTo(12);
    assertThat(getFile1Measure("classes")).isNull();
    assertThat(getFile1Measure("functions")).isEqualTo(4);
    assertThat(getFile1Measure("statements")).isEqualTo(8);

    assertThat(getFile1Measure("comment_lines")).isEqualTo(2);
    assertThat(getFile1Measure("comment_lines_density")).isEqualTo(14.3);

    assertThat(getFile1Measure("complexity")).isEqualTo(5);
    assertThat(getFile1Measure("complexity_in_functions")).isEqualTo(5);
    assertThat(getFile1Measure("function_complexity")).isEqualTo(1.3);
    assertThat(getFile1Measure("function_complexity_distribution")).isNull();
    assertThat(getFile1Measure("file_complexity")).isEqualTo(5.0);

    assertThat(getFile1Measure("duplicated_lines")).isEqualTo(0);
    assertThat(getFile1Measure("duplicated_blocks")).isEqualTo(0);
    assertThat(getFile1Measure("duplicated_lines_density")).isEqualTo(0);

    assertThat(getFile1Measure("violations")).isEqualTo(0);
  }

  @Test
  public void file2_measures() {
    assertThat(getFile2Measure("lines")).isEqualTo(24);
    assertThat(getFile2Measure("ncloc")).isEqualTo(17);
    assertThat(getFile2Measure("classes")).isNull();
    assertThat(getFile2Measure("functions")).isEqualTo(6);
    assertThat(getFile2Measure("statements")).isEqualTo(11);

    assertThat(getFile2Measure("comment_lines")).isEqualTo(3);
    assertThat(getFile2Measure("comment_lines_density")).isEqualTo(15);

    assertThat(getFile2Measure("complexity")).isEqualTo(7);
    assertThat(getFile2Measure("complexity_in_functions")).isEqualTo(7);
    assertThat(getFile2Measure("function_complexity")).isEqualTo(1.2);
    assertThat(getFile2Measure("function_complexity_distribution")).isNull();
    assertThat(getFile2Measure("file_complexity")).isEqualTo(7.0);

    assertThat(getFile2Measure("duplicated_lines")).isEqualTo(0);
    assertThat(getFile2Measure("duplicated_blocks")).isEqualTo(0);
    assertThat(getFile2Measure("duplicated_lines_density")).isEqualTo(0);

    assertThat(getFile2Measure("violations")).isEqualTo(0);
  }

  private Double getProjectMeasure(String metricKey) {
    return getMeasureAsDouble(PROJECT_KEY, metricKey);
  }

  private Double getDirMeasure(String metricKey) {
    return getMeasureAsDouble(PROJECT_KEY + ":src/dir", metricKey);
  }

  private Double getFile1Measure(String metricKey) {
    return getMeasureAsDouble(PROJECT_KEY + ":src/file1.scss", metricKey);
  }

  private Double getFile2Measure(String metricKey) {
    return getMeasureAsDouble(PROJECT_KEY + ":src/dir/file2.scss", metricKey);
  }

}
