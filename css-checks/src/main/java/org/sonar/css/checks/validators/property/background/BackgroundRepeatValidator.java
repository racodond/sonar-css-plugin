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
package org.sonar.css.checks.validators.property.background;

import com.google.common.collect.ImmutableList;
import org.sonar.css.checks.utils.CssValue;
import org.sonar.css.checks.utils.CssValueElement;
import org.sonar.css.checks.utils.valueelements.Delimiter;
import org.sonar.css.checks.utils.valueelements.Identifier;
import org.sonar.css.checks.validators.PropertyValueValidator;
import org.sonar.css.checks.validators.valueelement.IdentifierValidator;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

public class BackgroundRepeatValidator implements PropertyValueValidator {

  IdentifierValidator repeatXYValidator = new IdentifierValidator(ImmutableList.of("repeat-x", "repeat-y"));
  IdentifierValidator repeatOthersValidator = new IdentifierValidator(ImmutableList.of("repeat", "space", "round", "no-repeat"));

  @Override
  public boolean isValid(@Nonnull CssValue value) {
    List<CssValueElement> valueElements = value.getValueElements();
    int numberOfElements = value.getNumberOfValueElements();
    if (numberOfElements == 0) {
      return false;
    }

    for (CssValueElement valueElement : valueElements) {
      if (!(valueElement instanceof Delimiter) && !(valueElement instanceof Identifier)) {
        return false;
      }
      if (valueElement instanceof Delimiter && !",".equals(((Delimiter) valueElement).getType())) {
        return false;
      }
      if (valueElement instanceof Identifier && !(repeatXYValidator.isValid(valueElement) || repeatOthersValidator.isValid(valueElement))) {
        return false;
      }
    }
    return checkRepeatStyleList(buildRepeatStyleList(value));
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "repeat-x | repeat-y | [repeat | space | round | no-repeat]{1,2} [, repeat-x | repeat-y | [repeat | space | round | no-repeat]{1,2}]*";
  }

  private List<List<CssValueElement>> buildRepeatStyleList(CssValue cssValue) {
    List<List<CssValueElement>> repeatStyleList = new ArrayList();
    repeatStyleList.add(new ArrayList<CssValueElement>());
    int listIndex = 0;
    for (CssValueElement valueElement : cssValue.getValueElements()) {
      if (valueElement instanceof Identifier) {
        repeatStyleList.get(listIndex).add(valueElement);
      }
      if (valueElement instanceof Delimiter) {
        repeatStyleList.add(new ArrayList<CssValueElement>());
        listIndex++;
      }
    }
    return repeatStyleList;
  }

  private boolean checkRepeatStyleList(List<List<CssValueElement>> repeatStyleList) {
    for (List<CssValueElement> elementList : repeatStyleList) {
      if (elementList.size() == 0
        || (elementList.size() == 2 && (repeatXYValidator.isValid(elementList.get(0)) || repeatXYValidator.isValid(elementList.get(1))))) {
        return false;
      }
    }
    return true;
  }

}
