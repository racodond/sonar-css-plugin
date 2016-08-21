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
package org.sonar.css.parser;

import com.sonar.sslr.api.GenericTokenType;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

/**
 * CSS grammar based on:
 * - https://www.w3.org/TR/CSS22/grammar.html
 * - https://drafts.csswg.org/css-syntax-3/
 */
public enum CssLexicalGrammar implements GrammarRuleKey {

  /* High level nodes */
  STYLESHEET,

  AT_RULE,
  AT_RULE_BLOCK,
  RULESET,
  RULESET_BLOCK,
  PARENTHESIS_BLOCK_TREE,
  BRACKET_BLOCK_TREE,
  NAMESPACE,

  DECLARATIONS,
  DECLARATION,
  PROPERTY_DECLARATION,
  VARIABLE_DECLARATION,

  PROPERTY,
  VARIABLE,
  VALUE,
  FUNCTION,

  DIMENSION,
  PERCENTAGE,
  UNIT,
  STRING,
  NUMBER,
  IDENTIFIER,
  IDENTIFIER_NO_WS,
  HASH,
  AT_KEYWORD,
  URI,
  URI_CONTENT,
  UNICODE_RANGE,
  DELIMITER,

  IMPORTANT,

  SELECTORS,
  SELECTOR,
  COMPOUND_SELECTOR,
  CLASS_SELECTOR,
  TYPE_SELECTOR,
  KEYFRAMES_SELECTOR,
  ID_SELECTOR,
  ATTRIBUTE_SELECTOR,
  PSEUDO_SELECTOR,
  PSEUDO_FUNCTION,
  PSEUDO_IDENTIFIER,
  ATTRIBUTE_MATCHER_EXPRESSION,
  ATTRIBUTE_MATCHER,
  SELECTOR_COMBINATOR,
  CASE_INSENSITIVE_FLAG,

  /* Tokens */
  IDENT_IDENTIFIER,
  IDENT_IDENTIFIER_NO_WS,
  STRING_LITERAL,
  NUMBER_LITERAL,
  UNIT_LITERAL,
  UNICODE_RANGE_LITERAL,
  HASH_SYMBOL,
  HASH_SYMBOL_NO_WS,
  AT_SYMBOL,
  URI_CONTENT_LITERAL,
  URL_FUNCTION_NAME,

  PERCENTAGE_SYMBOL,
  VARIABLE_PREFIX,
  PSEUDO_PREFIX,

  IMPORTANT_KEYWORD,
  EXCLAMATION_MARK,
  DOT,
  PIPE,
  CASE_INSENSITIVE_FLAG_LITERAL,

  COLON,
  SEMICOLON,
  COMMA,

  OPEN_CURLY_BRACE,
  CLOSE_CURLY_BRACE,
  OPEN_PARENTHESIS,
  OPEN_PARENTHESIS_NO_WS,
  CLOSE_PARENTHESIS,
  CLOSE_PARENTHESIS_NO_WS,
  OPEN_BRACKET,
  OPEN_BRACKET_NO_WS,
  CLOSE_BRACKET,
  CLOSE_BRACKET_NO_WS,

  INCLUDE_ATTRIBUTE_MATCHER,
  DASH_ATTRIBUTE_MATCHER,
  EQUALS_ATTRIBUTE_MATCHER,
  SUBSTRING_ATTRIBUTE_MATCHER,
  PREFIX_ATTRIBUTE_MATCHER,
  SUFFIX_ATTRIBUTE_MATCHER,

  DESCENDANT_COMBINATOR,
  DESCENDANT_COMBINATOR_WS,
  CHILD_COMBINATOR,
  NEXT_SIBLING_COMBINATOR,
  FOLLOWING_SIBLING_COMBINATOR,
  COLUMN_COMBINATOR,

  FROM,
  TO,

  NAME,
  DELIM,

  BOM,
  EOF,

  /* Macros */
  _IDENT,
  _NMSTART,
  _NONASCII,
  _UNICODE,
  _ESCAPE,
  _NAME,
  _NMCHAR,
  _NUM,
  _STRING1,
  _STRING2,
  _NL,
  _W,

  /* Spacing */
  SPACING;

  public static LexerlessGrammarBuilder createGrammar() {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
    macros(b);
    spacing(b);
    tokens(b);
    b.setRootRule(STYLESHEET);
    return b;
  }

  private static void tokens(LexerlessGrammarBuilder b) {
    b.rule(IDENT_IDENTIFIER).is(SPACING, b.token(GenericTokenType.LITERAL, _IDENT));

    b.rule(IDENT_IDENTIFIER_NO_WS).is(b.token(GenericTokenType.LITERAL, _IDENT));

    b.rule(NUMBER_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, _NUM));

    b.rule(STRING_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, b.firstOf(_STRING1, _STRING2)));

    b.rule(UNICODE_RANGE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, b.regexp("u\\+[0-9a-f?]{1,6}(-[0-9a-f]{1,6})?")));

    b.rule(VARIABLE_PREFIX).is(SPACING, b.token(GenericTokenType.LITERAL, "--"));

    b.rule(NAME).is(b.token(GenericTokenType.LITERAL, b.oneOrMore(_NMCHAR)));

    b.rule(PSEUDO_PREFIX).is(b.token(GenericTokenType.LITERAL, b.firstOf("::", ":")));

    b.rule(URI_CONTENT_LITERAL).is(b.token(GenericTokenType.LITERAL,
      b.sequence(
        _W,
        b.zeroOrMore(
          b.firstOf(
            b.regexp("[!#$%&*-\\[\\]-~]+"),
            _NONASCII,
            _ESCAPE)),
        _W)));

    b.rule(UNIT_LITERAL).is(b.token(GenericTokenType.LITERAL, b.firstOf(
      matchCaseInsensitive(b, "em"),
      matchCaseInsensitive(b, "ex"),
      matchCaseInsensitive(b, "ch"),
      matchCaseInsensitive(b, "rem"),
      matchCaseInsensitive(b, "vw"),
      matchCaseInsensitive(b, "vh"),
      matchCaseInsensitive(b, "vmin"),
      matchCaseInsensitive(b, "vmax"),
      matchCaseInsensitive(b, "cm"),
      matchCaseInsensitive(b, "mm"),
      matchCaseInsensitive(b, "in"),
      matchCaseInsensitive(b, "px"),
      matchCaseInsensitive(b, "pt"),
      matchCaseInsensitive(b, "pc"),
      matchCaseInsensitive(b, "ms"),
      matchCaseInsensitive(b, "s"),
      matchCaseInsensitive(b, "Hz"),
      matchCaseInsensitive(b, "dB"),
      matchCaseInsensitive(b, "kHz"),
      matchCaseInsensitive(b, "deg"),
      matchCaseInsensitive(b, "grad"),
      matchCaseInsensitive(b, "rad"),
      matchCaseInsensitive(b, "turn"),
      matchCaseInsensitive(b, "dpi"),
      matchCaseInsensitive(b, "dpcm"),
      matchCaseInsensitive(b, "dppx"))));

    b.rule(PERCENTAGE_SYMBOL).is("%");
    b.rule(HASH_SYMBOL).is(SPACING, HASH_SYMBOL_NO_WS);
    b.rule(HASH_SYMBOL_NO_WS).is("#");
    b.rule(AT_SYMBOL).is(SPACING, "@");

    b.rule(CASE_INSENSITIVE_FLAG_LITERAL).is(SPACING, "i");

    b.rule(COLON).is(SPACING, ":");
    b.rule(SEMICOLON).is(SPACING, ";");
    b.rule(COMMA).is(SPACING, ",", SPACING);

    b.rule(OPEN_CURLY_BRACE).is(SPACING, "{");
    b.rule(CLOSE_CURLY_BRACE).is(SPACING, "}");
    b.rule(OPEN_PARENTHESIS).is(SPACING, OPEN_PARENTHESIS_NO_WS);
    b.rule(OPEN_PARENTHESIS_NO_WS).is("(");
    b.rule(CLOSE_PARENTHESIS).is(SPACING, CLOSE_PARENTHESIS_NO_WS);
    b.rule(CLOSE_PARENTHESIS_NO_WS).is(")");
    b.rule(OPEN_BRACKET).is(SPACING, OPEN_BRACKET_NO_WS);
    b.rule(OPEN_BRACKET_NO_WS).is("[");
    b.rule(CLOSE_BRACKET).is(SPACING, CLOSE_BRACKET_NO_WS);
    b.rule(CLOSE_BRACKET_NO_WS).is("]");

    b.rule(INCLUDE_ATTRIBUTE_MATCHER).is(SPACING, "~=");
    b.rule(DASH_ATTRIBUTE_MATCHER).is(SPACING, "|=");
    b.rule(EQUALS_ATTRIBUTE_MATCHER).is(SPACING, "=");
    b.rule(SUBSTRING_ATTRIBUTE_MATCHER).is(SPACING, "*=");
    b.rule(PREFIX_ATTRIBUTE_MATCHER).is(SPACING, "^=");
    b.rule(SUFFIX_ATTRIBUTE_MATCHER).is(SPACING, "$=");

    b.rule(DESCENDANT_COMBINATOR).is(SPACING, ">>", SPACING);
    b.rule(DESCENDANT_COMBINATOR_WS).is(
      b.token(GenericTokenType.LITERAL,
        b.sequence(
          SPACING,
          b.nextNot(b.firstOf(">", "+", "~")),
          b.next(COMPOUND_SELECTOR))));

    b.rule(CHILD_COMBINATOR).is(SPACING, ">", SPACING);
    b.rule(NEXT_SIBLING_COMBINATOR).is(SPACING, "+", SPACING);
    b.rule(FOLLOWING_SIBLING_COMBINATOR).is(SPACING, "~", SPACING);
    b.rule(COLUMN_COMBINATOR).is(SPACING, "||", SPACING);

    b.rule(DOT).is(".");
    b.rule(EXCLAMATION_MARK).is(SPACING, "!");
    b.rule(PIPE).is("|");
    b.rule(IMPORTANT_KEYWORD).is(SPACING, matchCaseInsensitive(b, "important"));

    b.rule(FROM).is(SPACING, matchCaseInsensitive(b, "from"));
    b.rule(TO).is(SPACING, matchCaseInsensitive(b, "to"));

    b.rule(URL_FUNCTION_NAME).is(SPACING, matchCaseInsensitive(b, "url"));

    b.rule(DELIM).is(SPACING, b.regexp("[^\"'\\{\\}\\(\\)\\[\\]:; \t\r\n\f]"));

    b.rule(BOM).is("\ufeff");
    b.rule(EOF).is(SPACING, b.token(GenericTokenType.EOF, b.endOfInput()));

  }

  private static void spacing(LexerlessGrammarBuilder b) {
    String COMMENT_REGEX = "(?:/\\*[\\s\\S]*?\\*/)";
    String COMMENT2_REGEX = "(?:\\<\\!--[\\s\\S]*?--\\>)";

    b.rule(SPACING).is(
      b.skippedTrivia(b.regexp("(?<!\\\\)[\\s]*+")),
      b.zeroOrMore(
        b.commentTrivia(b.regexp("(?:" + COMMENT_REGEX + "|" + COMMENT2_REGEX + ")")),
        b.skippedTrivia(b.regexp("(?<!\\\\)[\\s]*+"))));
  }

  private static void macros(LexerlessGrammarBuilder b) {
    b.rule(_IDENT).is(b.sequence(_NMSTART, b.zeroOrMore(_NMCHAR))).skip();

    b.rule(_NMSTART).is(
      b.firstOf(b.regexp("(?i)[_a-z]"), "-", "*", _NONASCII, _ESCAPE)).skip();

    b.rule(_NONASCII).is(b.regexp("[^\\x00-\\xED]")).skip();

    b.rule(_UNICODE).is(
      b.regexp("\\\\[0-9a-f]{1,6}(\\r\\n|[ \\n\\r\\t\\f])?")).skip();

    b.rule(_ESCAPE).is(
      b.firstOf(_UNICODE, b.regexp("\\\\[^\\n\\r\\f0-9a-f]"))).skip();

    b.rule(_NMCHAR).is(
      b.firstOf(b.regexp("(?i)[_a-z0-9-]"), _NONASCII, _ESCAPE)).skip();

    b.rule(_NUM).is(
      b.optional(
        b.firstOf("-", "+")),
      b.firstOf(
        b.regexp("[0-9]*\\.[0-9]+"),
        b.regexp("[0-9]+")))
      .skip();

    b.rule(_STRING1).is(
      "\"",
      b.zeroOrMore(
        b.firstOf(b.regexp("[^\\n\\r\\f\\\\\"]"),
          b.sequence("\\", _NL),
          _ESCAPE)),
      "\"")
      .skip();

    b.rule(_STRING2).is(
      "'",
      b.zeroOrMore(
        b.firstOf(
          b.regexp("[^\\n\\r\\f\\\\']"),
          b.sequence("\\", _NL),
          _ESCAPE)),
      "'")
      .skip();

    b.rule(_NL).is(b.firstOf("\n", "\r\n", "\r", "\f")).skip();

    b.rule(_W).is(b.regexp("[ \\t\\r\\n\\f]*")).skip();
  }

  private static Object matchCaseInsensitive(LexerlessGrammarBuilder b, String value) {
    return b.regexp("(?i)" + value);
  }

}
