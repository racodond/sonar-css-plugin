/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2017 David RACODON
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
package org.sonar.css.parser;

import com.sonar.sslr.api.GenericTokenType;
import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;

public enum LexicalGrammar implements GrammarRuleKey {

  /* High level nodes */
  STYLESHEET,

  EMPTY_STATEMENT,
  AT_RULE,
  RULESET,
  STATEMENT_BLOCK,
  AT_RULE_BLOCK,
  RULESET_BLOCK,
  PARENTHESIS_BLOCK_TREE,
  BRACKET_BLOCK_TREE,
  NAMESPACE,

  DECLARATION,
  PROPERTY_DECLARATION,
  VARIABLE_DECLARATION,

  PROPERTY,
  VARIABLE,
  DECLARATION_VALUE,
  SIMPLE_VALUE_WITHOUT_COMMA_SEPARATED_LIST,
  SIMPLE_VALUE,

  FUNCTION,
  FUNCTION_PARAMETERS,

  DIMENSION,
  PERCENTAGE,
  UNIT,
  STRING,
  STRING_NO_WS,
  NUMBER,
  IDENTIFIER,
  IDENTIFIER_NO_WS,
  HASH,
  AT_KEYWORD,
  URI,
  URI_CONTENT,
  UNICODE_RANGE,

  VALUE_COMMA_SEPARATED_LIST,

  DELIMITER,
  COMMA_DELIMITER,
  SEMICOLON_DELIMITER,

  IMPORTANT_FLAG,

  SELECTORS,
  SELECTOR,
  COMPOUND_SELECTOR,
  CLASS_SELECTOR,
  TYPE_SELECTOR,
  KEYFRAMES_SELECTOR,
  ID_SELECTOR,
  ATTRIBUTE_SELECTOR,
  PSEUDO_SELECTOR,
  PSEUDO_SELECTOR_NO_WS,
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
  STRING_LITERAL_NO_WS,
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
  OPEN_CURLY_BRACE_NO_WS,
  CLOSE_CURLY_BRACE,
  CLOSE_CURLY_BRACE_NO_WS,
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
  DEEP_COMBINATOR,
  DEEP_ALIAS_COMBINATOR,

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
  SPACING,

  /* Embedded CSS */
  FILE_WITH_EMBEDDED_CSS,
  CSS_IN_STYLE_TAG,
  OPENING_HTML_STYLE_TAG,
  CLOSING_HTML_STYLE_TAG,
  NON_CSS_TOKEN,

  /* SCSS */
  SCSS_NESTED_PROPERTIES_DECLARATION,

  SCSS_VARIABLE_DECLARATION,
  SCSS_VARIABLE_DECLARATION_AS_PARAMETER,
  SCSS_VARIABLE,
  SCSS_VARIABLE_ARGUMENT,
  SCSS_VARIABLE_PREFIX,
  SCSS_DOLLAR,
  SCSS_ELLIPSIS,
  SCSS_DEFAULT_FLAG,
  SCSS_DEFAULT_KEYWORD,
  SCSS_GLOBAL_FLAG,
  SCSS_GLOBAL_KEYWORD,

  SCSS_MIXIN_BLOCK,

  SCSS_PARENT_SELECTOR,
  SCSS_PARENT_SELECTOR_KEYWORD,
  SCSS_PARENT_SELECTOR_COMBINATOR,
  SCSS_PARENT_REFERENCING_SELECTOR,
  SCSS_PLACEHOLDER_SELECTOR,

  SCSS_EXTEND,
  SCSS_EXTEND_DIRECTIVE,
  SCSS_EXTEND_DIRECTIVE_LITERAL,
  SCSS_OPTIONAL_FLAG,
  SCSS_OPTIONAL_KEYWORD,

  SCSS_AT_ROOT,
  SCSS_AT_ROOT_DIRECTIVE,
  SCSS_AT_ROOT_DIRECTIVE_LITERAL,
  SCSS_AT_ROOT_PARAMETERS,
  SCSS_AT_ROOT_WITH,
  SCSS_AT_ROOT_WITHOUT,

  SCSS_FUNCTION,
  SCSS_FUNCTION_DIRECTIVE,
  SCSS_FUNCTION_DIRECTIVE_LITERAL,

  SCSS_MIXIN,
  SCSS_MIXIN_DIRECTIVE,
  SCSS_MIXIN_DIRECTIVE_LITERAL,

  SCSS_INCLUDE,
  SCSS_INCLUDE_DIRECTIVE,
  SCSS_INCLUDE_DIRECTIVE_LITERAL,

  SCSS_PARAMETERS,
  SCSS_PARAMETER,

  SCSS_CALL_PARAMETERS,
  SCSS_CALL_PARAMETER,

  SCSS_SASS_SCRIPT_EXPRESSION,
  SCSS_SASS_SCRIPT_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST,

  SCSS_MAP,
  SCSS_MAP_ENTRY,

  SCSS_INTERPOLATED_IDENTIFIER,
  SCSS_INTERPOLATED_IDENTIFIER_NO_WS,
  SCSS_IDENT_INTERPOLATED_IDENTIFIER_NO_WS,
  SCSS_IDENT_INTERPOLATED_IDENTIFIER,

  SCSS_CONTENT,
  SCSS_CONTENT_DIRECTIVE,
  SCSS_CONTENT_DIRECTIVE_LITERAL,

  SCSS_DEBUG,
  SCSS_DEBUG_DIRECTIVE,
  SCSS_DEBUG_DIRECTIVE_LITERAL,

  SCSS_WARN,
  SCSS_WARN_DIRECTIVE,
  SCSS_WARN_DIRECTIVE_LITERAL,

  SCSS_ERROR,
  SCSS_ERROR_DIRECTIVE,
  SCSS_ERROR_DIRECTIVE_LITERAL,

  SCSS_IF_ELSE_IF_ELSE,

  SCSS_IF,
  SCSS_IF_DIRECTIVE,
  SCSS_IF_DIRECTIVE_LITERAL,

  SCSS_ELSE,
  SCSS_ELSE_DIRECTIVE,
  SCSS_ELSE_DIRECTIVE_LITERAL,

  SCSS_ELSE_IF,
  SCSS_ELSE_IF_DIRECTIVE,
  SCSS_ELSE_IF_DIRECTIVE_LITERAL,

  SCSS_WHILE,
  SCSS_WHILE_DIRECTIVE,
  SCSS_WHILE_DIRECTIVE_LITERAL,

  SCSS_EACH,
  SCSS_EACH_DIRECTIVE,
  SCSS_EACH_DIRECTIVE_LITERAL,

  SCSS_FOR,
  SCSS_FOR_DIRECTIVE,
  SCSS_FOR_DIRECTIVE_LITERAL,

  SCSS_RETURN,
  SCSS_RETURN_DIRECTIVE,
  SCSS_RETURN_DIRECTIVE_LITERAL,

  SCSS_OPERATOR,
  SCSS_OPERATOR_LITERAL,

  SCSS_CONDITION,

  SCSS_MULTILINE_STRING,
  SCSS_MULTILINE_STRING_LITERAL,

  /* Less */
  LESS_VARIABLE_DECLARATION,
  LESS_VARIABLE_DECLARATION_AS_PARAMETER,
  LESS_VARIABLE,
  LESS_VARIABLE_PREFIX,
  LESS_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST,

  LESS_INTERPOLATED_IDENTIFIER,
  LESS_INTERPOLATED_IDENTIFIER_NO_WS,
  LESS_IDENT_INTERPOLATED_IDENTIFIER,
  LESS_IDENT_INTERPOLATED_IDENTIFIER_NO_WS,

  LESS_EXTEND,
  LESS_EXTEND_KEYWORD,
  LESS_EXTEND_PREFIX,

  LESS_PARENT_SELECTOR,
  LESS_PARENT_SELECTOR_KEYWORD,
  LESS_PARENT_REFERENCING_SELECTOR,

  LESS_MIXIN_CALL,
  LESS_PARENT_SELECTOR_COMBINATOR,
  LESS_MIXIN_GUARD,
  LESS_MIXIN_GUARD_CONDITION,
  LESS_MIXIN_GUARD_WHEN,
  LESS_MIXIN_GUARD_NOT,
  LESS_MIXIN_GUARD_AND,
  LESS_MIXIN_GUARD_OR,
  LESS_MIXIN_PARAMETERS,
  LESS_MIXIN_PARAMETER,
  LESS_MIXIN_PARAMETER_DEFAULT_VALUE,
  LESS_IDENTIFIER_NO_WS_NOR_WHEN,
  LESS_IDENT_IDENTIFIER_NO_WS_NOR_WHEN,
  LESS_MERGE,
  LESS_ESCAPING,
  LESS_ESCAPING_SYMBOL,

  LESS_OPERATOR,
  LESS_OPERATOR_LITERAL;

  private static final String CSS_COMMENT_REGEX = "(?:" + "(?:/\\*[\\s\\S]*?\\*/)" + "|" + "(?:\\<\\!--[\\s\\S]*?--\\>)" + ")";
  private static final String LESS_COMMENT_REGEX = "(?:" + "(?:/\\*[\\s\\S]*?\\*/)" + "|" + "(?:\\<\\!--[\\s\\S]*?--\\>)" + "|" + "//[^\\n\\r]*+" + ")";
  private static final String SCSS_COMMENT_REGEX = "(?:" + "(?:/\\*[\\s\\S]*?\\*/)" + "|" + "//[^\\n\\r]*+" + ")";

  public static LexerlessGrammarBuilder createCssGrammar() {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
    macros(b);
    spacing(b, CSS_COMMENT_REGEX);
    tokens(b);
    b.setRootRule(STYLESHEET);
    return b;
  }

  public static LexerlessGrammarBuilder createEmbeddedCssGrammar() {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
    macros(b);
    spacing(b, CSS_COMMENT_REGEX);
    tokens(b);
    embeddedCss(b);
    b.setRootRule(FILE_WITH_EMBEDDED_CSS);
    return b;
  }

  public static LexerlessGrammarBuilder createScssGrammar() {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
    macros(b);
    spacing(b, SCSS_COMMENT_REGEX);
    tokens(b);
    scss(b);
    b.setRootRule(STYLESHEET);
    return b;
  }

  public static LexerlessGrammarBuilder createLessGrammar() {
    LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
    macros(b);
    spacing(b, LESS_COMMENT_REGEX);
    tokens(b);
    less(b);
    b.setRootRule(STYLESHEET);
    return b;
  }

  private static void tokens(LexerlessGrammarBuilder b) {
    b.rule(IDENT_IDENTIFIER).is(SPACING, b.token(GenericTokenType.LITERAL, _IDENT));

    b.rule(IDENT_IDENTIFIER_NO_WS).is(b.token(GenericTokenType.LITERAL, _IDENT));

    b.rule(NUMBER_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, _NUM));

    b.rule(STRING_LITERAL).is(SPACING, STRING_LITERAL_NO_WS);

    b.rule(STRING_LITERAL_NO_WS).is(b.token(GenericTokenType.LITERAL, b.firstOf(_STRING1, _STRING2)));

    b.rule(UNICODE_RANGE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, b.regexp("u\\+[0-9a-f?]{1,6}(-[0-9a-f]{1,6})?")));

    b.rule(VARIABLE_PREFIX).is(SPACING, b.token(GenericTokenType.LITERAL, "--"));

    b.rule(NAME).is(b.token(GenericTokenType.LITERAL, b.oneOrMore(_NMCHAR)));

    b.rule(PSEUDO_PREFIX).is(b.token(GenericTokenType.LITERAL, b.sequence(b.firstOf("::", ":"), b.nextNot("extend"))));

    b.rule(URI_CONTENT_LITERAL).is(b.token(GenericTokenType.LITERAL,
      b.sequence(
        _W,
        b.oneOrMore(
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
    b.rule(OPEN_CURLY_BRACE_NO_WS).is("{");
    b.rule(CLOSE_CURLY_BRACE).is(SPACING, "}");
    b.rule(CLOSE_CURLY_BRACE_NO_WS).is("}");
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
    b.rule(DEEP_COMBINATOR).is(SPACING, "/deep/", SPACING);
    b.rule(DEEP_ALIAS_COMBINATOR).is(SPACING, ">>>", SPACING);

    b.rule(DOT).is(".");
    b.rule(EXCLAMATION_MARK).is(SPACING, "!");
    b.rule(PIPE).is("|");
    b.rule(IMPORTANT_KEYWORD).is(SPACING, matchCaseInsensitive(b, "important"));

    b.rule(FROM).is(SPACING, matchCaseInsensitive(b, "from"));
    b.rule(TO).is(SPACING, matchCaseInsensitive(b, "to"));

    b.rule(URL_FUNCTION_NAME).is(SPACING, matchCaseInsensitive(b, "url"));

    b.rule(DELIM).is(SPACING, b.regexp("[^\"'\\{\\}\\(\\)\\[\\]\\\\,:; \t\r\n\f]"));

    b.rule(BOM).is("\ufeff");
    b.rule(EOF).is(SPACING, b.token(GenericTokenType.EOF, b.endOfInput()));

  }

  private static void spacing(LexerlessGrammarBuilder b, String commentRegex) {
    b.rule(SPACING).is(
      b.skippedTrivia(b.regexp("(?<!\\\\)[\\s]*+")),
      b.zeroOrMore(
        b.commentTrivia(b.regexp(commentRegex)),
        b.skippedTrivia(b.regexp("(?<!\\\\)[\\s]*+"))));
  }

  private static void macros(LexerlessGrammarBuilder b) {
    b.rule(_IDENT).is(
      b.firstOf(
        b.regexp("(?i)(progid:DXImageTransform\\.Microsoft\\.[a-z]+)"),
        b.sequence(_NMSTART, b.zeroOrMore(_NMCHAR))))
      .skip();

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

  private static void embeddedCss(LexerlessGrammarBuilder b) {
    String openingHtmlStyleTagRegex = "<style[^>]+type[\\s]*=[\\s]*\"text/css\"[^>]*>";

    b.rule(OPENING_HTML_STYLE_TAG).is(
      SPACING,
      b.token(
        GenericTokenType.LITERAL,
        b.regexp(openingHtmlStyleTagRegex)));

    b.rule(CLOSING_HTML_STYLE_TAG).is(
      SPACING,
      b.token(
        GenericTokenType.LITERAL,
        b.regexp("</style[\\s]*>")));

    b.rule(NON_CSS_TOKEN).is(
      SPACING,
      b.token(
        GenericTokenType.LITERAL,
        b.firstOf(
          b.regexp(".+?(?=" + openingHtmlStyleTagRegex + ")"),
          b.regexp(".+"))));
  }

  private static void scss(LexerlessGrammarBuilder b) {
    b.rule(SCSS_DOLLAR).is(b.token(GenericTokenType.LITERAL, "$"));
    b.rule(SCSS_ELLIPSIS).is(b.token(GenericTokenType.LITERAL, "..."));
    b.rule(SCSS_PARENT_SELECTOR_KEYWORD).is("&");

    b.rule(SCSS_VARIABLE_PREFIX).is(SPACING, SCSS_DOLLAR);
    b.rule(SCSS_DEFAULT_KEYWORD).is(SPACING, b.token(GenericTokenType.LITERAL, "!default"));
    b.rule(SCSS_GLOBAL_KEYWORD).is(SPACING, b.token(GenericTokenType.LITERAL, "!global"));
    b.rule(SCSS_OPTIONAL_KEYWORD).is(SPACING, b.token(GenericTokenType.LITERAL, "!optional"));

    b.rule(SCSS_MIXIN_DIRECTIVE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, "mixin"));
    b.rule(SCSS_INCLUDE_DIRECTIVE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, "include"));
    b.rule(SCSS_EXTEND_DIRECTIVE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, "extend"), SPACING);

    b.rule(SCSS_CONTENT_DIRECTIVE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, "content"));

    b.rule(SCSS_DEBUG_DIRECTIVE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, "debug"));
    b.rule(SCSS_WARN_DIRECTIVE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, "warn"));
    b.rule(SCSS_ERROR_DIRECTIVE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, "error"));

    b.rule(SCSS_IF_DIRECTIVE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, "if"));
    b.rule(SCSS_ELSE_DIRECTIVE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, "else"));
    b.rule(SCSS_ELSE_IF_DIRECTIVE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, b.sequence("else", SPACING, "if")));

    b.rule(SCSS_FOR_DIRECTIVE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, "for"));
    b.rule(SCSS_EACH_DIRECTIVE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, "each"));
    b.rule(SCSS_WHILE_DIRECTIVE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, "while"));

    b.rule(SCSS_FUNCTION_DIRECTIVE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, "function"));
    b.rule(SCSS_RETURN_DIRECTIVE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, "return"));

    b.rule(SCSS_AT_ROOT_DIRECTIVE_LITERAL).is(SPACING, b.token(GenericTokenType.LITERAL, "at-root"));
    b.rule(SCSS_AT_ROOT_WITH).is(SPACING, b.token(GenericTokenType.LITERAL, "with"));
    b.rule(SCSS_AT_ROOT_WITHOUT).is(SPACING, b.token(GenericTokenType.LITERAL, "without"));

    b.rule(SCSS_IDENT_INTERPOLATED_IDENTIFIER_NO_WS).is(
      b.token(GenericTokenType.LITERAL,
        b.sequence(
          b.optional(IDENT_IDENTIFIER_NO_WS),
          b.regexp("#\\{[^\\n\\r\\f\\}]*\\}"),
          b.zeroOrMore(
            b.firstOf(
              b.regexp("#\\{[^\\n\\r\\f\\}]*\\}"),
              _NMCHAR)))));

    b.rule(SCSS_IDENT_INTERPOLATED_IDENTIFIER).is(SPACING, SCSS_IDENT_INTERPOLATED_IDENTIFIER_NO_WS);

    b.rule(SCSS_OPERATOR_LITERAL).is(
      SPACING,
      b.token(GenericTokenType.LITERAL,
        b.firstOf(
          "and",
          "or",
          "not",
          "+",
          "-",
          "/",
          "*",
          "%",
          "==",
          "!=",
          "=",
          ">",
          ">=",
          "<",
          "<=")));

    b.rule(SCSS_MULTILINE_STRING_LITERAL).is(
      SPACING,
      b.token(
        GenericTokenType.LITERAL,
        b.regexp("\"[^\"\\n\\r]*[\\n\\r]+[^\"]*\"|'[^'\\n\\r]*[\\n\\r]+[^']*'")));
  }

  private static void less(LexerlessGrammarBuilder b) {
    b.rule(LESS_VARIABLE_PREFIX).is(SPACING, b.token(GenericTokenType.LITERAL, "@"));

    b.rule(LESS_IDENT_INTERPOLATED_IDENTIFIER_NO_WS).is(
      b.token(GenericTokenType.LITERAL,
        b.sequence(
          b.optional(IDENT_IDENTIFIER_NO_WS),
          b.sequence(LESS_VARIABLE_PREFIX, OPEN_CURLY_BRACE, b.oneOrMore(_NMCHAR), CLOSE_CURLY_BRACE),
          b.zeroOrMore(
            b.firstOf(
              b.sequence(LESS_VARIABLE_PREFIX, OPEN_CURLY_BRACE, b.oneOrMore(_NMCHAR), CLOSE_CURLY_BRACE),
              _NMCHAR)))));

    b.rule(LESS_IDENT_IDENTIFIER_NO_WS_NOR_WHEN).is(b.token(GenericTokenType.LITERAL,
      b.sequence(
        b.nextNot(b.regexp("when[\\s]+|when\\(")),
        _IDENT)));

    b.rule(LESS_IDENT_INTERPOLATED_IDENTIFIER).is(SPACING, LESS_IDENT_INTERPOLATED_IDENTIFIER_NO_WS);

    b.rule(LESS_EXTEND_KEYWORD).is("extend");
    b.rule(LESS_EXTEND_PREFIX).is(SPACING, b.token(GenericTokenType.LITERAL, b.sequence(":", LESS_EXTEND_KEYWORD)));

    b.rule(LESS_PARENT_SELECTOR_KEYWORD).is("&");

    b.rule(LESS_MIXIN_GUARD_WHEN).is(SPACING, b.token(GenericTokenType.LITERAL, "when"));
    b.rule(LESS_MIXIN_GUARD_NOT).is(SPACING, b.token(GenericTokenType.LITERAL, "not"));
    b.rule(LESS_MIXIN_GUARD_AND).is(SPACING, b.token(GenericTokenType.LITERAL, "and"));
    b.rule(LESS_MIXIN_GUARD_OR).is(SPACING, b.token(GenericTokenType.LITERAL, ","));

    b.rule(LESS_MERGE).is(b.token(GenericTokenType.LITERAL, b.firstOf("+_", "+")));

    b.rule(LESS_ESCAPING_SYMBOL).is(SPACING, b.token(GenericTokenType.LITERAL, "~"));

    b.rule(LESS_OPERATOR_LITERAL).is(
      SPACING,
      b.token(GenericTokenType.LITERAL,
        b.firstOf(
          "+",
          "-",
          "/",
          "*"
        )));
  }

  private static Object matchCaseInsensitive(LexerlessGrammarBuilder b, String value) {
    return b.regexp("(?i)" + value);
  }

}
