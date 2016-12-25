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
package org.sonar.css.checks.common;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.CheckUtils;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.AtRuleTree;
import org.sonar.plugins.css.api.tree.css.PropertyTree;
import org.sonar.plugins.css.api.tree.css.RulesetTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.SubscriptionVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "alphabetize-declarations",
  name = "Rule properties should be alphabetically ordered",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION})
@SqaleConstantRemediation("2min")
public class AlphabetizeDeclarationsCheck extends SubscriptionVisitorCheck {

  private List<PropertyTree> properties = new ArrayList<>();

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(
      Tree.Kind.RULESET,
      Tree.Kind.AT_RULE,
      Tree.Kind.PROPERTY);
  }

  @Override
  public void leaveNode(Tree tree) {
    if (tree.is(Tree.Kind.PROPERTY)) {
      properties.add((PropertyTree) tree);
    } else {
      PropertyTree firstUnsortedProperty = firstUnsortedProperty(properties);
      if (firstUnsortedProperty != null) {
        addIssue(tree, firstUnsortedProperty);
      }
      properties.clear();
    }
  }

  @Nullable
  // TODO : check unsorted with Guava? Ordering.natural().onResultOf(
  private PropertyTree firstUnsortedProperty(List<PropertyTree> propertyTrees) {
    for (int i = 0; i < propertyTrees.size() - 1; i++) {
      if (!propertyTrees.get(i).property().isInterpolated()
        && !propertyTrees.get(i + 1).property().isInterpolated()
        && propertyTrees.get(i).standardProperty().getName().compareTo(propertyTrees.get(i + 1).standardProperty().getName()) > 0) {
        return propertyTrees.get(i + 1);
      }
    }
    return null;
  }

  private void addIssue(Tree statementTree, PropertyTree propertyTree) {
    Tree primaryIssueLocation;
    if (statementTree.is(Tree.Kind.RULESET)) {
      primaryIssueLocation = CheckUtils.rulesetIssueLocation((RulesetTree) statementTree);
    } else {
      primaryIssueLocation = ((AtRuleTree) statementTree).atKeyword();
    }
    PreciseIssue issue = addPreciseIssue(primaryIssueLocation, "Alphabetically order these rule's properties.");
    issue.secondary(propertyTree, "First unproperly ordered property");
  }

}
