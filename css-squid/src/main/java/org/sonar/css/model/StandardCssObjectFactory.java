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
package org.sonar.css.model;

import java.util.List;

import org.sonar.css.model.atrule.StandardAtRule;
import org.sonar.css.model.atrule.StandardAtRuleFactory;
import org.sonar.css.model.function.StandardFunction;
import org.sonar.css.model.function.StandardFunctionFactory;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.StandardPropertyFactory;

public class StandardCssObjectFactory {

  private StandardCssObjectFactory() {
  }

  public static List<StandardCssObject> createAll(Class<? extends StandardCssObject> clazz) {
    try {
      if (clazz.newInstance() instanceof StandardProperty) {
        return StandardPropertyFactory.createAll();
      } else if (clazz.newInstance() instanceof StandardAtRule) {
        return StandardAtRuleFactory.createAll();
      } else if (clazz.newInstance() instanceof StandardFunction) {
        return StandardFunctionFactory.createAll();
      } else {
        throw new IllegalArgumentException("Cannot get all Standard CSS Elements of type " + clazz.getName() + ". Unknown Standard CSS Element.");
      }
    } catch (InstantiationException | IllegalAccessException e) {
      throw new IllegalArgumentException("Cannot get all Standard CSS Elements of type " + clazz.getName(), e);
    }
  }

}
