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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

@Rule(
  key = "one-declaration-per-line",
  name = "There should be one single declaration per line",
  priority = Priority.MINOR,
  tags = {Tags.FORMAT})
@ActivatedByDefault
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("2min")
public class OneDeclarationPerLineCheck extends CssCheck {

  @Override
  public void init() {
    subscribeTo(CssGrammar.SUP_DECLARATION);
  }

  @Override
  public void leaveNode(AstNode astNode) {
    for (AstNode declarationsOnSameLine : getDeclarationsOnSameLine(astNode.getChildren(CssGrammar.DECLARATION, CssGrammar.VARIABLE_DECLARATION))) {
      addIssue(this, "Define this declaration on a separate line.", declarationsOnSameLine);
    }
  }

  private Set<AstNode> getDeclarationsOnSameLine(List<AstNode> declarations) {
    Set<AstNode> declarationsOnSameLine = new HashSet<>();
    for (int i = 0; i < declarations.size() - 1; i++) {
      if (declarations.get(i).getTokenLine() == declarations.get(i + 1).getTokenLine()) {
        declarationsOnSameLine.add(declarations.get(i + 1));
      }
    }
    return declarationsOnSameLine;
  }

}
