/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
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

import com.google.common.annotations.VisibleForTesting;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.NumberTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "number-precision",
  name = "Number precision should not be too high",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION, Tags.UNDERSTANDABILITY})
@SqaleConstantRemediation("5min")
public class NumberPrecisionCheck extends DoubleDispatchVisitorCheck {

  private static final int DEFAULT_NUMBER_PRECISION = 3;

  @RuleProperty(
    key = "precision",
    description = "The maximum precision that is allowed",
    defaultValue = DEFAULT_NUMBER_PRECISION + "")
  private int precision = DEFAULT_NUMBER_PRECISION;

  @Override
  public void visitNumber(NumberTree tree) {
    int actualPrecision = computePrecision(tree.text());
    if (actualPrecision > precision) {
      createIssue(tree, actualPrecision);
    }
    super.visitNumber(tree);
  }

  @VisibleForTesting
  void setPrecision(int precision) {
    this.precision = precision;
  }

  private void createIssue(Tree tree, int actualPrecision) {
    addPreciseIssue(tree, "Decrease the precision of this number. Actual is " + actualPrecision + ", maximum expected precision is " + precision + ".");
  }

  private int computePrecision(String number) {
    String[] array = number.split("\\.");
    return array.length > 1 ? array[1].length() : 0;
  }

}
