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
  nestedProperty, subDeclaration, placeHolderSelector, interpolation, expression, additiveExp, multiplicativeExp, primaryExp, multi, div, stringExp, def, extend, opt, debug, warn;

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
    b.rule(variable).is(CssGrammar.addSpacing(b.sequence("$", CssGrammar.ident), b));
    b.rule(extend).is(CssGrammar.addSpacing("@extend", b), CssGrammar.addSpacing(CssGrammar.selector, b), b.optional(opt));
    b.rule(opt).is("!optional");
    // nested properties
    // @*
    // nested rules
    // extend
    /**
     * What is the differenc between this and the any?
     */
    b.rule(subDeclaration).is(
        b.firstOf(
            extend,
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

    b.rule(interpolation).is(
        "#", CssGrammar.lCurlyBracket,
        CssGrammar.any,
        CssGrammar.rCurlyBracket);
    // #{} interpolation
    // aritchmetic expressions
    // string expressions
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
            def,
            debug,
            warn,
            expression,
            stringExp,
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

    b.rule(def).is(CssGrammar.addSpacing("!default", b));
    b.rule(debug).is(CssGrammar.addSpacing("@debug", b), CssGrammar.value);
    b.rule(warn).is(CssGrammar.addSpacing("@warn", b), CssGrammar.value);
    // expressions
    b.rule(expression).is(
        additiveExp);

    b.rule(additiveExp).is(
        multiplicativeExp, b.zeroOrMore(b.firstOf(CssGrammar.addSpacing("+", b), CssGrammar.addSpacing("-", b)), multiplicativeExp)).skipIfOneChild();
    /*
     * b.rule(multiplicativeExp).is(
     * b.firstOf(div, multi));
     */
    b.rule(multiplicativeExp).is(
        primaryExp, b.zeroOrMore(b.firstOf(CssGrammar.addSpacing("*", b), CssGrammar.addSpacing("/", b)), primaryExp)).skipIfOneChild();

    /**
     * It is not easy to properly cover this so for know these expressions will be divisions.
     * If we need a rule around this area we can try to:
     *  "Maybe one of the options is to split / duplicate grammar on two parts: one with "/" as a division, another one without, first one won't accept variables and expressions."
     */
    /*
     * b.rule(div).is(
     * b.firstOf(
     * b.sequence(variable, CssGrammar.addSpacing("/", b), primaryExp),
     * b.sequence(CssGrammar.lParenthesis, primaryExp, CssGrammar.addSpacing("/", b), primaryExp, CssGrammar.rParenthesis)
     * // TODO cover expressions like: margin-left: 5px + 8px/2px; // Uses +, does division
     * )).skipIfOneChild();
     */
    b.rule(primaryExp).is(b.firstOf(
        CssGrammar.dimension,
        CssGrammar.hash,
        CssGrammar.function,
        variable,
        b.sequence(CssGrammar.lParenthesis, expression, CssGrammar.rParenthesis),
        CssGrammar.number
        ));

    // string expressions
    b.rule(stringExp).is(
        b.firstOf(CssGrammar.string, CssGrammar.addSpacing(CssGrammar.ident, b)),
        CssGrammar.addSpacing("+", b),
        b.firstOf(CssGrammar.string, CssGrammar.addSpacing(CssGrammar.ident, b))
        );

    b.setRootRule(CssGrammar.stylesheet);
    return b;
  }

}
