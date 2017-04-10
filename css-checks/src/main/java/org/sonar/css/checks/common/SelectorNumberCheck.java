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
package org.sonar.css.checks.common;

import java.text.MessageFormat;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.SelectorTree;
import org.sonar.plugins.css.api.tree.css.StyleSheetTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.FileIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleLinearRemediation;

@Rule(
  key = "S2732",
  name = "Stylesheets should not contain too many selectors",
  priority = Priority.CRITICAL,
  tags = {Tags.BROWSER_COMPATIBILITY, Tags.BUG, Tags.DESIGN})
@ActivatedByDefault
@SqaleLinearRemediation(coeff = "10min", effortToFixDescription = "number of selectors beyond the limit")
public class SelectorNumberCheck extends DoubleDispatchVisitorCheck {

  private static final int DEFAULT_THRESHOLD = 4095;

  private int currentSelectorCount;

  @Override
  public void visitSelector(SelectorTree tree) {
    currentSelectorCount++;
    super.visitSelector(tree);
  }

  @Override
  public void visitStyleSheet(StyleSheetTree tree) {
    currentSelectorCount = 0;

    super.visitStyleSheet(tree);

    if (currentSelectorCount > DEFAULT_THRESHOLD) {
      addIssue();
    }
  }

  private void addIssue() {
    FileIssue issue = addFileIssue(
      MessageFormat.format("Reduce the number of selectors. This sheet contains {0,number,integer} selectors, "
        + "{1,number,integer} more than the {2,number,integer} maximum.",
        currentSelectorCount,
        currentSelectorCount - DEFAULT_THRESHOLD,
        DEFAULT_THRESHOLD));
    issue.cost((double) currentSelectorCount - DEFAULT_THRESHOLD);
  }

}
