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
import org.sonar.css.checks.CheckList;
import org.sonar.css.checks.CheckUtils;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.AtRuleTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Rule(
  key = "experimental-atrule-usage",
  name = "Experimental @-rules should not be used",
  priority = Priority.MAJOR,
  tags = {Tags.CONVENTION, Tags.BROWSER_COMPATIBILITY})
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class ExperimentalAtRuleCheck extends DoubleDispatchVisitorCheck {

  private static final String DEFAULT_AT_RULES_TO_EXCLUDE = "";

  @RuleProperty(
    key = "atRulesToExclude",
    description = "A regular expression to exclude @-rules from being considered as experimental. Example: \"annotation|bottom.*\". See " + CheckUtils.LINK_TO_JAVA_REGEX_PATTERN_DOC + " for detailed regular expression syntax.",
    defaultValue = DEFAULT_AT_RULES_TO_EXCLUDE)
  private String atRulesToExclude = DEFAULT_AT_RULES_TO_EXCLUDE;

  @Override
  public void visitAtRule(AtRuleTree tree) {
    if (!tree.standardAtRule().getName().matches(atRulesToExclude)
      && (tree.isVendorPrefixed() || tree.standardAtRule().isExperimental())) {
      addPreciseIssue(
        tree.atKeyword(),
        "Remove this usage of the experimental \"" + tree.standardAtRule().getName() + "\" @-rule.");
    }
    super.visitAtRule(tree);
  }

  @Override
  public void validateParameters() {
    try {
      Pattern.compile(atRulesToExclude);
    } catch (PatternSyntaxException exception) {
      throw new IllegalStateException(paramsErrorMessage(), exception);
    }
  }

  @VisibleForTesting
  void setAtRulesToExclude(String atRulesToExclude) {
    this.atRulesToExclude = atRulesToExclude;
  }

  private String paramsErrorMessage() {
    return CheckUtils.paramsErrorMessage(
      this.getClass(),
      CheckList.CSS_REPOSITORY_KEY,
      "atRulesToExclude parameter \"" + atRulesToExclude + "\" is not a valid regular expression.");
  }

}
