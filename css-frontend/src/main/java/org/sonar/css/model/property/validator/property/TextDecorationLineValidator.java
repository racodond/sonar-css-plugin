/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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

import java.util.ArrayList;
import java.util.List;

import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

public class TextDecorationLineValidator implements ValueValidator {

  private static final ValueElementValidator IDENTIFIER_VALIDATOR = new IdentifierValidator("underline", "overline", "line-through", "blink");

  @Override
  public boolean isValid(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    int numberOfValueElements = valueElements.size();

    if (numberOfValueElements > 4) {
      return false;
    }
    List<String> listTextDecorationLine = new ArrayList<>();
    for (int i = 0; i < valueElements.size(); i++) {
      if (i == 0 && (ValidatorFactory.getNoneValidator().isValid(valueElements.get(i)) && valueElements.size() == 1 || IDENTIFIER_VALIDATOR.isValid(valueElements.get(i)))) {
        listTextDecorationLine.add(((IdentifierTree) valueElements.get(i)).text());
        continue;
      }
      if (i != 0 && IDENTIFIER_VALIDATOR.isValid(valueElements.get(i)) && !listTextDecorationLine.contains(((IdentifierTree) valueElements.get(i)).text())) {
        listTextDecorationLine.add(((IdentifierTree) valueElements.get(i)).text());
        continue;
      }
      return false;
    }
    return true;
  }

  @Override
  public String getValidatorFormat() {
    return "none | [ underline || overline || line-through || blink ]";
  }

}
