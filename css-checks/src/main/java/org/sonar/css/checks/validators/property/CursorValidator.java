/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende
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
package org.sonar.css.checks.validators.property;

import com.google.common.collect.ImmutableList;
import org.sonar.css.checks.utils.CssValue;
import org.sonar.css.checks.utils.CssValueElement;
import org.sonar.css.checks.validators.PropertyValueElementValidator;
import org.sonar.css.checks.validators.PropertyValueValidator;
import org.sonar.css.checks.validators.valueelement.DelimiterValidator;
import org.sonar.css.checks.validators.valueelement.IdentifierValidator;
import org.sonar.css.checks.validators.valueelement.UriValidator;
import org.sonar.css.checks.validators.valueelement.numeric.IntegerRangeValidator;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

public class CursorValidator implements PropertyValueValidator {

  private final PropertyValueElementValidator cursorValidator = new IdentifierValidator(
    ImmutableList
      .of("auto", "default", "none", "context-menu", "help", "pointer", "progress", "wait", "cell", "crosshair", "text",
        "vertical-text", "alias", "copy", "move", "no-drop",
        "not-allowed", "e-resize", "n-resize", "ne-resize", "nw-resize", "s-resize", "se-resize", "sw-resize", "w-resize",
        "ew-resize", "ns-resize", "nesw-resize", "nwse-resize",
        "col-resize", "row-resize", "all-scroll", "zoom-in", "zoom-out", "grab", "grabbing"));
  private final PropertyValueElementValidator uriValidator = new UriValidator();
  private final PropertyValueElementValidator positiveIntegerValidator = new IntegerRangeValidator(0, 32);
  private final PropertyValueElementValidator delimiterValidator = new DelimiterValidator(",");

  @Override
  public boolean isValid(@Nonnull CssValue value) {
    if (value.getNumberOfValueElements() == 0) {
      return false;
    }
    List<List<CssValueElement>> cursorList = buildCursorList(value);
    if (cursorList.size() == 1) {
      if (cursorList.get(0).size() != 1
        || cursorList.get(0).size() == 1 && !cursorValidator.isValid(cursorList.get(0).get(0))) {
        return false;
      }
    } else {
      for (int i = 0; i < cursorList.size() - 1; i++) {
        if (cursorList.get(i).size() == 1 && uriValidator.isValid(cursorList.get(i).get(0))) {
          break;
        } else if (cursorList.get(i).size() == 3 && uriValidator.isValid(cursorList.get(i).get(0))
          && positiveIntegerValidator.isValid(cursorList.get(i).get(1)) && positiveIntegerValidator.isValid(
            cursorList.get(i).get(2))) {
          break;
        }
        return false;
      }
      return cursorValidator.isValid(cursorList.get(cursorList.size() - 1).get(0));
    }
    return true;
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return "[[ <uri> [<x> <y>]?,]* [" + cursorValidator.getValidatorFormat() + "]";
  }

  private List<List<CssValueElement>> buildCursorList(CssValue cssValue) {
    List<List<CssValueElement>> cursorList = new ArrayList();
    cursorList.add(new ArrayList<CssValueElement>());
    int listIndex = 0;
    for (CssValueElement valueElement : cssValue.getValueElements()) {
      if (delimiterValidator.isValid(valueElement)) {
        cursorList.add(new ArrayList<CssValueElement>());
        listIndex++;
      } else {
        cursorList.get(listIndex).add(valueElement);
      }
    }
    return cursorList;
  }

}
