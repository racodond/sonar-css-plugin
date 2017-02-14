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
package org.sonar.css.model.property.standard;

import org.sonar.css.model.Vendor;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.validator.MultiplierValidator;
import org.sonar.css.model.property.validator.ValidatorFactory;

public class ContentZoomLimit extends StandardProperty {

  public ContentZoomLimit() {
    setExperimental(true);
    addLinks("https://msdn.microsoft.com/en-us/library/jj127330(v=vs.85).aspx");
    addVendors(Vendor.MICROSOFT);
    addValidators(new MultiplierValidator(2, ValidatorFactory.getPercentageValidator()));
    addShorthandFor("content-zoom-limit-max", "content-zoom-limit-min");
  }

}
