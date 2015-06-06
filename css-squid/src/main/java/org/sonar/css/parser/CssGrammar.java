/*
 * SonarQube CSS Plugin
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

  STYLESHEET,
  STATEMENT,
  AT_RULE,
  BLOCK,
  RULESET,
  SELECTOR,
  SUB_SELECTOR,

  SUP_DECLARATION,
  DECLARATION,
  PROPERTY,
  VALUE,
  ANY,

  IDENT,
  AT_KEYWORD,
  STRING,
  BAD_STRING,
  BAD_URI,
  BAD_COMMENT,
  HASH,
  NUMBER,
  PERCENTAGE,
  DIMENSION,
  URI,
  UNICODE_RANGE,

  COLON,
  SEMICOLON,
  OPEN_CURLY_BRACE,
  CLOSE_CURLY_BRACE,
  OPEN_PARENTHESIS,
  CLOSE_PARENTHESIS,
  OPEN_BRACKET,
  CLOSE_BRACKET,

  WHITESPACE,
  WHITESPACES,
  FUNCTION,
  INCLUDES,
  DASH_MATCH,
  EQ,
  CONTAINS,
  STARTS_WITH,
  ENDS_WITH,
  IMPORTANT,
  DELIM,

  SIMPLE_SELECTOR,
  TYPE_SELECTOR,
  UNIVERSAL_SELECTOR,

  SUB_S,

  ATTRIBUTE_SELECTOR,
  CLASS_SELECTOR,
  ID_SELECTOR,
  PSEUDO,

  COMBINATORS,
  DESCENDANT_COMB,
  CHILD_COMB,
  ADJACENT_COMB,
  PRECEDED_COMB,

  _IDENT,
  _NAME,
  _NMSTART,
  _NONASCII,
  _UNICODE,
  _ESCAPE,
  _NMCHAR,
  _NUM,
  _STRING,
  _STRING1,
  _STRING2,
  _BAD_STRING,
  _BAD_STRING1,
  _BAD_STRING2,
  _BAD_COMMENT,
  _BAD_COMMENT1,
  _BAD_COMMENT2,
  _BADURI,
  _BADURI1,
  _BADURI2,
  _BADURI3,
  _NL,
  _W,

  eof, animationEvent, unit, parameters, comma, parameter, to, from, atRuleBlock, identNoWS, _URI_CONTENT;

  private static final String NMCHAR_REGEX = "(?i)[_a-z0-9-]";
  private static final String NONASCII_REGEX = "[^\\x00-\\xED]";
  private static final String NMSTART_REGEX = "(?i)[_a-z]";
  public static final String WHITESPACE_REGEX = "[ \\t\\r\\n\\f]+";
  public static final String IDENTIFIER_REGEX = NMSTART_REGEX + NMCHAR_REGEX.replace("\\(\\?i\\)", "") + "*";
  public static final String LITERAL_REGEX = "\"[^\"]*?\"|'[^']*?'";
  public static final String COMMENT_REGEX = "(?:/\\*[\\s\\S]*?\\*/)";
  public static final String COMMENT2_REGEX = "(?:\\<\\!--[\\s\\S]*?--\\>)";

  public static LexerlessGrammar createGrammar() {
    return createGrammarBuilder().build();
  }

  public static LexerlessGrammarBuilder createGrammarBuilder() {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
    macros(b);
    tokens(b);
    syntax(b);
    b.setRootRule(STYLESHEET);

    return b;
  }

  private static void syntax(LexerlessGrammarBuilder b) {
    b.rule(STYLESHEET).is(WHITESPACES, b.zeroOrMore(STATEMENT), eof);
    b.rule(STATEMENT).is(b.firstOf(AT_RULE, RULESET));
    b.rule(AT_RULE).is(AT_KEYWORD,
      addSpacing(b.zeroOrMore(ANY), b),
      b.firstOf(
        SEMICOLON,
        atRuleBlock
        ));

    b.rule(BLOCK).is(
      OPEN_CURLY_BRACE,
      b.optional(SUP_DECLARATION),
      /*--> add nested properties (font: 2px/3px {
      family: fantasy;
      size: 30em;
      weight: bold;
      } here */
      // --> add sass @extend, @import rule here:DONE
      CLOSE_CURLY_BRACE);

    b.rule(atRuleBlock).is(
      OPEN_CURLY_BRACE,
      b.zeroOrMore(
        b.firstOf(AT_RULE, RULESET, SUP_DECLARATION)),
      CLOSE_CURLY_BRACE);

    b.rule(RULESET).is(
      addSpacing(b.optional(SELECTOR), b),
      BLOCK
      );

    b.rule(SELECTOR).is(SUB_SELECTOR, b.zeroOrMore(b.sequence(comma, SUB_SELECTOR)));
    b.rule(SUB_SELECTOR).is(b.sequence(
      SIMPLE_SELECTOR,
      b.zeroOrMore(COMBINATORS, SIMPLE_SELECTOR),
      b.optional(WHITESPACES, b.next(b.firstOf(comma, eof)))));
    b.rule(COMBINATORS).is(b.firstOf(DESCENDANT_COMB, ADJACENT_COMB, PRECEDED_COMB, CHILD_COMB)).skip();
    b.rule(DESCENDANT_COMB).is(WHITESPACES, b.nextNot(b.firstOf(">", "+", "~")), b.next(SIMPLE_SELECTOR));
    b.rule(CHILD_COMB).is(addSpacing(">", b));
    b.rule(ADJACENT_COMB).is(addSpacing("+", b));
    b.rule(PRECEDED_COMB).is(addSpacing("~", b));
    b.rule(SIMPLE_SELECTOR).is(
      b.firstOf(UNIVERSAL_SELECTOR, TYPE_SELECTOR, animationEvent),
      b.optional(WHITESPACES, b.next(COMBINATORS)));
    b.rule(TYPE_SELECTOR).is(IDENT, b.zeroOrMore(SUB_S));
    b.rule(UNIVERSAL_SELECTOR).is(
      b.firstOf(
        b.sequence(addSpacing("*", b), b.nextNot(IDENT), b.zeroOrMore(SUB_S)),
        b.oneOrMore(SUB_S)));
    b.rule(animationEvent).is(b.firstOf(from, to, PERCENTAGE));

    b.rule(SUB_S).is(b.firstOf(ATTRIBUTE_SELECTOR, ID_SELECTOR, CLASS_SELECTOR, PSEUDO)).skip();
    b.rule(ATTRIBUTE_SELECTOR).is(
      b.oneOrMore(OPEN_BRACKET, IDENT,
        b.optional(
          b.firstOf(DASH_MATCH, INCLUDES, EQ, CONTAINS, STARTS_WITH, ENDS_WITH),
          ANY),
        CLOSE_BRACKET));
    b.rule(CLASS_SELECTOR).is(b.oneOrMore(".", identNoWS));
    b.rule(ID_SELECTOR).is("#", identNoWS);
    b.rule(PSEUDO).is(COLON, ANY);
    b.rule(SUP_DECLARATION).is(
      b.oneOrMore(b.firstOf(SEMICOLON, DECLARATION)));

    b.rule(DECLARATION)
      .is(PROPERTY, COLON, VALUE);
    b.rule(PROPERTY).is(addSpacing(IDENT, b));
    b.rule(VALUE).is(
      b.oneOrMore(b.firstOf(ANY, BLOCK, AT_KEYWORD)));
    b.rule(ANY)
      .is(
        b.firstOf(
          URI,
          FUNCTION,
          b.sequence(OPEN_PARENTHESIS,
            b.zeroOrMore(ANY),
            CLOSE_PARENTHESIS),
          b.sequence(OPEN_BRACKET,
            b.zeroOrMore(ANY), CLOSE_BRACKET),
          PERCENTAGE,
          DIMENSION,
          NUMBER,
          STRING,
          HASH,
          UNICODE_RANGE,
          INCLUDES,
          DASH_MATCH,
          addSpacing(IDENT, b),
          IMPORTANT,
          COLON,
          addSpacing(DELIM, b))).skipIfOneChild();
    b.rule(eof).is(b.token(GenericTokenType.EOF, b.endOfInput())).skip();

  }

  private static void tokens(LexerlessGrammarBuilder b) {
    b.rule(IDENT).is(addSpacing(_IDENT, b));
    b.rule(identNoWS).is(_IDENT);
    b.rule(AT_KEYWORD).is(addSpacing(b.sequence("@", IDENT), b));
    b.rule(STRING).is(addSpacing(_STRING, b));
    b.rule(BAD_STRING).is(_BAD_STRING); // TODO: do we need this?
    b.rule(BAD_URI).is(_BADURI); // TODO: do we need this?
    b.rule(BAD_COMMENT).is(_BAD_COMMENT); // TODO: do we need this?
    b.rule(HASH).is(addSpacing(b.sequence("#", _NAME), b));
    b.rule(NUMBER).is(addSpacing(_NUM, b));
    b.rule(PERCENTAGE).is(addSpacing(b.sequence(NUMBER, "%"), b));
    b.rule(DIMENSION).is(addSpacing(b.sequence(NUMBER, unit), b));
    b.rule(unit).is(b.firstOf("em", "ex", "ch", "rem", "vw", "vh", "vmin", "vmax", "cm", "mm", "in", "px", "pt", "pc", "ms", "s", "Hz", "kHz", "deg", "grad", "rad", "turn", "dpi", "dpcm", "dppx"));
    b.rule(URI).is(
      addSpacing(
        b.sequence("url(", _URI_CONTENT, CLOSE_PARENTHESIS), b));
    b.rule(_URI_CONTENT).is(
      b.sequence(_W, b.firstOf(
        STRING,
        b.zeroOrMore(
          b.firstOf(
            b.regexp("[!#$%&*-\\[\\]-~]+"),
            _NONASCII,
            _ESCAPE))
      ), _W));
    b.rule(UNICODE_RANGE)
      .is(addSpacing(b.regexp("u\\+[0-9a-f?]{1,6}(-[0-9a-f]{1,6})?"), b));
    b.rule(COLON).is(addSpacing(":", b));
    b.rule(SEMICOLON).is(addSpacing(";", b));
    b.rule(OPEN_CURLY_BRACE).is(addSpacing("{", b));
    b.rule(CLOSE_CURLY_BRACE).is(addSpacing("}", b));
    b.rule(OPEN_PARENTHESIS).is(addSpacing("(", b));
    b.rule(CLOSE_PARENTHESIS).is(addSpacing(")", b));
    b.rule(OPEN_BRACKET).is(addSpacing("[", b));
    b.rule(CLOSE_BRACKET).is(addSpacing("]", b));
    b.rule(comma).is(addSpacing(",", b));
    b.rule(WHITESPACE).is(b.regexp(WHITESPACE_REGEX)).skip();
    b.rule(WHITESPACES).is(b.zeroOrMore(
      b.firstOf(
        b.skippedTrivia(WHITESPACE),
        b.commentTrivia(b.regexp("(?:" + COMMENT_REGEX + "|" + COMMENT2_REGEX + ")"))))).skip();
    b.rule(FUNCTION).is(addSpacing(b.sequence(IDENT, OPEN_PARENTHESIS), b), b.zeroOrMore(parameters),
      CLOSE_PARENTHESIS);
    b.rule(parameters).is(parameter, b.zeroOrMore(comma, parameter));
    b.rule(parameter).is(addSpacing(b.oneOrMore(ANY), b));
    b.rule(INCLUDES).is(addSpacing("~=", b));
    b.rule(DASH_MATCH).is(addSpacing("|=", b));
    b.rule(EQ).is(addSpacing("=", b));
    b.rule(CONTAINS).is(addSpacing("*=", b));
    b.rule(STARTS_WITH).is(addSpacing("^=", b));
    b.rule(ENDS_WITH).is(addSpacing("$=", b));

    b.rule(from).is(addSpacing("from", b));
    b.rule(to).is(addSpacing("to", b));
    b.rule(IMPORTANT).is(addSpacing(b.sequence("!", b.optional(WHITESPACE), "important"), b));
    /**
     * TODO: How to cover this: any other character not matched by the above
     * rules, and neither a single nor a double quote
     */
    b.rule(DELIM).is(b.regexp("[^\"'\\{\\}\\(\\)\\[\\]:; \t\r\n\f]"));

  }

  private static void macros(LexerlessGrammarBuilder b) {
    b.rule(_IDENT).is(b.token(GenericTokenType.IDENTIFIER, b.sequence(_NMSTART, b.zeroOrMore(_NMCHAR)))).skip();
    b.rule(_NAME).is(b.token(GenericTokenType.LITERAL, b.oneOrMore(_NMCHAR))).skip();
    b.rule(_NMSTART).is(
      b.firstOf(b.regexp(NMSTART_REGEX), "-", "*", _NONASCII, _ESCAPE)).skip();
    b.rule(_NONASCII).is(b.regexp(NONASCII_REGEX)).skip();
    b.rule(_UNICODE).is(
      b.regexp("\\\\[0-9a-f]{1,6}(\\r\\n|[ \\n\\r\\t\\f])?")).skip();
    b.rule(_ESCAPE).is(
      b.firstOf(_UNICODE, b.regexp("\\\\[^\\n\\r\\f0-9a-f]"))).skip();
    b.rule(_NMCHAR).is(
      b.firstOf(b.regexp(NMCHAR_REGEX), _NONASCII, _ESCAPE)).skip();
    b.rule(_NUM).is(b.token(GenericTokenType.LITERAL, b.sequence(b.optional(b.firstOf("-", "+")),
      b.firstOf(b.regexp("[0-9]*\\.[0-9]+"), b.regexp("[0-9]+"))))).skip();
    b.rule(_STRING).is(b.token(GenericTokenType.LITERAL, b.firstOf(_STRING1, _STRING2))).skip();
    b.rule(_STRING1).is(
      "\"",
      b.zeroOrMore(b.firstOf(b.regexp("[^\\n\\r\\f\\\\\"]"),
        b.sequence("\\", _NL), _ESCAPE)), "\"").skip();
    b.rule(_STRING2).is(
      "'",
      b.zeroOrMore(b.firstOf(b.regexp("[^\\n\\r\\f\\\\']"),
        b.sequence("\\", _NL), _ESCAPE)), "'").skip();
    b.rule(_BAD_STRING).is(b.firstOf(_BAD_STRING1, _BAD_STRING2)).skip();
    b.rule(_BAD_STRING1).is(
      "\"",
      b.zeroOrMore(b.regexp("[^\\n\\r\\f\\\\\"]"),
        b.sequence("\\", _NL), _ESCAPE), "\"").skip();
    b.rule(_BAD_STRING2).is(
      "'",
      b.zeroOrMore(b.regexp("[^\\n\\r\\f\\\\\']"),
        b.sequence("\\", _NL), _ESCAPE), "'").skip();
    b.rule(_BAD_COMMENT).is(b.firstOf(_BAD_COMMENT1, _BAD_COMMENT2)).skip();
    b.rule(_BAD_COMMENT1).is(b.regexp("\\/\\*[^*]*\\*+([^/*][^*]*\\*+)*")).skip();
    b.rule(_BAD_COMMENT2).is(b.regexp("\\/\\*[^*]*(\\*+[^/*][^*]*)*")).skip();
    b.rule(_BADURI).is(b.firstOf(_BADURI1, _BADURI2, _BADURI3)).skip();
    b.rule(_BADURI1).is(
      "url(",
      _W,
      b.zeroOrMore(b.firstOf(b.regexp("[!#$%&*-~]"), _NONASCII,
        _ESCAPE)), _W).skip();
    b.rule(_BADURI2).is("url(", _W, _STRING, _W).skip();
    b.rule(_BADURI3).is("url(", _W, _BAD_STRING).skip();
    b.rule(_NL).is(b.firstOf("\n", "\r\n", "\r", "\f")).skip();
    b.rule(_W).is(b.regexp("[ \\t\\r\\n\\f]*")).skip();
  }

  static Object addSpacing(Object value, LexerlessGrammarBuilder b) {
    return b.sequence(value, WHITESPACES);
  }

}
