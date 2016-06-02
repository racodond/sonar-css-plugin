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

import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.StandardPropertyFactory;

import java.util.Locale;

public class Property {

  private final String rawName;
  private final Vendor vendorPrefix;
  private final String hack;
  private final StandardProperty standardProperty;

  public Property(String rawName) {
    this.rawName = rawName;
    this.hack = setHack();
    this.vendorPrefix = setVendorPrefix();
    this.standardProperty = setStandardCssProperty();
  }

  public Vendor getVendorPrefix() {
    return vendorPrefix;
  }

  public String getHack() {
    return hack;
  }

  @Nonnull
  public StandardProperty getStandardProperty() {
    return standardProperty;
  }

  public boolean isHacked() {
    return hack != null;
  }

  public boolean isVendorPrefixed() {
    return vendorPrefix != null;
  }

  public String getUnhackedFullName() {
    return (vendorPrefix != null ? vendorPrefix.getPrefix() : "") + standardProperty.getName();
  }

  private String setHack() {
    if (rawName.startsWith("*") || rawName.startsWith("_")) {
      return rawName.substring(0, 1);
    } else {
      return null;
    }
  }

  private Vendor setVendorPrefix() {
    for (Vendor vendor : Vendor.values()) {
      if (rawName.toLowerCase(Locale.ENGLISH).startsWith(vendor.getPrefix())) {
        return vendor;
      }
    }
    return null;
  }

  private StandardProperty setStandardCssProperty() {
    String propertyName = rawName;
    if (isHacked()) {
      propertyName = propertyName.substring(1);
    }
    if (isVendorPrefixed()) {
      propertyName = propertyName.substring(vendorPrefix.getPrefix().length());
    }
    return StandardPropertyFactory.createStandardProperty(propertyName);
  }

}
