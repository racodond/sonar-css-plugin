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
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

/**
 * https://github.com/stubbornella/csslint/wiki/Disallow-universal-selector
 */
@Rule(
  key = "universal-selector",
  name = "Universal selector should not be used as key part",
  priority = Priority.MAJOR,
  tags = {Tags.PERFORMANCE})
@SqaleConstantRemediation("1h")
@ActivatedByDefault
public class DisallowUniversalSelector extends CssCheck {

  @Override
  public void init() {
    subscribeTo(CssGrammar.UNIVERSAL_SELECTOR);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if ("*".equals(astNode.getTokenValue()) && astNode.getFirstAncestor(CssGrammar.SUB_SELECTOR).getNextSibling() == null) {
      addIssue(this, "Remove this usage of the universal selector as key part.", astNode);
    }
  }

}
