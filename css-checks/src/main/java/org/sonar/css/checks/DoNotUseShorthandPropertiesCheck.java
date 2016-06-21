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

import java.util.Set;
import java.util.stream.Collectors;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.model.Property;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.StandardPropertyFactory;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "do-not-use-shorthand-properties",
  name = "Shorthand properties should not be used",
  priority = Priority.MINOR,
  tags = {Tags.PITFALL, Tags.UNDERSTANDABILITY})
@SqaleConstantRemediation("5min")
public class DoNotUseShorthandPropertiesCheck extends CssCheck {

  private static final Set<String> SHORTHAND_PROPERTIES = StandardPropertyFactory.getAll()
    .stream()
    .filter(StandardProperty::isShorthand)
    .map(StandardProperty::getName)
    .collect(Collectors.toSet());

  @Override
  public void init() {
    subscribeTo(CssGrammar.PROPERTY);
  }

  @Override
  public void visitNode(AstNode propertyNode) {
    Property property = new Property(propertyNode.getTokenValue());
    if (SHORTHAND_PROPERTIES.contains(property.getStandardProperty().getName())) {
      addIssue(
        this,
        "Replace this \"" + property.getStandardProperty().getName() + "\" shorthand property with its longhand properties: "
          + String.join(", ", property.getStandardProperty().getShorthandForPropertyNames().stream().sorted().collect(Collectors.toList())),
        propertyNode);
    }
  }

}
