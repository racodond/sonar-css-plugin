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
package org.sonar.css.model.property;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.css.model.StandardCssObject;
import org.sonar.css.model.property.validator.Validator;

public class StandardProperty extends StandardCssObject {

  private final List<Validator> validators;

  public StandardProperty() {
    validators = new ArrayList<>();
  }

  public void addValidators(Validator... allValidators) {
    validators.addAll(Lists.newArrayList(allValidators));
  }

  @Nonnull
  @VisibleForTesting
  public List<Validator> getValidators() {
    return validators;
  }

  @Nonnull
  public String getValidatorFormat() {
    StringBuilder format = new StringBuilder("");
    for (Validator validator : validators) {
      if (format.length() != 0) {
        format.append(" | ");
      }
      format.append(validator.getValidatorFormat());
    }
    return format.toString();
  }

  public boolean hasValidators() {
    return !validators.isEmpty();
  }

}
