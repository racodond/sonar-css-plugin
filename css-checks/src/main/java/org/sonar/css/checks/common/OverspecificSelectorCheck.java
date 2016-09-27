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
package org.sonar.css.checks.common;

import com.google.common.annotations.VisibleForTesting;

import java.text.MessageFormat;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.CompoundSelectorTree;
import org.sonar.plugins.css.api.tree.css.SelectorTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "overspecific-selectors",
  name = "Over-specified selectors should be simplified",
  priority = Priority.MAJOR,
  tags = {Tags.DESIGN})
@SqaleConstantRemediation("2h")
@ActivatedByDefault
public class OverspecificSelectorCheck extends DoubleDispatchVisitorCheck {

  private static final int DEFAULT_NUM_LEVELS = 3;

  @RuleProperty(
    key = "deepnessThreshold",
    description = "The maximum allowed depth of a selector",
    defaultValue = "" + DEFAULT_NUM_LEVELS)
  private int deepnessThreshold = DEFAULT_NUM_LEVELS;

  @Override
  public void visitSelector(SelectorTree tree) {
    if (tree.compoundSelectors().size() > deepnessThreshold) {

      PreciseIssue issue = addPreciseIssue(
        tree,
        MessageFormat.format(
          "Simplify this over-specified selector. Maximum allowed depth: {0}. Actual depth: {1}",
          deepnessThreshold,
          tree.compoundSelectors().size()));

      for (CompoundSelectorTree compoundSelector : tree.compoundSelectors()) {
        issue.secondary(compoundSelector, "+1");
      }
    }

    super.visitSelector(tree);
  }

  @VisibleForTesting
  public void setDeepnessThreshold(int deepnessThreshold) {
    this.deepnessThreshold = deepnessThreshold;
  }

}
