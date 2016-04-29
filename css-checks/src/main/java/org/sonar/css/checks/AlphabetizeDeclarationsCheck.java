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

import java.util.ArrayList;
import java.util.List;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.utils.CssProperties;
import org.sonar.css.checks.utils.Vendors;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

@Rule(
  key = "alphabetize-declarations",
  name = "Rule properties should be alphabetically ordered",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("2min")
public class AlphabetizeDeclarationsCheck extends SquidCheck<LexerlessGrammar> {

  private List<String> declarations = new ArrayList<>();

  @Override
  public void init() {
    subscribeTo(CssGrammar.RULESET, CssGrammar.AT_RULE, CssGrammar.DECLARATION);
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode.is(CssGrammar.DECLARATION)
      && astNode.getFirstChild(CssGrammar.PROPERTY) != null
      && !Vendors.isVendorPrefixed(astNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue())) {
      declarations.add(CssProperties.getUnhackedPropertyName(astNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue()));
    } else if (astNode.is(CssGrammar.RULESET) || astNode.is(CssGrammar.AT_RULE)) {
      if (!arePropertiesAlphabeticallyOrdered(declarations)) {
        getContext().createLineViolation(this, "Alphabetically order these rule's properties", astNode);
      }
      declarations = new ArrayList<>();
    }
  }

  private boolean arePropertiesAlphabeticallyOrdered(List<String> declarations) {
    for (int i = 0; i < declarations.size() - 1; i++) {
      if (declarations.get(i).compareToIgnoreCase(declarations.get(i + 1)) > 0) {
        return false;
      }
    }
    return true;
  }

}
