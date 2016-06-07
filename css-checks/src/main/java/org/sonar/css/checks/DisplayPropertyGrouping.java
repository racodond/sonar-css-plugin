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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.sonar.sslr.api.AstNode;

import java.util.List;
import java.util.Map;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.issue.PreciseIssue;
import org.sonar.css.model.Declaration;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

/**
 * https://github.com/stubbornella/csslint/wiki/Require-properties-appropriate-for-display
 */
@Rule(
  key = "display-property-grouping",
  name = "Properties that do not work with the \"display\" property should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.PERFORMANCE, Tags.PITFALL})
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class DisplayPropertyGrouping extends CssCheck {

  private static final Map<String, ImmutableList<String>> RULES = ImmutableMap.of(
    "inline", ImmutableList.of("width", "height", "margin", "margin-top", "margin-bottom", "float"),
    "inline-block", ImmutableList.of("float"),
    "block", ImmutableList.of("vertical-align"),
    "table-*", ImmutableList.of("margin", "margin-top", "margin-bottom", "margin-left", "margin-right", "float"));

  private AstNode displayDeclarationNode;
  private List<String> propertiesToNotUse;

  @Override
  public void init() {
    subscribeTo(CssGrammar.RULESET, CssGrammar.AT_RULE);
  }

  // TODO refactor this to not use getDescendants
  @Override
  public void visitNode(AstNode astNode) {
    List<AstNode> declarationNodes = astNode.getDescendants(CssGrammar.DECLARATION);
    setPropertiesToNotUse(declarationNodes);
    if (propertiesToNotUse != null) {
      addIssues(declarationNodes, propertiesToNotUse);
    }
  }

  private void setPropertiesToNotUse(List<AstNode> declarationNodes) {
    propertiesToNotUse = null;
    for (AstNode declarationNode : declarationNodes) {
      Declaration declaration = new Declaration(declarationNode);
      if ("display".equals(declaration.getProperty().getStandardProperty().getName())) {
        displayDeclarationNode = declarationNode;
        if (declarationNode.getFirstChild(CssGrammar.VALUE).getTokenValue().startsWith("table-")) {
          propertiesToNotUse = RULES.get("table-*");
        } else {
          propertiesToNotUse = RULES.get(declarationNode.getFirstChild(CssGrammar.VALUE).getTokenValue());
        }
        return;
      }
    }
  }

  private void addIssues(List<AstNode> declarationNodes, List<String> propertiesToNotUse) {
    for (AstNode declarationNode : declarationNodes) {
      String property = declarationNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue();
      if (propertiesToNotUse.contains(property)) {
        PreciseIssue issue = addIssue(
          this,
          "Remove this \"" + property + "\" declaration that does not work with the \"display\" declaration.",
          declarationNode);
        issue.addSecondaryLocation("\"display\" property declaration", displayDeclarationNode);
      }
    }
  }

}
