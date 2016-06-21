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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.issue.PreciseIssue;
import org.sonar.css.model.Property;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.StandardPropertyFactory;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "shorthand",
  name = "Shorthand properties should be used whenever possible",
  priority = Priority.MINOR,
  tags = {Tags.PERFORMANCE, Tags.CONVENTION})
@SqaleConstantRemediation("5min")
public class UseShorthandPropertiesCheck extends CssCheck {

  private static final List<StandardProperty> SHORTHAND_PROPERTIES = StandardPropertyFactory.getAll()
    .stream()
    .filter(StandardProperty::isShorthand)
    .collect(Collectors.toList());

  private final Map<String, AstNode> declaredProperties = new HashMap<>();

  @Override
  public void init() {
    subscribeTo(CssGrammar.RULESET, CssGrammar.AT_RULE, CssGrammar.DECLARATION);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(CssGrammar.DECLARATION)) {
      declaredProperties.put(
        new Property(astNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue()).getStandardProperty().getName(),
        astNode.getFirstChild(CssGrammar.PROPERTY));
    } else {
      declaredProperties.clear();
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode.is(CssGrammar.RULESET, CssGrammar.AT_RULE)) {
      SHORTHAND_PROPERTIES
        .stream()
        .filter(p -> declaredProperties.keySet().containsAll(p.getShorthandForPropertyNames()))
        .forEach(p -> createIssue(p, astNode));
    }
  }

  private void createIssue(StandardProperty shorthandProperty, AstNode astNode) {
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

    PreciseIssue issue = addIssue(this, "Use the \"" + shorthandProperty.getName() + "\" shorthand property instead.", primaryIssueLocationNode);

    for (Iterator<Map.Entry<String, AstNode>> it = declaredProperties.entrySet().iterator(); it.hasNext();) {
      Map.Entry<String, AstNode> entry = it.next();
      if (shorthandProperty.getShorthandForPropertyNames().contains(entry.getKey())) {
        issue.addSecondaryLocation("\"" + shorthandProperty.getName() + "\" property", entry.getValue());
        it.remove();
      }
    }
  }

}
