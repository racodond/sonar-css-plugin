/*
 * SonarQube CSS Plugin
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
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleLinearRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

/**
 * @author tkende
 *
 */
@Rule(
  key = "S2735",
  name = "Stylesheets should not be too \"@import\" too many other sheets",
  priority = Priority.CRITICAL)
@ActivatedByDefault
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleLinearRemediation(coeff = "10min", effortToFixDescription = "number of imports beyond the limit")
public class ImportNumberThreshold extends SquidCheck<LexerlessGrammar> {

  private static final int DEFAULT_THRESHOLD = 31;

  private int currentImportCount;

  @Override
  public void init() {
    subscribeTo(CssGrammar.AT_RULE);
  }

  @Override
  public void visitFile(AstNode astNode) {
    currentImportCount = 0;
  }

  @Override
  public void visitNode(AstNode astNode) {
    if ("import".equals(astNode.getFirstChild(CssGrammar.AT_KEYWORD).getFirstChild(CssGrammar.IDENT).getTokenValue())) {
      currentImportCount++;
    }
  }

  @Override
  public void leaveFile(AstNode astNode) {
    if (currentImportCount > DEFAULT_THRESHOLD) {
      getContext().createFileViolation(this, "This sheet imports {0,number,integer} other sheets, {1,number,integer}" +
        " more than the {2,number,integer} maximum.",
        currentImportCount, currentImportCount - DEFAULT_THRESHOLD, DEFAULT_THRESHOLD);
    }
  }
}
