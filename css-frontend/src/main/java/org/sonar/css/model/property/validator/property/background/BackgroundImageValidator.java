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
package org.sonar.css.model.property.validator.property.background;

import org.sonar.css.model.property.validator.HashMultiplierValidator;
import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

public class BackgroundImageValidator implements ValueValidator {

  @Override
  public boolean isValid(ValueTree tree) {

    if (new HashMultiplierValidator(ValidatorFactory.getImageValidator()).isValid(tree)) {
      return true;
    }

    if (tree.sanitizedValueElements().size() == 1) {
      Tree value = tree.sanitizedValueElements().get(0);
      if (!ValidatorFactory.getNoneValidator().isValid(value)
        && !ValidatorFactory.getImageValidator().isValid(value)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public String getValidatorFormat() {
    return "none | <image>#";
  }

}
