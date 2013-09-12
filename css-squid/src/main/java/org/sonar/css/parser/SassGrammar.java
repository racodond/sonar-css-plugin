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
package org.sonar.css.parser;

import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.sonar.sslr.parser.LexerlessGrammar;

/**
 * SASS grammar based on http://sass-lang.com/docs/yardoc/file.SASS_REFERENCE.html
 *
 * @author tkende
 *
 */
public enum SassGrammar implements GrammarRuleKey {

  varDeclaration,
  variable,
  parentSelector,
  nestedProperty;

  public static LexerlessGrammar createGrammar() {
    return createGrammarBuilder().build();
  }

  public static LexerlessGrammarBuilder createGrammarBuilder() {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.createBasedOn(CssGrammar.createGrammarBuilder());
    // variable declaration + variable
    b.rule(CssGrammar.statement).override(b.firstOf(varDeclaration, CssGrammar.ruleset, CssGrammar.atRule));
    b.rule(varDeclaration).is(variable, CssGrammar.colon, CssGrammar.value, CssGrammar.semiColon);
    b.rule(variable).is("$", CssGrammar.ident);
    // + nested properties + @*
    b.rule(CssGrammar.supDeclaration).override(b.firstOf(nestedProperty, CssGrammar.atRule, CssGrammar.ruleset, CssGrammar.declaration, varDeclaration)).skip();
    b.rule(CssGrammar.property).override(b.firstOf(variable, CssGrammar.ident));

    b.rule(nestedProperty).is(
        CssGrammar.ident, CssGrammar.colon, b.optional(CssGrammar.value),
        CssGrammar.lCurlyBracket,
        b.optional(CssGrammar.supDeclaration),
        b.zeroOrMore(b.sequence(CssGrammar.semiColon, b.optional(CssGrammar.supDeclaration))),
        CssGrammar.rCurlyBracket);

    // parent selector
    b.rule(CssGrammar.simpleSelector).override(b.firstOf(CssGrammar.typeSelector, parentSelector, CssGrammar.universalSelector)).skip();
    b.rule(parentSelector).is(CssGrammar.addSpacing("&", b), b.zeroOrMore(CssGrammar.subS));

    b.setRootRule(CssGrammar.stylesheet);
    return b;
  }

}
