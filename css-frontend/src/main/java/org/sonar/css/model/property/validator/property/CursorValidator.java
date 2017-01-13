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

import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.css.model.property.validator.valueelement.numeric.IntegerRangeValidator;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueCommaSeparatedListTree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

public class CursorValidator implements ValueValidator {

  private final ValueElementValidator identifierValidator = new IdentifierValidator(
    "auto", "default", "none", "context-menu", "help", "pointer", "progress", "wait", "cell", "crosshair", "text",
    "vertical-text", "alias", "copy", "move", "no-drop",
    "not-allowed", "e-resize", "n-resize", "ne-resize", "nw-resize", "s-resize", "se-resize", "sw-resize", "w-resize",
    "ew-resize", "ns-resize", "nesw-resize", "nwse-resize",
    "col-resize", "row-resize", "all-scroll", "zoom-in", "zoom-out", "grab", "grabbing");
  private final ValueElementValidator positiveIntegerValidator = new IntegerRangeValidator(0, 32);

  @Override
  public boolean isValid(ValueTree valueTree) {

    if (valueTree.sanitizedValueElements().size() != 1) {
      return false;
    }

    Tree value = valueTree.sanitizedValueElements().get(0);

    if (value.is(Tree.Kind.VALUE_COMMA_SEPARATED_LIST)) {

      ValueCommaSeparatedListTree list = (ValueCommaSeparatedListTree) value;
      int numberOfElements = list.values().size();

      if (list.values().get(numberOfElements - 1).sanitizedValueElements().size() != 1) {
        return false;
      }

      if (!identifierValidator.isValid(list.values().get(numberOfElements - 1).sanitizedValueElements().get(0))) {
        return false;
      }

      for (int i = 0; i < numberOfElements - 1; i++) {
        if (list.values().get(i).sanitizedValueElements().size() != 1
          && list.values().get(i).sanitizedValueElements().size() != 3) {
          return false;
        }

        if (!ValidatorFactory.getUriValidator().isValid(list.values().get(i).sanitizedValueElements().get(0))) {
          return false;
        }

        if (list.values().get(i).sanitizedValueElements().size() == 3) {
          for (int j = 1; j < 3; j++) {
            if (!positiveIntegerValidator.isValid(list.values().get(i).sanitizedValueElements().get(j))) {
              return false;
            }
          }
        }
      }

      return true;
    }

    if (!identifierValidator.isValid(valueTree.sanitizedValueElements().get(0))) {
      return false;
    }

    return true;
  }

  @Override
  public String getValidatorFormat() {
    return "[[ <uri> [<number> <number>]?,]* [" + identifierValidator.getValidatorFormat() + "]";
  }

}
