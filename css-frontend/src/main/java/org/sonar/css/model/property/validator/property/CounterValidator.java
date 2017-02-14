/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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
package org.sonar.css.model.property.validator.property;

import java.util.List;

import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

public class CounterValidator implements ValueValidator {

  @Override
  public boolean isValid(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    for (int i = 0; i < valueElements.size(); i++) {
      if (!(valueElements.get(i) instanceof IdentifierTree) && !(ValidatorFactory.getIntegerValidator().isValid(valueElements.get(i)))) {
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
          (!(valueElements.get(i - 1) instanceof IdentifierTree) || ValidatorFactory.getNoneValidator().isValid(valueElements.get(i - 1)))) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public String getValidatorFormat() {
    return "[ <identifier> <integer>? ]+ | none";
  }

}
