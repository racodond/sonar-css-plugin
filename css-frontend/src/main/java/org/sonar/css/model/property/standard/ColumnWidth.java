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
package org.sonar.css.model.property.standard;

import org.sonar.css.model.Vendor;
import org.sonar.css.model.function.standard.FitContent;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.css.model.property.validator.valueelement.function.FunctionValidator;

public class ColumnWidth extends StandardProperty {

  public ColumnWidth() {
    setExperimental(true);
    addLinks(
      "https://drafts.csswg.org/css-multicol-1/#propdef-column-width",
      "https://drafts.csswg.org/css-sizing-3/#column-sizing",
      "https://developer.mozilla.org/en-US/docs/Web/CSS/column-width");
    addVendors(Vendor.MOZILLA);
    addValidators(
      ValidatorFactory.getAutoValidator(),
      ValidatorFactory.getPositiveLengthValidator(),
      new IdentifierValidator("fill", "max-content", "min-content", "fit-content"),
      new FunctionValidator(FitContent.class));
  }

}
