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

import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.AstNode;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.model.Value;
import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.value.CssValueElement;
import org.sonar.css.model.value.valueelement.DimensionValueElement;
import org.sonar.css.model.value.valueelement.NumberValueElement;
import org.sonar.css.model.value.valueelement.PercentageValueElement;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

/**
 * https://github.com/stubbornella/csslint/wiki/Beware-of-box-model-size
 */
@Rule(
  key = "box-model",
  name = "Box model size should be carefully reviewed",
  priority = Priority.MAJOR,
  tags = {Tags.PITFALL})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("1h")
@ActivatedByDefault
public class BewareOfBoxModel extends CssCheck {

  private static final List<String> WIDTH_SIZING = ImmutableList.of(
    "border", "border-left", "border-right", "border-width", "border-left-width", "border-right-width", "padding", "padding-left", "padding-right");

  private static final List<String> HEIGHT_SIZING = ImmutableList.of(
    "border", "border-top", "border-bottom", "border-width", "border-top-width", "border-bottom-width", "padding", "padding-top", "padding-bottom");

  private static final List<String> PADDING_WIDTH = ImmutableList.of(
    "padding", "padding-top", "padding-bottom", "padding-right", "padding-left");

  private static final List<String> BORDER_WIDTH = ImmutableList.of(
    "border", "border-left", "border-right", "border-top", "border-bottom", "border-top-width", "border-bottom-width", "border-left-width", "border-right-width");

  private Set<Combinations> combinations;

  @Override
  public void init() {
    subscribeTo(CssGrammar.RULESET, CssGrammar.AT_RULE, CssGrammar.DECLARATION);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(CssGrammar.RULESET) || astNode.is(CssGrammar.AT_RULE)) {
      combinations = EnumSet.noneOf(Combinations.class);
    } else if (astNode.is(CssGrammar.DECLARATION)) {
      if (isBoxSizing(astNode)) {
        combinations.clear();
        combinations.add(Combinations.IS_BOX_SIZING);
      }
      if (!combinations.contains(Combinations.IS_BOX_SIZING)) {
        if (!combinations.contains(Combinations.WIDTH_FOUND) && isWidth(astNode)) {
          combinations.add(Combinations.WIDTH_FOUND);
        } else if (!combinations.contains(Combinations.HEIGHT_FOUND) && isHeight(astNode)) {
          combinations.add(Combinations.HEIGHT_FOUND);
        }
        if (isWidthSizing(astNode)) {
          combinations.add(Combinations.WIDTH_SIZING);
        }
        if (isHeightSizing(astNode)) {
          combinations.add(Combinations.HEIGHT_SIZING);
        }
      }
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode.is(CssGrammar.RULESET)
      && (combinations.containsAll(Arrays.asList(Combinations.WIDTH_FOUND, Combinations.WIDTH_SIZING))
        || combinations.containsAll(Arrays.asList(Combinations.HEIGHT_FOUND, Combinations.HEIGHT_SIZING)))) {
      createIssue(astNode);
    }
  }

  private void createIssue(AstNode ruleSetNode) {
    AstNode node;
    if (ruleSetNode.getFirstChild(CssGrammar.SELECTOR) != null) {
      node = ruleSetNode.getFirstChild(CssGrammar.SELECTOR);
    } else {
      node = ruleSetNode.getFirstChild(CssGrammar.BLOCK).getFirstChild(CssGrammar.OPEN_CURLY_BRACE);
    }
    addIssue(this, "Check this potential box model size issue.", node);
  }

  private boolean isWidthSizing(AstNode astNode) {
    return isOtherUsed(WIDTH_SIZING, astNode);
  }

  private boolean isHeightSizing(AstNode astNode) {
    return isOtherUsed(HEIGHT_SIZING, astNode);
  }

  private boolean isOtherUsed(List<String> props, AstNode declaration) {
    String property = declaration.getFirstChild(CssGrammar.PROPERTY).getTokenValue().toLowerCase();
    AstNode valueNode = declaration.getFirstChild(CssGrammar.VALUE);
    return props.contains(property) && !isZeroValue(property, valueNode);
  }

  private boolean isZeroValue(String property, AstNode valueNode) {
    return PADDING_WIDTH.contains(property) && isZeroValuePaddingWidth(valueNode)
      || BORDER_WIDTH.contains(property) && isZeroValueBorderWidth(valueNode);
  }

  private boolean isZeroValueBorderWidth(AstNode valueNode) {
    Value value = new Value(valueNode);
    List<CssValueElement> valueElements = value.getValueElements();
    for (CssValueElement valueElement : valueElements) {
      if (ValidatorFactory.getBorderWidthValidator().isValid(valueElement)) {
        if (valueElement instanceof DimensionValueElement && ((DimensionValueElement) valueElement).isNotZero()
          || valueElement instanceof PercentageValueElement && ((PercentageValueElement) valueElement).isNotZero()
          || valueElement instanceof NumberValueElement && !((NumberValueElement) valueElement).isZero()) {
          return false;
        } else if (!(valueElement instanceof DimensionValueElement)
          && !(valueElement instanceof PercentageValueElement)
          && !(valueElement instanceof NumberValueElement)) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean isZeroValuePaddingWidth(AstNode valueNode) {
    Value value = new Value(valueNode);
    List<CssValueElement> valueElements = value.getValueElements();
    for (CssValueElement valueElement : valueElements) {
      if (ValidatorFactory.getPaddingWidthValidator().isValid(valueElement)) {
        if (valueElement instanceof DimensionValueElement && ((DimensionValueElement) valueElement).isNotZero()
          || valueElement instanceof PercentageValueElement && ((PercentageValueElement) valueElement).isNotZero()
          || valueElement instanceof NumberValueElement && !((NumberValueElement) valueElement).isZero()) {
          return false;
        } else if (!(valueElement instanceof DimensionValueElement)
          && !(valueElement instanceof PercentageValueElement)
          && !(valueElement instanceof NumberValueElement)) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean isBoxSizing(AstNode declaration) {
    String property = declaration.getFirstChild(CssGrammar.PROPERTY).getTokenValue();
    return "box-sizing".equalsIgnoreCase(property);
  }

  private boolean isWidth(AstNode astNode) {
    return Combinations.WIDTH_FOUND.equals(isWidthOrHeight(astNode));
  }

  private boolean isHeight(AstNode astNode) {
    return Combinations.HEIGHT_FOUND.equals(isWidthOrHeight(astNode));
  }

  private Combinations isWidthOrHeight(AstNode declaration) {
    String property = declaration.getFirstChild(CssGrammar.PROPERTY).getToken().getValue();
    if ("height".equalsIgnoreCase(property)) {
      return Combinations.HEIGHT_FOUND;
    } else if ("width".equalsIgnoreCase(property)) {
      return Combinations.WIDTH_FOUND;
    }
    return null;
  }

  private enum Combinations {
    WIDTH_FOUND, WIDTH_SIZING,
    HEIGHT_FOUND, HEIGHT_SIZING,
    IS_BOX_SIZING
  }

}
