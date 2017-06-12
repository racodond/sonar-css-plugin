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

import com.google.common.collect.ImmutableList;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.DeclarationTree;
import org.sonar.plugins.css.api.visitors.SubscriptionVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.List;

@Rule(
  key = "semicolon-declaration",
  name = "Each declaration should end with a semicolon",
  priority = Priority.MAJOR,
  tags = {Tags.CONVENTION, Tags.PITFALL})
@ActivatedByDefault
@SqaleConstantRemediation("2min")
public class SemicolonDeclarationCheck extends SubscriptionVisitorCheck {

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(
      Tree.Kind.PROPERTY_DECLARATION,
      Tree.Kind.VARIABLE_DECLARATION,
      Tree.Kind.LESS_VARIABLE_DECLARATION);
  }

  @Override
  public void visitNode(Tree tree) {
    DeclarationTree declarationTree = (DeclarationTree) tree;
    if (declarationTree.semicolon() == null
      && !declarationTree.parent().is(Tree.Kind.SCSS_PARAMETER, Tree.Kind.LESS_MIXIN_PARAMETER)) {
      addPreciseIssue(tree, "Add a semicolon at the end of this declaration.");
    }
  }

}
