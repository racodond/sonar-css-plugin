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
import org.sonar.css.model.property.UnknownProperty;
import org.sonar.plugins.css.api.tree.css.PropertyDeclarationTree;
import org.sonar.plugins.css.api.tree.css.PropertyTree;
import org.sonar.plugins.css.api.tree.css.StatementBlockTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import javax.annotation.Nullable;
import java.util.List;

@Rule(
  key = "vendor-prefix",
  name = "Standard properties should be specified along with vendor-prefixed properties",
  priority = Priority.MAJOR,
  tags = {Tags.BROWSER_COMPATIBILITY})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class VendorPrefixWithStandardCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitStatementBlock(StatementBlockTree tree) {
    List<PropertyDeclarationTree> declarations = tree.propertyDeclarations();

    for (int i = 0; i < declarations.size(); i++) {

      PropertyTree currentProperty = declarations.get(i).property();
      PropertyDeclarationTree nextDeclaration = i + 1 < declarations.size() ? declarations.get(i + 1) : null;

      if (!(currentProperty.standardProperty() instanceof UnknownProperty)
        && currentProperty.isVendorPrefixed()
        && !isNextPropertyValid(nextDeclaration, currentProperty)) {

        addPreciseIssue(currentProperty, "Define the standard property right after this vendor-prefixed property.");
      }
    }

    super.visitStatementBlock(tree);
  }

  private boolean isNextPropertyValid(@Nullable PropertyDeclarationTree nextDeclaration, PropertyTree currentProperty) {
    return nextDeclaration != null && nextDeclaration.property().standardProperty().getName().equals(currentProperty.standardProperty().getName());
  }

}
