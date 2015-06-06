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

import org.junit.Test;
import org.sonar.css.checks.validators.base.NotYetImplementedValidator;
import org.sonar.css.checks.validators.PropertyValueValidator;

import static org.fest.assertions.Assertions.assertThat;

public class CssPropertiesTest {

  @Test
  public void number_of_vendors() {
    assertThat(Vendors.VENDORS.size()).isEqualTo(17);
  }

  @Test
  public void number_of_properties() {
    assertThat(CssProperties.PROPERTIES.size()).isEqualTo(309);
  }

  @Test
  public void number_of_not_yet_implemented_validator_properties() {
    int count = 0;
    for (CssProperty property : CssProperties.PROPERTIES.values()) {
      if (containsNoYetImplementedValidator(property)) {
        count++;
      }
    }
    assertThat(count).isEqualTo(219);
  }

  @Test
  public void each_property_should_contain_at_least_one_validator() {
    for (CssProperty property : CssProperties.PROPERTIES.values()) {
      assertThat(property.getValidators().size()).isGreaterThanOrEqualTo(1);
    }
  }

  @Test
  public void there_should_not_be_not_yet_implemented_validator_with_other_validators() {
    for (CssProperty property : CssProperties.PROPERTIES.values()) {
      assertThat(containsNotYetImplementedValidatorWithOtherValidators(property)).isFalse();
    }
  }

  private boolean containsNoYetImplementedValidator(CssProperty property) {
    for (PropertyValueValidator validator : property.getValidators()) {
      if (validator.getClass().equals(NotYetImplementedValidator.class)) {
        return true;
      }
    }
    return false;
  }

  private boolean containsNotYetImplementedValidatorWithOtherValidators(CssProperty property) {
    if (containsNoYetImplementedValidator(property)) {
      for (PropertyValueValidator validator : property.getValidators()) {
        if (!validator.getClass().equals(NotYetImplementedValidator.class)) {
          return true;
        }
      }
    }
    return false;
  }

}
