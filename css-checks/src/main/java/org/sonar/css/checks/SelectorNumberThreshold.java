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
package org.sonar.css.checks;

import com.sonar.sslr.api.AstNode;

import java.text.MessageFormat;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.issue.PreciseIssue;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleLinearRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

@Rule(
  key = "S2732",
  name = "Stylesheets should not contain too many selectors",
  priority = Priority.CRITICAL,
  tags = {Tags.BROWSER_COMPATIBILITY, Tags.BUG, Tags.DESIGN})
@ActivatedByDefault
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.HARDWARE_RELATED_PORTABILITY)
@SqaleLinearRemediation(coeff = "10min", effortToFixDescription = "number of selectors beyond the limit")
public class SelectorNumberThreshold extends CssCheck {

  private static final int DEFAULT_THRESHOLD = 4095;

  private int currentSelectorCount;

  @Override
  public void init() {
    subscribeTo(CssGrammar.SUB_SELECTOR);
  }

  @Override
  public void visitFile(AstNode astNode) {
    currentSelectorCount = 0;
  }

  @Override
  public void visitNode(AstNode astNode) {
    currentSelectorCount++;
  }

  @Override
  public void leaveFile(AstNode astNode) {
    if (currentSelectorCount > DEFAULT_THRESHOLD) {
      PreciseIssue issue = addFileIssue(
        this,
        MessageFormat.format("Reduce the number of selectors. This sheet contains {0,number,integer} selectors, "
          + "{1,number,integer} more than the {2,number,integer} maximum.", currentSelectorCount,
          currentSelectorCount - DEFAULT_THRESHOLD, DEFAULT_THRESHOLD));
      issue.setEffortToFix((double) currentSelectorCount - DEFAULT_THRESHOLD);
    }
  }
}
