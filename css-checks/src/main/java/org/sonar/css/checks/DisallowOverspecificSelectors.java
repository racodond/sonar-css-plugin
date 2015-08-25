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
import org.sonar.check.RuleProperty;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

/**
 * @author tkende
 */
@Rule(
  key = "overspecific-selectors",
  name = "Over-specified selectors should be simplified",
  priority = Priority.MAJOR,
  tags = {Tags.DESIGN})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.LOGIC_CHANGEABILITY)
@SqaleConstantRemediation("2h")
@ActivatedByDefault
public class DisallowOverspecificSelectors extends SquidCheck<LexerlessGrammar> {

  private static final int DEFAULT_NUM_LEVELS = 3;

  @RuleProperty(
    key = "deepnessThreshold",
    description = "The maximum allowed depth of a selector",
    defaultValue = "" + DEFAULT_NUM_LEVELS)
  private int deepnessThreshold = DEFAULT_NUM_LEVELS;

  private int deepness;

  @Override
  public void init() {
    subscribeTo(CssGrammar.SUB_SELECTOR, CssGrammar.SIMPLE_SELECTOR);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(CssGrammar.SUB_SELECTOR)) {
      deepness = 0;
    } else {
      deepness++;
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode.is(CssGrammar.SUB_SELECTOR) && deepness > deepnessThreshold) {
      getContext().createLineViolation(this, "Simplify this over-specified selector", astNode);
    }
  }

  public void setDeepnessThreshold(int deepnessThreshold) {
    this.deepnessThreshold = deepnessThreshold;
  }

}
