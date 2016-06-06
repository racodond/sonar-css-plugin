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
package org.sonar.css.model.property.validator.property;

import com.google.common.collect.ImmutableList;

import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.css.model.Value;
import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.css.model.value.CssValueElement;

public class TextIndentValidator implements ValueValidator {

  IdentifierValidator hangingValidator = new IdentifierValidator(ImmutableList.of("hanging"));
  IdentifierValidator eachLineValidator = new IdentifierValidator(ImmutableList.of("each-line"));

  @Override
  public boolean isValid(@Nonnull Value value) {
    List<CssValueElement> valueElements = value.getValueElements();
    if (value.getNumberOfValueElements() > 3) {
      return false;
    }
    if (value.getNumberOfValueElements() == 1) {
      return ValidatorFactory.getLengthValidator().isValid(valueElements.get(0))
        || ValidatorFactory.getPercentageValidator().isValid(valueElements.get(0));
    }
    if (value.getNumberOfValueElements() > 1) {
      return validateMultiElementValue(valueElements);
    }
    return false;
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "[ <length> | <percentage> ] && hanging? && each-line?";
  }

  private boolean validateMultiElementValue(List<CssValueElement> valueElements) {
    int hanging = 0;
    int eachLine = 0;
    int lengthPercentage = 0;
    boolean isValid = true;

    for (CssValueElement valueElement : valueElements) {
      if (ValidatorFactory.getLengthValidator().isValid(valueElement) || ValidatorFactory.getPercentageValidator().isValid(valueElement)) {
        lengthPercentage++;
      } else if (hangingValidator.isValid(valueElement)) {
        hanging++;
      } else if (eachLineValidator.isValid(valueElement)) {
        eachLine++;
      }
    }

    if (lengthPercentage != 1) {
      isValid = false;
    } else if (valueElements.size() == 2) {
      isValid = hanging + eachLine == 1;
    } else if (valueElements.size() == 3) {
      isValid = hanging == 1 && eachLine == 1;
    }
    return isValid;
  }

}
