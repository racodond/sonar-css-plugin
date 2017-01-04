/*
 * SonarQube CSS / SCSS / Less Analyzer
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
package org.sonar.css.parser.css;

import com.sonar.sslr.api.typed.GrammarBuilder;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.css.parser.TreeFactory;
import org.sonar.css.tree.impl.SeparatedList;
import org.sonar.css.tree.impl.css.InternalSyntaxToken;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;

/**
 * CSS grammar based on:
 * - https://www.w3.org/TR/CSS22/grammar.html
 * - https://drafts.csswg.org/css-syntax-3/
 */
public class CssGrammar {

  protected GrammarBuilder<InternalSyntaxToken> b;
  protected TreeFactory f;

  public CssGrammar(GrammarBuilder<InternalSyntaxToken> b, TreeFactory f) {
    this.b = b;
    this.f = f;
  }

  public StyleSheetTree STYLESHEET() {
    return b.<StyleSheetTree>nonterminal(LexicalGrammar.STYLESHEET).is(
      f.stylesheet(
        b.optional(b.token(LexicalGrammar.BOM)),
        b.zeroOrMore(
          b.firstOf(
            AT_RULE(),
            RULESET())),
        b.token(LexicalGrammar.EOF)));
  }

  public AtRuleTree AT_RULE() {
    return b.<AtRuleTree>nonterminal(LexicalGrammar.AT_RULE).is(
      f.atRule(
        AT_KEYWORD(),
        b.zeroOrMore(ANY()),
        b.optional(AT_RULE_BLOCK()),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public RulesetTree RULESET() {
    return b.<RulesetTree>nonterminal(LexicalGrammar.RULESET).is(
      f.ruleset(
        b.optional(b.token(LexicalGrammar.SPACING)),
        b.optional(SELECTORS()),
        RULESET_BLOCK()));
  }

  public StatementBlockTree AT_RULE_BLOCK() {
    return b.<StatementBlockTree>nonterminal(LexicalGrammar.AT_RULE_BLOCK).is(
      f.atRuleBlock(
        b.token(LexicalGrammar.OPEN_CURLY_BRACE),
        b.zeroOrMore(
          b.firstOf(
            DECLARATION(),
            AT_RULE(),
            RULESET(),
            EMPTY_STATEMENT())),
        b.token(LexicalGrammar.CLOSE_CURLY_BRACE)));
  }

  public StatementBlockTree RULESET_BLOCK() {
    return b.<StatementBlockTree>nonterminal(LexicalGrammar.RULESET_BLOCK).is(
      f.rulesetBlock(
        b.token(LexicalGrammar.OPEN_CURLY_BRACE),
        b.zeroOrMore(
          b.firstOf(
            DECLARATION(),
            EMPTY_STATEMENT())),
        b.token(LexicalGrammar.CLOSE_CURLY_BRACE)));
  }

  public ValueTree VALUE() {
    return b.<ValueTree>nonterminal(LexicalGrammar.VALUE).is(
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
        IMPORTANT_FLAG(),
        b.token(LexicalGrammar.COLON),
        DELIMITER()));
  }

  public ParenthesisBlockTree PARENTHESIS_BLOCK() {
    return b.<ParenthesisBlockTree>nonterminal(LexicalGrammar.PARENTHESIS_BLOCK_TREE).is(
      f.parenthesisBlock(
        b.token(LexicalGrammar.OPEN_PARENTHESIS),
        b.zeroOrMore(ANY()),
        b.token(LexicalGrammar.CLOSE_PARENTHESIS)));
  }

  public BracketBlockTree BRACKET_BLOCK() {
    return b.<BracketBlockTree>nonterminal(LexicalGrammar.BRACKET_BLOCK_TREE).is(
      f.bracketBlock(
        b.token(LexicalGrammar.OPEN_BRACKET),
        b.zeroOrMore(ANY()),
        b.token(LexicalGrammar.CLOSE_BRACKET)));
  }

  public DeclarationTree DECLARATION() {
    return b.<DeclarationTree>nonterminal(LexicalGrammar.DECLARATION).is(
      b.firstOf(CSS_DECLARATION()));
  }

  public DeclarationTree CSS_DECLARATION() {
    return b.<DeclarationTree>nonterminal().is(
      b.firstOf(
        VARIABLE_DECLARATION(),
        PROPERTY_DECLARATION()));
  }

  public PropertyDeclarationTree PROPERTY_DECLARATION() {
    return b.<PropertyDeclarationTree>nonterminal(LexicalGrammar.PROPERTY_DECLARATION).is(
      f.propertyDeclaration(
        PROPERTY(),
        b.token(LexicalGrammar.COLON),
        VALUE(),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public VariableDeclarationTree VARIABLE_DECLARATION() {
    return b.<VariableDeclarationTree>nonterminal(LexicalGrammar.VARIABLE_DECLARATION).is(
      f.variableDeclaration(
        VARIABLE(),
        b.token(LexicalGrammar.COLON),
        VALUE(),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public EmptyStatementTree EMPTY_STATEMENT() {
    return b.<EmptyStatementTree>nonterminal(LexicalGrammar.EMPTY_STATEMENT).is(
      f.emptyStatement(b.token(LexicalGrammar.SEMICOLON)));
  }

  public PropertyTree PROPERTY() {
    return b.<PropertyTree>nonterminal(LexicalGrammar.PROPERTY).is(
      f.property(IDENTIFIER()));
  }

  public FunctionTree FUNCTION() {
    return b.<FunctionTree>nonterminal(LexicalGrammar.FUNCTION).is(
      f.function(
        IDENTIFIER(),
        b.token(LexicalGrammar.OPEN_PARENTHESIS_NO_WS),
        b.optional(b.oneOrMore(ANY())),
        b.token(LexicalGrammar.CLOSE_PARENTHESIS)));
  }

  public NamespaceTree NAMESPACE() {
    return b.<NamespaceTree>nonterminal(LexicalGrammar.NAMESPACE).is(
      f.namespace(
        b.optional(IDENTIFIER_NO_WS()),
        b.token(LexicalGrammar.PIPE)));
  }

  public SelectorsTree SELECTORS() {
    return b.<SelectorsTree>nonterminal(LexicalGrammar.SELECTORS).is(
      f.selectors(SELECTOR_LIST()));
  }

  public SeparatedList<SelectorTree, SyntaxToken> SELECTOR_LIST() {
    return b.<SeparatedList<SelectorTree, SyntaxToken>>nonterminal().is(
      f.selectorList(
        SELECTOR(),
        b.zeroOrMore(
          f.newTuple1(
            b.token(LexicalGrammar.COMMA),
            SELECTOR()))));
  }

  public SelectorTree SELECTOR() {
    return b.<SelectorTree>nonterminal(LexicalGrammar.SELECTOR).is(
      f.selector(COMPOUND_SELECTOR_COMBINATION_LIST()));
  }

  public SeparatedList<CompoundSelectorTree, SelectorCombinatorTree> COMPOUND_SELECTOR_COMBINATION_LIST() {
    return b.<SeparatedList<CompoundSelectorTree, SelectorCombinatorTree>>nonterminal().is(
      f.selectorCombinationList(
        COMPOUND_SELECTOR(),
        b.zeroOrMore(
          f.newTuple4(
            SELECTOR_COMBINATOR(),
            COMPOUND_SELECTOR()))));
  }

  public SelectorCombinatorTree SELECTOR_COMBINATOR() {
    return b.<SelectorCombinatorTree>nonterminal(LexicalGrammar.SELECTOR_COMBINATOR).is(
      f.selectorCombinator(
        b.firstOf(
          b.token(LexicalGrammar.DESCENDANT_COMBINATOR),
          b.token(LexicalGrammar.CHILD_COMBINATOR),
          b.token(LexicalGrammar.COLUMN_COMBINATOR),
          b.token(LexicalGrammar.NEXT_SIBLING_COMBINATOR),
          b.token(LexicalGrammar.FOLLOWING_SIBLING_COMBINATOR),
          b.token(LexicalGrammar.DESCENDANT_COMBINATOR_WS))));
  }

  public CompoundSelectorTree COMPOUND_SELECTOR() {
    return b.<CompoundSelectorTree>nonterminal(LexicalGrammar.COMPOUND_SELECTOR).is(
      f.compoundSelector(
        b.oneOrMore(
          b.firstOf(
            SIMPLE_CSS_SELECTOR()))));
  }

  public SimpleSelectorTree SIMPLE_CSS_SELECTOR() {
    return b.<SimpleSelectorTree>nonterminal().is(
      b.firstOf(
        KEYFRAMES_SELECTOR(),
        CLASS_SELECTOR(),
        ID_SELECTOR(),
        PSEUDO_SELECTOR_NO_WS(),
        ATTRIBUTE_SELECTOR(),
        TYPE_SELECTOR()));
  }

  public ClassSelectorTree CLASS_SELECTOR() {
    return b.<ClassSelectorTree>nonterminal(LexicalGrammar.CLASS_SELECTOR).is(
      f.classSelector(
        b.token(LexicalGrammar.DOT),
        IDENTIFIER_NO_WS()));
  }

  public KeyframesSelectorTree KEYFRAMES_SELECTOR() {
    return b.<KeyframesSelectorTree>nonterminal(LexicalGrammar.KEYFRAMES_SELECTOR).is(
      f.keyframesSelector(
        b.firstOf(
          b.token(LexicalGrammar.FROM),
          b.token(LexicalGrammar.TO),
          PERCENTAGE())));
  }

  public TypeSelectorTree TYPE_SELECTOR() {
    return b.<TypeSelectorTree>nonterminal(LexicalGrammar.TYPE_SELECTOR).is(
      f.typeSelector(
        b.optional(NAMESPACE()),
        IDENTIFIER_NO_WS()));
  }

  public IdSelectorTree ID_SELECTOR() {
    return b.<IdSelectorTree>nonterminal(LexicalGrammar.ID_SELECTOR).is(
      f.idSelector(b.token(LexicalGrammar.HASH_SYMBOL_NO_WS), IDENTIFIER_NO_WS()));
  }

  public AttributeSelectorTree ATTRIBUTE_SELECTOR() {
    return b.<AttributeSelectorTree>nonterminal(LexicalGrammar.ATTRIBUTE_SELECTOR).is(
      b.firstOf(
        f.attributeSelector(
          b.token(LexicalGrammar.OPEN_BRACKET_NO_WS),
          IDENTIFIER(),
          b.optional(ATTRIBUTE_MATCHER_EXPRESSION()),
          b.token(LexicalGrammar.CLOSE_BRACKET)),
        f.attributeSelector(
          b.token(LexicalGrammar.OPEN_BRACKET_NO_WS),
          b.token(LexicalGrammar.SPACING),
          NAMESPACE(),
          IDENTIFIER_NO_WS(),
          b.optional(ATTRIBUTE_MATCHER_EXPRESSION()),
          b.token(LexicalGrammar.CLOSE_BRACKET))));
  }

  public AttributeMatcherExpressionTree ATTRIBUTE_MATCHER_EXPRESSION() {
    return b.<AttributeMatcherExpressionTree>nonterminal(LexicalGrammar.ATTRIBUTE_MATCHER_EXPRESSION).is(
      f.attributeMatcherExpression(
        ATTRIBUTE_MATCHER(),
        b.firstOf(STRING(), IDENTIFIER()),
        b.optional(CASE_INSENSITIVE_FLAG())));
  }

  public AttributeMatcherTree ATTRIBUTE_MATCHER() {
    return b.<AttributeMatcherTree>nonterminal(LexicalGrammar.ATTRIBUTE_MATCHER).is(
      f.attributeMatcher(
        b.firstOf(
          b.token(LexicalGrammar.DASH_ATTRIBUTE_MATCHER),
          b.token(LexicalGrammar.INCLUDE_ATTRIBUTE_MATCHER),
          b.token(LexicalGrammar.EQUALS_ATTRIBUTE_MATCHER),
          b.token(LexicalGrammar.SUBSTRING_ATTRIBUTE_MATCHER),
          b.token(LexicalGrammar.PREFIX_ATTRIBUTE_MATCHER),
          b.token(LexicalGrammar.SUFFIX_ATTRIBUTE_MATCHER))));
  }

  public PseudoSelectorTree PSEUDO_SELECTOR() {
    return b.<PseudoSelectorTree>nonterminal(LexicalGrammar.PSEUDO_SELECTOR).is(
      f.pseudoSelector(
        b.optional(b.token(LexicalGrammar.SPACING)),
        b.firstOf(
          PSEUDO_FUNCTION(),
          PSEUDO_IDENTIFIER())));
  }

  public PseudoSelectorTree PSEUDO_SELECTOR_NO_WS() {
    return b.<PseudoSelectorTree>nonterminal(LexicalGrammar.PSEUDO_SELECTOR_NO_WS).is(
      f.pseudoSelector(
        b.firstOf(
          PSEUDO_FUNCTION(),
          PSEUDO_IDENTIFIER())));
  }

  public PseudoFunctionTree PSEUDO_FUNCTION() {
    return b.<PseudoFunctionTree>nonterminal(LexicalGrammar.PSEUDO_FUNCTION).is(
      f.pseudoFunction(
        b.token(LexicalGrammar.PSEUDO_PREFIX),
        IDENTIFIER_NO_WS(),
        b.token(LexicalGrammar.OPEN_PARENTHESIS_NO_WS),
        b.optional(b.oneOrMore(ANY())),
        b.token(LexicalGrammar.CLOSE_PARENTHESIS)));
  }

  public PseudoIdentifierTree PSEUDO_IDENTIFIER() {
    return b.<PseudoIdentifierTree>nonterminal(LexicalGrammar.PSEUDO_IDENTIFIER).is(
      f.pseudoIdentifier(
        b.token(LexicalGrammar.PSEUDO_PREFIX),
        IDENTIFIER_NO_WS()));
  }

  public ImportantFlagTree IMPORTANT_FLAG() {
    return b.<ImportantFlagTree>nonterminal(LexicalGrammar.IMPORTANT_FLAG).is(
      f.important(
        b.token(LexicalGrammar.EXCLAMATION_MARK),
        b.token(LexicalGrammar.IMPORTANT_KEYWORD)));
  }

  public VariableTree VARIABLE() {
    return b.<VariableTree>nonterminal(LexicalGrammar.VARIABLE).is(
      f.variable(
        b.token(LexicalGrammar.VARIABLE_PREFIX),
        IDENTIFIER_NO_WS()));
  }

  public UriTree URI() {
    return b.<UriTree>nonterminal(LexicalGrammar.URI).is(
      f.uri(
        b.token(LexicalGrammar.URL_FUNCTION_NAME),
        b.token(LexicalGrammar.OPEN_PARENTHESIS_NO_WS),
        b.optional(URI_CONTENT()),
        b.token(LexicalGrammar.CLOSE_PARENTHESIS)));
  }

  public UriContentTree URI_CONTENT() {
    return b.<UriContentTree>nonterminal(LexicalGrammar.URI_CONTENT).is(
      b.firstOf(
        f.uriContent(STRING()),
        f.uriContent(b.token(LexicalGrammar.URI_CONTENT_LITERAL))));
  }

  public UnicodeRangeTree UNICODE_RANGE() {
    return b.<UnicodeRangeTree>nonterminal(LexicalGrammar.UNICODE_RANGE).is(
      f.unicodeRange(b.token(LexicalGrammar.UNICODE_RANGE_LITERAL)));
  }

  public PercentageTree PERCENTAGE() {
    return b.<PercentageTree>nonterminal(LexicalGrammar.PERCENTAGE).is(
      f.percentage(NUMBER(), b.token(LexicalGrammar.PERCENTAGE_SYMBOL)));
  }

  public DimensionTree DIMENSION() {
    return b.<DimensionTree>nonterminal(LexicalGrammar.DIMENSION).is(
      f.dimension(NUMBER(), UNIT()));
  }

  public UnitTree UNIT() {
    return b.<UnitTree>nonterminal(LexicalGrammar.UNIT).is(
      f.unit(b.token(LexicalGrammar.UNIT_LITERAL)));
  }

  public HashTree HASH() {
    return b.<HashTree>nonterminal(LexicalGrammar.HASH).is(
      f.hash(
        b.token(LexicalGrammar.HASH_SYMBOL),
        b.token(LexicalGrammar.NAME)));
  }

  public AtKeywordTree AT_KEYWORD() {
    return b.<AtKeywordTree>nonterminal(LexicalGrammar.AT_KEYWORD).is(
      f.atKeyword(
        b.token(LexicalGrammar.AT_SYMBOL),
        IDENTIFIER_NO_WS()));
  }

  public IdentifierTree IDENTIFIER() {
    return b.<IdentifierTree>nonterminal(LexicalGrammar.IDENTIFIER).is(
      f.identifier(b.token(LexicalGrammar.IDENT_IDENTIFIER)));
  }

  public IdentifierTree IDENTIFIER_NO_WS() {
    return b.<IdentifierTree>nonterminal(LexicalGrammar.IDENTIFIER_NO_WS).is(
      f.identifierNoWs(b.token(LexicalGrammar.IDENT_IDENTIFIER_NO_WS)));
  }

  public StringTree STRING() {
    return b.<StringTree>nonterminal(LexicalGrammar.STRING).is(
      f.string(b.token(LexicalGrammar.STRING_LITERAL)));
  }

  public StringTree STRING_NO_WS() {
    return b.<StringTree>nonterminal(LexicalGrammar.STRING_NO_WS).is(
      f.stringNoWs(b.token(LexicalGrammar.STRING_LITERAL_NO_WS)));
  }

  public NumberTree NUMBER() {
    return b.<NumberTree>nonterminal(LexicalGrammar.NUMBER).is(
      f.number(b.token(LexicalGrammar.NUMBER_LITERAL)));
  }

  public DelimiterTree DELIMITER() {
    return b.<DelimiterTree>nonterminal(LexicalGrammar.DELIMITER).is(
      f.delimiter(b.token(LexicalGrammar.DELIM)));
  }

  public CaseInsensitiveFlagTree CASE_INSENSITIVE_FLAG() {
    return b.<CaseInsensitiveFlagTree>nonterminal(LexicalGrammar.CASE_INSENSITIVE_FLAG).is(
      f.caseInsensitiveFlag(b.token(LexicalGrammar.CASE_INSENSITIVE_FLAG_LITERAL)));
  }

}
