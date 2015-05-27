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

import com.google.common.collect.ImmutableList;

public class BasePropertyValidatorFactory {

  private static final PropertyValueValidator angleValidator = new AngleValidator();
  private static final PropertyValueValidator autoValueValidator = new EnumValidator(ImmutableList.of("auto"));
  private static final PropertyValueValidator allowedValuesForAllPropertiesValidator = new AllowedValuesForAllPropertiesValidator();
  private static final PropertyValueValidator frequencyValidator = new FrequencyValidator();
  private static final PropertyValueValidator integerValidator = new IntegerValidator();
  private static final PropertyValueValidator lengthValidator = new LengthValidator(false);
  private static final PropertyValueValidator positiveLengthValidator = new LengthValidator(true);
  private static final PropertyValueValidator notYetImplementedValidator = new NotYetImplementedValidator();
  private static final PropertyValueValidator numberValidator = new NumberValidator();
  private static final PropertyValueValidator percentageValidator = new PercentageValidator(false);
  private static final PropertyValueValidator positivePercentageValidator = new PercentageValidator(true);
  private static final PropertyValueValidator timeValidator = new TimeValidator();
  private static final PropertyValueValidator uriValidator = new UriValidator();

  public static PropertyValueValidator getAngleValidator() {
    return angleValidator;
  }

  public static PropertyValueValidator getAutoValueValidator() {
    return autoValueValidator;
  }

  public static PropertyValueValidator getAllowedValuesForAllPropertiesValidator() {
    return allowedValuesForAllPropertiesValidator;
  }

  public static PropertyValueValidator getFrequencyValidator() {
    return frequencyValidator;
  }

  public static PropertyValueValidator getIntegerValidator() {
    return integerValidator;
  }

  public static PropertyValueValidator getLengthValidator() {
    return lengthValidator;
  }

  public static PropertyValueValidator getPositiveLengthValidator() {
    return positiveLengthValidator;
  }

  public static PropertyValueValidator getNumberValidator() {
    return numberValidator;
  }

  public static PropertyValueValidator getNotYetImplementedValidator() {
    return notYetImplementedValidator;
  }

  public static PropertyValueValidator getPercentageValidator() {
    return percentageValidator;
  }

  public static PropertyValueValidator getPositivePercentageValidator() {
    return positivePercentageValidator;
  }

  public static PropertyValueValidator getTimeValidator() {
    return timeValidator;
  }

  public static PropertyValueValidator getUriValidator() {
    return uriValidator;
  }

}
