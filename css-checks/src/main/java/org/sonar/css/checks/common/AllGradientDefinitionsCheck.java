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

import com.google.common.collect.ImmutableList;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.CheckUtils;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.FunctionTree;
import org.sonar.plugins.css.api.tree.css.PropertyDeclarationTree;
import org.sonar.plugins.css.api.tree.css.RulesetTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Rule(
  key = "gradients",
  name = "Gradient definitions should be set for all vendors",
  priority = Priority.MAJOR,
  tags = {Tags.BROWSER_COMPATIBILITY})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class AllGradientDefinitionsCheck extends DoubleDispatchVisitorCheck {

  // TODO:
  // - Not specific enough because the check does not take into account the property name. What if all gradient functions are used but
  // for different properties? Same about the gradient function itself. What if all vendor prefix are used but for different functions
  // (linear, radial)?
  // - Still up to date to have to declare -webkit-gradient.* ? I don't think so.

  private static final List<String> GRADIENTS = ImmutableList.of(
    "-ms-(linear|radial)-gradient.*",
    "-moz-(linear|radial)-gradient.*",
    "-o-(linear|radial)-gradient.*",
    "-webkit-(linear|radial)-gradient.*",
    "-webkit-gradient.*");

  private static final String GRADIENT_MATCHER = "-(ms|o|moz|webkit)-.*gradient.*";

  @Override
  public void visitRuleset(RulesetTree tree) {
    List<String> missingGradients = missingGradients(tree);

    if (!missingGradients.isEmpty() && missingGradients.size() != GRADIENTS.size()) {
      addPreciseIssue(
        CheckUtils.rulesetIssueLocation(tree),
        "Add missing gradient definitions: " + missingGradients.stream().collect(Collectors.joining(", ")));
    }

    super.visitRuleset(tree);
  }

  private List<String> missingGradients(RulesetTree tree) {
    List<String> missingGradients = new ArrayList<>(GRADIENTS);

    for (PropertyDeclarationTree propertyDeclaration : tree.block().propertyDeclarations()) {
      for (FunctionTree function : propertyDeclaration.value().valueElementsOfType(FunctionTree.class)) {
        String functionName = function.function().text().toLowerCase();
        if (functionName.matches(GRADIENT_MATCHER)) {
          missingGradients.removeIf(functionName::matches);
        }
      }
    }

    return missingGradients;
  }

}
