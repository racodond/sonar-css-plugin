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

import java.util.List;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.checks.SquidCheck;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.parser.CssGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;

/**
 * https://github.com/stubbornella/csslint/wiki/Beware-of-box-model-size
 * @author tkende
 *
 */
@Rule(key = "box-model", priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class BewareOfBoxModel extends SquidCheck<LexerlessGrammar> {

  private List<String> widthSizing = ImmutableList.<String> of(
      "border", "border-left", "border-right", "padding", "padding-left", "padding-right"
      );

  private List<String> heightSizing = ImmutableList.<String> of(
      "border", "border-top", "border-bottom", "padding", "padding-top", "padding-bottom"
      );

  @Override
  public void init() {
    subscribeTo(CssGrammar.ruleset);
  }

  @Override
  public void visitNode(AstNode astNode) {
    List<AstNode> declarations = astNode.getChildren(CssGrammar.declaration);
    int widthOrHeight = isWidthOrHeight(declarations);
    if (widthOrHeight > 0 && !isBoxSizing(declarations)) {
      if (isOtherUsed(widthOrHeight, declarations)) {
        getContext().createLineViolation(this, "Possible box sizing issue", astNode);
      }
    }
  }

  private boolean isOtherUsed(int widthOrHeight, List<AstNode> declarations) {
    List<String> others = (widthOrHeight == 1) ? heightSizing : widthSizing;
    for (AstNode astNode : declarations) {
      String property = astNode.getFirstChild(CssGrammar.property).getTokenValue();
      String value = astNode.getFirstChild(CssGrammar.value).getTokenValue();
      if (others.contains(property) && !"none".equalsIgnoreCase(value)) {
        return true;
      }
    }
    return false;
  }

  private boolean isBoxSizing(List<AstNode> declarations) {
    for (AstNode astNode : declarations) {
      String property = astNode.getFirstChild(CssGrammar.property).getTokenValue();
      if ("box-sizing".equalsIgnoreCase(property)) {
        return "border-box".equalsIgnoreCase(astNode.getFirstChild(CssGrammar.value).getTokenValue());
      }
    }
    return false;
  }

  private int isWidthOrHeight(List<AstNode> declarations) {
    for (AstNode astNode : declarations) {
      String property = astNode.getFirstChild(CssGrammar.property).getToken().getValue();
      if ("height".equalsIgnoreCase(property)) {
        return 1;
      } else if ("width".equalsIgnoreCase(property)) {
        return 2;
      }
    }
    return 0;
  }

}
