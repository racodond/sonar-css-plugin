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
package org.sonar.css.checks.validators.propertyValue;

import com.sonar.sslr.api.AstNode;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ZeroNumberValidatorTest {

  ZeroNumberValidator validator = new ZeroNumberValidator();
  AstNode astNode = mock(AstNode.class);

  @Test
  public void should_be_zero_when_value_is_zero() {
    when(astNode.getTokenValue()).thenReturn("0");
    assertThat(validator.isValid(astNode)).isTrue();
  }

  @Test
  public void should_not_be_zero_when_value_is_not_a_number() {
    when(astNode.getTokenValue()).thenReturn("abc");
    assertThat(validator.isValid(astNode)).isFalse();
  }

  @Test
  public void should_be_zero_integer() {
    when(astNode.getTokenValue()).thenReturn("0");
    assertThat(validator.isValid(astNode)).isTrue();
  }

  @Test
  public void should_be_zero_one_decimal() {
    when(astNode.getTokenValue()).thenReturn("0.0");
    assertThat(validator.isValid(astNode)).isTrue();
  }

  @Test
  public void should_be_zero_two_decimals() {
    when(astNode.getTokenValue()).thenReturn("0.00");
    assertThat(validator.isValid(astNode)).isTrue();
  }

  @Test
  public void should_be_zero_starting_with_period_one_decimal() {
    when(astNode.getTokenValue()).thenReturn(".0");
    assertThat(validator.isValid(astNode)).isTrue();
  }

  @Test
  public void should_be_zero_starting_with_period_two_decimals() {
    when(astNode.getTokenValue()).thenReturn(".00");
    assertThat(validator.isValid(astNode)).isTrue();
  }

  @Test
  public void should_be_zero_negative_integer() {
    when(astNode.getTokenValue()).thenReturn("-0");
    assertThat(validator.isValid(astNode)).isTrue();
  }

  @Test
  public void should_be_zero_negative_decimal() {
    when(astNode.getTokenValue()).thenReturn("-0.0");
    assertThat(validator.isValid(astNode)).isTrue();
  }

  @Test
  public void should_not_be_zero_when_value_is_period() {
    when(astNode.getTokenValue()).thenReturn(".");
    assertThat(validator.isValid(astNode)).isFalse();
  }

}
