/*
 * SonarQube CSS / Less Plugin
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
package org.sonar.plugins.css.embedded;

import com.google.common.base.Charsets;

import java.io.File;

import org.junit.Test;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.FileMetadata;
import org.sonar.api.batch.rule.ActiveRules;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.internal.ActiveRulesBuilder;
import org.sonar.api.batch.sensor.internal.DefaultSensorDescriptor;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.config.Settings;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.rule.RuleKey;
import org.sonar.css.checks.CheckList;
import org.sonar.plugins.css.Plugin;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmbeddedCssAnalyzerSensorTest {

  private final File baseDir = new File("src/test/resources/embedded");
  private final SensorContextTester context = SensorContextTester.create(baseDir);
  private CheckFactory checkFactory = new CheckFactory(mock(ActiveRules.class));
  private Settings settings = mock(Settings.class);

  @Test
  public void should_create_a_valid_sensor_descriptor() {
    DefaultSensorDescriptor descriptor = new DefaultSensorDescriptor();
    createEmbeddedCssSquidSensor().describe(descriptor);
    assertThat(descriptor.name()).isEqualTo("Embedded CSS Analyzer Sensor");
    assertThat(descriptor.languages()).isEmpty();
    assertThat(descriptor.type()).isNull();
  }

  @Test
  public void should_execute_and_save_issues_on_file_whose_suffix_is_in_the_list_of_default_embedded_css_file_suffixes() {
    String[] suffixes = {"html", "xhtml"};
    when(settings.getStringArray(Plugin.EMBEDDED_CSS_FILE_SUFFIXES_KEY)).thenReturn(suffixes);
    should_execute_and_save_issues("embeddedCss.html");
  }

  @Test
  public void should_execute_and_save_issues_on_file_whose_suffix_is_in_the_list_of_custom_embedded_css_file_suffixes() {
    String[] suffixes = {"abc"};
    when(settings.getStringArray(Plugin.EMBEDDED_CSS_FILE_SUFFIXES_KEY)).thenReturn(suffixes);
    should_execute_and_save_issues("embeddedCss.abc");
  }

  @Test
  public void should_not_execute_and_save_issues_on_file_whose_suffix_is_not_in_the_list_of_embedded_css_file_suffixes() {
    String[] suffixes = {"abc"};
    when(settings.getStringArray(Plugin.EMBEDDED_CSS_FILE_SUFFIXES_KEY)).thenReturn(suffixes);
    should_not_execute_and_save_issues("embeddedCss.html");
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

    createEmbeddedCssSquidSensor().execute(context);

    assertThat(context.allIssues()).hasSize(2);
  }

  private void should_not_execute_and_save_issues(String fileName) {
    inputFile(fileName);

    ActiveRules activeRules = (new ActiveRulesBuilder())
      .create(RuleKey.of(CheckList.CSS_REPOSITORY_KEY, "S1135"))
      .activate()
      .create(RuleKey.of(CheckList.CSS_REPOSITORY_KEY, "important"))
      .activate()
      .build();
    checkFactory = new CheckFactory(activeRules);

    createEmbeddedCssSquidSensor().execute(context);

    assertThat(context.allIssues()).hasSize(0);
  }

  private EmbeddedCssAnalyzerSensor createEmbeddedCssSquidSensor() {
    return new EmbeddedCssAnalyzerSensor(context.fileSystem(), checkFactory, settings, new NoSonarFilter());
  }

  private DefaultInputFile inputFile(String relativePath) {
    DefaultInputFile inputFile = new DefaultInputFile("moduleKey", relativePath)
      .setModuleBaseDir(baseDir.toPath())
      .setType(InputFile.Type.MAIN)
      .setLanguage("unknown");

    context.fileSystem().add(inputFile);
    context.fileSystem().setEncoding(Charsets.UTF_8);

    return inputFile.initMetadata(new FileMetadata().readMetadata(inputFile.file(), Charsets.UTF_8));
  }

}
