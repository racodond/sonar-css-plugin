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
package org.sonar.css.checks.utils;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.AstNode;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.css.checks.validators.Validator;
import org.sonar.css.checks.validators.ValueElementValidator;
import org.sonar.css.checks.validators.ValueValidator;
import org.sonar.css.checks.validators.valueelement.IdentifierValidator;
import org.sonar.css.checks.validators.valueelement.function.FunctionValidator;
import org.sonar.css.parser.CssGrammar;

public class CssProperty {

  private final String name;
  private String url;
  private boolean obsolete = false;
  private List<String> vendors = new ArrayList<>();
  private List<Validator> validators = new ArrayList<>();

  public CssProperty(String name) {
    this.name = name;
  }

  @VisibleForTesting
  @Nonnull
  public String getName() {
    return name;
  }

  public boolean isObsolete() {
    return obsolete;
  }

  public CssProperty setObsolete(boolean obsolete) {
    this.obsolete = obsolete;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public CssProperty setUrl(String url) {
    this.url = url;
    return this;
  }

  @Nonnull
  public List<String> getVendors() {
    return vendors;
  }

  @VisibleForTesting
  @Nonnull
  public List<Validator> getValidators() {
    return validators;
  }

  @Nonnull
  public CssProperty addValidator(@Nonnull Validator validator) {
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
      for (Validator validator : validators) {
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
      throw new IllegalArgumentException("Node is not of type VALUE: " + astNode.getType().toString());
    }

    if (validators.size() == 0) {
      return true;
    }

    CssValue value = new CssValue(astNode);
    if (value.getNumberOfValueElements() == 0) {
      return false;
    }

    if (hasOnlyPropertyValueElementValidators() && value.getNumberOfValueElements() > 1) {
      return false;
    }

    for (Validator validator : validators) {
      if (validator instanceof ValueElementValidator
        && ((ValueElementValidator) validator).isValid(value.getValueElements().get(0))) {
        return true;
      }
      if (validator instanceof ValueValidator
        && ((ValueValidator) validator).isValid(value)) {
        return true;
      }
    }

    return new IdentifierValidator(ImmutableList.of("inherit", "initial", "unset")).isValid(value.getValueElements().get(0))
      || new FunctionValidator(ImmutableList.of("var")).isValid(value.getValueElements().get(0));
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

  private boolean hasOnlyPropertyValueElementValidators() {
    for (Validator validator : validators) {
      if (!(validator instanceof ValueElementValidator)) {
        return false;
      }
    }
    return true;
  }

}
