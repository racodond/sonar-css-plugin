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
package org.sonar.plugins.css;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.css.CssAstScanner;
import org.sonar.css.CssConfiguration;
import org.sonar.css.api.CssMetric;
import org.sonar.css.checks.CheckList;
import org.sonar.css.issue.Issue;
import org.sonar.css.issue.PreciseIssue;
import org.sonar.plugins.css.core.CssLanguage;
import org.sonar.squidbridge.AstScanner;
import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.squidbridge.api.SourceCode;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.squidbridge.indexer.QueryByType;
import org.sonar.sslr.parser.LexerlessGrammar;

public class CssSquidSensor implements Sensor {

  private final CheckFactory checkFactory;
  private final FileSystem fileSystem;
  private final FilePredicate mainFilePredicate;
  private final NoSonarFilter noSonarFilter;

  private SensorContext sensorContext;

  public CssSquidSensor(FileSystem fileSystem, CheckFactory checkFactory, NoSonarFilter noSonarFilter) {
    this.checkFactory = checkFactory;
    this.fileSystem = fileSystem;
    this.noSonarFilter = noSonarFilter;
    this.mainFilePredicate = fileSystem.predicates().and(
      fileSystem.predicates().hasType(InputFile.Type.MAIN),
      fileSystem.predicates().hasLanguage(CssLanguage.KEY));
  }

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor
      .onlyOnLanguage(CssLanguage.KEY)
      .name("CSS Squid Sensor")
      .onlyOnFileType(Type.MAIN);
  }

  @Override
  public void execute(SensorContext context) {
    this.sensorContext = context;

    CssConfiguration cssConfiguration = new CssConfiguration(fileSystem.encoding());

    Checks<SquidCheck> checks = checkFactory.<SquidCheck>create(CheckList.REPOSITORY_KEY).addAnnotatedChecks((Iterable) CheckList.getChecks());
    Collection<SquidCheck> checkList = checks.all();

    Set<Issue> issues = new HashSet<>();

    AstScanner<LexerlessGrammar> scanner = CssAstScanner.create(sensorContext, cssConfiguration, issues, checkList.toArray(new SquidAstVisitor[checkList.size()]));
    scanner.scanFiles(Lists.newArrayList(fileSystem.files(mainFilePredicate)));

    Collection<SourceCode> squidSourceFiles = scanner.getIndex().search(new QueryByType(SourceFile.class));
    save(squidSourceFiles, checks, issues);
  }

  private void save(Collection<SourceCode> squidSourceFiles, Checks<SquidCheck> checks, Set<Issue> issues) {
    for (SourceCode squidSourceFile : squidSourceFiles) {
      SourceFile squidFile = (SourceFile) squidSourceFile;
      InputFile sonarFile = fileSystem.inputFile(fileSystem.predicates().hasAbsolutePath(squidFile.getKey()));
      noSonarFilter.noSonarInFile(sonarFile, squidFile.getNoSonarTagLines());
      saveMeasures(sonarFile, squidFile);
    }
    saveIssues(checks, issues);
  }

  private void saveMeasures(InputFile sonarFile, SourceFile squidFile) {
    sensorContext.<Integer>newMeasure()
      .on(sonarFile)
      .forMetric(CoreMetrics.NCLOC)
      .withValue(squidFile.getInt(CssMetric.LINES_OF_CODE))
      .save();

    sensorContext.<Integer>newMeasure()
      .on(sonarFile)
      .forMetric(CoreMetrics.STATEMENTS)
      .withValue(squidFile.getInt(CssMetric.STATEMENTS))
      .save();

    sensorContext.<Integer>newMeasure()
      .on(sonarFile)
      .forMetric(CoreMetrics.COMMENT_LINES)
      .withValue(squidFile.getInt(CssMetric.COMMENT_LINES))
      .save();

    sensorContext.<Integer>newMeasure()
      .on(sonarFile)
      .forMetric(CoreMetrics.FUNCTIONS)
      .withValue(squidFile.getInt(CssMetric.FUNCTIONS))
      .save();

    sensorContext.<Integer>newMeasure()
      .on(sonarFile)
      .forMetric(CoreMetrics.COMPLEXITY)
      .withValue(squidFile.getInt(CssMetric.COMPLEXITY))
      .save();

    sensorContext.<Integer>newMeasure()
      .on(sonarFile)
      .forMetric(CoreMetrics.COMPLEXITY_IN_FUNCTIONS)
      .withValue(squidFile.getInt(CssMetric.COMPLEXITY_IN_FUNCTIONS))
      .save();
  }

  private void saveIssues(Checks<SquidCheck> checks, Set<Issue> issues) {
    // TODO: Try and remove TestIssue to avoid this ugly if
    for (Issue issue : issues) {
      if (issue instanceof PreciseIssue) {
        ((PreciseIssue) issue).save(checks, sensorContext);
      } else {
        throw new IllegalStateException("Unsupported type of issue to be saved.");
      }
    }
  }

}
