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
package org.sonar.css.checks.utils;

import com.google.common.annotations.VisibleForTesting;
import com.sonar.sslr.api.AstNode;
import org.sonar.css.checks.validators.propertyvalue.PropertyValidatorFactory;
import org.sonar.css.checks.validators.propertyvalue.PropertyValueValidator;
import org.sonar.css.parser.CssGrammar;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

public class CssProperty {

  private String name;
  private List<String> vendors = new ArrayList<>();
  private List<PropertyValueValidator> validators = new ArrayList<>();

  public CssProperty(String name) {
    this.name = name;
  }

  @VisibleForTesting
  @Nonnull
  public String getName() {
    return name;
  }

  @Nonnull
  public List<String> getVendors() {
    return vendors;
  }

  @VisibleForTesting
  @Nonnull
  public List<PropertyValueValidator> getValidators() {
    return validators;
  }

  @Nonnull
  public CssProperty addValidator(@Nonnull PropertyValueValidator validator) {
    validators.add(validator);
    return this;
  }

  @Nonnull
  public CssProperty addVendor(@Nonnull String vendor) {
    vendors.add(vendor);
    return this;
  }

  @Nonnull
  public String getValidatorFormat() {
    StringBuilder format = new StringBuilder("");
    if (validators != null) {
      for (PropertyValueValidator validator : validators) {
        if (format.length() != 0) {
          format.append(" | ");
        }
        format.append(validator.getValidatorFormat());
      }
    }
    return format.toString();
  }

  public boolean isPropertyValueValid(@Nonnull AstNode astNode) {
    if (!astNode.is(CssGrammar.VALUE)) {
      throw new IllegalArgumentException("Node is not of type VALUE");
    }
    for (PropertyValueValidator validator : validators) {
      if (validator.isPropertyValueValid(astNode)) {
        return true;
      }
    }
    return PropertyValidatorFactory.getAllowedValuesForAllPropertiesValidator().isPropertyValueValid(astNode);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (!((obj instanceof CssProperty) || (obj instanceof String))) {
      return false;
    }
    return name.equalsIgnoreCase(obj.toString());
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return name;
  }

}
