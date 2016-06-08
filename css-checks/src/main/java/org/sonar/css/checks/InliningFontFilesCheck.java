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
import com.sonar.sslr.api.Token;

import java.util.regex.Pattern;
import javax.annotation.Nonnull;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

/*
 * See http://bramstein.com/writing/web-font-anti-patterns-inlining.html
 */
@Rule(
  key = "inlining-font-files",
  name = "Font files inlining should not be used",
  priority = Priority.MAJOR,
  tags = {Tags.PERFORMANCE})
@SqaleConstantRemediation("1h")
public class InliningFontFilesCheck extends CssCheck {

  private static final Pattern BASE64_PATTERN = Pattern.compile(".*base64.*");

  @Override
  public void init() {
    subscribeTo(CssGrammar._URI_CONTENT);
  }

  @Override
  public void visitNode(AstNode uriContentNode) {
    if (uriContentNode.getFirstAncestor(CssGrammar.AT_RULE) != null
      && "font-face".equals(getAtRuleIdentifier(uriContentNode.getFirstAncestor(CssGrammar.AT_RULE)))) {
      for (Token uriContentToken : uriContentNode.getTokens()) {
        if (BASE64_PATTERN.matcher(uriContentToken.getValue()).matches()) {
          addIssue(this, "Remove this inline font file.", uriContentNode);
        }
      }
    }
  }

  @Nonnull
  private static String getAtRuleIdentifier(AstNode atRuleNode) {
    return atRuleNode.getFirstChild(CssGrammar.AT_KEYWORD).getFirstChild(CssGrammar.IDENT).getTokenValue();
  }

}
