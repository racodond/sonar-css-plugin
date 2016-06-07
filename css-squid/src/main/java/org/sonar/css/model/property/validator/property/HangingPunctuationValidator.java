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
package org.sonar.css.model.property.validator.property;

import com.google.common.collect.ImmutableList;

import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.css.model.Value;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.css.model.value.CssValueElement;

public class HangingPunctuationValidator implements ValueValidator {

  private final IdentifierValidator singleElementValidator = new IdentifierValidator(ImmutableList.of("none", "first", "force-end", "allow-end", "last"));
  private final IdentifierValidator firstValidator = new IdentifierValidator(ImmutableList.of("first"));
  private final IdentifierValidator lastValidator = new IdentifierValidator(ImmutableList.of("last"));
  private final IdentifierValidator forceEndAllowEndValidator = new IdentifierValidator(ImmutableList.of("force-end", "allow-end"));

  @Override
  public boolean isValid(@Nonnull Value value) {
    List<CssValueElement> valueElements = value.getValueElements();
    if (value.getNumberOfValueElements() > 3) {
      return false;
    }
    if (value.getNumberOfValueElements() == 1) {
      return singleElementValidator.isValid(valueElements.get(0));
    }
    if (value.getNumberOfValueElements() > 1) {
      return validateMultiElementValue(valueElements);
    }
    return false;
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "none | [ first || [ force-end | allow-end ] || last ]";
  }

  private boolean validateMultiElementValue(List<CssValueElement> valueElements) {
    int first = 0;
    int forceEndAllowEnd = 0;
    int last = 0;

    for (CssValueElement valueElement : valueElements) {
      if (firstValidator.isValid(valueElement)) {
        first++;
      } else if (lastValidator.isValid(valueElement)) {
        last++;
      } else if (forceEndAllowEndValidator.isValid(valueElement)) {
        forceEndAllowEnd++;
      }
    }

    if (first > 1 || last > 1 || forceEndAllowEnd > 1) {
      return false;
    } else {
      return first + forceEndAllowEnd + last == valueElements.size();
    }
  }

}
