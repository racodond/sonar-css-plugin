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

import org.sonar.css.checks.validators.base.BasePropertyValueValidatorFactory;
import org.sonar.css.checks.validators.base.ColorValidator;
import org.sonar.css.checks.validators.property.BorderStyleValidator;
import org.sonar.css.checks.validators.property.BorderWidthValidator;
import org.sonar.css.checks.validators.property.FontSizeValidator;
import org.sonar.css.checks.validators.property.MarginWidthValidator;
import org.sonar.css.checks.validators.property.OutlineStyleValidator;
import org.sonar.css.checks.validators.property.PaddingWidthValidator;
import org.sonar.css.checks.validators.property.PageBreakValidator;
import org.sonar.css.checks.validators.property.PauseValidator;
import org.sonar.css.checks.validators.property.PitchValidator;

public class PropertyValueValidatorFactory extends BasePropertyValueValidatorFactory {

  private static final PropertyValueValidator borderStyleValidator = new BorderStyleValidator();
  private static final PropertyValueValidator borderWidthValidator = new BorderWidthValidator();
  private static final PropertyValueValidator colorValidator = new ColorValidator();
  private static final PropertyValueValidator fontSizeValidator = new FontSizeValidator();
  private static final PropertyValueValidator marginWidthValidator = new MarginWidthValidator();
  private static final PropertyValueValidator outlineStyleValidator = new OutlineStyleValidator();
  private static final PropertyValueValidator paddingWidthValidator = new PaddingWidthValidator();
  private static final PropertyValueValidator pitchValidator = new PitchValidator();
  private static final PropertyValueValidator pageBreakValidator = new PageBreakValidator();
  private static final PropertyValueValidator pauseValidator = new PauseValidator();

  public static PropertyValueValidator getBorderStyleValidator() {
    return borderStyleValidator;
  }

  public static PropertyValueValidator getColorValidator() {
    return colorValidator;
  }

  public static PropertyValueValidator getBorderWidthValidator() {
    return borderWidthValidator;
  }

  public static PropertyValueValidator getFontSizeValidator() {
    return fontSizeValidator;
  }

  public static PropertyValueValidator getMarginWidthValidator() {
    return marginWidthValidator;
  }

  public static PropertyValueValidator getOutlineStyleValidator() {
    return outlineStyleValidator;
  }

  public static PropertyValueValidator getPaddingWidthValidator() {
    return paddingWidthValidator;
  }

  public static PropertyValueValidator getPitchValidator() {
    return pitchValidator;
  }

  public static PropertyValueValidator getPageBreakValidator() {
    return pageBreakValidator;
  }

  public static PropertyValueValidator getPauseValidator() {
    return pauseValidator;
  }

}
