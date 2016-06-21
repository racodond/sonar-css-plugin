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
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.model.Property;
import org.sonar.css.model.Vendor;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.StandardPropertyFactory;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "compatible-vendor-prefixes",
  name = "Missing vendor prefixes should be added to experimental properties",
  priority = Priority.MAJOR,
  tags = {Tags.BROWSER_COMPATIBILITY})
@SqaleConstantRemediation("10min")
public class CompatibleVendorPrefixes extends CssCheck {

  private final Map<String, Set<Vendor>> properties = new HashMap<>();

  @Override
  public void init() {
    subscribeTo(CssGrammar.RULESET, CssGrammar.AT_RULE, CssGrammar.DECLARATION);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(CssGrammar.RULESET) || astNode.is(CssGrammar.AT_RULE)) {
      properties.clear();
    } else if (astNode.is(CssGrammar.DECLARATION)) {
      Property property = new Property(astNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue());
      if (property.isVendorPrefixed()) {
        String cssPropertyName = property.getStandardProperty().getName();
        Vendor vendorPrefix = property.getVendorPrefix();
        if (properties.containsKey(cssPropertyName)) {
          properties.get(cssPropertyName).add(vendorPrefix);
        } else {
          properties.put(cssPropertyName, new HashSet<>());
          properties.get(cssPropertyName).add(vendorPrefix);
        }
      }
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    List<String> missingVendorPrefixes;
    if (astNode.is(CssGrammar.RULESET)) {
      for (Entry<String, Set<Vendor>> props : properties.entrySet()) {
        StandardProperty p = StandardPropertyFactory.getByName(props.getKey());
        missingVendorPrefixes = p.getVendors()
          .stream()
          .filter(vendor -> !props.getValue().contains(vendor))
          .map(Vendor::getPrefix)
          .sorted()
          .collect(Collectors.toList());

        if (!missingVendorPrefixes.isEmpty()) {
          createIssue(p.getName(), missingVendorPrefixes.stream().sorted().collect(Collectors.toList()), astNode);
        }
      }
    }
  }

  private void createIssue(String propertyName, List<String> missingVendorPrefixes, AstNode ruleSetNode) {

    StringBuilder message = new StringBuilder("Define the missing vendor prefixes: ");
    for (String missingVendorPrefix : missingVendorPrefixes) {
      message.append(missingVendorPrefix).append(" ");
    }
    message.append("for the \"").append(propertyName).append("\" property.");

    AstNode primaryLocation;
    if (ruleSetNode.getFirstChild(CssGrammar.SELECTOR) != null) {
      primaryLocation = ruleSetNode.getFirstChild(CssGrammar.SELECTOR);
    } else {
      primaryLocation = ruleSetNode.getFirstChild(CssGrammar.BLOCK).getFirstChild(CssGrammar.OPEN_CURLY_BRACE);
    }

    addIssue(this, message.toString(), primaryLocation);

  }

}
