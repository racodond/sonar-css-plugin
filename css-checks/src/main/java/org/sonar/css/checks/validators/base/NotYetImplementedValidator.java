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
package org.sonar.css.checks.validators.base;

import com.sonar.sslr.api.AstNode;

import javax.annotation.Nonnull;
import org.sonar.css.checks.validators.PropertyValueValidator;

/** 
 * Validator to be assigned to all checks whose validation is not yet implemented
 */
public class NotYetImplementedValidator implements PropertyValueValidator {

  @Override
  public boolean isPropertyValueValid(@Nonnull AstNode astNode) {
    return true;
  }

  @Override
  @Nonnull
  public String getValidatorFormat() {
    return "<not yet implemented>";
  }
}
