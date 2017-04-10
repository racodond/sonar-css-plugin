/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
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

import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

import java.util.List;

public class TextUnderlinePositionValidator implements ValueValidator {

  private static final ValueElementValidator IDENTIFIER_VALIDATOR = new IdentifierValidator("auto", "under", "left", "right");

  @Override
  public boolean isValid(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    int numberOfValueElements = valueElements.size();

    if (numberOfValueElements == 1) {
      return IDENTIFIER_VALIDATOR.isValid(valueElements.get(0));
    }
    if (numberOfValueElements == 2) {
      int count = 0;
      for (Tree valueElement : valueElements) {
        if (!(valueElement instanceof IdentifierTree)) {
          return false;
        }

        String identifierText = ((IdentifierTree) valueElement).text();

        if ("right".equals(identifierText) || "left".equals(identifierText)) {
          count++;
        } else if (!"under".equals(identifierText)) {
          return false;
        }
      }
      return count < 2;
    }
    return false;
  }

  @Override
  public String getValidatorFormat() {
    return "auto | [ under || [ left | right ] ]";
  }

}
