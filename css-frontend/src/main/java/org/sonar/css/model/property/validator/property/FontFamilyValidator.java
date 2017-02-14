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

import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueCommaSeparatedListTree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

import java.util.List;

public class FontFamilyValidator implements ValueValidator {

  @Override
  public boolean isValid(ValueTree tree) {

    List<Tree> elements = tree.sanitizedValueElements();

    if (elements.isEmpty()) {
      return false;
    }

    if (elements.size() == 1) {
      if (isValidSingleValue(elements.get(0))) {
        return true;
      } else if (elements.get(0).is(Tree.Kind.VALUE_COMMA_SEPARATED_LIST)) {
        return isValidCommaSeparatedList((ValueCommaSeparatedListTree) elements.get(0));
      } else {
        return false;
      }
    }

    if (elements.size() > 1) {
      return onlyContainsIdentifiers(elements);
    }

    return false;

  }

  @Override
  public String getValidatorFormat() {
    return "[ <family-name> | <generic-family> ]#";
  }

  private boolean onlyContainsIdentifiers(List<Tree> trees) {
    for (Tree tree : trees) {
      if (!ValidatorFactory.getAnyIdentifierValidator().isValid(tree)) {
        return false;
      }
    }
    return true;
  }

  private boolean isValidSingleValue(Tree tree) {
    return ValidatorFactory.getAnyIdentifierValidator().isValid(tree) || ValidatorFactory.getStringValidator().isValid(tree);
  }

  private boolean isValidCommaSeparatedListElement(ValueTree tree) {
    List<Tree> elements = tree.sanitizedValueElements();
    return (elements.size() > 1 && onlyContainsIdentifiers(elements))
      || (elements.size() == 1 && isValidSingleValue(elements.get(0)));
  }

  private boolean isValidCommaSeparatedList(ValueCommaSeparatedListTree list) {
    for (ValueTree element : list.values()) {
      if (!isValidCommaSeparatedListElement(element)) {
        return false;
      }
    }
    return true;
  }

}
