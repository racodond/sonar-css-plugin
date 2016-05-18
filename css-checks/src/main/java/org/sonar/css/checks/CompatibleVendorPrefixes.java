/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende and David RACODON
 * kende.tamas@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.css.checks;

import com.sonar.sslr.api.AstNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.model.Property;
import org.sonar.css.model.Vendor;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.StandardPropertyFactory;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

/**
 * https://github.com/stubbornella/csslint/wiki/Require-compatible-vendor-prefixes
 */
@Rule(
  key = "compatible-vendor-prefixes",
  name = "Missing vendor prefixes should be added to experimental CSS properties",
  priority = Priority.MAJOR,
  tags = {Tags.BROWSER_COMPATIBILITY})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class CompatibleVendorPrefixes extends SquidCheck<LexerlessGrammar> {

  Map<String, Set<Vendor>> properties = new HashMap<>();

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
          properties.put(cssPropertyName, new HashSet<Vendor>());
          properties.get(cssPropertyName).add(vendorPrefix);
        }
      }
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode.is(CssGrammar.RULESET)) {
      for (Entry<String, Set<Vendor>> props : properties.entrySet()) {
        StandardProperty p = StandardPropertyFactory.createStandardProperty(props.getKey());
        for (Vendor vendor : p.getVendors()) {
          if (!props.getValue().contains(vendor)) {
            getContext().createLineViolation(this, "Add the missing vendor prefix: " + vendor.getPrefix() + " to the property: " + props.getKey(), astNode);
          }
        }
      }
    }
  }

}
