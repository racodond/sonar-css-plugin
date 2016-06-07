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

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.css.model.Value;
import org.sonar.css.model.value.CssValueElement;
import org.sonar.css.model.value.valueelement.DelimiterValueElement;

public class ValueElementListValidator implements ValueValidator {

  private List<? extends ValueElementValidator> validators = new ArrayList<>();

  public ValueElementListValidator(@Nonnull ImmutableList<? extends ValueElementValidator> validators) {
    this.validators = validators;
  }

  @Override
  public boolean isValid(@Nonnull Value value) {
    boolean valid;
    for (CssValueElement valueElement : value.getValueElements()) {
      valid = false;
      if (!(valueElement instanceof DelimiterValueElement)) {
        for (ValueElementValidator validator : validators) {
          if (validator.isValid(valueElement)) {
            valid = true;
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
    StringBuilder format = new StringBuilder();
    for (Validator validator : validators) {
      if (format.length() > 0) {
        format.append(" | ");
      }
      format.append(validator.getValidatorFormat());
    }

    format.append(" [, ");
    int length = format.length();

    for (Validator validator : validators) {
      if (format.length() > length) {
        format.append(" | ");
      }
      format.append(validator.getValidatorFormat());
    }

    format.append("]*");
    return format.toString();
  }

}
