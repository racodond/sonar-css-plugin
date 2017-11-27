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
package org.sonar.plugins.css.scss;

import com.google.common.collect.Lists;
import com.sonar.sslr.api.typed.ActionParser;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.config.Settings;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.css.checks.CheckList;
import org.sonar.css.checks.scss.ParsingErrorCheck;
import org.sonar.css.parser.scss.ScssParser;
import org.sonar.css.visitors.CpdVisitor;
import org.sonar.css.visitors.highlighter.ScssSyntaxHighlighterVisitor;
import org.sonar.css.visitors.metrics.scss.ScssMetricsVisitor;
import org.sonar.plugins.css.AbstractLanguageAnalyzerSensor;
import org.sonar.plugins.css.Checks;
import org.sonar.plugins.css.api.CustomRulesDefinition;
import org.sonar.plugins.css.api.CustomScssRulesDefinition;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.TreeVisitor;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ScssAnalyzerSensor extends AbstractLanguageAnalyzerSensor {

  public ScssAnalyzerSensor(FileSystem fileSystem, CheckFactory checkFactory, Settings settings, NoSonarFilter noSonarFilter) {
    super(fileSystem, checkFactory, settings, noSonarFilter);
  }

  public ScssAnalyzerSensor(FileSystem fileSystem, CheckFactory checkFactory, Settings settings, NoSonarFilter noSonarFilter,
                            @Nullable CustomScssRulesDefinition[] customRulesDefinition) {
    super(fileSystem, checkFactory, settings, noSonarFilter, customRulesDefinition);
  }

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor
      .onlyOnLanguage(ScssLanguage.KEY)
      .name("SCSS Analyzer Sensor")
      .onlyOnFileType(Type.MAIN);
  }

  @Override
  public String analyzerName() {
    return ScssLanguage.NAME;
  }

  @Override
  public String language() {
    return ScssLanguage.KEY;
  }

  @Override
  public ActionParser<Tree> parser(FileSystem fileSystem) {
    return ScssParser.createParser(fileSystem.encoding());
  }

  @Override
  public List<InputFile> filesToAnalyze(FileSystem fileSystem) {
    return StreamSupport.stream(fileSystem.inputFiles(mainFilePredicate(fileSystem)).spliterator(), false)
      .collect(Collectors.toList());
  }

  @Override
  public Checks checks(CheckFactory checkFactory, CustomRulesDefinition[] customRulesDefinitions) {
    return Checks.createCssChecks(checkFactory)
      .addChecks(CheckList.SCSS_REPOSITORY_KEY, CheckList.getScssChecks())
      .addCustomChecks(customRulesDefinitions);
  }

  @Override
  public List<TreeVisitor> treeVisitors(SensorContext sensorContext, Checks checks, NoSonarFilter noSonarFilter) {
    List<TreeVisitor> treeVisitors = Lists.newArrayList();
    treeVisitors.addAll(checks.visitorChecks());
    treeVisitors.add(new ScssSyntaxHighlighterVisitor(sensorContext));
    treeVisitors.add(new CpdVisitor(sensorContext));
    treeVisitors.add(new ScssMetricsVisitor(sensorContext, noSonarFilter));
    return treeVisitors;
  }

  @Override
  public Class parsingErrorCheck() {
    return ParsingErrorCheck.class;
  }

  private FilePredicate mainFilePredicate(FileSystem fileSystem) {
    return fileSystem.predicates()
      .and(
        fileSystem.predicates().hasType(Type.MAIN),
        fileSystem.predicates().hasLanguage(ScssLanguage.KEY));
  }

}
