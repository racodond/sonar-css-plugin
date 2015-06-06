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
package org.sonar.css.checks.validators.property.animation;

import com.google.common.collect.ImmutableList;
import org.sonar.css.checks.utils.CssValueElement;
import org.sonar.css.checks.validators.PropertyValueElementValidator;
import org.sonar.css.checks.validators.valueelement.FunctionValidator;
import org.sonar.css.checks.validators.valueelement.IdentifierValidator;

import javax.annotation.Nonnull;

public class AnimationTimingFunctionValidator implements PropertyValueElementValidator {

  IdentifierValidator identifierValidator = new IdentifierValidator(ImmutableList.of("ease", "linear", "ease-in", "ease-out", "ease-in-out", "step-start", "step-end"));
  FunctionValidator functionValidator = new FunctionValidator(ImmutableList.of("steps", "cubic-bezier"));

  @Override
  public boolean isValid(@Nonnull CssValueElement cssValueElement) {
    return identifierValidator.isValid(cssValueElement) || functionValidator.isValid(cssValueElement);
  }

  @Nonnull
  @Override
  public String getValidatorFormat() {
    return identifierValidator.getValidatorFormat() + " | " + functionValidator.getValidatorFormat();
  }

}
