/*
 * Sonar CSS Plugin
 * Copyright (C) 2013 Tamas Kende
 * kende.tamas@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.css.checks;

import com.sonar.sslr.api.AstNode;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.parser.CssGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;

/**
 * @author tkende
 */
@Rule(key = "overspecific-selectors", priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class DisallowOverspecificSelectors extends SquidCheck<LexerlessGrammar> {

  private static final int DEFAULT_NUM_LEVELS = 3;

  @RuleProperty(key = "deepnessThreshold", defaultValue = ""
    + DEFAULT_NUM_LEVELS)
  private int deepnessThreshold = DEFAULT_NUM_LEVELS;

  private int deepness;

  @Override
  public void init() {
    subscribeTo(CssGrammar.subSelector, CssGrammar.simpleSelector);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(CssGrammar.subSelector)) {
      deepness = 0;
    } else {
      deepness++;
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode.is(CssGrammar.subSelector) && deepness > deepnessThreshold) {
      getContext().createLineViolation(this, "Disallow overspecified selectors",
        astNode);
    }
  }

  public void setDeepnessThreshold(int deepnessThreshold) {
    this.deepnessThreshold = deepnessThreshold;
  }

}
