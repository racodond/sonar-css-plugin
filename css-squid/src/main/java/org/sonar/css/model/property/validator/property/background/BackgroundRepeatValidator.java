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
package org.sonar.css.model.property.validator.property.background;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.css.model.Value;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.css.model.value.CssValueElement;
import org.sonar.css.model.value.valueelement.DelimiterValueElement;

public class BackgroundRepeatValidator implements ValueValidator {

  private final IdentifierValidator repeatXYValidator = new IdentifierValidator(ImmutableList.of("repeat-x", "repeat-y"));
  private final IdentifierValidator repeatOthersValidator = new IdentifierValidator(ImmutableList.of("repeat", "space", "round", "no-repeat"));

  @Override
  public boolean isValid(@Nonnull Value value) {
    List<CssValueElement> valueElements = value.getValueElements();
    for (CssValueElement valueElement : valueElements) {
      if (valueElement instanceof DelimiterValueElement) {
        if (!",".equals(((DelimiterValueElement) valueElement).getType())) {
          return false;
        }
      } else if (!repeatXYValidator.isValid(valueElement) && !repeatOthersValidator.isValid(valueElement)) {
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

  private List<List<CssValueElement>> buildRepeatStyleList(Value value) {
    List<List<CssValueElement>> repeatStyleList = new ArrayList<>();
    repeatStyleList.add(new ArrayList<CssValueElement>());
    int listIndex = 0;
    for (CssValueElement valueElement : value.getValueElements()) {
      if (valueElement instanceof DelimiterValueElement) {
        repeatStyleList.add(new ArrayList<CssValueElement>());
        listIndex++;
      } else {
        repeatStyleList.get(listIndex).add(valueElement);
      }

    }
    return repeatStyleList;
  }

  private boolean checkRepeatStyleList(List<List<CssValueElement>> repeatStyleList) {
    for (List<CssValueElement> elementList : repeatStyleList) {
      if (elementList.isEmpty()
        || (elementList.size() == 2 && (repeatXYValidator.isValid(elementList.get(0)) || repeatXYValidator.isValid(elementList.get(1))))) {
        return false;
      }
    }
    return true;
  }

}
