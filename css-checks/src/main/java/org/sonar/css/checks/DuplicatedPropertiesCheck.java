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

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.css.api.tree.DeclarationsTree;
import org.sonar.plugins.css.api.tree.PropertyDeclarationTree;
import org.sonar.plugins.css.api.tree.PropertyTree;
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
    Map<String, List<PropertyDeclarationTree>> propertiesMap = getPropertiesMap(propertyDeclarations);

    Iterator<PropertyDeclarationTree> iterator = propertyDeclarations.iterator();
    while (iterator.hasNext()) {
      PropertyDeclarationTree propertyDeclarationToCompare = iterator.next();
      iterator.remove();
      PropertyTree propertyToCompare = propertyDeclarationToCompare.property();
      String propertyToCompareName = propertyToCompare.unhackedFullName();
      if (propertiesMap.containsKey(propertyToCompareName)
        && (existSamePropertyWithSameValue(propertyDeclarationToCompare)
          || existSamePropertyWithDifferentValue(propertyDeclarationToCompare))) {

        PreciseIssue issue = addPreciseIssue(
          propertiesMap.get(propertyToCompareName).get(0),
          "Keep only one declaration of \"" + propertyToCompareName + "\" property.");

        for (int i = 1; i < propertiesMap.get(propertyToCompareName).size(); i++) {
          issue.secondary(propertiesMap.get(propertyToCompareName).get(i), "Duplicated property");
        }
        propertiesMap.remove(propertyToCompareName);
      }
    }
  }

  private Map<String, List<PropertyDeclarationTree>> getPropertiesMap(List<PropertyDeclarationTree> declarations) {
    Map<String, List<PropertyDeclarationTree>> propertiesMap = new HashMap<>();
    String propertyName;
    for (PropertyDeclarationTree declaration : declarations) {
      propertyName = declaration.property().unhackedFullName();
      if (propertiesMap.containsKey(propertyName)) {
        propertiesMap.get(propertyName).add(declaration);
      } else {
        propertiesMap.put(propertyName, Lists.newArrayList(declaration));
      }
    }
    return propertiesMap;
  }

  private boolean existSamePropertyWithSameValue(PropertyDeclarationTree declarationToCompare) {
    PropertyTree propertyToCompare = declarationToCompare.property();
    PropertyTree currentProperty;
    for (PropertyDeclarationTree currentDeclaration : propertyDeclarations) {
      currentProperty = currentDeclaration.property();
      if (propertyToCompare.unhackedFullName().equals(currentProperty.unhackedFullName())
        && declarationToCompare.treeValue().equalsIgnoreCase(currentDeclaration.treeValue())) {
        return true;
      }
    }
    return false;
  }

  private boolean existSamePropertyWithDifferentValue(PropertyDeclarationTree declarationToCompare) {
    PropertyTree propertyToCompare = declarationToCompare.property();
    PropertyTree currentProperty;
    PropertyDeclarationTree previousDeclaration = declarationToCompare;
    for (PropertyDeclarationTree currentDeclaration : propertyDeclarations) {
      currentProperty = currentDeclaration.property();
      if (propertyToCompare.unhackedFullName().equals(currentProperty.unhackedFullName())
        && !declarationToCompare.treeValue().equalsIgnoreCase(currentDeclaration.treeValue())
        && !beforeSameProperty(previousDeclaration, currentDeclaration)) {
        return true;
      }
      previousDeclaration = currentDeclaration;
    }
    return false;
  }

  private boolean beforeSameProperty(PropertyDeclarationTree previousDeclaration, PropertyDeclarationTree currentDeclaration) {
    if (!propertyDeclarations.isEmpty()) {
      PropertyTree propertyToCompare = previousDeclaration.property();
      PropertyTree currentProperty = currentDeclaration.property();
      return propertyToCompare.unhackedFullName().equals(currentProperty.unhackedFullName());
    } else {
      return false;
    }
  }

}
