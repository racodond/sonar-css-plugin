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
package org.sonar.css.model;

import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.AstNode;

import java.util.List;

import org.sonar.css.model.property.validator.Validator;
import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.css.model.property.validator.valueelement.function.FunctionValidator;
import org.sonar.css.parser.CssGrammar;

public class Declaration {

  private final Property property;
  private final Value value;

  public Declaration(AstNode declarationNode) {
    this.property = new Property(declarationNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue());
    this.value = new Value(declarationNode.getFirstChild(CssGrammar.VALUE));
  }

  public Property getProperty() {
    return property;
  }

  public Value getValue() {
    return value;
  }

  public boolean isValid() {
    List<Validator> validators = property.getStandardProperty().getValidators();

    if (validators.isEmpty()) {
      return true;
    }

    if (value.getNumberOfValueElements() == 0) {
      return false;
    }

    if (hasOnlyPropertyValueElementValidators() && value.getNumberOfValueElements() > 1) {
      return false;
    }

    for (Validator validator : validators) {
      if (validator instanceof ValueElementValidator
        && ((ValueElementValidator) validator).isValid(value.getValueElements().get(0))) {
        return true;
      }
      if (validator instanceof ValueValidator
        && ((ValueValidator) validator).isValid(value)) {
        return true;
      }
    }

    return new IdentifierValidator(ImmutableList.of("inherit", "initial", "unset")).isValid(value.getValueElements().get(0))
      || new FunctionValidator(ImmutableList.of("var")).isValid(value.getValueElements().get(0));
  }

  private boolean hasOnlyPropertyValueElementValidators() {
    for (Validator validator : property.getStandardProperty().getValidators()) {
      if (!(validator instanceof ValueElementValidator)) {
        return false;
      }
    }
    return true;
  }

}
