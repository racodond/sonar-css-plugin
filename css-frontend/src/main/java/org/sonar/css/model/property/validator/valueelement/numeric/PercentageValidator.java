/*
 * SonarQube CSS / Less Plugin
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
package org.sonar.css.model.property.validator.valueelement.numeric;

import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.plugins.css.api.tree.css.PercentageTree;
import org.sonar.plugins.css.api.tree.Tree;

public class PercentageValidator implements ValueElementValidator {

  private final boolean positiveOnly;

  public PercentageValidator(boolean positiveOnly) {
    this.positiveOnly = positiveOnly;
  }

  @Override
  public boolean isValid(Tree tree) {
    if (tree instanceof PercentageTree) {
      if (positiveOnly) {
        return ((PercentageTree) tree).value().isPositive();
      }
      return true;
    }
    return false;
  }

  @Override
  public String getValidatorFormat() {
    return positiveOnly ? "<percentage>(>=0)" : "<percentage>";
  }

}
