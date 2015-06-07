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
package org.sonar.css.checks.utils;

import com.sonar.sslr.api.AstNode;
import org.sonar.css.checks.utils.valueelements.DelimiterValueElement;
import org.sonar.css.checks.utils.valueelements.DimensionValueElement;
import org.sonar.css.checks.utils.valueelements.FunctionValueElement;
import org.sonar.css.checks.utils.valueelements.HashValueElement;
import org.sonar.css.checks.utils.valueelements.IdentifierValueElement;
import org.sonar.css.checks.utils.valueelements.NotSupportedValueElement;
import org.sonar.css.checks.utils.valueelements.NumberValueElement;
import org.sonar.css.checks.utils.valueelements.PercentageValueElement;
import org.sonar.css.checks.utils.valueelements.StringValueElement;
import org.sonar.css.checks.utils.valueelements.UriValueElement;
import org.sonar.css.parser.CssGrammar;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

public class CssValue {

  @Nonnull
  private List<CssValueElement> valueElements = new ArrayList<>();

  private boolean hasImportantAnnotation = false;

  public CssValue(@Nonnull AstNode valueAstNode) {
    if (valueAstNode.getChildren() != null) {
      for (AstNode valueElementNode : valueAstNode.getChildren()) {
        if (!valueElementNode.is(CssGrammar.IMPORTANT)) {
          valueElements.add(convertAstNodeToCssValueElement(valueElementNode));
        } else {
          hasImportantAnnotation = true;
        }
      }
    }
  }

  private CssValueElement convertAstNodeToCssValueElement(AstNode valueElementNode) {
    if (valueElementNode.is(CssGrammar.IDENT)) {
      return new IdentifierValueElement(valueElementNode);
    }
    if (valueElementNode.is(CssGrammar.FUNCTION)) {
      return new FunctionValueElement(valueElementNode);
    }
    if (valueElementNode.is(CssGrammar.NUMBER)) {
      return new NumberValueElement(valueElementNode);
    }
    if (valueElementNode.is(CssGrammar.PERCENTAGE)) {
      return new PercentageValueElement(valueElementNode);
    }
    if (valueElementNode.is(CssGrammar.URI)) {
      return new UriValueElement(valueElementNode);
    }
    if (valueElementNode.is(CssGrammar.DIMENSION)) {
      return new DimensionValueElement(valueElementNode);
    }
    if (valueElementNode.is(CssGrammar.HASH)) {
      return new HashValueElement(valueElementNode);
    }
    if (valueElementNode.is(CssGrammar.STRING)) {
      return new StringValueElement(valueElementNode);
    }
    if (valueElementNode.is(CssGrammar.DELIM)) {
      return new DelimiterValueElement(valueElementNode);
    }
    return new NotSupportedValueElement(valueElementNode);
  }

  @Nonnull
  public List<CssValueElement> getValueElements() {
    return valueElements;
  }

  public int getNumberOfValueElements() {
    return valueElements.size();
  }

}
