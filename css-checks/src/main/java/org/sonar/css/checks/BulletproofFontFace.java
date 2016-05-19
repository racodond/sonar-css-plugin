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

import java.util.List;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

/**
 * https://github.com/stubbornella/csslint/wiki/Bulletproof-font-face
 */
@Rule(
  key = "bulletproof-font-face",
  name = "Font face should be made compatible with IE 6, 7 and 8",
  priority = Priority.MAJOR,
  tags = {Tags.BROWSER_COMPATIBILITY},
  status = org.sonar.api.rules.Rule.STATUS_DEPRECATED)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("10min")
public class BulletproofFontFace extends SquidCheck<LexerlessGrammar> {

  boolean foundEot;

  @Override
  public void init() {
    subscribeTo(CssGrammar.AT_RULE);
    foundEot = false;
  }

  @Override
  public void visitNode(AstNode astNode) {
    if ("font-face".equals(astNode.getFirstChild(CssGrammar.AT_KEYWORD).getFirstChild(CssGrammar.IDENT).getTokenValue()) && !isEmptyFontFace(astNode)) {
      List<AstNode> declarations = astNode.getFirstDescendant(CssGrammar.atRuleBlock).getFirstChild(CssGrammar.SUP_DECLARATION).getChildren(CssGrammar.DECLARATION);
      for (AstNode declaration : declarations) {
        if ("src".equals(declaration.getFirstChild(CssGrammar.PROPERTY).getTokenValue())
          && declaration.getFirstChild(CssGrammar.VALUE).getFirstChild(CssGrammar.URI) != null) {
          String firstAnyFunctionValue = CssChecksUtil.getStringValue(
            declaration.getFirstChild(CssGrammar.VALUE)
              .getFirstChild(CssGrammar.URI)
              .getFirstChild(CssGrammar._URI_CONTENT));
          if (!firstAnyFunctionValue.matches(".*\\.eot\\?.*?['\"]?$") && !foundEot) {
            getContext().createLineViolation(this,
              "Check that the first file is the .eot file and that the workaround for IE is set", astNode);
          } else {
            foundEot = true;
          }
          // We only care about the first function
          return;
        }
      }
    }
  }

  private boolean isEmptyFontFace(AstNode astNode) {
    return astNode.getFirstDescendant(CssGrammar.atRuleBlock).getFirstChild(CssGrammar.SUP_DECLARATION) == null;
  }

}
