/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2017 David RACODON
 * mailto: david.racodon@gmail.com
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
package org.sonar.css.model.property.validator.property.background;

import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

import java.util.List;

public class BackgroundPositionValidator implements ValueValidator {

  @Override
  public boolean isValid(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    int numberOfValueElements = valueElements.size();

    if (numberOfValueElements == 1) {
      return validateOneElement(valueElements.get(0));
    } else if (numberOfValueElements == 2) {
      return validateTwoElements(valueElements);
    } else if (numberOfValueElements == 3 || numberOfValueElements == 4) {
      return validateThreeOrFourElements(valueElements);
    } else {
      return false;
    }
  }

  @Override
  public String getValidatorFormat() {
    return "[ [ left | center | right | top | bottom | <length> | <percentage> ] | [ left | center | right | <length> | <percentage> ] [ top | center | bottom | <length> | <percentage> ] | [ center | [ left | right ] [ <length> | <percentage> ]? ] && [ center | [ top | bottom ] [ <length> | <percentage>]? ] ]";
  }

  private boolean validateOneElement(Tree valueElement) {
    return isLengthOrPercentage(valueElement)
      || new IdentifierValidator("left", "right", "center", "top", "bottom").isValid(valueElement);
  }

  private boolean validateTwoElements(List<Tree> valueElements) {
    return (isLengthOrPercentage(valueElements.get(0))
      || new IdentifierValidator("left", "center", "right").isValid(valueElements.get(0)))
      && (isLengthOrPercentage(valueElements.get(1))
      || new IdentifierValidator("top", "center", "bottom").isValid(valueElements.get(1)));
  }

  private boolean validateThreeOrFourElements(List<Tree> valueElements) {

    IdentifierValidator centerValidator = new IdentifierValidator("center");
    IdentifierValidator leftRightValidator = new IdentifierValidator("right", "left");
    IdentifierValidator topBottomValidator = new IdentifierValidator("top", "bottom");

    int countCenter = 0;
    int countLeftRight = 0;
    int countTopBottom = 0;
    int countLengthPercentage = 0;

    for (Tree valueElement : valueElements) {
      if (centerValidator.isValid(valueElement)) countCenter++;
      if (leftRightValidator.isValid(valueElement)) countLeftRight++;
      if (topBottomValidator.isValid(valueElement)) countTopBottom++;
      if (isLengthOrPercentage(valueElement)) countLengthPercentage++;
    }

    if (countCenter > 1 || countLeftRight > 1 || countTopBottom > 1 || countLengthPercentage > 2) {
      return false;
    }

    for (int i = 1; i < valueElements.size(); i++) {
      if (isLengthOrPercentage(valueElements.get(i))
        && (isLengthOrPercentage(valueElements.get(i - 1)) || centerValidator.isValid(valueElements.get(i - 1)))) {
        return false;
      }
    }

    if (isLengthOrPercentage(valueElements.get(0))) {
      return false;
    }

    return true;
  }

  private boolean isLengthOrPercentage(Tree valueElement) {
    return ValidatorFactory.getPercentageValidator().isValid(valueElement)
      || ValidatorFactory.getLengthValidator().isValid(valueElement);
  }

}
