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

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.css.model.atrule.standard.Charset;
import org.sonar.plugins.css.api.tree.css.AtRuleTree;
import org.sonar.plugins.css.api.tree.css.StringTree;
import org.sonar.plugins.css.api.tree.scss.ScssMultilineStringTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.regex.Pattern;

@Rule(
  key = "single-quotes",
  name = "Single quotes should be used instead of double quotes for strings",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION})
@SqaleConstantRemediation("2min")
@ActivatedByDefault
public class SingleQuotesCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitString(StringTree tree) {
    checkForDoubleQuotes(tree);
    super.visitString(tree);
  }

  @Override
  public void visitScssMultilineString(ScssMultilineStringTree tree) {
    checkForDoubleQuotes(tree);
    super.visitScssMultilineString(tree);
  }

  private void checkForDoubleQuotes(StringTree tree) {
    if (tree.text().startsWith("\"")
      && !containsQuote(tree.text())
      && !isEncodingOfCharsetAtRule(tree)) {
      addPreciseIssue(tree, "Wrap this string with single quotes instead of double quotes.");
    }
  }

  private boolean isEncodingOfCharsetAtRule(StringTree tree) {
    return tree.parent() != null
      && tree.parent().parent() instanceof AtRuleTree
      && ((AtRuleTree) tree.parent().parent()).standardAtRule() instanceof Charset;
  }

  private boolean containsQuote(String text) {
    return Pattern.compile("'").matcher(text).find();
  }

}
