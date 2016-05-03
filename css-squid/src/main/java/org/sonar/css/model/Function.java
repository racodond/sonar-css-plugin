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
package org.sonar.css.model;

import javax.annotation.Nonnull;

import org.sonar.css.model.function.StandardFunction;
import org.sonar.css.model.function.StandardFunctionFactory;

public class Function {

  private final String fullName;
  private final Vendor vendorPrefix;
  private final StandardFunction standardFunction;

  public Function(String fullName) {
    this.fullName = fullName;
    this.vendorPrefix = setVendorPrefix();
    this.standardFunction = setStandardFunction();
  }

  public Vendor getVendorPrefix() {
    return vendorPrefix;
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
      if (fullName.startsWith(vendor.getPrefix())) {
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
