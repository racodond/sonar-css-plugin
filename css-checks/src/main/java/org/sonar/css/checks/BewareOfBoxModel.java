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

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * https://github.com/stubbornella/csslint/wiki/Beware-of-box-model-size
 *
 * @author tkende
 */
@Rule(key = "box-model", priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class BewareOfBoxModel extends SquidCheck<LexerlessGrammar> {

  private List<String> widthSizing = ImmutableList.<String>of(
    "border", "border-left", "border-right", "padding", "padding-left", "padding-right"
  );

  private List<String> heightSizing = ImmutableList.<String>of(
    "border", "border-top", "border-bottom", "padding", "padding-top", "padding-bottom"
  );

  private EnumSet<Combinations> combinations;

  @Override
  public void init() {
    subscribeTo(CssGrammar.ruleset, CssGrammar.atRule, CssGrammar.declaration);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(CssGrammar.ruleset) || astNode.is(CssGrammar.atRule)) {
      combinations = EnumSet.noneOf(Combinations.class);
    } else if (astNode.is(CssGrammar.declaration)) {
      if (isBoxSizing(astNode)) {
        combinations.clear();
        combinations.add(Combinations.IS_BOX_SIZING);
      }
      if (!combinations.contains(Combinations.IS_BOX_SIZING)) {
        if (!combinations.contains(Combinations.WIDTH_FOUND) && isWidth(astNode)) {
          combinations.add(Combinations.WIDTH_FOUND);
        } else if (!combinations.contains(Combinations.HEIGHT_FOUND) && isHeight(astNode)) {
          combinations.add(Combinations.HEIGHT_FOUND);
        }
        if (isWidthSizing(astNode)) {
          combinations.add(Combinations.WIDTH_SIZING);
        }
        if (isHeightSizing(astNode)) {
          combinations.add(Combinations.HEIGHT_SIZING);
        }
      }
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (astNode.is(CssGrammar.ruleset)
      && (combinations.containsAll(Arrays.asList(Combinations.WIDTH_FOUND, Combinations.WIDTH_SIZING))
      || combinations.containsAll(Arrays.asList(Combinations.HEIGHT_FOUND, Combinations.HEIGHT_SIZING)))) {
      getContext().createLineViolation(this, "Possible box sizing issue", astNode);
    }
  }

  private boolean isWidthSizing(AstNode astNode) {
    return isOtherUsed(widthSizing, astNode);
  }

  private boolean isHeightSizing(AstNode astNode) {
    return isOtherUsed(heightSizing, astNode);
  }

  private boolean isOtherUsed(List<String> props, AstNode declaration) {
    String property = declaration.getFirstChild(CssGrammar.property).getTokenValue();
    String value = declaration.getFirstChild(CssGrammar.value).getTokenValue();
    return (props.contains(property) && !"none".equalsIgnoreCase(value));
  }

  private boolean isBoxSizing(AstNode declaration) {
    String property = declaration.getFirstChild(CssGrammar.property).getTokenValue();
    return "box-sizing".equalsIgnoreCase(property);
    /*
     * if ("box-sizing".equalsIgnoreCase(property)) {
     * return "border-box".equalsIgnoreCase(declaration.getFirstChild(CssGrammar.value).getTokenValue());
     * }
     */
  }

  private boolean isWidth(AstNode astNode) {
    return Combinations.WIDTH_FOUND.equals(isWidthOrHeight(astNode));
  }

  private boolean isHeight(AstNode astNode) {
    return Combinations.HEIGHT_FOUND.equals(isWidthOrHeight(astNode));
  }

  private Combinations isWidthOrHeight(AstNode declaration) {
    String property = declaration.getFirstChild(CssGrammar.property).getToken().getValue();
    if ("height".equalsIgnoreCase(property)) {
      return Combinations.HEIGHT_FOUND;
    } else if ("width".equalsIgnoreCase(property)) {
      return Combinations.WIDTH_FOUND;
    }
    return null;
  }

  private enum Combinations {
    WIDTH_FOUND, WIDTH_SIZING,
    HEIGHT_FOUND, HEIGHT_SIZING,
    IS_BOX_SIZING;
  }

}
