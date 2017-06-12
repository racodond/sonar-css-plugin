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

  public AtRuleTree atRule(AtKeywordTree atKeyword, Optional<ValueTree> prelude, Optional<StatementBlockTree> block, Optional<SyntaxToken> semicolon) {
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

  public ValueTree declarationValue(List<Tree> valueElements) {
    return new ValueTreeImpl(valueElements);
  }

  public ValueTree simpleValueWithoutCommaDeclarationList(List<Tree> valueElements) {
    return new ValueTreeImpl(valueElements);
  }

  public ValueTree simpleValue(List<Tree> valueElements) {
    return new ValueTreeImpl(valueElements);
  }

  public FunctionTree function(IdentifierTree functionName, ParametersTree parameters) {
    return new FunctionTreeImpl(functionName, parameters);
  }

  public ParametersTree parameters(SyntaxToken openParenthesis, Optional<SeparatedList<ValueTree, DelimiterTree>> parameters, SyntaxToken closeParenthesis) {
    return new ParametersTreeImpl(openParenthesis, parameters.orNull(), closeParenthesis);
  }

  public SeparatedList<ValueTree, DelimiterTree> parameterList(ValueTree parameter, Optional<List<Tuple<DelimiterTree, ValueTree>>> subsequentParameters) {
    List<ValueTree> parameters = Lists.newArrayList(parameter);
    List<DelimiterTree> separators = Lists.newArrayList();

    if (subsequentParameters.isPresent()) {
      for (Tuple<DelimiterTree, ValueTree> t : subsequentParameters.get()) {
        separators.add(t.first());
        parameters.add(t.second());
      }
    }

    return new SeparatedList<>(parameters, separators);
  }

  public SelectorsTree selectors(SeparatedList<SelectorTree, DelimiterTree> selectors) {
    return new SelectorsTreeImpl(selectors);
  }

  public SeparatedList<SelectorTree, DelimiterTree> selectorList(SelectorTree selector, Optional<List<Tuple<DelimiterTree, SelectorTree>>> subsequentSelectors, Optional<DelimiterTree> trailingComma) {
    List<SelectorTree> selectors = Lists.newArrayList(selector);
    List<DelimiterTree> separators = Lists.newArrayList();

    if (subsequentSelectors.isPresent()) {
      for (Tuple<DelimiterTree, SelectorTree> t : subsequentSelectors.get()) {
        separators.add(t.first());
        selectors.add(t.second());
      }
    }

    if (trailingComma.isPresent()) {
      separators.add(trailingComma.get());
    }

    return new SeparatedList<>(selectors, separators);
  }

  public SelectorTree selector(SeparatedList<CompoundSelectorTree, SelectorCombinatorTree> compoundSelectors) {
    return new SelectorTreeImpl(compoundSelectors);
  }

  public SeparatedList<CompoundSelectorTree, SelectorCombinatorTree> selectorCombinationList(CompoundSelectorTree selector, Optional<List<Tuple<SelectorCombinatorTree, CompoundSelectorTree>>> subsequentSelectors) {
    List<CompoundSelectorTree> compoundSelectors = Lists.newArrayList(selector);
    List<SelectorCombinatorTree> selectorCombinators = Lists.newArrayList();

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

  public PseudoFunctionTree pseudoFunction(SyntaxToken prefix, IdentifierTree pseudoFunctionName, ParametersTree parameters) {
    return new PseudoFunctionTreeImpl(prefix, pseudoFunctionName, parameters);
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

  public ImportantFlagTree important(SyntaxToken exclamationMark, SyntaxToken importantKeyword) {
    return new ImportantFlagTreeImpl(exclamationMark, importantKeyword);
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

  public ValueCommaSeparatedListTree valueCommaSeparatedList(ValueTree value, List<Tuple<DelimiterTree, ValueTree>> subsequentValues, Optional<DelimiterTree> trailingComma) {
    List<ValueTree> values = Lists.newArrayList(value);
    List<DelimiterTree> separators = Lists.newArrayList();

    for (Tuple<DelimiterTree, ValueTree> t : subsequentValues) {
      separators.add(t.first());
      values.add(t.second());
    }

    if (trailingComma.isPresent()) {
      separators.add(trailingComma.get());
    }

    return new ValueCommaSeparatedListTreeImpl(new SeparatedList<>(values, separators));
  }

  public ValueCommaSeparatedListTree valueCommaSeparatedListOneSingleValue(ValueTree value, DelimiterTree trailingComma) {
    List<ValueTree> values = Lists.newArrayList(value);
    List<DelimiterTree> separators = Lists.newArrayList(trailingComma);
    return new ValueCommaSeparatedListTreeImpl(new SeparatedList<>(values, separators));
  }

  public DelimiterTree delimiter(SyntaxToken delimiter) {
    return new DelimiterTreeImpl(delimiter);
  }

  public DelimiterTree commaDelimiter(SyntaxToken comma) {
    return new DelimiterTreeImpl(comma);
  }

  public DelimiterTree semicolonDelimiter(SyntaxToken semicolon) {
    return new DelimiterTreeImpl(semicolon);
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

  public ScssParentReferencingSelectorTree scssParentReferencingSelector(ScssParentSelectorTree parent, IdentifierTree append) {
    return new ScssParentReferencingSelectorTreeImpl(parent, append);
  }

  public ScssPlaceholderSelectorTree scssPlaceholderSelector(SyntaxToken prefix, IdentifierTree name) {
    return new ScssPlaceholderSelectorTreeImpl(prefix, name);
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

  public ScssVariableDeclarationTree scssVariableDeclarationAsParameter(ScssVariableTree variable, SyntaxToken colon, ValueTree value) {
    return new ScssVariableDeclarationTreeImpl(variable, colon, value, null);
  }

  public ScssVariableTree scssVariable(SyntaxToken prefix, IdentifierTree name) {
    return new ScssVariableTreeImpl(prefix, name);
  }

  public ScssVariableArgumentTree scssVariableArgument(SyntaxToken prefix, IdentifierTree name, SyntaxToken ellipsis) {
    return new ScssVariableArgumentTreeImpl(prefix, name, ellipsis);
  }

  public ScssDefaultFlagTree scssDefaultFlag(SyntaxToken flag) {
    return new ScssDefaultFlagTreeImpl(flag);
  }

  public ScssGlobalFlagTree scssGlobalFlag(SyntaxToken flag) {
    return new ScssGlobalFlagTreeImpl(flag);
  }

  public ScssOptionalFlagTree scssOptionalFlag(SyntaxToken flag) {
    return new ScssOptionalFlagTreeImpl(flag);
  }

  public ScssFunctionTree scssFunction(ScssDirectiveTree directive, IdentifierTree name, Optional<ScssParametersTree> parameters, StatementBlockTree block) {
    return new ScssFunctionTreeImpl(directive, name, parameters.orNull(), block);
  }

  public ScssMixinTree scssMixin(ScssDirectiveTree directive, IdentifierTree name, Optional<ScssParametersTree> parameters, StatementBlockTree block) {
    return new ScssMixinTreeImpl(directive, name, parameters.orNull(), block);
  }

  public ScssIncludeTree scssInclude(ScssDirectiveTree directive, IdentifierTree name, Optional<ScssParametersTree> parameters, Optional<StatementBlockTree> block, Optional<SyntaxToken> semicolon) {
    return new ScssIncludeTreeImpl(directive, name, parameters.orNull(), block.orNull(), semicolon.orNull());
  }

  public ScssParametersTree scssParameters(SyntaxToken openParenthesis, Optional<SeparatedList<ScssParameterTree, DelimiterTree>> parameters, SyntaxToken closeParenthesis) {
    return new ScssParametersTreeImpl(openParenthesis, parameters.orNull(), closeParenthesis);
  }

  public ScssParametersTree scssCallParameters(SyntaxToken openParenthesis, Optional<SeparatedList<ScssParameterTree, DelimiterTree>> parameters, SyntaxToken closeParenthesis) {
    return new ScssParametersTreeImpl(openParenthesis, parameters.orNull(), closeParenthesis);
  }

  public SeparatedList<ScssParameterTree, DelimiterTree> scssCallParameterList(ScssParameterTree parameter, Optional<List<Tuple<DelimiterTree, ScssParameterTree>>> subsequentParameters) {
    return scssParameterList(parameter, subsequentParameters);
  }

  public SeparatedList<ScssParameterTree, DelimiterTree> scssParameterList(ScssParameterTree parameter, Optional<List<Tuple<DelimiterTree, ScssParameterTree>>> subsequentParameters) {
    List<ScssParameterTree> parameters = Lists.newArrayList(parameter);
    List<DelimiterTree> separators = Lists.newArrayList();

    if (subsequentParameters.isPresent()) {
      for (Tuple<DelimiterTree, ScssParameterTree> t : subsequentParameters.get()) {
        separators.add(t.first());
        parameters.add(t.second());
      }
    }

    return new SeparatedList<>(parameters, separators);
  }

  public ScssParameterTree scssParameter(Tree parameter) {
    return new ScssParameterTreeImpl(parameter);
  }

  public ScssParameterTree scssCallParameter(Tree parameter) {
    return new ScssParameterTreeImpl(parameter);
  }

  public ScssExtendTree scssExtend(ScssDirectiveTree directive, CompoundSelectorTree compoundSelector, Optional<ScssOptionalFlagTree> optionalFlag, Optional<SyntaxToken> semicolon) {
    return new ScssExtendTreeImpl(directive, compoundSelector, optionalFlag.orNull(), semicolon.orNull());
  }

  public ScssContentTree scssContent(ScssDirectiveTree directive, Optional<SyntaxToken> semicolon) {
    return new ScssContentTreeImpl(directive, semicolon.orNull());
  }

  public ScssDebugTree scssDebug(ScssDirectiveTree directive, ValueTree value, Optional<SyntaxToken> semicolon) {
    return new ScssDebugTreeImpl(directive, value, semicolon.orNull());
  }

  public ScssWarnTree scssWarn(ScssDirectiveTree directive, ValueTree value, Optional<SyntaxToken> semicolon) {
    return new ScssWarnTreeImpl(directive, value, semicolon.orNull());
  }

  public ScssErrorTree scssError(ScssDirectiveTree directive, ValueTree value, Optional<SyntaxToken> semicolon) {
    return new ScssErrorTreeImpl(directive, value, semicolon.orNull());
  }

  public ScssReturnTree scssReturn(ScssDirectiveTree directive, ValueTree value, Optional<SyntaxToken> semicolon) {
    return new ScssReturnTreeImpl(directive, value, semicolon.orNull());
  }

  public ScssIfElseIfElseTree scssIfElseIfElse(ScssIfTree ife, Optional<List<ScssElseIfTree>> elseIf, Optional<ScssElseTree> elsee) {
    return new ScssIfElseIfElseTreeImpl(ife, elseIf.orNull(), elsee.orNull());
  }

  public ScssIfTree scssIf(ScssDirectiveTree directive, ScssConditionTree condition, StatementBlockTree block) {
    return new ScssIfTreeImpl(directive, condition, block);
  }

  public ScssElseIfTree scssElseIf(ScssDirectiveTree directive, ScssConditionTree condition, StatementBlockTree block) {
    return new ScssElseIfTreeImpl(directive, condition, block);
  }

  public ScssWhileTree scssWhile(ScssDirectiveTree directive, ScssConditionTree condition, StatementBlockTree block) {
    return new ScssWhileTreeImpl(directive, condition, block);
  }

  public ScssElseTree scssElse(ScssDirectiveTree directive, StatementBlockTree block) {
    return new ScssElseTreeImpl(directive, block);
  }

  public ScssForTree scssFor(ScssDirectiveTree directive, ScssConditionTree condition, StatementBlockTree block) {
    return new ScssForTreeImpl(directive, condition, block);
  }

  public ScssEachTree scssEach(ScssDirectiveTree directive, ScssConditionTree condition, StatementBlockTree block) {
    return new ScssEachTreeImpl(directive, condition, block);
  }

  public ScssAtRootTree scssAtRoot(ScssDirectiveTree directive, Optional<ScssAtRootParametersTree> parameters, Tree content) {
    return new ScssAtRootTreeImpl(directive, parameters.orNull(), content);
  }

  public ScssAtRootParametersTree scssAtRootParameters(SyntaxToken openParenthesis, SyntaxToken parameter, SyntaxToken colon, List<IdentifierTree> values, SyntaxToken closeParenthesis) {
    return new ScssAtRootParametersTreeImpl(openParenthesis, parameter, colon, values, closeParenthesis);
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

  public ScssConditionTree scssCondition(ValueTree condition) {
    return new ScssConditionTreeImpl(condition);
  }

  public ScssDirectiveTree scssExtendDirective(SyntaxToken at, SyntaxToken name) {
    return new ScssDirectiveTreeImpl(at, name);
  }

  public ScssDirectiveTree scssContentDirective(SyntaxToken at, SyntaxToken name) {
    return new ScssDirectiveTreeImpl(at, name);
  }

  public ScssDirectiveTree scssDebugDirective(SyntaxToken at, SyntaxToken name) {
    return new ScssDirectiveTreeImpl(at, name);
  }

  public ScssDirectiveTree scssWarnDirective(SyntaxToken at, SyntaxToken name) {
    return new ScssDirectiveTreeImpl(at, name);
  }

  public ScssDirectiveTree scssErrorDirective(SyntaxToken at, SyntaxToken name) {
    return new ScssDirectiveTreeImpl(at, name);
  }

  public ScssDirectiveTree scssIfDirective(SyntaxToken at, SyntaxToken name) {
    return new ScssDirectiveTreeImpl(at, name);
  }

  public ScssDirectiveTree scssElseIfDirective(SyntaxToken at, SyntaxToken name) {
    return new ScssDirectiveTreeImpl(at, name);
  }

  public ScssDirectiveTree scssElseDirective(SyntaxToken at, SyntaxToken name) {
    return new ScssDirectiveTreeImpl(at, name);
  }

  public ScssDirectiveTree scssForDirective(SyntaxToken at, SyntaxToken name) {
    return new ScssDirectiveTreeImpl(at, name);
  }

  public ScssDirectiveTree scssWhileDirective(SyntaxToken at, SyntaxToken name) {
    return new ScssDirectiveTreeImpl(at, name);
  }

  public ScssDirectiveTree scssEachDirective(SyntaxToken at, SyntaxToken name) {
    return new ScssDirectiveTreeImpl(at, name);
  }

  public ScssDirectiveTree scssMixinDirective(SyntaxToken at, SyntaxToken name) {
    return new ScssDirectiveTreeImpl(at, name);
  }

  public ScssDirectiveTree scssIncludeDirective(SyntaxToken at, SyntaxToken name) {
    return new ScssDirectiveTreeImpl(at, name);
  }

  public ScssDirectiveTree scssFunctionDirective(SyntaxToken at, SyntaxToken name) {
    return new ScssDirectiveTreeImpl(at, name);
  }

  public ScssDirectiveTree scssReturnDirective(SyntaxToken at, SyntaxToken name) {
    return new ScssDirectiveTreeImpl(at, name);
  }

  public ScssDirectiveTree scssAtRootDirective(SyntaxToken at, SyntaxToken name) {
    return new ScssDirectiveTreeImpl(at, name);
  }

  public ScssMapTree scssMap(SyntaxToken openParenthesis, SeparatedList<ScssMapEntryTree, DelimiterTree> entries, SyntaxToken closeParenthesis) {
    return new ScssMapTreeImpl(openParenthesis, entries, closeParenthesis);
  }

  public SeparatedList<ScssMapEntryTree, DelimiterTree> scssMapEntryList(ScssMapEntryTree mapEntry, Optional<List<Tuple<DelimiterTree, ScssMapEntryTree>>> subsequentMapEntries, Optional<DelimiterTree> trailingComma) {
    List<ScssMapEntryTree> mapEntries = Lists.newArrayList(mapEntry);
    List<DelimiterTree> commas = Lists.newArrayList();

    if (subsequentMapEntries.isPresent()) {
      for (Tuple<DelimiterTree, ScssMapEntryTree> t : subsequentMapEntries.get()) {
        commas.add(t.first());
        mapEntries.add(t.second());
      }
    }

    if (trailingComma.isPresent()) {
      commas.add(trailingComma.get());
    }

    return new SeparatedList<>(mapEntries, commas);
  }

  public ScssMapEntryTree scssMapEntry(ValueTree key, SyntaxToken colon, ValueTree value) {
    return new ScssMapEntryTreeImpl(key, colon, value);
  }

  public ValueTree simpleValueSassScriptExpression(List<Tree> valueElements) {
    return new ValueTreeImpl(valueElements);
  }

  public ValueTree simpleValueSassScriptExpressionWithoutCommaSeparatedList(List<Tree> valueElements) {
    return new ValueTreeImpl(valueElements);
  }

  // ---------------------------------
  // Less
  // ---------------------------------

  public PropertyTree property(IdentifierTree property, Optional<SyntaxToken> merge) {
    return new PropertyTreeImpl(property, merge.orNull());
  }

  public SelectorTree lessSelector(Optional<SelectorCombinatorTree> parentCombinator, SeparatedList<CompoundSelectorTree, SelectorCombinatorTree> selectors, Optional<LessExtendTree> extend,
                                   Optional<LessMixinParametersTree> parameters, Optional<LessMixinGuardTree> guard) {
    return new SelectorTreeImpl(parentCombinator.orNull(), selectors, extend.orNull(), parameters.orNull(), guard.orNull());
  }

  public LessVariableDeclarationTree lessVariableDeclaration(LessVariableTree variable, SyntaxToken colon, ValueTree value, Optional<SyntaxToken> semicolon) {
    return new LessVariableDeclarationTreeImpl(variable, colon, value, semicolon.orNull());
  }

  public LessVariableDeclarationTree lessVariableDeclarationAsParameter(LessVariableTree variable, SyntaxToken colon, ValueTree value) {
    return new LessVariableDeclarationTreeImpl(variable, colon, value, null);
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

  public LessExtendTree lessExtend(SyntaxToken extendKeyword, SyntaxToken openParenthesis, List<ValueTree> parameterElements, SyntaxToken closeParenthesis) {
    return new LessExtendTreeImpl(extendKeyword, openParenthesis, parameterElements, closeParenthesis);
  }

  public LessParentSelectorTree lessParentSelector(SyntaxToken parentSelector) {
    return new LessParentSelectorTreeImpl(parentSelector);
  }

  public LessParentReferencingSelectorTree lessParentReferencingSelector(LessParentSelectorTree parent, IdentifierTree append) {
    return new LessParentReferencingSelectorTreeImpl(parent, append);
  }

  public SelectorCombinatorTree lessParentSelectorCombinator(SyntaxToken combinator) {
    return new SelectorCombinatorTreeImpl(combinator);
  }

  public LessMixinCallTree lessMixinCall(Optional<SelectorCombinatorTree> parentCombinator, Optional<SyntaxToken> spacing, SelectorTree selector, Optional<ImportantFlagTree> important,
                                         Optional<SyntaxToken> semicolon) {
    return new LessMixinCallTreeImpl(parentCombinator.orNull(), selector, important.orNull(), semicolon.orNull());
  }

  public LessMixinGuardTree lessMixinGuard(SyntaxToken when, SeparatedList<LessMixinGuardConditionTree, SyntaxToken> conditions) {
    return new LessMixinGuardTreeImpl(when, conditions);
  }

  public SeparatedList<LessMixinGuardConditionTree, SyntaxToken> lessMixinGuardConditionList(LessMixinGuardConditionTree condition, Optional<List<Tuple<SyntaxToken, LessMixinGuardConditionTree>>> subsequentConditions) {
    List<LessMixinGuardConditionTree> conditions = Lists.newArrayList(condition);
    List<SyntaxToken> separators = Lists.newArrayList();

    if (subsequentConditions.isPresent()) {
      for (Tuple<SyntaxToken, LessMixinGuardConditionTree> t : subsequentConditions.get()) {
        separators.add(t.first());
        conditions.add(t.second());
      }
    }

    return new SeparatedList<>(conditions, separators);
  }

  public LessMixinGuardConditionTree lessMixinGuardCondition(Optional<SyntaxToken> not, ParenthesisBlockTree block) {
    return new LessMixinGuardConditionTreeImpl(not.orNull(), block);
  }

  public LessMixinParametersTree lessMixinParameters(SyntaxToken openParenthesis, Optional<SeparatedList<LessMixinParameterTree, DelimiterTree>> parameters, SyntaxToken closeParenthesis) {
    return new LessMixinParametersTreeImpl(openParenthesis, parameters.orNull(), closeParenthesis);
  }

  public SeparatedList<LessMixinParameterTree, DelimiterTree> lessMixinParameterList(LessMixinParameterTree parameter, Optional<List<Tuple<DelimiterTree, LessMixinParameterTree>>> subsequentParameters, Optional<DelimiterTree> trailingSeparator) {
    List<LessMixinParameterTree> parameters = Lists.newArrayList(parameter);
    List<DelimiterTree> separators = Lists.newArrayList();

    if (subsequentParameters.isPresent()) {
      for (Tuple<DelimiterTree, LessMixinParameterTree> t : subsequentParameters.get()) {
        separators.add(t.first());
        parameters.add(t.second());
      }
    }

    if (trailingSeparator.isPresent()) {
      separators.add(trailingSeparator.get());
    }

    return new SeparatedList<>(parameters, separators);
  }

  public LessMixinParameterTree lessMixinParameter(Tree parameter) {
    return new LessMixinParameterTreeImpl(parameter);
  }

  public LessEscapingTree lessEscaping(SyntaxToken escapingSymbol, StringTree string) {
    return new LessEscapingTreeImpl(escapingSymbol, string);
  }

  public LessOperatorTree lessOperator(SyntaxToken operator) {
    return new LessOperatorTreeImpl(operator);
  }

  public ValueTree simpleValueLessExpressionWithoutCommaSeparatedList(List<Tree> valueElements) {
    return new ValueTreeImpl(valueElements);
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

  public <T, U> Tuple<T, U> newTuple7(T first, U second) {
    return newTuple(first, second);
  }

  public <T, U> Tuple<T, U> newTuple8(T first, U second) {
    return newTuple(first, second);
  }

  public <T, U> Tuple<T, U> newTuple9(T first, U second) {
    return newTuple(first, second);
  }

}
