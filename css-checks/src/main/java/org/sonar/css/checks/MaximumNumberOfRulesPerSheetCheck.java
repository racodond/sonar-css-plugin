/*
 * SonarQube CSS Plugin
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
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.SqaleLinearRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

@Rule(
  key = "sheet-too-many-rules",
  name = "Stylesheets should not contain too many rules",
  priority = Priority.MAJOR,
  tags = {Tags.DESIGN, Tags.PERFORMANCE})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_CHANGEABILITY)
@SqaleLinearRemediation(coeff = "5min", effortToFixDescription = "number of rules beyond the limit")
public class MaximumNumberOfRulesPerSheetCheck extends SquidCheck<LexerlessGrammar> {

  private static final int DEFAULT_MAX_RULES = 500;

  @RuleProperty(
    key = "Max",
    description = "The maximum allowed number of rules per stylesheet",
    defaultValue = "" + DEFAULT_MAX_RULES)
  private int max = DEFAULT_MAX_RULES;

  private int currentRuleCount;

  @Override
  public void init() {
    subscribeTo(CssGrammar.AT_RULE, CssGrammar.RULESET);
  }

  @Override
  public void visitFile(AstNode astNode) {
    currentRuleCount = 0;
  }

  @Override
  public void visitNode(AstNode astNode) {
    currentRuleCount++;
  }

  @Override
  public void leaveFile(AstNode astNode) {
    if (currentRuleCount > max) {
      getContext().createFileViolation(this, "Reduce the number of rules. This sheet contains {0,number,integer} rules, "
        + "{1,number,integer} more than the {2,number,integer} allowed rules.", currentRuleCount,
        currentRuleCount - max, max);
    }
  }

  public void setMax(int max) {
    this.max = max;
  }
}
