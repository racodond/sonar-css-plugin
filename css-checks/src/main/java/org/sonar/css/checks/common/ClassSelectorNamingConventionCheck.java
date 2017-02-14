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

import com.google.common.annotations.VisibleForTesting;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.checks.CheckList;
import org.sonar.css.checks.CheckUtils;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.ClassSelectorTree;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.text.MessageFormat;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Rule(
  key = "class-selector-naming-convention",
  name = "Class selectors should follow a naming convention",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class ClassSelectorNamingConventionCheck extends DoubleDispatchVisitorCheck {

  private static final String DEFAULT_FORMAT = "^[a-z][-a-z0-9]*$";
  @RuleProperty(
    key = "Format",
    description = "Regular expression used to check class selectors against. See "
      + CheckUtils.LINK_TO_JAVA_REGEX_PATTERN_DOC
      + " for detailed regular expression syntax.",
    defaultValue = DEFAULT_FORMAT)
  private String format = DEFAULT_FORMAT;

  @Override
  public void visitClassSelector(ClassSelectorTree tree) {
    if (!tree.className().isInterpolated() && !tree.className().text().matches(format)) {
      addIssue(tree.className());
    }
    super.visitClassSelector(tree);
  }

  @VisibleForTesting
  void setFormat(String format) {
    this.format = format;
  }

  @Override
  public void validateParameters() {
    try {
      Pattern.compile(format);
    } catch (PatternSyntaxException exception) {
      throw new IllegalStateException(paramsErrorMessage(), exception);
    }
  }

  private String paramsErrorMessage() {
    return CheckUtils.paramsErrorMessage(
      this.getClass(),
      CheckList.CSS_REPOSITORY_KEY,
      MessageFormat.format("format parameter \"{0}\" is not a valid regular expression.", format));
  }

  private void addIssue(IdentifierTree classNameTree) {
    addPreciseIssue(
      classNameTree,
      MessageFormat.format("Rename class selector \"{0}\" to match the regular expression: {1}", classNameTree.text(), format));
  }

}
