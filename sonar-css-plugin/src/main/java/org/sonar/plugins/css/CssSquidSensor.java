/*
 * SonarQube CSS Plugin
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
package org.sonar.plugins.css;

import com.google.common.collect.Lists;

import java.io.File;
import java.util.Collection;

import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.config.Settings;
import org.sonar.api.issue.Issuable;
import org.sonar.api.issue.Issue;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.resources.Project;
import org.sonar.api.rule.RuleKey;
import org.sonar.css.CssAstScanner;
import org.sonar.css.CssConfiguration;
import org.sonar.css.api.CssMetric;
import org.sonar.css.ast.visitors.SonarComponents;
import org.sonar.css.checks.CheckList;
import org.sonar.plugins.css.core.Css;
import org.sonar.squidbridge.AstScanner;
import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.squidbridge.api.CheckMessage;
import org.sonar.squidbridge.api.SourceCode;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.indexer.QueryByType;
import org.sonar.sslr.parser.LexerlessGrammar;

public class CssSquidSensor implements Sensor {

  private final CheckFactory checkFactory;
  private final NoSonarFilter noSonarFilter;

  private SensorContext context;
  private AstScanner<LexerlessGrammar> scanner;
  private final SonarComponents sonarComponents;
  private final FileSystem fs;
  private final Settings settings;
  private final CssConfiguration configuration;

  public CssSquidSensor(RulesProfile profile, SonarComponents sonarComponents, FileSystem fs, CheckFactory checkFactory, NoSonarFilter noSonarFilter, Settings settings) {
    this.checkFactory = checkFactory;
    this.sonarComponents = sonarComponents;
    this.fs = fs;
    this.noSonarFilter = noSonarFilter;
    this.settings = settings;
    this.configuration = createConfiguration();
  }

  @Override
  public boolean shouldExecuteOnProject(Project project) {
    return filesToAnalyze().iterator().hasNext();
  }

  @Override
  public void analyse(Project project, SensorContext context) {
    this.context = context;

    Checks<SquidAstVisitor> checks = checkFactory.<SquidAstVisitor>create(CheckList.REPOSITORY_KEY).addAnnotatedChecks(CheckList.getChecks());
    Collection<SquidAstVisitor> checkList = checks.all();
    CssConfiguration conf = createConfiguration();
    this.scanner = CssAstScanner.create(conf, sonarComponents, checkList.toArray(new SquidAstVisitor[checkList.size()]));
    scanner.scanFiles(Lists.newArrayList(filesToAnalyze()));

    Collection<SourceCode> squidSourceFiles = scanner.getIndex().search(
      new QueryByType(SourceFile.class));
    save(squidSourceFiles, checks);
  }

  private Iterable<File> filesToAnalyze() {
    return fs.files(fs.predicates().and(fs.predicates().hasLanguage(Css.KEY), fs.predicates().hasType(Type.MAIN)));
  }

  private void save(Collection<SourceCode> squidSourceFiles, Checks<SquidAstVisitor> checks) {
    for (SourceCode squidSourceFile : squidSourceFiles) {
      SourceFile squidFile = (SourceFile) squidSourceFile;

      InputFile sonarFile = fs.inputFile(fs.predicates().hasAbsolutePath(squidFile.getKey()));

      if (sonarFile != null) {
        noSonarFilter.addComponent(((DefaultInputFile) sonarFile).key(), squidFile.getNoSonarTagLines());
      }

      saveMeasures(sonarFile, squidFile);
      saveIssues(sonarFile, squidFile, checks);
    }
  }

  private void saveMeasures(InputFile sonarFile, SourceFile squidFile) {
    context.saveMeasure(sonarFile, CoreMetrics.LINES, squidFile.getDouble(CssMetric.LINES));
    context.saveMeasure(sonarFile, CoreMetrics.NCLOC, squidFile.getDouble(CssMetric.LINES_OF_CODE));
    context.saveMeasure(sonarFile, CoreMetrics.STATEMENTS, squidFile.getDouble(CssMetric.STATEMENTS));
    context.saveMeasure(sonarFile, CoreMetrics.COMMENT_LINES, squidFile.getDouble(CssMetric.COMMENT_LINES));
    if (configuration.getComputeComplexity()) {
      context.saveMeasure(sonarFile, CoreMetrics.FUNCTIONS, squidFile.getDouble(CssMetric.FUNCTIONS));
      context.saveMeasure(sonarFile, CoreMetrics.COMPLEXITY, squidFile.getDouble(CssMetric.COMPLEXITY));
      context.saveMeasure(sonarFile, CoreMetrics.FUNCTION_COMPLEXITY,
        squidFile.getDouble(CssMetric.FUNCTIONS) != 0 ? squidFile.getDouble(CssMetric.COMPLEXITY) / squidFile.getDouble(CssMetric.FUNCTIONS) : 0);
    }
  }

  private void saveIssues(InputFile sonarFile, SourceFile squidFile, Checks<SquidAstVisitor> checks) {
    Collection<CheckMessage> messages = squidFile.getCheckMessages();
    if (messages != null) {
      for (CheckMessage message : messages) {
        RuleKey activeRule = checks.ruleKey((SquidAstVisitor) message.getCheck());
        Issuable issuable = sonarComponents.getResourcePerspectives().as(Issuable.class, sonarFile);
        Issue issue = issuable.newIssueBuilder()
          .ruleKey(RuleKey.of(activeRule.repository(), activeRule.rule()))
          .line(message.getLine())
          .message(message.formatDefaultMessage())
          .effortToFix(message.getCost())
          .build();
        issuable.addIssue(issue);
      }
    }
  }

  private CssConfiguration createConfiguration() {
    CssConfiguration cssConfiguration = new CssConfiguration(fs.encoding());
    cssConfiguration.setComputeComplexity(settings.getBoolean(CssPlugin.COMPUTE_COMPLEXITY));
    return cssConfiguration;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}
