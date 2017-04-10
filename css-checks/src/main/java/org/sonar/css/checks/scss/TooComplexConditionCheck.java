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
package org.sonar.css.checks.scss;

import com.google.common.annotations.VisibleForTesting;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.scss.ScssConditionTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "too-complex-condition",
  name = "Conditions should not be too complex",
  priority = Priority.MAJOR,
  tags = {Tags.UNDERSTANDABILITY})
@SqaleConstantRemediation("15min")
@ActivatedByDefault
public class TooComplexConditionCheck extends DoubleDispatchVisitorCheck {

  private static final int DEFAULT_MAX_CONDITIONAL_OPERATORS = 3;

  @RuleProperty(
    key = "Max",
    description = "The maximum allowed number of conditional operators per condition",
    defaultValue = "" + DEFAULT_MAX_CONDITIONAL_OPERATORS)
  private int max = DEFAULT_MAX_CONDITIONAL_OPERATORS;

  @Override
  public void visitScssCondition(ScssConditionTree tree) {
    if (tree.complexity() > max) {
      addPreciseIssue(tree, "Reduce the number of conditional operators (" + tree.complexity() + ") "
        + "of this condition (maximum allowed " + max + ").");
    }

    super.visitScssCondition(tree);
  }

  @VisibleForTesting
  void setMax(int max) {
    this.max = max;
  }

}
