/*
 * SonarQube CSS / SCSS / Less Analyzer
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
package org.sonar.css.checks.common;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.css.model.atrule.standard.FontFace;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.standard.FontFamily;
import org.sonar.css.tree.impl.SeparatedList;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import javax.annotation.Nullable;
import java.util.List;

@Rule(
  key = "font-family-not-ending-with-generic-font-family",
  name = "font-family properties should end with a generic font family",
  priority = Priority.CRITICAL,
  tags = {Tags.BUG})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class FontFamilyNotEndingWithGenericFontFamilyCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitPropertyDeclaration(PropertyDeclarationTree tree) {
    if (propertyToBeChecked(tree)) {
      Tree lastTree = getLastTree(tree.value());
      if (!isPotentialGenericFamily(lastTree)) {
        addPreciseIssue(tree.value(), "Add a generic font family at the end of the declaration.");
      }
      super.visitPropertyDeclaration(tree);
    }
  }

  @Nullable
  private Tree getLastTree(ValueTree valueTree) {
    List<Tree> sanitizedValueElements = valueTree.sanitizedValueElements();
    if (!sanitizedValueElements.isEmpty()) {
      Tree lastValueTree = sanitizedValueElements.get(sanitizedValueElements.size() - 1);
      if (lastValueTree.is(Tree.Kind.VALUE_COMMA_SEPARATED_LIST)) {
        return getLastTreeOfValueCommaSeparatedListTree((ValueCommaSeparatedListTree) lastValueTree);
      } else {
        return lastValueTree;
      }
    }
    return null;
  }

  @Nullable
  private Tree getLastTreeOfValueCommaSeparatedListTree(ValueCommaSeparatedListTree tree) {
    SeparatedList<ValueTree, DelimiterTree> listOfValues = tree.values();
    List<Tree> lastValue = listOfValues.get(listOfValues.size() - 1).sanitizedValueElements();
    if (!lastValue.isEmpty()) {
      return lastValue.get(lastValue.size() - 1);
    } else {
      return null;
    }
  }

  private boolean isPotentialGenericFamily(@Nullable Tree tree) {
    if (tree == null) {
      return false;
    }

    if (!tree.is(Tree.Kind.IDENTIFIER, Tree.Kind.LESS_VARIABLE, Tree.Kind.SCSS_VARIABLE, Tree.Kind.VARIABLE, Tree.Kind.FUNCTION)) {
      return false;
    }

    if (tree.is(Tree.Kind.IDENTIFIER)) {
      IdentifierTree identifier = (IdentifierTree) tree;
      if (!identifier.isInterpolated()
        && !FontFamily.GENERIC_FAMILY_NAMES.contains(identifier.text().toLowerCase())
        && !StandardProperty.COMMON_VALUES.contains(identifier.text().toLowerCase())) {
        return false;
      }
    }
    return true;
  }

  private boolean propertyToBeChecked(PropertyDeclarationTree tree) {
    if (!(tree.property().standardProperty() instanceof FontFamily)) {
      return false;
    }

    if (tree.parent() != null
      && tree.parent().parent() != null
      && tree.parent().parent() instanceof AtRuleTree
      && ((AtRuleTree) tree.parent().parent()).standardAtRule() instanceof FontFace) {
      return false;
    }

    return true;
  }

}
