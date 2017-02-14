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
import com.google.common.io.Files;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.checks.Tags;
import org.sonar.css.visitors.CharsetAwareVisitor;
import org.sonar.plugins.css.api.tree.css.StyleSheetTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Rule(
  key = "file-too-many-lines",
  name = "Files should not have too many lines",
  priority = Priority.MAJOR,
  tags = {Tags.UNDERSTANDABILITY})
@ActivatedByDefault
@SqaleConstantRemediation("20min")
public class FileTooManyLinesCheck extends DoubleDispatchVisitorCheck implements CharsetAwareVisitor {

  private static final int DEFAULT_MAX_LINES = 1000;
  private Charset charset;

  @RuleProperty(
    key = "Max",
    description = "The maximum authorized lines in a file",
    defaultValue = "" + DEFAULT_MAX_LINES)
  private int max = DEFAULT_MAX_LINES;


  @Override
  public void visitStyleSheet(StyleSheetTree tree) {
    List<String> lines;
    try {
      lines = Files.readLines(getContext().getFile(), charset);
    } catch (IOException e) {
      throw new IllegalStateException("Check css:" + this.getClass().getAnnotation(Rule.class).key()
        + ": Error while reading " + getContext().getFile().getName(), e);
    }
    if (lines.size() > max) {
      addFileIssue("This file contains " + lines.size() + " lines, that is more than the maximum allowed " + max + "."
        + " Split this file into smaller files.");
    }
    super.visitStyleSheet(tree);
  }

  @Override
  public void setCharset(Charset charset) {
    this.charset = charset;
  }

  @VisibleForTesting
  void setMax(int max) {
    this.max = max;
  }

}
