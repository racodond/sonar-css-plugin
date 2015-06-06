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
package org.sonar.css.checks.validators;

import com.google.common.collect.ImmutableList;
import org.sonar.css.checks.utils.CssValueElement;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

public class MultiPropertyValueElementValidator implements PropertyValueElementValidator {

  private List<PropertyValueElementValidator> validators = new ArrayList<>();

  public MultiPropertyValueElementValidator() {
  }

  public MultiPropertyValueElementValidator(@Nonnull ImmutableList<PropertyValueElementValidator> validators) {
    this.validators = validators;
  }

  public List<PropertyValueElementValidator> getValidators() {
    return validators;
  }

  @Override
  public boolean isValid(@Nonnull CssValueElement cssValueElement) {
    for (PropertyValueElementValidator validator : validators) {
      if (validator.isValid(cssValueElement)) {
        return true;
      }
    }
    return false;
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
    return format.toString();
  }

}
