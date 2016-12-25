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
package org.sonar.css.model.property.validator.property.background;

import java.util.ArrayList;
import java.util.List;

import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.plugins.css.api.tree.css.DelimiterTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

public class BackgroundRepeatValidator implements ValueValidator {

  private static final IdentifierValidator REPEAT_XY_VALIDATOR = new IdentifierValidator("repeat-x", "repeat-y");
  private static final IdentifierValidator REPEAT_OTHERS_VALIDATOR = new IdentifierValidator("repeat", "space", "round", "no-repeat");

  @Override
  public boolean isValid(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    for (Tree valueElement : valueElements) {
      if (valueElement instanceof DelimiterTree) {
        if (!",".equals(((DelimiterTree) valueElement).text())) {
          return false;
        }
      } else if (!REPEAT_XY_VALIDATOR.isValid(valueElement) && !REPEAT_OTHERS_VALIDATOR.isValid(valueElement)) {
        return false;
      }
    }
    return checkRepeatStyleList(buildRepeatStyleList(valueTree));
  }

  @Override
  public String getValidatorFormat() {
    return "repeat-x | repeat-y | [repeat | space | round | no-repeat]{1,2} [, repeat-x | repeat-y | [repeat | space | round | no-repeat]{1,2}]*";
  }

  private List<List<Tree>> buildRepeatStyleList(ValueTree valueTree) {
    List<List<Tree>> repeatStyleList = new ArrayList<>();
    repeatStyleList.add(new ArrayList<>());
    int listIndex = 0;
    for (Tree valueElement : valueTree.sanitizedValueElements()) {
      if (valueElement instanceof DelimiterTree) {
        repeatStyleList.add(new ArrayList<>());
        listIndex++;
      } else {
        repeatStyleList.get(listIndex).add(valueElement);
      }

    }
    return repeatStyleList;
  }

  private boolean checkRepeatStyleList(List<List<Tree>> repeatStyleList) {
    for (List<Tree> elementList : repeatStyleList) {
      if (elementList.isEmpty()
        || (elementList.size() == 2 && (REPEAT_XY_VALIDATOR.isValid(elementList.get(0)) || REPEAT_XY_VALIDATOR.isValid(elementList.get(1))))) {
        return false;
      }
    }
    return true;
  }

}
