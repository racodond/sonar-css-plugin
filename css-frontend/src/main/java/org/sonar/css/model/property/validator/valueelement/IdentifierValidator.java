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
package org.sonar.css.model.property.validator.valueelement;

import java.util.Arrays;
import java.util.List;

import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.plugins.css.api.tree.IdentifierTree;
import org.sonar.plugins.css.api.tree.Tree;

public class IdentifierValidator implements ValueElementValidator {

  private final List<String> allowedValues;

  public IdentifierValidator(String... allowedValues) {
    this.allowedValues = Arrays.asList(allowedValues);
  }

  public IdentifierValidator(List<String> allowedValues) {
    this.allowedValues = allowedValues;
  }

  @Override
  public boolean isValid(Tree tree) {
    if (tree instanceof IdentifierTree) {
      if (allowedValues.isEmpty()) {
        return true;
      }
      return allowedValues.contains(((IdentifierTree) tree).text().toLowerCase());
    }
    return false;
  }

  @Override
  public String getValidatorFormat() {
    if (allowedValues.isEmpty()) {
      return "<identifier>";
    } else {
      return String.join(" | ", allowedValues);
    }
  }
}
