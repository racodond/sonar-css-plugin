/*
 * SonarQube CSS Plugin
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
package org.sonar.css.checks;

import com.google.common.collect.ImmutableList;

import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.model.atrule.standard.*;
import org.sonar.plugins.css.api.tree.AtRuleTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.SubscriptionVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "empty-rules",
  name = "Empty rules should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.PITFALL})
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class EmptyRuleCheck extends SubscriptionVisitorCheck {

  private static final ImmutableList<Class> AT_RULES_NOT_REQUIRING_DECLARATION_BLOCK = ImmutableList.of(
    Charset.class,
    CustomMedia.class,
    Import.class,
    Namespace.class,
    Viewport.class);

  int counter;

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(
      Tree.Kind.RULESET,
      Tree.Kind.AT_RULE,
      Tree.Kind.PROPERTY_DECLARATION,
      Tree.Kind.VARIABLE_DECLARATION);
  }

  @Override
  public void visitNode(Tree tree) {
    if (tree.is(Tree.Kind.PROPERTY_DECLARATION, Tree.Kind.VARIABLE_DECLARATION)) {
      counter++;
    } else {
      counter = 0;
    }
  }

  @Override
  public void leaveNode(Tree tree) {
    if (counter == 0 && (tree.is(Tree.Kind.RULESET) || isAtRuleRequiringBlock(tree))) {
      addPreciseIssue(tree, "Remove this empty rule.");
    }
  }

  private boolean isAtRuleRequiringBlock(Tree tree) {
    return tree.is(Tree.Kind.AT_RULE)
      && !AT_RULES_NOT_REQUIRING_DECLARATION_BLOCK.contains(((AtRuleTree) tree).standardAtRule().getClass());
  }

}
