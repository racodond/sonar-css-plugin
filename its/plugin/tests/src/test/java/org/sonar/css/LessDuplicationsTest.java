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
package org.sonar.css;

import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.build.SonarScanner;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static org.sonar.css.Tests.getMeasureAsDouble;

public class LessDuplicationsTest {

  @ClassRule
  public static Orchestrator orchestrator = Tests.ORCHESTRATOR;

  private static final String PROJECT_KEY = "less-duplications";

  @BeforeClass
  public static void init() {
    orchestrator.resetData();

    SonarScanner build = Tests.createSonarScannerBuild()
      .setProjectDir(new File("../projects/less-duplications/"))
      .setProjectKey(PROJECT_KEY)
      .setProjectName(PROJECT_KEY);

    orchestrator.getServer().provisionProject(PROJECT_KEY, PROJECT_KEY);
    Tests.setLessProfile("less-empty-profile", PROJECT_KEY);
    orchestrator.executeBuild(build);
  }

  @Test
  public void project_measures() {
    assertThat(getProjectMeasure("duplicated_lines")).isEqualTo(43);
    assertThat(getProjectMeasure("duplicated_files")).isEqualTo(2);
    assertThat(getProjectMeasure("duplicated_blocks")).isEqualTo(3);
    assertThat(getProjectMeasure("duplicated_lines_density")).isEqualTo(95.6);
  }

  @Test
  public void dir_measures() {
    assertThat(getDirMeasure("duplicated_lines")).isEqualTo(14);
    assertThat(getDirMeasure("duplicated_files")).isEqualTo(1);
    assertThat(getDirMeasure("duplicated_blocks")).isEqualTo(1);
    assertThat(getDirMeasure("duplicated_lines_density")).isEqualTo(100.0);
  }

  @Test
  public void file1_measures() {
    assertThat(getFile1Measure("duplicated_lines")).isEqualTo(29);
    assertThat(getFile1Measure("duplicated_blocks")).isEqualTo(2);
    assertThat(getFile1Measure("duplicated_lines_density")).isEqualTo(93.5);
  }

  @Test
  public void file2_measures() {
    assertThat(getFile2Measure("duplicated_lines")).isEqualTo(14);
    assertThat(getFile2Measure("duplicated_blocks")).isEqualTo(1);
    assertThat(getFile2Measure("duplicated_lines_density")).isEqualTo(100.0);
  }

  private Double getProjectMeasure(String metricKey) {
    return getMeasureAsDouble(PROJECT_KEY, metricKey);
  }

  private Double getDirMeasure(String metricKey) {
    return getMeasureAsDouble(PROJECT_KEY + ":src/dir", metricKey);
  }

  private Double getFile1Measure(String metricKey) {
    return getMeasureAsDouble(PROJECT_KEY + ":src/file1.less", metricKey);
  }

  private Double getFile2Measure(String metricKey) {
    return getMeasureAsDouble(PROJECT_KEY + ":src/dir/file2.less", metricKey);
  }

}
