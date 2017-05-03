/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2017 David RACODON
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

import org.sonar.css.model.function.standard.*;
import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.function.FunctionValidator;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

public class FilterValidator implements ValueValidator {

  private static final ValueElementValidator FUNCTION_VALIDATOR = new FunctionValidator(
    Blur.class, Brightness.class, Contrast.class, DropShadow.class, Grayscale.class, HueRotate.class, Invert.class,
    Opacity.class, Saturate.class, Sepia.class, org.sonar.css.model.function.standard.Alpha.class, Basicimage.class, Blendtrans.class, Chroma.class,
    Compositor.class, DropShadow.class, Emboss.class, Engrave.class, Fliph.class, Flipv.class, Glow.class,
    Icmfilter.class, Light.class, MaskFilter.class, Matrix.class, Motionblur.class, Redirect.class, Revealtrans.class,
    Shadow.class, Wave.class, Xray.class);

  @Override
  public boolean isValid(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    int numberOfValueElements = valueElements.size();

    if (ValidatorFactory.getNoneValidator().isValid(valueElements.get(0)) && numberOfValueElements == 1) {
      return true;
    }
    for (Tree valueElement : valueElements) {
      if (!FUNCTION_VALIDATOR.isValid(valueElement)
        && !ValidatorFactory.getUriValidator().isValid(valueElement)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String getValidatorFormat() {
    return "none | [<uri> | <filter-function>]+";
  }

}
