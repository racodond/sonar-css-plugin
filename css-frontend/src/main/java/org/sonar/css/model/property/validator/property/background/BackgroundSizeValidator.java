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

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.css.model.Value;
import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.css.model.value.CssValueElement;
import org.sonar.css.model.value.valueelement.DelimiterValueElement;

public class BackgroundSizeValidator implements ValueValidator {

  private static final ValueElementValidator COVER_CONTAIN_VALIDATOR = new IdentifierValidator("cover", "contain");

  @Override
  public boolean isValid(Value value) {
    List<CssValueElement> valueElements = value.getValueElements();
    for (CssValueElement valueElement : valueElements) {
      if (valueElement instanceof DelimiterValueElement) {
        if (!",".equals(((DelimiterValueElement) valueElement).getType())) {
          return false;
        }
      } else if (!COVER_CONTAIN_VALIDATOR.isValid(valueElement)
        && !ValidatorFactory.getAutoValidator().isValid(valueElement)
        && !ValidatorFactory.getPositiveLengthValidator().isValid(valueElement)
        && !ValidatorFactory.getPositivePercentageValidator().isValid(valueElement)) {
        return false;
      }
    }
    return checkRepeatStyleList(buildRepeatStyleList(value));
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "[ <length> | <percentage> | auto ]{1,2} | cover | contain [,  [ <length> | <percentage> | auto ]{1,2} | cover | contain ]*";
  }

  private List<List<CssValueElement>> buildRepeatStyleList(Value value) {
    List<List<CssValueElement>> repeatStyleList = new ArrayList<>();
    repeatStyleList.add(new ArrayList<>());
    int listIndex = 0;
    for (CssValueElement valueElement : value.getValueElements()) {
      if (valueElement instanceof DelimiterValueElement) {
        repeatStyleList.add(new ArrayList<>());
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
        || (elementList.size() == 2 && (COVER_CONTAIN_VALIDATOR.isValid(elementList.get(0)) || COVER_CONTAIN_VALIDATOR.isValid(elementList.get(1))))) {
        return false;
      }
    }
    return true;
  }

}
