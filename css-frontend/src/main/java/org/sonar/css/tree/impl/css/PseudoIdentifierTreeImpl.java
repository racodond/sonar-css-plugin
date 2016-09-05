/*
 * SonarQube CSS / Less Plugin
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
package org.sonar.css.tree.impl.css;

import com.google.common.collect.Iterators;

import java.util.Iterator;
import java.util.Locale;

import org.sonar.css.model.Vendor;
import org.sonar.css.model.pseudo.pseudoidentifier.StandardPseudoIdentifier;
import org.sonar.css.model.pseudo.pseudoidentifier.StandardPseudoIdentifierFactory;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.css.PseudoIdentifierTree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class PseudoIdentifierTreeImpl extends TreeImpl implements PseudoIdentifierTree {

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
    for (Vendor v : Vendor.values()) {
      if (identifier.text().toLowerCase(Locale.ENGLISH).startsWith(v.getPrefix())) {
        return v;
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
