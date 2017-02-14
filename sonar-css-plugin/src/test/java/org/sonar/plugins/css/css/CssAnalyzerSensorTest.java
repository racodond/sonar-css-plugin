/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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
package org.sonar.plugins.css.css;

import com.google.common.base.Charsets;

import java.io.File;
import java.util.Collection;

import org.junit.Test;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.FileMetadata;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.internal.ActiveRulesBuilder;
import org.sonar.api.batch.sensor.internal.DefaultSensorDescriptor;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.batch.sensor.issue.Issue;
import org.sonar.api.config.Settings;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.rule.RuleKey;
import org.sonar.css.checks.CheckList;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CssAnalyzerSensorTest {

  private final File baseDir = new File("src/test/resources/css");
  private final SensorContextTester context = SensorContextTester.create(baseDir);
  private CheckFactory checkFactory = new CheckFactory(mock(ActiveRules.class));
  private Settings settings = mock(Settings.class);

  @Test
  public void should_create_a_valid_sensor_descriptor() {
    DefaultSensorDescriptor descriptor = new DefaultSensorDescriptor();
    createCssSquidSensor().describe(descriptor);
    assertThat(descriptor.name()).isEqualTo("CSS Analyzer Sensor");
    assertThat(descriptor.languages()).containsOnly("css");
    assertThat(descriptor.type()).isEqualTo(InputFile.Type.MAIN);
  }

  @Test
  public void should_execute_and_compute_valid_measures() {
    String relativePath = "measures.css";
    inputFile(relativePath);
    createCssSquidSensor().execute(context);
    assertCssMeasures("moduleKey:" + relativePath);
  }

  @Test
  public void should_execute_and_compute_valid_measures_on_file_starting_with_BOM() {
    String relativePath = "measuresWithBOM.css";
    inputFile(relativePath);
    createCssSquidSensor().execute(context);
    assertCssMeasures("moduleKey:" + relativePath);
  }

  private void assertCssMeasures(String key) {
    assertThat(context.measure(key, CoreMetrics.NCLOC).value()).isEqualTo(17);
    assertThat(context.measure(key, CoreMetrics.STATEMENTS).value()).isEqualTo(11);
    assertThat(context.measure(key, CoreMetrics.COMMENT_LINES).value()).isEqualTo(4);
    assertThat(context.measure(key, CoreMetrics.STATEMENTS).value()).isEqualTo(11);
    assertThat(context.measure(key, CoreMetrics.FUNCTIONS).value()).isEqualTo(6);
    assertThat(context.measure(key, CoreMetrics.COMPLEXITY).value()).isEqualTo(7);
    assertThat(context.measure(key, CoreMetrics.COMPLEXITY_IN_FUNCTIONS).value()).isEqualTo(7);
  }

  @Test
  public void should_execute_and_save_issues_on_file_not_starting_with_BOM() {
    should_execute_and_save_issues("issues.css");
  }

  @Test
  public void should_execute_and_save_issues_on_file_starting_with_BOM() {
    should_execute_and_save_issues("issuesWithBOM.css");
  }

  private void should_execute_and_save_issues(String fileName) {
    inputFile(fileName);

    ActiveRules activeRules = (new ActiveRulesBuilder())
      .create(RuleKey.of(CheckList.CSS_REPOSITORY_KEY, "S1135"))
      .activate()
      .create(RuleKey.of(CheckList.CSS_REPOSITORY_KEY, "important"))
      .activate()
      .build();
    checkFactory = new CheckFactory(activeRules);

    createCssSquidSensor().execute(context);

    assertThat(context.allIssues()).hasSize(3);
  }

  @Test
  public void should_raise_an_issue_because_the_parsing_error_rule_is_activated() {
    inputFile("parsingError.css");

    ActiveRules activeRules = (new ActiveRulesBuilder())
      .create(RuleKey.of(CheckList.CSS_REPOSITORY_KEY, "S2260"))
      .activate()
      .build();

    checkFactory = new CheckFactory(activeRules);

    context.setActiveRules(activeRules);
    createCssSquidSensor().execute(context);
    Collection<Issue> issues = context.allIssues();
    assertThat(issues).hasSize(1);
    Issue issue = issues.iterator().next();
    assertThat(issue.primaryLocation().textRange().start().line()).isEqualTo(1);
  }

  @Test
  public void should_not_raise_any_issue_because_the_parsing_error_rule_is_not_activated() {
    inputFile("parsingError.css");

    ActiveRules activeRules = new ActiveRulesBuilder().build();
    checkFactory = new CheckFactory(activeRules);

    context.setActiveRules(activeRules);
    createCssSquidSensor().execute(context);
    Collection<Issue> issues = context.allIssues();
    assertThat(issues).isEmpty();
  }

  @Test
  public void should_not_analyze_minified_files() {
    inputFile("minified/test.min.css");
    inputFile("minified/test-min.css");
    inputFile("minified/testmin.css");

    createCssSquidSensor().execute(context);

    assertThat(context.measure("moduleKey:minified/testmin.css", CoreMetrics.NCLOC)).isNotNull();
    assertThat(context.measure("moduleKey:minified/test.min.css", CoreMetrics.NCLOC)).isNull();
    assertThat(context.measure("moduleKey:minified/test-min.css", CoreMetrics.NCLOC)).isNull();
  }

  private CssAnalyzerSensor createCssSquidSensor() {
    return new CssAnalyzerSensor(context.fileSystem(), checkFactory, settings, new NoSonarFilter());
  }

  private DefaultInputFile inputFile(String relativePath) {
    DefaultInputFile inputFile = new DefaultInputFile("moduleKey", relativePath)
      .setModuleBaseDir(baseDir.toPath())
      .setType(InputFile.Type.MAIN)
      .setLanguage(CssLanguage.KEY);

    context.fileSystem().add(inputFile);
    context.fileSystem().setEncoding(Charsets.UTF_8);

    return inputFile.initMetadata(new FileMetadata().readMetadata(inputFile.file(), Charsets.UTF_8));
  }

}
