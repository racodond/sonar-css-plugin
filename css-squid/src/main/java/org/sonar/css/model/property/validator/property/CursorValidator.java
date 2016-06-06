/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende and David RACODON
 * kende.tamas@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.css.model.property.validator.property;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.css.model.Value;
import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.css.model.property.validator.valueelement.numeric.IntegerRangeValidator;
import org.sonar.css.model.value.CssValueElement;

public class CursorValidator implements ValueValidator {

  private final ValueElementValidator identifierValidator = new IdentifierValidator(
    ImmutableList
      .of("auto", "default", "none", "context-menu", "help", "pointer", "progress", "wait", "cell", "crosshair", "text",
        "vertical-text", "alias", "copy", "move", "no-drop",
        "not-allowed", "e-resize", "n-resize", "ne-resize", "nw-resize", "s-resize", "se-resize", "sw-resize", "w-resize",
        "ew-resize", "ns-resize", "nesw-resize", "nwse-resize",
        "col-resize", "row-resize", "all-scroll", "zoom-in", "zoom-out", "grab", "grabbing"));
  private final ValueElementValidator positiveIntegerValidator = new IntegerRangeValidator(0, 32);

  @Override
  public boolean isValid(@Nonnull Value value) {
    List<List<CssValueElement>> cursorList = buildCursorList(value);
    if (cursorList.size() == 1) {
      if (cursorList.get(0).size() != 1
        || cursorList.get(0).size() == 1 && !identifierValidator.isValid(cursorList.get(0).get(0))) {
        return false;
      }
    } else {
      for (int i = 0; i < cursorList.size() - 1; i++) {
        if (cursorList.get(i).size() == 1 && ValidatorFactory.getUriValidator().isValid(cursorList.get(i).get(0))) {
          break;
        } else if (cursorList.get(i).size() == 3 && ValidatorFactory.getUriValidator().isValid(cursorList.get(i).get(0))
          && positiveIntegerValidator.isValid(cursorList.get(i).get(1)) && positiveIntegerValidator.isValid(
            cursorList.get(i).get(2))) {
          break;
        }
        return false;
      }
      return identifierValidator.isValid(cursorList.get(cursorList.size() - 1).get(0));
    }
    return true;
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "[[ <uri> [<x> <y>]?,]* [" + identifierValidator.getValidatorFormat() + "]";
  }

  private List<List<CssValueElement>> buildCursorList(Value value) {
    List<List<CssValueElement>> cursorList = new ArrayList<>();
    cursorList.add(new ArrayList<CssValueElement>());
    int listIndex = 0;
    for (CssValueElement valueElement : value.getValueElements()) {
      if (ValidatorFactory.getCommaDelimiterValidator().isValid(valueElement)) {
        cursorList.add(new ArrayList<CssValueElement>());
        listIndex++;
      } else {
        cursorList.get(listIndex).add(valueElement);
      }
    }
    return cursorList;
  }

}
