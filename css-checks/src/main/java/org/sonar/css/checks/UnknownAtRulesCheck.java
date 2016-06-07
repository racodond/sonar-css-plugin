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
import org.sonar.css.model.atrule.UnknownAtRule;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "unknown-at-rules",
  name = "Unknown @-rules should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.PITFALL})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class UnknownAtRulesCheck extends CssCheck {

  @Override
  public void init() {
    subscribeTo(CssGrammar.AT_KEYWORD);
  }

  @Override
  public void leaveNode(AstNode astNode) {
    AtRule atRule = new AtRule(astNode.getFirstChild(CssGrammar.IDENT).getTokenValue());
    if (atRule.getStandardAtRule() instanceof UnknownAtRule && !atRule.isVendorPrefixed()) {
      addIssue(
        this,
        "Remove this usage of the unknown \"" + atRule.getStandardAtRule().getName() + "\" @-rule.",
        astNode);
    }
  }

}
