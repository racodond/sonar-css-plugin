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
package org.sonar.css.model.property.validator.property.liststyle;

import java.util.List;

import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

public class ListStyleValidator implements ValueValidator {

  private static final ValueElementValidator LIST_STYLE_TYPE_VALIDATOR = new ListStyleTypeValidator();
  private static final ValueElementValidator LIST_STYLE_POSITION_VALIDATOR = new ListStylePositionValidator();
  private static final ValueElementValidator LIST_STYLE_IMAGE_VALIDATOR = new ListStyleImageValidator();

  @Override
  public boolean isValid(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    int numberOfValueElements = valueElements.size();

    if (numberOfValueElements > 3) {
      return false;
    }
    for (Tree valueElement : valueElements) {
      if (!LIST_STYLE_TYPE_VALIDATOR.isValid(valueElement)
        && !LIST_STYLE_POSITION_VALIDATOR.isValid(valueElement)
        && !LIST_STYLE_IMAGE_VALIDATOR.isValid(valueElement)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public String getValidatorFormat() {
    return "<list-style-type> || <list-style-position> || <list-style-image>";
  }

}
