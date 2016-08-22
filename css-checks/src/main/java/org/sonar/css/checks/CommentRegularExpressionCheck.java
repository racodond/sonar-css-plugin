/*
 * SonarQube CSS Plugin
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
package org.sonar.css.checks;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.css.api.tree.SyntaxTrivia;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.NoSqale;
import org.sonar.squidbridge.annotations.RuleTemplate;

@Rule(
  key = "comment-regular-expression",
  name = "Regular expression on comment",
  priority = Priority.MAJOR)
@RuleTemplate
@NoSqale
public class CommentRegularExpressionCheck extends DoubleDispatchVisitorCheck {

  private static final String DEFAULT_REGULAR_EXPRESSION = ".*";
  private static final String DEFAULT_MESSAGE = "The regular expression matches this comment.";

  @RuleProperty(
    key = "regularExpression",
    description = "The regular expression. See " + CheckUtils.LINK_TO_JAVA_REGEX_PATTERN_DOC + " for detailed regular expression syntax.",
    defaultValue = DEFAULT_REGULAR_EXPRESSION)
  public String regularExpression = DEFAULT_REGULAR_EXPRESSION;

  @RuleProperty(
    key = "message",
    description = "The issue message",
    defaultValue = DEFAULT_MESSAGE)
  public String message = DEFAULT_MESSAGE;

  @Override
  public void visitComment(SyntaxTrivia trivia) {
    if (trivia.text().matches(regularExpression)) {
      addPreciseIssue(trivia, message);
    }
    super.visitComment(trivia);
  }

  @Override
  public void validateParameters() {
    try {
      Pattern.compile(regularExpression);
    } catch (PatternSyntaxException exception) {
      throw new IllegalStateException(paramsErrorMessage(), exception);
    }
  }

  private String paramsErrorMessage() {
    return CheckUtils.paramsErrorMessage(
      this.getClass(),
      "regularExpression parameter \"" + regularExpression + "\" is not a valid regular expression.");
  }

}
