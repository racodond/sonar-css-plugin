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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.css.api.tree.DeclarationsTree;
import org.sonar.plugins.css.api.tree.PropertyDeclarationTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
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
public class DuplicatedPropertiesCheck extends DoubleDispatchVisitorCheck {

  private List<PropertyDeclarationTree> propertyDeclarations;

  @Override
  public void visitDeclarations(DeclarationsTree declarationsTree) {
    propertyDeclarations = declarationsTree.propertyDeclarations();

    PropertyDeclarationTree current;
    PropertyDeclarationTree next;
    Set<PropertyDeclarationTree> duplicates = new HashSet<>();
    Set<Class> alreadyChecked = new HashSet<>();
    boolean nextSameProperty;

    for (int i = 0; i < propertyDeclarations.size() - 1; i++) {
      current = propertyDeclarations.get(i);
      if (current.property().isVendorPrefixed()) {
        continue;
      }
      if (alreadyChecked.contains(current.property().standardProperty().getClass())) {
        continue;
      }
      alreadyChecked.add(current.property().standardProperty().getClass());
      nextSameProperty = true;
      duplicates.clear();

      for (int j = i + 1; j < propertyDeclarations.size(); j++) {
        next = propertyDeclarations.get(j);

        if (current.property().standardProperty().getClass() == next.property().standardProperty().getClass()) {
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

    super.visitDeclarations(declarationsTree);
  }

}
