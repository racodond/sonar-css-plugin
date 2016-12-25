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
package org.sonar.css.parser.scss;

import com.sonar.sslr.api.typed.GrammarBuilder;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.css.parser.TreeFactory;
import org.sonar.css.parser.css.CssGrammar;
import org.sonar.css.tree.impl.SeparatedList;
import org.sonar.css.tree.impl.css.InternalSyntaxToken;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.tree.scss.*;

/**
 * SCSS grammar based on:
 * - http://sass-lang.com/guide
 */
public class ScssGrammar extends CssGrammar {

  public ScssGrammar(GrammarBuilder<InternalSyntaxToken> b, TreeFactory f) {
    super(b, f);
  }

  @Override
  public StyleSheetTree STYLESHEET() {
    return b.<StyleSheetTree>nonterminal(LexicalGrammar.STYLESHEET).is(
      f.stylesheet(
        b.optional(b.token(LexicalGrammar.BOM)),
        b.zeroOrMore(
          b.firstOf(
            SCSS_VARIABLE_DECLARATION(),
            SCSS_DEBUG(),
            SCSS_WARN(),
            SCSS_ERROR(),
            SCSS_FUNCTION_DEFINITION(),
            SCSS_RETURN(),
            SCSS_IF_CONDITIONS(),
            SCSS_FOR(),
            SCSS_EACH(),
            SCSS_WHILE(),
            SCSS_AT_ROOT(),
            SCSS_MIXIN_DEFINITION(),
            SCSS_MIXIN_INCLUDE(),
            AT_RULE(),
            RULESET(),
            EMPTY_STATEMENT())),
        b.token(LexicalGrammar.EOF)));
  }

  @Override
  public StatementBlockTree RULESET_BLOCK() {
    return b.<StatementBlockTree>nonterminal(LexicalGrammar.RULESET_BLOCK).is(
      f.rulesetBlock(STATEMENT_BLOCK()));
  }

  @Override
  public StatementBlockTree AT_RULE_BLOCK() {
    return b.<StatementBlockTree>nonterminal(LexicalGrammar.AT_RULE_BLOCK).is(
      f.atRuleBlock(STATEMENT_BLOCK()));
  }

  @Override
  public PropertyTree PROPERTY() {
    return b.<PropertyTree>nonterminal(LexicalGrammar.PROPERTY).is(
      f.property(
        b.firstOf(
          SCSS_INTERPOLATED_IDENTIFIER(),
          IDENTIFIER())));
  }

  @Override
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
        SCSS_INTERPOLATED_IDENTIFIER(),
        HASH(),
        UNICODE_RANGE(),
        IDENTIFIER(),
        SCSS_GLOBAL_FLAG(),
        SCSS_DEFAULT_FLAG(),
        IMPORTANT(),
        SCSS_VARIABLE(),
        b.token(LexicalGrammar.COLON),
        DELIMITER()));
  }

  @Override
  public SelectorTree SELECTOR() {
    return b.<SelectorTree>nonterminal(LexicalGrammar.SELECTOR).is(
      f.scssSelector(
        b.optional(SCSS_PARENT_SELECTOR_COMBINATOR()),
        COMPOUND_SELECTOR_COMBINATION_LIST(),
        b.optional(SELECTOR_COMBINATOR())));
  }

  @Override
  public CompoundSelectorTree COMPOUND_SELECTOR() {
    return b.<CompoundSelectorTree>nonterminal(LexicalGrammar.COMPOUND_SELECTOR).is(
      f.compoundSelector(
        b.oneOrMore(
          b.firstOf(
            SCSS_PARENT_SELECTOR(),
            SCSS_PLACEHOLDER_SELECTOR(),
            SIMPLE_CSS_SELECTOR()))));
  }

  @Override
  public DeclarationTree DECLARATION() {
    return b.<DeclarationTree>nonterminal(LexicalGrammar.DECLARATION).is(
      b.firstOf(
        SCSS_VARIABLE_DECLARATION(),
        SCSS_NESTED_PROPERTIES_DECLARATION(),
        CSS_DECLARATION()));
  }

  @Override
  public ClassSelectorTree CLASS_SELECTOR() {
    return b.<ClassSelectorTree>nonterminal(LexicalGrammar.CLASS_SELECTOR).is(
      f.classSelector(
        b.token(LexicalGrammar.DOT),
        b.firstOf(
          SCSS_INTERPOLATED_IDENTIFIER_NO_WS(),
          IDENTIFIER_NO_WS())));
  }

  @Override
  public TypeSelectorTree TYPE_SELECTOR() {
    return b.<TypeSelectorTree>nonterminal(LexicalGrammar.TYPE_SELECTOR).is(
      f.typeSelector(
        b.optional(NAMESPACE()),
        b.firstOf(
          SCSS_INTERPOLATED_IDENTIFIER_NO_WS(),
          IDENTIFIER_NO_WS())));
  }

  @Override
  public IdSelectorTree ID_SELECTOR() {
    return b.<IdSelectorTree>nonterminal(LexicalGrammar.ID_SELECTOR).is(
      f.idSelector(
        b.token(LexicalGrammar.HASH_SYMBOL_NO_WS),
        b.firstOf(
          SCSS_INTERPOLATED_IDENTIFIER_NO_WS(),
          IDENTIFIER_NO_WS())));
  }

  @Override
  public AttributeSelectorTree ATTRIBUTE_SELECTOR() {
    return b.<AttributeSelectorTree>nonterminal(LexicalGrammar.ATTRIBUTE_SELECTOR).is(
      b.firstOf(
        f.attributeSelector(
          b.token(LexicalGrammar.OPEN_BRACKET_NO_WS),
          b.firstOf(
            SCSS_INTERPOLATED_IDENTIFIER(),
            IDENTIFIER()),
          b.optional(ATTRIBUTE_MATCHER_EXPRESSION()),
          b.token(LexicalGrammar.CLOSE_BRACKET)),
        f.attributeSelector(
          b.token(LexicalGrammar.OPEN_BRACKET_NO_WS),
          b.token(LexicalGrammar.SPACING),
          NAMESPACE(),
          b.firstOf(
            SCSS_INTERPOLATED_IDENTIFIER_NO_WS(),
            IDENTIFIER_NO_WS()),
          b.optional(ATTRIBUTE_MATCHER_EXPRESSION()),
          b.token(LexicalGrammar.CLOSE_BRACKET))));
  }

  @Override
  public AttributeMatcherExpressionTree ATTRIBUTE_MATCHER_EXPRESSION() {
    return b.<AttributeMatcherExpressionTree>nonterminal(LexicalGrammar.ATTRIBUTE_MATCHER_EXPRESSION).is(
      f.attributeMatcherExpression(
        ATTRIBUTE_MATCHER(),
        b.firstOf(
          STRING(),
          b.firstOf(
            SCSS_INTERPOLATED_IDENTIFIER(),
            IDENTIFIER())),
        b.optional(CASE_INSENSITIVE_FLAG())));
  }

  @Override
  public PseudoIdentifierTree PSEUDO_IDENTIFIER() {
    return b.<PseudoIdentifierTree>nonterminal(LexicalGrammar.PSEUDO_IDENTIFIER).is(
      f.pseudoIdentifier(
        b.token(LexicalGrammar.PSEUDO_PREFIX),
        b.firstOf(
          SCSS_INTERPOLATED_IDENTIFIER_NO_WS(),
          IDENTIFIER_NO_WS())));
  }

  public SelectorCombinatorTree SCSS_PARENT_SELECTOR_COMBINATOR() {
    return b.<SelectorCombinatorTree>nonterminal(LexicalGrammar.SCSS_PARENT_SELECTOR_COMBINATOR).is(
      f.scssParentSelectorCombinator(
        b.firstOf(
          b.token(LexicalGrammar.CHILD_COMBINATOR),
          b.token(LexicalGrammar.NEXT_SIBLING_COMBINATOR),
          b.token(LexicalGrammar.FOLLOWING_SIBLING_COMBINATOR))));
  }

  public StatementBlockTree STATEMENT_BLOCK() {
    return b.<StatementBlockTree>nonterminal(LexicalGrammar.STATEMENT_BLOCK).is(
      f.statementBlock(
        b.token(LexicalGrammar.OPEN_CURLY_BRACE),
        b.zeroOrMore(
          b.firstOf(
            SCSS_CONTENT(),
            SCSS_EXTEND(),
            SCSS_DEBUG(),
            SCSS_WARN(),
            SCSS_ERROR(),
            SCSS_FUNCTION_DEFINITION(),
            SCSS_RETURN(),
            SCSS_IF_CONDITIONS(),
            SCSS_FOR(),
            SCSS_EACH(),
            SCSS_WHILE(),
            SCSS_AT_ROOT(),
            SCSS_MIXIN_DEFINITION(),
            SCSS_MIXIN_INCLUDE(),
            RULESET(),
            DECLARATION(),
            AT_RULE(),
            EMPTY_STATEMENT())),
        b.token(LexicalGrammar.CLOSE_CURLY_BRACE)));
  }

  public ValueTree SCSS_VALUE() {
    return b.<ValueTree>nonterminal(LexicalGrammar.SCSS_VALUE).is(
      f.scssValue(
        b.oneOrMore(
          b.firstOf(
            URI(),
            FUNCTION(),
            PARENTHESIS_BLOCK(),
            BRACKET_BLOCK(),
            PERCENTAGE(),
            DIMENSION(),
            NUMBER(),
            SCSS_MULTILINE_STRING(),
            STRING(),
            HASH(),
            UNICODE_RANGE(),
            SCSS_OPERATOR(),
            SCSS_INTERPOLATED_IDENTIFIER(),
            IDENTIFIER(),
            SCSS_GLOBAL_FLAG(),
            SCSS_DEFAULT_FLAG(),
            SCSS_VARIABLE(),
            DELIMITER()))));
  }

  public ValueTree SCSS_VALUE_WITHOUT_DELIMITER() {
    return b.<ValueTree>nonterminal(LexicalGrammar.SCSS_VALUE_WITHOUT_DELIMITER).is(
      f.scssValueWithoutDelimiter(
        b.oneOrMore(
          b.firstOf(
            URI(),
            FUNCTION(),
            PARENTHESIS_BLOCK(),
            BRACKET_BLOCK(),
            PERCENTAGE(),
            DIMENSION(),
            NUMBER(),
            SCSS_MULTILINE_STRING(),
            STRING(),
            HASH(),
            UNICODE_RANGE(),
            SCSS_OPERATOR(),
            SCSS_INTERPOLATED_IDENTIFIER(),
            IDENTIFIER(),
            SCSS_GLOBAL_FLAG(),
            SCSS_DEFAULT_FLAG(),
            SCSS_VARIABLE()))));
  }

  public ScssFunctionDefinitionTree SCSS_FUNCTION_DEFINITION() {
    return b.<ScssFunctionDefinitionTree>nonterminal(LexicalGrammar.SCSS_FUNCTION_DEFINITION).is(
      f.scssFunctionDefinition(
        b.token(LexicalGrammar.SCSS_FUNCTION_DEFINITION_DIRECTIVE),
        IDENTIFIER(),
        b.optional(SCSS_DEFINITION_PARAMETERS()),
        STATEMENT_BLOCK()));
  }

  public ScssMixinDefinitionTree SCSS_MIXIN_DEFINITION() {
    return b.<ScssMixinDefinitionTree>nonterminal(LexicalGrammar.SCSS_MIXIN_DEFINITION).is(
      f.scssMixinDefinition(
        b.token(LexicalGrammar.SCSS_MIXIN_DEFINITION_DIRECTIVE),
        IDENTIFIER(),
        b.optional(SCSS_DEFINITION_PARAMETERS()),
        STATEMENT_BLOCK()));
  }

  public ScssMixinIncludeTree SCSS_MIXIN_INCLUDE() {
    return b.<ScssMixinIncludeTree>nonterminal(LexicalGrammar.SCSS_MIXIN_INCLUDE).is(
      f.scssMixinInclude(
        b.token(LexicalGrammar.SCSS_MIXIN_INCLUDE_DIRECTIVE),
        IDENTIFIER(),
        b.optional(SCSS_CALL_PARAMETERS()),
        b.optional(STATEMENT_BLOCK()),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssParametersTree SCSS_DEFINITION_PARAMETERS() {
    return b.<ScssParametersTree>nonterminal(LexicalGrammar.SCSS_DEFINITION_PARAMETERS).is(
      f.scssDefinitionParameters(
        b.token(LexicalGrammar.OPEN_PARENTHESIS),
        b.optional(SCSS_DEFINITION_PARAMETER_LIST()),
        b.token(LexicalGrammar.CLOSE_PARENTHESIS)));
  }

  public ScssParametersTree SCSS_CALL_PARAMETERS() {
    return b.<ScssParametersTree>nonterminal(LexicalGrammar.SCSS_CALL_PARAMETERS).is(
      f.scssCallParameters(
        b.token(LexicalGrammar.OPEN_PARENTHESIS),
        b.optional(SCSS_CALL_PARAMETER_LIST()),
        b.token(LexicalGrammar.CLOSE_PARENTHESIS)));
  }

  public SeparatedList<ScssParameterTree, SyntaxToken> SCSS_DEFINITION_PARAMETER_LIST() {
    return b.<SeparatedList<ScssParameterTree, SyntaxToken>>nonterminal().is(
      f.scssDefinitionParameterList(
        SCSS_DEFINITION_PARAMETER(),
        b.zeroOrMore(
          f.newTuple5(
            b.token(LexicalGrammar.COMMA),
            SCSS_DEFINITION_PARAMETER()))));
  }

  public SeparatedList<ScssParameterTree, SyntaxToken> SCSS_CALL_PARAMETER_LIST() {
    return b.<SeparatedList<ScssParameterTree, SyntaxToken>>nonterminal().is(
      f.scssCallParameterList(
        SCSS_CALL_PARAMETER(),
        b.zeroOrMore(
          f.newTuple6(
            b.token(LexicalGrammar.COMMA),
            SCSS_CALL_PARAMETER()))));
  }

  public ScssParameterTree SCSS_DEFINITION_PARAMETER() {
    return b.<ScssParameterTree>nonterminal(LexicalGrammar.SCSS_DEFINITION_PARAMETER).is(
      f.scssDefinitionParameter(
        b.firstOf(
          SCSS_VARIABLE_DECLARATION_WITHOUT_DELIMITER_IN_VALUE(),
          SCSS_VARIABLE_ARGUMENT(),
          SCSS_VARIABLE())));
  }

  public ScssParameterTree SCSS_CALL_PARAMETER() {
    return b.<ScssParameterTree>nonterminal(LexicalGrammar.SCSS_CALL_PARAMETER).is(
      f.scssCallParameter(
        b.firstOf(
          SCSS_VARIABLE_DECLARATION_WITHOUT_DELIMITER_IN_VALUE(),
          SCSS_VARIABLE_ARGUMENT(),
          SCSS_VALUE_WITHOUT_DELIMITER())));
  }

  public ScssParentSelectorTree SCSS_PARENT_SELECTOR() {
    return b.<ScssParentSelectorTree>nonterminal(LexicalGrammar.SCSS_PARENT_SELECTOR).is(
      f.scssParentSelector(b.token(LexicalGrammar.SCSS_PARENT_SELECTOR_KEYWORD)));
  }

  public ScssNestedPropertiesDeclarationTree SCSS_NESTED_PROPERTIES_DECLARATION() {
    return b.<ScssNestedPropertiesDeclarationTree>nonterminal(LexicalGrammar.SCSS_NESTED_PROPERTIES_DECLARATION).is(
      f.scssNestedPropertiesDeclaration(
        PROPERTY(),
        b.token(LexicalGrammar.COLON),
        STATEMENT_BLOCK()));
  }

  public ScssVariableDeclarationTree SCSS_VARIABLE_DECLARATION() {
    return b.<ScssVariableDeclarationTree>nonterminal(LexicalGrammar.SCSS_VARIABLE_DECLARATION).is(
      f.scssVariableDeclaration(
        SCSS_VARIABLE(),
        b.token(LexicalGrammar.COLON),
        SCSS_VALUE(),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssVariableDeclarationTree SCSS_VARIABLE_DECLARATION_WITHOUT_DELIMITER_IN_VALUE() {
    return b.<ScssVariableDeclarationTree>nonterminal(LexicalGrammar.SCSS_VARIABLE_DECLARATION_WITHOUT_DELIMITER_IN_VALUE).is(
      f.scssVariableDeclarationWithoutDelimiterInValue(
        SCSS_VARIABLE(),
        b.token(LexicalGrammar.COLON),
        SCSS_VALUE_WITHOUT_DELIMITER(),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssVariableTree SCSS_VARIABLE() {
    return b.<ScssVariableTree>nonterminal(LexicalGrammar.SCSS_VARIABLE).is(
      f.scssVariable(
        b.token(LexicalGrammar.SCSS_VARIABLE_PREFIX),
        IDENTIFIER_NO_WS()));
  }

  public ScssVariableArgumentTree SCSS_VARIABLE_ARGUMENT() {
    return b.<ScssVariableArgumentTree>nonterminal(LexicalGrammar.SCSS_VARIABLE_ARGUMENT).is(
      f.scssVariableArgument(
        b.token(LexicalGrammar.SCSS_VARIABLE_PREFIX),
        IDENTIFIER_NO_WS(),
        b.token(LexicalGrammar.SCSS_ELLIPSIS)));
  }

  public ScssDefaultFlagTree SCSS_DEFAULT_FLAG() {
    return b.<ScssDefaultFlagTree>nonterminal(LexicalGrammar.SCSS_DEFAULT_FLAG).is(
      f.scssDefaultFlag(
        b.token(LexicalGrammar.SCSS_DEFAULT_KEYWORD)));
  }

  public ScssGlobalFlagTree SCSS_GLOBAL_FLAG() {
    return b.<ScssGlobalFlagTree>nonterminal(LexicalGrammar.SCSS_GLOBAL_FLAG).is(
      f.scssGlobalFlag(
        b.token(LexicalGrammar.SCSS_GLOBAL_KEYWORD)));
  }

  public ScssOptionalFlagTree SCSS_OPTIONAL_FLAG() {
    return b.<ScssOptionalFlagTree>nonterminal(LexicalGrammar.SCSS_OPTIONAL_FLAG).is(
      f.scssOptionalFlag(
        b.token(LexicalGrammar.SCSS_OPTIONAL_KEYWORD)));
  }

  public ScssExtendTree SCSS_EXTEND() {
    return b.<ScssExtendTree>nonterminal(LexicalGrammar.SCSS_EXTEND).is(
      f.scssExtend(
        b.token(LexicalGrammar.SCSS_EXTEND_DIRECTIVE),
        COMPOUND_SELECTOR(),
        b.optional(SCSS_OPTIONAL_FLAG()),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssContentTree SCSS_CONTENT() {
    return b.<ScssContentTree>nonterminal(LexicalGrammar.SCSS_CONTENT).is(
      f.scssContent(
        b.token(LexicalGrammar.SCSS_CONTENT_DIRECTIVE),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssDebugTree SCSS_DEBUG() {
    return b.<ScssDebugTree>nonterminal(LexicalGrammar.SCSS_DEBUG).is(
      f.scssDebug(
        b.token(LexicalGrammar.SCSS_DEBUG_DIRECTIVE),
        SCSS_VALUE(),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssWarnTree SCSS_WARN() {
    return b.<ScssWarnTree>nonterminal(LexicalGrammar.SCSS_WARN).is(
      f.scssWarn(
        b.token(LexicalGrammar.SCSS_WARN_DIRECTIVE),
        SCSS_VALUE(),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssErrorTree SCSS_ERROR() {
    return b.<ScssErrorTree>nonterminal(LexicalGrammar.SCSS_ERROR).is(
      f.scssError(
        b.token(LexicalGrammar.SCSS_ERROR_DIRECTIVE),
        SCSS_VALUE(),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssReturnTree SCSS_RETURN() {
    return b.<ScssReturnTree>nonterminal(LexicalGrammar.SCSS_RETURN).is(
      f.scssReturn(
        b.token(LexicalGrammar.SCSS_RETURN_DIRECTIVE),
        SCSS_VALUE(),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssIfConditionsTree SCSS_IF_CONDITIONS() {
    return b.<ScssIfConditionsTree>nonterminal(LexicalGrammar.SCSS_IF_CONDITIONS).is(
      f.scssIfConditions(
        SCSS_IF(),
        b.zeroOrMore(SCSS_ELSE_IF()),
        b.optional(SCSS_ELSE())));
  }

  public ScssIfTree SCSS_IF() {
    return b.<ScssIfTree>nonterminal(LexicalGrammar.SCSS_IF).is(
      f.scssIf(
        b.token(LexicalGrammar.SCSS_IF_DIRECTIVE),
        SCSS_VALUE(),
        STATEMENT_BLOCK()));
  }

  public ScssElseIfTree SCSS_ELSE_IF() {
    return b.<ScssElseIfTree>nonterminal(LexicalGrammar.SCSS_ELSE_IF).is(
      f.scssElseIf(
        b.token(LexicalGrammar.SCSS_ELSE_IF_DIRECTIVE),
        SCSS_VALUE(),
        STATEMENT_BLOCK()));
  }

  public ScssElseTree SCSS_ELSE() {
    return b.<ScssElseTree>nonterminal(LexicalGrammar.SCSS_ELSE).is(
      f.scssElse(
        b.token(LexicalGrammar.SCSS_ELSE_DIRECTIVE),
        STATEMENT_BLOCK()));
  }

  public ScssWhileTree SCSS_WHILE() {
    return b.<ScssWhileTree>nonterminal(LexicalGrammar.SCSS_WHILE).is(
      f.scssWhile(
        b.token(LexicalGrammar.SCSS_WHILE_DIRECTIVE),
        SCSS_VALUE(),
        STATEMENT_BLOCK()));
  }

  public ScssForTree SCSS_FOR() {
    return b.<ScssForTree>nonterminal(LexicalGrammar.SCSS_FOR).is(
      f.scssFor(
        b.token(LexicalGrammar.SCSS_FOR_DIRECTIVE),
        SCSS_VALUE(),
        STATEMENT_BLOCK()));
  }

  public ScssEachTree SCSS_EACH() {
    return b.<ScssEachTree>nonterminal(LexicalGrammar.SCSS_EACH).is(
      f.scssEach(
        b.token(LexicalGrammar.SCSS_EACH_DIRECTIVE),
        SCSS_VALUE(),
        STATEMENT_BLOCK()));
  }

  public ScssAtRootTree SCSS_AT_ROOT() {
    return b.<ScssAtRootTree>nonterminal(LexicalGrammar.SCSS_AT_ROOT).is(
      f.scssAtRoot(
        b.token(LexicalGrammar.SCSS_AT_ROOT_DIRECTIVE),
        b.optional(SCSS_AT_ROOT_PARAMETERS()),
        b.firstOf(
          STATEMENT_BLOCK(),
          RULESET()
        )));
  }

  public ScssAtRootParametersTree SCSS_AT_ROOT_PARAMETERS() {
    return b.<ScssAtRootParametersTree>nonterminal(LexicalGrammar.SCSS_AT_ROOT_PARAMETERS).is(
      f.scssAtRootParameters(
        b.token(LexicalGrammar.OPEN_PARENTHESIS),
        b.firstOf(
          b.token(LexicalGrammar.SCSS_AT_ROOT_WITHOUT),
          b.token(LexicalGrammar.SCSS_AT_ROOT_WITH)),
        b.token(LexicalGrammar.COLON),
        b.oneOrMore(IDENTIFIER()),
        b.token(LexicalGrammar.CLOSE_PARENTHESIS)));
  }

  public ScssPlaceholderSelectorTree SCSS_PLACEHOLDER_SELECTOR() {
    return b.<ScssPlaceholderSelectorTree>nonterminal(LexicalGrammar.SCSS_PLACEHOLDER_SELECTOR).is(
      f.scssPlaceholderSelector(
        b.token(LexicalGrammar.PERCENTAGE_SYMBOL),
        b.firstOf(
          SCSS_INTERPOLATED_IDENTIFIER_NO_WS(),
          IDENTIFIER_NO_WS())));
  }

  public IdentifierTree SCSS_INTERPOLATED_IDENTIFIER() {
    return b.<IdentifierTree>nonterminal(LexicalGrammar.SCSS_INTERPOLATED_IDENTIFIER).is(
      f.scssInterpolatedIdentifier(b.token(LexicalGrammar.SCSS_IDENT_INTERPOLATED_IDENTIFIER)));
  }

  public IdentifierTree SCSS_INTERPOLATED_IDENTIFIER_NO_WS() {
    return b.<IdentifierTree>nonterminal(LexicalGrammar.SCSS_INTERPOLATED_IDENTIFIER_NO_WS).is(
      f.scssInterpolatedIdentifierNoWs(b.token(LexicalGrammar.SCSS_IDENT_INTERPOLATED_IDENTIFIER_NO_WS)));
  }

  public ScssOperatorTree SCSS_OPERATOR() {
    return b.<ScssOperatorTree>nonterminal(LexicalGrammar.SCSS_OPERATOR).is(
      f.scssOperator(b.token(LexicalGrammar.SCSS_OPERATOR_LITERAL)));
  }

  public ScssMultilineStringTree SCSS_MULTILINE_STRING() {
    return b.<ScssMultilineStringTree>nonterminal(LexicalGrammar.SCSS_MULTILINE_STRING).is(
      f.scssMultilineString(b.token(LexicalGrammar.SCSS_MULTILINE_STRING_LITERAL)));
  }

}
