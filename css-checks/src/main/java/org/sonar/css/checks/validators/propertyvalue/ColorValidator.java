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
package org.sonar.css.checks.validators.propertyvalue;

import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.GenericTokenType;
import org.sonar.css.checks.utils.CssColors;
import org.sonar.css.parser.CssGrammar;

import javax.annotation.Nonnull;

import java.util.List;

public class ColorValidator implements PropertyValueValidator {

  @Override
  public boolean isPropertyValueValid(@Nonnull AstNode astNode) {
    return new EnumValidator(CssColors.SVG_COLORS).isPropertyValueValid(astNode)
      || new EnumValidator(CssColors.CSS4_COLORS).isPropertyValueValid(astNode)
      || new EnumValidator(CssColors.CSS2_SYSTEM_COLORS).isPropertyValueValid(astNode)
      || new EnumValidator(ImmutableList.of("transparent", "currentColor")).isPropertyValueValid(astNode)
      || new HexadecimalColorValidator().isPropertyValueValid(astNode)
      || new RgbColorValidator().isPropertyValueValid(astNode)
      || new HslColorValidator().isPropertyValueValid(astNode);
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "<color> | transparent | currentColor";
  }

  private class HexadecimalColorValidator implements PropertyValueValidator {

    @Override
    public boolean isPropertyValueValid(@Nonnull AstNode astNode) {
      return astNode.getFirstChild(CssGrammar.HASH) != null
        && astNode.getFirstChild(CssGrammar.HASH).getFirstChild(GenericTokenType.LITERAL) != null
        && ("#" + astNode.getFirstChild(CssGrammar.HASH).getFirstChild(GenericTokenType.LITERAL).getTokenValue())
          .matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
    }

    @Override
    @Nonnull
    public String getValidatorFormat() {
      return "";
    }

  }

  private class RgbColorValidator implements PropertyValueValidator {

    @Override
    public boolean isPropertyValueValid(@Nonnull AstNode astNode) {
      return astNode.getFirstChild(CssGrammar.FUNCTION) != null
        && astNode.getFirstChild(CssGrammar.FUNCTION).getFirstChild(CssGrammar.parameters) != null
        && astNode.getFirstChild(CssGrammar.FUNCTION).getFirstChild(CssGrammar.parameters).getFirstChild(CssGrammar.parameter) != null
        && (("rgb".equals(astNode.getFirstChild(CssGrammar.FUNCTION).getTokenValue()) && validateRgbParameters(
          astNode.getFirstChild(CssGrammar.FUNCTION).getFirstChild(CssGrammar.parameters).getFirstChild(CssGrammar.parameter))
          || "rgba".equals(astNode.getFirstChild(CssGrammar.FUNCTION).getTokenValue()) && validateRgbaParameters(
          astNode.getFirstChild(CssGrammar.FUNCTION).getFirstChild(CssGrammar.parameters).getFirstChild(CssGrammar.parameter))));
    }

    @Override
    @Nonnull
    public String getValidatorFormat() {
      return "";
    }

    private boolean validateRgbParameters(AstNode parametersNode) {
      if (parametersNode.getChildren().size() == 5
        && (arePercentageParametersValid(parametersNode.getChildren()) || areNumberParametersValid(parametersNode.getChildren()))) {
        return true;
      }
      return false;
    }

    private boolean validateRgbaParameters(AstNode parametersNode) {
      if (parametersNode.getChildren().size() == 7
        && (arePercentageParametersValid(parametersNode.getChildren()) || areNumberParametersValid(parametersNode.getChildren()))
        && parametersNode.getChildren().get(5).is(CssGrammar.DELIM)
        && isValidAlphaParameter(parametersNode.getChildren().get(6))) {
        return true;
      }
      return false;
    }

    private boolean arePercentageParametersValid(List<AstNode> node) {
      return isValidPercentageParameter(node.get(0))
        && node.get(1).is(CssGrammar.DELIM)
        && isValidPercentageParameter(node.get(2))
        && node.get(3).is(CssGrammar.DELIM)
        && isValidPercentageParameter(node.get(4));
    }

    private boolean areNumberParametersValid(List<AstNode> node) {
      return isValidNumberParameter(node.get(0))
        && node.get(1).is(CssGrammar.DELIM)
        && isValidNumberParameter(node.get(2))
        && node.get(3).is(CssGrammar.DELIM)
        && isValidNumberParameter(node.get(4));
    }

    private boolean isValidPercentageParameter(AstNode parameterNode) {
      return parameterNode.is(CssGrammar.PERCENTAGE)
        && Double.valueOf(parameterNode.getTokenValue()) >= 0
        && Double.valueOf(parameterNode.getTokenValue()) <= 100;
    }

    private boolean isValidNumberParameter(AstNode parameterNode) {
      return parameterNode.is(CssGrammar.NUMBER)
        && parameterNode.getTokenValue().matches("[\\-\\+]{0,1}[0-9]+")
        && Integer.valueOf(parameterNode.getTokenValue()) >= 0
        && Integer.valueOf(parameterNode.getTokenValue()) <= 255;
    }

    private boolean isValidAlphaParameter(AstNode parameterNode) {
      return parameterNode.is(CssGrammar.NUMBER)
        && Double.valueOf(parameterNode.getTokenValue()) >= 0
        && Double.valueOf(parameterNode.getTokenValue()) <= 1;
    }

  }

  private class HslColorValidator implements PropertyValueValidator {
    @Override
    public boolean isPropertyValueValid(@Nonnull AstNode astNode) {
      return astNode.getFirstChild(CssGrammar.FUNCTION) != null
        && astNode.getFirstChild(CssGrammar.FUNCTION).getFirstChild(CssGrammar.parameters) != null
        && astNode.getFirstChild(CssGrammar.FUNCTION).getFirstChild(CssGrammar.parameters).getFirstChild(CssGrammar.parameter) != null
        && (("hsl".equals(astNode.getFirstChild(CssGrammar.FUNCTION).getTokenValue())
          && astNode.getFirstChild(CssGrammar.FUNCTION).getFirstChild(CssGrammar.parameters).getFirstChild(CssGrammar.parameter).getChildren().size() == 5
          && validateHslParameters(astNode.getFirstChild(CssGrammar.FUNCTION).getFirstChild(CssGrammar.parameters).getFirstChild(CssGrammar.parameter)))
          || ("hsla".equals(astNode.getFirstChild(CssGrammar.FUNCTION).getTokenValue())
          && astNode.getFirstChild(CssGrammar.FUNCTION).getFirstChild(CssGrammar.parameters).getFirstChild(CssGrammar.parameter).getChildren().size() == 7
          && validateHslaParameters(astNode.getFirstChild(CssGrammar.FUNCTION).getFirstChild(CssGrammar.parameters).getFirstChild(CssGrammar.parameter))));
    }

    @Nonnull
    @Override
    public String getValidatorFormat() {
      return "";
    }

    private boolean validateHslParameters(AstNode parametersNode) {
      return parametersNode.getChildren().get(0).is(CssGrammar.NUMBER)
        && parametersNode.getChildren().get(1).is(CssGrammar.DELIM)
        && isPercentageParameterValid(parametersNode.getChildren().get(2))
        && parametersNode.getChildren().get(3).is(CssGrammar.DELIM)
        && isPercentageParameterValid(parametersNode.getChildren().get(4));
    }

    private boolean validateHslaParameters(AstNode parametersNode) {
      return validateHslParameters(parametersNode)
        && parametersNode.getChildren().get(5).is(CssGrammar.DELIM)
        && isValidAlphaParameter(parametersNode.getChildren().get(6));
    }

    private boolean isPercentageParameterValid(AstNode parameterNode) {
      return parameterNode.is(CssGrammar.PERCENTAGE)
        && Double.valueOf(parameterNode.getTokenValue()) >= 0
        && Double.valueOf(parameterNode.getTokenValue()) <= 100;
    }

    private boolean isValidAlphaParameter(AstNode parameterNode) {
      return parameterNode.is(CssGrammar.NUMBER)
        && Double.valueOf(parameterNode.getTokenValue()) >= 0
        && Double.valueOf(parameterNode.getTokenValue()) <= 1;
    }
  }

}
