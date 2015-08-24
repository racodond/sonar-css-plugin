/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende and David RACODON
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

  VAR_DECLARATION,
  VARIABLE,
  PARENT_SELECTOR,
  NESTED_PROPERTY,
  SUB_DECLARATION,
  PLACEHOLDER_SELECTOR,
  INTERPOLATION,
  EXPRESSION,
  ADDITIVE_EXP,
  MULTIPLICATIVE_EXP,
  PRIMARY_EXP,
  MULTI,
  DIV,
  STRING_EXP,
  DEF,
  EXTEND,
  OPT,
  DEBUG,
  WARN;

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
    b.rule(CssGrammar.STATEMENT).override(b.firstOf(VAR_DECLARATION, CssGrammar.AT_RULE, CssGrammar.RULESET));
    b.rule(VAR_DECLARATION).is(VARIABLE, CssGrammar.COLON, CssGrammar.VALUE, CssGrammar.SEMICOLON);
    b.rule(VARIABLE).is(CssGrammar.addSpacing(b.sequence("$", CssGrammar.IDENT), b));
    b.rule(EXTEND).is(CssGrammar.addSpacing("@extend", b), CssGrammar.addSpacing(CssGrammar.SELECTOR, b), b.optional(OPT));
    b.rule(OPT).is("!optional");
    // nested properties
    // @*
    // nested rules
    // extend
    /**
     * What is the differenc between this and the any?
     */
    b.rule(SUB_DECLARATION).is(
        b.firstOf(
            EXTEND,
            NESTED_PROPERTY,
            CssGrammar.AT_RULE,
            CssGrammar.RULESET,
            CssGrammar.DECLARATION,
            VAR_DECLARATION)
        );

    /**
     * SUPDECLARATION OVERRIDE
     */
    b.rule(CssGrammar.SUP_DECLARATION).override(
        SUB_DECLARATION,
        b.zeroOrMore(
            b.firstOf(CssGrammar.SEMICOLON, SUB_DECLARATION)
            )).skip();

    /**
     * PROPERTY OVERRIDE
     */
    b.rule(CssGrammar.PROPERTY).override(b.firstOf(CssGrammar.addSpacing(VARIABLE, b), CssGrammar.addSpacing(CssGrammar.IDENT, b)));

    b.rule(NESTED_PROPERTY).is(
        CssGrammar.IDENT, CssGrammar.COLON, b.optional(CssGrammar.VALUE),
        CssGrammar.BLOCK);

    // parent selector
    b.rule(PARENT_SELECTOR).is(CssGrammar.addSpacing("&", b), b.zeroOrMore(CssGrammar.SUB_S));
    /**
     * SIMPLESELECTOR OVERRIDE
     */
    b.rule(CssGrammar.SIMPLE_SELECTOR).override(
        b.firstOf(
            CssGrammar.UNIVERSAL_SELECTOR,
            CssGrammar.TYPE_SELECTOR,
            CssGrammar.animationEvent,
            PARENT_SELECTOR),
        b.optional(
            CssGrammar.WHITESPACES, b.next(CssGrammar.COMBINATORS))
        ).skip();

    // placeholder selector
    b.rule(PLACEHOLDER_SELECTOR).is("%", CssGrammar.IDENT);
    /**
     * SUBS OVERRIDE
     */
    b.rule(CssGrammar.SUB_S).override(
        b.firstOf(
            CssGrammar.ATTRIBUTE_SELECTOR,
            CssGrammar.ID_SELECTOR,
            CssGrammar.CLASS_SELECTOR,
            PLACEHOLDER_SELECTOR,
            CssGrammar.PSEUDO)).skip();

    // single line comment
    /**
     * WHITESPACES OVERRIDE
     */
    b.rule(CssGrammar.WHITESPACES).override(b.zeroOrMore(
        b.firstOf(
            b.skippedTrivia(CssGrammar.WHITESPACE),
            b.commentTrivia(b.regexp("(?:" + CssGrammar.COMMENT_REGEX + "|" + CssGrammar.COMMENT2_REGEX + "|" + SINGLE_LINE_COMMENT + ")"))))).skip();

    b.rule(INTERPOLATION).is(
        "#", CssGrammar.OPEN_CURLY_BRACE,
        CssGrammar.ANY,
        CssGrammar.CLOSE_CURLY_BRACE);
    // #{} interpolation
    // aritchmetic expressions
    // string expressions
    /**
     * ANY OVERRIDE
     */
    b.rule(CssGrammar.ANY).override(
        b.firstOf(
            CssGrammar.FUNCTION,
            b.sequence(CssGrammar.OPEN_PARENTHESIS,
                b.zeroOrMore(CssGrammar.ANY),
                CssGrammar.CLOSE_PARENTHESIS),
            b.sequence(CssGrammar.OPEN_BRACKET,
                b.zeroOrMore(CssGrammar.ANY), CssGrammar.CLOSE_BRACKET),
            DEF,
            DEBUG,
            WARN,
            EXPRESSION,
            STRING_EXP,
            CssGrammar.PERCENTAGE,
            CssGrammar.DIMENSION,
            CssGrammar.STRING,
            INTERPOLATION,
            CssGrammar.URI,
            CssGrammar.HASH,
            CssGrammar.UNICODE_RANGE,
            CssGrammar.INCLUDES,
            CssGrammar.DASH_MATCH,
            CssGrammar.addSpacing(CssGrammar.IDENT, b),
            CssGrammar.NUMBER,
            CssGrammar.COLON,
            CssGrammar.IMPORTANT,
            CssGrammar.addSpacing(CssGrammar.DELIM, b))).skipIfOneChild();

    b.rule(DEF).is(CssGrammar.addSpacing("!default", b));
    b.rule(DEBUG).is(CssGrammar.addSpacing("@debug", b), CssGrammar.VALUE);
    b.rule(WARN).is(CssGrammar.addSpacing("@warn", b), CssGrammar.VALUE);
    // expressions
    b.rule(EXPRESSION).is(
        ADDITIVE_EXP);

    b.rule(ADDITIVE_EXP).is(
        MULTIPLICATIVE_EXP, b.zeroOrMore(b.firstOf(CssGrammar.addSpacing("+", b), CssGrammar.addSpacing("-", b)), MULTIPLICATIVE_EXP)).skipIfOneChild();
    /*
     * b.rule(multiplicativeExp).is(
     * b.firstOf(div, multi));
     */
    b.rule(MULTIPLICATIVE_EXP).is(
        PRIMARY_EXP, b.zeroOrMore(b.firstOf(CssGrammar.addSpacing("*", b), CssGrammar.addSpacing("/", b)), PRIMARY_EXP)).skipIfOneChild();

    /**
     * It is not easy to properly cover this so for know these expressions will be divisions.
     * If we need a rule around this area we can try to:
     *  "Maybe one of the options is to split / duplicate grammar on two parts: one with "/" as a division, another one without,
     *  first one won't accept variables and expressions."
     */
    /*
     * b.rule(div).is(
     * b.firstOf(
     * b.sequence(variable, CssGrammar.addSpacing("/", b), primaryExp),
     * b.sequence(CssGrammar.lParenthesis, primaryExp, CssGrammar.addSpacing("/", b), primaryExp, CssGrammar.rParenthesis)
     * // TODO cover expressions like: margin-left: 5px + 8px/2px; // Uses +, does division
     * )).skipIfOneChild();
     */
    b.rule(PRIMARY_EXP).is(b.firstOf(
        CssGrammar.DIMENSION,
        CssGrammar.HASH,
        CssGrammar.FUNCTION,
        VARIABLE,
        b.sequence(CssGrammar.OPEN_PARENTHESIS, EXPRESSION, CssGrammar.CLOSE_PARENTHESIS),
        CssGrammar.NUMBER
        ));

    // string expressions
    b.rule(STRING_EXP).is(
        b.firstOf(CssGrammar.STRING, CssGrammar.addSpacing(CssGrammar.IDENT, b)),
        CssGrammar.addSpacing("+", b),
        b.firstOf(CssGrammar.STRING, CssGrammar.addSpacing(CssGrammar.IDENT, b))
        );

    b.setRootRule(CssGrammar.STYLESHEET);
    return b;
  }

}
