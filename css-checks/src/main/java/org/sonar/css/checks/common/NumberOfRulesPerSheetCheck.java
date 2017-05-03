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
package org.sonar.css.checks.common;

import com.google.common.annotations.VisibleForTesting;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.AtRuleTree;
import org.sonar.plugins.css.api.tree.css.RulesetTree;
import org.sonar.plugins.css.api.tree.css.StatementTree;
import org.sonar.plugins.css.api.tree.css.StyleSheetTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.FileIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleLinearRemediation;

@Rule(
  key = "sheet-too-many-rules",
  name = "Stylesheets should not contain too many rules",
  priority = Priority.MAJOR,
  tags = {Tags.DESIGN, Tags.PERFORMANCE})
@SqaleLinearRemediation(coeff = "5min", effortToFixDescription = "number of rules beyond the limit")
@ActivatedByDefault
public class NumberOfRulesPerSheetCheck extends DoubleDispatchVisitorCheck {

  private static final int DEFAULT_MAX_RULES = 500;

  @RuleProperty(
    key = "Max",
    description = "The maximum allowed number of rules per stylesheet",
    defaultValue = "" + DEFAULT_MAX_RULES)
  private int max = DEFAULT_MAX_RULES;

  private final Set<StatementTree> ruleTrees = new HashSet<>();

  @Override
  public void visitStyleSheet(StyleSheetTree tree) {
    ruleTrees.clear();

    super.visitStyleSheet(tree);

    if (ruleTrees.size() > max) {
      addIssue();
    }
  }

  @Override
  public void visitAtRule(AtRuleTree tree) {
    ruleTrees.add(tree);
    super.visitAtRule(tree);
  }

  @Override
  public void visitRuleset(RulesetTree tree) {
    ruleTrees.add(tree);
    super.visitRuleset(tree);
  }

  private void addIssue() {
    int numberOfRules = ruleTrees.size();

    FileIssue issue = addFileIssue(
      MessageFormat.format("Reduce the number of rules. This sheet contains {0,number,integer} rules, "
        + "{1,number,integer} more than the {2,number,integer} allowed rules.", numberOfRules, numberOfRules - max, max));

    ruleTrees.stream().forEach(t -> issue.secondary(t, "+1"));
    issue.cost((double) numberOfRules - max);
  }

  @VisibleForTesting
  void setMax(int max) {
    this.max = max;
  }

}
