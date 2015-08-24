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
package org.sonar.css.checks.validators;

import com.google.common.collect.ImmutableList;
import org.sonar.css.checks.validators.property.CounterValidator;
import org.sonar.css.checks.validators.property.border.BorderRadiusValidator;
import org.sonar.css.checks.validators.property.border.BorderValidator;
import org.sonar.css.checks.validators.valueelement.*;
import org.sonar.css.checks.validators.valueelement.dimension.AngleValidator;
import org.sonar.css.checks.validators.valueelement.dimension.FrequencyValidator;
import org.sonar.css.checks.validators.valueelement.dimension.LengthValidator;
import org.sonar.css.checks.validators.valueelement.dimension.TimeValidator;
import org.sonar.css.checks.validators.valueelement.numeric.IntegerValidator;
import org.sonar.css.checks.validators.valueelement.numeric.NumberValidator;
import org.sonar.css.checks.validators.valueelement.numeric.PercentageValidator;

public class ValidatorFactory {

  private static final ValueElementValidator angleValidator = new AngleValidator();
  private static final ValueElementValidator anyIdentifierValidator = new IdentifierValidator();
  private static final ValueElementValidator autoValidator = new IdentifierValidator(ImmutableList.of("auto"));
  private static final ValueElementValidator boxValidator = new BoxValidator();
  private static final ValueElementValidator colorValidator = new ColorValidator();
  private static final ValueElementValidator commaDelimiterValidator = new DelimiterValidator(",");
  private static final ValueElementValidator frequencyValidator = new FrequencyValidator();
  private static final ValueElementValidator integerValidator = new IntegerValidator();
  private static final ValueElementValidator lengthValidator = new LengthValidator(false);
  private static final ValueElementValidator noneValidator = new IdentifierValidator(ImmutableList.of("none"));
  private static final ValueElementValidator numberValidator = new NumberValidator(false);
  private static final ValueElementValidator pageBreakValidator = new IdentifierValidator(ImmutableList.of("auto", "always", "avoid", "left", "right"));
  private static final ValueElementValidator percentageValidator = new PercentageValidator(false);
  private static final ValueElementValidator positiveIntegerValidator = new IntegerValidator(true);
  private static final ValueElementValidator positiveLengthValidator = new LengthValidator(true);
  private static final ValueElementValidator positiveNumberValidator = new NumberValidator(true);
  private static final ValueElementValidator positivePercentageValidator = new PercentageValidator(true);
  private static final ValueElementValidator positiveTimeValidator = new TimeValidator(true);
  private static final ValueElementValidator sizeValidator = new SizeValidator();
  private static final ValueElementValidator stringValidator = new StringValidator();
  private static final ValueElementValidator timeValidator = new TimeValidator(false);
  private static final ValueElementValidator uriValidator = new UriValidator();

  private static final ValueElementValidator borderStyleValidator = new BorderStyleValidator();
  private static final ValueElementValidator borderWidthValidator = new BorderWidthValidator();
  private static final ValueElementValidator cueValidator = new CueValidator();
  private static final ValueElementValidator imageValidator = new ImageValidator();
  private static final ValueElementValidator marginWidthValidator = new MarginWidthValidator();
  private static final ValueElementValidator paddingWidthValidator = new PaddingWidthValidator();
  private static final ValueElementValidator pauseValidator = new PauseValidator();
  private static final ValueElementValidator widthHeightValidator = new WidthHeightValidator();

  private static final ValueValidator borderRadiusValidator = new BorderRadiusValidator();
  private static final ValueValidator borderValidator = new BorderValidator();
  private static final ValueValidator counterValidator = new CounterValidator();

  public static ValueElementValidator getAngleValidator() {
    return angleValidator;
  }

  public static ValueElementValidator getAnyIdentifierValidator() {
    return anyIdentifierValidator;
  }

  public static ValueElementValidator getAutoValidator() {
    return autoValidator;
  }

  public static ValueElementValidator getBoxValidator() {
    return boxValidator;
  }


  public static ValueElementValidator getColorValidator() {
    return colorValidator;
  }

  public static ValueElementValidator getCommaDelimiterValidator() {
    return commaDelimiterValidator;
  }

  public static ValueElementValidator getFrequencyValidator() {
    return frequencyValidator;
  }

  public static ValueElementValidator getIntegerValidator() {
    return integerValidator;
  }

  public static ValueElementValidator getLengthValidator() {
    return lengthValidator;
  }

  public static ValueElementValidator getNoneValidator() {
    return noneValidator;
  }

  public static ValueElementValidator getNumberValidator() {
    return numberValidator;
  }

  public static ValueElementValidator getPageBreakValidator() {
    return pageBreakValidator;
  }

  public static ValueElementValidator getPercentageValidator() {
    return percentageValidator;
  }

  public static ValueElementValidator getPositiveIntegerValidator() {
    return positiveIntegerValidator;
  }

  public static ValueElementValidator getPositiveLengthValidator() {
    return positiveLengthValidator;
  }

  public static ValueElementValidator getPositiveNumberValidator() {
    return positiveNumberValidator;
  }

  public static ValueElementValidator getPositivePercentageValidator() {
    return positivePercentageValidator;
  }

  public static ValueElementValidator getPositiveTimeValidator() {
    return positiveTimeValidator;
  }

  public static ValueElementValidator getSizeValidator() {
    return sizeValidator;
  }

  public static ValueElementValidator getStringValidator() {
    return stringValidator;
  }

  public static ValueElementValidator getTimeValidator() {
    return timeValidator;
  }

  public static ValueElementValidator getUriValidator() {
    return uriValidator;
  }

  public static ValueElementValidator getBorderStyleValidator() {
    return borderStyleValidator;
  }

  public static ValueElementValidator getBorderWidthValidator() {
    return borderWidthValidator;
  }

  public static ValueElementValidator getCueValidator() {
    return cueValidator;
  }

  public static ValueElementValidator getImageValidator() {
    return imageValidator;
  }

  public static ValueElementValidator getMarginWidthValidator() {
    return marginWidthValidator;
  }

  public static ValueElementValidator getPaddingWidthValidator() {
    return paddingWidthValidator;
  }

  public static ValueElementValidator getPauseValidator() {
    return pauseValidator;
  }

  public static ValueElementValidator getWidthHeightValidator() {
    return widthHeightValidator;
  }

  public static ValueValidator getBorderRadiusValidator() {
    return borderRadiusValidator;
  }

  public static ValueValidator getBorderValidator() {
    return borderValidator;
  }

  public static ValueValidator getCounterValidator() {
    return counterValidator;
  }
}
