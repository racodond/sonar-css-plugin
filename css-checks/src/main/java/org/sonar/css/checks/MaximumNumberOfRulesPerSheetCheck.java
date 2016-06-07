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

import com.google.common.annotations.VisibleForTesting;
import com.sonar.sslr.api.AstNode;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.CssCheck;
import org.sonar.css.issue.PreciseIssue;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleLinearRemediation;

@Rule(
  key = "sheet-too-many-rules",
  name = "Stylesheets should not contain too many rules",
  priority = Priority.MAJOR,
  tags = {Tags.DESIGN, Tags.PERFORMANCE})
@SqaleLinearRemediation(coeff = "5min", effortToFixDescription = "number of rules beyond the limit")
@ActivatedByDefault
public class MaximumNumberOfRulesPerSheetCheck extends CssCheck {

  private static final int DEFAULT_MAX_RULES = 500;

  @RuleProperty(
    key = "Max",
    description = "The maximum allowed number of rules per stylesheet",
    defaultValue = "" + DEFAULT_MAX_RULES)
  private int max = DEFAULT_MAX_RULES;

  private final Set<AstNode> ruleNodes = new HashSet<>();

  @Override
  public void init() {
    subscribeTo(CssGrammar.AT_RULE, CssGrammar.RULESET);
  }

  @Override
  public void visitFile(AstNode astNode) {
    ruleNodes.clear();
  }

  @Override
  public void visitNode(AstNode astNode) {
    ruleNodes.add(astNode);
  }

  @Override
  public void leaveFile(AstNode astNode) {
    int numberOfRules = ruleNodes.size();
    if (numberOfRules > max) {
      PreciseIssue issue = addFileIssue(
        this,
        MessageFormat.format("Reduce the number of rules. This sheet contains {0,number,integer} rules, "
          + "{1,number,integer} more than the {2,number,integer} allowed rules.", numberOfRules, numberOfRules - max, max));
      issue.setEffortToFix((double) numberOfRules - max);
      for (AstNode ruleNode : ruleNodes) {
        issue.addSecondaryLocation("+1", ruleNode);
      }
    }
  }

  @VisibleForTesting
  public void setMax(int max) {
    this.max = max;
  }

}
