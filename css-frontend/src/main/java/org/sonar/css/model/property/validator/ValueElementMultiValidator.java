/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
 * mailto: david.racodon@gmail.com
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

import org.sonar.plugins.css.api.tree.Tree;

public class ValueElementMultiValidator implements ValueElementValidator {

  private final List<ValueElementValidator> validators;

  public ValueElementMultiValidator(ValueElementValidator... validators) {
    this.validators = Arrays.asList(validators);
  }

  @Override
  public boolean isValid(Tree tree) {
    for (ValueElementValidator validator : validators) {
      if (validator.isValid(tree)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String getValidatorFormat() {
    return validators.stream()
      .map(Validator::getValidatorFormat)
      .collect(Collectors.joining(" | "));
  }

}
