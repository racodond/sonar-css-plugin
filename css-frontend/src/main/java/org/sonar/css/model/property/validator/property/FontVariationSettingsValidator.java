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

import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueCommaSeparatedListTree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

import java.util.List;

public class FontVariationSettingsValidator implements ValueValidator {

  @Override
  public boolean isValid(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    if (valueElements.size() == 1) {
      if (valueElements.get(0).is(Tree.Kind.IDENTIFIER)) {
        return new IdentifierValidator("normal").isValid(valueElements.get(0));
      } else if (valueElements.get(0).is(Tree.Kind.VALUE_COMMA_SEPARATED_LIST)) {
        return validateMultipleFontVariationSettings((ValueCommaSeparatedListTree) valueElements.get(0));
      }
    } else if (valueElements.size() == 2) {
      return validateSingleFontVariationSettings(valueElements);
    }
    return false;
  }

  private boolean validateMultipleFontVariationSettings(ValueCommaSeparatedListTree stringNumbers) {
    for (ValueTree stringNumber : stringNumbers.values()) {
      if (!validateSingleFontVariationSettings(stringNumber.sanitizedValueElements())) {
        return false;
      }
    }
    return true;
  }

  private boolean validateSingleFontVariationSettings(List<Tree> valueElements) {
    return valueElements.size() == 2
      && ValidatorFactory.getStringValidator().isValid(valueElements.get(0))
      && ValidatorFactory.getNumberValidator().isValid(valueElements.get(1));
  }

  @Override
  public String getValidatorFormat() {
    return "normal | [<string> <number>]#";
  }

}
