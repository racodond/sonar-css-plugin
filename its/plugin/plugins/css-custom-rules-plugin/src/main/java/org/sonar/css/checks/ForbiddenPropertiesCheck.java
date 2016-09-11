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

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.model.property.standard.BackgroundColor;
import org.sonar.css.model.property.standard.BackgroundImage;
import org.sonar.plugins.css.api.tree.PropertyTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "forbidden-properties",
  priority = Priority.MAJOR,
  name = "Forbidden properties should not be used",
  tags = {"bug"})
@SqaleConstantRemediation("5min")
public class ForbiddenPropertiesCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitProperty(PropertyTree tree) {
    if (tree.standardProperty() instanceof BackgroundColor || tree.standardProperty() instanceof BackgroundImage) {
      addPreciseIssue(tree, "Remove the usage of this forbidden \"" + tree.standardProperty().getName() + "\" property.");
    }
    // super method must be called in order to visit what is under the key node in the syntax tree
    super.visitProperty(tree);
  }

}
