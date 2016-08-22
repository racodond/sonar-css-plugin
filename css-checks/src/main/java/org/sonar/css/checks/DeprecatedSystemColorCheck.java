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
import org.sonar.css.model.Color;
import org.sonar.plugins.css.api.tree.IdentifierTree;
import org.sonar.plugins.css.api.tree.ValueTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "deprecated-system-colors",
  name = "Deprecated system colors should not be used",
  priority = Priority.MAJOR,
  tags = {Tags.CONVENTION, Tags.PITFALL, Tags.BROWSER_COMPATIBILITY})
@ActivatedByDefault
@SqaleConstantRemediation("10min")
public class DeprecatedSystemColorCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitValue(ValueTree valueTree) {
    valueTree.valueElementsOfType(IdentifierTree.class).stream()
      .filter(t -> Color.CSS2_SYSTEM_COLORS.contains(t.text().toLowerCase()))
      .forEach(t -> addPreciseIssue(t, "Remove this usage of the deprecated \"" + t.text() + "\" system color."));

    super.visitValue(valueTree);
  }

}
