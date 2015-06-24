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

import com.google.common.base.Preconditions;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.GenericTokenType;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

@Rule(
  key = "formatting",
  name = "Source code should comply with formatting standards",
  priority = Priority.MINOR,
  tags = {Tags.FORMAT})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("2min")
public class FormattingCheck extends SquidCheck<LexerlessGrammar> {

  @Override
  public void init() {
    subscribeTo(
      CssGrammar.IMPORTANT,
      CssGrammar.DECLARATION,
      CssGrammar.OPEN_CURLY_BRACE,
      CssGrammar.CLOSE_CURLY_BRACE);
  }

  @Override
  public void leaveNode(AstNode node) {
    checkBlock(node);
    checkDeclaration(node);
    checkImportant(node);
  }

  private void checkBlock(AstNode node) {
    checkOpeningCurlyBraceLastTokenOnTheLine(node);
    checkClosingCurlyBraceOnlyTokenOnTheLine(node);
  }

  private void checkDeclaration(AstNode node) {
    if (node.is(CssGrammar.DECLARATION)) {
      checkPropertyAndValueOnSameLine(node);
      checkWhitespacesInDeclaration(node);
    }
  }

  private void checkImportant(AstNode node) {
    if (node.is(CssGrammar.IMPORTANT)) {
      for (int i = 0; i < node.getChildren().size(); i++) {
        if ("important".equals(node.getChildren().get(i).getTokenValue()) && !"!".equals(node.getChildren().get(i - 1).getTokenValue())) {
          getContext().createLineViolation(this, "Remove the whitespaces between \"!\" and \"important\".", node);
          break;
        }
      }
    }
  }

  private void checkPropertyAndValueOnSameLine(AstNode node) {
    if (!isOnSameLine(node.getFirstChild(CssGrammar.PROPERTY), node.getFirstChild(CssGrammar.COLON), node.getFirstChild(CssGrammar.VALUE))) {
      getContext().createLineViolation(this, "Move the property, colon and value to the same line.", node);
    }
  }

  private void checkWhitespacesInDeclaration(AstNode node) {
    if (isOnSameLine(node.getFirstChild(CssGrammar.PROPERTY), node.getFirstChild(CssGrammar.COLON))
      && getNbWhitespacesBetween(node.getFirstChild(CssGrammar.PROPERTY), node.getFirstChild(CssGrammar.COLON)) > 0) {
      getContext().createLineViolation(this, "Remove the whitespaces between the property and the colon.", node);
    }
    if (isOnSameLine(node.getFirstChild(CssGrammar.COLON), node.getFirstChild(CssGrammar.VALUE))
      && getNbWhitespacesBetween(node.getFirstChild(CssGrammar.COLON), node.getFirstChild(CssGrammar.VALUE)) == 0) {
      getContext().createLineViolation(this, "Add one whitespace between the colon and the value.", node);
    }
    if (isOnSameLine(node.getFirstChild(CssGrammar.COLON), node.getFirstChild(CssGrammar.VALUE))
      && getNbWhitespacesBetween(node.getFirstChild(CssGrammar.COLON), node.getFirstChild(CssGrammar.VALUE)) > 1) {
      getContext().createLineViolation(this, "Leave only one whitespace between the colon and the value.", node);
    }
  }

  private void checkOpeningCurlyBraceLastTokenOnTheLine(AstNode node) {
    if (node.is(CssGrammar.OPEN_CURLY_BRACE)) {
      if (isOnSameLine(node, node.getNextAstNode())) {
        getContext().createLineViolation(this, "Move the code following the opening curly brace to the next line.", node);
      }
      if (!isOnSameLine(node, node.getPreviousAstNode())) {
        getContext().createLineViolation(this, "Move the opening curly brace to the previous line.", node);
      }
    }
  }

  private void checkClosingCurlyBraceOnlyTokenOnTheLine(AstNode node) {
    if (node.is(CssGrammar.CLOSE_CURLY_BRACE)) {
      if (isOnSameLine(node, node.getPreviousAstNode())) {
        getContext().createLineViolation(this, "Move this closing curly brace to the next line.", node);
      }
      if (!node.getNextAstNode().is(GenericTokenType.EOF) && isOnSameLine(node, node.getNextAstNode())) {
        getContext().createLineViolation(this, "Move the code following the closing curly brace to the next line.", node);
      }
    }
  }

  private boolean isOnSameLine(AstNode... nodes) {
    Preconditions.checkArgument(nodes.length > 1);
    int lineRef = nodes[0].getTokenLine();
    for (AstNode node : nodes) {
      if (node.getTokenLine() != lineRef) {
        return false;
      }
    }
    return true;
  }

  private int getNbWhitespacesBetween(AstNode node1, AstNode node2) {
    return node2.getToken().getColumn() - node1.getToken().getColumn() - node1.getTokenValue().length();
  }

}
