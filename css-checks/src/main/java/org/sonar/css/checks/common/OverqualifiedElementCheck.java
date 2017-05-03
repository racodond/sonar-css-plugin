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
package org.sonar.css.checks.common;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.ClassSelectorTree;
import org.sonar.plugins.css.api.tree.css.CompoundSelectorTree;
import org.sonar.plugins.css.api.tree.css.StyleSheetTree;
import org.sonar.plugins.css.api.tree.css.TypeSelectorTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "overqualified-elements",
  name = "Name of overqualified element should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.DESIGN, Tags.PERFORMANCE})
@SqaleConstantRemediation("1h")
@ActivatedByDefault
public class OverqualifiedElementCheck extends DoubleDispatchVisitorCheck {

  private final Map<String, List<TypeSelectorTree>> classSelectorMap = new HashMap<>();

  @Override
  public void visitStyleSheet(StyleSheetTree tree) {
    classSelectorMap.clear();
    super.visitStyleSheet(tree);
    addIssues();
  }

  @Override
  public void visitCompoundSelector(CompoundSelectorTree tree) {
    if (tree.selectors().size() > 1
      && tree.selectors().get(0) instanceof TypeSelectorTree
      && tree.selectors().get(1) instanceof ClassSelectorTree) {

      String className = ((ClassSelectorTree) tree.selectors().get(1)).className().text();
      TypeSelectorTree typeSelector = (TypeSelectorTree) tree.selectors().get(0);

      if (classSelectorMap.get(className) == null) {
        classSelectorMap.put(className, Lists.newArrayList(typeSelector));
      } else {
        classSelectorMap.get(className).add(typeSelector);
      }
    }
    super.visitCompoundSelector(tree);
  }

  private void addIssues() {
    classSelectorMap.entrySet().stream()
      .filter(e -> e.getValue().size() == 1)
      .forEach(e -> addPreciseIssue(e.getValue().get(0), "Remove the name of this overqualified element."));
  }

}
