/*
 * SonarQube CSS Plugin
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
package org.sonar.css.model;

import javax.annotation.Nonnull;

import org.sonar.css.model.function.StandardFunction;
import org.sonar.css.model.function.StandardFunctionFactory;

import java.util.Locale;

public class Function {

  private final String fullName;
  private final Vendor vendorPrefix;
  private final StandardFunction standardFunction;

  public Function(String fullName) {
    this.fullName = fullName;
    this.vendorPrefix = setVendorPrefix();
    this.standardFunction = setStandardFunction();
  }

  public boolean isVendorPrefixed() {
    return vendorPrefix != null;
  }

  @Nonnull
  public StandardFunction getStandardFunction() {
    return standardFunction;
  }

  private Vendor setVendorPrefix() {
    for (Vendor vendor : Vendor.values()) {
      if (fullName.toLowerCase(Locale.ENGLISH).startsWith(vendor.getPrefix())) {
        return vendor;
      }
    }
    return null;
  }

  private StandardFunction setStandardFunction() {
    String functionName = fullName;
    if (isVendorPrefixed()) {
      functionName = functionName.substring(vendorPrefix.getPrefix().length());
    }
    return StandardFunctionFactory.createStandardFunction(functionName);
  }

}
