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

import java.util.*;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.issue.PreciseIssue;
import org.sonar.css.model.Property;
import org.sonar.css.model.property.UnknownProperty;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

/**
 * https://github.com/stubbornella/csslint/wiki/Require-standard-property-with-vendor-prefix
 */
@Rule(
  key = "vendor-prefix",
  name = "Standard properties should be specified along with vendor-prefixed properties",
  priority = Priority.MAJOR,
  tags = {Tags.BROWSER_COMPATIBILITY})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class VendorPrefixWithStandard extends CssCheck {

  private final Map<String, List<AstNode>> missingStandardProperties = new HashMap<>();

  @Override
  public void init() {
    subscribeTo(CssGrammar.DECLARATION, CssGrammar.SUP_DECLARATION);
  }

  @Override
  public void visitNode(AstNode node) {
    if (node.is(CssGrammar.SUP_DECLARATION)) {
      missingStandardProperties.clear();
    } else {
      AstNode propertyNode = node.getFirstChild(CssGrammar.PROPERTY);
      Property property = new Property(propertyNode.getTokenValue());
      if (!(property.getStandardProperty() instanceof UnknownProperty)
        && property.isVendorPrefixed()
        && !isNonPrefixedPropertyDefined(node, property)) {
        if (missingStandardProperties.get(property.getStandardProperty().getName()) != null) {
          missingStandardProperties.get(property.getStandardProperty().getName()).add(propertyNode);
        } else {
          missingStandardProperties.put(
            property.getStandardProperty().getName(),
            new ArrayList<>(Arrays.asList(propertyNode)));

        }
      }
    }
  }

  @Override
  public void leaveNode(AstNode node) {
    if (node.is(CssGrammar.SUP_DECLARATION)) {
      for (Map.Entry<String, List<AstNode>> missingStandardProperty : missingStandardProperties.entrySet()) {
        createIssue(missingStandardProperty.getKey(), missingStandardProperty.getValue());
      }
    }
  }

  private void createIssue(String missingStandardProperty, List<AstNode> vendorPrefixProperties) {
    PreciseIssue issue = addIssue(
      this,
      "Define the standard property after this vendor-prefixed property.",
      vendorPrefixProperties.get(0));
    for (int i = 1; i < vendorPrefixProperties.size(); i++) {
      issue.addSecondaryLocation("Missing standard " + missingStandardProperty + " property", vendorPrefixProperties.get(i));
    }
  }

  private boolean isNonPrefixedPropertyDefined(AstNode currentDeclarationNode, Property currentProperty) {
    AstNode nextDeclarationNode = currentDeclarationNode.getNextSibling();
    while (nextDeclarationNode != null) {
      if (nextDeclarationNode.getFirstChild(CssGrammar.PROPERTY) != null) {
        Property nextProperty = new Property(nextDeclarationNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue());
        if (!nextProperty.isVendorPrefixed()
          && nextProperty.getStandardProperty().getName().equals(currentProperty.getStandardProperty().getName())) {
          return true;
        }
      }
      nextDeclarationNode = nextDeclarationNode.getNextSibling();
    }
    return false;
  }
}
