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

@Rule(
  key = "obsolete-properties",
  name = "Obsolete properties should not be used",
  priority = Priority.MAJOR,
  tags = {Tags.BROWSER_COMPATIBILITY})
@ActivatedByDefault
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.LANGUAGE_RELATED_PORTABILITY)
@SqaleConstantRemediation("10min")
public class ObsoletePropertiesCheck extends SquidCheck<LexerlessGrammar> {

  private final ImmutableList<String> obsoleteProperties = ImmutableList.of("azimuth", "clip");

  @Override
  public void init() {
    subscribeTo(CssGrammar.PROPERTY);
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (obsoleteProperties.contains(astNode.getTokenValue().toLowerCase())) {
      getContext().createLineViolation(
        this,
        "Remove the obsolete \"{0}\" property.",
        astNode,
        astNode.getTokenValue()
        );
    }
  }

}
