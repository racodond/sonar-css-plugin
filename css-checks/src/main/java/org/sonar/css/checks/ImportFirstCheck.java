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

import java.util.HashSet;
import java.util.Set;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.model.atrule.standard.Charset;
import org.sonar.css.model.atrule.standard.Import;
import org.sonar.plugins.css.api.tree.*;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "import-first",
  name = "@import rules should precede all other at-rules and style rules",
  priority = Priority.CRITICAL,
  tags = {Tags.BUG})
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class ImportFirstCheck extends DoubleDispatchVisitorCheck {

  private Set<Tree> precedingRules = new HashSet<>();

  @Override
  public void visitStyleSheet(StyleSheetTree tree) {
    precedingRules.clear();

    super.visitStyleSheet(tree);

    for (StatementTree statementTree : tree.statements()) {
      if (statementTree instanceof RulesetTree) {
        precedingRules.add(CheckUtils.rulesetIssueLocation((RulesetTree) statementTree));
      } else if (((AtRuleTree) statementTree).standardAtRule() instanceof Import) {
        if (!precedingRules.isEmpty()) {
          createIssue(((AtRuleTree) statementTree).atKeyword());
        }
      } else if (!(((AtRuleTree) statementTree).standardAtRule() instanceof Charset)) {
        precedingRules.add(statementTree);
      }
    }
  }

  private void createIssue(AtKeywordTree tree) {
    PreciseIssue issue = addPreciseIssue(
      tree,
      "Move this @import rule before all the other at-rules and style rules.");

    precedingRules.stream().forEach(t -> issue.secondary(t, "Preceding rule"));
  }

}
