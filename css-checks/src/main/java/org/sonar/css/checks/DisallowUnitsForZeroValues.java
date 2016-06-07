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
import com.sonar.sslr.api.GenericTokenType;

import java.util.regex.Pattern;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.model.Unit;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

/**
 * See https://drafts.csswg.org/css-values-3/#lengths:
 * "However, for zero lengths the unit identifier is optional (i.e. can be syntactically represented as the 0 )."
 *
 * For lengths only, not for other dimensions such as angle, time, etc.
 * See https://developer.mozilla.org/en-US/docs/Web/CSS/time#Summary for example.
 */
@Rule(
  key = "zero-units",
  name = "Units for zero length values should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.CONVENTION, Tags.PERFORMANCE})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.MEMORY_EFFICIENCY)
@SqaleConstantRemediation("2min")
@ActivatedByDefault
public class DisallowUnitsForZeroValues extends CssCheck {

  private static final String ISSUE_MESSAGE = "Remove the unit for this zero length.";

  @Override
  public void init() {
    subscribeTo(CssGrammar.DIMENSION, CssGrammar.PERCENTAGE);
  }

  @Override
  public void visitNode(AstNode node) {
    if (isZeroValue(node) && !node.hasAncestor(CssGrammar.FUNCTION)) {
      if (isLength(node)) {
        addIssue(this, ISSUE_MESSAGE, node.getFirstChild(CssGrammar.unit));
      } else if (isPercentage(node)) {
        addIssue(this, ISSUE_MESSAGE, node.getFirstChild(CssGrammar.PERCENTAGE_SIGN));
      }
    }
  }

  private boolean isLength(AstNode node) {
    return node.is(CssGrammar.DIMENSION) && Unit.LENGTH_UNITS.contains(node.getFirstChild(CssGrammar.unit).getFirstChild().getTokenValue());
  }

  private boolean isZeroValue(AstNode node) {
    return Pattern.compile("(0\\.|\\.)?0+").matcher(node.getFirstChild(GenericTokenType.LITERAL).getTokenValue()).matches();
  }

  private boolean isPercentage(AstNode node) {
    return node.is(CssGrammar.PERCENTAGE);
  }

}
