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
package org.sonar.css.model.property;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.css.model.Vendor;
import org.sonar.css.model.property.validator.Validator;

public class StandardProperty {

  private String name;
  private boolean obsolete;
  private boolean experimental;
  private final List<Vendor> vendors;
  private final List<Validator> validators;
  private final List<String> links;

  public StandardProperty() {
    name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, this.getClass().getSimpleName());
    obsolete = false;
    experimental = false;
    vendors = new ArrayList<>();
    validators = new ArrayList<>();
    links = new ArrayList<>();
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setObsolete(boolean obsolete) {
    this.obsolete = obsolete;
  }

  public void setExperimental(boolean experimental) {
    this.experimental = experimental;
  }

  public void addVendors(Vendor... allVendors) {
    vendors.addAll(Lists.newArrayList(allVendors));
  }

  public void addValidators(Validator... allValidators) {
    validators.addAll(Lists.newArrayList(allValidators));
  }

  public void addLinks(String... allLinks) {
    links.addAll(Lists.newArrayList(allLinks));
  }

  @Nonnull
  public String getName() {
    return name;
  }

  public boolean isObsolete() {
    return obsolete;
  }

  public boolean isExperimental() {
    return experimental;
  }

  @Nonnull
  @VisibleForTesting
  public List<Validator> getValidators() {
    return validators;
  }

  @Nonnull
  public List<Vendor> getVendors() {
    return vendors;
  }

  @Nonnull
  public List<String> getLinks() {
    return links;
  }

  @Nonnull
  public String getValidatorFormat() {
    StringBuilder format = new StringBuilder("");
    for (Validator validator : validators) {
      if (format.length() != 0) {
        format.append(" | ");
      }
      format.append(validator.getValidatorFormat());
    }
    return format.toString();
  }

}
