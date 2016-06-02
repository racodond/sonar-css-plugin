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
package org.sonar.css.model.property;

import com.google.common.base.CaseFormat;

import java.util.Locale;

public class StandardPropertyFactory {

  private StandardPropertyFactory() {
  }

  public static StandardProperty createStandardProperty(String propertyName) {
    try {
      String className = getClassNameFromPropertyName(propertyName);
      Class clazz = Class.forName("org.sonar.css.model.property.standard." + className);
      return (StandardProperty) clazz.newInstance();
    } catch (ClassNotFoundException e) {
      return new UnknownProperty(propertyName);
    } catch (IllegalAccessException | InstantiationException e) {
      throw new IllegalStateException("CSS property for '" + propertyName + "' cannot be created.", e);
    }
  }

  private static String getClassNameFromPropertyName(String propertyName) {
    return CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, propertyName.toLowerCase(Locale.ENGLISH));
  }

}
