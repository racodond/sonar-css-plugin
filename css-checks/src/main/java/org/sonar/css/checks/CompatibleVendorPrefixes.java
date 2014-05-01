/*
 * Sonar CSS Plugin
 * Copyright (C) 2013 Tamas Kende
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
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.utils.CssP;
import org.sonar.css.checks.utils.CssProperties;
import org.sonar.css.checks.utils.CssProperty;
import org.sonar.css.parser.CssGrammar;
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
@Rule(key = "compatible-vendor-prefixes", priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class CompatibleVendorPrefixes extends SquidCheck<LexerlessGrammar> {

  Map<String, Set<String>> properties = new HashMap<String, Set<String>>();

  @Override
  public void init() {
    subscribeTo(CssGrammar.ruleset, CssGrammar.atRule, CssGrammar.declaration);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(CssGrammar.ruleset) || astNode.is(CssGrammar.atRule)) {
      properties.clear();
    } else if (astNode.is(CssGrammar.declaration)) {
      String property = astNode.getFirstChild(CssGrammar.property).getTokenValue();
      if (CssProperties.isVendor(property)) {
        CssP p = CssP.factory(property);
        if (properties.containsKey(p.getName())) {
          properties.get(p.getName()).add(p.getVendor());
        } else {
          properties.put(p.getName(), new HashSet<String>());
          properties.get(p.getName()).add(p.getVendor());
        }
      }
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode != null && astNode.is(CssGrammar.ruleset)) {
      for (Entry<String, Set<String>> props : properties.entrySet()) {
        CssProperty p = CssProperties.getProperty(props.getKey());
        // If p is null it is an unknown vendor property
        if (p != null) {
          for (String vendor : p.getVendors()) {
            if (!props.getValue().contains(vendor)) {
              getContext().createLineViolation(this, "Missing vendor: -" + vendor + " for property: " + props.getKey(), astNode);
            }
          }
        }
      }
    }

  }

}
