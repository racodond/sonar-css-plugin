/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2017 David RACODON
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

import java.util.Iterator;
import javax.annotation.Nullable;

import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class AttributeSelectorTreeImpl extends TreeImpl implements AttributeSelectorTree {

  private final SyntaxToken openBracket;
  private final NamespaceTree namespace;
  private final IdentifierTree attribute;
  private final AttributeMatcherExpressionTree matcherExpression;
  private final SyntaxToken closeBracket;

  public AttributeSelectorTreeImpl(SyntaxToken openBracket, @Nullable NamespaceTree namespace, IdentifierTree attribute, @Nullable AttributeMatcherExpressionTree matcherExpression,
    SyntaxToken closeBracket) {
    this.openBracket = openBracket;
    this.namespace = namespace;
    this.attribute = attribute;
    this.matcherExpression = matcherExpression;
    this.closeBracket = closeBracket;
  }

  @Override
  public Kind getKind() {
    return Kind.ATTRIBUTE_SELECTOR;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(openBracket, namespace, attribute, matcherExpression, closeBracket);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitAttributeSelector(this);
  }

  @Override
  public SyntaxToken openBracket() {
    return openBracket;
  }

  @Override
  public SyntaxToken closeBracket() {
    return closeBracket;
  }

  @Override
  @Nullable
  public AttributeMatcherExpressionTree matcherExpression() {
    return matcherExpression;
  }

  @Override
  @Nullable
  public NamespaceTree namespace() {
    return namespace;
  }

  @Override
  public IdentifierTree attribute() {
    return attribute;
  }

}
