/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
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
 * SCSS grammar based on http://sass-lang.com/guide
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
            SCSS_FUNCTION(),
            SCSS_IF_ELSE_IF_ELSE(),
            SCSS_FOR(),
            SCSS_EACH(),
            SCSS_WHILE(),
            SCSS_AT_ROOT(),
            SCSS_MIXIN(),
            SCSS_INCLUDE(),
            AT_RULE(),
            RULESET(),
            EMPTY_STATEMENT())),
        b.token(LexicalGrammar.EOF)));
  }

  @Override
  public Tree ANY() {
    return b.<Tree>nonterminal().is(
      b.firstOf(
        // SCSS_SASS_SCRIPT_EXPRESSION
        VALUE_COMMA_SEPARATED_LIST(),
        IMPORTANT_FLAG(),
        SCSS_GLOBAL_FLAG(),
        SCSS_DEFAULT_FLAG(),
        SCSS_MAP(),
        PARENTHESIS_BLOCK(),
        BRACKET_BLOCK(),
        URI(),
        FUNCTION(),
        PSEUDO_SELECTOR(),
        PERCENTAGE(),
        DIMENSION(),
        NUMBER(),
        SCSS_OPERATOR(),
        SCSS_MULTILINE_STRING(),
        STRING(),
        SCSS_INTERPOLATED_IDENTIFIER(),
        HASH(),
        UNICODE_RANGE(),
        IDENTIFIER(),
        SCSS_VARIABLE(),
        // Non SCSS_SASS_SCRIPT_EXPRESSION
        BRACKET_BLOCK(),
        PSEUDO_SELECTOR(),
        b.token(LexicalGrammar.COLON),
        DELIMITER()));
  }

  @Override
  public Tree ANY_WITHOUT_COMMA_SEPARATED_LIST() {
    return b.<Tree>nonterminal().is(
      b.firstOf(
        // SCSS_SASS_SCRIPT_EXPRESSION
        IMPORTANT_FLAG(),
        SCSS_GLOBAL_FLAG(),
        SCSS_DEFAULT_FLAG(),
        SCSS_MAP(),
        PARENTHESIS_BLOCK(),
        BRACKET_BLOCK(),
        URI(),
        FUNCTION(),
        PSEUDO_SELECTOR(),
        PERCENTAGE(),
        DIMENSION(),
        NUMBER(),
        SCSS_OPERATOR(),
        SCSS_MULTILINE_STRING(),
        STRING(),
        SCSS_INTERPOLATED_IDENTIFIER(),
        HASH(),
        UNICODE_RANGE(),
        IDENTIFIER(),
        SCSS_VARIABLE(),
        // Non SCSS_SASS_SCRIPT_EXPRESSION
        BRACKET_BLOCK(),
        PSEUDO_SELECTOR(),
        b.token(LexicalGrammar.COLON),
        DELIMITER()));
  }

  public Tree ANY_SASS_SCRIPT_EXPRESSION() {
    return b.<Tree>nonterminal().is(
      b.firstOf(
        VALUE_COMMA_SEPARATED_LIST(),
        IMPORTANT_FLAG(),
        SCSS_GLOBAL_FLAG(),
        SCSS_DEFAULT_FLAG(),
        SCSS_MAP(),
        PARENTHESIS_BLOCK(),
        BRACKET_BLOCK(),
        URI(),
        FUNCTION(),
        PERCENTAGE(),
        DIMENSION(),
        NUMBER(),
        SCSS_OPERATOR(),
        SCSS_MULTILINE_STRING(),
        STRING(),
        SCSS_INTERPOLATED_IDENTIFIER(),
        HASH(),
        UNICODE_RANGE(),
        IDENTIFIER(),
        SCSS_VARIABLE()));
  }

  public Tree ANY_SASS_SCRIPT_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST() {
    return b.<Tree>nonterminal().is(
      b.firstOf(
        IMPORTANT_FLAG(),
        SCSS_GLOBAL_FLAG(),
        SCSS_DEFAULT_FLAG(),
        SCSS_MAP(),
        PARENTHESIS_BLOCK(),
        BRACKET_BLOCK(),
        URI(),
        FUNCTION(),
        PERCENTAGE(),
        DIMENSION(),
        NUMBER(),
        SCSS_OPERATOR(),
        SCSS_MULTILINE_STRING(),
        STRING(),
        SCSS_INTERPOLATED_IDENTIFIER(),
        HASH(),
        UNICODE_RANGE(),
        IDENTIFIER(),
        SCSS_VARIABLE()));
  }

  // -----------------------
  // Rules
  // -----------------------

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
            SCSS_FUNCTION(),
            SCSS_RETURN(),
            SCSS_IF_ELSE_IF_ELSE(),
            SCSS_FOR(),
            SCSS_EACH(),
            SCSS_WHILE(),
            SCSS_AT_ROOT(),
            SCSS_MIXIN(),
            SCSS_INCLUDE(),
            RULESET(),
            DECLARATION(),
            AT_RULE(),
            EMPTY_STATEMENT())),
        b.token(LexicalGrammar.CLOSE_CURLY_BRACE)));
  }

  // -----------------------
  // Properties
  // -----------------------

  @Override
  public DeclarationTree DECLARATION() {
    return b.<DeclarationTree>nonterminal(LexicalGrammar.DECLARATION).is(
      b.firstOf(
        SCSS_VARIABLE_DECLARATION(),
        SCSS_NESTED_PROPERTIES_DECLARATION(),
        CSS_DECLARATION()));
  }

  public ScssNestedPropertiesDeclarationTree SCSS_NESTED_PROPERTIES_DECLARATION() {
    return b.<ScssNestedPropertiesDeclarationTree>nonterminal(LexicalGrammar.SCSS_NESTED_PROPERTIES_DECLARATION).is(
      f.scssNestedPropertiesDeclaration(
        PROPERTY(),
        b.token(LexicalGrammar.COLON),
        STATEMENT_BLOCK()));
  }

  @Override
  public PropertyTree PROPERTY() {
    return b.<PropertyTree>nonterminal(LexicalGrammar.PROPERTY).is(
      f.property(
        b.firstOf(
          SCSS_INTERPOLATED_IDENTIFIER(),
          IDENTIFIER())));
  }

  // -----------------------
  // Selectors
  // -----------------------

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
            SCSS_PARENT_REFERENCING_SELECTOR(),
            SCSS_PARENT_SELECTOR(),
            SCSS_PLACEHOLDER_SELECTOR(),
            SIMPLE_CSS_SELECTOR()))));
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

  public ScssParentSelectorTree SCSS_PARENT_SELECTOR() {
    return b.<ScssParentSelectorTree>nonterminal(LexicalGrammar.SCSS_PARENT_SELECTOR).is(
      f.scssParentSelector(b.token(LexicalGrammar.SCSS_PARENT_SELECTOR_KEYWORD)));
  }

  public ScssParentReferencingSelectorTree SCSS_PARENT_REFERENCING_SELECTOR() {
    return b.<ScssParentReferencingSelectorTree>nonterminal(LexicalGrammar.SCSS_PARENT_REFERENCING_SELECTOR).is(
      f.scssParentReferencingSelector(
        SCSS_PARENT_SELECTOR(),
        b.firstOf(
          SCSS_INTERPOLATED_IDENTIFIER_NO_WS(),
          IDENTIFIER_NO_WS())));
  }

  public ScssPlaceholderSelectorTree SCSS_PLACEHOLDER_SELECTOR() {
    return b.<ScssPlaceholderSelectorTree>nonterminal(LexicalGrammar.SCSS_PLACEHOLDER_SELECTOR).is(
      f.scssPlaceholderSelector(
        b.token(LexicalGrammar.PERCENTAGE_SYMBOL),
        b.firstOf(
          SCSS_INTERPOLATED_IDENTIFIER_NO_WS(),
          IDENTIFIER_NO_WS())));
  }

  public SelectorCombinatorTree SCSS_PARENT_SELECTOR_COMBINATOR() {
    return b.<SelectorCombinatorTree>nonterminal(LexicalGrammar.SCSS_PARENT_SELECTOR_COMBINATOR).is(
      f.scssParentSelectorCombinator(
        b.firstOf(
          b.token(LexicalGrammar.DEEP_COMBINATOR),
          b.token(LexicalGrammar.DEEP_ALIAS_COMBINATOR),
          b.token(LexicalGrammar.CHILD_COMBINATOR),
          b.token(LexicalGrammar.NEXT_SIBLING_COMBINATOR),
          b.token(LexicalGrammar.FOLLOWING_SIBLING_COMBINATOR))));
  }

  // -----------------------
  // Mixins
  // -----------------------

  public ScssMixinTree SCSS_MIXIN() {
    return b.<ScssMixinTree>nonterminal(LexicalGrammar.SCSS_MIXIN).is(
      f.scssMixin(
        SCSS_MIXIN_DIRECTIVE(),
        IDENTIFIER(),
        b.optional(SCSS_PARAMETERS()),
        STATEMENT_BLOCK()));
  }

  public ScssIncludeTree SCSS_INCLUDE() {
    return b.<ScssIncludeTree>nonterminal(LexicalGrammar.SCSS_INCLUDE).is(
      f.scssInclude(
        SCSS_INCLUDE_DIRECTIVE(),
        IDENTIFIER(),
        b.optional(SCSS_CALL_PARAMETERS()),
        b.optional(STATEMENT_BLOCK()),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssDirectiveTree SCSS_MIXIN_DIRECTIVE() {
    return b.<ScssDirectiveTree>nonterminal(LexicalGrammar.SCSS_MIXIN_DIRECTIVE).is(
      f.scssMixinDirective(
        b.token(LexicalGrammar.AT_SYMBOL),
        b.token(LexicalGrammar.SCSS_MIXIN_DIRECTIVE_LITERAL)));
  }

  public ScssDirectiveTree SCSS_INCLUDE_DIRECTIVE() {
    return b.<ScssDirectiveTree>nonterminal(LexicalGrammar.SCSS_INCLUDE_DIRECTIVE).is(
      f.scssIncludeDirective(
        b.token(LexicalGrammar.AT_SYMBOL),
        b.token(LexicalGrammar.SCSS_INCLUDE_DIRECTIVE_LITERAL)));
  }

  public ScssParametersTree SCSS_PARAMETERS() {
    return b.<ScssParametersTree>nonterminal(LexicalGrammar.SCSS_PARAMETERS).is(
      f.scssParameters(
        b.token(LexicalGrammar.OPEN_PARENTHESIS),
        b.optional(SCSS_PARAMETER_LIST()),
        b.token(LexicalGrammar.CLOSE_PARENTHESIS)));
  }

  public ScssParametersTree SCSS_CALL_PARAMETERS() {
    return b.<ScssParametersTree>nonterminal(LexicalGrammar.SCSS_CALL_PARAMETERS).is(
      f.scssCallParameters(
        b.token(LexicalGrammar.OPEN_PARENTHESIS),
        b.optional(SCSS_CALL_PARAMETER_LIST()),
        b.token(LexicalGrammar.CLOSE_PARENTHESIS)));
  }

  public SeparatedList<ScssParameterTree, DelimiterTree> SCSS_PARAMETER_LIST() {
    return b.<SeparatedList<ScssParameterTree, DelimiterTree>>nonterminal().is(
      f.scssParameterList(
        SCSS_PARAMETER(),
        b.zeroOrMore(
          f.newTuple5(
            COMMA_DELIMITER(),
            SCSS_PARAMETER()))));
  }

  public SeparatedList<ScssParameterTree, DelimiterTree> SCSS_CALL_PARAMETER_LIST() {
    return b.<SeparatedList<ScssParameterTree, DelimiterTree>>nonterminal().is(
      f.scssCallParameterList(
        SCSS_CALL_PARAMETER(),
        b.zeroOrMore(
          f.newTuple6(
            COMMA_DELIMITER(),
            SCSS_CALL_PARAMETER()))));
  }

  public ScssParameterTree SCSS_PARAMETER() {
    return b.<ScssParameterTree>nonterminal(LexicalGrammar.SCSS_PARAMETER).is(
      f.scssParameter(
        b.firstOf(
          SCSS_VARIABLE_DECLARATION_AS_PARAMETER(),
          SCSS_VARIABLE_ARGUMENT(),
          SCSS_VARIABLE())));
  }

  public ScssParameterTree SCSS_CALL_PARAMETER() {
    return b.<ScssParameterTree>nonterminal(LexicalGrammar.SCSS_CALL_PARAMETER).is(
      f.scssCallParameter(
        b.firstOf(
          SCSS_VARIABLE_DECLARATION_AS_PARAMETER(),
          SCSS_VARIABLE_ARGUMENT(),
          SCSS_SASS_SCRIPT_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST())));
  }

  // -----------------------
  // Ruleset Directives
  // -----------------------

  public ScssExtendTree SCSS_EXTEND() {
    return b.<ScssExtendTree>nonterminal(LexicalGrammar.SCSS_EXTEND).is(
      f.scssExtend(
        SCSS_EXTEND_DIRECTIVE(),
        COMPOUND_SELECTOR(),
        b.optional(SCSS_OPTIONAL_FLAG()),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssDirectiveTree SCSS_EXTEND_DIRECTIVE() {
    return b.<ScssDirectiveTree>nonterminal(LexicalGrammar.SCSS_EXTEND_DIRECTIVE).is(
      f.scssExtendDirective(
        b.token(LexicalGrammar.AT_SYMBOL),
        b.token(LexicalGrammar.SCSS_EXTEND_DIRECTIVE_LITERAL)));
  }

  public ScssOptionalFlagTree SCSS_OPTIONAL_FLAG() {
    return b.<ScssOptionalFlagTree>nonterminal(LexicalGrammar.SCSS_OPTIONAL_FLAG).is(
      f.scssOptionalFlag(
        b.token(LexicalGrammar.SCSS_OPTIONAL_KEYWORD)));
  }

  public ScssContentTree SCSS_CONTENT() {
    return b.<ScssContentTree>nonterminal(LexicalGrammar.SCSS_CONTENT).is(
      f.scssContent(
        SCSS_CONTENT_DIRECTIVE(),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssDirectiveTree SCSS_CONTENT_DIRECTIVE() {
    return b.<ScssDirectiveTree>nonterminal(LexicalGrammar.SCSS_CONTENT_DIRECTIVE).is(
      f.scssContentDirective(
        b.token(LexicalGrammar.AT_SYMBOL),
        b.token(LexicalGrammar.SCSS_CONTENT_DIRECTIVE_LITERAL)));
  }

  public ScssDirectiveTree SCSS_AT_ROOT_DIRECTIVE() {
    return b.<ScssDirectiveTree>nonterminal(LexicalGrammar.SCSS_AT_ROOT_DIRECTIVE).is(
      f.scssAtRootDirective(
        b.token(LexicalGrammar.AT_SYMBOL),
        b.token(LexicalGrammar.SCSS_AT_ROOT_DIRECTIVE_LITERAL)));
  }

  public ScssAtRootTree SCSS_AT_ROOT() {
    return b.<ScssAtRootTree>nonterminal(LexicalGrammar.SCSS_AT_ROOT).is(
      f.scssAtRoot(
        SCSS_AT_ROOT_DIRECTIVE(),
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

  // -----------------------
  // Logging Directives
  // -----------------------

  public ScssDebugTree SCSS_DEBUG() {
    return b.<ScssDebugTree>nonterminal(LexicalGrammar.SCSS_DEBUG).is(
      f.scssDebug(
        SCSS_DEBUG_DIRECTIVE(),
        SCSS_SASS_SCRIPT_EXPRESSION(),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssWarnTree SCSS_WARN() {
    return b.<ScssWarnTree>nonterminal(LexicalGrammar.SCSS_WARN).is(
      f.scssWarn(
        SCSS_WARN_DIRECTIVE(),
        SCSS_SASS_SCRIPT_EXPRESSION(),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssErrorTree SCSS_ERROR() {
    return b.<ScssErrorTree>nonterminal(LexicalGrammar.SCSS_ERROR).is(
      f.scssError(
        SCSS_ERROR_DIRECTIVE(),
        SCSS_SASS_SCRIPT_EXPRESSION(),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssDirectiveTree SCSS_DEBUG_DIRECTIVE() {
    return b.<ScssDirectiveTree>nonterminal(LexicalGrammar.SCSS_DEBUG_DIRECTIVE).is(
      f.scssDebugDirective(
        b.token(LexicalGrammar.AT_SYMBOL),
        b.token(LexicalGrammar.SCSS_DEBUG_DIRECTIVE_LITERAL)));
  }

  public ScssDirectiveTree SCSS_WARN_DIRECTIVE() {
    return b.<ScssDirectiveTree>nonterminal(LexicalGrammar.SCSS_WARN_DIRECTIVE).is(
      f.scssWarnDirective(
        b.token(LexicalGrammar.AT_SYMBOL),
        b.token(LexicalGrammar.SCSS_WARN_DIRECTIVE_LITERAL)));
  }

  public ScssDirectiveTree SCSS_ERROR_DIRECTIVE() {
    return b.<ScssDirectiveTree>nonterminal(LexicalGrammar.SCSS_ERROR_DIRECTIVE).is(
      f.scssErrorDirective(
        b.token(LexicalGrammar.AT_SYMBOL),
        b.token(LexicalGrammar.SCSS_ERROR_DIRECTIVE_LITERAL)));
  }

  // -----------------------
  // Function Directives
  // -----------------------

  public ScssFunctionTree SCSS_FUNCTION() {
    return b.<ScssFunctionTree>nonterminal(LexicalGrammar.SCSS_FUNCTION).is(
      f.scssFunction(
        SCSS_FUNCTION_DIRECTIVE(),
        IDENTIFIER(),
        b.optional(SCSS_PARAMETERS()),
        STATEMENT_BLOCK()));
  }

  public ScssDirectiveTree SCSS_FUNCTION_DIRECTIVE() {
    return b.<ScssDirectiveTree>nonterminal(LexicalGrammar.SCSS_FUNCTION_DIRECTIVE).is(
      f.scssFunctionDirective(
        b.token(LexicalGrammar.AT_SYMBOL),
        b.token(LexicalGrammar.SCSS_FUNCTION_DIRECTIVE_LITERAL)));
  }

  public ScssReturnTree SCSS_RETURN() {
    return b.<ScssReturnTree>nonterminal(LexicalGrammar.SCSS_RETURN).is(
      f.scssReturn(
        SCSS_RETURN_DIRECTIVE(),
        SCSS_SASS_SCRIPT_EXPRESSION(),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssDirectiveTree SCSS_RETURN_DIRECTIVE() {
    return b.<ScssDirectiveTree>nonterminal(LexicalGrammar.SCSS_RETURN_DIRECTIVE).is(
      f.scssReturnDirective(
        b.token(LexicalGrammar.AT_SYMBOL),
        b.token(LexicalGrammar.SCSS_RETURN_DIRECTIVE_LITERAL)));
  }

  // -----------------------
  // Control Flow Directives
  // -----------------------

  public ScssIfElseIfElseTree SCSS_IF_ELSE_IF_ELSE() {
    return b.<ScssIfElseIfElseTree>nonterminal(LexicalGrammar.SCSS_IF_ELSE_IF_ELSE).is(
      f.scssIfElseIfElse(
        SCSS_IF(),
        b.zeroOrMore(SCSS_ELSE_IF()),
        b.optional(SCSS_ELSE())));
  }

  public ScssIfTree SCSS_IF() {
    return b.<ScssIfTree>nonterminal(LexicalGrammar.SCSS_IF).is(
      f.scssIf(
        SCSS_IF_DIRECTIVE(),
        SCSS_CONDITION(),
        STATEMENT_BLOCK()));
  }

  public ScssElseIfTree SCSS_ELSE_IF() {
    return b.<ScssElseIfTree>nonterminal(LexicalGrammar.SCSS_ELSE_IF).is(
      f.scssElseIf(
        SCSS_ELSE_IF_DIRECTIVE(),
        SCSS_CONDITION(),
        STATEMENT_BLOCK()));
  }

  public ScssElseTree SCSS_ELSE() {
    return b.<ScssElseTree>nonterminal(LexicalGrammar.SCSS_ELSE).is(
      f.scssElse(
        SCSS_ELSE_DIRECTIVE(),
        STATEMENT_BLOCK()));
  }

  public ScssWhileTree SCSS_WHILE() {
    return b.<ScssWhileTree>nonterminal(LexicalGrammar.SCSS_WHILE).is(
      f.scssWhile(
        SCSS_WHILE_DIRECTIVE(),
        SCSS_CONDITION(),
        STATEMENT_BLOCK()));
  }

  public ScssForTree SCSS_FOR() {
    return b.<ScssForTree>nonterminal(LexicalGrammar.SCSS_FOR).is(
      f.scssFor(
        SCSS_FOR_DIRECTIVE(),
        SCSS_CONDITION(),
        STATEMENT_BLOCK()));
  }

  public ScssEachTree SCSS_EACH() {
    return b.<ScssEachTree>nonterminal(LexicalGrammar.SCSS_EACH).is(
      f.scssEach(
        SCSS_EACH_DIRECTIVE(),
        SCSS_CONDITION(),
        STATEMENT_BLOCK()));
  }

  public ScssConditionTree SCSS_CONDITION() {
    return b.<ScssConditionTree>nonterminal(LexicalGrammar.SCSS_CONDITION).is(
      f.scssCondition(SCSS_SASS_SCRIPT_EXPRESSION()));
  }

  public ScssDirectiveTree SCSS_IF_DIRECTIVE() {
    return b.<ScssDirectiveTree>nonterminal(LexicalGrammar.SCSS_IF_DIRECTIVE).is(
      f.scssIfDirective(
        b.token(LexicalGrammar.AT_SYMBOL),
        b.token(LexicalGrammar.SCSS_IF_DIRECTIVE_LITERAL)));
  }

  public ScssDirectiveTree SCSS_ELSE_IF_DIRECTIVE() {
    return b.<ScssDirectiveTree>nonterminal(LexicalGrammar.SCSS_ELSE_IF_DIRECTIVE).is(
      f.scssElseIfDirective(
        b.token(LexicalGrammar.AT_SYMBOL),
        b.token(LexicalGrammar.SCSS_ELSE_IF_DIRECTIVE_LITERAL)));
  }

  public ScssDirectiveTree SCSS_ELSE_DIRECTIVE() {
    return b.<ScssDirectiveTree>nonterminal(LexicalGrammar.SCSS_ELSE_DIRECTIVE).is(
      f.scssElseDirective(
        b.token(LexicalGrammar.AT_SYMBOL),
        b.token(LexicalGrammar.SCSS_ELSE_DIRECTIVE_LITERAL)));
  }

  public ScssDirectiveTree SCSS_WHILE_DIRECTIVE() {
    return b.<ScssDirectiveTree>nonterminal(LexicalGrammar.SCSS_WHILE_DIRECTIVE).is(
      f.scssWhileDirective(
        b.token(LexicalGrammar.AT_SYMBOL),
        b.token(LexicalGrammar.SCSS_WHILE_DIRECTIVE_LITERAL)));
  }

  public ScssDirectiveTree SCSS_FOR_DIRECTIVE() {
    return b.<ScssDirectiveTree>nonterminal(LexicalGrammar.SCSS_FOR_DIRECTIVE).is(
      f.scssForDirective(
        b.token(LexicalGrammar.AT_SYMBOL),
        b.token(LexicalGrammar.SCSS_FOR_DIRECTIVE_LITERAL)));
  }

  public ScssDirectiveTree SCSS_EACH_DIRECTIVE() {
    return b.<ScssDirectiveTree>nonterminal(LexicalGrammar.SCSS_EACH_DIRECTIVE).is(
      f.scssEachDirective(
        b.token(LexicalGrammar.AT_SYMBOL),
        b.token(LexicalGrammar.SCSS_EACH_DIRECTIVE_LITERAL)));
  }

  // -----------------------
  // Variables
  // -----------------------

  public ScssVariableDeclarationTree SCSS_VARIABLE_DECLARATION() {
    return b.<ScssVariableDeclarationTree>nonterminal(LexicalGrammar.SCSS_VARIABLE_DECLARATION).is(
      f.scssVariableDeclaration(
        SCSS_VARIABLE(),
        b.token(LexicalGrammar.COLON),
        SCSS_SASS_SCRIPT_EXPRESSION(),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public ScssVariableDeclarationTree SCSS_VARIABLE_DECLARATION_AS_PARAMETER() {
    return b.<ScssVariableDeclarationTree>nonterminal(LexicalGrammar.SCSS_VARIABLE_DECLARATION_AS_PARAMETER).is(
      f.scssVariableDeclarationAsParameter(
        SCSS_VARIABLE(),
        b.token(LexicalGrammar.COLON),
        SCSS_SASS_SCRIPT_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST()));
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

  // -----------------------
  // Sass Script
  // -----------------------

  public ValueTree SCSS_SASS_SCRIPT_EXPRESSION() {
    return b.<ValueTree>nonterminal(LexicalGrammar.SCSS_SASS_SCRIPT_EXPRESSION).is(
      f.simpleValueSassScriptExpression(
        b.oneOrMore(ANY_SASS_SCRIPT_EXPRESSION())));
  }

  public ValueTree SCSS_SASS_SCRIPT_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST() {
    return b.<ValueTree>nonterminal(LexicalGrammar.SCSS_SASS_SCRIPT_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST).is(
      f.simpleValueSassScriptExpressionWithoutCommaSeparatedList(
        b.oneOrMore(ANY_SASS_SCRIPT_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST())));
  }

  @Override
  public ValueCommaSeparatedListTree VALUE_COMMA_SEPARATED_LIST() {
    return b.<ValueCommaSeparatedListTree>nonterminal(LexicalGrammar.VALUE_COMMA_SEPARATED_LIST).is(
      b.firstOf(
        f.valueCommaSeparatedList(
          SCSS_SASS_SCRIPT_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST(),
          b.oneOrMore(
            f.newTuple8(
              COMMA_DELIMITER(),
              SCSS_SASS_SCRIPT_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST())),
          b.optional(COMMA_DELIMITER())),
        f.valueCommaSeparatedListOneSingleValue(
          SCSS_SASS_SCRIPT_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST(),
          COMMA_DELIMITER())));
  }

  public ScssMapTree SCSS_MAP() {
    return b.<ScssMapTree>nonterminal(LexicalGrammar.SCSS_MAP).is(
      f.scssMap(
        b.token(LexicalGrammar.OPEN_PARENTHESIS),
        SCSS_MAP_ENTRY_LIST(),
        b.token(LexicalGrammar.CLOSE_PARENTHESIS)));
  }

  public SeparatedList<ScssMapEntryTree, DelimiterTree> SCSS_MAP_ENTRY_LIST() {
    return b.<SeparatedList<ScssMapEntryTree, DelimiterTree>>nonterminal().is(
      f.scssMapEntryList(
        SCSS_MAP_ENTRY(),
        b.zeroOrMore(
          f.newTuple7(
            COMMA_DELIMITER(),
            SCSS_MAP_ENTRY())),
        b.optional(COMMA_DELIMITER())));
  }

  public ScssMapEntryTree SCSS_MAP_ENTRY() {
    return b.<ScssMapEntryTree>nonterminal(LexicalGrammar.SCSS_MAP_ENTRY).is(
      f.scssMapEntry(
        SCSS_SASS_SCRIPT_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST(),
        b.token(LexicalGrammar.COLON),
        SCSS_SASS_SCRIPT_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST()));
  }

  public ScssOperatorTree SCSS_OPERATOR() {
    return b.<ScssOperatorTree>nonterminal(LexicalGrammar.SCSS_OPERATOR).is(
      f.scssOperator(b.token(LexicalGrammar.SCSS_OPERATOR_LITERAL)));
  }

  public IdentifierTree SCSS_INTERPOLATED_IDENTIFIER() {
    return b.<IdentifierTree>nonterminal(LexicalGrammar.SCSS_INTERPOLATED_IDENTIFIER).is(
      f.scssInterpolatedIdentifier(b.token(LexicalGrammar.SCSS_IDENT_INTERPOLATED_IDENTIFIER)));
  }

  public IdentifierTree SCSS_INTERPOLATED_IDENTIFIER_NO_WS() {
    return b.<IdentifierTree>nonterminal(LexicalGrammar.SCSS_INTERPOLATED_IDENTIFIER_NO_WS).is(
      f.scssInterpolatedIdentifierNoWs(b.token(LexicalGrammar.SCSS_IDENT_INTERPOLATED_IDENTIFIER_NO_WS)));
  }

  public ScssMultilineStringTree SCSS_MULTILINE_STRING() {
    return b.<ScssMultilineStringTree>nonterminal(LexicalGrammar.SCSS_MULTILINE_STRING).is(
      f.scssMultilineString(b.token(LexicalGrammar.SCSS_MULTILINE_STRING_LITERAL)));
  }

}
