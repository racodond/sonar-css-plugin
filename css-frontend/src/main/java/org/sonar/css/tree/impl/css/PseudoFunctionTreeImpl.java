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
import org.sonar.css.model.Vendor;
import org.sonar.css.model.pseudo.pseudofunction.StandardPseudoFunction;
import org.sonar.css.model.pseudo.pseudofunction.StandardPseudoFunctionFactory;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.css.ParametersTree;
import org.sonar.plugins.css.api.tree.css.PseudoFunctionTree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import java.util.Iterator;
import java.util.Locale;

public class PseudoFunctionTreeImpl extends TreeImpl implements PseudoFunctionTree {

  private final SyntaxToken prefix;
  private final IdentifierTree function;
  private final ParametersTree parameters;
  private final Vendor vendor;
  private final StandardPseudoFunction standardFunction;

  public PseudoFunctionTreeImpl(SyntaxToken prefix, IdentifierTree function, ParametersTree parameters) {
    this.prefix = prefix;
    this.function = function;
    this.parameters = parameters;

    this.vendor = setVendor();
    this.standardFunction = setStandardFunction();
  }

  @Override
  public Kind getKind() {
    return Kind.PSEUDO_FUNCTION;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(prefix, function, parameters);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitPseudoFunction(this);
  }

  @Override
  public SyntaxToken prefix() {
    return prefix;
  }

  @Override
  public IdentifierTree function() {
    return function;
  }

  @Override
  public boolean isVendorPrefixed() {
    return vendor != null;
  }

  @Override
  public Vendor vendor() {
    return vendor;
  }

  @Override
  public StandardPseudoFunction standardFunction() {
    return standardFunction;
  }

  @Override
  public ParametersTree parameters() {
    return parameters;
  }

  private Vendor setVendor() {
    for (Vendor v : Vendor.values()) {
      if (function.text().toLowerCase(Locale.ENGLISH).startsWith(v.getPrefix())) {
        return v;
      }
    }
    return null;
  }

  private StandardPseudoFunction setStandardFunction() {
    String name = function.text();
    if (isVendorPrefixed()) {
      name = name.substring(vendor.getPrefix().length());
    }
    return StandardPseudoFunctionFactory.getByName(name);
  }

}
