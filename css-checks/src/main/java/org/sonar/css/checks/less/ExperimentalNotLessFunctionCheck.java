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
package org.sonar.css.checks.less;

import com.google.common.annotations.VisibleForTesting;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.checks.CheckList;
import org.sonar.css.checks.CheckUtils;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.FunctionTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Rule(
  key = "experimental-not-less-function-usage",
  name = "Experimental functions should not be used",
  priority = Priority.MAJOR,
  tags = {Tags.CONVENTION, Tags.BROWSER_COMPATIBILITY})
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class ExperimentalNotLessFunctionCheck extends DoubleDispatchVisitorCheck {

  private static final String DEFAULT_FUNCTIONS_TO_EXCLUDE = "";

  @RuleProperty(
    key = "functionsToExclude",
    description = "A regular expression to exclude CSS functions from being considered as experimental. Example: \"conic-gradient|count.*\". See " + CheckUtils.LINK_TO_JAVA_REGEX_PATTERN_DOC + " for detailed regular expression syntax.",
    defaultValue = DEFAULT_FUNCTIONS_TO_EXCLUDE)
  private String functionsToExclude = DEFAULT_FUNCTIONS_TO_EXCLUDE;

  @Override
  public void visitFunction(FunctionTree tree) {
    if (!tree.standardFunction().isLess()
      && !tree.standardFunction().getName().matches(functionsToExclude)
      && (tree.isVendorPrefixed() || tree.standardFunction().isExperimental())) {
      addPreciseIssue(
        tree.function(),
        "Remove this usage of the experimental \"" + tree.standardFunction().getName() + "\" function.");
    }
    super.visitFunction(tree);
  }

  @Override
  public void validateParameters() {
    try {
      Pattern.compile(functionsToExclude);
    } catch (PatternSyntaxException exception) {
      throw new IllegalStateException(paramsErrorMessage(), exception);
    }
  }

  @VisibleForTesting
  void setFunctionsToExclude(String functionsToExclude) {
    this.functionsToExclude = functionsToExclude;
  }

  private String paramsErrorMessage() {
    return CheckUtils.paramsErrorMessage(
      this.getClass(),
      CheckList.LESS_REPOSITORY_KEY,
      "functionsToExclude parameter \"" + functionsToExclude + "\" is not a valid regular expression.");
  }

}
