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

import java.text.MessageFormat;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.model.Declaration;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "validate-property-value",
  name = "Property values should be valid",
  priority = Priority.CRITICAL,
  tags = {Tags.BUG})
@ActivatedByDefault
@SqaleConstantRemediation("10min")
public class ValidatePropertyValueCheck extends CssCheck {

  @Override
  public void init() {
    subscribeTo(CssGrammar.DECLARATION);
  }

  @Override
  public void leaveNode(AstNode declarationNode) {
    Declaration declaration = new Declaration(declarationNode);
    if (!declaration.getProperty().isVendorPrefixed() && !declaration.isValid()) {
      addIssue(
        this,
        MessageFormat.format(
          "Update the invalid value of property \"{0}\". Expected format: {1}",
          declaration.getProperty().getStandardProperty().getName(),
          declaration.getProperty().getStandardProperty().getValidatorFormat()),
        declarationNode);
    }
  }

}
