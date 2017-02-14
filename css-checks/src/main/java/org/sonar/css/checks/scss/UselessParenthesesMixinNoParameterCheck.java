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
package org.sonar.css.checks.scss;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.scss.ScssDirectiveNameParametersTree;
import org.sonar.plugins.css.api.tree.scss.ScssIncludeTree;
import org.sonar.plugins.css.api.tree.scss.ScssMixinTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "useless-parentheses-mixin-no-parameter",
  name = "Useless parentheses following @include and @mixin with no parameter should be removed",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION})
@SqaleConstantRemediation("2min")
@ActivatedByDefault
public class UselessParenthesesMixinNoParameterCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitScssMixin(ScssMixinTree tree) {
    checkForUselessParentheses(tree);
    super.visitScssMixin(tree);
  }

  @Override
  public void visitScssInclude(ScssIncludeTree tree) {
    checkForUselessParentheses(tree);
    super.visitScssInclude(tree);
  }

  private void checkForUselessParentheses(ScssDirectiveNameParametersTree tree) {
    if (tree.parameters() != null && tree.parameters().parameters() == null) {
      addPreciseIssue(tree.parameters(), "Remove those useless parentheses.");
    }
  }

}
