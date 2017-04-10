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
package org.sonar.css.checks.scss;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.scss.ScssIfElseIfElseTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "if-elseif-without-else",
  name = "@if ... @else if ... constructs should end with @else directive",
  priority = Priority.MAJOR,
  tags = {Tags.PITFALL})
@SqaleConstantRemediation("15min")
@ActivatedByDefault
public class IfElseIfWithoutElseCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitScssIfElseIfElse(ScssIfElseIfElseTree tree) {
    if (!tree.elseif().isEmpty() && tree.elsee() == null) {
      addPreciseIssue(
        tree.elseif().get(tree.elseif().size() - 1).directive(),
        "Add an @else directive after this @else if directive.");
    }
    super.visitScssIfElseIfElse(tree);
  }

}
