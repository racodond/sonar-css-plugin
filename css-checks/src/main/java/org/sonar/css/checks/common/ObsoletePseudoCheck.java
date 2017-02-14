/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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

import java.text.MessageFormat;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.PseudoFunctionTree;
import org.sonar.plugins.css.api.tree.css.PseudoIdentifierTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "obsolete-pseudos",
  name = "Obsolete pseudo-elements and pseudo-classes should not be used",
  priority = Priority.MAJOR,
  tags = {Tags.BROWSER_COMPATIBILITY})
@ActivatedByDefault
@SqaleConstantRemediation("10min")
public class ObsoletePseudoCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitPseudoFunction(PseudoFunctionTree tree) {
    if (tree.standardFunction().isObsolete()) {
      createIssue(tree.function(), tree.standardFunction().getName());
    }
    super.visitPseudoFunction(tree);
  }

  @Override
  public void visitPseudoIdentifier(PseudoIdentifierTree tree) {
    if (tree.standardPseudoIdentifier().isObsolete()) {
      createIssue(tree.identifier(), tree.standardPseudoIdentifier().getName());
    }
    super.visitPseudoIdentifier(tree);
  }

  private void createIssue(Tree location, String pseudoName) {
    addPreciseIssue(
      location,
      MessageFormat.format(
        "Remove this usage of the obsolete / not on W3C Standards track \"{0}\" pseudo.",
        pseudoName));
  }

}
