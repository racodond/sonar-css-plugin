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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://github.com/stubbornella/csslint/wiki/Require-properties-appropriate-for-display
 * @author tkende
 *
 */
@Rule(key = "display-property-grouping", priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class DisplayPropertyGrouping extends SquidCheck<LexerlessGrammar> {

  private Map<String, List<String>> rules = new HashMap<String, List<String>>() {
    private static final long serialVersionUID = -6508282306820423526L;
    {
      put("inline",
          ImmutableList.<String> of(
              "width", "height", "margin", "margin-top", "margin-bottom", "float"));
      put("inline-block", ImmutableList.<String> of("float"));
      put("block", ImmutableList.<String> of("vertical-align"));
      put("table*", ImmutableList.<String> of("margin", "margin-top", "margin-bottom", "margin-left", "margin-right", "float"));
    }
  };

  @Override
  public void init() {
    subscribeTo(CssGrammar.ruleset, CssGrammar.atRule);
  }

  //TODO refactor this to not use getDescendants
  @Override
  public void visitNode(AstNode astNode) {
    List<AstNode> declarations = astNode.getDescendants(CssGrammar.declaration);
    List<String> avoidProps = isDisplay(declarations);
    if (avoidProps != null && avoidProps.size() > 0) {
      if (isOtherUsed(declarations, avoidProps)) {
        getContext().createLineViolation(this, "Unnecessary property with display", astNode);
      }
    }
  }

  private List<String> isDisplay(List<AstNode> declarations) {
    for (AstNode astNode : declarations) {
      String property = astNode.getFirstChild(CssGrammar.property).getToken().getValue();
      String value = astNode.getFirstChild(CssGrammar.value).getTokenValue();
      if ("display".equalsIgnoreCase(property)) {
        if (value.startsWith("table")) {
          return rules.get("table*");
        } else {
          return rules.get(value);
        }
      }
    }
    return null;
  }

  private boolean isOtherUsed(List<AstNode> declarations, List<String> avoidProps) {
    for (AstNode astNode : declarations) {
      String property = astNode.getFirstChild(CssGrammar.property).getTokenValue();
      if (avoidProps.contains(property)) {
        return true;
      }
    }
    return false;
  }

}
