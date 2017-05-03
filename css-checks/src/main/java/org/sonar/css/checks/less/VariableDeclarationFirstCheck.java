/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2017 David RACODON
 * mailto: david.racodon@gmail.com
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
package org.sonar.css.checks.less;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.css.model.atrule.standard.Charset;
import org.sonar.css.model.atrule.standard.Import;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.AtRuleTree;
import org.sonar.plugins.css.api.tree.css.StatementBlockTree;
import org.sonar.plugins.css.api.tree.css.StyleSheetTree;
import org.sonar.plugins.css.api.tree.less.LessVariableDeclarationTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.List;

@Rule(
  key = "variable-declaration-first",
  name = "Variables should be declared at the beginning of the block",
  priority = Priority.MAJOR,
  tags = {Tags.UNDERSTANDABILITY, Tags.PITFALL})
@SqaleConstantRemediation("5min")
public class VariableDeclarationFirstCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitStyleSheet(StyleSheetTree tree) {
    checkBlock(tree.all());
    super.visitStyleSheet(tree);
  }

  @Override
  public void visitStatementBlock(StatementBlockTree tree) {
    checkBlock(tree.allStatements());
    super.visitStatementBlock(tree);
  }

  private void checkBlock(List<Tree> statements) {
    boolean found = false;
    for (Tree tree : statements) {
      if (!found
        && !(tree instanceof LessVariableDeclarationTree)
        && !(tree instanceof AtRuleTree && (((AtRuleTree) tree).standardAtRule() instanceof Import || ((AtRuleTree) tree).standardAtRule() instanceof Charset))) {
        found = true;
      }
      if (found && tree instanceof LessVariableDeclarationTree) {
        addPreciseIssue(tree, "Move this declaration to the beginning of the block.");
      }
    }
  }

}
