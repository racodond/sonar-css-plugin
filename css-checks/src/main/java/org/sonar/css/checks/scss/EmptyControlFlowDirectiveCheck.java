/*
 * SonarQube CSS / SCSS / Less Analyzer
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
package org.sonar.css.checks.scss;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.scss.ScssDirectiveConditionBlockTree;
import org.sonar.plugins.css.api.tree.scss.ScssElseTree;
import org.sonar.plugins.css.api.visitors.SubscriptionVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.List;

@Rule(
  key = "empty-control-flow-directive",
  name = "Empty control flow directive should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.PITFALL})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class EmptyControlFlowDirectiveCheck extends SubscriptionVisitorCheck {

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(
      Tree.Kind.SCSS_IF,
      Tree.Kind.SCSS_ELSE_IF,
      Tree.Kind.SCSS_ELSE,
      Tree.Kind.SCSS_FOR,
      Tree.Kind.SCSS_WHILE,
      Tree.Kind.SCSS_EACH);
  }

  @Override
  public void visitNode(Tree tree) {
    if ((tree.is(Tree.Kind.SCSS_IF, Tree.Kind.SCSS_ELSE_IF, Tree.Kind.SCSS_FOR, Tree.Kind.SCSS_WHILE, Tree.Kind.SCSS_EACH)
      && ((ScssDirectiveConditionBlockTree) tree).block().content().isEmpty())
      || (tree.is(Tree.Kind.SCSS_ELSE) && ((ScssElseTree) tree).block().content().isEmpty())) {
      addPreciseIssue(tree, "Remove this empty control flow directive.");
    }
  }

}
