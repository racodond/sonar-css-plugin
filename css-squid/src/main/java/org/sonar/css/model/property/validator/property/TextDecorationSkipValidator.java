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

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.css.model.Value;
import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.css.model.value.CssValueElement;
import org.sonar.css.model.value.valueelement.IdentifierValueElement;

public class TextDecorationSkipValidator implements ValueValidator {

  private static final ValueElementValidator IDENTIFIER_VALIDATOR = new IdentifierValidator("objects", "spaces", "ink", "edges", "box-decoration");

  @Override
  public boolean isValid(Value value) {
    List<CssValueElement> valueElements = value.getValueElements();
    if (value.getNumberOfValueElements() > 5) {
      return false;
    }
    List<String> listTextDecorationSkip = new ArrayList<>();
    for (int i = 0; i < valueElements.size(); i++) {
      if (i == 0 && (ValidatorFactory.getNoneValidator().isValid(valueElements.get(i)) && valueElements.size() == 1 || IDENTIFIER_VALIDATOR.isValid(valueElements.get(i)))) {
        listTextDecorationSkip.add(((IdentifierValueElement) valueElements.get(i)).getName());
        continue;
      }
      if (i != 0 && IDENTIFIER_VALIDATOR.isValid(valueElements.get(i)) && !listTextDecorationSkip.contains(((IdentifierValueElement) valueElements.get(i)).getName())) {
        listTextDecorationSkip.add(((IdentifierValueElement) valueElements.get(i)).getName());
        continue;
      }
      return false;
    }
    return true;
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "none | [ objects || spaces || ink || edges || box-decoration ]";
  }

}
