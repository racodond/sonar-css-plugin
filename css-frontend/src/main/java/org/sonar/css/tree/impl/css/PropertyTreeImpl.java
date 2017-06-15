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
import org.sonar.css.model.Vendor;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.StandardPropertyFactory;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.css.PropertyTree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Locale;

public class PropertyTreeImpl extends TreeImpl implements PropertyTree {

  private final IdentifierTree property;
  private final SyntaxToken lessMerge;
  private StandardProperty standardProperty;
  private Vendor vendor;
  private String hack;
  private boolean scssNamespace;

  public PropertyTreeImpl(IdentifierTree property, @Nullable SyntaxToken lessMerge) {
    this.property = property;
    this.lessMerge = lessMerge;
    setProperty(property.text());
  }

  @Override
  public Kind getKind() {
    return Kind.PROPERTY;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(property, lessMerge);
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
  public boolean isScssNamespace() {
    return scssNamespace;
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
  @Nullable
  public SyntaxToken lessMerge() {
    return lessMerge;
  }

  @Override
  public boolean isLessMerge() {
    return lessMerge != null;
  }

  @Override
  public String unhackedFullName() {
    return (vendor != null ? vendor.getPrefix() : "") + standardProperty.getName();
  }

  @Override
  public void setProperty(String propertyName) {
    this.hack = setHack(propertyName);
    this.vendor = setVendorPrefix(propertyName);
    this.standardProperty = setStandardProperty(propertyName, vendor);
  }

  @Override
  public void setScssNamespace(boolean scssNamespace) {
    this.scssNamespace = scssNamespace;
  }

  private StandardProperty setStandardProperty(String propertyName, @Nullable Vendor vendor) {
    String actualPropertyName = propertyName;
    if (isHacked()) {
      actualPropertyName = propertyName.substring(1);
    }
    if (vendor != null) {
      actualPropertyName = propertyName.substring(vendor.getPrefix().length());
    }
    return StandardPropertyFactory.getByName(actualPropertyName);
  }

  private String setHack(String propertyName) {
    if (propertyName.startsWith("*") || propertyName.startsWith("_")) {
      return propertyName.substring(0, 1);
    } else {
      return null;
    }
  }

  @Nullable
  private Vendor setVendorPrefix(String propertyName) {
    for (Vendor v : Vendor.values()) {
      if (propertyName.toLowerCase(Locale.ENGLISH).startsWith(v.getPrefix())) {
        return v;
      }
    }
    return null;
  }

}
