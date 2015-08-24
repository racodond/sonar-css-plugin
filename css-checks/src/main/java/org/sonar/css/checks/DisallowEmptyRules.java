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

import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.AstNode;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

/**
 * https://github.com/stubbornella/csslint/wiki/Disallow-empty-rules
 *
 * @author tkende
 */
@Rule(
  key = "empty-rules",
  name = "Empty rules should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.PITFALL})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.UNDERSTANDABILITY)
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class DisallowEmptyRules extends SquidCheck<LexerlessGrammar> {

  private static final ImmutableList<String> AT_RULES_NOT_REQUIRING_DECLARATION_BLOCK = ImmutableList.of(
    "charset",
    "custom-media",
    "import",
    "namespace",
    "viewport"
    );

  int counter = 0;

  @Override
  public void init() {
    subscribeTo(CssGrammar.RULESET, CssGrammar.AT_RULE, CssGrammar.DECLARATION, CssGrammar.VARIABLE_DECLARATION);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(CssGrammar.DECLARATION) || astNode.is(CssGrammar.VARIABLE_DECLARATION)) {
      counter++;
    } else {
      counter = 0;
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (counter == 0 && (astNode.is(CssGrammar.RULESET) || isAtRuleRequiringBlock(astNode))) {
      getContext().createLineViolation(this, "Remove this empty rule", astNode);
    }
  }

  private boolean isAtRuleRequiringBlock(AstNode astNode) {
    return astNode.is(CssGrammar.AT_RULE)
      && !AT_RULES_NOT_REQUIRING_DECLARATION_BLOCK.contains(astNode.getFirstChild(CssGrammar.AT_KEYWORD).getFirstChild(CssGrammar
        .IDENT).getTokenValue());
  }
}
