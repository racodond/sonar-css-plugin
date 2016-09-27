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
package org.sonar.css.visitors.metrics;

import com.google.common.collect.ImmutableList;

import java.io.Serializable;
import java.util.List;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.issue.NoSonarFilter;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.SubscriptionVisitor;

public abstract class AbstractMetricsVisitor extends SubscriptionVisitor {

  protected final SensorContext sensorContext;
  protected final NoSonarFilter noSonarFilter;
  protected InputFile inputFile;
  private final FileSystem fileSystem;

  public AbstractMetricsVisitor(SensorContext sensorContext, NoSonarFilter noSonarFilter) {
    this.sensorContext = sensorContext;
    this.fileSystem = sensorContext.fileSystem();
    this.noSonarFilter = noSonarFilter;
  }

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(Tree.Kind.STYLESHEET);
  }

  @Override
  public void visitFile(Tree tree) {
    this.inputFile = fileSystem.inputFile(fileSystem.predicates().is(getContext().getFile()));
  }

  @Override
  public void leaveFile(Tree tree) {
    LinesOfCodeVisitor linesOfCodeVisitor = new LinesOfCodeVisitor(tree);
    saveMetricOnFile(CoreMetrics.NCLOC, linesOfCodeVisitor.getNumberOfLinesOfCode());

    StatementsVisitor statementsVisitor = new StatementsVisitor(tree);
    saveMetricOnFile(CoreMetrics.STATEMENTS, statementsVisitor.getNumberOfStatements());

    ComplexityInFunctionsVisitor complexityInFunctionsVisitor = new ComplexityInFunctionsVisitor(tree);
    saveMetricOnFile(CoreMetrics.COMPLEXITY_IN_FUNCTIONS, complexityInFunctionsVisitor.getComplexityInFunctions());

    ComplexityVisitor complexityVisitor = new ComplexityVisitor(tree);
    saveMetricOnFile(CoreMetrics.COMPLEXITY, complexityVisitor.getComplexity());

    FunctionsVisitor functionsVisitor = new FunctionsVisitor(tree);
    saveMetricOnFile(CoreMetrics.FUNCTIONS, functionsVisitor.getNumberOfFunctions());
  }

  protected <T extends Serializable> void saveMetricOnFile(Metric metric, T value) {
    sensorContext.<T>newMeasure()
      .withValue(value)
      .forMetric(metric)
      .on(inputFile)
      .save();
  }

}
