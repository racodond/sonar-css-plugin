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

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Token;

import java.util.*;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.issue.PreciseIssue;
import org.sonar.css.model.Property;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "duplicate-properties",
  name = "Duplicated properties should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.PITFALL})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class DuplicatedProperties extends CssCheck {

  private List<AstNode> declarationNodes;

  @Override
  public void init() {
    subscribeTo(CssGrammar.SUP_DECLARATION);
  }

  @Override
  public void visitNode(AstNode supDeclarationNode) {
    declarationNodes = supDeclarationNode.getChildren(CssGrammar.DECLARATION);
    Map<String, List<AstNode>> propertiesMap = getPropertiesMap(declarationNodes);

    Iterator<AstNode> iterator = declarationNodes.iterator();
    while (iterator.hasNext()) {
      AstNode declarationNodeToCompare = iterator.next();
      iterator.remove();
      Property propertyToCompare = new Property(declarationNodeToCompare.getFirstChild(CssGrammar.PROPERTY).getTokenValue());
      String propertyToCompareName = propertyToCompare.getUnhackedFullName();
      if (propertiesMap.containsKey(propertyToCompareName)
        && (existSamePropertyWithSameValue(declarationNodeToCompare)
          || existSamePropertyWithDifferentValue(declarationNodeToCompare))) {
        PreciseIssue issue = addIssue(
          this,
          "Keep only one declaration of \"" + propertyToCompareName + "\" property.",
          propertiesMap.get(propertyToCompareName).get(0));
        for (int i = 1; i < propertiesMap.get(propertyToCompareName).size(); i++) {
          issue.addSecondaryLocation("Duplicated property", propertiesMap.get(propertyToCompareName).get(i));
        }
        propertiesMap.remove(propertyToCompareName);
      }
    }
  }

  private Map<String, List<AstNode>> getPropertiesMap(List<AstNode> declarationNodes) {
    Map<String, List<AstNode>> propertiesMap = new HashMap<>();
    Property property;
    String propertyName;
    for (AstNode declarationNode : declarationNodes) {
      property = new Property(declarationNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue());
      propertyName = property.getUnhackedFullName();
      if (propertiesMap.containsKey(propertyName)) {
        propertiesMap.get(propertyName).add(declarationNode);
      } else {
        propertiesMap.put(propertyName, new ArrayList<AstNode>());
        propertiesMap.get(propertyName).add(declarationNode);
      }
    }
    return propertiesMap;
  }

  private String getDeclarationValue(AstNode declarationNode) {
    StringBuilder b = new StringBuilder();
    for (Token token : declarationNode.getFirstChild(CssGrammar.VALUE).getTokens()) {
      b.append(token.getValue());
    }
    return b.toString().toLowerCase();
  }

  private boolean existSamePropertyWithSameValue(AstNode declarationNodeToCompare) {
    Property propertyToCompare = new Property(declarationNodeToCompare.getFirstChild(CssGrammar.PROPERTY).getTokenValue());
    Property currentProperty;
    for (AstNode currentDeclarationNode : declarationNodes) {
      currentProperty = new Property(currentDeclarationNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue());
      if (propertyToCompare.getUnhackedFullName().equals(currentProperty.getUnhackedFullName())
        && getDeclarationValue(declarationNodeToCompare).equals(getDeclarationValue(currentDeclarationNode))) {
        return true;
      }
    }
    return false;
  }

  private boolean existSamePropertyWithDifferentValue(AstNode declarationNodeToCompare) {
    Property propertyToCompare = new Property(declarationNodeToCompare.getFirstChild(CssGrammar.PROPERTY).getTokenValue());
    Property currentProperty;
    AstNode previousDeclarationNode = declarationNodeToCompare;
    for (AstNode currentDeclarationNode : declarationNodes) {
      currentProperty = new Property(currentDeclarationNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue());
      if (propertyToCompare.getUnhackedFullName().equals(currentProperty.getUnhackedFullName())
        && !getDeclarationValue(declarationNodeToCompare).equals(getDeclarationValue(currentDeclarationNode))
        && !beforeSameProperty(previousDeclarationNode, currentDeclarationNode)) {
        return true;
      }
      previousDeclarationNode = currentDeclarationNode;
    }
    return false;
  }

  private boolean beforeSameProperty(AstNode previousDeclarationNode, AstNode currentDeclarationNode) {
    if (!declarationNodes.isEmpty()) {
      Property propertyToCompare = new Property(previousDeclarationNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue());
      Property currentProperty = new Property(currentDeclarationNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue());
      return propertyToCompare.getUnhackedFullName().equals(currentProperty.getUnhackedFullName());
    } else {
      return false;
    }
  }

}
