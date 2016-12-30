/*
 * SonarQube CSS / Less Plugin
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
package org.sonar.css.checks.common;

import com.google.common.base.Preconditions;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.tree.less.LessVariableDeclarationTree;
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
  public void visitLessVariableDeclaration(LessVariableDeclarationTree tree) {
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

    super.visitLessVariableDeclaration(tree);
  }

  @Override
  public void visitRuleset(RulesetTree tree) {

    if (tree.block().content() == null) {
      return;
    }

    if (tree.block().content().size() < 2
      && isOnSameLine(tree.block().openCurlyBrace(), tree.block().closeCurlyBrace())) {
      return;
    }

    if (isOnSameLine(tree.block().openCurlyBrace(), tree.block().content().get(0))) {
      addPreciseIssue(tree.block().openCurlyBrace(), "Move the code following the opening curly brace to the next line.");
    }

    if (tree.selectors() != null && !isOnSameLine(tree.selectors().lastSelector(), tree.block().openCurlyBrace())) {
      addPreciseIssue(tree.block().openCurlyBrace(), "Move the opening curly brace to the previous line.");
    }

    if (isOnSameLine(tree.block().content().get(tree.block().content().size() - 1), tree.block().closeCurlyBrace())) {
      addPreciseIssue(tree.block().closeCurlyBrace(), "Move the closing curly brace to the next line.");
    }

    super.visitRuleset(tree);
  }

  @Override
  public void visitAtRule(AtRuleTree tree) {
    if (tree.block() != null) {

      checkAtRuleOpeningCurlyBrace(tree);

      if (tree.block().content() != null) {
        if (isOnSameLine(tree.block().openCurlyBrace(), tree.block().content().get(0))) {
          addPreciseIssue(tree.block().openCurlyBrace(), "Move the code following the opening curly brace to the next line.");
        }
        if (isOnSameLine(tree.block().content().get(0), tree.block().closeCurlyBrace())) {
          addPreciseIssue(tree.block().closeCurlyBrace(), "Move the closing curly brace to the next line.");
        }
      }
    }

    super.visitAtRule(tree);
  }

  private void checkAtRuleOpeningCurlyBrace(AtRuleTree tree) {
    Preconditions.checkNotNull(tree.block());

    Tree tree1 = tree.preludes() != null ? tree.preludes().get(tree.preludes().size() - 1) : tree.atKeyword();
    Tree tree2 = tree.block().openCurlyBrace();

    if (!isOnSameLine(tree1, tree2)) {
      addPreciseIssue(tree2, "Move the opening curly brace to the previous line.");
    }
  }

  private boolean isOnSameLine(Tree... trees) {
    Preconditions.checkArgument(trees.length > 1);

    int lineRef;
    if (trees[0] instanceof TreeImpl) {
      lineRef = ((TreeImpl) trees[0]).getLastToken().line();
    } else {
      lineRef = ((SyntaxToken) trees[0]).line();
    }

    for (int i = 1; i < trees.length; i++) {
      if (trees[i] instanceof TreeImpl && ((TreeImpl) trees[i]).getFirstToken().line() != lineRef
        || trees[i] instanceof SyntaxToken && ((SyntaxToken) trees[i]).line() != lineRef) {
        return false;
      }
    }

    return true;
  }

  private int nbWhitespacesBetween(Tree tree1, Tree tree2) {
    int endColumnTree1;
    if (tree1 instanceof TreeImpl) {
      endColumnTree1 = ((TreeImpl) tree1).getLastToken().endColumn();
    } else {
      endColumnTree1 = ((SyntaxToken) tree1).endColumn();
    }

    int startColumnTree2;
    if (tree2 instanceof TreeImpl) {
      startColumnTree2 = ((TreeImpl) tree2).getFirstToken().column();
    } else {
      startColumnTree2 = ((SyntaxToken) tree2).column();
    }

    return startColumnTree2 - endColumnTree1;
  }

}
