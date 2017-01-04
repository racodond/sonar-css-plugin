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
import com.google.common.collect.ImmutableMap;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.css.PropertyDeclarationTree;
import org.sonar.plugins.css.api.tree.css.StatementBlockTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.List;
import java.util.Map;

@Rule(
  key = "display-property-grouping",
  name = "Properties that do not work with the \"display\" property should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.PERFORMANCE, Tags.PITFALL})
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class DisplayPropertyGroupingCheck extends DoubleDispatchVisitorCheck {

  private static final Map<String, ImmutableList<String>> RULES = ImmutableMap.of(
    "inline", ImmutableList.of("width", "height", "margin", "margin-top", "margin-bottom", "float"),
    "inline-block", ImmutableList.of("float"),
    "block", ImmutableList.of("vertical-align"),
    "table-*", ImmutableList.of("margin", "margin-top", "margin-bottom", "margin-left", "margin-right", "float"));

  private PropertyDeclarationTree displayDeclaration;
  private List<String> propertiesToNotUse;

  @Override
  public void visitStatementBlock(StatementBlockTree tree) {
    List<PropertyDeclarationTree> propertyDeclarations = tree.propertyDeclarations();
    setPropertiesToNotUse(propertyDeclarations);
    if (propertiesToNotUse != null) {
      addIssues(propertyDeclarations, propertiesToNotUse);
    }
    super.visitStatementBlock(tree);
  }

  private void setPropertiesToNotUse(List<PropertyDeclarationTree> propertyDeclarationTrees) {
    propertiesToNotUse = null;
    for (PropertyDeclarationTree declaration : propertyDeclarationTrees) {

      if ("display".equals(declaration.property().standardProperty().getName())
        && declaration.isValid(this.getContext().getLanguage())
        && declaration.value().sanitizedValueElements().get(0) instanceof IdentifierTree) {

        displayDeclaration = declaration;
        String displayValue = ((IdentifierTree) declaration.value().sanitizedValueElements().get(0)).text();

        if (displayValue.startsWith("table-")) {
          propertiesToNotUse = RULES.get("table-*");
        } else {
          propertiesToNotUse = RULES.get(displayValue);
        }

        return;
      }
    }
  }

  private void addIssues(List<PropertyDeclarationTree> propertyDeclarationTrees, List<String> propertiesToNotUse) {
    for (PropertyDeclarationTree declaration : propertyDeclarationTrees) {
      String propertyName = declaration.property().standardProperty().getName();
      if (propertiesToNotUse.contains(propertyName)) {
        PreciseIssue issue = addPreciseIssue(
          declaration,
          "Remove this \"" + propertyName + "\" declaration that does not work with the \"display\" declaration.");
        issue.secondary(displayDeclaration, "\"display\" property declaration");
      }
    }
  }

}
