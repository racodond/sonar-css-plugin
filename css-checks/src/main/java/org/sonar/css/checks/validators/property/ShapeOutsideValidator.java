/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende
 * kende.tamas@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.css.checks.validators.property;

import org.sonar.css.checks.utils.CssValue;
import org.sonar.css.checks.utils.CssValueElement;
import org.sonar.css.checks.validators.PropertyValueElementValidator;
import org.sonar.css.checks.validators.PropertyValueValidator;
import org.sonar.css.checks.validators.PropertyValueValidatorFactory;
import org.sonar.css.checks.validators.valueelement.ImageValidator;
import org.sonar.css.checks.validators.valueelement.ShapeBoxValidator;
import org.sonar.css.checks.validators.valueelement.function.BasicShapeValidator;

import javax.annotation.Nonnull;

import java.util.List;

public class ShapeOutsideValidator implements PropertyValueValidator {

  BasicShapeValidator basicShapeValidator = new BasicShapeValidator();
  ImageValidator imageValidator = new ImageValidator();
  PropertyValueElementValidator noneValidator = PropertyValueValidatorFactory.getNoneValidator();
  ShapeBoxValidator shapeBoxValidator = new ShapeBoxValidator();

  @Override
  public boolean isValid(@Nonnull CssValue value) {
    List<CssValueElement> valueElements = value.getValueElements();
    int numberOfElements = value.getNumberOfValueElements();
    if (numberOfElements == 0) {
      return false;
    }
    if (numberOfElements == 1) {
      return noneValidator.isValid(valueElements.get(0))
        || imageValidator.isValid(value)
        || shapeBoxValidator.isValid(valueElements.get(0))
        || basicShapeValidator.isValid(valueElements.get(0));
    }
    if (numberOfElements == 2) {
      return shapeBoxValidator.isValid(valueElements.get(0)) && basicShapeValidator.isValid(valueElements.get(1))
        || shapeBoxValidator.isValid(valueElements.get(1)) && basicShapeValidator.isValid(valueElements.get(0))
        || imageValidator.isValid(value);
    }
    if (numberOfElements > 2) {
      return imageValidator.isValid(value);
    }
    return false;
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "none | [<basic-shape> || <shape-box>] | <image>";
  }

}
