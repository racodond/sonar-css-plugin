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
  cdo,
  cdc,

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
  delim,

  simpleSelector, // either a type selector or universal selector followed immediately by zero or more attribute selectors, ID selectors, or
                  // pseudo-classes, in any order
  typeSelector, // ident
  //ADD NAMESPACE
  //ADD SUBSTRING MATCHERS: ^= $= *=
  universalSelector, // If the universal selector is not the only component of a simple selector, the "*" may be omitted

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
    b.rule(stylesheet).is(
      b.zeroOrMore(b.firstOf(cdo, cdc, whiteSpace, statement)), eof);
    b.rule(statement).is(b.firstOf(ruleset, atRule));
    b.rule(atRule).is(atkeyword, whiteSpaces, b.zeroOrMore(any),
      b.firstOf(block, b.sequence(semiColon, whiteSpaces)));
    b.rule(block).is(
      lCurlyBracket,
      whiteSpaces,
      b.zeroOrMore(
        b.firstOf(
          any,
          block,
          b.sequence(atkeyword, whiteSpaces),
          b.sequence(semiColon, whiteSpaces))),
      rCurlyBracket,
      whiteSpaces);
    b.rule(ruleset).is(
      b.optional(selector),
      whiteSpaces,
      lCurlyBracket,
      whiteSpaces,
      b.optional(declaration),
      b.zeroOrMore(b.sequence(semiColon, whiteSpaces,
        b.optional(declaration))), rCurlyBracket, whiteSpaces);

    b.rule(selector).is(subSelector, b.zeroOrMore(b.firstOf(subSelector, b.sequence(whiteSpaces, ",", whiteSpaces))));
    b.rule(subSelector).is(simpleSelector, b.zeroOrMore(combinators, simpleSelector));
    b.rule(combinators).is(b.firstOf(descendantComb, adjacentComb, precededComb, childComb)).skip();
    b.rule(descendantComb).is(whiteSpace, b.nextNot(combinators));
    b.rule(childComb).is(b.optional(whiteSpace), ">", b.optional(b.zeroOrMore(whiteSpace)));
    b.rule(adjacentComb).is(b.optional(whiteSpace), "+", b.optional(b.zeroOrMore(whiteSpace)));
    b.rule(precededComb).is(b.optional(whiteSpace), "~", b.optional(b.zeroOrMore(whiteSpace)));
    b.rule(simpleSelector).is(b.firstOf(typeSelector, universalSelector)).skip();
    b.rule(typeSelector).is(ident, b.zeroOrMore(b.firstOf(attributeSelector, idSelector, classSelector, pseudo)));
    b.rule(universalSelector).is(
      b.firstOf(
        b.sequence("*", b.zeroOrMore(b.firstOf(attributeSelector, idSelector, classSelector, pseudo))),
        b.oneOrMore(b.firstOf(attributeSelector, idSelector, classSelector, pseudo))));
    b.rule(attributeSelector).is(b.oneOrMore(lBracket, ident, b.optional(b.firstOf(dashMatch, includes, "="), any), rBracket));
    b.rule(classSelector).is(b.oneOrMore(".", ident));
    b.rule(idSelector).is("#", ident);
    b.rule(pseudo).is(colon, any);

    b.rule(declaration)
      .is(property, whiteSpaces, colon, whiteSpaces, value);
    b.rule(property).is(ident);
    b.rule(value).is(
      b.oneOrMore(b.firstOf(any, block,
        b.sequence(atkeyword, whiteSpaces))));
    b.rule(any)
      .is(b.firstOf(
        b.sequence(function, whiteSpaces,
          b.zeroOrMore(b.firstOf(any, unused)),
          rParenthesis),
        b.sequence(lParenthesis,
          whiteSpaces,
          b.zeroOrMore(b.firstOf(any, unused)),
          rParenthesis),
        b.sequence(lBracket,
          whiteSpaces,
          b.zeroOrMore(b.firstOf(any, unused)), rBracket),
        percentage, dimension, string,
        uri, hash, unicodeRange, includes, dashMatch,
        ident, number, colon, delim),
        whiteSpaces).skipIfOneChild();
    b.rule(unused).is(
      b.firstOf(block, b.sequence(atkeyword, whiteSpaces),
        b.sequence(semiColon, whiteSpaces),
        b.sequence(cdo, whiteSpaces),
        b.sequence(cdc, whiteSpaces)));

    b.rule(eof).is(b.token(GenericTokenType.EOF, b.endOfInput())).skip();

  }

  private static void tokens(LexerlessGrammarBuilder b) {
    b.rule(ident).is(whiteSpaces, _ident);
    b.rule(atkeyword).is("@", ident);
    b.rule(string).is(whiteSpaces, _string);
    b.rule(bad_string).is(_badString);
    b.rule(bad_uri).is(_baduri);
    b.rule(bad_comment).is(_badcomment);
    b.rule(hash).is(whiteSpaces, "#", _name);
    b.rule(number).is(whiteSpaces, _num);
    b.rule(percentage).is(number, "%");
    b.rule(dimension).is(number, ident);
    b.rule(uri).is(
      whiteSpaces,
      b.firstOf(b.sequence("url(", _w, string, _w, rParenthesis), b
        .sequence("url(", _w, b.zeroOrMore(b.firstOf(
          b.regexp("[!#$%&*-\\[\\]-~]"), _nonascii,
          _escape)), _w, rParenthesis)));
    b.rule(unicodeRange)
      .is(b.regexp("u\\+[0-9a-f?]{1,6}(-[0-9a-f]{1,6})?"));
    b.rule(cdo).is("<!--");
    b.rule(cdc).is("-->");
    b.rule(colon).is(":");
    b.rule(semiColon).is(";");
    b.rule(lCurlyBracket).is("{");
    b.rule(rCurlyBracket).is("}");
    b.rule(lParenthesis).is("(");
    b.rule(rParenthesis).is(")");
    b.rule(lBracket).is("[");
    b.rule(rBracket).is("]");
    b.rule(whiteSpace).is(b.regexp("[ \\t\\r\\n\\f]+")).skip();
    b.rule(whiteSpaces).is(b.zeroOrMore(
      b.firstOf(
        b.skippedTrivia(whiteSpace),
        b.commentTrivia(b.regexp(CssLexer.COMMENT))))).skip();
    b.rule(comment).is(b.regexp("\\/\\*[^*]*\\*+([^/*][^*]*\\*+)*\\/"));
    b.rule(function).is(ident, lParenthesis);
    b.rule(includes).is("~=");
    b.rule(dashMatch).is("|=");
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
      b.firstOf(b.regexp("(?i)[_a-z]"), _nonascii, _escape)).skip();
    b.rule(_nonascii).is(b.regexp("[^\\x00-\\xED]")).skip();
    b.rule(_unicode).is(
      b.regexp("\\\\[0-9a-f]{1,6}(\\r\\n|[ \\n\\r\\t\\f])?")).skip();
    b.rule(_escape).is(
      b.firstOf(_unicode, b.regexp("\\\\[^\\n\\r\\f0-9a-f]"))).skip();
    b.rule(_nmchar).is(
      b.firstOf(b.regexp("(?i)[_a-z0-9-]"), _nonascii, _escape)).skip();
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

}
