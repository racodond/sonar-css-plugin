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
import org.sonar.css.model.Function;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "experimental-function-usage",
  name = "Experimental functions should not be used",
  priority = Priority.MAJOR,
  tags = {Tags.CONVENTION, Tags.BROWSER_COMPATIBILITY})
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class ExperimentalFunctionUsageCheck extends CssCheck {

  @Override
  public void init() {
    subscribeTo(CssGrammar.FUNCTION);
  }

  @Override
  public void visitNode(AstNode astNode) {
    Function function = new Function(astNode.getFirstChild(CssGrammar.IDENT).getTokenValue());
    if (function.isVendorPrefixed() || function.getStandardFunction().isExperimental()) {
      addIssue(
        this,
        "Remove this usage of the experimental \"" + function.getStandardFunction().getName() + "\" function.",
        astNode.getFirstChild(CssGrammar.IDENT));
    }
  }

}
