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
package org.sonar.css.parser;

import com.google.common.collect.Lists;
import com.sonar.sslr.api.typed.Optional;
import org.sonar.css.tree.impl.SeparatedList;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.css.tree.impl.css.*;
import org.sonar.css.tree.impl.embedded.CssInStyleTagTreeImpl;
import org.sonar.css.tree.impl.embedded.FileWithEmbeddedCssTreeImpl;
import org.sonar.css.tree.impl.less.*;
import org.sonar.css.tree.impl.scss.*;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.tree.embedded.CssInStyleTagTree;
import org.sonar.plugins.css.api.tree.embedded.FileWithEmbeddedCssTree;
import org.sonar.plugins.css.api.tree.less.*;
import org.sonar.plugins.css.api.tree.scss.*;

import java.util.List;

public class TreeFactory {

  public StyleSheetTree stylesheet(Optional<SyntaxToken> byteOrderMark, Optional<List<Tree>> statements, SyntaxToken eof) {
    return new StyleSheetTreeImpl(byteOrderMark.orNull(), statements.orNull(), eof);
  }

  public AtRuleTree atRule(AtKeywordTree atKeyword, Optional<List<Tree>> prelude, Optional<StatementBlockTree> block, Optional<SyntaxToken> semicolon) {
    return new AtRuleTreeImpl(atKeyword, prelude.orNull(), block.orNull(), semicolon.orNull());
  }

  public RulesetTree ruleset(Optional<SyntaxToken> spacing, Optional<SelectorsTree> selectors, StatementBlockTree block) {
    return new RulesetTreeImpl(selectors.orNull(), block);
  }

  public StatementBlockTree atRuleBlock(SyntaxToken openCurlyBrace, Optional<List<Tree>> content, SyntaxToken closeCurlyBrace) {
    return new StatementBlockTreeImpl(openCurlyBrace, content.orNull(), closeCurlyBrace);
  }

  public StatementBlockTree rulesetBlock(SyntaxToken openCurlyBrace, Optional<List<Tree>> content, SyntaxToken closeCurlyBrace) {
    return new StatementBlockTreeImpl(openCurlyBrace, content.orNull(), closeCurlyBrace);
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

  public SelectorsTree selectors(SeparatedList<SelectorTree, SyntaxToken> selectors) {
    return new SelectorsTreeImpl(selectors);
  }

  public SeparatedList<SelectorTree, SyntaxToken> selectorList(SelectorTree selector, Optional<List<Tuple<SyntaxToken, SelectorTree>>> subsequentSelectors) {
    List<SelectorTree> selectors = Lists.newArrayList();
    List<SyntaxToken> separators = Lists.newArrayList();

    selectors.add(selector);

    if (subsequentSelectors.isPresent()) {
      for (Tuple<SyntaxToken, SelectorTree> t : subsequentSelectors.get()) {
        separators.add(t.first());
        selectors.add(t.second());
      }
    }

    return new SeparatedList<>(selectors, separators);
  }

  public SelectorTree selector(SeparatedList<CompoundSelectorTree, SelectorCombinatorTree> compoundSelectors) {
    return new SelectorTreeImpl(compoundSelectors);
  }

  public SeparatedList<CompoundSelectorTree, SelectorCombinatorTree> selectorCombinationList(CompoundSelectorTree selector, Optional<List<Tuple<SelectorCombinatorTree, CompoundSelectorTree>>> subsequentSelectors) {
    List<CompoundSelectorTree> compoundSelectors = Lists.newArrayList();
    List<SelectorCombinatorTree> selectorCombinators = Lists.newArrayList();

    compoundSelectors.add(selector);

    if (subsequentSelectors.isPresent()) {
      for (Tuple<SelectorCombinatorTree, CompoundSelectorTree> t : subsequentSelectors.get()) {
        selectorCombinators.add(t.first());
        compoundSelectors.add(t.second());
      }
    }

    return new SeparatedList<>(compoundSelectors, selectorCombinators);
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
  // Embedded CSS
  // ---------------------------------

  public StyleSheetTree stylesheet(Optional<List<Tree>> statements) {
    return new StyleSheetTreeImpl(null, statements.orNull(), null);
  }

  public CssInStyleTagTree cssInStyleTag(SyntaxToken openingTag, StyleSheetTree styleSheet, SyntaxToken closingTag) {
    return new CssInStyleTagTreeImpl(openingTag, styleSheet, closingTag);
  }

  public FileWithEmbeddedCssTree fileWithEmbeddedCss(Optional<List<Tree>> trees, SyntaxToken eof) {
    return new FileWithEmbeddedCssTreeImpl(trees.orNull(), eof);
  }

  // ---------------------------------
  // Common to SCSS and Less
  // ---------------------------------
  public StatementBlockTree rulesetBlock(StatementBlockTree block) {
    return new StatementBlockTreeImpl(block);
  }

  public StatementBlockTree atRuleBlock(StatementBlockTree block) {
    return new StatementBlockTreeImpl(block);
  }

  public StatementBlockTree statementBlock(SyntaxToken openCurlyBrace, Optional<List<Tree>> content, SyntaxToken closeCurlyBrace) {
    return new StatementBlockTreeImpl(openCurlyBrace, content.orNull(), closeCurlyBrace);
  }

  // ---------------------------------
  // SCSS
  // ---------------------------------
  public SelectorTree scssSelector(Optional<SelectorCombinatorTree> parentCombinator, SeparatedList<CompoundSelectorTree, SelectorCombinatorTree> selectors, Optional<SelectorCombinatorTree> blockCombinator) {
    return new SelectorTreeImpl(parentCombinator.orNull(), selectors, blockCombinator.orNull());
  }

  public ScssParentSelectorTree scssParentSelector(SyntaxToken parentSelector) {
    return new ScssParentSelectorTreeImpl(parentSelector);
  }

  public SelectorCombinatorTree scssParentSelectorCombinator(SyntaxToken combinator) {
    return new SelectorCombinatorTreeImpl(combinator);
  }

  public ScssNestedPropertiesDeclarationTree scssNestedPropertiesDeclaration(PropertyTree namespace, SyntaxToken colon, StatementBlockTree block) {
    return new ScssNestedPropertiesDeclarationTreeImpl(namespace, colon, block);
  }

  public ScssVariableDeclarationTree scssVariableDeclaration(ScssVariableTree variable, SyntaxToken colon, ValueTree value, Optional<SyntaxToken> semicolon) {
    return new ScssVariableDeclarationTreeImpl(variable, colon, value, semicolon.orNull());
  }

  public ScssVariableDeclarationTree scssVariableDeclarationWithoutDelimiterInValue(ScssVariableTree variable, SyntaxToken colon, ValueTree value, Optional<SyntaxToken> semicolon) {
    return new ScssVariableDeclarationTreeImpl(variable, colon, value, semicolon.orNull());
  }

  public ScssVariableTree scssVariable(SyntaxToken variablePrefix, IdentifierTree variable) {
    return new ScssVariableTreeImpl(variablePrefix, variable);
  }

  public ScssVariableArgumentTree scssVariableArgument(SyntaxToken variablePrefix, IdentifierTree variable, SyntaxToken ellipsis) {
    return new ScssVariableArgumentTreeImpl(variablePrefix, variable, ellipsis);
  }

  public ScssDefaultFlagTree scssDefaultFlag(SyntaxToken keyword) {
    return new ScssDefaultFlagTreeImpl(keyword);
  }

  public ScssGlobalFlagTree scssGlobalFlag(SyntaxToken keyword) {
    return new ScssGlobalFlagTreeImpl(keyword);
  }

  public ScssOptionalFlagTree scssOptionalFlag(SyntaxToken keyword) {
    return new ScssOptionalFlagTreeImpl(keyword);
  }

  public ScssFunctionDefinitionTree scssFunctionDefinition(SyntaxToken directive, IdentifierTree name, Optional<ScssParametersTree> parameters, StatementBlockTree block) {
    return new ScssFunctionDefinitionTreeImpl(directive, name, parameters.orNull(), block);
  }

  public ScssMixinDefinitionTree scssMixinDefinition(SyntaxToken directive, IdentifierTree name, Optional<ScssParametersTree> parameters, StatementBlockTree block) {
    return new ScssMixinDefinitionTreeImpl(directive, name, parameters.orNull(), block);
  }

  public ScssMixinIncludeTree scssMixinInclude(SyntaxToken directive, IdentifierTree name, Optional<ScssParametersTree> parameters, Optional<StatementBlockTree> block, Optional<SyntaxToken> semicolon) {
    return new ScssMixinIncludeTreeImpl(directive, name, parameters.orNull(), block.orNull(), semicolon.orNull());
  }

  public ScssParametersTree scssDefinitionParameters(SyntaxToken openParenthesis, Optional<SeparatedList<ScssParameterTree, SyntaxToken>> parameters, SyntaxToken closeParenthesis) {
    return new ScssParametersTreeImpl(openParenthesis, parameters.orNull(), closeParenthesis);
  }

  public ScssParametersTree scssCallParameters(SyntaxToken openParenthesis, Optional<SeparatedList<ScssParameterTree, SyntaxToken>> parameters, SyntaxToken closeParenthesis) {
    return new ScssParametersTreeImpl(openParenthesis, parameters.orNull(), closeParenthesis);
  }

  public SeparatedList<ScssParameterTree, SyntaxToken> scssDefinitionParameterList(ScssParameterTree parameter, Optional<List<Tuple<SyntaxToken, ScssParameterTree>>> subsequentParameters) {
    return scssParameterList(parameter, subsequentParameters);
  }

  public SeparatedList<ScssParameterTree, SyntaxToken> scssCallParameterList(ScssParameterTree parameter, Optional<List<Tuple<SyntaxToken, ScssParameterTree>>> subsequentParameters) {
    return scssParameterList(parameter, subsequentParameters);
  }

  private SeparatedList<ScssParameterTree, SyntaxToken> scssParameterList(ScssParameterTree parameter, Optional<List<Tuple<SyntaxToken, ScssParameterTree>>> subsequentParameters) {
    List<ScssParameterTree> parameters = Lists.newArrayList();
    List<SyntaxToken> separators = Lists.newArrayList();

    parameters.add(parameter);

    if (subsequentParameters.isPresent()) {
      for (Tuple<SyntaxToken, ScssParameterTree> t : subsequentParameters.get()) {
        separators.add(t.first());
        parameters.add(t.second());
      }
    }

    return new SeparatedList<>(parameters, separators);
  }

  public ScssParameterTree scssDefinitionParameter(Tree tree) {
    return new ScssParameterTreeImpl(tree);
  }

  public ScssParameterTree scssCallParameter(Tree tree) {
    return new ScssParameterTreeImpl(tree);
  }

  public ValueTree scssValue(List<Tree> valueElements) {
    return new ValueTreeImpl(valueElements);
  }

  public ValueTree scssValueWithoutDelimiter(List<Tree> valueElements) {
    return new ValueTreeImpl(valueElements);
  }

  public ScssExtendTree scssExtend(SyntaxToken directive, CompoundSelectorTree compoundSelector, Optional<ScssOptionalFlagTree> optionalFlag, Optional<SyntaxToken> semicolon) {
    return new ScssExtendTreeImpl(directive, compoundSelector, optionalFlag.orNull(), semicolon.orNull());
  }

  public ScssContentTree scssContent(SyntaxToken directive, Optional<SyntaxToken> semicolon) {
    return new ScssContentTreeImpl(directive, semicolon.orNull());
  }

  public ScssDebugTree scssDebug(SyntaxToken directive, ValueTree value, Optional<SyntaxToken> semicolon) {
    return new ScssDebugTreeImpl(directive, value, semicolon.orNull());
  }

  public ScssWarnTree scssWarn(SyntaxToken directive, ValueTree value, Optional<SyntaxToken> semicolon) {
    return new ScssWarnTreeImpl(directive, value, semicolon.orNull());
  }

  public ScssErrorTree scssError(SyntaxToken directive, ValueTree value, Optional<SyntaxToken> semicolon) {
    return new ScssErrorTreeImpl(directive, value, semicolon.orNull());
  }

  public ScssReturnTree scssReturn(SyntaxToken directive, ValueTree value, Optional<SyntaxToken> semicolon) {
    return new ScssReturnTreeImpl(directive, value, semicolon.orNull());
  }

  public ScssIfConditionsTree scssIfConditions(ScssIfTree ifDirective, Optional<List<ScssElseIfTree>> elseIfDirectives, Optional<ScssElseTree> elseDirective) {
    return new ScssIfConditionsTreeImpl(ifDirective, elseIfDirectives.orNull(), elseDirective.orNull());
  }

  public ScssIfTree scssIf(SyntaxToken directive, ValueTree condition, StatementBlockTree block) {
    return new ScssIfTreeImpl(directive, condition, block);
  }

  public ScssElseIfTree scssElseIf(SyntaxToken directive, ValueTree condition, StatementBlockTree block) {
    return new ScssElseIfTreeImpl(directive, condition, block);
  }

  public ScssWhileTree scssWhile(SyntaxToken directive, ValueTree condition, StatementBlockTree block) {
    return new ScssWhileTreeImpl(directive, condition, block);
  }

  public ScssElseTree scssElse(SyntaxToken directive, StatementBlockTree block) {
    return new ScssElseTreeImpl(directive, block);
  }

  public ScssForTree scssFor(SyntaxToken directive, ValueTree condition, StatementBlockTree block) {
    return new ScssForTreeImpl(directive, condition, block);
  }

  public ScssEachTree scssEach(SyntaxToken directive, ValueTree condition, StatementBlockTree block) {
    return new ScssEachTreeImpl(directive, condition, block);
  }

  public ScssAtRootTree scssAtRoot(SyntaxToken directive, Optional<ScssAtRootParametersTree> parameters, Tree content) {
    return new ScssAtRootTreeImpl(directive, parameters.orNull(), content);
  }

  public ScssAtRootParametersTree scssAtRootParameters(SyntaxToken openParenthesis, SyntaxToken parameter, SyntaxToken colon, List<IdentifierTree> values, SyntaxToken closeParenthesis) {
    return new ScssAtRootParametersTreeImpl(openParenthesis, parameter, colon, values, closeParenthesis);
  }

  public ScssPlaceholderSelectorTree scssPlaceholderSelector(SyntaxToken percentage, IdentifierTree identifier) {
    return new ScssPlaceholderSelectorTreeImpl(percentage, identifier);
  }

  public IdentifierTree scssInterpolatedIdentifier(SyntaxToken identifier) {
    return identifier(identifier);
  }

  public IdentifierTree scssInterpolatedIdentifierNoWs(SyntaxToken identifier) {
    return identifier(identifier);
  }

  public ScssOperatorTree scssOperator(SyntaxToken operator) {
    return new ScssOperatorTreeImpl(operator);
  }

  public ScssMultilineStringTree scssMultilineString(SyntaxToken string) {
    return new ScssMultilineStringTreeImpl(string);
  }

  // ---------------------------------
  // Less
  // ---------------------------------

  public PropertyTree property(IdentifierTree property, Optional<SyntaxToken> merge) {
    return new PropertyTreeImpl(property, merge.orNull());
  }

  public SelectorsTree lessSelectors(SeparatedList<SelectorTree, SyntaxToken> selectors, Optional<SyntaxToken> comma) {
    return new SelectorsTreeImpl(selectors, comma.orNull());
  }

  public SelectorTree lessSelector(Optional<SelectorCombinatorTree> parentCombinator, SeparatedList<CompoundSelectorTree, SelectorCombinatorTree> selectors, Optional<LessExtendTree> extend,
                                   Optional<LessMixinParametersTree> parameters, Optional<LessMixinGuardTree> guard) {
    return new SelectorTreeImpl(parentCombinator.orNull(), selectors, extend.orNull(), parameters.orNull(), guard.orNull());
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

  public LessMixinGuardTree lessMixinGuard(SyntaxToken when, Optional<SyntaxToken> not, SeparatedList<ParenthesisBlockTree, SyntaxToken> conditions) {
    return new LessMixinGuardTreeImpl(when, not.orNull(), conditions);
  }

  public SeparatedList<ParenthesisBlockTree, SyntaxToken> lessMixinGuardConditionList(ParenthesisBlockTree condition, Optional<List<Tuple<SyntaxToken, ParenthesisBlockTree>>> subsequentConditions) {
    List<ParenthesisBlockTree> conditions = Lists.newArrayList();
    List<SyntaxToken> separators = Lists.newArrayList();

    conditions.add(condition);

    if (subsequentConditions.isPresent()) {
      for (Tuple<SyntaxToken, ParenthesisBlockTree> t : subsequentConditions.get()) {
        separators.add(t.first());
        conditions.add(t.second());
      }
    }

    return new SeparatedList<>(conditions, separators);
  }

  public LessMixinParametersTree lessMixinParameters(SyntaxToken openParenthesis, Optional<SeparatedList<LessMixinParameterTree, SyntaxToken>> parameters, SyntaxToken closeParenthesis) {
    return new LessMixinParametersTreeImpl(openParenthesis, parameters.orNull(), closeParenthesis);
  }

  public SeparatedList<LessMixinParameterTree, SyntaxToken> lessMixinParameterList(LessMixinParameterTree parameter, Optional<List<Tuple<SyntaxToken, LessMixinParameterTree>>> subsequentParameters, Optional<SyntaxToken> trailingSeparator) {
    List<LessMixinParameterTree> parameters = Lists.newArrayList();
    List<SyntaxToken> separators = Lists.newArrayList();

    parameters.add(parameter);

    if (subsequentParameters.isPresent()) {
      for (Tuple<SyntaxToken, LessMixinParameterTree> t : subsequentParameters.get()) {
        separators.add(t.first());
        parameters.add(t.second());
      }
    }

    if (trailingSeparator.isPresent()) {
      separators.add(trailingSeparator.get());
    }

    return new SeparatedList<>(parameters, separators);
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

  // ---------------------------------
  // Tuple
  // ---------------------------------

  public static class Tuple<T, U> {

    private final T first;
    private final U second;

    public Tuple(T first, U second) {
      super();

      this.first = first;
      this.second = second;
    }

    public T first() {
      return first;
    }

    public U second() {
      return second;
    }
  }

  private static <T, U> Tuple<T, U> newTuple(T first, U second) {
    return new Tuple<>(first, second);
  }

  public <T, U> Tuple<T, U> newTuple1(T first, U second) {
    return newTuple(first, second);
  }

  public <T, U> Tuple<T, U> newTuple2(T first, U second) {
    return newTuple(first, second);
  }

  public <T, U> Tuple<T, U> newTuple3(T first, U second) {
    return newTuple(first, second);
  }

  public <T, U> Tuple<T, U> newTuple4(T first, U second) {
    return newTuple(first, second);
  }

  public <T, U> Tuple<T, U> newTuple5(T first, U second) {
    return newTuple(first, second);
  }

  public <T, U> Tuple<T, U> newTuple6(T first, U second) {
    return newTuple(first, second);
  }

}
