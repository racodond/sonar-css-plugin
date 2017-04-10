/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
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
import org.sonar.plugins.css.api.tree.css.ImportantFlagTree;
import org.sonar.plugins.css.api.tree.css.ValueTree;
import org.sonar.plugins.css.api.tree.scss.ScssDefaultFlagTree;
import org.sonar.plugins.css.api.tree.scss.ScssGlobalFlagTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "important-position",
  name = "\"!important\" flag should be placed at the end of the declaration",
  priority = Priority.CRITICAL,
  tags = {Tags.BUG})
@ActivatedByDefault
@SqaleConstantRemediation("5min")
public class ImportantPositionCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitValue(ValueTree tree) {
    for (int i = 0; i < tree.valueElements().size() - 1; i++) {
      if (tree.valueElements().get(i) instanceof ImportantFlagTree) {
        for (int j = i + 1; j < tree.valueElements().size(); j++) {
          if (!(tree.valueElements().get(j) instanceof ScssDefaultFlagTree)
            && !(tree.valueElements().get(j) instanceof ScssGlobalFlagTree)) {
            addPreciseIssue(tree.valueElements().get(i), "Move the \"!important\" flag to the end of the declaration.");
            break;
          }
        }
      }
    }
    super.visitValue(tree);
  }

}
