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
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.css.ValueTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Rule(
  key = "experimental-identifier-usage",
  name = "Experimental identifiers should not be used",
  priority = Priority.MAJOR,
  tags = {Tags.CONVENTION, Tags.BROWSER_COMPATIBILITY})
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class ExperimentalIdentifierCheck extends DoubleDispatchVisitorCheck {

  private static final String DEFAULT_IDENTIFIERS_TO_EXCLUDE = "";

  @RuleProperty(
    key = "identifiersToExclude",
    description = "A regular expression to exclude identifiers from being considered as experimental. Example: \"gradient|flex.*\". See " + CheckUtils.LINK_TO_JAVA_REGEX_PATTERN_DOC + " for detailed regular expression syntax.",
    defaultValue = DEFAULT_IDENTIFIERS_TO_EXCLUDE)
  private String identifiersToExclude = DEFAULT_IDENTIFIERS_TO_EXCLUDE;

  @Override
  public void visitValue(ValueTree tree) {
    tree.valueElementsOfType(IdentifierTree.class)
      .stream()
      .filter(i -> i.isVendorPrefixed() && !i.name().matches(identifiersToExclude))
      .forEach(i -> addPreciseIssue(i, "Remove this usage of the experimental \"" + i.text() + "\" identifier."));

    super.visitValue(tree);
  }

  @Override
  public void validateParameters() {
    try {
      Pattern.compile(identifiersToExclude);
    } catch (PatternSyntaxException exception) {
      throw new IllegalStateException(paramsErrorMessage(), exception);
    }
  }

  @VisibleForTesting
  void setIdentifiersToExclude(String identifiersToExclude) {
    this.identifiersToExclude = identifiersToExclude;
  }

  private String paramsErrorMessage() {
    return CheckUtils.paramsErrorMessage(
      this.getClass(),
      CheckList.CSS_REPOSITORY_KEY,
      "identifiersToExclude parameter \"" + identifiersToExclude + "\" is not a valid regular expression.");
  }

}
