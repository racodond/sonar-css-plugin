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
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.sonar.css.model.atrule.StandardAtRule;
import org.sonar.css.model.atrule.StandardAtRuleFactory;
import org.sonar.css.model.function.StandardFunction;
import org.sonar.css.model.function.StandardFunctionFactory;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.StandardPropertyFactory;
import org.sonar.css.model.pseudo.StandardPseudoComponent;
import org.sonar.css.model.pseudo.StandardPseudoComponentFactory;

public class StandardCssObjectFactory {

  private StandardCssObjectFactory() {
  }

  public static List<StandardCssObject> getStandardCssObjects(Class<? extends StandardCssObject> type, Predicate<StandardCssObject> filteringFunction) {
    try {
      List<? extends StandardCssObject> all;
      if (type.newInstance() instanceof StandardProperty) {
        all = StandardPropertyFactory.getAll();
      } else if (type.newInstance() instanceof StandardAtRule) {
        all = StandardAtRuleFactory.getAll();
      } else if (type.newInstance() instanceof StandardFunction) {
        all = StandardFunctionFactory.getAll();
      } else if (type.newInstance() instanceof StandardPseudoComponent) {
        all = StandardPseudoComponentFactory.getAll();
      } else {
        throw new IllegalArgumentException("Cannot get all CSS Standard Elements of type " + type.getName() + ". Unknown Standard CSS object");
      }
      return all
        .stream()
        .filter(filteringFunction)
        .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
        .collect(Collectors.toList());
    } catch (IllegalAccessException | InstantiationException e) {
      throw new IllegalArgumentException("Unknown Standard CSS object", e);
    }
  }

}
