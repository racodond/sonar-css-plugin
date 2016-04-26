/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende and David RACODON
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

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.css.checks.utils.CssValue;
import org.sonar.css.checks.utils.CssValueElement;
import org.sonar.css.checks.utils.valueelements.DelimiterValueElement;
import org.sonar.css.checks.validators.ValidatorFactory;
import org.sonar.css.checks.validators.ValueElementValidator;
import org.sonar.css.checks.validators.ValueValidator;
import org.sonar.css.checks.validators.valueelement.IdentifierValidator;

public class BackgroundSizeValidator implements ValueValidator {

  ValueElementValidator coverContainValidator = new IdentifierValidator(ImmutableList.of("cover", "contain"));

  @Override
  public boolean isValid(@Nonnull CssValue value) {
    List<CssValueElement> valueElements = value.getValueElements();
    for (CssValueElement valueElement : valueElements) {
      if (valueElement instanceof DelimiterValueElement) {
        if (!",".equals(((DelimiterValueElement) valueElement).getType())) {
          return false;
        }
      } else if (!coverContainValidator.isValid(valueElement)
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

  private List<List<CssValueElement>> buildRepeatStyleList(CssValue cssValue) {
    List<List<CssValueElement>> repeatStyleList = new ArrayList();
    repeatStyleList.add(new ArrayList<CssValueElement>());
    int listIndex = 0;
    for (CssValueElement valueElement : cssValue.getValueElements()) {
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
      if (elementList.size() == 0
        || (elementList.size() == 2 && (coverContainValidator.isValid(elementList.get(0)) || coverContainValidator.isValid(elementList.get(1))))) {
        return false;
      }
    }
    return true;
  }

}
