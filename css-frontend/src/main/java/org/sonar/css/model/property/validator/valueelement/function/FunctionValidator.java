/*
 * SonarQube CSS / SCSS / Less Analyzer
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
package org.sonar.css.model.property.validator.valueelement.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.sonar.css.model.function.StandardFunction;
import org.sonar.css.model.function.standard.Expression;
import org.sonar.css.model.function.standard.Var;
import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.plugins.css.api.tree.css.FunctionTree;
import org.sonar.plugins.css.api.tree.Tree;

public class FunctionValidator implements ValueElementValidator {

  private final List<Class<? extends StandardFunction>> allowedFunctions = new ArrayList<>();
  private final List<Class<? extends StandardFunction>> alwaysAllowedFunctions = Arrays.asList(Var.class, Expression.class);

  public FunctionValidator(Class<? extends StandardFunction>... allowedFunctions) {
    this.allowedFunctions.addAll(Arrays.asList(allowedFunctions));
  }

  @Override
  public boolean isValid(Tree tree) {
    return tree instanceof FunctionTree
      && (((FunctionTree) tree).isVendorPrefixed()
        || allowedFunctions.contains(((FunctionTree) tree).standardFunction().getClass())
        || alwaysAllowedFunctions.contains(((FunctionTree) tree).standardFunction().getClass()));
  }

  @Override
  public String getValidatorFormat() {
    return "<function>("
      + allowedFunctions.stream()
        .map(c -> {
          try {
            return c.newInstance().getName();
          } catch (IllegalAccessException | InstantiationException e) {
            throw new IllegalStateException("Cannot create instance of function " + c.getName(), e);
          }
        })
        .sorted()
        .collect(Collectors.joining(" | "))
      + ")";
  }

}
