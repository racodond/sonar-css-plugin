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
import org.sonar.css.parser.CssGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;

/**
 * https://github.com/stubbornella/csslint/wiki/Disallow-empty-rules
 *
 * @author tkende
 */
@Rule(key = "empty-rules", priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class DisallowEmptyRules extends SquidCheck<LexerlessGrammar> {

  int counter = 0;

  @Override
  public void init() {
    subscribeTo(CssGrammar.ruleset, CssGrammar.atRule, CssGrammar.declaration);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(CssGrammar.declaration)) {
      counter++;
    } else {
      counter = 0;
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode.is(CssGrammar.ruleset, CssGrammar.atRule) && counter == 0) {
      getContext().createLineViolation(this, "Empty rule", astNode);
    }
  }
}
