/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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
package org.sonar.css.checks.scss;

import com.google.common.collect.Lists;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.scss.ScssConditionTree;
import org.sonar.plugins.css.api.tree.scss.ScssElseIfTree;
import org.sonar.plugins.css.api.tree.scss.ScssIfElseIfElseTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Rule(
  key = "if-elseif-same-condition",
  name = "Related @if / @else if directives should not have the same condition",
  priority = Priority.CRITICAL,
  tags = {Tags.BUG})
@SqaleConstantRemediation("15min")
@ActivatedByDefault
public class IfElseIfSameConditionCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitScssIfElseIfElse(ScssIfElseIfElseTree tree) {
    Map<String, List<ScssConditionTree>> conditions = buildConditionsMap(tree);
    createIssues(conditions);
    super.visitScssIfElseIfElse(tree);
  }

  private Map<String, List<ScssConditionTree>> buildConditionsMap(ScssIfElseIfElseTree tree) {
    Map<String, List<ScssConditionTree>> conditions = new HashMap<>();

    conditions.put(tree.ife().condition().treeValue(), Lists.newArrayList(tree.ife().condition()));

    tree.elseif()
      .stream()
      .map(ScssElseIfTree::condition)
      .forEach(c -> {
        if (conditions.get(c.treeValue()) == null) {
          conditions.put(c.treeValue(), Lists.newArrayList(c));
        } else {
          conditions.get(c.treeValue()).add(c);
        }
      });

    return conditions;
  }

  private void createIssues(Map<String, List<ScssConditionTree>> conditions) {
    for (Map.Entry<String, List<ScssConditionTree>> condition : conditions.entrySet()) {
      if (condition.getValue().size() > 1) {
        for (int i = 1; i < condition.getValue().size(); i++) {
          PreciseIssue issue = addPreciseIssue(
            condition.getValue().get(i),
            "This condition duplicates the one at line " + condition.getValue().get(0).condition().firstValueElement().getLine() + "."
              + " Either remove or update this condition.");

          issue.secondary(condition.getValue().get(0), "Duplicated condition");
        }
      }
    }
  }

}
