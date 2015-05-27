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

import java.util.List;

public class EnumValidator implements PropertyValueValidator {

  private List<String> allowedValues;

  public EnumValidator(List<String> allowedValues) {
    this.allowedValues = allowedValues;
  }

  public boolean isValid(AstNode astNode) {
    return allowedValues.contains(astNode.getTokenValue().toLowerCase());
  }

  public String getFormat() {
    StringBuilder format = new StringBuilder("");
    for (String allowedValue : allowedValues) {
      if (format.length() != 0) {
        format.append(" | ");
      }
      format.append(allowedValue);
    }
    return format.toString();
  }
}
