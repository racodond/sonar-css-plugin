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

/***
 *  TODO THIS IS PARSED INCORRECTLY:
 *
.heart-icon {
    background: url(sprite.png) -16px 0 no-repeat;
}
 * TODO: check http://www.w3.org/TR/css3-values/
 */
public enum CssGrammar implements GrammarRuleKey {

  stylesheet,
  statement,
  atRule,
  block,
  ruleset,
  selector,
  subSelector,

  supDeclaration,
  declaration,
  property,
  value,
  any,

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
  function,
  includes,
  dashMatch,
  eq,
  contains,
  startsWith,
  endsWith,
  important,
  delim,

  simpleSelector,
  typeSelector,
  universalSelector,

  subS,

  attributeSelector,
  classSelector,
  idSelector,
  pseudo,

  combinators,
  descendantComb,
  childComb,
  adjacentComb,
  precededComb,

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

  eof, animationEvent, unit, parameters, comma, parameter, to, from, atRuleBlock;

  private static final String NMCHAR = "(?i)[_a-z0-9-]";
  private static final String NONASCII = "[^\\x00-\\xED]";
  private static final String NMSTART = "(?i)[_a-z]";
  public static final String WHITESPACE = "[ \\t\\r\\n\\f]+";
  public static final String IDENTIFIER = NMSTART + NMCHAR.replace("\\(\\?i\\)", "") + "*";
  public static final String LITERAL = "\"[^\"]*?\"|'[^']*?'";
  public static final String COMMENT = "(?:/\\*[\\s\\S]*?\\*/)";
  public static final String COMMENT2 = "(?:\\<\\!--[\\s\\S]*?--\\>)";

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
    b.rule(statement).is(b.firstOf(atRule, ruleset));
    b.rule(atRule).is(atkeyword,
        addSpacing(b.zeroOrMore(any), b),
        b.firstOf(
            semiColon,
            atRuleBlock
            ));

    b.rule(block).is(
        lCurlyBracket,
        b.optional(supDeclaration),
        /*--> add nested properties (font: 2px/3px {
        family: fantasy;
        size: 30em;
        weight: bold;
        } here */
        // --> add sass @extend, @import rule here:DONE
        rCurlyBracket);

    b.rule(atRuleBlock).is(
        lCurlyBracket,
        b.zeroOrMore(
            b.firstOf(atRule, ruleset, supDeclaration)),
        rCurlyBracket);

    b.rule(ruleset).is(
        addSpacing(b.optional(selector), b),
        block
        );

    b.rule(selector).is(subSelector, b.zeroOrMore(b.sequence(comma, subSelector)));
    b.rule(subSelector).is(b.sequence(
        simpleSelector,
        b.zeroOrMore(combinators, simpleSelector),
        b.optional(whiteSpaces, b.next(b.firstOf(comma, eof)))));
    b.rule(combinators).is(b.firstOf(descendantComb, adjacentComb, precededComb, childComb)).skip();
    b.rule(descendantComb).is(whiteSpaces, b.nextNot(b.firstOf(">", "+", "~")), b.next(simpleSelector));
    b.rule(childComb).is(addSpacing(">", b));
    b.rule(adjacentComb).is(addSpacing("+", b));
    b.rule(precededComb).is(addSpacing("~", b));
    b.rule(simpleSelector).is(
        b.firstOf(universalSelector, typeSelector, animationEvent),
        b.optional(whiteSpaces, b.next(combinators)));
    b.rule(typeSelector).is(ident, b.zeroOrMore(subS));
    b.rule(universalSelector).is(
        b.firstOf(
            b.sequence(addSpacing("*", b), b.nextNot(ident), b.zeroOrMore(subS)),
            b.oneOrMore(subS)));
    b.rule(animationEvent).is(b.firstOf(from, to, percentage));

    b.rule(subS).is(b.firstOf(attributeSelector, idSelector, classSelector, pseudo)).skip();
    b.rule(attributeSelector).is(
        b.oneOrMore(lBracket, ident,
            b.optional(
                b.firstOf(dashMatch, includes, eq, contains, startsWith, endsWith),
                any),
            rBracket));
    b.rule(classSelector).is(b.oneOrMore(".", ident));
    b.rule(idSelector).is("#", ident);
    b.rule(pseudo).is(colon, any);

    b.rule(supDeclaration).is(
        declaration,
        b.zeroOrMore(b.firstOf(semiColon, declaration))).skip();

    b.rule(declaration)
        .is(property, colon, value);
    b.rule(property).is(addSpacing(ident, b));
    b.rule(value).is(
        b.oneOrMore(b.firstOf(any, block, atkeyword)));
    b.rule(any)
        .is(
            b.firstOf(
                function,
                b.sequence(lParenthesis,
                    b.zeroOrMore(any),
                    rParenthesis),
                b.sequence(lBracket,
                    b.zeroOrMore(any), rBracket),
                percentage,
                dimension,
                string,
                uri,
                hash,
                unicodeRange,
                includes,
                dashMatch,
                addSpacing(ident, b),
                number,
                colon,
                important,
                addSpacing(delim, b))).skipIfOneChild();
    b.rule(eof).is(b.token(GenericTokenType.EOF, b.endOfInput())).skip();

  }

  private static void tokens(LexerlessGrammarBuilder b) {
    b.rule(ident).is(_ident);
    b.rule(atkeyword).is(addSpacing(b.sequence("@", ident), b));
    b.rule(string).is(addSpacing(_string, b));
    b.rule(bad_string).is(_badString); // TODO: do we need this?
    b.rule(bad_uri).is(_baduri); // TODO: do we need this?
    b.rule(bad_comment).is(_badcomment); // TODO: do we need this?
    b.rule(hash).is(addSpacing(b.sequence("#", _name), b));
    b.rule(number).is(addSpacing(_num, b));
    b.rule(percentage).is(addSpacing(b.sequence(number, "%"), b));
    b.rule(dimension).is(addSpacing(b.sequence(number, unit), b));
    b.rule(unit).is(b.firstOf("em", "ex", "ch", "rem", "vw", "vh", "vmin", "vmax", "cm", "mm", "in", "px", "pt", "pc"));
    b.rule(uri).is(
        addSpacing(
            b.firstOf(b.sequence("url(", _w, string, _w, rParenthesis), b
                .sequence("url(", _w, b.zeroOrMore(b.firstOf(
                    b.regexp("[!#$%&*-\\[\\]-~]"), _nonascii,
                    _escape)), _w, rParenthesis)), b));
    b.rule(unicodeRange)
        .is(addSpacing(b.regexp("u\\+[0-9a-f?]{1,6}(-[0-9a-f]{1,6})?"), b));
    b.rule(colon).is(addSpacing(":", b));
    b.rule(semiColon).is(addSpacing(";", b));
    b.rule(lCurlyBracket).is(addSpacing("{", b));
    b.rule(rCurlyBracket).is(addSpacing("}", b));
    b.rule(lParenthesis).is(addSpacing("(", b));
    b.rule(rParenthesis).is(addSpacing(")", b));
    b.rule(lBracket).is(addSpacing("[", b));
    b.rule(rBracket).is(addSpacing("]", b));
    b.rule(comma).is(addSpacing(",", b));
    b.rule(whiteSpace).is(b.regexp(WHITESPACE)).skip();
    b.rule(whiteSpaces).is(b.zeroOrMore(
        b.firstOf(
            b.skippedTrivia(whiteSpace),
            b.commentTrivia(b.regexp("(?:"+COMMENT+"|"+COMMENT2+")"))))).skip();
    b.rule(function).is(addSpacing(b.sequence(ident, lParenthesis), b), b.zeroOrMore(parameters),
        rParenthesis);
    b.rule(parameters).is(parameter, b.zeroOrMore(comma, parameter));
    b.rule(parameter).is(addSpacing(b.oneOrMore(any), b));
    b.rule(includes).is(addSpacing("~=", b));
    b.rule(dashMatch).is(addSpacing("|=", b));
    b.rule(eq).is(addSpacing("=", b));
    b.rule(contains).is(addSpacing("*=", b));
    b.rule(startsWith).is(addSpacing("^=", b));
    b.rule(endsWith).is(addSpacing("$=", b));

    b.rule(from).is(addSpacing("from", b));
    b.rule(to).is(addSpacing("to", b));

    b.rule(important).is(addSpacing("!important", b));
    /**
     * TODO: How to cover this: any other character not matched by the above
     * rules, and neither a single nor a double quote
     */
    b.rule(delim).is(b.regexp("[^\"'\\{\\}\\(\\)\\[\\]:; \t\r\n\f]"));

  }

  private static void macros(LexerlessGrammarBuilder b) {
    b.rule(_ident).is(b.token(GenericTokenType.IDENTIFIER, b.sequence(_nmstart, b.zeroOrMore(_nmchar)))).skip();
    b.rule(_name).is(b.token(GenericTokenType.LITERAL, b.oneOrMore(_nmchar))).skip();
    b.rule(_nmstart).is(
        b.firstOf(b.regexp(NMSTART), "-", "*", _nonascii, _escape)).skip();
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
    return b.sequence(/* b.optional(whiteSpace), */value, whiteSpaces);
  }

}
