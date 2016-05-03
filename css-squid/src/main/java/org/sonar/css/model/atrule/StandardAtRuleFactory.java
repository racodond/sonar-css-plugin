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
package org.sonar.css.model.atrule;

import com.google.common.base.CaseFormat;

import java.util.Locale;

public class StandardAtRuleFactory {

  public static StandardAtRule createStandardAtRule(String atRuleName) {
    try {
      String className = getClassNameFromAtRuleName(atRuleName);
      Class clazz = Class.forName("org.sonar.css.model.atrule.standard." + className);
      return (StandardAtRule) clazz.newInstance();
    } catch (ClassNotFoundException e) {
      return new UnknownAtRule(atRuleName);
    } catch (IllegalAccessException | InstantiationException e) {
      throw new IllegalStateException("CSS at rule for '" + atRuleName + "' cannot be created.", e);
    }
  }

  private static String getClassNameFromAtRuleName(String atRuleName) {
    return CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, atRuleName.toLowerCase(Locale.ENGLISH));
  }

}
