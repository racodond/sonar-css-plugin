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

import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.SelectorTree;
import org.sonar.plugins.css.api.tree.css.SimpleSelectorTree;
import org.sonar.plugins.css.api.tree.css.TypeSelectorTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "universal-selector",
  name = "Universal selector should not be used as key part",
  priority = Priority.MAJOR,
  tags = {Tags.PERFORMANCE})
@SqaleConstantRemediation("1h")
@ActivatedByDefault
public class UniversalSelectorCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitSelector(SelectorTree tree) {
    List<SimpleSelectorTree> lastCompoundSelector = tree.compoundSelectors().get(tree.compoundSelectors().size() - 1).selectors();
    if (lastCompoundSelector.size() == 1
      && lastCompoundSelector.get(0) instanceof TypeSelectorTree
      && "*".equals(((TypeSelectorTree) lastCompoundSelector.get(0)).identifier().text())) {
      addPreciseIssue(
        ((TypeSelectorTree) lastCompoundSelector.get(0)).identifier(),
        "Remove this usage of the universal selector as key part.");
    }
    super.visitSelector(tree);
  }

}
