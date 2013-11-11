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

import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.checks.SquidCheck;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.parser.CssGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/stubbornella/csslint/wiki/Require-shorthand-properties
 * @author tkende
 *
 */
@Rule(key = "shorthand", priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class ShorthandProperties extends SquidCheck<LexerlessGrammar> {

  private static List<String> margin = ImmutableList.<String> of("margin-left", "margin-right", "margin-top", "margin-bottom");
  private static List<String> padding = ImmutableList.<String> of("padding-left", "padding-right", "padding-top", "padding-bottom");

  List<String> properties = new ArrayList<String>();

  @Override
  public void init() {
    subscribeTo(CssGrammar.ruleset, CssGrammar.atRule, CssGrammar.declaration);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.getType().equals(CssGrammar.ruleset) || astNode.getType().equals(CssGrammar.atRule)) {
      properties.clear();
    } else if (astNode.getType().equals(CssGrammar.declaration)) {
      String property = astNode.getFirstChild(CssGrammar.property).getTokenValue();
      if (margin.contains(property) || padding.contains(property)) {
        properties.add(property);
      }
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode.getType().equals(CssGrammar.ruleset) || astNode.getType().equals(CssGrammar.atRule)) {
      if (properties.containsAll(margin)) {
        getContext().createLineViolation(this, "Margin shorthand can be applied", astNode);
      }
      if (properties.containsAll(padding)) {
        getContext().createLineViolation(this, "Padding shorthand can be applied", astNode);
      }
    }
  }

}
