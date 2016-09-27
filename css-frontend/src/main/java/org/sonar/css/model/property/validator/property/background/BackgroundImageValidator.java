/*
 * SonarQube CSS / Less Plugin
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

import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.ValueTree;

public class BackgroundImageValidator implements ValueValidator {

  @Override
  public boolean isValid(ValueTree valueTree) {
    List<Tree> valueElements = valueTree.sanitizedValueElements();
    for (int i = 0; i < valueElements.size(); i++) {
      if (ValidatorFactory.getNoneValidator().isValid(valueElements.get(i)) && i != 0) {
        return false;
      } else if (!ValidatorFactory.getImageValidator().isValid(valueElements.get(i)) && !ValidatorFactory.getNoneValidator().isValid(valueElements.get(i))
        && !ValidatorFactory.getCommaDelimiterValidator().isValid(valueElements.get(i))) {
        return false;
      }
    }
    return checkRepeatStyleList(buildBackgroundImageList(valueTree));
  }

  @Override
  public String getValidatorFormat() {
    return "none | <image> [, <image>]*";
  }

  private List<List<Tree>> buildBackgroundImageList(ValueTree valueTree) {
    List<List<Tree>> backgroundImageList = new ArrayList<>();
    backgroundImageList.add(new ArrayList<>());
    int listIndex = 0;
    for (Tree valueElement : valueTree.sanitizedValueElements()) {
      if (ValidatorFactory.getCommaDelimiterValidator().isValid(valueElement)) {
        backgroundImageList.add(new ArrayList<>());
        listIndex++;
      } else {
        backgroundImageList.get(listIndex).add(valueElement);
      }

    }
    return backgroundImageList;
  }

  private boolean checkRepeatStyleList(List<List<Tree>> repeatStyleList) {
    for (List<Tree> elementList : repeatStyleList) {
      if (elementList.size() != 1) {
        return false;
      }
    }
    return true;
  }

}
