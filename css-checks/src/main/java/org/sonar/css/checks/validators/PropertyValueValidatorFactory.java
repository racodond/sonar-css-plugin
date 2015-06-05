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
package org.sonar.css.checks.validators;

import com.google.common.collect.ImmutableList;
import org.sonar.css.checks.validators.valueelement.MarginWidthValidator;
import org.sonar.css.checks.validators.valueelement.PaddingWidthValidator;
import org.sonar.css.checks.validators.valueelement.PauseValidator;
import org.sonar.css.checks.validators.valueelement.ColorValidator;
import org.sonar.css.checks.validators.valueelement.IdentifierValidator;
import org.sonar.css.checks.validators.valueelement.UriValidator;
import org.sonar.css.checks.validators.valueelement.dimension.AngleValidator;
import org.sonar.css.checks.validators.valueelement.dimension.FrequencyValidator;
import org.sonar.css.checks.validators.valueelement.dimension.LengthValidator;
import org.sonar.css.checks.validators.valueelement.dimension.TimeValidator;
import org.sonar.css.checks.validators.valueelement.numeric.IntegerValidator;
import org.sonar.css.checks.validators.valueelement.numeric.NumberValidator;
import org.sonar.css.checks.validators.valueelement.numeric.PercentageValidator;

public class PropertyValueValidatorFactory {

  private static final PropertyValueElementValidator angleValidator = new AngleValidator();
  private static final PropertyValueElementValidator autoValidator = new IdentifierValidator(ImmutableList.of("auto"));
  private static final PropertyValueElementValidator colorValidator = new ColorValidator();
  private static final PropertyValueElementValidator frequencyValidator = new FrequencyValidator();
  private static final PropertyValueElementValidator integerValidator = new IntegerValidator();
  private static final PropertyValueElementValidator lengthValidator = new LengthValidator(false);
  private static final PropertyValueElementValidator noneValidator = new IdentifierValidator(ImmutableList.of("none"));
  private static final PropertyValueElementValidator numberValidator = new NumberValidator(false);
  private static final PropertyValueElementValidator percentageValidator = new PercentageValidator(false);
  private static final PropertyValueElementValidator positiveIntegerValidator = new IntegerValidator(true);
  private static final PropertyValueElementValidator positiveLengthValidator = new LengthValidator(true);
  private static final PropertyValueElementValidator positiveNumberValidator = new NumberValidator(true);
  private static final PropertyValueElementValidator positivePercentageValidator = new PercentageValidator(true);
  private static final PropertyValueElementValidator positiveTimeValidator = new TimeValidator(true);
  private static final PropertyValueElementValidator timeValidator = new TimeValidator(false);
  private static final PropertyValueElementValidator uriValidator = new UriValidator();

  private static final Validator marginWidthValidator = new MarginWidthValidator();
  private static final Validator outlineStyleValidator = new IdentifierValidator(ImmutableList.of("none", "dotted", "dashed", "solid", "double", "groove", "ridge", "inset",
    "outset"));
  private static final Validator paddingWidthValidator = new PaddingWidthValidator();
  private static final Validator pageBreakValidator = new IdentifierValidator(ImmutableList.of("auto", "always", "avoid", "left", "right"));
  private static final Validator pauseValidator = new PauseValidator();

  public static PropertyValueElementValidator getAngleValidator() {
    return angleValidator;
  }

  public static PropertyValueElementValidator getAutoValidator() {
    return autoValidator;
  }

  public static PropertyValueElementValidator getFrequencyValidator() {
    return frequencyValidator;
  }

  public static PropertyValueElementValidator getColorValidator() {
    return colorValidator;
  }

  public static PropertyValueElementValidator getIntegerValidator() {
    return integerValidator;
  }

  public static PropertyValueElementValidator getLengthValidator() {
    return lengthValidator;
  }

  public static PropertyValueElementValidator getPositiveLengthValidator() {
    return positiveLengthValidator;
  }

  public static PropertyValueElementValidator getNoneValidator() {
    return noneValidator;
  }

  public static PropertyValueElementValidator getNumberValidator() {
    return numberValidator;
  }

  public static PropertyValueElementValidator getPercentageValidator() {
    return percentageValidator;
  }

  public static PropertyValueElementValidator getPositiveIntegerValidator() {
    return positiveIntegerValidator;
  }

  public static PropertyValueElementValidator getPositiveNumberValidator() {
    return positiveNumberValidator;
  }

  public static PropertyValueElementValidator getPositivePercentageValidator() {
    return positivePercentageValidator;
  }

  public static PropertyValueElementValidator getPositiveTimeValidator() {
    return positiveTimeValidator;
  }

  public static PropertyValueElementValidator getTimeValidator() {
    return timeValidator;
  }

  public static PropertyValueElementValidator getUriValidator() {
    return uriValidator;
  }

  public static Validator getMarginWidthValidator() {
    return marginWidthValidator;
  }

  public static Validator getOutlineStyleValidator() {
    return outlineStyleValidator;
  }

  public static Validator getPaddingWidthValidator() {
    return paddingWidthValidator;
  }

  public static Validator getPageBreakValidator() {
    return pageBreakValidator;
  }

  public static Validator getPauseValidator() {
    return pauseValidator;
  }

}
