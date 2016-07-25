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

import org.sonar.css.model.Color;
import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.valueelement.function.FunctionValidator;
import org.sonar.plugins.css.api.tree.Tree;

public class ColorValidator implements ValueElementValidator {

  @Override
  public boolean isValid(Tree valueElement) {
    return new IdentifierValidator(Color.SVG_COLORS).isValid(valueElement)
      || new IdentifierValidator(Color.CSS4_COLORS).isValid(valueElement)
      || new IdentifierValidator(Color.CSS2_SYSTEM_COLORS).isValid(valueElement)
      || new IdentifierValidator("transparent", "currentcolor").isValid(valueElement)
      || new FunctionValidator("rgb", "rgba", "hsl", "hsla").isValid(valueElement)
      || new HashValidator().isValid(valueElement);
  }

  @Override
  public String getValidatorFormat() {
    return "<color>";
  }

}
