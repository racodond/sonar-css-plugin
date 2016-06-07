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
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.model.AtRule;
import org.sonar.css.model.atrule.standard.Charset;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "charset-first",
  name = "@charset should be the first element in the style sheet and not be preceded by any character",
  priority = Priority.CRITICAL,
  tags = {Tags.BUG})
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class CharsetFirstCheck extends CssCheck {

  private int offset;

  @Override
  public void init() {
    subscribeTo(CssGrammar.AT_RULE, CssGrammar.BOM);
  }

  @Override
  public void visitFile(AstNode astNode) {
    offset = 0;
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(CssGrammar.BOM)) {
      offset = 1;
    } else {
      AtRule atRule = new AtRule(astNode.getFirstChild(CssGrammar.AT_KEYWORD).getFirstChild(CssGrammar.IDENT).getTokenValue());
      if (atRule.getStandardAtRule() instanceof Charset && !isFirst(astNode.getFirstChild(CssGrammar.AT_KEYWORD))) {
        addIssue(
          this,
          "Move the @charset rule to the beginning of the style sheet.",
          astNode.getFirstChild(CssGrammar.AT_KEYWORD));
      }
    }
  }

  private boolean isFirst(AstNode astNode) {
    return astNode.getTokenLine() == 1 && astNode.getToken().getColumn() == offset;
  }

}
