/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013-2016 Tamas Kende and David RACODON
 * mailto: kende.tamas@gmail.com and david.racodon@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.css.model.property;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.*;

import org.sonar.css.model.property.standard.Border;

public class StandardPropertyFactory {

  private static Map<String, StandardProperty> ALL = new HashMap<>();

  static {
    try {
      ImmutableSet<ClassPath.ClassInfo> classInfos = ClassPath.from(Border.class.getClassLoader()).getTopLevelClasses("org.sonar.css.model.property.standard");
      StandardProperty standardProperty;
      for (ClassPath.ClassInfo classInfo : classInfos) {
        if (!"org.sonar.css.model.property.standard.package-info".equals(classInfo.getName())) {
          standardProperty = (StandardProperty) Class.forName(classInfo.getName()).newInstance();
          ALL.put(standardProperty.getName(), standardProperty);
        }
      }
    } catch (ClassNotFoundException | IOException | InstantiationException | IllegalAccessException e) {
      throw new IllegalStateException("CSS properties full list cannot be created.", e);
    }
  }

  private StandardPropertyFactory() {
  }

  public static StandardProperty getByName(String propertyName) {
    StandardProperty property = ALL.get(propertyName.toLowerCase(Locale.ENGLISH));
    return property != null ? property : new UnknownProperty(propertyName.toLowerCase(Locale.ENGLISH));
  }

  public static List<StandardProperty> getAll() {
    return new ArrayList<>(ALL.values());
  }

}
