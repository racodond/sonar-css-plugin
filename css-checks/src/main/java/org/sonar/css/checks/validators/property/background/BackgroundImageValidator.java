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

import org.sonar.css.checks.utils.CssValue;
import org.sonar.css.checks.utils.CssValueElement;
import org.sonar.css.checks.validators.PropertyValueElementValidator;
import org.sonar.css.checks.validators.PropertyValueValidator;
import org.sonar.css.checks.validators.valueelement.DelimiterValidator;
import org.sonar.css.checks.validators.valueelement.ImageValidator;
import org.sonar.css.checks.validators.valueelement.NoneValidator;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

public class BackgroundImageValidator implements PropertyValueValidator {

  PropertyValueElementValidator noneValidator = new NoneValidator();
  PropertyValueElementValidator imageValidator = new ImageValidator();
  PropertyValueElementValidator delimiterValidator = new DelimiterValidator(",");

  @Override
  public boolean isValid(@Nonnull CssValue value) {
    List<CssValueElement> valueElements = value.getValueElements();
    int numberOfElements = value.getNumberOfValueElements();
    if (numberOfElements == 0) {
      return false;
    }
    for (int i = 0; i < valueElements.size(); i++) {
      if (noneValidator.isValid(valueElements.get(i)) && i != 0) {
        return false;
      }
      else if (!imageValidator.isValid(valueElements.get(i)) && !noneValidator.isValid(valueElements.get(i)) && !delimiterValidator.isValid(valueElements.get(i))) {
        return false;
      }
    }
    return checkRepeatStyleList(buildBackgroundImageList(value));
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "none | <image> [, <image>]*";
  }

  private List<List<CssValueElement>> buildBackgroundImageList(CssValue cssValue) {
    List<List<CssValueElement>> backgroundImageList = new ArrayList();
    backgroundImageList.add(new ArrayList<CssValueElement>());
    int listIndex = 0;
    for (CssValueElement valueElement : cssValue.getValueElements()) {
      if (delimiterValidator.isValid(valueElement)) {
        backgroundImageList.add(new ArrayList<CssValueElement>());
        listIndex++;
      } else {
        backgroundImageList.get(listIndex).add(valueElement);
      }

    }
    return backgroundImageList;
  }

  private boolean checkRepeatStyleList(List<List<CssValueElement>> repeatStyleList) {
    for (List<CssValueElement> elementList : repeatStyleList) {
      if (elementList.size() != 1) {
        return false;
      }
    }
    return true;
  }

}
