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

import com.google.common.base.Preconditions;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.TextRange;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.sonar.plugins.css.api.CssCheck;
import org.sonar.plugins.css.api.visitors.issue.*;

public class IssueSaver {

  private final SensorContext sensorContext;
  private final Checks checks;
  private final FileSystem fileSystem;

  public IssueSaver(SensorContext sensorContext, Checks checks) {
    this.sensorContext = sensorContext;
    this.fileSystem = sensorContext.fileSystem();
    this.checks = checks;
  }

  public void saveIssue(Issue issue) {
    if (issue instanceof FileIssue) {
      saveFileIssue((FileIssue) issue);
    } else if (issue instanceof LineIssue) {
      saveLineIssue((LineIssue) issue);
    } else {
      savePreciseIssue((PreciseIssue) issue);
    }
  }

  private void savePreciseIssue(PreciseIssue issue) {
    NewIssue newIssue = sensorContext.newIssue();
    InputFile primaryFile = Preconditions.checkNotNull(fileSystem.inputFile(fileSystem.predicates().is(issue.primaryLocation().file())));

    newIssue
      .forRule(ruleKey(issue.check()))
      .at(newLocation(primaryFile, newIssue, issue.primaryLocation()));

    if (issue.cost() != null) {
      newIssue.gap(issue.cost());
    }

    InputFile secondaryFile;
    for (IssueLocation secondary : issue.secondaryLocations()) {
      secondaryFile = fileSystem.inputFile(fileSystem.predicates().is(secondary.file()));
      if (secondaryFile == null) {
        secondaryFile = primaryFile;
      }
      newIssue.addLocation(newLocation(secondaryFile, newIssue, secondary));
    }

    newIssue.save();
  }

  private void saveFileIssue(FileIssue issue) {
    NewIssue newIssue = sensorContext.newIssue();
    InputFile primaryFile = Preconditions.checkNotNull(fileSystem.inputFile(fileSystem.predicates().is(issue.file())));

    NewIssueLocation primaryLocation = newIssue.newLocation()
      .message(issue.message())
      .on(primaryFile);

    newIssue
      .forRule(ruleKey(issue.check()))
      .at(primaryLocation);

    if (issue.cost() != null) {
      newIssue.gap(issue.cost());
    }

    InputFile secondaryFile;
    for (IssueLocation secondary : issue.secondaryLocations()) {
      secondaryFile = fileSystem.inputFile(fileSystem.predicates().is(secondary.file()));
      if (secondaryFile == null) {
        secondaryFile = primaryFile;
      }
      newIssue.addLocation(newLocation(secondaryFile, newIssue, secondary));
    }

    newIssue.save();
  }

  private void saveLineIssue(LineIssue issue) {
    NewIssue newIssue = sensorContext.newIssue();
    InputFile primaryFile = Preconditions.checkNotNull(fileSystem.inputFile(fileSystem.predicates().is(issue.file())));

    NewIssueLocation primaryLocation = newIssue.newLocation()
      .message(issue.message())
      .on(primaryFile)
      .at(primaryFile.selectLine(issue.line()));

    newIssue
      .forRule(ruleKey(issue.check()))
      .at(primaryLocation);

    if (issue.cost() != null) {
      newIssue.gap(issue.cost());
    }

    newIssue.save();
  }

  private NewIssueLocation newLocation(InputFile inputFile, NewIssue issue, IssueLocation location) {
    TextRange range = inputFile.newRange(
      location.startLine(), location.startLineOffset(), location.endLine(), location.endLineOffset());

    NewIssueLocation newLocation = issue.newLocation()
      .on(inputFile)
      .at(range);

    if (location.message() != null) {
      newLocation.message(location.message());
    }
    return newLocation;
  }

  private RuleKey ruleKey(CssCheck check) {
    Preconditions.checkNotNull(check);
    RuleKey ruleKey = checks.ruleKeyFor(check);
    if (ruleKey == null) {
      throw new IllegalStateException("No rule key found for a rule");
    }
    return ruleKey;
  }

}
