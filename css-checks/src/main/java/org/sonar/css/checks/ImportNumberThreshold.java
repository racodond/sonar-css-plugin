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

import java.util.ArrayList;
import java.util.List;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.issue.PreciseIssue;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleLinearRemediation;

import javax.annotation.Nullable;

@Rule(
  key = "S2735",
  name = "Stylesheets should not \"@import\" too many other sheets",
  priority = Priority.CRITICAL,
  tags = {Tags.BROWSER_COMPATIBILITY, Tags.BUG})
@ActivatedByDefault
@SqaleLinearRemediation(coeff = "10min", effortToFixDescription = "number of imports beyond the limit")
public class ImportNumberThreshold extends CssCheck {

  private static final int DEFAULT_THRESHOLD = 31;

  private List<AstNode> importNodes;

  @Override
  public void init() {
    subscribeTo(CssGrammar.AT_RULE);
  }

  @Override
  public void visitFile(@Nullable AstNode astNode) {
    importNodes = new ArrayList<>();
  }

  @Override
  public void visitNode(AstNode astNode) {
    if ("import".equals(astNode.getFirstChild(CssGrammar.AT_KEYWORD).getFirstChild(CssGrammar.IDENT).getTokenValue())) {
      importNodes.add(astNode.getFirstChild(CssGrammar.AT_KEYWORD));
    }
  }

  @Override
  public void leaveFile(@Nullable AstNode astNode) {
    if (importNodes.size() > DEFAULT_THRESHOLD) {
      PreciseIssue issue = addFileIssue(
        this,
        String.format(
          "Reduce the number of @import rules. This sheet imports %s other sheets, %s more than the %s maximum allowed.",
          importNodes.size(),
          importNodes.size() - DEFAULT_THRESHOLD,
          DEFAULT_THRESHOLD));
      for (AstNode importNode : importNodes) {
        issue.addSecondaryLocation("+1", importNode);
      }
      issue.setEffortToFix((double) importNodes.size() - DEFAULT_THRESHOLD);
    }
  }

}
