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
import org.sonar.plugins.css.api.tree.scss.ScssNestedPropertiesDeclarationTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "at-least-two-nested-properties",
  name = "Nested properties should define at least two properties",
  priority = Priority.MAJOR,
  tags = {Tags.UNDERSTANDABILITY, Tags.PITFALL})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class AtLeastTwoNestedPropertiesCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitScssNestedPropertiesDeclaration(ScssNestedPropertiesDeclarationTree tree) {
    if (tree.propertyDeclarations().isEmpty()) {
      addPreciseIssue(tree.block(), "Remove this useless nested property or add the missing properties.");
    } else if (tree.propertyDeclarations().size() < 2) {
      addPreciseIssue(tree.block(), "Convert this nested property into a simple property or add the missing properties.");
    }
    super.visitScssNestedPropertiesDeclaration(tree);
  }

}
