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
import org.sonar.css.checks.validators.property.BorderWidthValidator;
import org.sonar.css.checks.validators.property.MarginWidthValidator;
import org.sonar.css.checks.validators.property.PaddingWidthValidator;
import org.sonar.css.checks.validators.property.PauseValidator;
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

  private static final Validator angleValidator = new AngleValidator();
  private static final Validator autoValidator = new IdentifierValidator(ImmutableList.of("auto"));
  private static final Validator colorValidator = new ColorValidator();
  private static final Validator frequencyValidator = new FrequencyValidator();
  private static final Validator integerValidator = new IntegerValidator();
  private static final Validator lengthValidator = new LengthValidator(false);
  private static final Validator positiveLengthValidator = new LengthValidator(true);
  private static final Validator noneValidator = new IdentifierValidator(ImmutableList.of("none"));
  private static final Validator numberValidator = new NumberValidator();
  private static final Validator percentageValidator = new PercentageValidator(false);
  private static final Validator positivePercentageValidator = new PercentageValidator(true);
  private static final Validator timeValidator = new TimeValidator();
  private static final Validator uriValidator = new UriValidator();

  private static final Validator borderStyleValidator = new IdentifierValidator(ImmutableList.of("none", "hidden", "dotted", "dashed", "solid", "double", "groove", "ridge",
    "inset", "outset"));
  private static final Validator borderWidthValidator = new BorderWidthValidator();
  private static final Validator marginWidthValidator = new MarginWidthValidator();
  private static final Validator outlineStyleValidator = new IdentifierValidator(ImmutableList.of("none", "dotted", "dashed", "solid", "double", "groove", "ridge", "inset",
    "outset"));
  private static final Validator paddingWidthValidator = new PaddingWidthValidator();
  private static final Validator pageBreakValidator = new IdentifierValidator(ImmutableList.of("auto", "always", "avoid", "left", "right"));
  private static final Validator pauseValidator = new PauseValidator();

  public static Validator getAngleValidator() {
    return angleValidator;
  }

  public static Validator getAutoValidator() {
    return autoValidator;
  }

  public static Validator getFrequencyValidator() {
    return frequencyValidator;
  }

  public static Validator getColorValidator() {
    return colorValidator;
  }

  public static Validator getIntegerValidator() {
    return integerValidator;
  }

  public static Validator getLengthValidator() {
    return lengthValidator;
  }

  public static Validator getPositiveLengthValidator() {
    return positiveLengthValidator;
  }

  public static Validator getNoneValidator() {
    return noneValidator;
  }

  public static Validator getNumberValidator() {
    return numberValidator;
  }

  public static Validator getPercentageValidator() {
    return percentageValidator;
  }

  public static Validator getPositivePercentageValidator() {
    return positivePercentageValidator;
  }

  public static Validator getTimeValidator() {
    return timeValidator;
  }

  public static Validator getUriValidator() {
    return uriValidator;
  }

  public static Validator getBorderStyleValidator() {
    return borderStyleValidator;
  }

  public static Validator getBorderWidthValidator() {
    return borderWidthValidator;
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
