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
package org.sonar.css.model.pseudo.pseudofunction;

import com.google.common.collect.ImmutableSet;

import java.util.*;

import org.sonar.css.model.pseudo.pseudofunction.standard.*;

public class StandardPseudoFunctionFactory {

  private static final Set<Class> ALL_PSEUDO_FUNCTION_CLASSES = ImmutableSet.of(
    Any.class,
    Current.class,
    Dir.class,
    Drop.class,
    Has.class,
    Lang.class,
    Matches.class,
    Not.class,
    NthChild.class,
    NthColumn.class,
    NthLastChild.class,
    NthLastColumn.class,
    NthLastOfType.class,
    NthOfType.class);

  private static final Map<String, StandardPseudoFunction> ALL = new HashMap<>();

  static {
    try {
      StandardPseudoFunction standardFunction;
      for (Class clazz : ALL_PSEUDO_FUNCTION_CLASSES) {
        standardFunction = (StandardPseudoFunction) clazz.newInstance();
        ALL.put(standardFunction.getName(), standardFunction);
      }
    } catch (InstantiationException | IllegalAccessException e) {
      throw new IllegalStateException("CSS pseudo-function full list cannot be created.", e);
    }
  }

  private StandardPseudoFunctionFactory() {
  }

  public static StandardPseudoFunction getByName(String functionName) {
    StandardPseudoFunction standardPseudoFunction = ALL.get(functionName.toLowerCase(Locale.ENGLISH));
    return standardPseudoFunction != null ? standardPseudoFunction : new UnknownPseudoFunction(functionName);
  }

  public static List<StandardPseudoFunction> getAll() {
    return new ArrayList<>(ALL.values());
  }

}
