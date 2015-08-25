/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende and David RACODON
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
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

import java.util.List;

@Rule(
  key = "one-declaration-per-line",
  name = "There should be one single declaration per line",
  priority = Priority.MINOR,
  tags = {Tags.FORMAT})
@ActivatedByDefault
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("2min")
public class OneDeclarationPerLineCheck extends SquidCheck<LexerlessGrammar> {

  @Override
  public void init() {
    subscribeTo(CssGrammar.SUP_DECLARATION);
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (!isThereOneSingleDeclarationPerLine(astNode.getChildren(CssGrammar.DECLARATION, CssGrammar.VARIABLE_DECLARATION))) {
      getContext().createLineViolation(this, "Define each declaration on a separate line", astNode.getParent());
    }
  }

  private boolean isThereOneSingleDeclarationPerLine(List<AstNode> declarations) {
    for (int i = 0; i < declarations.size() - 1; i++) {
      if (declarations.get(i).getTokenLine() == declarations.get(i + 1).getTokenLine()) {
        return false;
      }
    }
    return true;
  }

}
