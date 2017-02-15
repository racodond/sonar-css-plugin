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
package org.sonar.css.checks.common;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.css.model.Color;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.css.ValueTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Rule(
  key = "named-color",
  name = "Named colors should not be used",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION})
@SqaleConstantRemediation("5min")
public class NamedColorCheck extends DoubleDispatchVisitorCheck {

  private static final List<String> COLORS = Stream.concat(Color.SVG_COLORS.stream(), Color.CSS4_COLORS.stream()).collect(Collectors.toList());

  @Override
  public void visitValue(ValueTree valueTree) {
    for (Tree tree : valueTree.valueElements()) {
      if (tree.is(Tree.Kind.IDENTIFIER)
        && COLORS.contains(((IdentifierTree) tree).text().toLowerCase())) {
        addPreciseIssue(tree, "Replace this named color.");
      }
    }
    super.visitValue(valueTree);
  }

}
