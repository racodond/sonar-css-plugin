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

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.sonar.plugins.css.api.tree.ImportantTree;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.ValueTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class ValueTreeImpl extends CssTree implements ValueTree {

  private final List<Tree> allValueElements;
  private final List<Tree> sanitizedValueElements;

  public ValueTreeImpl(List<Tree> allValueElements) {
    this.allValueElements = allValueElements;
    this.sanitizedValueElements = buildSanitizedList();
  }

  @Override
  public Kind getKind() {
    return Kind.VALUE;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return allValueElements.iterator();
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitValue(this);
  }

  @Override
  public List<Tree> sanitizedValueElements() {
    return sanitizedValueElements;
  }

  @Override
  public List<Tree> valueElements() {
    return allValueElements;
  }

  public <T extends Tree> List<T> valueElementsOfType(Class<T> treeType) {
    return allValueElements.stream()
      .filter(e -> treeType.isAssignableFrom(e.getClass()))
      .map(treeType::cast)
      .collect(Collectors.toList());
  }

  public <T extends Tree> Optional<T> firstValueElementOfType(Class<T> treeType) {
    return allValueElements.stream()
      .filter(e -> treeType.isAssignableFrom(e.getClass()))
      .map(treeType::cast)
      .findFirst();
  }

  private List<Tree> buildSanitizedList() {
    return allValueElements.stream()
      .filter(e -> !ImportantTree.class.isAssignableFrom(e.getClass()))
      .collect(Collectors.toList());
  }

}
