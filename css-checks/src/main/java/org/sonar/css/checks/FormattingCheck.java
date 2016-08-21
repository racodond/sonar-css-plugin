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
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.tree.impl.CssTree;
import org.sonar.plugins.css.api.tree.*;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "formatting",
  name = "Source code should comply with formatting standards",
  priority = Priority.MINOR,
  tags = {Tags.FORMAT})
@SqaleConstantRemediation("2min")
@ActivatedByDefault
public class FormattingCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitImportant(ImportantTree tree) {
    if (tree.exclamationMark().endColumn() != tree.importantKeyword().column()) {
      addPreciseIssue(tree, "Remove the whitespaces between \"!\" and \"important\".");
    }
    super.visitImportant(tree);
  }

  @Override
  public void visitPropertyDeclaration(PropertyDeclarationTree tree) {
    if (!isOnSameLine(tree.property(), tree.colon(), tree.value())) {
      addPreciseIssue(tree, "Move the property, colon and value to the same line.");
    }

    if (isOnSameLine(tree.property(), tree.colon()) && nbWhitespacesBetween(tree.property(), tree.colon()) > 0) {
      addPreciseIssue(tree.colon(), "Remove the whitespaces between the property and the colon.");
    }

    if (isOnSameLine(tree.colon(), tree.value())) {
      if (nbWhitespacesBetween(tree.colon(), tree.value()) == 0) {
        addPreciseIssue(tree.colon(), "Add one whitespace between the colon and the value.");

      } else if (nbWhitespacesBetween(tree.colon(), tree.value()) > 1) {
        addPreciseIssue(tree.colon(), "Leave only one whitespace between the colon and the value.");
      }
    }

    super.visitPropertyDeclaration(tree);
  }

  @Override
  public void visitVariableDeclaration(VariableDeclarationTree tree) {
    if (!isOnSameLine(tree.variable(), tree.colon(), tree.value())) {
      addPreciseIssue(tree, "Move the variable, colon and value to the same line.");
    }

    if (isOnSameLine(tree.variable(), tree.colon()) && nbWhitespacesBetween(tree.variable(), tree.colon()) > 0) {
      addPreciseIssue(tree.colon(), "Remove the whitespaces between the variable and the colon.");
    }

    if (isOnSameLine(tree.colon(), tree.value())) {
      if (nbWhitespacesBetween(tree.colon(), tree.value()) == 0) {
        addPreciseIssue(tree.colon(), "Add one whitespace between the colon and the value.");

      } else if (nbWhitespacesBetween(tree.colon(), tree.value()) > 1) {
        addPreciseIssue(tree.colon(), "Leave only one whitespace between the colon and the value.");
      }
    }

    super.visitVariableDeclaration(tree);
  }

  @Override
  public void visitRuleset(RulesetTree tree) {

    if (tree.block().declarations() != null && isOnSameLine(tree.block().openCurlyBrace(), tree.block().declarations())) {
      addPreciseIssue(tree.block().openCurlyBrace(), "Move the code following the opening curly brace to the next line.");
    }

    if (tree.selectors() != null && !isOnSameLine(tree.selectors().lastSelector(), tree.block().openCurlyBrace())) {
      addPreciseIssue(tree.block().openCurlyBrace(), "Move the opening curly brace to the previous line.");
    }

    if (tree.block().declarations() != null && isOnSameLine(tree.block().declarations(), tree.block().closeCurlyBrace())) {
      addPreciseIssue(tree.block().closeCurlyBrace(), "Move the closing curly brace to the next line.");
    }

    super.visitRuleset(tree);
  }

  @Override
  public void visitAtRule(AtRuleTree tree) {
    if (tree.block() != null) {

      if (!isOnSameLine(tree.atKeyword(), tree.block().openCurlyBrace())) {
        addPreciseIssue(tree.block().openCurlyBrace(), "Move the opening curly brace to the previous line.");
      }

      Tree content = null;
      if (tree.block().declarations() != null) {
        content = tree.block().declarations();
      } else if (!tree.block().statements().isEmpty()) {
        content = tree.block().statements().get(0);
      }

      if (content != null) {
        if (isOnSameLine(tree.block().openCurlyBrace(), content)) {
          addPreciseIssue(tree.block().openCurlyBrace(), "Move the code following the opening curly brace to the next line.");
        }
        if (isOnSameLine(content, tree.block().closeCurlyBrace())) {
          addPreciseIssue(tree.block().closeCurlyBrace(), "Move the closing curly brace to the next line.");
        }
      }
    }

    super.visitAtRule(tree);
  }

  private boolean isOnSameLine(Tree... trees) {
    Preconditions.checkArgument(trees.length > 1);
    int lineRef;
    if (trees[0] instanceof CssTree) {
      lineRef = ((CssTree) trees[0]).getFirstToken().line();
    } else {
      lineRef = ((SyntaxToken) trees[0]).line();
    }
    for (Tree tree : trees) {
      if (tree instanceof CssTree && ((CssTree) tree).getFirstToken().line() != lineRef
        || tree instanceof SyntaxToken && ((SyntaxToken) tree).line() != lineRef) {
        return false;
      }
    }
    return true;
  }

  private int nbWhitespacesBetween(Tree tree1, Tree tree2) {
    int endColumnTree1;
    if (tree1 instanceof CssTree) {
      endColumnTree1 = ((CssTree) tree1).getLastToken().endColumn();
    } else {
      endColumnTree1 = ((SyntaxToken) tree1).endColumn();
    }

    int startColumnTree2;
    if (tree2 instanceof CssTree) {
      startColumnTree2 = ((CssTree) tree2).getFirstToken().column();
    } else {
      startColumnTree2 = ((SyntaxToken) tree2).column();
    }

    return startColumnTree2 - endColumnTree1;
  }

}
