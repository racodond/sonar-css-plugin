/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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
package org.sonar.css.checks.scss;

import com.google.common.annotations.VisibleForTesting;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.StyleSheetTree;
import org.sonar.plugins.css.api.tree.scss.*;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.ArrayDeque;
import java.util.Deque;

@Rule(
  key = "nested-control-flow-directives",
  name = "Control flow directives @if, @else if, @else, @for, @while, and @each should not be nested too deeply",
  priority = Priority.MAJOR,
  tags = {Tags.UNDERSTANDABILITY})
@SqaleConstantRemediation("20min")
@ActivatedByDefault
public class NestedControlFlowDirectivesCheck extends DoubleDispatchVisitorCheck {

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
  public void visitScssIf(ScssIfTree tree) {
    increaseAndCheckNestedLevel(tree);
    super.visitScssIf(tree);
    decreaseNestedLevel();
  }

  @Override
  public void visitScssElseIf(ScssElseIfTree tree) {
    increaseAndCheckNestedLevel(tree);
    super.visitScssElseIf(tree);
    decreaseNestedLevel();
  }

  @Override
  public void visitScssElse(ScssElseTree tree) {
    increaseAndCheckNestedLevel(tree);
    super.visitScssElse(tree);
    decreaseNestedLevel();
  }

  @Override
  public void visitScssFor(ScssForTree tree) {
    increaseAndCheckNestedLevel(tree);
    super.visitScssFor(tree);
    decreaseNestedLevel();
  }

  @Override
  public void visitScssWhile(ScssWhileTree tree) {
    increaseAndCheckNestedLevel(tree);
    super.visitScssWhile(tree);
    decreaseNestedLevel();
  }

  @Override
  public void visitScssEach(ScssEachTree tree) {
    increaseAndCheckNestedLevel(tree);
    super.visitScssEach(tree);
    decreaseNestedLevel();
  }

  @VisibleForTesting
  public void setMax(int max) {
    this.max = max;
  }

  private void increaseAndCheckNestedLevel(Tree tree) {
    if (stack.size() == max && !(tree instanceof ScssElseIfTree) && !(tree instanceof ScssElseTree)) {
      PreciseIssue issue = addPreciseIssue(
        ((ScssDirectiveConditionBlockTree) tree).directive(),
        String.format("Refactor this code to not nest more than %s control flow directives.", max));

      stack.forEach(t -> issue.secondary(
        t instanceof ScssDirectiveConditionBlockTree ? ((ScssDirectiveConditionBlockTree) t).directive() : ((ScssElseTree) t).directive(),
        "+1"));
    }
    stack.push(tree);
  }

  private void decreaseNestedLevel() {
    stack.pop();
  }

}
