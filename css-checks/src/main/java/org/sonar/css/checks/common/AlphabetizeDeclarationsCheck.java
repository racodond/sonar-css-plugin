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

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.PropertyDeclarationTree;
import org.sonar.plugins.css.api.tree.css.PropertyTree;
import org.sonar.plugins.css.api.tree.css.StatementBlockTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Rule(
  key = "alphabetize-declarations",
  name = "Rule properties should be alphabetically ordered",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION})
@SqaleConstantRemediation("2min")
public class AlphabetizeDeclarationsCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitStatementBlock(StatementBlockTree tree) {
    PropertyTree firstUnsortedProperty = firstUnsortedProperty(
      tree.propertyDeclarations()
        .stream()
        .map(PropertyDeclarationTree::property)
        .collect(Collectors.toList()));

    if (firstUnsortedProperty != null) {
      addIssue(tree, firstUnsortedProperty);
    }

    super.visitStatementBlock(tree);
  }

  @Nullable
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

  private void addIssue(StatementBlockTree statementBlockTree, PropertyTree propertyTree) {
    PreciseIssue issue = addPreciseIssue(statementBlockTree, "Alphabetically order these rule's properties.");
    issue.secondary(propertyTree, "First unproperly ordered property");
  }

}
