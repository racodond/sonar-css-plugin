/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
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
package org.sonar.css.checks.css;

import java.util.ArrayList;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.AtKeywordTree;
import org.sonar.plugins.css.api.tree.css.StyleSheetTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.FileIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleLinearRemediation;

@Rule(
  key = "S2735",
  name = "Stylesheets should not \"@import\" too many other sheets",
  priority = Priority.CRITICAL,
  tags = {Tags.BROWSER_COMPATIBILITY, Tags.BUG})
@ActivatedByDefault
@SqaleLinearRemediation(coeff = "10min", effortToFixDescription = "number of imports beyond the limit")
public class ImportNumberCheck extends DoubleDispatchVisitorCheck {

  private static final int DEFAULT_THRESHOLD = 31;

  private List<AtKeywordTree> atKeywordTrees = new ArrayList<>();

  @Override
  public void visitStyleSheet(StyleSheetTree tree) {
    atKeywordTrees.clear();

    super.visitStyleSheet(tree);

    if (atKeywordTrees.size() > DEFAULT_THRESHOLD) {
      addIssue();
    }
  }

  @Override
  public void visitAtKeyword(AtKeywordTree tree) {
    if ("import".equalsIgnoreCase(tree.keyword().text())) {
      atKeywordTrees.add(tree);
    }
    super.visitAtKeyword(tree);
  }

  private void addIssue() {
    FileIssue issue = addFileIssue(
      String.format(
        "Reduce the number of @import rules. This sheet imports %s other sheets, %s more than the %s maximum allowed.",
        atKeywordTrees.size(),
        atKeywordTrees.size() - DEFAULT_THRESHOLD,
        DEFAULT_THRESHOLD));
    atKeywordTrees.stream().forEach(t -> issue.secondary(t, "+1"));
    issue.cost((double) atKeywordTrees.size() - DEFAULT_THRESHOLD);
  }

}
