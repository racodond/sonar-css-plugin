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
package org.sonar.css.model.property.validator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

import org.sonar.css.model.Value;
import org.sonar.css.model.value.CssValueElement;
import org.sonar.css.model.value.valueelement.DelimiterValueElement;

public class ValueElementListValidator implements ValueValidator {

  private final List<ValueElementValidator> validators;

  public ValueElementListValidator(ValueElementValidator... validators) {
    this.validators = Arrays.asList(validators);
  }

  @Override
  public boolean isValid(Value value) {
    boolean valid;
    for (CssValueElement valueElement : value.getValueElements()) {
      valid = false;
      if (!(valueElement instanceof DelimiterValueElement)) {
        for (ValueElementValidator validator : validators) {
          if (validator.isValid(valueElement)) {
            valid = true;
            break;
          }
        }
        if (!valid) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  @Nonnull
  public String getValidatorFormat() {
    String joinedValidatorsFormat = validators.stream()
      .map(Validator::getValidatorFormat)
      .collect(Collectors.joining(" | "));

    return joinedValidatorsFormat + " [, " + joinedValidatorsFormat + "]*";
  }

}
