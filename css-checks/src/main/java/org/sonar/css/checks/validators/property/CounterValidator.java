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
package org.sonar.css.checks.validators.property;

import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.css.checks.utils.CssValue;
import org.sonar.css.checks.utils.CssValueElement;
import org.sonar.css.checks.utils.valueelements.IdentifierValueElement;
import org.sonar.css.checks.validators.ValidatorFactory;
import org.sonar.css.checks.validators.ValueValidator;

public class CounterValidator implements ValueValidator {

  @Override
  public boolean isValid(@Nonnull CssValue value) {
    List<CssValueElement> valueElements = value.getValueElements();
    for (int i = 0; i < valueElements.size(); i++) {
      if (!(valueElements.get(i) instanceof IdentifierValueElement) && !(ValidatorFactory.getIntegerValidator().isValid(valueElements.get(i)))) {
        return false;
      }
      if (i == 0 && ValidatorFactory.getIntegerValidator().isValid(valueElements.get(i))) {
        return false;
      }
      if (i > 0) {
        if (ValidatorFactory.getNoneValidator().isValid(valueElements.get(i))) {
          return false;
        }
        if (ValidatorFactory.getIntegerValidator().isValid(valueElements.get(i)) &&
          (!(valueElements.get(i - 1) instanceof IdentifierValueElement) || ValidatorFactory.getNoneValidator().isValid(valueElements.get(i - 1)))) {
          return false;
        }
      }
    }
    return true;
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "[ <identifier> <integer>? ]+ | none";
  }

}
