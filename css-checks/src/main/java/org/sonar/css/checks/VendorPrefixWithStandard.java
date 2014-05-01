/*
 * Sonar CSS Plugin
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

import com.sonar.sslr.api.AstNode;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.utils.CssP;
import org.sonar.css.checks.utils.CssProperties;
import org.sonar.css.parser.CssGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;

/**
 * https://github.com/stubbornella/csslint/wiki/Require-standard-property-with-vendor-prefix
 * @author tkende
 *
 */
@Rule(key = "vendor-prefix", priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class VendorPrefixWithStandard extends SquidCheck<LexerlessGrammar> {

  @Override
  public void init() {
    subscribeTo(CssGrammar.declaration);
  }

  @Override
  public void leaveNode(AstNode astNode) {
    String property = astNode.getFirstChild(CssGrammar.property).getTokenValue();
    if (CssProperties.isVendor(property) && CssProperties.getProperty(property) != null) {
      CssP prop = CssP.factory(property);
      if (!isNextExists(astNode, prop.getName())) {
        getContext().createLineViolation(this, "No standard property defined after", astNode);
      }
    }
  }

  private boolean isNextExists(AstNode actual, String propertyName) {
    AstNode next = actual.getNextSibling();
    while (next != null) {
      AstNode property = next.getFirstChild(CssGrammar.property);
      if (property != null) {
        String nextProperty = property.getTokenValue();
        if (propertyName.equalsIgnoreCase(nextProperty)) {
          return true;
        }
      }
      next = next.getNextSibling();
    }
    return false;
  }
}
