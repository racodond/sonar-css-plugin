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
  nestedProperty, subDeclaration, placeHolderSelector, interpolation;

  public static final String SINGLE_LINE_COMMENT = "//[^\\n\\r]*+";

  public static LexerlessGrammar createGrammar() {
    return createGrammarBuilder().build();
  }

  public static LexerlessGrammarBuilder createGrammarBuilder() {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.createBasedOn(CssGrammar.createGrammarBuilder());
    // variable declaration + variable
    /**
     * STATEMENT OVERRIDE
     */
    b.rule(CssGrammar.statement).override(b.firstOf(varDeclaration, CssGrammar.atRule, CssGrammar.ruleset));
    b.rule(varDeclaration).is(variable, CssGrammar.colon, CssGrammar.value, CssGrammar.semiColon);
    b.rule(variable).is("$", CssGrammar.ident);
    // nested properties
    // @*
    // nested rules
    b.rule(subDeclaration).is(
        b.firstOf(
            nestedProperty,
            CssGrammar.atRule,
            CssGrammar.ruleset,
            CssGrammar.declaration,
            varDeclaration)
        );

    /**
     * SUPDECLARATION OVERRIDE
     */
    b.rule(CssGrammar.supDeclaration).override(
        subDeclaration,
        b.zeroOrMore(
            b.firstOf(CssGrammar.semiColon, subDeclaration)
            )).skip();

    /**
     * PROPERTY OVERRIDE
     */
    b.rule(CssGrammar.property).override(b.firstOf(CssGrammar.addSpacing(variable, b), CssGrammar.addSpacing(CssGrammar.ident, b)));

    b.rule(nestedProperty).is(
        CssGrammar.ident, CssGrammar.colon, b.optional(CssGrammar.value),
        CssGrammar.block);

    // parent selector
    b.rule(parentSelector).is(CssGrammar.addSpacing("&", b), b.zeroOrMore(CssGrammar.subS));
    /**
     * SIMPLESELECTOR OVERRIDE
     */
    b.rule(CssGrammar.simpleSelector).override(
        b.firstOf(
            CssGrammar.universalSelector,
            CssGrammar.typeSelector,
            CssGrammar.animationEvent,
            parentSelector),
        b.optional(
            CssGrammar.whiteSpaces, b.next(CssGrammar.combinators))
        ).skip();

    // placeholder selector
    b.rule(placeHolderSelector).is("%", CssGrammar.ident);
    /**
     * SUBS OVERRIDE
     */
    b.rule(CssGrammar.subS).override(
        b.firstOf(
            CssGrammar.attributeSelector,
            CssGrammar.idSelector,
            CssGrammar.classSelector,
            placeHolderSelector,
            CssGrammar.pseudo)).skip();

    // single line comment
    /**
     * WHITESPACES OVERRIDE
     */
    b.rule(CssGrammar.whiteSpaces).override(b.zeroOrMore(
        b.firstOf(
            b.skippedTrivia(CssGrammar.whiteSpace),
            b.commentTrivia(b.regexp("(?:" + CssGrammar.COMMENT + "|" + CssGrammar.COMMENT2 + "|" + SINGLE_LINE_COMMENT + ")"))))).skip();

    // #{} interpolation
    b.rule(interpolation).is(
        "#", CssGrammar.lCurlyBracket,
        CssGrammar.any,
        CssGrammar.rCurlyBracket);
    /**
     * ANY OVERRIDE
     */
    b.rule(CssGrammar.any).override(
        b.firstOf(
            CssGrammar.function,
            b.sequence(CssGrammar.lParenthesis,
                b.zeroOrMore(CssGrammar.any),
                CssGrammar.rParenthesis),
            b.sequence(CssGrammar.lBracket,
                b.zeroOrMore(CssGrammar.any), CssGrammar.rBracket),
            CssGrammar.percentage,
            CssGrammar.dimension,
            CssGrammar.string,
            interpolation,
            CssGrammar.uri,
            CssGrammar.hash,
            CssGrammar.unicodeRange,
            CssGrammar.includes,
            CssGrammar.dashMatch,
            CssGrammar.addSpacing(CssGrammar.ident, b),
            CssGrammar.number,
            CssGrammar.colon,
            CssGrammar.important,
            CssGrammar.addSpacing(CssGrammar.delim, b))).skipIfOneChild();

    b.setRootRule(CssGrammar.stylesheet);
    return b;
  }

}
