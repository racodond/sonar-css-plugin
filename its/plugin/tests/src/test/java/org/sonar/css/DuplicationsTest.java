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

import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.build.SonarScanner;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.services.Measure;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;

import static org.fest.assertions.Assertions.assertThat;

public class DuplicationsTest {

  @ClassRule
  public static Orchestrator orchestrator = Tests.ORCHESTRATOR;

  private static final String PROJECT_KEY = "duplications";
  private static Sonar wsClient;

  @BeforeClass
  public static void init() {
    orchestrator.resetData();

    SonarScanner build = Tests.createSonarScannerBuild()
      .setProjectDir(new File("../projects/duplications/"))
      .setProjectKey(PROJECT_KEY)
      .setProjectName(PROJECT_KEY);

    orchestrator.getServer().provisionProject(PROJECT_KEY, PROJECT_KEY);
    Tests.setProfile("empty-profile", PROJECT_KEY);
    orchestrator.executeBuild(build);

    wsClient = orchestrator.getServer().getWsClient();
  }

  @Test
  public void project_measures() {
    assertThat(getProjectMeasure("duplicated_lines").getIntValue()).isEqualTo(40);
    assertThat(getProjectMeasure("duplicated_files").getIntValue()).isEqualTo(2);
    assertThat(getProjectMeasure("duplicated_blocks").getIntValue()).isEqualTo(3);
    assertThat(getProjectMeasure("duplicated_lines_density").getValue()).isEqualTo(95.2);
  }

  @Test
  public void dir_measures() {
    assertThat(getDirMeasure("duplicated_lines").getIntValue()).isEqualTo(13);
    assertThat(getDirMeasure("duplicated_files").getIntValue()).isEqualTo(1);
    assertThat(getDirMeasure("duplicated_blocks").getIntValue()).isEqualTo(1);
    assertThat(getDirMeasure("duplicated_lines_density").getValue()).isEqualTo(100.0);
  }

  @Test
  public void file1_measures() {
    assertThat(getFile1Measure("duplicated_lines").getIntValue()).isEqualTo(27);
    assertThat(getFile1Measure("duplicated_blocks").getIntValue()).isEqualTo(2);
    assertThat(getFile1Measure("duplicated_lines_density").getValue()).isEqualTo(93.1);
  }

  @Test
  public void file2_measures() {
    assertThat(getFile2Measure("duplicated_lines").getIntValue()).isEqualTo(13);
    assertThat(getFile2Measure("duplicated_blocks").getIntValue()).isEqualTo(1);
    assertThat(getFile2Measure("duplicated_lines_density").getValue()).isEqualTo(100.0);
  }

  private Measure getProjectMeasure(String metricKey) {
    Resource resource = wsClient.find(ResourceQuery.createForMetrics(PROJECT_KEY, metricKey));
    return resource == null ? null : resource.getMeasure(metricKey);
  }

  private Measure getDirMeasure(String metricKey) {
    Resource resource = wsClient.find(ResourceQuery.createForMetrics(PROJECT_KEY + ":src/dir", metricKey));
    return resource == null ? null : resource.getMeasure(metricKey);
  }

  private Measure getFileMeasure(String metricKey, String fileKey) {
    Resource resource = wsClient.find(ResourceQuery.createForMetrics(fileKey, metricKey));
    return resource == null ? null : resource.getMeasure(metricKey);
  }

  private Measure getFile1Measure(String metricKey) {
    return getFileMeasure(metricKey, PROJECT_KEY + ":src/file1.css");
  }

  private Measure getFile2Measure(String metricKey) {
    return getFileMeasure(metricKey, PROJECT_KEY + ":src/dir/file2.css");
  }

}
