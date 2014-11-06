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
package org.sonar.plugins.css;

import com.google.common.collect.Lists;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.checks.AnnotationCheckFactory;
import org.sonar.api.issue.Issuable;
import org.sonar.api.issue.Issue;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.resources.File;
import org.sonar.api.resources.Project;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.rules.ActiveRule;
import org.sonar.api.scan.filesystem.ModuleFileSystem;
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

import java.util.Collection;
import java.util.List;

public class CssSquidSensor implements Sensor {

  private final AnnotationCheckFactory annotationCheckFactory;

  private Project project;
  private SensorContext context;
  private AstScanner<LexerlessGrammar> scanner;
  private final SonarComponents sonarComponents;
  private final ModuleFileSystem moduleFileSystem;

  public CssSquidSensor(RulesProfile profile, SonarComponents sonarComponents, ModuleFileSystem moduleFileSystem) {
    this.annotationCheckFactory = AnnotationCheckFactory.create(profile,
      CheckList.REPOSITORY_KEY, CheckList.getChecks());
    this.sonarComponents = sonarComponents;
    this.moduleFileSystem = moduleFileSystem;
  }

  @Override
  public boolean shouldExecuteOnProject(Project project) {
    return !moduleFileSystem.files(Css.SOURCE_QUERY).isEmpty();
  }

  @Override
  public void analyse(Project project, SensorContext context) {
    this.project = project;
    this.context = context;

    Collection<SquidAstVisitor<LexerlessGrammar>> squidChecks = annotationCheckFactory.getChecks();
    List<SquidAstVisitor<LexerlessGrammar>> visitors = Lists.newArrayList(squidChecks);
    CssConfiguration conf = new CssConfiguration(moduleFileSystem.sourceCharset());
    this.scanner = CssAstScanner.create(conf, sonarComponents, visitors.toArray(new SquidAstVisitor[visitors.size()]));
    scanner.scanFiles(moduleFileSystem.files(Css.SOURCE_QUERY));

    Collection<SourceCode> squidSourceFiles = scanner.getIndex().search(
      new QueryByType(SourceFile.class));
    save(squidSourceFiles);
  }

  private void save(Collection<SourceCode> squidSourceFiles) {
    for (SourceCode squidSourceFile : squidSourceFiles) {
      SourceFile squidFile = (SourceFile) squidSourceFile;

      File sonarFile = File.fromIOFile(new java.io.File(squidFile.getKey()), project);

      saveMeasures(sonarFile, squidFile);
      saveViolations(sonarFile, squidFile);
    }
  }

  private void saveMeasures(File sonarFile, SourceFile squidFile) {
    context.saveMeasure(sonarFile, CoreMetrics.FILES, squidFile.getDouble(CssMetric.FILES));
    context.saveMeasure(sonarFile, CoreMetrics.LINES, squidFile.getDouble(CssMetric.LINES));
    context.saveMeasure(sonarFile, CoreMetrics.NCLOC, squidFile.getDouble(CssMetric.LINES_OF_CODE));
    //context.saveMeasure(sonarFile, CoreMetrics.FUNCTIONS, squidFile.getDouble(CssMetric.AT_RULES) + squidFile.getDouble(CssMetric.RULE_SETS));
    context.saveMeasure(sonarFile, CoreMetrics.STATEMENTS, squidFile.getDouble(CssMetric.STATEMENTS));
    context.saveMeasure(sonarFile, CoreMetrics.COMMENT_LINES, squidFile.getDouble(CssMetric.COMMENT_LINES));
  }

  private void saveViolations(File sonarFile, SourceFile squidFile) {
    Collection<CheckMessage> messages = squidFile.getCheckMessages();
    if (messages != null) {
      for (CheckMessage message : messages) {
        ActiveRule activeRule = annotationCheckFactory.getActiveRule(message.getCheck());
        Issuable issuable = sonarComponents.getResourcePerspectives().as(Issuable.class, sonarFile);
        Issue issue = issuable.newIssueBuilder()
          .ruleKey(RuleKey.of(activeRule.getRepositoryKey(), activeRule.getRuleKey()))
          .line(message.getLine())
          .message(message.formatDefaultMessage())
          .build();
        issuable.addIssue(issue);
      }
    }
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}
