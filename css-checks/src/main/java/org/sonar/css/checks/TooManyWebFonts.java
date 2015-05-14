/*
 * Sonar CSS Plugin
 * Copyright (C) 2013 Tamas Kende
 * kende.tamas@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.css.checks;

import com.sonar.sslr.api.AstNode;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.parser.CssGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;

/**
 * https://github.com/stubbornella/csslint/wiki/Don%27t-use-too-many-web-fonts
 * @author tkende
 *
 */
@Rule(
  key = "font-faces",
  name = "Don't use too many web fonts",
  priority = Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.MEMORY_EFFICIENCY)
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class TooManyWebFonts extends SquidCheck<LexerlessGrammar> {

  private static final int DEFAULT_THRESHOLD = 2;

  @RuleProperty(key = "fontFaceThreshold", defaultValue = ""
    + DEFAULT_THRESHOLD)
  private int fontFaceThreshold = DEFAULT_THRESHOLD;

  private int currentFontFace;

  @Override
  public void init() {
    subscribeTo(CssGrammar.AT_RULE);
  }

  @Override
  public void visitFile(AstNode astNode) {
    currentFontFace = 0;
  }

  @Override
  public void visitNode(AstNode astNode) {
    if ("font-face".equals(astNode.getFirstChild(CssGrammar.AT_KEYWORD).getFirstChild(CssGrammar.IDENT).getTokenValue())) {
      currentFontFace++;
    }
  }

  @Override
  public void leaveFile(AstNode astNode) {
    if (currentFontFace > fontFaceThreshold) {
      getContext().createLineViolation(this, "Do not use too many web fonts, the number of font-faces is {0} greater than {1} authorized.", astNode, currentFontFace,
          fontFaceThreshold);
    }
  }

  public void setFontFaceThreshold(int fontFaceThreshold) {
    this.fontFaceThreshold = fontFaceThreshold;
  }

}
