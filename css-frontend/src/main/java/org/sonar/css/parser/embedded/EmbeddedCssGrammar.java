/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
 * mailto: david.racodon@gmail.com
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
package org.sonar.css.parser.embedded;

import com.sonar.sslr.api.typed.GrammarBuilder;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.css.parser.TreeFactory;
import org.sonar.css.parser.css.CssGrammar;
import org.sonar.css.tree.impl.css.InternalSyntaxToken;
import org.sonar.plugins.css.api.tree.css.StyleSheetTree;
import org.sonar.plugins.css.api.tree.embedded.CssInStyleTagTree;
import org.sonar.plugins.css.api.tree.embedded.FileWithEmbeddedCssTree;

public class EmbeddedCssGrammar extends CssGrammar {

  public EmbeddedCssGrammar(GrammarBuilder<InternalSyntaxToken> b, TreeFactory f) {
    super(b, f);
  }

  @Override
  public StyleSheetTree STYLESHEET() {
    return b.<StyleSheetTree>nonterminal(LexicalGrammar.STYLESHEET).is(
      f.stylesheet(
        b.zeroOrMore(
          b.firstOf(
            AT_RULE(),
            RULESET()))));
  }

  public CssInStyleTagTree CSS_IN_STYLE_TAG() {
    return b.<CssInStyleTagTree>nonterminal(LexicalGrammar.CSS_IN_STYLE_TAG).is(
      f.cssInStyleTag(
        b.token(LexicalGrammar.OPENING_HTML_STYLE_TAG),
        STYLESHEET(),
        b.token(LexicalGrammar.CLOSING_HTML_STYLE_TAG)));
  }

  public FileWithEmbeddedCssTree FILE_WITH_EMBEDDED_CSS() {
    return b.<FileWithEmbeddedCssTree>nonterminal(LexicalGrammar.FILE_WITH_EMBEDDED_CSS).is(
      f.fileWithEmbeddedCss(
        b.zeroOrMore(
          b.firstOf(
            CSS_IN_STYLE_TAG(),
            b.token(LexicalGrammar.NON_CSS_TOKEN))),
        b.token(LexicalGrammar.EOF)));
  }

}
