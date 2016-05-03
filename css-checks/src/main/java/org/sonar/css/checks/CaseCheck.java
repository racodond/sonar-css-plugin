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
import com.sonar.sslr.api.GenericTokenType;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

@Rule(
  key = "case",
  name = "Properties, functions and variables should be lower case",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("2min")
@ActivatedByDefault
public class CaseCheck extends SquidCheck<LexerlessGrammar> {

  @Override
  public void init() {
    subscribeTo(CssGrammar.PROPERTY, CssGrammar.FUNCTION, CssGrammar.VARIABLE);
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode.is(CssGrammar.PROPERTY)
      && containsUpperCaseCharacter(astNode.getTokenValue())) {
      createIssue(astNode, "property", astNode.getTokenValue());
    } else if (astNode.is(CssGrammar.FUNCTION)
      && containsUpperCaseCharacter(astNode.getTokenValue())) {
      createIssue(astNode, "function", astNode.getTokenValue());
    } else if (astNode.is(CssGrammar.VARIABLE)
      && containsUpperCaseCharacter(astNode.getFirstChild(GenericTokenType.IDENTIFIER).getTokenValue())) {
      createIssue(astNode, "variable", astNode.getFirstChild(GenericTokenType.IDENTIFIER).getTokenValue());
    }
  }

  private void createIssue(AstNode astNode, String nodeType, String value) {
    getContext().createLineViolation(
      this,
      "Write {0} \"{1}\" in lowercase.",
      astNode,
      nodeType,
      value);
  }

  private boolean containsUpperCaseCharacter(String value) {
    return value.matches("^.*[A-Z]+.*$");
  }

}
