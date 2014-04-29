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
import org.sonar.css.parser.CssGrammar;

import java.util.List;

import com.sonar.sslr.squid.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;

/**
 * https://github.com/stubbornella/csslint/wiki/Bulletproof-font-face
 * @author tkende
 *
 */
@Rule(key = "bulletproof-font-face", priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class BulletproofFontFace extends SquidCheck<LexerlessGrammar> {

  @Override
  public void init() {
    subscribeTo(CssGrammar.atRule);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if ("font-face".equals(astNode.getFirstChild(CssGrammar.atkeyword).getFirstChild(CssGrammar.ident).getTokenValue())) {
      List<AstNode> declarations = astNode.getFirstDescendant(CssGrammar.atRuleBlock).getFirstChild(CssGrammar.supDeclaration).getChildren(CssGrammar.declaration);
      for (AstNode declaration : declarations) {
        if ("src".equals(declaration.getFirstChild(CssGrammar.property).getTokenValue())) {
          String firstAnyFunciontValue = CssChecksUtil.getStringValue(
              declaration.getFirstChild(CssGrammar.value)
                  .getFirstChild(CssGrammar.function)
                  .getFirstChild(CssGrammar.parameters)
                  .getFirstDescendant(CssGrammar.parameter));
          if (!firstAnyFunciontValue.matches(".*\\.eot\\?.*?['\"]?$")) {
            getContext().createLineViolation(this, "First web font has missing query string or it is not eot", astNode);
          }
          // We only care about the first function
          return;
        }
      }
    }
  }

}
