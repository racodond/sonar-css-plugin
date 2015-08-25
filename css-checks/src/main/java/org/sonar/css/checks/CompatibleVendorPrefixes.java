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
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.utils.CssProperties;
import org.sonar.css.checks.utils.CssProperty;
import org.sonar.css.checks.utils.Vendors;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * https://github.com/stubbornella/csslint/wiki/Require-compatible-vendor-prefixes
 *
 * @author tkende
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

  Map<String, Set<String>> properties = new HashMap<String, Set<String>>();

  @Override
  public void init() {
    subscribeTo(CssGrammar.RULESET, CssGrammar.AT_RULE, CssGrammar.DECLARATION);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(CssGrammar.RULESET) || astNode.is(CssGrammar.AT_RULE)) {
      properties.clear();
    } else if (astNode.is(CssGrammar.DECLARATION)) {
      String property = astNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue();
      if (Vendors.isVendorPrefixed(property)) {
        String cssProperty = CssProperties.getPropertyNameWithoutVendorPrefix(property);
        String cssVendorPrefix = CssProperties.getVendorPrefix(property);
        if (properties.containsKey(cssProperty)) {
          properties.get(cssProperty).add(cssVendorPrefix);
        } else {
          properties.put(cssProperty, new HashSet<String>());
          properties.get(cssProperty).add(cssVendorPrefix);
        }
      }
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode != null && astNode.is(CssGrammar.RULESET)) {
      for (Entry<String, Set<String>> props : properties.entrySet()) {
        CssProperty p = CssProperties.getProperty(props.getKey());
        // If p is null it is an unknown vendor property
        if (p != null) {
          for (String vendor : p.getVendors()) {
            if (!props.getValue().contains(vendor)) {
              getContext().createLineViolation(this, "Add the missing vendor prefix: " + vendor + " to the property: " + props.getKey(), astNode);
            }
          }
        }
      }
    }

  }

}
