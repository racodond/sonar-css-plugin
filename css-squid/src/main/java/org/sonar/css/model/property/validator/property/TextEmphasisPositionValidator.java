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
import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.css.model.value.CssValueElement;

public class TextEmphasisPositionValidator implements ValueValidator {

  private static final ValueElementValidator OVER_UNDER_VALIDATOR = new IdentifierValidator(ImmutableList.of("over", "under"));
  private static final ValueElementValidator RIGHT_LEFT_VALIDATOR = new IdentifierValidator(ImmutableList.of("right", "left"));

  @Override
  public boolean isValid(Value value) {
    List<CssValueElement> valueElements = value.getValueElements();
    if (valueElements.size() != 2) {
      return false;
    } else {
      return OVER_UNDER_VALIDATOR.isValid(valueElements.get(0)) && RIGHT_LEFT_VALIDATOR.isValid(valueElements.get(1))
        || RIGHT_LEFT_VALIDATOR.isValid(valueElements.get(0)) && OVER_UNDER_VALIDATOR.isValid(valueElements.get(1));
    }
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "[ over | under ] && [ right | left ]";
  }

}
