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

import com.google.common.collect.ImmutableList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.PropertyDeclarationTree;
import org.sonar.plugins.css.api.tree.css.StatementBlockTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.SubscriptionVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "duplicate-properties",
  name = "Duplicated properties should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.PITFALL})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class DuplicatedPropertiesCheck extends SubscriptionVisitorCheck {

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(
      Tree.Kind.RULESET_BLOCK,
      Tree.Kind.AT_RULE_BLOCK);
  }

  @Override
  public void visitNode(Tree tree) {
    List<PropertyDeclarationTree> propertyDeclarations = ((StatementBlockTree) tree).propertyDeclarations();

    PropertyDeclarationTree current;
    PropertyDeclarationTree next;
    Set<PropertyDeclarationTree> duplicates = new HashSet<>();
    Set<String> alreadyChecked = new HashSet<>();
    boolean nextSameProperty;

    for (int i = 0; i < propertyDeclarations.size() - 1; i++) {
      current = propertyDeclarations.get(i);
      if (current.property().isVendorPrefixed()) {
        continue;
      }
      if (alreadyChecked.contains(current.property().standardProperty().getName())) {
        continue;
      }
      alreadyChecked.add(current.property().standardProperty().getName());
      nextSameProperty = true;
      duplicates.clear();

      for (int j = i + 1; j < propertyDeclarations.size(); j++) {
        next = propertyDeclarations.get(j);

        if (current.property().standardProperty().getName().equals(next.property().standardProperty().getName())
          && !next.property().isVendorPrefixed()) {
          if (current.value().treeValue().equalsIgnoreCase(next.value().treeValue()) || !nextSameProperty) {
            duplicates.add(next);
          }
        } else {
          nextSameProperty = false;
        }
      }

      if (!duplicates.isEmpty()) {
        PreciseIssue issue = addPreciseIssue(
          current.property(),
          "Keep only one declaration of \"" + current.property().standardProperty().getName() + "\" property.");

        duplicates.stream().forEach(d -> issue.secondary(d.property(), "Duplicated property"));
      }
    }
  }

}
