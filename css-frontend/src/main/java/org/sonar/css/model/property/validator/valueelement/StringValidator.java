/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
 * mailto: david.racodon@gmail.com
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
package org.sonar.css.model.property.validator.valueelement;

import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.plugins.css.api.tree.css.StringTree;
import org.sonar.plugins.css.api.tree.Tree;

public class StringValidator implements ValueElementValidator {

  @Override
  public boolean isValid(Tree tree) {
    return tree instanceof StringTree;
  }

  @Override
  public String getValidatorFormat() {
    return "<string>";
  }

}
