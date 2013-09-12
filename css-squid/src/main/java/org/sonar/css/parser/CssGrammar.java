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

import org.sonar.css.lexer.CssLexer;
import com.sonar.sslr.api.GenericTokenType;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.sonar.sslr.parser.LexerlessGrammar;

/**
 * CSS grammar based on http://www.w3.org/TR/CSS2/syndata.html
 * + http://www.w3.org/TR/css3-syntax/
 *
 * block: http://www.w3.org/TR/CSS2/syndata.html#block is not supported, it will
 * be handled as declaration block
 *
 * There are problems with comments+whitespaces...
 *
 * @author tkende
 *
 */
public enum CssGrammar implements GrammarRuleKey {

  stylesheet,
  statement,
  atRule,
  block,
  ruleset,
  selector, // comma separated more selector
  subSelector, // one or more simple selector separated with combinators

  supDeclaration,
  declaration,
  property,
  value,
  any,
  unused,

  ident,
  atkeyword,
  string,
  bad_string,
  bad_uri,
  bad_comment,
  hash,
  number,
  percentage,
  dimension,
  uri,
  unicodeRange,
  // cdo,
  // cdc,

  colon,
  semiColon,
  lCurlyBracket,
  rCurlyBracket,
  lParenthesis,
  rParenthesis,
  lBracket,
  rBracket,

  whiteSpace,
  whiteSpaces,
  comment,
  function,
  includes,
  dashMatch,
  eq,
  contains,
  startsWith,
  endsWith,
  important,
  delim,

  simpleSelector, // either a type selector or universal selector followed immediately by zero or more attribute selectors, ID selectors, or
                  // pseudo-classes, in any order
  typeSelector, // ident
  // ADD NAMESPACE
  // ADD SUBSTRING MATCHERS: ^= $= *=
  universalSelector, // If the universal selector is not the only component of a simple selector, the "*" may be omitted

  subS,

  attributeSelector, // [att] | [att=val] | [att~=val] | [att|=val]
  classSelector, // '.'+ident --> type or universal
  idSelector, // '#'+ident --> type or universal
  pseudo, // ':'+ident --> type or universal

  descendantSelector, // white space between selectors
  childSelecotr, // '>' +optional whitespaces between selectors
  adjacentSiblingSelector, // '+' +optional whitespaces between selectors

  combinators,
  descendantComb, // ws
  childComb, // >
  adjacentComb, // +
  precededComb, // ~

  _ident,
  _name,
  _nmstart,
  _nonascii,
  _unicode,
  _escape,
  _nmchar,
  _num,
  _string,
  _string1,
  _string2,
  _badString,
  _badString1,
  _badString2,
  _badcomment,
  _badcomment1,
  _badcomment2,
  _baduri,
  _baduri1,
  _baduri2,
  _baduri3,
  _nl,
  _w,

  eof;

  private static final String NMCHAR = "(?i)[_a-z0-9-]";
  private static final String NONASCII = "[^\\x00-\\xED]";
  private static final String NMSTART = "(?i)[_a-z]";
  public static final String WHITESPACE = "[ \\t\\r\\n\\f]+";
  public static final String IDENTIFIER = NMSTART + NMCHAR.replace("\\(\\?i\\)", "") + "*";
  public static final String LITERAL = "\"[^\"]*?\"|'[^']*?'";

  public static LexerlessGrammar createGrammar() {
    return createGrammarBuilder().build();
  }

  public static LexerlessGrammarBuilder createGrammarBuilder() {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
    macros(b);
    tokens(b);
    syntax(b);
    b.setRootRule(stylesheet);

    return b;
  }

  private static void syntax(LexerlessGrammarBuilder b) {
    b.rule(stylesheet).is(whiteSpaces, b.zeroOrMore(statement), eof);
    b.rule(statement).is(b.firstOf(ruleset, atRule)); // --> add sass variable declaration here:DONE
    b.rule(atRule).is(atkeyword,
        b.zeroOrMore(any),
        b.firstOf(
            semiColon,
            b.sequence(lCurlyBracket, ruleset, rCurlyBracket)));

    b.rule(block).is(
        lCurlyBracket,
        b.optional(supDeclaration), // --> add sass variable declaration here:DONE
        // --> add sass subruleset here:DONE
        /*--> add nested properties (font: 2px/3px {
        family: fantasy;
        size: 30em;
        weight: bold;
        } here */
        // --> add sass @extend, @import rule here:DONE
        b.zeroOrMore(b.sequence(semiColon,
            b.optional(supDeclaration))), rCurlyBracket);
    b.rule(ruleset).is(
        b.optional(selector), // --> add sass parent selector '&' here:DONE
        block
        );

    b.rule(selector).is(subSelector, b.zeroOrMore(b.firstOf(subSelector, addSpacing(",", b))));
    b.rule(subSelector).is(simpleSelector, b.zeroOrMore(combinators, simpleSelector));
    b.rule(combinators).is(b.firstOf(descendantComb, adjacentComb, precededComb, childComb)).skip();
    b.rule(descendantComb).is(whiteSpace, b.nextNot(combinators));
    b.rule(childComb).is(addSpacing(">", b));
    b.rule(adjacentComb).is(addSpacing("+", b));
    b.rule(precededComb).is(addSpacing("~", b));
    b.rule(simpleSelector).is(b.firstOf(typeSelector, universalSelector)).skip();
    b.rule(typeSelector).is(ident, b.zeroOrMore(subS));
    b.rule(universalSelector).is(
        b.firstOf(
            b.sequence(addSpacing("*", b), b.zeroOrMore(subS)),
            b.oneOrMore(subS)));

    b.rule(subS).is(b.firstOf(attributeSelector, idSelector, classSelector, pseudo)).skip();
    b.rule(attributeSelector).is(b.oneOrMore(lBracket, ident, b.optional(b.firstOf(dashMatch, includes, eq, contains, startsWith, endsWith), any), rBracket));
    b.rule(classSelector).is(b.oneOrMore(".", ident));
    b.rule(idSelector).is("#", ident);
    b.rule(pseudo).is(colon, any);

    b.rule(supDeclaration).is(declaration).skip();

    b.rule(declaration)
        .is(property, colon, value);
    b.rule(property).is(ident);
    b.rule(value).is(
        b.oneOrMore(b.firstOf(any, block, atkeyword)));
    b.rule(any)
        .is(
            addSpacing(
                b.firstOf(
                    b.sequence(function,
                        b.zeroOrMore(any),
                        rParenthesis),
                    b.sequence(lParenthesis,
                        b.zeroOrMore(any),
                        rParenthesis),
                    b.sequence(lBracket,
                        b.zeroOrMore(any), rBracket),
                    percentage, dimension, string,
                    uri, hash, unicodeRange, includes, dashMatch,
                    ident, number, colon, important, delim), b)).skipIfOneChild();
    //b.rule(unused).is(
    //    b.firstOf(block, b.sequence(atkeyword, whiteSpaces),
    //        b.sequence(semiColon, whiteSpaces)/*
    //                                           * ,
    //                                           * b.sequence(cdo, whiteSpaces),
    //                                           * b.sequence(cdc, whiteSpaces)
    //                                           */));

    b.rule(eof).is(b.token(GenericTokenType.EOF, b.endOfInput())).skip();

  }

  private static void tokens(LexerlessGrammarBuilder b) {
    b.rule(ident).is(addSpacing(_ident, b));
    b.rule(atkeyword).is(addSpacing(b.sequence("@", ident), b));
    b.rule(string).is(addSpacing(_string, b));
    b.rule(bad_string).is(_badString);
    b.rule(bad_uri).is(_baduri);
    b.rule(bad_comment).is(_badcomment);
    b.rule(hash).is(addSpacing(b.sequence("#", _name), b));
    b.rule(number).is(addSpacing(_num, b));
    b.rule(percentage).is(addSpacing(b.sequence(number, "%"), b));
    b.rule(dimension).is(addSpacing(b.sequence(number, ident), b));
    b.rule(uri).is(
        addSpacing(
            b.firstOf(b.sequence("url(", _w, string, _w, rParenthesis), b
                .sequence("url(", _w, b.zeroOrMore(b.firstOf(
                    b.regexp("[!#$%&*-\\[\\]-~]"), _nonascii,
                    _escape)), _w, rParenthesis)), b));
    b.rule(unicodeRange)
        .is(addSpacing(b.regexp("u\\+[0-9a-f?]{1,6}(-[0-9a-f]{1,6})?"), b));
    // b.rule(cdo).is("<!--");
    // b.rule(cdc).is("-->");
    b.rule(colon).is(addSpacing(":", b));
    b.rule(semiColon).is(addSpacing(";", b));
    b.rule(lCurlyBracket).is(addSpacing("{", b));
    b.rule(rCurlyBracket).is(addSpacing("}", b));
    b.rule(lParenthesis).is(addSpacing("(", b));
    b.rule(rParenthesis).is(addSpacing(")", b));
    b.rule(lBracket).is(addSpacing("[", b));
    b.rule(rBracket).is(addSpacing("]", b));
    b.rule(whiteSpace).is(b.regexp(WHITESPACE)).skip();
    b.rule(whiteSpaces).is(b.zeroOrMore(
        b.firstOf(
            b.skippedTrivia(whiteSpace),
            b.commentTrivia(b.regexp(CssLexer.COMMENT)), // --> add sass // comment here
            b.commentTrivia(b.regexp(CssLexer.COMMENT2))))).skip();
    b.rule(comment).is(b.regexp("\\/\\*[^*]*\\*+([^/*][^*]*\\*+)*\\/"));
    b.rule(function).is(addSpacing(b.sequence(ident, lParenthesis), b));
    b.rule(includes).is(addSpacing("~=", b));
    b.rule(dashMatch).is(addSpacing("|=", b));
    b.rule(eq).is(addSpacing("=", b));
    b.rule(contains).is(addSpacing("*=", b));
    b.rule(startsWith).is(addSpacing("^=", b));
    b.rule(endsWith).is(addSpacing("$=", b));

    b.rule(important).is("!important");
    /**
     * TODO: How to cover this: any other character not matched by the above
     * rules, and neither a single nor a double quote
     */
    b.rule(delim).is(b.regexp("[^\"'\\{\\}\\(\\)\\[\\]:; \t\r\n\f]"));

  }

  private static void macros(LexerlessGrammarBuilder b) {
    b.rule(_ident).is(b.token(GenericTokenType.IDENTIFIER, b.sequence(b.optional("-"), _nmstart, b.zeroOrMore(_nmchar)))).skip();
    b.rule(_name).is(b.token(GenericTokenType.LITERAL, b.oneOrMore(_nmchar))).skip();
    b.rule(_nmstart).is(
        b.firstOf(b.regexp(NMSTART), _nonascii, _escape)).skip();
    b.rule(_nonascii).is(b.regexp(NONASCII)).skip();
    b.rule(_unicode).is(
        b.regexp("\\\\[0-9a-f]{1,6}(\\r\\n|[ \\n\\r\\t\\f])?")).skip();
    b.rule(_escape).is(
        b.firstOf(_unicode, b.regexp("\\\\[^\\n\\r\\f0-9a-f]"))).skip();
    b.rule(_nmchar).is(
        b.firstOf(b.regexp(NMCHAR), _nonascii, _escape)).skip();
    b.rule(_num).is(b.token(GenericTokenType.LITERAL, b.sequence(b.optional("-"), // '-' IS NOT DEFINED IN THE W3 spec
        b.firstOf(b.regexp("[0-9]*\\.[0-9]+"), b.regexp("[0-9]+"))))).skip();
    b.rule(_string).is(b.token(GenericTokenType.LITERAL, b.firstOf(_string1, _string2))).skip();
    b.rule(_string1).is(
        "\"",
        b.zeroOrMore(b.firstOf(b.regexp("[^\\n\\r\\f\\\\\"]"),
            b.sequence("\\", _nl), _escape)), "\"").skip();
    b.rule(_string2).is(
        "'",
        b.zeroOrMore(b.firstOf(b.regexp("[^\\n\\r\\f\\\\']"),
            b.sequence("\\", _nl), _escape)), "'").skip();
    b.rule(_badString).is(b.firstOf(_badString1, _badString2)).skip();
    b.rule(_badString1).is(
        "\"",
        b.zeroOrMore(b.regexp("[^\\n\\r\\f\\\\\"]"),
            b.sequence("\\", _nl), _escape), "\"").skip();
    b.rule(_badString2).is(
        "'",
        b.zeroOrMore(b.regexp("[^\\n\\r\\f\\\\\']"),
            b.sequence("\\", _nl), _escape), "'").skip();
    b.rule(_badcomment).is(b.firstOf(_badcomment1, _badcomment2)).skip();
    b.rule(_badcomment1).is(b.regexp("\\/\\*[^*]*\\*+([^/*][^*]*\\*+)*")).skip();
    b.rule(_badcomment2).is(b.regexp("\\/\\*[^*]*(\\*+[^/*][^*]*)*")).skip();
    b.rule(_baduri).is(b.firstOf(_baduri1, _baduri2, _baduri3)).skip();
    b.rule(_baduri1).is(
        "url(",
        _w,
        b.zeroOrMore(b.firstOf(b.regexp("[!#$%&*-~]"), _nonascii,
            _escape)), _w).skip();
    b.rule(_baduri2).is("url(", _w, _string, _w).skip();
    b.rule(_baduri3).is("url(", _w, _badString).skip();
    b.rule(_nl).is(b.firstOf("\n", "\r\n", "\r", "\f")).skip();
    b.rule(_w).is(b.regexp("[ \\t\\r\\n\\f]*")).skip();
  }

  static Object addSpacing(Object value, LexerlessGrammarBuilder b) {
    return b.sequence(value, whiteSpaces);
  }

}
