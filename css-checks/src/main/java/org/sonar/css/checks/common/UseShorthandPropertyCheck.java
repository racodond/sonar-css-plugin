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
package org.sonar.css.checks.common;

import com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.sonar.css.checks.CheckUtils;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.StandardPropertyFactory;
import org.sonar.plugins.css.api.tree.css.AtRuleTree;
import org.sonar.plugins.css.api.tree.css.PropertyTree;
import org.sonar.plugins.css.api.tree.css.RulesetTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.SubscriptionVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "shorthand",
  name = "Shorthand properties should be used whenever possible",
  priority = Priority.MINOR,
  tags = {Tags.PERFORMANCE})
@SqaleConstantRemediation("5min")
public class UseShorthandPropertyCheck extends SubscriptionVisitorCheck {

  private static final List<StandardProperty> SHORTHAND_PROPERTIES = StandardPropertyFactory.getAll()
    .stream()
    .filter(StandardProperty::isShorthand)
    .collect(Collectors.toList());

  private final Map<String, PropertyTree> declaredProperties = new HashMap<>();

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(
      Tree.Kind.RULESET,
      Tree.Kind.AT_RULE,
      Tree.Kind.STATEMENT_BLOCK,
      Tree.Kind.PROPERTY);
  }

  @Override
  public void visitNode(Tree tree) {
    if (tree.is(Tree.Kind.PROPERTY)) {
      PropertyTree propertyTree = (PropertyTree) tree;
      declaredProperties.put(propertyTree.standardProperty().getName(), propertyTree);
    } else {
      declaredProperties.clear();
    }
  }

  @Override
  public void leaveNode(Tree tree) {
    if (tree.is(Tree.Kind.RULESET, Tree.Kind.AT_RULE)) {
      SHORTHAND_PROPERTIES
        .stream()
        .filter(p -> declaredProperties.keySet().containsAll(p.getShorthandForPropertyNames()))
        .forEach(p -> createIssue(p, tree));
    }
  }

  private void createIssue(StandardProperty shorthandProperty, Tree tree) {
    Tree primaryIssueLocation;
    if (tree.is(Tree.Kind.RULESET)) {
      primaryIssueLocation = CheckUtils.rulesetIssueLocation((RulesetTree) tree);
    } else {
      primaryIssueLocation = ((AtRuleTree) tree).atKeyword();
    }

    PreciseIssue issue = addPreciseIssue(primaryIssueLocation, "Use the \"" + shorthandProperty.getName() + "\" shorthand property instead.");

    for (Iterator<Map.Entry<String, PropertyTree>> it = declaredProperties.entrySet().iterator(); it.hasNext();) {
      Map.Entry<String, PropertyTree> entry = it.next();
      if (shorthandProperty.getShorthandForPropertyNames().contains(entry.getKey())) {
        issue.secondary(entry.getValue(), "\"" + shorthandProperty.getName() + "\" property");
      }
    }
  }

}
