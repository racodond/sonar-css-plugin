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
package org.sonar.css.issue;

import com.google.common.base.Preconditions;
import com.sonar.sslr.api.AstNode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.rule.RuleKey;
import org.sonar.squidbridge.checks.SquidCheck;

public class PreciseIssue implements Issue {
  private final SquidCheck check;
  private final File file;
  private Double effortToFix;
  private PreciseIssueLocation primaryLocation;
  private final List<PreciseIssueLocation> secondaryLocations;

  private PreciseIssue(SquidCheck check, File file) {
    this.check = check;
    this.file = file;
    this.secondaryLocations = new ArrayList<>();
  }

  public PreciseIssue(SquidCheck check, File file, String message, AstNode primaryLocation) {
    this(check, file);
    this.primaryLocation = new PreciseIssueLocation(message, primaryLocation);
  }

  public PreciseIssue(SquidCheck check, File file, String message, int line) {
    this(check, file);
    this.primaryLocation = new PreciseIssueLocation(message, line);
  }

  public PreciseIssue(SquidCheck check, File file, String message) {
    this(check, file);
    this.primaryLocation = new PreciseIssueLocation(message);
  }

  public Double getEffortToFix() {
    return effortToFix;
  }

  public List<PreciseIssueLocation> getSecondaryLocations() {
    return secondaryLocations;
  }

  public PreciseIssueLocation getPrimaryLocation() {
    return primaryLocation;
  }

  public void setEffortToFix(Double effortToFix) {
    this.effortToFix = effortToFix;
  }

  public PreciseIssue addSecondaryLocation(String message, AstNode astNode) {
    secondaryLocations.add(new PreciseIssueLocation(message, astNode));
    return this;
  }

  public void save(Checks<SquidCheck> checks, SensorContext sensorContext) {
    FileSystem fileSystem = sensorContext.fileSystem();
    InputFile inputFile = fileSystem.inputFile(fileSystem.predicates().is(file));
    inputFile = Preconditions.checkNotNull(inputFile);

    RuleKey ruleKey = checks.ruleKey(check);

    NewIssue issue = sensorContext.newIssue();
    issue
      .forRule(ruleKey)
      .effortToFix(effortToFix);

    if (primaryLocation.getStartLine() != 0) {
      if (primaryLocation.getEndLine() != 0) {
        issue.at(issue.newLocation()
          .on(inputFile)
          .at(inputFile.newRange(primaryLocation.getStartLine(), primaryLocation.getStartColumn(), primaryLocation.getEndLine(), primaryLocation.getEndColumn()))
          .message(primaryLocation.getMessage()));
      } else {
        issue.at(issue.newLocation()
          .on(inputFile)
          .at(inputFile.selectLine(primaryLocation.getStartLine()))
          .message(primaryLocation.getMessage()));
      }
    } else {
      issue.at(issue.newLocation()
        .on(inputFile)
        .message(primaryLocation.getMessage()));
    }

    for (PreciseIssueLocation preciseIssueLocation : secondaryLocations) {
      issue.addLocation(issue.newLocation()
        .on(inputFile)
        .at(inputFile.newRange(preciseIssueLocation.getStartLine(), preciseIssueLocation.getStartColumn(), preciseIssueLocation.getEndLine(), preciseIssueLocation.getEndColumn()))
        .message(preciseIssueLocation.getMessage()));
    }

    issue.save();
  }
}
