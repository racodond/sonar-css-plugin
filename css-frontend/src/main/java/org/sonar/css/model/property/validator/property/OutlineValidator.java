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

import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.OutlineColorValidator;
import org.sonar.css.model.property.validator.valueelement.OutlineStyleValidator;
import org.sonar.css.model.property.validator.valueelement.OutlineWidthValidator;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

public class OutlineValidator implements ValueValidator {

  private static final OutlineColorValidator OUTLINE_COLOR_VALIDATOR = new OutlineColorValidator();
  private static final OutlineWidthValidator OUTLINE_WIDTH_VALIDATOR = new OutlineWidthValidator();
  private static final OutlineStyleValidator outlineStyleValidator = new OutlineStyleValidator();

  @Override
  public boolean isValid(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    int numberOfValueElements = valueElements.size();

    if (numberOfValueElements > 3) {
      return false;
    }
    for (Tree valueElement : valueElements) {
      if (!OUTLINE_COLOR_VALIDATOR.isValid(valueElement)
        && !OUTLINE_WIDTH_VALIDATOR.isValid(valueElement)
        && !outlineStyleValidator.isValid(valueElement)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String getValidatorFormat() {
    return "<outline-width> || <outline-style> || <outline-color>";
  }

}
