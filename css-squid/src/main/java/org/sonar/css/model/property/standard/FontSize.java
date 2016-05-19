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
package org.sonar.css.model.property.standard;

import com.google.common.collect.ImmutableList;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.validator.ValidatorFactory;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;

public class FontSize extends StandardProperty {

  public FontSize() {
    addLinks("http://www.w3.org/TR/CSS2/fonts.html#propdef-font-size");
    addValidators(
      new IdentifierValidator(ImmutableList.of("xx-small", "x-small", "small", "medium", "large", "x-large", "xx-large")),
      new IdentifierValidator(ImmutableList.of("larger", "smaller")),
      ValidatorFactory.getPositiveLengthValidator(),
      ValidatorFactory.getPositivePercentageValidator());
  }

}
