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
import org.sonar.css.model.Unit;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.DimensionTree;
import org.sonar.plugins.css.api.tree.css.NumberTree;
import org.sonar.plugins.css.api.tree.css.PercentageTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.regex.Pattern;

/**
 * See https://drafts.csswg.org/css-values-3/#lengths:
 * "However, for zero lengths the unit identifier is optional (i.e. can be syntactically represented as the 0 )."
 * For lengths only, not for other dimensions such as angle, time, etc.
 * See https://developer.mozilla.org/en-US/docs/Web/CSS/time#Summary for example.
 */
@Rule(
  key = "zero-units",
  name = "Units for zero length values should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.CONVENTION, Tags.PERFORMANCE})
@SqaleConstantRemediation("2min")
@ActivatedByDefault
public class UnitForZeroValueCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitPercentage(PercentageTree tree) {
    if (isZeroValue(tree.value())
      && (tree.parent() == null || !tree.parent().is(Tree.Kind.KEYFRAMES_SELECTOR))) {
      addIssue(tree.percentageSymbol());
    }
    super.visitPercentage(tree);
  }

  @Override
  public void visitDimension(DimensionTree tree) {
    if (isZeroValue(tree.value()) && isLength(tree)) {
      addIssue(tree.unit());
    }
    super.visitDimension(tree);
  }

  private void addIssue(Tree tree) {
    addPreciseIssue(tree, "Remove the unit for this zero length.");
  }

  private boolean isLength(DimensionTree tree) {
    return Unit.LENGTH_UNITS.contains(tree.unit().text().toLowerCase());
  }

  private boolean isZeroValue(NumberTree tree) {
    return Pattern.compile("(0\\.|\\.)?0+").matcher(tree.value().text()).matches();
  }

}
