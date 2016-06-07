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
import com.sonar.sslr.api.AstNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.issue.PreciseIssue;
import org.sonar.css.model.Property;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

/**
 * https://github.com/stubbornella/csslint/wiki/Require-shorthand-properties
 */
@Rule(
  key = "shorthand",
  name = "Shorthand properties should be used whenever possible",
  priority = Priority.MAJOR,
  tags = {Tags.PERFORMANCE, Tags.CONVENTION})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.MEMORY_EFFICIENCY)
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class ShorthandProperties extends CssCheck {

  private static final List<String> margin = ImmutableList.of("margin-left", "margin-right", "margin-top", "margin-bottom");
  private static final List<String> padding = ImmutableList.of("padding-left", "padding-right", "padding-top", "padding-bottom");

  private final List<String> properties = new ArrayList<>();
  private final List<AstNode> marginNodes = new ArrayList<>();
  private final List<AstNode> paddingNodes = new ArrayList<>();

  @Override
  public void init() {
    subscribeTo(CssGrammar.RULESET, CssGrammar.AT_RULE, CssGrammar.DECLARATION);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.getType().equals(CssGrammar.RULESET) || astNode.getType().equals(CssGrammar.AT_RULE)) {
      properties.clear();
      marginNodes.clear();
      paddingNodes.clear();
    } else if (astNode.getType().equals(CssGrammar.DECLARATION)) {
      Property property = new Property(astNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue());
      if (margin.contains(property.getStandardProperty().getName())) {
        properties.add(property.getStandardProperty().getName());
        marginNodes.add(astNode.getFirstChild(CssGrammar.PROPERTY));
      } else if (padding.contains(property.getStandardProperty().getName())) {
        properties.add(property.getStandardProperty().getName());
        paddingNodes.add(astNode.getFirstChild(CssGrammar.PROPERTY));
      }
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    AstNode primaryIssueLocationNode;
    if (astNode.is(CssGrammar.RULESET)) {
      if (astNode.getFirstChild(CssGrammar.SELECTOR) != null) {
        primaryIssueLocationNode = astNode.getFirstChild(CssGrammar.SELECTOR);
      } else {
        primaryIssueLocationNode = astNode.getFirstChild(CssGrammar.BLOCK).getFirstChild(CssGrammar.OPEN_CURLY_BRACE);
      }
    } else if (astNode.getType().equals(CssGrammar.AT_RULE)) {
      primaryIssueLocationNode = astNode.getFirstChild(CssGrammar.AT_KEYWORD);
    } else {
      return;
    }
    if (properties.containsAll(margin)) {
      createIssue("margin", primaryIssueLocationNode);
    }
    if (properties.containsAll(padding)) {
      createIssue("padding", primaryIssueLocationNode);
    }
  }

  private void createIssue(String shorthandProperty, AstNode primaryIssueLocation) {
    List<AstNode> nodes = Collections.emptyList();
    PreciseIssue issue = addIssue(this, "Use the \"" + shorthandProperty + "\" shorthand property instead.", primaryIssueLocation);
    if ("margin".equals(shorthandProperty)) {
      nodes = marginNodes;
    } else if ("padding".equals(shorthandProperty)) {
      nodes = paddingNodes;
    }
    for (AstNode node : nodes) {
      issue.addSecondaryLocation("\"" + shorthandProperty + "\" property", node);
    }
  }

}
