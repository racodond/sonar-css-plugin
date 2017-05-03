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
package org.sonar.css.checks.less;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayDeque;
import java.util.Deque;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.checks.CheckUtils;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.RulesetTree;
import org.sonar.plugins.css.api.tree.css.StyleSheetTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "nested-rulesets",
  name = "Rulesets should not be nested too deeply",
  priority = Priority.MAJOR,
  tags = {Tags.UNDERSTANDABILITY})
@SqaleConstantRemediation("20min")
@ActivatedByDefault
public class NestedRulesetsCheck extends DoubleDispatchVisitorCheck {

  private static final int DEFAULT_MAX_NESTING_LEVEL = 3;
  private Deque<Tree> stack = new ArrayDeque<>();

  @RuleProperty(
    key = "Max",
    description = "The maximum allowed nesting level",
    defaultValue = "" + DEFAULT_MAX_NESTING_LEVEL)
  private int max = DEFAULT_MAX_NESTING_LEVEL;

  @Override
  public void visitStyleSheet(StyleSheetTree tree) {
    stack.clear();
    super.visitStyleSheet(tree);
  }

  @Override
  public void visitRuleset(RulesetTree tree) {
    increaseAndCheckNestedLevel(CheckUtils.rulesetIssueLocation(tree));
    super.visitRuleset(tree);
    decreaseNestedLevel();
  }

  @VisibleForTesting
  void setMax(int max) {
    this.max = max;
  }

  private void increaseAndCheckNestedLevel(Tree tree) {
    if (stack.size() == max) {
      PreciseIssue issue = addPreciseIssue(tree, String.format("Refactor this code to not nest more than %s rulesets.", max));
      stack.forEach(t -> issue.secondary(t, "+1"));
    }
    stack.push(tree);
  }

  private void decreaseNestedLevel() {
    stack.pop();
  }

}
