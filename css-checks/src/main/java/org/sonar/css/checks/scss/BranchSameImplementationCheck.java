/*
 * SonarQube CSS / SCSS / Less Analyzer
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
package org.sonar.css.checks.scss;

import com.google.common.collect.Lists;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.StatementBlockTree;
import org.sonar.plugins.css.api.tree.scss.ScssElseIfTree;
import org.sonar.plugins.css.api.tree.scss.ScssIfConditionsTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Rule(
  key = "branch-same-implementation",
  name = "Two branches in the same conditional structure should not have exactly the same implementation",
  priority = Priority.CRITICAL,
  tags = {Tags.BUG})
@SqaleConstantRemediation("15min")
@ActivatedByDefault
public class BranchSameImplementationCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitScssIfConditions(ScssIfConditionsTree tree) {
    Map<String, List<StatementBlockTree>> blocks = buildBlocksMap(tree);
    createIssues(blocks);
    super.visitScssIfConditions(tree);
  }

  private Map<String, List<StatementBlockTree>> buildBlocksMap(ScssIfConditionsTree tree) {
    Map<String, List<StatementBlockTree>> blocks = new HashMap<>();

    blocks.put(tree.ife().block().treeValue(), Lists.newArrayList(tree.ife().block()));

    tree.elseif()
      .stream()
      .map(ScssElseIfTree::block)
      .forEach(b -> {
        if (blocks.get(b.treeValue()) == null) {
          blocks.put(b.treeValue(), Lists.newArrayList(b));
        } else {
          blocks.get(b.treeValue()).add(b);
        }
      });

    if (tree.elsee() != null) {
      StatementBlockTree elseBlock = tree.elsee().block();
      if (blocks.get(elseBlock.treeValue()) == null) {
        blocks.put(elseBlock.treeValue(), Lists.newArrayList(elseBlock));
      } else {
        blocks.get(elseBlock.treeValue()).add(elseBlock);
      }
    }

    return blocks;
  }

  private void createIssues(Map<String, List<StatementBlockTree>> blocks) {
    for (Map.Entry<String, List<StatementBlockTree>> block : blocks.entrySet()) {
      if (block.getValue().size() > 1) {
        for (int i = 1; i < block.getValue().size(); i++) {
          PreciseIssue issue = addPreciseIssue(
            block.getValue().get(i),
            "This block duplicates the one at line " + block.getValue().get(0).openCurlyBrace().getLine() + "."
              + " Either update the block or the condition.");

          issue.secondary(block.getValue().get(0), "Duplicated block");
        }
      }
    }
  }

}
