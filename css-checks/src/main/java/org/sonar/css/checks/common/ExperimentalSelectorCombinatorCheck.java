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
package org.sonar.css.checks.common;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.SelectorCombinatorTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "experimental-selector-combinator-usage",
  name = "Experimental selector combinators should not be used",
  priority = Priority.MAJOR,
  tags = {Tags.CONVENTION, Tags.BROWSER_COMPATIBILITY})
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class ExperimentalSelectorCombinatorCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitSelectorCombinator(SelectorCombinatorTree tree) {
    checkForExperimentalSelectorCombinator(tree);
    super.visitSelectorCombinator(tree);
  }

  private void checkForExperimentalSelectorCombinator(SelectorCombinatorTree tree) {
    if (tree.type() == SelectorCombinatorTree.COMBINATOR.DESCENDANT
      || tree.type() == SelectorCombinatorTree.COMBINATOR.COLUMN
      || tree.type() == SelectorCombinatorTree.COMBINATOR.DEEP
      || tree.type() == SelectorCombinatorTree.COMBINATOR.DEEP_ALIAS) {
      addPreciseIssue(tree, "Remove this experimental selector combinator.");
    }
  }

}
