/*
 * SonarQube CSS / SCSS / Less Analyzer
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

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "case",
  name = "Properties, functions and @rule keywords should be lower case",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION})
@SqaleConstantRemediation("2min")
@ActivatedByDefault
public class CaseCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitProperty(PropertyTree tree) {
    if (containsUpperCaseCharacter(tree.property().text())
      && !tree.property().isInterpolated()) {
      addIssue(tree, "property", tree.property().text());
    }
    super.visitProperty(tree);
  }

  @Override
  public void visitAtKeyword(AtKeywordTree tree) {
    if (containsUpperCaseCharacter(tree.keyword().text())) {
      addIssue(tree, "@rule keyword", tree.keyword().text());
    }
    super.visitAtKeyword(tree);
  }

  @Override
  public void visitFunction(FunctionTree tree) {
    if (containsUpperCaseCharacter(tree.function().text())) {
      addIssue(tree.function(), "function", tree.function().text());
    }
    super.visitFunction(tree);
  }

  private void addIssue(Tree tree, String treeType, String value) {
    addPreciseIssue(
      tree,
      "Write " + treeType + " \"" + value + "\" in lowercase.");
  }

  private boolean containsUpperCaseCharacter(String value) {
    return value.matches("^.*[A-Z]+.*$");
  }

}
