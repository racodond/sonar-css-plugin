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

import com.google.common.collect.ImmutableList;
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

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/stubbornella/csslint/wiki/Require-shorthand-properties
 * @author tkende
 *
 */
@Rule(
  key = "shorthand",
  name = "Shorthand properties should be used whenever possible",
  priority = Priority.MAJOR,
  tags = {Tags.PERFORMANCE, Tags.CONVENTION})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.MEMORY_EFFICIENCY)
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class ShorthandProperties extends SquidCheck<LexerlessGrammar> {

  private static List<String> margin = ImmutableList.<String>of("margin-left", "margin-right", "margin-top", "margin-bottom");
  private static List<String> padding = ImmutableList.<String>of("padding-left", "padding-right", "padding-top", "padding-bottom");

  List<String> properties = new ArrayList<String>();

  @Override
  public void init() {
    subscribeTo(CssGrammar.RULESET, CssGrammar.AT_RULE, CssGrammar.DECLARATION);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.getType().equals(CssGrammar.RULESET) || astNode.getType().equals(CssGrammar.AT_RULE)) {
      properties.clear();
    } else if (astNode.getType().equals(CssGrammar.DECLARATION)) {
      String property = astNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue();
      if (margin.contains(property) || padding.contains(property)) {
        properties.add(property);
      }
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode.getType().equals(CssGrammar.RULESET) || astNode.getType().equals(CssGrammar.AT_RULE)) {
      if (properties.containsAll(margin)) {
        getContext().createLineViolation(this, "Use margin shorthand instead", astNode);
      }
      if (properties.containsAll(padding)) {
        getContext().createLineViolation(this, "Use padding shorthand instead", astNode);
      }
    }
  }

}
