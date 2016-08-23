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

import java.util.List;

import org.sonar.css.model.function.standard.*;
import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.function.FunctionValidator;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.ValueTree;

public class TransformValidator implements ValueValidator {

  private static final ValueElementValidator FUNCTION_VALIDATOR = new FunctionValidator(
    Matrix.class, Translate.class, Translatex.class, Translatey.class, Scale.class, Scalex.class, Scaley.class,
    Rotate.class, Skew.class, Skewx.class, Skewy.class, Matrix3d.class, Translate3d.class, Translatez.class,
    Scale3d.class, Scalez.class, Rotate3d.class, Rotatex.class, Rotatey.class, Rotatez.class, Perspective.class);

  @Override
  public boolean isValid(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    int numberOfValueElements = valueElements.size();

    if (ValidatorFactory.getNoneValidator().isValid(valueElements.get(0)) && numberOfValueElements == 1) {
      return true;
    }
    for (Tree valueElement : valueElements) {
      if (!FUNCTION_VALIDATOR.isValid(valueElement)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String getValidatorFormat() {
    return "none | <transform-function>+";
  }

}
