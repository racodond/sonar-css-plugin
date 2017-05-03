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

import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueCommaSeparatedListTree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

import java.util.List;

public class BackgroundRepeatValidator implements ValueValidator {

  private static final IdentifierValidator REPEAT_XY_VALIDATOR = new IdentifierValidator("repeat-x", "repeat-y");
  private static final IdentifierValidator REPEAT_OTHERS_VALIDATOR = new IdentifierValidator("repeat", "space", "round", "no-repeat");

  @Override
  public boolean isValid(ValueTree valueTree) {

    if (valueTree.sanitizedValueElements().size() > 2) {
      return false;
    }

    List<Tree> valueElements = valueTree.sanitizedValueElements();

    if (valueElements.size() == 1) {
      if (valueElements.get(0).is(Tree.Kind.VALUE_COMMA_SEPARATED_LIST)) {
        if (!checkRepeatStyleList((ValueCommaSeparatedListTree) valueElements.get(0))) {
          return false;
        }
      } else {
        if (!validateOneValueElement(valueElements.get(0))) {
          return false;
        }
      }

    } else {
      if (!validateTwoValueElements(valueElements.get(0))
        || !validateTwoValueElements(valueElements.get(1))) {
        return false;
      }
    }

    return true;
  }

  @Override
  public String getValidatorFormat() {
    return "[ repeat-x | repeat-y | [ repeat | space | round | no-repeat ]{1,2} ]#";
  }

  private boolean checkRepeatStyleList(ValueCommaSeparatedListTree tree) {
    for (ValueTree listElement : tree.values()) {
      if (listElement.sanitizedValueElements().size() > 2) {
        return false;
      }
      if (listElement.sanitizedValueElements().size() == 1
        && !validateOneValueElement(listElement.sanitizedValueElements().get(0))) {
        return false;
      }
      if (listElement.sanitizedValueElements().size() == 2
        && (!validateTwoValueElements(listElement.sanitizedValueElements().get(0))
        || !validateTwoValueElements(listElement.sanitizedValueElements().get(1)))) {
        return false;
      }
    }
    return true;
  }

  private boolean validateOneValueElement(Tree valueElement) {
    return REPEAT_XY_VALIDATOR.isValid(valueElement) || REPEAT_OTHERS_VALIDATOR.isValid(valueElement);
  }

  private boolean validateTwoValueElements(Tree valueElement) {
    return REPEAT_OTHERS_VALIDATOR.isValid(valueElement);
  }

}
