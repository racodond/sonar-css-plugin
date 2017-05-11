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

public class JustifySelfValidator implements ValueValidator {

  private static final IdentifierValidator AUTO_NORMAL_STRETCH_VALIDATOR = new IdentifierValidator("auto", "normal", "stretch");
  private static final IdentifierValidator BASELINE_POSITION_VALIDATOR = new IdentifierValidator("first", "last");
  private static final IdentifierValidator BASELINE_VALIDATOR = new IdentifierValidator("baseline");
  private static final IdentifierValidator OVERFLOW_POSITION_VALIDATOR = new IdentifierValidator("unsafe", "safe");
  private static final IdentifierValidator SELF_POSITION_VALIDATOR = new IdentifierValidator("center", "start", "end", "self-start", "self-end", "flex-start", "flex-end", "left", "right");

  @Override
  public boolean isValid(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    int numberOfValueElements = valueElements.size();

    if (numberOfValueElements > 2) {
      return false;
    }

    if (numberOfValueElements == 1) {
      return AUTO_NORMAL_STRETCH_VALIDATOR.isValid(valueElements.get(0))
        || BASELINE_VALIDATOR.isValid(valueElements.get(0))
        || SELF_POSITION_VALIDATOR.isValid(valueElements.get(0));
    } else {
      return (OVERFLOW_POSITION_VALIDATOR.isValid(valueElements.get(0)) && SELF_POSITION_VALIDATOR.isValid(valueElements.get(1)))
        || (SELF_POSITION_VALIDATOR.isValid(valueElements.get(0)) && OVERFLOW_POSITION_VALIDATOR.isValid(valueElements.get(1)))
        || (BASELINE_POSITION_VALIDATOR.isValid(valueElements.get(0)) && BASELINE_VALIDATOR.isValid(valueElements.get(1)));
    }
  }

  @Override
  public String getValidatorFormat() {
    return "auto | normal | stretch | [ [first | last]? baseline ] | [ [unsafe | safe]? && [center | start | end | self-start | self-end | flex-start | flex-end | left | right] ]";
  }

}
