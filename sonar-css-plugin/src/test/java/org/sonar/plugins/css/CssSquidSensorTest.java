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
package org.sonar.plugins.css;

import com.google.common.collect.ImmutableList;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Collections;

import org.apache.commons.collections.ListUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.resources.Project;
import org.sonar.css.ast.visitors.SonarComponents;
import org.sonar.squidbridge.SquidAstVisitor;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CssSquidSensorTest {

  private CssSquidSensor sensor;

  @Before
  public void setUp() {
    FileLinesContextFactory fileLinesContextFactory = mock(FileLinesContextFactory.class);
    FileLinesContext fileLinesContext = mock(FileLinesContext.class);
    when(fileLinesContextFactory.createFor(Mockito.any(InputFile.class))).thenReturn(fileLinesContext);

    FileSystem fs = mock(FileSystem.class);
    when(fs.predicates()).thenReturn(mock(FilePredicates.class));
    when(fs.files(Mockito.any(FilePredicate.class))).thenReturn(Collections.singletonList(new File("src/test/resources/org/sonar/plugins/css/cssProject/css/metrics.css")));
    when(fs.encoding()).thenReturn(Charset.forName("UTF-8"));

    Checks checks = mock(Checks.class);
    when(checks.addAnnotatedChecks(Mockito.anyCollection())).thenReturn(checks);
    CheckFactory checkFactory = mock(CheckFactory.class);
    when(checkFactory.<SquidAstVisitor>create(Mockito.anyString())).thenReturn(checks);

    sensor = new CssSquidSensor(null, fs, checkFactory, mock(NoSonarFilter.class));
  }

  @Test
  public void should_execute_on() {
    Project project = new Project("key");
    FileSystem fs = mock(FileSystem.class);
    when(fs.predicates()).thenReturn(mock(FilePredicates.class));
    CssSquidSensor cssSensor = new CssSquidSensor(mock(SonarComponents.class), fs, mock(CheckFactory.class), mock(NoSonarFilter.class));

    when(fs.files(Mockito.any(FilePredicate.class))).thenReturn(ListUtils.EMPTY_LIST);
    assertThat(cssSensor.shouldExecuteOnProject(project)).isFalse();

    when(fs.files(Mockito.any(FilePredicate.class))).thenReturn(ImmutableList.of(new File("/tmp")));
    assertThat(cssSensor.shouldExecuteOnProject(project)).isTrue();
  }

  @Test
  public void should_analyse() {
    Project project = new Project("key");
    SensorContext context = mock(SensorContext.class);

    sensor.analyse(project, context);

    verify(context).saveMeasure(Mockito.any(InputFile.class), Mockito.eq(CoreMetrics.LINES), Mockito.eq(43.0));
    verify(context).saveMeasure(Mockito.any(InputFile.class), Mockito.eq(CoreMetrics.NCLOC), Mockito.eq(31.0));
    verify(context).saveMeasure(Mockito.any(InputFile.class), Mockito.eq(CoreMetrics.STATEMENTS), Mockito.eq(21.0));
    verify(context).saveMeasure(Mockito.any(InputFile.class), Mockito.eq(CoreMetrics.COMMENT_LINES), Mockito.eq(6.0));
  }

}
