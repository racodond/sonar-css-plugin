/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
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
package org.sonar.css.model.property.validator.property;

import java.util.List;

import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.flex.FlexDirectionValidator;
import org.sonar.css.model.property.validator.valueelement.flex.FlexWrapValidator;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

public class FlexFlowValidator implements ValueValidator {

  private static final ValueElementValidator FLEX_DIRECTION_VALIDATOR = new FlexDirectionValidator();
  private static final ValueElementValidator FLEX_WRAP_VALIDATOR = new FlexWrapValidator();

  @Override
  public boolean isValid(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    int numberOfValueElements = valueElements.size();

    if (numberOfValueElements > 2) {
      return false;
    }
    int countFlexWrap = 0;
    int countFlexDirection = 0;
    for (Tree valueElement : valueElements) {
      if (!FLEX_WRAP_VALIDATOR.isValid(valueElement)
        && !FLEX_DIRECTION_VALIDATOR.isValid(valueElement)) {
        return false;
      } else if (FLEX_WRAP_VALIDATOR.isValid(valueElement)) {
        countFlexWrap++;
      } else if (FLEX_DIRECTION_VALIDATOR.isValid(valueElement)) {
        countFlexDirection++;
      }
    }
    if (countFlexDirection > 1 || countFlexWrap > 1) {
      return false;
    }
    return true;
  }

  @Override
  public String getValidatorFormat() {
    return "<flex-direction> || <flex-wrap>";
  }

}
