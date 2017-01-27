/*
 * SonarQube CSS / SCSS / Less Analyzer
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
package org.sonar.css.checks.common;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.CheckUtils;
import org.sonar.css.checks.Tags;
import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Rule(
  key = "box-model",
  name = "Box model size should be carefully reviewed",
  priority = Priority.MAJOR,
  tags = {Tags.PITFALL})
@SqaleConstantRemediation("1h")
@ActivatedByDefault
public class BewareOfBoxModelCheck extends DoubleDispatchVisitorCheck {

  private static final List<String> WIDTH_SIZING = ImmutableList.of(
    "border", "border-left", "border-right", "border-width", "border-left-width", "border-right-width", "padding", "padding-left", "padding-right");

  private static final List<String> HEIGHT_SIZING = ImmutableList.of(
    "border", "border-top", "border-bottom", "border-width", "border-top-width", "border-bottom-width", "padding", "padding-top", "padding-bottom");

  private static final List<String> PADDING_WIDTH = ImmutableList.of(
    "padding", "padding-top", "padding-bottom", "padding-right", "padding-left");

  private static final List<String> BORDER_WIDTH = ImmutableList.of(
    "border", "border-left", "border-right", "border-top", "border-bottom", "border-top-width", "border-bottom-width", "border-left-width", "border-right-width");

  @Override
  public void visitRuleset(RulesetTree tree) {
    Set<Combinations> combinations = EnumSet.noneOf(Combinations.class);

    for (PropertyDeclarationTree propertyDeclarationTree : tree.block().propertyDeclarations()) {
      PropertyTree propertyTree = propertyDeclarationTree.property();
      if (isBoxSizing(propertyTree)) {
        combinations.clear();
        combinations.add(Combinations.IS_BOX_SIZING);
      }
      if (!combinations.contains(Combinations.IS_BOX_SIZING)) {
        if (!combinations.contains(Combinations.WIDTH_FOUND) && isWidth(propertyTree)) {
          combinations.add(Combinations.WIDTH_FOUND);
        } else if (!combinations.contains(Combinations.HEIGHT_FOUND) && isHeight(propertyTree)) {
          combinations.add(Combinations.HEIGHT_FOUND);
        }
        if (isWidthSizing(propertyDeclarationTree)) {
          combinations.add(Combinations.WIDTH_SIZING);
        }
        if (isHeightSizing(propertyDeclarationTree)) {
          combinations.add(Combinations.HEIGHT_SIZING);
        }
      }
    }

    if (combinations.containsAll(Arrays.asList(Combinations.WIDTH_FOUND, Combinations.WIDTH_SIZING))
      || combinations.containsAll(Arrays.asList(Combinations.HEIGHT_FOUND, Combinations.HEIGHT_SIZING))) {
      addPreciseIssue(CheckUtils.rulesetIssueLocation(tree), "Check this potential box model size issue.");
    }

    super.visitRuleset(tree);
  }

  private boolean isWidthSizing(PropertyDeclarationTree tree) {
    return isOtherUsed(WIDTH_SIZING, tree);
  }

  private boolean isHeightSizing(PropertyDeclarationTree tree) {
    return isOtherUsed(HEIGHT_SIZING, tree);
  }

  private boolean isOtherUsed(List<String> props, PropertyDeclarationTree tree) {
    String propertyName = tree.property().standardProperty().getName();
    return props.contains(propertyName) && !isZeroValue(propertyName, tree.value());
  }

  private boolean isZeroValue(String property, ValueTree valueTree) {
    return PADDING_WIDTH.contains(property) && isZeroValuePaddingWidth(valueTree)
      || BORDER_WIDTH.contains(property) && isZeroValueBorderWidth(valueTree);
  }

  private boolean isZeroValueBorderWidth(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    for (Tree valueElement : valueElements) {
      if (ValidatorFactory.getBorderWidthValidator().isValid(valueElement)) {
        if (valueElement instanceof DimensionTree && ((DimensionTree) valueElement).value().isNotZero()
          || valueElement instanceof PercentageTree && ((PercentageTree) valueElement).value().isNotZero()
          || valueElement instanceof NumberTree && !((NumberTree) valueElement).isZero()) {
          return false;
        } else if (!(valueElement instanceof DimensionTree)
          && !(valueElement instanceof PercentageTree)
          && !(valueElement instanceof NumberTree)) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean isZeroValuePaddingWidth(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    for (Tree valueElement : valueElements) {
      if (ValidatorFactory.getPaddingWidthValidator().isValid(valueElement)) {
        if (valueElement instanceof DimensionTree && ((DimensionTree) valueElement).value().isNotZero()
          || valueElement instanceof PercentageTree && ((PercentageTree) valueElement).value().isNotZero()
          || valueElement instanceof NumberTree && !((NumberTree) valueElement).isZero()) {
          return false;
        } else if (!(valueElement instanceof DimensionTree)
          && !(valueElement instanceof PercentageTree)
          && !(valueElement instanceof NumberTree)) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean isBoxSizing(PropertyTree tree) {
    return "box-sizing".equalsIgnoreCase(tree.property().text());
  }

  private boolean isWidth(PropertyTree tree) {
    return Combinations.WIDTH_FOUND.equals(isWidthOrHeight(tree));
  }

  private boolean isHeight(PropertyTree tree) {
    return Combinations.HEIGHT_FOUND.equals(isWidthOrHeight(tree));
  }

  private Combinations isWidthOrHeight(PropertyTree tree) {
    String propertyName = tree.property().text();
    if ("height".equalsIgnoreCase(propertyName)) {
      return Combinations.HEIGHT_FOUND;
    } else if ("width".equalsIgnoreCase(propertyName)) {
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
