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
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.StandardPropertyFactory;
import org.sonar.plugins.css.api.tree.IdentifierTree;
import org.sonar.plugins.css.api.tree.PropertyTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class PropertyTreeImpl extends CssTree implements PropertyTree {

  private final IdentifierTree property;
  private final StandardProperty standardProperty;
  private final Vendor vendor;
  private final String hack;

  public PropertyTreeImpl(IdentifierTree property) {
    this.property = property;
    this.hack = setHack();
    this.vendor = setVendorPrefix();
    this.standardProperty = setStandardProperty();
  }

  @Override
  public Kind getKind() {
    return Kind.PROPERTY;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.singletonIterator(property);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitProperty(this);
  }

  @Override
  public IdentifierTree property() {
    return property;
  }

  @Override
  public StandardProperty standardProperty() {
    return standardProperty;
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
  public String hack() {
    return hack;
  }

  @Override
  public boolean isHacked() {
    return hack != null;
  }

  @Override
  public String unhackedFullName() {
    return (vendor != null ? vendor.getPrefix() : "") + standardProperty.getName();
  }

  private String setHack() {
    if (property.text().startsWith("*") || property.text().startsWith("_")) {
      return property.text().substring(0, 1);
    } else {
      return null;
    }
  }

  private Vendor setVendorPrefix() {
    for (Vendor v : Vendor.values()) {
      if (property.text().toLowerCase(Locale.ENGLISH).startsWith(v.getPrefix())) {
        return v;
      }
    }
    return null;
  }

  private StandardProperty setStandardProperty() {
    String propertyName = property.text();
    if (isHacked()) {
      propertyName = propertyName.substring(1);
    }
    if (isVendorPrefixed()) {
      propertyName = propertyName.substring(vendor.getPrefix().length());
    }
    return StandardPropertyFactory.getByName(propertyName);
  }

}
