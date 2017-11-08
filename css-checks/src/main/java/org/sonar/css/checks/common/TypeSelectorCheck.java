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

import com.google.common.annotations.VisibleForTesting;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.TypeSelectorTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
        key = "type-selector",
        name = "Types in selectors should be removed",
        priority = Priority.MAJOR,
        tags = {Tags.DESIGN})
@SqaleConstantRemediation("30min")
@ActivatedByDefault
public class TypeSelectorCheck extends DoubleDispatchVisitorCheck {

  private static final String DEFAULT_ALLOWED_TYPES = "";
  @RuleProperty(
          key = "Exclusions",
          description = "A list of types that are allowed in selectors (separated by \",\")",
          defaultValue = DEFAULT_ALLOWED_TYPES)
  private String allowedTypes = DEFAULT_ALLOWED_TYPES;

  @Override
  public void visitTypeSelector(TypeSelectorTree tree) {
    if (!tree.identifier().isInterpolated()
            && !isAllowedType(tree.identifier().text())) {
      addPreciseIssue(
              tree.identifier(),
              "Remove the type \"" + tree.identifier().text() + "\" from this selector.");
    }
    super.visitTypeSelector(tree);
  }

  private boolean isAllowedType(String type) {
    for (String allowedType : allowedTypes.split(",")) {
      if(allowedType.trim().equalsIgnoreCase(type)) {
        return true;
      }
    }
    return false;
  }

  @VisibleForTesting
  void setAllowedTypes(String allowedTypes) {
    this.allowedTypes = allowedTypes;
  }

}
