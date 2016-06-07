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

import com.sonar.sslr.api.AstNode;

import java.util.HashSet;
import java.util.Set;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.CssCheck;
import org.sonar.css.issue.PreciseIssue;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

/**
 * https://github.com/stubbornella/csslint/wiki/Don%27t-use-too-many-web-fonts
 */
@Rule(
  key = "font-faces",
  name = "The number of web fonts should be reduced",
  priority = Priority.MAJOR,
  tags = {Tags.PERFORMANCE})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.MEMORY_EFFICIENCY)
@SqaleConstantRemediation("30min")
@ActivatedByDefault
public class TooManyWebFonts extends CssCheck {

  private static final int DEFAULT_THRESHOLD = 2;

  @RuleProperty(
    key = "fontFaceThreshold",
    description = "The maximum allowed number of fonts defined per stylesheet",
    defaultValue = "" + DEFAULT_THRESHOLD)
  private int fontFaceThreshold = DEFAULT_THRESHOLD;

  private Set<AstNode> fontFaceNodes;

  @Override
  public void init() {
    subscribeTo(CssGrammar.AT_RULE);
  }

  @Override
  public void visitFile(AstNode astNode) {
    fontFaceNodes = new HashSet<>();
  }

  @Override
  public void visitNode(AstNode astNode) {
    if ("font-face".equals(astNode.getFirstChild(CssGrammar.AT_KEYWORD).getFirstChild(CssGrammar.IDENT).getTokenValue())) {
      fontFaceNodes.add(astNode.getFirstChild(CssGrammar.AT_KEYWORD));
    }
  }

  @Override
  public void leaveFile(AstNode astNode) {
    if (fontFaceNodes.size() > fontFaceThreshold) {
      PreciseIssue issue = addFileIssue(
        this,
        "Reduce the number of web fonts. The number of @font-face is " + fontFaceNodes.size() + " greater than " + fontFaceThreshold + " authorized.");
      for (AstNode fontFaceNode : fontFaceNodes) {
        issue.addSecondaryLocation("+1", fontFaceNode);
      }
    }
  }

  public void setFontFaceThreshold(int fontFaceThreshold) {
    this.fontFaceThreshold = fontFaceThreshold;
  }

}
