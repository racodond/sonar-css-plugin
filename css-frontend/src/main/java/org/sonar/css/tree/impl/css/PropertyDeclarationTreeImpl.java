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
package org.sonar.css.tree.impl.css;

import com.google.common.collect.Iterators;
import org.sonar.css.model.function.standard.Expression;
import org.sonar.css.model.function.standard.Var;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.validator.Validator;
import org.sonar.css.model.property.validator.ValueElementValidator;
import org.sonar.css.model.property.validator.ValueValidator;
import org.sonar.css.model.property.validator.valueelement.IdentifierValidator;
import org.sonar.css.model.property.validator.valueelement.function.FunctionValidator;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.tree.less.LessEscapingTree;
import org.sonar.plugins.css.api.tree.less.LessOperatorTree;
import org.sonar.plugins.css.api.tree.less.LessVariableTree;
import org.sonar.plugins.css.api.tree.scss.ScssOperatorTree;
import org.sonar.plugins.css.api.tree.scss.ScssVariableTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class PropertyDeclarationTreeImpl extends TreeImpl implements PropertyDeclarationTree {

  private final PropertyTree property;
  private final SyntaxToken colon;
  private final ValueTree value;
  private final SyntaxToken semicolon;

  public PropertyDeclarationTreeImpl(PropertyTree property, SyntaxToken colon, ValueTree value, @Nullable SyntaxToken semicolon) {
    this.property = property;
    this.colon = colon;
    this.value = value;
    this.semicolon = semicolon;
  }

  @Override
  public Kind getKind() {
    return Kind.PROPERTY_DECLARATION;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(property, colon, value, semicolon);
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
  @Nullable
  public SyntaxToken semicolon() {
    return semicolon;
  }

  @Override
  public boolean isValid(String language) {
    List<Validator> validators = property.standardProperty().getValidators();
    List<Tree> valueElements = value.sanitizedValueElements();
    int numberOfValueElements = valueElements.size();

    if (validators.isEmpty()) {
      return true;
    }

    if ("scss".equals(language)
      && (doesValueContainScssElements(value.childrenIterator()) || property.isScssNested())) {
      return true;
    }

    if ("less".equals(language)
      && doesValueContainLessElements(value.childrenIterator())) {
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

    return new IdentifierValidator(StandardProperty.COMMON_VALUES).isValid(valueElements.get(0))
      || new FunctionValidator(Var.class, Expression.class).isValid(valueElements.get(0));
  }

  private boolean hasOnlyPropertyValueElementValidators() {
    for (Validator validator : property.standardProperty().getValidators()) {
      if (!(validator instanceof ValueElementValidator)) {
        return false;
      }
    }
    return true;
  }

  private boolean doesValueContainLessElements(Iterator<Tree> iterator) {
    while (iterator.hasNext()) {
      Tree next = iterator.next();
      if (next != null && !(next instanceof SyntaxToken)
        && (isLessElement(next) || doesValueContainLessElements(next.childrenIterator()))) {
        return true;
      }
    }
    return false;
  }

  private boolean doesValueContainScssElements(Iterator<Tree> iterator) {
    while (iterator.hasNext()) {
      Tree next = iterator.next();
      if (next != null && !(next instanceof SyntaxToken)
        && (isScssElement(next) || doesValueContainScssElements(next.childrenIterator()))) {
        return true;
      }
    }
    return false;
  }

  private static boolean isLessElement(Tree tree) {
    return tree instanceof LessVariableTree
      || (tree instanceof IdentifierTree && ((IdentifierTree) tree).isLessInterpolated())
      || tree instanceof LessEscapingTree
      || (tree instanceof FunctionTree && ((FunctionTree) tree).standardFunction().isLess())
      || tree instanceof LessOperatorTree;
  }

  private static boolean isScssElement(Tree tree) {
    return tree instanceof ScssVariableTree
      || (tree instanceof IdentifierTree && ((IdentifierTree) tree).isScssInterpolated())
      || tree instanceof FunctionTree
      || tree instanceof ScssOperatorTree;
  }

  public static boolean isScssOrLessElement(Tree tree) {
    return isScssElement(tree) || isLessElement(tree);
  }

}
