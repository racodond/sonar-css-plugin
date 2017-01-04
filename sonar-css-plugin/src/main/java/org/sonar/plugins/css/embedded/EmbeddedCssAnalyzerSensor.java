/*
 * SonarQube CSS / SCSS / Less Analyzer
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

import com.google.common.collect.Lists;
import com.sonar.sslr.api.typed.ActionParser;
import org.apache.commons.lang.StringUtils;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.config.Settings;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.resources.Language;
import org.sonar.css.checks.CheckList;
import org.sonar.css.checks.css.ParsingErrorCheck;
import org.sonar.css.parser.embedded.EmbeddedCssParser;
import org.sonar.plugins.css.AbstractLanguageAnalyzerSensor;
import org.sonar.plugins.css.Checks;
import org.sonar.plugins.css.Plugin;
import org.sonar.plugins.css.api.CustomCssRulesDefinition;
import org.sonar.plugins.css.api.CustomRulesDefinition;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.TreeVisitor;
import org.sonar.plugins.css.css.CssLanguage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EmbeddedCssAnalyzerSensor extends AbstractLanguageAnalyzerSensor {

  public EmbeddedCssAnalyzerSensor(FileSystem fileSystem, CheckFactory checkFactory, Settings settings, NoSonarFilter noSonarFilter) {
    super(fileSystem, checkFactory, settings, noSonarFilter);
  }

  public EmbeddedCssAnalyzerSensor(FileSystem fileSystem, CheckFactory checkFactory, Settings settings, NoSonarFilter noSonarFilter,
                                   @Nullable CustomCssRulesDefinition[] customRulesDefinition) {
    super(fileSystem, checkFactory, settings, noSonarFilter, customRulesDefinition);
  }

  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor.name("Embedded CSS Analyzer Sensor");
  }

  @Override
  public String analyzerName() {
    return "Embedded CSS";
  }

  @Override
  public String language() {
    return CssLanguage.KEY;
  }

  @Override
  public ActionParser<Tree> parser(FileSystem fileSystem) {
    return EmbeddedCssParser.createParser(fileSystem.encoding());
  }

  @Override
  public List<InputFile> filesToAnalyze(FileSystem fileSystem) {
    return StreamSupport.stream(fileSystem.inputFiles(mainFilePredicate(fileSystem)).spliterator(), false)
      .collect(Collectors.toList());
  }

  @Override
  public Checks checks(CheckFactory checkFactory, CustomRulesDefinition[] customRulesDefinitions) {
    return Checks.createCssChecks(checkFactory)
      .addChecks(CheckList.CSS_REPOSITORY_KEY, CheckList.getEmbeddedCssChecks())
      .addCustomChecks(customRulesDefinitions);
  }

  @Override
  public List<TreeVisitor> treeVisitors(SensorContext sensorContext, Checks checks, NoSonarFilter noSonarFilter) {
    List<TreeVisitor> treeVisitors = Lists.newArrayList();
    treeVisitors.addAll(checks.visitorChecks());
    return treeVisitors;
  }

  private FilePredicate mainFilePredicate(FileSystem fileSystem) {
    String[] suffixes = getSettings().getStringArray(Plugin.EMBEDDED_CSS_FILE_SUFFIXES_KEY);
    if (suffixes == null || suffixes.length == 0) {
      suffixes = StringUtils.split(Plugin.CSS_FILE_SUFFIXES_DEFAULT_VALUE, ",");
    }

    List<FilePredicate> filePredicates = new ArrayList<>();
    for (String suffix : suffixes) {
      filePredicates.add(fileSystem.predicates().matchesPathPattern("**/*." + suffix));
    }

    return fileSystem.predicates().or(filePredicates);
  }

  @Override
  public Class parsingErrorCheck() {
    return ParsingErrorCheck.class;
  }

}
