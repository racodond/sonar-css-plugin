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

import java.util.HashSet;
import java.util.Set;

import com.sonar.sslr.api.AstNode;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.parser.CssGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;

/**
 * https://github.com/stubbornella/csslint/wiki/Disallow-duplicate-background-images
 * @author tkende
 *
 */
@Rule(key = "duplicate-background-images", priority = Priority.MAJOR, cardinality = Cardinality.SINGLE)
@BelongsToProfile(title = CheckList.REPOSITORY_NAME, priority = Priority.MAJOR)
public class DisallowDuplicateBackgroundImages extends SquidCheck<LexerlessGrammar> {

  Set<String> urls = new HashSet<String>();

  @Override
  public void init() {
    subscribeTo(CssGrammar.PROPERTY);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if(astNode.getTokenValue().startsWith("background")){
      AstNode func = astNode.getParent().getFirstChild(CssGrammar.VALUE).getFirstChild(CssGrammar.FUNCTION);
      if(func!=null && "url".equals(func.getFirstChild(CssGrammar.IDENT).getTokenValue()) && func.getFirstChild(CssGrammar.parameters) != null) {
        String url = CssChecksUtil.getStringValue(func.getFirstChild(CssGrammar.parameters).getFirstChild(CssGrammar.parameter)).replaceAll("['\"]", "");
        if(!urls.add(url)){
          getContext().createLineViolation(this, "Disallow duplicate background images", astNode);
        }
      }
    }
  }

}
