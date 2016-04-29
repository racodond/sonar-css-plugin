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

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.css.checks.utils.CssValue;
import org.sonar.css.checks.utils.CssValueElement;
import org.sonar.css.checks.utils.valueelements.IdentifierValueElement;
import org.sonar.css.checks.validators.ValidatorFactory;
import org.sonar.css.checks.validators.ValueElementValidator;
import org.sonar.css.checks.validators.ValueValidator;
import org.sonar.css.checks.validators.valueelement.IdentifierValidator;

public class TextDecorationLineValidator implements ValueValidator {

  private final ValueElementValidator identifierValidator = new IdentifierValidator(
    ImmutableList.of("underline", "overline", "line-through", "blink"));

  @Override
  public boolean isValid(@Nonnull CssValue value) {
    List<CssValueElement> valueElements = value.getValueElements();
    if (value.getNumberOfValueElements() > 4) {
      return false;
    }
    List<String> listTextDecorationLine = new ArrayList<>();
    for (int i = 0; i < valueElements.size(); i++) {
      if (i == 0 && (ValidatorFactory.getNoneValidator().isValid(valueElements.get(i)) && valueElements.size() == 1 || identifierValidator.isValid(valueElements.get(i)))) {
        listTextDecorationLine.add(((IdentifierValueElement) valueElements.get(i)).getName());
        continue;
      }
      if (i != 0 && identifierValidator.isValid(valueElements.get(i)) && !listTextDecorationLine.contains(((IdentifierValueElement) valueElements.get(i)).getName())) {
        listTextDecorationLine.add(((IdentifierValueElement) valueElements.get(i)).getName());
        continue;
      }
      return false;
    }
    return true;
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "none | [ underline || overline || line-through || blink ]";
  }

}
