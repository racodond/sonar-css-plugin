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

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.css.api.tree.AtKeywordTree;
import org.sonar.plugins.css.api.tree.StyleSheetTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.FileIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "font-faces",
  name = "The number of web fonts should be reduced",
  priority = Priority.MAJOR,
  tags = {Tags.PERFORMANCE})
@SqaleConstantRemediation("30min")
@ActivatedByDefault
public class TooManyWebFontsCheck extends DoubleDispatchVisitorCheck {

  private static final int DEFAULT_THRESHOLD = 2;

  @RuleProperty(
    key = "fontFaceThreshold",
    description = "The maximum allowed number of fonts defined per stylesheet",
    defaultValue = "" + DEFAULT_THRESHOLD)
  private int fontFaceThreshold = DEFAULT_THRESHOLD;

  private final List<AtKeywordTree> fontFaceTrees = new ArrayList<>();

  @Override
  public void visitStyleSheet(StyleSheetTree tree) {
    fontFaceTrees.clear();

    super.visitStyleSheet(tree);

    if (fontFaceTrees.size() > fontFaceThreshold) {
      addIssue();
    }
  }

  @Override
  public void visitAtKeyword(AtKeywordTree tree) {
    if ("font-face".equalsIgnoreCase(tree.keyword().text())) {
      fontFaceTrees.add(tree);
    }
    super.visitAtKeyword(tree);
  }

  @VisibleForTesting
  public void setFontFaceThreshold(int fontFaceThreshold) {
    this.fontFaceThreshold = fontFaceThreshold;
  }

  private void addIssue() {
    FileIssue issue = addFileIssue(
      "Reduce the number of web fonts. The number of @font-face is "
        + fontFaceTrees.size() + " greater than " + fontFaceThreshold + " authorized.");
    fontFaceTrees.stream().forEach(t -> issue.secondary(t, "+1"));
  }

}
