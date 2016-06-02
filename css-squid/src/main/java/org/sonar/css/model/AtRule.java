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

import org.sonar.css.model.atrule.StandardAtRule;
import org.sonar.css.model.atrule.StandardAtRuleFactory;

import javax.annotation.Nonnull;
import java.util.Locale;

public class AtRule {

  private final String rawName;
  private final Vendor vendorPrefix;
  private final StandardAtRule standardAtRule;

  public AtRule(String rawName) {
    this.rawName = rawName;
    this.vendorPrefix = setVendorPrefix();
    this.standardAtRule = setStandardAtRule();
  }

  public boolean isVendorPrefixed() {
    return vendorPrefix != null;
  }

  @Nonnull
  public StandardAtRule getStandardAtRule() {
    return standardAtRule;
  }

  private Vendor setVendorPrefix() {
    for (Vendor vendor : Vendor.values()) {
      if (rawName.toLowerCase(Locale.ENGLISH).startsWith(vendor.getPrefix())) {
        return vendor;
      }
    }
    return null;
  }

  private StandardAtRule setStandardAtRule() {
    String atRuleName = rawName;
    if (isVendorPrefixed()) {
      atRuleName = atRuleName.substring(vendorPrefix.getPrefix().length());
    }
    return StandardAtRuleFactory.createStandardAtRule(atRuleName);
  }

}
