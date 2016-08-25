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
import java.util.Locale;

import org.sonar.css.model.Vendor;
import org.sonar.css.model.pseudo.pseudoidentifier.StandardPseudoIdentifier;
import org.sonar.css.model.pseudo.pseudoidentifier.StandardPseudoIdentifierFactory;
import org.sonar.plugins.css.api.tree.IdentifierTree;
import org.sonar.plugins.css.api.tree.PseudoIdentifierTree;
import org.sonar.plugins.css.api.tree.SyntaxToken;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class PseudoIdentifierTreeImpl extends CssTree implements PseudoIdentifierTree {

  private final SyntaxToken prefix;
  private final IdentifierTree identifier;
  private final Vendor vendor;
  private final StandardPseudoIdentifier standardPseudoIdentifier;

  public PseudoIdentifierTreeImpl(SyntaxToken prefix, IdentifierTree identifier) {
    this.prefix = prefix;
    this.identifier = identifier;

    this.vendor = setVendor();
    this.standardPseudoIdentifier = setStandardPseudoIdentifier();
  }

  @Override
  public Kind getKind() {
    return Kind.PSEUDO_IDENTIFIER;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(prefix, identifier);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitPseudoIdentifier(this);
  }

  @Override
  public SyntaxToken prefix() {
    return prefix;
  }

  @Override
  public IdentifierTree identifier() {
    return identifier;
  }

  @Override
  public StandardPseudoIdentifier standardPseudoIdentifier() {
    return standardPseudoIdentifier;
  }

  @Override
  public boolean isVendorPrefixed() {
    return vendor != null;
  }

  @Override
  public Vendor vendor() {
    return vendor;
  }

  private Vendor setVendor() {
    for (Vendor vendor : Vendor.values()) {
      if (identifier.text().toLowerCase(Locale.ENGLISH).startsWith(vendor.getPrefix())) {
        return vendor;
      }
    }
    return null;
  }

  private StandardPseudoIdentifier setStandardPseudoIdentifier() {
    String name = identifier.text();
    if (isVendorPrefixed()) {
      name = name.substring(vendor.getPrefix().length());
    }
    return StandardPseudoIdentifierFactory.getByName(name);
  }

}
