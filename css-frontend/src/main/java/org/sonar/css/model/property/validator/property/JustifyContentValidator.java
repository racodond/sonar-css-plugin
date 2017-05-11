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

import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

import java.util.List;

public class JustifyContentValidator implements ValueValidator {

  private static final IdentifierValidator NORMAL_VALIDATOR = new IdentifierValidator("normal");
  private static final IdentifierValidator CONTENT_DISTRIBUTION_VALIDATOR = new IdentifierValidator("space-between", "space-around", "space-evenly", "stretch");
  private static final IdentifierValidator OVERFLOW_POSITION_VALIDATOR = new IdentifierValidator("unsafe", "safe");
  private static final IdentifierValidator CONTENT_POSITION_VALIDATOR = new IdentifierValidator("center", "start", "end", "flex-start", "flex-end", "left", "right");

  @Override
  public boolean isValid(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    int numberOfValueElements = valueElements.size();

    if (numberOfValueElements > 2) {
      return false;
    }

    if (numberOfValueElements == 1) {
      return NORMAL_VALIDATOR.isValid(valueElements.get(0))
        || CONTENT_DISTRIBUTION_VALIDATOR.isValid(valueElements.get(0))
        || CONTENT_POSITION_VALIDATOR.isValid(valueElements.get(0));
    } else {
      return OVERFLOW_POSITION_VALIDATOR.isValid(valueElements.get(0))
        && CONTENT_POSITION_VALIDATOR.isValid(valueElements.get(1));
    }
  }

  @Override
  public String getValidatorFormat() {
    return "normal | space-between | space-around | space-evenly | stretch | [ [unsafe | safe]? [center | start | end | flex-start | flex-end | left | right] ]";
  }

}
