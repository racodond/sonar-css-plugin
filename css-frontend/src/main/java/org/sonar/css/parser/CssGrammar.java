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

import com.sonar.sslr.api.typed.GrammarBuilder;
import org.sonar.css.tree.impl.InternalSyntaxToken;
import org.sonar.css.tree.impl.SelectorCombinationList;
import org.sonar.css.tree.impl.SyntaxList;
import org.sonar.plugins.css.api.tree.*;

public class CssGrammar {

  private final GrammarBuilder<InternalSyntaxToken> b;
  private final TreeFactory f;

  public CssGrammar(GrammarBuilder<InternalSyntaxToken> b, TreeFactory f) {
    this.b = b;
    this.f = f;
  }

  public StyleSheetTree STYLESHEET() {
    return b.<StyleSheetTree>nonterminal(CssLexicalGrammar.STYLESHEET).is(
      f.stylesheet(
        b.optional(b.token(CssLexicalGrammar.BOM)),
        b.zeroOrMore(
          b.firstOf(
            AT_RULE(),
            RULESET())),
        b.token(CssLexicalGrammar.EOF)));
  }

  public AtRuleTree AT_RULE() {
    return b.<AtRuleTree>nonterminal(CssLexicalGrammar.AT_RULE).is(
      f.atRule(
        AT_KEYWORD(),
        b.zeroOrMore(ANY()),
        b.optional(AT_RULE_BLOCK()),
        b.optional(b.token(CssLexicalGrammar.SEMICOLON))));
  }

  public RulesetTree RULESET() {
    return b.<RulesetTree>nonterminal(CssLexicalGrammar.RULESET).is(
      f.ruleset(
        b.optional(b.token(CssLexicalGrammar.SPACING)),
        b.optional(SELECTORS()),
        RULESET_BLOCK()));
  }

  public AtRuleBlockTree AT_RULE_BLOCK() {
    return b.<AtRuleBlockTree>nonterminal(CssLexicalGrammar.AT_RULE_BLOCK).is(
      f.atRuleBlock(
        b.token(CssLexicalGrammar.OPEN_CURLY_BRACE),
        b.zeroOrMore(
          b.firstOf(
            DECLARATIONS(),
            AT_RULE(),
            RULESET())),
        b.token(CssLexicalGrammar.CLOSE_CURLY_BRACE)));
  }

  public RulesetBlockTree RULESET_BLOCK() {
    return b.<RulesetBlockTree>nonterminal(CssLexicalGrammar.RULESET_BLOCK).is(
      f.rulesetBlock(
        b.token(CssLexicalGrammar.OPEN_CURLY_BRACE),
        b.optional(DECLARATIONS()),
        b.token(CssLexicalGrammar.CLOSE_CURLY_BRACE)));
  }

  public ValueTree VALUE() {
    return b.<ValueTree>nonterminal(CssLexicalGrammar.VALUE).is(
      f.value(
        b.oneOrMore(
          b.firstOf(
            ANY(),
            RULESET_BLOCK(),
            AT_KEYWORD()))));
  }

  public Tree ANY() {
    return b.<Tree>nonterminal().is(
      b.firstOf(
        URI(),
        FUNCTION(),
        PSEUDO_SELECTOR(),
        PARENTHESIS_BLOCK(),
        BRACKET_BLOCK(),
        PERCENTAGE(),
        DIMENSION(),
        NUMBER(),
        STRING(),
        HASH(),
        UNICODE_RANGE(),
        IDENTIFIER(),
        IMPORTANT(),
        b.token(CssLexicalGrammar.COLON),
        DELIMITER()));
  }

  public ParenthesisBlockTree PARENTHESIS_BLOCK() {
    return b.<ParenthesisBlockTree>nonterminal(CssLexicalGrammar.PARENTHESIS_BLOCK_TREE).is(
      f.parenthesisBlock(
        b.token(CssLexicalGrammar.OPEN_PARENTHESIS),
        b.zeroOrMore(ANY()),
        b.token(CssLexicalGrammar.CLOSE_PARENTHESIS)));
  }

  public BracketBlockTree BRACKET_BLOCK() {
    return b.<BracketBlockTree>nonterminal(CssLexicalGrammar.BRACKET_BLOCK_TREE).is(
      f.bracketBlock(
        b.token(CssLexicalGrammar.OPEN_BRACKET),
        b.zeroOrMore(ANY()),
        b.token(CssLexicalGrammar.CLOSE_BRACKET)));
  }

  public DeclarationsTree DECLARATIONS() {
    return b.<DeclarationsTree>nonterminal(CssLexicalGrammar.DECLARATIONS).is(
      f.declarations(DECLARATION_LIST()));
  }

  public SyntaxList<DeclarationTree> DECLARATION_LIST() {
    return b.<SyntaxList<DeclarationTree>>nonterminal().is(
      b.firstOf(
        f.declarationList(DECLARATION(), b.token(CssLexicalGrammar.SEMICOLON), DECLARATION_LIST()),
        f.declarationList(DECLARATION(), b.token(CssLexicalGrammar.SEMICOLON)),
        f.declarationList(b.token(CssLexicalGrammar.SEMICOLON), DECLARATION_LIST()),
        f.declarationList(b.token(CssLexicalGrammar.SEMICOLON)),
        f.declarationList(DECLARATION())));
  }

  public DeclarationTree DECLARATION() {
    return b.<DeclarationTree>nonterminal(CssLexicalGrammar.DECLARATION).is(
      b.firstOf(
        VARIABLE_DECLARATION(),
        PROPERTY_DECLARATION()));
  }

  public PropertyDeclarationTree PROPERTY_DECLARATION() {
    return b.<PropertyDeclarationTree>nonterminal(CssLexicalGrammar.PROPERTY_DECLARATION).is(
      f.propertyDeclaration(
        PROPERTY(),
        b.token(CssLexicalGrammar.COLON),
        VALUE()));
  }

  public VariableDeclarationTree VARIABLE_DECLARATION() {
    return b.<VariableDeclarationTree>nonterminal(CssLexicalGrammar.VARIABLE_DECLARATION).is(
      f.variableDeclaration(
        VARIABLE(),
        b.token(CssLexicalGrammar.COLON),
        VALUE()));
  }

  public PropertyTree PROPERTY() {
    return b.<PropertyTree>nonterminal(CssLexicalGrammar.PROPERTY).is(
      f.property(IDENTIFIER()));
  }

  public FunctionTree FUNCTION() {
    return b.<FunctionTree>nonterminal(CssLexicalGrammar.FUNCTION).is(
      f.function(
        IDENTIFIER(),
        b.token(CssLexicalGrammar.OPEN_PARENTHESIS_NO_WS),
        b.optional(b.oneOrMore(ANY())),
        b.token(CssLexicalGrammar.CLOSE_PARENTHESIS)));
  }

  public NamespaceTree NAMESPACE() {
    return b.<NamespaceTree>nonterminal(CssLexicalGrammar.NAMESPACE).is(
      f.namespace(
        b.optional(IDENTIFIER_NO_WS()),
        b.token(CssLexicalGrammar.PIPE)));
  }

  public SelectorsTree SELECTORS() {
    return b.<SelectorsTree>nonterminal(CssLexicalGrammar.SELECTORS).is(
      f.selectors(SELECTOR_LIST()));
  }

  public SyntaxList<SelectorTree> SELECTOR_LIST() {
    return b.<SyntaxList<SelectorTree>>nonterminal().is(
      b.firstOf(
        f.selectorList(SELECTOR(), b.token(CssLexicalGrammar.COMMA), SELECTOR_LIST()),
        f.selectorList(SELECTOR())));
  }

  public SelectorTree SELECTOR() {
    return b.<SelectorTree>nonterminal(CssLexicalGrammar.SELECTOR).is(
      f.selector(SELECTOR_COMBINATION_LIST()));
  }

  public SelectorCombinationList SELECTOR_COMBINATION_LIST() {
    return b.<SelectorCombinationList>nonterminal().is(
      b.firstOf(
        f.selectorCombinationList(
          COMPOUND_SELECTOR(),
          SELECTOR_COMBINATOR(),
          SELECTOR_COMBINATION_LIST()),
        f.selectorCombinationList(COMPOUND_SELECTOR())));
  }

  public SelectorCombinatorTree SELECTOR_COMBINATOR() {
    return b.<SelectorCombinatorTree>nonterminal(CssLexicalGrammar.SELECTOR_COMBINATOR).is(
      f.selectorCombinator(
        b.firstOf(
          b.token(CssLexicalGrammar.DESCENDANT_COMBINATOR),
          b.token(CssLexicalGrammar.CHILD_COMBINATOR),
          b.token(CssLexicalGrammar.COLUMN_COMBINATOR),
          b.token(CssLexicalGrammar.NEXT_SIBLING_COMBINATOR),
          b.token(CssLexicalGrammar.FOLLOWING_SIBLING_COMBINATOR),
          b.token(CssLexicalGrammar.DESCENDANT_COMBINATOR_WS))));
  }

  // Gap with the grammar that should not lead to side effects: do not check that there should be zero or more
  // type selector and if one that it should be the first one. Same for keyframes selectors.
  public CompoundSelectorTree COMPOUND_SELECTOR() {
    return b.<CompoundSelectorTree>nonterminal(CssLexicalGrammar.COMPOUND_SELECTOR).is(
      f.compoundSelector(
        b.oneOrMore(
          b.firstOf(
            KEYFRAMES_SELECTOR(),
            CLASS_SELECTOR(),
            ID_SELECTOR(),
            PSEUDO_SELECTOR_NO_WS(),
            ATTRIBUTE_SELECTOR(),
            TYPE_SELECTOR()))));
  }

  public ClassSelectorTree CLASS_SELECTOR() {
    return b.<ClassSelectorTree>nonterminal(CssLexicalGrammar.CLASS_SELECTOR).is(
      f.classSelector(
        b.token(CssLexicalGrammar.DOT),
        IDENTIFIER_NO_WS()));
  }

  public KeyframesSelectorTree KEYFRAMES_SELECTOR() {
    return b.<KeyframesSelectorTree>nonterminal(CssLexicalGrammar.KEYFRAMES_SELECTOR).is(
      f.keyframesSelector(
        b.firstOf(
          b.token(CssLexicalGrammar.FROM),
          b.token(CssLexicalGrammar.TO),
          PERCENTAGE())));
  }

  public TypeSelectorTree TYPE_SELECTOR() {
    return b.<TypeSelectorTree>nonterminal(CssLexicalGrammar.TYPE_SELECTOR).is(
      f.typeSelector(
        b.optional(NAMESPACE()),
        IDENTIFIER_NO_WS()));
  }

  public IdSelectorTree ID_SELECTOR() {
    return b.<IdSelectorTree>nonterminal(CssLexicalGrammar.ID_SELECTOR).is(
      f.idSelector(b.token(CssLexicalGrammar.HASH_SYMBOL_NO_WS), IDENTIFIER_NO_WS()));
  }

  public AttributeSelectorTree ATTRIBUTE_SELECTOR() {
    return b.<AttributeSelectorTree>nonterminal(CssLexicalGrammar.ATTRIBUTE_SELECTOR).is(
      b.firstOf(
        f.attributeSelector(
          b.token(CssLexicalGrammar.OPEN_BRACKET_NO_WS),
          IDENTIFIER(),
          b.optional(ATTRIBUTE_MATCHER_EXPRESSION()),
          b.token(CssLexicalGrammar.CLOSE_BRACKET)),
        f.attributeSelector(
          b.token(CssLexicalGrammar.OPEN_BRACKET_NO_WS),
          b.token(CssLexicalGrammar.SPACING),
          NAMESPACE(),
          IDENTIFIER_NO_WS(),
          b.optional(ATTRIBUTE_MATCHER_EXPRESSION()),
          b.token(CssLexicalGrammar.CLOSE_BRACKET))));
  }

  public AttributeMatcherExpressionTree ATTRIBUTE_MATCHER_EXPRESSION() {
    return b.<AttributeMatcherExpressionTree>nonterminal(CssLexicalGrammar.ATTRIBUTE_MATCHER_EXPRESSION).is(
      f.attributeMatcherExpression(
        ATTRIBUTE_MATCHER(),
        b.firstOf(STRING(), IDENTIFIER()),
        b.optional(CASE_INSENSITIVE_FLAG())));
  }

  public AttributeMatcherTree ATTRIBUTE_MATCHER() {
    return b.<AttributeMatcherTree>nonterminal(CssLexicalGrammar.ATTRIBUTE_MATCHER).is(
      f.attributeMatcher(
        b.firstOf(
          b.token(CssLexicalGrammar.DASH_ATTRIBUTE_MATCHER),
          b.token(CssLexicalGrammar.INCLUDE_ATTRIBUTE_MATCHER),
          b.token(CssLexicalGrammar.EQUALS_ATTRIBUTE_MATCHER),
          b.token(CssLexicalGrammar.SUBSTRING_ATTRIBUTE_MATCHER),
          b.token(CssLexicalGrammar.PREFIX_ATTRIBUTE_MATCHER),
          b.token(CssLexicalGrammar.SUFFIX_ATTRIBUTE_MATCHER))));
  }

  public PseudoSelectorTree PSEUDO_SELECTOR() {
    return b.<PseudoSelectorTree>nonterminal(CssLexicalGrammar.PSEUDO_SELECTOR).is(
      f.pseudoSelector(
        b.optional(b.token(CssLexicalGrammar.SPACING)),
        b.firstOf(
          PSEUDO_FUNCTION(),
          PSEUDO_IDENTIFIER())));
  }

  public PseudoSelectorTree PSEUDO_SELECTOR_NO_WS() {
    return b.<PseudoSelectorTree>nonterminal(CssLexicalGrammar.PSEUDO_SELECTOR_NO_WS).is(
      f.pseudoSelector(
        b.firstOf(
          PSEUDO_FUNCTION(),
          PSEUDO_IDENTIFIER())));
  }

  public PseudoFunctionTree PSEUDO_FUNCTION() {
    return b.<PseudoFunctionTree>nonterminal(CssLexicalGrammar.PSEUDO_FUNCTION).is(
      f.pseudoFunction(
        b.token(CssLexicalGrammar.PSEUDO_PREFIX),
        IDENTIFIER_NO_WS(),
        b.token(CssLexicalGrammar.OPEN_PARENTHESIS_NO_WS),
        b.optional(b.oneOrMore(ANY())),
        b.token(CssLexicalGrammar.CLOSE_PARENTHESIS)));
  }

  public PseudoIdentifierTree PSEUDO_IDENTIFIER() {
    return b.<PseudoIdentifierTree>nonterminal(CssLexicalGrammar.PSEUDO_IDENTIFIER).is(
      f.pseudoIdentifier(
        b.token(CssLexicalGrammar.PSEUDO_PREFIX),
        IDENTIFIER_NO_WS()));
  }

  public ImportantTree IMPORTANT() {
    return b.<ImportantTree>nonterminal(CssLexicalGrammar.IMPORTANT).is(
      f.important(
        b.token(CssLexicalGrammar.EXCLAMATION_MARK),
        b.token(CssLexicalGrammar.IMPORTANT_KEYWORD)));
  }

  public VariableTree VARIABLE() {
    return b.<VariableTree>nonterminal(CssLexicalGrammar.VARIABLE).is(
      f.variable(
        b.token(CssLexicalGrammar.VARIABLE_PREFIX),
        IDENTIFIER_NO_WS()));
  }

  public UriTree URI() {
    return b.<UriTree>nonterminal(CssLexicalGrammar.URI).is(
      f.uri(
        b.token(CssLexicalGrammar.URL_FUNCTION_NAME),
        b.token(CssLexicalGrammar.OPEN_PARENTHESIS_NO_WS),
        URI_CONTENT(),
        b.token(CssLexicalGrammar.CLOSE_PARENTHESIS)));
  }

  public UriContentTree URI_CONTENT() {
    return b.<UriContentTree>nonterminal(CssLexicalGrammar.URI_CONTENT).is(
      b.firstOf(
        f.uriContent(STRING()),
        f.uriContent(b.token(CssLexicalGrammar.URI_CONTENT_LITERAL))));
  }

  public UnicodeRangeTree UNICODE_RANGE() {
    return b.<UnicodeRangeTree>nonterminal(CssLexicalGrammar.UNICODE_RANGE).is(
      f.unicodeRange(b.token(CssLexicalGrammar.UNICODE_RANGE_LITERAL)));
  }

  public PercentageTree PERCENTAGE() {
    return b.<PercentageTree>nonterminal(CssLexicalGrammar.PERCENTAGE).is(
      f.percentage(NUMBER(), b.token(CssLexicalGrammar.PERCENTAGE_SYMBOL)));
  }

  public DimensionTree DIMENSION() {
    return b.<DimensionTree>nonterminal(CssLexicalGrammar.DIMENSION).is(
      f.dimension(NUMBER(), UNIT()));
  }

  public UnitTree UNIT() {
    return b.<UnitTree>nonterminal(CssLexicalGrammar.UNIT).is(
      f.unit(b.token(CssLexicalGrammar.UNIT_LITERAL)));
  }

  public HashTree HASH() {
    return b.<HashTree>nonterminal(CssLexicalGrammar.HASH).is(
      f.hash(
        b.token(CssLexicalGrammar.HASH_SYMBOL),
        b.token(CssLexicalGrammar.NAME)));
  }

  public AtKeywordTree AT_KEYWORD() {
    return b.<AtKeywordTree>nonterminal(CssLexicalGrammar.AT_KEYWORD).is(
      f.atKeyword(
        b.token(CssLexicalGrammar.AT_SYMBOL),
        IDENTIFIER_NO_WS()));
  }

  public IdentifierTree IDENTIFIER() {
    return b.<IdentifierTree>nonterminal(CssLexicalGrammar.IDENTIFIER).is(
      f.identifier(b.token(CssLexicalGrammar.IDENT_IDENTIFIER)));
  }

  public IdentifierTree IDENTIFIER_NO_WS() {
    return b.<IdentifierTree>nonterminal(CssLexicalGrammar.IDENTIFIER_NO_WS).is(
      f.identifierNoWs(b.token(CssLexicalGrammar.IDENT_IDENTIFIER_NO_WS)));
  }

  public StringTree STRING() {
    return b.<StringTree>nonterminal(CssLexicalGrammar.STRING).is(
      f.string(b.token(CssLexicalGrammar.STRING_LITERAL)));
  }

  public NumberTree NUMBER() {
    return b.<NumberTree>nonterminal(CssLexicalGrammar.NUMBER).is(
      f.number(b.token(CssLexicalGrammar.NUMBER_LITERAL)));
  }

  public DelimiterTree DELIMITER() {
    return b.<DelimiterTree>nonterminal(CssLexicalGrammar.DELIMITER).is(
      f.delimiter(b.token(CssLexicalGrammar.DELIM)));
  }

  public CaseInsensitiveFlagTree CASE_INSENSITIVE_FLAG() {
    return b.<CaseInsensitiveFlagTree>nonterminal(CssLexicalGrammar.CASE_INSENSITIVE_FLAG).is(
      f.caseInsensitiveFlag(b.token(CssLexicalGrammar.CASE_INSENSITIVE_FLAG_LITERAL)));
  }

}
