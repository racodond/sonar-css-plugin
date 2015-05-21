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
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

@Rule(
  key = "declaration-format",
  name = "Formatting of declarations should be consistent",
  priority = Priority.MINOR,
  status = "BETA",
  tags = {Tags.FORMAT})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("2min")
public class DeclarationFormatCheck extends SquidCheck<LexerlessGrammar> {

  @Override
  public void init() {
    subscribeTo(CssGrammar.DECLARATION);
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (hasPropertyAndValueOnDifferentLines(astNode)
      || hasWhitespaceBetweenPropertyAndColon(astNode)
      || hasNoWhitespaceBetweenColonAndValue(astNode)
      || hasTooManyWhitespacesBetweenColonAndValue(astNode)) {
      getContext().createLineViolation(this, "Reformat the declaration to comply with the standard", astNode);
    }
  }

  private boolean hasPropertyAndValueOnDifferentLines(AstNode astNode) {
    return astNode.getFirstChild(CssGrammar.PROPERTY).getTokenLine() != astNode.getFirstChild(CssGrammar.VALUE).getTokenLine();
  }

  private boolean hasWhitespaceBetweenPropertyAndColon(AstNode astNode) {
    return astNode.getFirstChild(CssGrammar.PROPERTY).getToken().getColumn() + astNode.getFirstChild(CssGrammar.PROPERTY).getTokenOriginalValue().length()
      != astNode.getFirstChild(CssGrammar.COLON).getToken().getColumn();
  }

  private boolean hasNoWhitespaceBetweenColonAndValue(AstNode astNode) {
    return astNode.getFirstChild(CssGrammar.VALUE).getToken().getColumn() - astNode.getFirstChild(CssGrammar.COLON).getToken().getColumn() < 2;
  }

  private boolean hasTooManyWhitespacesBetweenColonAndValue(AstNode astNode) {
    return astNode.getFirstChild(CssGrammar.VALUE).getToken().getColumn() - astNode.getFirstChild(CssGrammar.COLON).getToken().getColumn() > 2;
  }

//  private boolean hasWhitespaceBetweenValueAndSemiColon(AstNode astNode) {
//    if (astNode.getFirstChild(CssGrammar.SEMICOLON) != null) {
//      if (astNode.getFirstChild(CssGrammar.VALUE).getFirstChild(CssGrammar.IMPORTANT) != null) {
//        return astNode.getFirstChild(CssGrammar.VALUE).getToken().getColumn() + astNode.getFirstChild(CssGrammar.VALUE).getTokenOriginalValue().length() < astNode
//          .getFirstChild(CssGrammar.SEMICOLON).getToken().getColumn();
//      } else {
//        return astNode.getFirstChild(CssGrammar.VALUE).getFirstChild(CssGrammar.IMPORTANT).getToken().getColumn() + 10 < astNode
//          .getFirstChild(CssGrammar.SEMICOLON).getToken().getColumn();
//      }
//    }
//    return false;
//  }

}
