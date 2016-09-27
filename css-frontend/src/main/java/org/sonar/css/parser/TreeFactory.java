/*
 * SonarQube CSS / Less Plugin
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

import com.sonar.sslr.api.typed.Optional;

import java.util.List;

import org.sonar.css.tree.impl.SyntaxList;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.css.tree.impl.css.*;
import org.sonar.css.tree.impl.less.*;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.tree.less.*;

public class TreeFactory {

  public StyleSheetTree stylesheet(Optional<SyntaxToken> byteOrderMark, Optional<List<Tree>> statements, SyntaxToken eof) {
    return new StyleSheetTreeImpl(byteOrderMark.orNull(), statements.orNull(), eof);
  }

  public AtRuleTree atRule(AtKeywordTree atKeyword, Optional<List<Tree>> prelude, Optional<AtRuleBlockTree> block, Optional<SyntaxToken> semicolon) {
    return new AtRuleTreeImpl(atKeyword, prelude.orNull(), block.orNull(), semicolon.orNull());
  }

  public AtRuleBlockTree atRuleBlock(SyntaxToken openCurlyBrace, Optional<List<Tree>> content, SyntaxToken closeCurlyBrace) {
    return new AtRuleBlockTreeImpl(openCurlyBrace, content.orNull(), closeCurlyBrace);
  }

  public RulesetTree ruleset(Optional<SyntaxToken> spacing, Optional<SelectorsTree> selectors, RulesetBlockTree block) {
    return new RulesetTreeImpl(selectors.orNull(), block);
  }

  public RulesetBlockTree rulesetBlock(SyntaxToken openCurlyBrace, Optional<List<Tree>> content, SyntaxToken closeCurlyBrace) {
    return new RulesetBlockTreeImpl(openCurlyBrace, content.orNull(), closeCurlyBrace);
  }

  public ParenthesisBlockTree parenthesisBlock(SyntaxToken openParenthesis, Optional<List<Tree>> content, SyntaxToken closeParenthesis) {
    return new ParenthesisBlockTreeImpl(openParenthesis, content.orNull(), closeParenthesis);
  }

  public BracketBlockTree bracketBlock(SyntaxToken openBracket, Optional<List<Tree>> content, SyntaxToken closeBracket) {
    return new BracketBlockTreeImpl(openBracket, content.orNull(), closeBracket);
  }

  public UriTree uri(SyntaxToken urlFunction, SyntaxToken openParenthesis, Optional<UriContentTree> uriContent, SyntaxToken closeParenthesis) {
    return new UriTreeImpl(urlFunction, openParenthesis, uriContent.orNull(), closeParenthesis);
  }

  public UriContentTree uriContent(SyntaxToken ident) {
    return new UriContentTreeImpl(ident);
  }

  public UriContentTree uriContent(StringTree string) {
    return new UriContentTreeImpl(string);
  }

  public PropertyDeclarationTree propertyDeclaration(PropertyTree property, SyntaxToken colon, ValueTree value, Optional<SyntaxToken> semicolon) {
    return new PropertyDeclarationTreeImpl(property, colon, value, semicolon.orNull());
  }

  public VariableDeclarationTree variableDeclaration(VariableTree variable, SyntaxToken colon, ValueTree value, Optional<SyntaxToken> semicolon) {
    return new VariableDeclarationTreeImpl(variable, colon, value, semicolon.orNull());
  }

  public EmptyStatementTree emptyStatement(SyntaxToken semicolon) {
    return new EmptyStatementTreeImpl(semicolon);
  }

  public PropertyTree property(IdentifierTree property) {
    return new PropertyTreeImpl(property, null);
  }

  public ValueTree value(List<Tree> valueElements) {
    return new ValueTreeImpl(valueElements);
  }

  public FunctionTree function(IdentifierTree functionName, SyntaxToken openParenthesis, Optional<List<Tree>> parameterElements, SyntaxToken closeParenthesis) {
    return new FunctionTreeImpl(functionName, openParenthesis, parameterElements.orNull(), closeParenthesis);
  }

  public SelectorsTree selectors(SyntaxList<SelectorTree> selectors) {
    return new SelectorsTreeImpl(selectors);
  }

  public SyntaxList<SelectorTree> selectorList(SelectorTree selector) {
    return new SyntaxList<>(selector, null, null);
  }

  public SyntaxList<SelectorTree> selectorList(SelectorTree selector, SyntaxToken commaToken, SyntaxList<SelectorTree> next) {
    return new SyntaxList<>(selector, commaToken, next);
  }

  public SelectorTree selector(SelectorCombinationList selectorCombinationList) {
    return new SelectorTreeImpl(selectorCombinationList);
  }

  public SelectorCombinationList selectorCombinationList(CompoundSelectorTree selector, SelectorCombinatorTree combinator, SelectorCombinationList next) {
    return new SelectorCombinationList(selector, combinator, next);
  }

  public SelectorCombinationList selectorCombinationList(CompoundSelectorTree selector) {
    return new SelectorCombinationList(selector, null, null);
  }

  public SelectorCombinatorTree selectorCombinator(SyntaxToken combinator) {
    return new SelectorCombinatorTreeImpl(combinator);
  }

  public CompoundSelectorTree compoundSelector(List<SimpleSelectorTree> selectors) {
    return new CompoundSelectorTreeImpl(selectors);
  }

  public KeyframesSelectorTree keyframesSelector(Tree selector) {
    return new KeyframesSelectorTreeImpl(selector);
  }

  public TypeSelectorTree typeSelector(Optional<NamespaceTree> namespace, IdentifierTree identifier) {
    return new TypeSelectorTreeImpl(namespace.orNull(), identifier);
  }

  public ClassSelectorTree classSelector(SyntaxToken dot, IdentifierTree className) {
    return new ClassSelectorTreeImpl(dot, className);
  }

  public IdSelectorTree idSelector(SyntaxToken hash, IdentifierTree identifier) {
    return new IdSelectorTreeImpl(hash, identifier);
  }

  public PseudoSelectorTree pseudoSelector(Optional<SyntaxToken> spacing, PseudoComponentTree pseudo) {
    return new PseudoSelectorTreeImpl(pseudo);
  }

  public PseudoSelectorTree pseudoSelector(PseudoComponentTree pseudo) {
    return new PseudoSelectorTreeImpl(pseudo);
  }

  public PseudoFunctionTree pseudoFunction(SyntaxToken prefix, IdentifierTree pseudoFunctionName, SyntaxToken openParenthesis, Optional<List<Tree>> parameterElements,
    SyntaxToken closeParenthesis) {
    return new PseudoFunctionTreeImpl(prefix, pseudoFunctionName, openParenthesis, parameterElements.orNull(), closeParenthesis);
  }

  public PseudoIdentifierTree pseudoIdentifier(SyntaxToken prefix, IdentifierTree identifier) {
    return new PseudoIdentifierTreeImpl(prefix, identifier);
  }

  public AttributeSelectorTree attributeSelector(SyntaxToken openBracket, IdentifierTree attribute,
    Optional<AttributeMatcherExpressionTree> matcherExpression, SyntaxToken closeBracket) {
    return new AttributeSelectorTreeImpl(openBracket, null, attribute, matcherExpression.orNull(), closeBracket);
  }

  public AttributeSelectorTree attributeSelector(SyntaxToken openBracket, TreeImpl spacing, NamespaceTree namespace, IdentifierTree attribute,
    Optional<AttributeMatcherExpressionTree> matcherExpression, SyntaxToken closeBracket) {
    return new AttributeSelectorTreeImpl(openBracket, namespace, attribute, matcherExpression.orNull(), closeBracket);
  }

  public AttributeMatcherTree attributeMatcher(SyntaxToken matcher) {
    return new AttributeMatcherTreeImpl(matcher);
  }

  public AttributeMatcherExpressionTree attributeMatcherExpression(AttributeMatcherTree attributeMatcher, Tree toMatch, Optional<CaseInsensitiveFlagTree> caseInsensitiveFlag) {
    return new AttributeMatcherExpressionTreeImpl(attributeMatcher, toMatch, caseInsensitiveFlag.orNull());
  }

  public NamespaceTree namespace(Optional<IdentifierTree> namespace, SyntaxToken pipe) {
    return new NamespaceTreeImpl(namespace.orNull(), pipe);
  }

  public ImportantTree important(SyntaxToken exclamationMark, SyntaxToken importantKeyword) {
    return new ImportantTreeImpl(exclamationMark, importantKeyword);
  }

  public IdentifierTree identifier(SyntaxToken identifier) {
    return new IdentifierTreeImpl(identifier);
  }

  public IdentifierTree identifierNoWs(SyntaxToken identifier) {
    return identifier(identifier);
  }

  public AtKeywordTree atKeyword(SyntaxToken atSymbol, IdentifierTree keyword) {
    return new AtKeywordTreeImpl(atSymbol, keyword);
  }

  public HashTree hash(SyntaxToken hashSymbol, SyntaxToken value) {
    return new HashTreeImpl(hashSymbol, value);
  }

  public PercentageTree percentage(NumberTree value, SyntaxToken percentageSymbol) {
    return new PercentageTreeImpl(value, percentageSymbol);
  }

  public DimensionTree dimension(NumberTree value, UnitTree unit) {
    return new DimensionTreeImpl(value, unit);
  }

  public StringTree string(SyntaxToken string) {
    return new StringTreeImpl(string);
  }

  public StringTree stringNoWs(SyntaxToken string) {
    return new StringTreeImpl(string);
  }

  public NumberTree number(SyntaxToken number) {
    return new NumberTreeImpl(number);
  }

  public DelimiterTree delimiter(SyntaxToken delimiter) {
    return new DelimiterTreeImpl(delimiter);
  }

  public CaseInsensitiveFlagTree caseInsensitiveFlag(SyntaxToken flag) {
    return new CaseInsensitiveFlagTreeImpl(flag);
  }

  public UnicodeRangeTree unicodeRange(SyntaxToken unicodeRange) {
    return new UnicodeRangeTreeImpl(unicodeRange);
  }

  public VariableTree variable(SyntaxToken variablePrefix, IdentifierTree variable) {
    return new VariableTreeImpl(variablePrefix, variable);
  }

  public UnitTree unit(SyntaxToken unit) {
    return new UnitTreeImpl(unit);
  }

  // ---------------------------------
  // Less
  // ---------------------------------

  public PropertyTree property(IdentifierTree property, Optional<SyntaxToken> merge) {
    return new PropertyTreeImpl(property, merge.orNull());
  }

  public SelectorsTree lessSelectors(SyntaxList<SelectorTree> selectors, Optional<SyntaxToken> comma) {
    return new SelectorsTreeImpl(selectors, comma.orNull());
  }

  public SelectorTree lessSelector(Optional<SelectorCombinatorTree> parentCombinator, SelectorCombinationList selectorCombinationList, Optional<LessExtendTree> extend,
    Optional<LessMixinParametersTree> parameters, Optional<LessMixinGuardTree> guard) {
    return new SelectorTreeImpl(parentCombinator.orNull(), selectorCombinationList, extend.orNull(), parameters.orNull(), guard.orNull());
  }

  public LessVariableDeclarationTree lessVariableDeclaration(LessVariableTree variable, SyntaxToken colon, ValueTree value, Optional<SyntaxToken> semicolon) {
    return new LessVariableDeclarationTreeImpl(variable, colon, value, semicolon.orNull());
  }

  public LessVariableTree lessVariable(SyntaxToken variablePrefix, IdentifierTree variable) {
    return new LessVariableTreeImpl(variablePrefix, variable);
  }

  public IdentifierTree lessInterpolatedIdentifier(SyntaxToken identifier) {
    return identifier(identifier);
  }

  public IdentifierTree lessInterpolatedIdentifierNoWs(SyntaxToken identifier) {
    return identifier(identifier);
  }

  public IdentifierTree lessIdentifierNoWsNorWhen(SyntaxToken identifier) {
    return identifier(identifier);
  }

  public LessExtendTree lessExtend(SyntaxToken extendKeyword, SyntaxToken openParenthesis, List<Tree> parameterElements, SyntaxToken closeParenthesis) {
    return new LessExtendTreeImpl(extendKeyword, openParenthesis, parameterElements, closeParenthesis);
  }

  public LessParentSelectorTree lessParentSelector(SyntaxToken parentSelector) {
    return new LessParentSelectorTreeImpl(parentSelector);
  }

  public SelectorCombinatorTree lessParentSelectorCombinator(SyntaxToken combinator) {
    return new SelectorCombinatorTreeImpl(combinator);
  }

  public LessMixinCallTree lessMixinCall(Optional<SelectorCombinatorTree> parentCombinator, Optional<SyntaxToken> spacing, SelectorTree selector, Optional<ImportantTree> important,
    Optional<SyntaxToken> semicolon) {
    return new LessMixinCallTreeImpl(parentCombinator.orNull(), selector, important.orNull(), semicolon.orNull());
  }

  public LessMixinGuardTree lessMixinGuard(SyntaxToken when, Optional<SyntaxToken> not, SyntaxList<ParenthesisBlockTree> conditions) {
    return new LessMixinGuardTreeImpl(when, not.orNull(), conditions);
  }

  public SyntaxList<ParenthesisBlockTree> lessMixinGuardConditionList(ParenthesisBlockTree condition, SyntaxToken comma, SyntaxList<ParenthesisBlockTree> next) {
    return new SyntaxList<>(condition, comma, next);
  }

  public SyntaxList<ParenthesisBlockTree> lessMixinGuardConditionList(ParenthesisBlockTree condition) {
    return new SyntaxList<>(condition, null, null);
  }

  public LessMixinParametersTree lessMixinParameters(SyntaxToken openParenthesis, Optional<SyntaxList<LessMixinParameterTree>> parameters, SyntaxToken closeParenthesis) {
    return new LessMixinParametersTreeImpl(openParenthesis, parameters.orNull(), closeParenthesis);
  }

  public SyntaxList<LessMixinParameterTree> lessMixinParameterList(LessMixinParameterTree parameter) {
    return new SyntaxList<>(parameter, null, null);
  }

  public SyntaxList<LessMixinParameterTree> lessMixinParameterList(LessMixinParameterTree parameter, SyntaxToken separator) {
    return new SyntaxList<>(parameter, separator, null);
  }

  public SyntaxList<LessMixinParameterTree> lessMixinParameterList(LessMixinParameterTree parameter, SyntaxToken separator, SyntaxList<LessMixinParameterTree> next) {
    return new SyntaxList<>(parameter, separator, next);
  }

  public LessMixinParameterTree lessMixinParameter(LessVariableTree variable, Optional<SyntaxToken> colon, Optional<ValueTree> value) {
    return new LessMixinParameterTreeImpl(variable, colon.orNull(), value.orNull());
  }

  public LessMixinParameterTree lessMixinParameter(ValueTree value) {
    return new LessMixinParameterTreeImpl(null, null, value);
  }

  public LessEscapingTree lessEscaping(SyntaxToken escapingSymbol, StringTree string) {
    return new LessEscapingTreeImpl(escapingSymbol, string);
  }

}
