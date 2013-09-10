/*
 * Sonar CSS Plugin
 * Copyright (C) 2013 Tamas Kende
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

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.sonar.sslr.squid.AstScanner;
import org.junit.Test;
import org.sonar.css.api.CssMetric;
import org.sonar.squid.api.SourceFile;
import org.sonar.squid.api.SourceProject;
import org.sonar.squid.indexer.QueryByType;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

@SuppressWarnings("unchecked")
public class CssAstScannerTest {

  @Test
  public void files() {
    AstScanner<?> scanner = CssAstScanner.create(new CssConfiguration(
        Charsets.UTF_8));
    scanner.scanFiles(ImmutableList.of(
        new File("src/test/resources/form-elements.css"),
        new File("src/test/resources/normalize.css")));
    SourceProject project = (SourceProject) scanner.getIndex().search(
        new QueryByType(SourceProject.class)).iterator().next();
    assertThat(project.getInt(CssMetric.FILES)).isEqualTo(2);
  }

  @Test
  public void comments() {
    SourceFile file = CssAstScanner.scanSingleFile(new File(
        "src/test/resources/metrics/metric.css"));
    assertThat(file.getInt(CssMetric.COMMENT_BLANK_LINES)).isEqualTo(5);
    assertThat(file.getInt(CssMetric.COMMENT_LINES)).isEqualTo(9);
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



}
