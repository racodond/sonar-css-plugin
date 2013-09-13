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
 * https://github.com/stubbornella/csslint/wiki/Require-all-gradient-definitions
 * @author tkende
 *
 */
@Rule(key = "gradients", priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class AllGradientDefinitions extends SquidCheck<LexerlessGrammar> {

  private static List<String> gradients = ImmutableList.<String> of(
      "-ms-(linear|radial)-gradient.*",
      "-moz-(linear|radial)-gradient.*",
      "-o-(linear|radial)-gradient.*",
      "-webkit-(linear|radial)-gradient.*",
      "-webkit-gradient.*");

  private static String gradientMatcher = "-(ms|o|moz|webkit)-.*gradient.*";

  @Override
  public void init() {
    subscribeTo(CssGrammar.ruleset);
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode != null) {
      List<AstNode> declarations = astNode.getFirstChild(CssGrammar.block).getChildren(CssGrammar.declaration);
      List<String> usedGradients = new ArrayList<String>();
      for (AstNode declaration : declarations) {
        String value = declaration.getFirstChild(CssGrammar.value).getTokenValue();
        if (value.startsWith("-")) {
          if (value.matches(gradientMatcher)) {
            usedGradients.add(value);
          }
        }
      }
      for (String exptected : gradients) {
        if(!findMatch(usedGradients, exptected)){
          getContext().createLineViolation(this, "Missing gradient: " + exptected, astNode);
        }
      }

    }
  }

  private boolean findMatch(List<String> used, String defined) {
    for (String use : used) {
      if (use.matches(defined)) {
        return true;
      }
    }
    return false;
  }

}
