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
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ClassSelectorTree;
import org.sonar.plugins.css.api.tree.css.IdSelectorTree;
import org.sonar.plugins.css.api.tree.scss.ScssPlaceholderSelectorTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.text.MessageFormat;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Rule(
  key = "selector-naming-convention",
  name = "Selectors should follow a naming convention",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION},
  status = "DEPRECATED")
@SqaleConstantRemediation("10min")
public class SelectorNamingConventionCheck extends DoubleDispatchVisitorCheck {

  private static final String DEFAULT_FORMAT = "^[a-z][-a-z0-9]*$";
  @RuleProperty(
    key = "Format",
    description = "Regular expression used to check the selector names against. See " + CheckUtils.LINK_TO_JAVA_REGEX_PATTERN_DOC + " for detailed regular expression syntax.",
    defaultValue = DEFAULT_FORMAT)
  private String format = DEFAULT_FORMAT;

  @Override
  public void visitClassSelector(ClassSelectorTree tree) {
    if (!tree.className().isInterpolated() && !tree.className().text().matches(format)) {
      addIssue(tree.className(), tree.className().text());
    }
    super.visitClassSelector(tree);
  }

  @Override
  public void visitIdSelector(IdSelectorTree tree) {
    if (!tree.identifier().isInterpolated() && !tree.text().matches(format)) {
      addIssue(tree.identifier(), tree.text());
    }
    super.visitIdSelector(tree);
  }

  @Override
  public void visitScssPlaceholderSelector(ScssPlaceholderSelectorTree tree) {
    if (!tree.name().isInterpolated() && !tree.text().matches(format)) {
      addIssue(tree.name(), tree.text());
    }
    super.visitScssPlaceholderSelector(tree);
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
      "format parameter \"" + format + "\" is not a valid regular expression.");
  }

  private void addIssue(Tree tree, String value) {
    addPreciseIssue(
      tree,
      MessageFormat.format(
        "Rename selector \"{0}\" to match the regular expression: {1}",
        value, format));
  }

}
