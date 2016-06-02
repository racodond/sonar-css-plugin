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

import java.util.HashSet;
import java.util.Set;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.issue.PreciseIssue;
import org.sonar.css.model.AtRule;
import org.sonar.css.model.atrule.standard.Charset;
import org.sonar.css.model.atrule.standard.Import;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

@Rule(
  key = "import-first",
  name = "@import rules should precede all other at-rules and style rules",
  priority = Priority.CRITICAL,
  tags = {Tags.BUG})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.INSTRUCTION_RELIABILITY)
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class ImportFirstCheck extends CssCheck {

  private Set<AstNode> precedingRules;

  @Override
  public void init() {
    subscribeTo(CssGrammar.AT_RULE, CssGrammar.RULESET);
  }

  @Override
  public void visitFile(AstNode astNode) {
    precedingRules = new HashSet<>();
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(CssGrammar.AT_RULE)) {
      AtRule atRule = new AtRule(astNode.getFirstChild(CssGrammar.AT_KEYWORD).getFirstChild(CssGrammar.IDENT).getTokenValue());
      if (atRule.getStandardAtRule() instanceof Import) {
        if (!precedingRules.isEmpty()) {
          createIssue(astNode);
        }
      } else if (!(atRule.getStandardAtRule() instanceof Charset)) {
        precedingRules.add(astNode.getFirstChild(CssGrammar.AT_KEYWORD));
      }
    } else {
      if (astNode.getFirstChild(CssGrammar.SELECTOR) != null) {
        precedingRules.add(astNode.getFirstChild(CssGrammar.SELECTOR));
      } else {
        precedingRules.add(astNode.getFirstChild(CssGrammar.BLOCK).getFirstChild(CssGrammar.OPEN_CURLY_BRACE));
      }
    }
  }

  private void createIssue(AstNode atRuleNode) {
    PreciseIssue issue = addIssue(
      this,
      "Move this @import rule before all the other at-rules and style rules.",
      atRuleNode.getFirstChild(CssGrammar.AT_KEYWORD));
    for (AstNode ruleNode : precedingRules) {
      issue.addSecondaryLocation("Preceding rule", ruleNode);
    }
  }

}
