/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende and David RACODON
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
package org.sonar.css.checks.verifier;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.collect.Ordering;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.sonar.css.CssAstScanner;
import org.sonar.css.CssCheck;
import org.sonar.css.CssConfiguration;
import org.sonar.css.issue.Issue;
import org.sonar.css.issue.PreciseIssue;
import org.sonar.css.issue.PreciseIssueLocation;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

public class CssCheckVerifier {

  private CssCheckVerifier() {
  }

  public static void verify(CssCheck check, File file) {
    verify(check, file, new CssConfiguration(Charsets.UTF_8));
  }

  public static void verify(CssCheck check, File file, CssConfiguration configuration) {
    CssAstScanner.scanSingleFileWithCustomConfiguration(file, configuration, check);
    Iterator<Issue> actualIssues = getActualIssues(check);

    TestIssueCheck testIssueCheck = new TestIssueCheck();
    CssAstScanner.scanSingleFileWithCustomConfiguration(file, configuration, testIssueCheck);
    List<TestIssue> expectedIssues = getExpectedIssues(testIssueCheck);

    for (TestIssue expected : expectedIssues) {
      if (actualIssues.hasNext()) {
        verifyIssue(expected, actualIssues.next(), file);
      } else {
        throw new AssertionError(fullMessage("Missing issue at line " + expected.line(), file));
      }
    }

    if (actualIssues.hasNext()) {
      Issue issue = actualIssues.next();
      throw new AssertionError(fullMessage("Unexpected issue at line " + line(issue) + ": \"" + message(issue) + "\"", file));
    }
  }

  private static List<TestIssue> getExpectedIssues(TestIssueCheck check) {
    Set<TestIssue> issues = check.getTestIssues();
    List<TestIssue> sortedIssues = Ordering.natural().onResultOf(new IssueToLine()).sortedCopy(issues);
    return sortedIssues;
  }

  private static Iterator<Issue> getActualIssues(CssCheck check) {
    Set<Issue> issues = check.getIssues();
    List<Issue> sortedIssues = Ordering.natural().onResultOf(new IssueToLine()).sortedCopy(issues);
    return sortedIssues.iterator();
  }

  private static void verifyIssue(TestIssue expected, Issue actual, File file) {
    if (line(actual) > expected.line()) {
      fail(fullMessage("Missing issue at line " + expected.line(), file));
    }
    if (line(actual) < expected.line()) {
      fail(fullMessage("Unexpected issue at line " + line(actual) + ": \"" + message(actual) + "\"", file));
    }
    if (expected.message() != null) {
      assertThat(message(actual)).as(fullMessage("Bad message at line " + expected.line(), file)).isEqualTo(expected.message());
    }
    if (expected.effortToFix() != null) {
      assertThat(((PreciseIssue) actual).getEffortToFix()).as(fullMessage("Bad effortToFix at line " + expected.line(), file)).isEqualTo(expected.effortToFix());
    }
    if (expected.startColumn() != null) {
      assertThat(((PreciseIssue) actual).getPrimaryLocation().getStartColumn() + 1).as(fullMessage("Bad start column at line " + expected.line(), file))
        .isEqualTo(expected.startColumn());
    }
    if (expected.endColumn() != null) {
      assertThat(((PreciseIssue) actual).getPrimaryLocation().getEndColumn() + 1).as(fullMessage("Bad end column at line " + expected.line(), file))
        .isEqualTo(expected.endColumn());
    }
    if (expected.endLine() != null) {
      assertThat(((PreciseIssue) actual).getPrimaryLocation().getEndLine()).as(fullMessage("Bad end line at line " + expected.line(), file)).isEqualTo(expected.endLine());
    }
    if (expected.secondaryLines() != null) {
      assertThat(secondary(actual)).as(fullMessage("Bad secondary locations at line " + expected.line(), file)).isEqualTo(expected.secondaryLines());
    }
  }

  private static class IssueToLine implements Function<Issue, Integer> {
    @Override
    public Integer apply(Issue issue) {
      return line(issue);
    }
  }

  private static int line(Issue issue) {
    if (issue instanceof TestIssue) {
      return ((TestIssue) issue).line();
    } else if (issue instanceof PreciseIssue) {
      return ((PreciseIssue) issue).getPrimaryLocation().getStartLine();
    } else {
      throw new IllegalStateException("Unsupported type of issue.");
    }
  }

  private static String message(Issue issue) {
    if (issue instanceof TestIssue) {
      return ((TestIssue) issue).message();
    } else if (issue instanceof PreciseIssue) {
      return ((PreciseIssue) issue).getPrimaryLocation().getMessage();
    } else {
      throw new IllegalStateException("Unsupported type of issue.");
    }
  }

  private static List<Integer> secondary(Issue issue) {
    List<Integer> result = new ArrayList<>();
    if (issue instanceof PreciseIssue) {
      for (PreciseIssueLocation issueLocation : ((PreciseIssue) issue).getSecondaryLocations()) {
        result.add(issueLocation.getStartLine());
      }
    } else {
      throw new IllegalStateException("Unsupported type of issue.");
    }
    return Ordering.natural().sortedCopy(result);
  }

  private static String fullMessage(String message, File file) {
    return message + " on file " + file.getPath();
  }

}
