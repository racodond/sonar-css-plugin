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

import com.google.common.base.Preconditions;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.GenericTokenType;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "formatting",
  name = "Source code should comply with formatting standards",
  priority = Priority.MINOR,
  tags = {Tags.FORMAT})
@SqaleConstantRemediation("2min")
@ActivatedByDefault
public class FormattingCheck extends CssCheck {

  @Override
  public void init() {
    subscribeTo(
      CssGrammar.IMPORTANT,
      CssGrammar.DECLARATION,
      CssGrammar.VARIABLE_DECLARATION,
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
    if (node.is(CssGrammar.DECLARATION) || node.is(CssGrammar.VARIABLE_DECLARATION)) {
      checkPropertyAndValueOnSameLine(node);
      checkWhitespacesInDeclaration(node);
    }
  }

  private void checkImportant(AstNode node) {
    if (node.is(CssGrammar.IMPORTANT)) {
      for (int i = 0; i < node.getChildren().size(); i++) {
        if ("important".equals(node.getChildren().get(i).getTokenValue()) && !"!".equals(node.getChildren().get(i - 1).getTokenValue())) {
          addIssue(this, "Remove the whitespaces between \"!\" and \"important\".", node);
          break;
        }
      }
    }
  }

  private void checkPropertyAndValueOnSameLine(AstNode node) {
    if (node.is(CssGrammar.DECLARATION) && !isOnSameLine(node.getFirstChild(CssGrammar.PROPERTY), node.getFirstChild(CssGrammar.COLON), node.getFirstChild(CssGrammar.VALUE))) {
      addIssue(this, "Move the property, colon and value to the same line.", node);
    }
    if (node.is(CssGrammar.VARIABLE_DECLARATION)
      && !isOnSameLine(node.getFirstChild(CssGrammar.VARIABLE), node.getFirstChild(CssGrammar.COLON), node.getFirstChild(CssGrammar.VALUE))) {
      addIssue(this, "Move the variable, colon and value to the same line.", node);
    }
  }

  private void checkWhitespacesInDeclaration(AstNode node) {
    if (node.is(CssGrammar.DECLARATION) && isOnSameLine(node.getFirstChild(CssGrammar.PROPERTY), node.getFirstChild(CssGrammar.COLON))
      && getNbWhitespacesBetween(node.getFirstChild(CssGrammar.PROPERTY), node.getFirstChild(CssGrammar.COLON)) > 0) {
      addIssue(this, "Remove the whitespaces between the property and the colon.", node);
    }
    if (node.is(CssGrammar.VARIABLE_DECLARATION) && isOnSameLine(node.getFirstChild(CssGrammar.VARIABLE), node.getFirstChild(CssGrammar.COLON))
      && getNbWhitespacesBetween(node.getFirstChild(CssGrammar.VARIABLE).getFirstChild(GenericTokenType.IDENTIFIER), node.getFirstChild(CssGrammar.COLON)) > 0) {
      addIssue(this, "Remove the whitespaces between the variable and the colon.", node);
    }
    if (isOnSameLine(node.getFirstChild(CssGrammar.COLON), node.getFirstChild(CssGrammar.VALUE))
      && getNbWhitespacesBetween(node.getFirstChild(CssGrammar.COLON), node.getFirstChild(CssGrammar.VALUE)) == 0) {
      addIssue(this, "Add one whitespace between the colon and the value.", node);
    }
    if (isOnSameLine(node.getFirstChild(CssGrammar.COLON), node.getFirstChild(CssGrammar.VALUE))
      && getNbWhitespacesBetween(node.getFirstChild(CssGrammar.COLON), node.getFirstChild(CssGrammar.VALUE)) > 1) {
      addIssue(this, "Leave only one whitespace between the colon and the value.", node);
    }
  }

  private void checkOpeningCurlyBraceLastTokenOnTheLine(AstNode node) {
    if (node.is(CssGrammar.OPEN_CURLY_BRACE)) {
      if (isOnSameLine(node, node.getNextAstNode())) {
        addIssue(this, "Move the code following the opening curly brace to the next line.", node);
      }
      if (!isRuleSetWithoutSelector(node) && !isOnSameLine(node, node.getPreviousAstNode().getLastChild())) {
        addIssue(this, "Move the opening curly brace to the previous line.", node);
      }
    }
  }

  private void checkClosingCurlyBraceOnlyTokenOnTheLine(AstNode node) {
    if (node.is(CssGrammar.CLOSE_CURLY_BRACE)) {
      if (isOnSameLine(node, node.getPreviousAstNode())) {
        addIssue(this, "Move the closing curly brace to the next line.", node);
      }
      if (!node.getNextAstNode().is(GenericTokenType.EOF) && isOnSameLine(node, node.getNextAstNode())) {
        addIssue(this, "Move the code following the closing curly brace to the next line.", node);
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

  private boolean isRuleSetWithoutSelector(AstNode openCurlyBraceNode) {
    return openCurlyBraceNode.getParent().getType().equals(CssGrammar.BLOCK)
      && openCurlyBraceNode.getParent().getParent() != null
      && openCurlyBraceNode.getParent().getParent().getType().equals(CssGrammar.RULESET)
      && openCurlyBraceNode.getParent().getParent().getFirstChild(CssGrammar.SELECTOR) == null;
  }

}
