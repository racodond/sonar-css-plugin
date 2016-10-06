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
package org.sonar.plugins.css;

import com.google.common.base.Throwables;
import com.sonar.sslr.api.RecognitionException;
import com.sonar.sslr.api.typed.ActionParser;

import java.io.File;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.config.Settings;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.css.visitors.CharsetAwareVisitor;
import org.sonar.css.visitors.CssTreeVisitorContext;
import org.sonar.plugins.css.api.CssCheck;
import org.sonar.plugins.css.api.CustomRulesDefinition;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.TreeVisitor;
import org.sonar.plugins.css.api.visitors.issue.Issue;
import org.sonar.squidbridge.ProgressReport;
import org.sonar.squidbridge.api.AnalysisException;

public abstract class AbstractLanguageAnalyzerSensor implements Sensor {

  private static final Logger LOG = Loggers.get(AbstractLanguageAnalyzerSensor.class);

  private final FileSystem fileSystem;
  private final CheckFactory checkFactory;
  private final Settings settings;
  private final NoSonarFilter noSonarFilter;
  private final ActionParser<Tree> parser;
  private final CustomRulesDefinition[] customRulesDefinition;

  private Checks checks;
  private RuleKey parsingErrorRuleKey = null;
  private IssueSaver issueSaver;

  public AbstractLanguageAnalyzerSensor(FileSystem fileSystem, CheckFactory checkFactory, Settings settings, NoSonarFilter noSonarFilter) {
    this(fileSystem, checkFactory, settings, noSonarFilter, null);
  }

  public AbstractLanguageAnalyzerSensor(FileSystem fileSystem, CheckFactory checkFactory, Settings settings, NoSonarFilter noSonarFilter,
    @Nullable CustomRulesDefinition[] customRulesDefinition) {

    this.fileSystem = fileSystem;
    this.settings = settings;
    this.noSonarFilter = noSonarFilter;
    this.checkFactory = checkFactory;
    this.customRulesDefinition = customRulesDefinition;
    this.parser = parser(fileSystem);
  }

  public Settings getSettings() {
    return settings;
  }

  @Override
  public void execute(SensorContext sensorContext) {
    checks = checks(checkFactory, customRulesDefinition);
    List<TreeVisitor> treeVisitors = treeVisitors(sensorContext, checks, noSonarFilter);
    List<InputFile> filesToAnalyze = filesToAnalyze(fileSystem);

    setParsingErrorCheckIfActivated(treeVisitors);

    ProgressReport progressReport = new ProgressReport(
      "Report about progress of " + languageToAnalyze() + " analyzer",
      TimeUnit.SECONDS.toMillis(10));

    progressReport.start(filesToAnalyze.stream().map(InputFile::file).collect(Collectors.toList()));

    issueSaver = new IssueSaver(sensorContext, checks);
    List<Issue> issues = new ArrayList<>();

    boolean success = false;
    try {
      for (InputFile inputFile : filesToAnalyze(fileSystem)) {
        issues.addAll(analyzeFile(sensorContext, inputFile, treeVisitors));
        progressReport.nextFile();
      }
      saveSingleFileIssues(issues);
      success = true;
    } finally {
      stopProgressReport(progressReport, success);
    }
  }

  public abstract ActionParser<Tree> parser(FileSystem fileSystem);

  public abstract List<TreeVisitor> treeVisitors(SensorContext sensorContext, Checks checks, NoSonarFilter noSonarFilter);

  public abstract Checks checks(CheckFactory checkFactory, CustomRulesDefinition[] customRulesDefinitions);

  public abstract List<InputFile> filesToAnalyze(FileSystem fileSystem);

  public abstract String languageToAnalyze();

  public abstract Class parsingErrorCheck();

  private List<Issue> analyzeFile(SensorContext sensorContext, InputFile inputFile, List<TreeVisitor> visitors) {
    try {
      TreeImpl cssTree = (TreeImpl) parser.parse(new File(inputFile.absolutePath()));
      return scanFile(inputFile, cssTree, visitors);

    } catch (RecognitionException e) {
      checkInterrupted(e);
      LOG.error("Unable to parse file: " + inputFile.absolutePath());
      LOG.error(e.getMessage());
      processRecognitionException(e, sensorContext, inputFile);

    } catch (Exception e) {
      checkInterrupted(e);
      throw new AnalysisException("Unable to analyze file: " + inputFile.absolutePath(), e);
    }
    return new ArrayList<>();
  }

  private List<Issue> scanFile(InputFile inputFile, TreeImpl tree, List<TreeVisitor> visitors) {
    CssTreeVisitorContext context = new CssTreeVisitorContext(tree, inputFile.file());
    List<Issue> issues = new ArrayList<>();
    for (TreeVisitor visitor : visitors) {
      if (visitor instanceof CharsetAwareVisitor) {
        ((CharsetAwareVisitor) visitor).setCharset(fileSystem.encoding());
      }
      if (visitor instanceof CssCheck) {
        issues.addAll(((CssCheck) visitor).scanFile(context));
      } else {
        visitor.scanTree(context);
      }
    }
    return issues;
  }

  private void saveSingleFileIssues(List<Issue> issues) {
    issues.forEach(issueSaver::saveIssue);
  }

  private void processRecognitionException(RecognitionException e, SensorContext sensorContext, InputFile inputFile) {
    if (parsingErrorRuleKey != null) {
      NewIssue newIssue = sensorContext.newIssue();

      NewIssueLocation primaryLocation = newIssue.newLocation()
        .message(e.getMessage())
        .on(inputFile)
        .at(inputFile.selectLine(e.getLine()));

      newIssue
        .forRule(parsingErrorRuleKey)
        .at(primaryLocation)
        .save();
    }
  }

  private void setParsingErrorCheckIfActivated(List<TreeVisitor> treeVisitors) {
    for (TreeVisitor check : treeVisitors) {
      if (parsingErrorCheck() == check.getClass()) {
        parsingErrorRuleKey = checks.ruleKeyFor((CssCheck) check);
        break;
      }
    }
  }

  private static void stopProgressReport(ProgressReport progressReport, boolean success) {
    if (success) {
      progressReport.stop();
    } else {
      progressReport.cancel();
    }
  }

  private static void checkInterrupted(Exception e) {
    Throwable cause = Throwables.getRootCause(e);
    if (cause instanceof InterruptedException || cause instanceof InterruptedIOException) {
      throw new AnalysisException("Analysis cancelled", e);
    }
  }

}
