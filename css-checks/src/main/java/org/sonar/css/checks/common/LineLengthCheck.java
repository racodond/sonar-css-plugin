/*
 * SonarQube CSS / Less Plugin
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
package org.sonar.css.checks.common;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.Files;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.checks.Tags;
import org.sonar.css.visitors.CharsetAwareVisitor;
import org.sonar.plugins.css.api.tree.css.StyleSheetTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "line-length",
  name = "Lines should not be too long",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION})
@SqaleConstantRemediation("1min")
@ActivatedByDefault
public class LineLengthCheck extends DoubleDispatchVisitorCheck implements CharsetAwareVisitor {

  private static final int DEFAULT_MAXIMUM_LINE_LENGTH = 120;
  private Charset charset;

  @RuleProperty(
    key = "maximumLineLength",
    description = "The maximum authorized line length.",
    defaultValue = "" + DEFAULT_MAXIMUM_LINE_LENGTH)
  private int maximumLineLength = DEFAULT_MAXIMUM_LINE_LENGTH;

  @Override
  public void visitStyleSheet(StyleSheetTree tree) {
    List<String> lines;
    try {
      lines = Files.readLines(getContext().getFile(), charset);
    } catch (IOException e) {
      throw new IllegalStateException(readFileErrorMessage(), e);
    }

    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      if (line.length() > maximumLineLength) {
        addLineIssue(i + 1, issueMessage(line.length()));
      }
    }
    super.visitStyleSheet(tree);
  }

  @Override
  public void setCharset(Charset charset) {
    this.charset = charset;
  }

  @VisibleForTesting
  public void setMaximumLineLength(int maximumLineLength) {
    this.maximumLineLength = maximumLineLength;
  }

  private String issueMessage(int lineLength) {
    return MessageFormat.format(
      "The line contains {0,number,integer} characters which is greater than {1,number,integer} authorized.",
      lineLength,
      maximumLineLength);
  }

  private String readFileErrorMessage() {
    return "Check css:" + this.getClass().getAnnotation(Rule.class).key()
      + ": Error while reading " + getContext().getFile().getName();
  }
}
