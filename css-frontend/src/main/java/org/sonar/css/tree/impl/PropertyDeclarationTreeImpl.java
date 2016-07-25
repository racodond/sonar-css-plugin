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
package org.sonar.css.tree.impl;

import com.google.common.collect.Iterators;

import java.util.Iterator;
import java.util.List;

import org.sonar.css.model.property.validator.Validator;
import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.css.model.property.validator.valueelement.function.FunctionValidator;
import org.sonar.plugins.css.api.tree.*;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class PropertyDeclarationTreeImpl extends CssTree implements PropertyDeclarationTree {

  private final PropertyTree property;
  private final SyntaxToken colon;
  private final ValueTree value;

  public PropertyDeclarationTreeImpl(PropertyTree property, SyntaxToken colon, ValueTree value) {
    this.property = property;
    this.colon = colon;
    this.value = value;
  }

  @Override
  public Kind getKind() {
    return Kind.PROPERTY_DECLARATION;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(property, colon, value);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitPropertyDeclaration(this);
  }

  @Override
  public PropertyTree property() {
    return property;
  }

  @Override
  public SyntaxToken colon() {
    return colon;
  }

  @Override
  public ValueTree value() {
    return value;
  }

  @Override
  public boolean isValid() {
    List<Validator> validators = property.standardProperty().getValidators();
    List<Tree> valueElements = value.sanitizedValueElements();
    int numberOfValueElements = valueElements.size();


    if (validators.isEmpty()) {
      return true;
    }

    if (numberOfValueElements == 0) {
      return false;
    }

    if (hasOnlyPropertyValueElementValidators() && numberOfValueElements > 1) {
      return false;
    }

    for (Validator validator : validators) {
      if (validator instanceof ValueElementValidator
        && ((ValueElementValidator) validator).isValid(valueElements.get(0))) {
        return true;
      }
      if (validator instanceof ValueValidator
        && ((ValueValidator) validator).isValid(value)) {
        return true;
      }
    }

    return new IdentifierValidator("inherit", "initial", "unset").isValid(valueElements.get(0))
      || new FunctionValidator("var").isValid(valueElements.get(0));
  }

  private boolean hasOnlyPropertyValueElementValidators() {
    for (Validator validator : property.standardProperty().getValidators()) {
      if (!(validator instanceof ValueElementValidator)) {
        return false;
      }
    }
    return true;
  }

}
