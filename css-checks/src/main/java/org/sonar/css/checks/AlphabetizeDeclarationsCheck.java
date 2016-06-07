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

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.issue.PreciseIssue;
import org.sonar.css.model.Property;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

@Rule(
  key = "alphabetize-declarations",
  name = "Rule properties should be alphabetically ordered",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("2min")
public class AlphabetizeDeclarationsCheck extends CssCheck {

  private List<Map.Entry<Property, AstNode>> properties = new ArrayList<>();

  @Override
  public void init() {
    subscribeTo(CssGrammar.RULESET, CssGrammar.AT_RULE, CssGrammar.DECLARATION);
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode.is(CssGrammar.DECLARATION)) {
      Map.Entry<Property, AstNode> propertyNodeTuple = new AbstractMap.SimpleEntry<>(
        new Property(astNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue()),
        astNode.getFirstChild(CssGrammar.PROPERTY));
      properties.add(propertyNodeTuple);
    } else if (astNode.is(CssGrammar.RULESET) || astNode.is(CssGrammar.AT_RULE)) {
      AstNode firstUnsortedPropertyNode = getFirstUnsortedProperty(properties);
      if (firstUnsortedPropertyNode != null) {
        addIssue(astNode, firstUnsortedPropertyNode);
      }
      properties = new ArrayList<>();

    }
  }

  @Nullable
  // TODO : check unsorted with Guava? Ordering.natural().onResultOf(
  private AstNode getFirstUnsortedProperty(List<Map.Entry<Property, AstNode>> propertyNodeTuple) {
    for (int i = 0; i < propertyNodeTuple.size() - 1; i++) {
      if (propertyNodeTuple.get(i).getKey().getStandardProperty().getName().compareTo(propertyNodeTuple.get(i + 1).getKey().getStandardProperty().getName()) > 0) {
        return propertyNodeTuple.get(i + 1).getValue();
      }
    }
    return null;
  }

  private void addIssue(AstNode astNode, AstNode unsortedPropertyNode) {
    AstNode primaryIssueLocationNode;
    if (astNode.is(CssGrammar.RULESET)) {
      if (astNode.getFirstChild(CssGrammar.SELECTOR) != null) {
        primaryIssueLocationNode = astNode.getFirstChild(CssGrammar.SELECTOR);
      } else {
        primaryIssueLocationNode = astNode.getFirstChild(CssGrammar.BLOCK).getFirstChild(CssGrammar.OPEN_CURLY_BRACE);
      }
    } else {
      primaryIssueLocationNode = astNode.getFirstChild(CssGrammar.AT_KEYWORD);
    }
    PreciseIssue issue = addIssue(this, "Alphabetically order these rule's properties.", primaryIssueLocationNode);
    issue.addSecondaryLocation("First unproperly ordered property", unsortedPropertyNode);
  }

}
