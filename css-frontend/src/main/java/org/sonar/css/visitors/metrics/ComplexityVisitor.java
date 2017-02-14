/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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
package org.sonar.css.visitors.metrics;

import com.google.common.collect.ImmutableList;

import java.util.List;

import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.SubscriptionVisitor;

public class ComplexityVisitor extends SubscriptionVisitor {

  private int complexity;

  public ComplexityVisitor(Tree tree) {
    complexity = 0;
    scanTree(tree);
  }

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(
      Tree.Kind.CLASS_SELECTOR,
      Tree.Kind.ATTRIBUTE_SELECTOR,
      Tree.Kind.TYPE_SELECTOR,
      Tree.Kind.ID_SELECTOR,
      Tree.Kind.PSEUDO_SELECTOR,
      Tree.Kind.KEYFRAMES_SELECTOR,
      Tree.Kind.AT_RULE);
  }

  @Override
  public void visitNode(Tree tree) {
    complexity++;
  }

  public int getComplexity() {
    return complexity;
  }

}
