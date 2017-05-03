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

import com.google.common.collect.ImmutableList;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.css.model.atrule.standard.*;
import org.sonar.plugins.css.api.tree.css.AtRuleTree;
import org.sonar.plugins.css.api.tree.css.RulesetTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "empty-rules",
  name = "Empty rules should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.PITFALL})
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class EmptyRuleCheck extends DoubleDispatchVisitorCheck {

  private static final ImmutableList<Class> AT_RULES_NOT_REQUIRING_BLOCK = ImmutableList.of(
    Charset.class,
    CustomMedia.class,
    Import.class,
    Namespace.class,
    Viewport.class);

  @Override
  public void visitRuleset(RulesetTree tree) {
    if (tree.block().content().isEmpty()) {
      addIssue(tree);
    }
    super.visitRuleset(tree);
  }

  @Override
  public void visitAtRule(AtRuleTree tree) {
    if (tree.block() != null
      && tree.block().content().isEmpty()
      && !AT_RULES_NOT_REQUIRING_BLOCK.contains(tree.standardAtRule().getClass())) {
      addIssue(tree);
    }
    super.visitAtRule(tree);
  }

  private void addIssue(Tree tree) {
    addPreciseIssue(tree, "Remove this empty rule.");
  }

}
