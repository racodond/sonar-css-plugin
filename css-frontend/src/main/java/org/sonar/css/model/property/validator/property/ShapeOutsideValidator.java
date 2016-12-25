/*
 * SonarQube CSS / SCSS / Less Analyzer
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

import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.ShapeBoxValidator;
import org.sonar.css.model.property.validator.valueelement.function.BasicShapeValidator;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

public class ShapeOutsideValidator implements ValueValidator {

  private static final BasicShapeValidator BASIC_SHAPE_VALIDATOR = new BasicShapeValidator();
  private static final ShapeBoxValidator SHAPE_BOX_VALIDATOR = new ShapeBoxValidator();

  @Override
  public boolean isValid(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    int numberOfValueElements = valueElements.size();

    if (numberOfValueElements > 2) {
      return false;
    }
    if (numberOfValueElements == 1) {
      return ValidatorFactory.getNoneValidator().isValid(valueElements.get(0))
        || ValidatorFactory.getImageValidator().isValid(valueElements.get(0))
        || SHAPE_BOX_VALIDATOR.isValid(valueElements.get(0))
        || BASIC_SHAPE_VALIDATOR.isValid(valueElements.get(0));
    }
    if (numberOfValueElements == 2) {
      return SHAPE_BOX_VALIDATOR.isValid(valueElements.get(0)) && BASIC_SHAPE_VALIDATOR.isValid(valueElements.get(1))
        || SHAPE_BOX_VALIDATOR.isValid(valueElements.get(1)) && BASIC_SHAPE_VALIDATOR.isValid(valueElements.get(0));
    }
    return false;
  }

  @Override
  public String getValidatorFormat() {
    return "none | [<basic-shape> || <shape-box>] | <image>";
  }

}
