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
package org.sonar.css.parser.less;

import com.sonar.sslr.api.typed.GrammarBuilder;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.css.parser.TreeFactory;
import org.sonar.css.parser.css.CssGrammar;
import org.sonar.css.tree.impl.SeparatedList;
import org.sonar.css.tree.impl.css.InternalSyntaxToken;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.tree.less.*;

/**
 * Less grammar based on:
 * - http://lesscss.org/features/
 */
public class LessGrammar extends CssGrammar {

  public LessGrammar(GrammarBuilder<InternalSyntaxToken> b, TreeFactory f) {
    super(b, f);
  }

  @Override
  public StyleSheetTree STYLESHEET() {
    return b.<StyleSheetTree>nonterminal(LexicalGrammar.STYLESHEET).is(
      f.stylesheet(
        b.optional(b.token(LexicalGrammar.BOM)),
        b.zeroOrMore(
          b.firstOf(
            LESS_VARIABLE_DECLARATION(),
            AT_RULE(),
            RULESET(),
            LESS_MIXIN_CALL(),
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

  public StatementBlockTree STATEMENT_BLOCK() {
    return b.<StatementBlockTree>nonterminal(LexicalGrammar.STATEMENT_BLOCK).is(
      f.statementBlock(
        b.token(LexicalGrammar.OPEN_CURLY_BRACE),
        b.zeroOrMore(
          b.firstOf(
            LESS_VARIABLE_DECLARATION(),
            AT_RULE(),
            RULESET(),
            CSS_DECLARATION(),
            LESS_MIXIN_CALL(),
            EMPTY_STATEMENT())),
        b.token(LexicalGrammar.CLOSE_CURLY_BRACE)));
  }

  @Override
  public Tree ANY() {
    return b.<Tree>nonterminal().is(
      b.firstOf(
        VALUE_COMMA_SEPARATED_LIST(),
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
        LESS_OPERATOR(),
        IDENTIFIER(),
        IMPORTANT_FLAG(),
        LESS_ESCAPING(),
        LESS_INTERPOLATED_IDENTIFIER(),
        LESS_VARIABLE(),
        b.token(LexicalGrammar.COLON),
        DELIMITER()));
  }

  @Override
  public Tree ANY_WITHOUT_COMMA_SEPARATED_LIST() {
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
        LESS_OPERATOR(),
        IDENTIFIER(),
        IMPORTANT_FLAG(),
        LESS_ESCAPING(),
        LESS_INTERPOLATED_IDENTIFIER(),
        LESS_VARIABLE(),
        b.token(LexicalGrammar.COLON),
        DELIMITER()));
  }

  @Override
  public PropertyTree PROPERTY() {
    return b.<PropertyTree>nonterminal(LexicalGrammar.PROPERTY).is(
      f.property(
        b.firstOf(
          LESS_INTERPOLATED_IDENTIFIER(),
          IDENTIFIER()),
        b.optional(b.token(LexicalGrammar.LESS_MERGE))));
  }

  @Override
  public SelectorTree SELECTOR() {
    return b.<SelectorTree>nonterminal(LexicalGrammar.SELECTOR).is(
      f.lessSelector(
        b.optional(LESS_PARENT_SELECTOR_COMBINATOR()),
        COMPOUND_SELECTOR_COMBINATION_LIST(),
        b.optional(LESS_EXTEND()),
        b.optional(LESS_MIXIN_PARAMETERS()),
        b.optional(LESS_MIXIN_GUARD())));
  }

  public SelectorCombinatorTree LESS_PARENT_SELECTOR_COMBINATOR() {
    return b.<SelectorCombinatorTree>nonterminal(LexicalGrammar.LESS_PARENT_SELECTOR_COMBINATOR).is(
      f.lessParentSelectorCombinator(
        b.firstOf(
          b.token(LexicalGrammar.DEEP_COMBINATOR),
          b.token(LexicalGrammar.DEEP_ALIAS_COMBINATOR),
          b.token(LexicalGrammar.CHILD_COMBINATOR),
          b.token(LexicalGrammar.NEXT_SIBLING_COMBINATOR),
          b.token(LexicalGrammar.FOLLOWING_SIBLING_COMBINATOR))));
  }

  @Override
  public CompoundSelectorTree COMPOUND_SELECTOR() {
    return b.<CompoundSelectorTree>nonterminal(LexicalGrammar.COMPOUND_SELECTOR).is(
      f.compoundSelector(
        b.oneOrMore(
          b.firstOf(
            LESS_PARENT_REFERENCING_SELECTOR(),
            LESS_PARENT_SELECTOR(),
            SIMPLE_CSS_SELECTOR()))));
  }

  @Override
  public ClassSelectorTree CLASS_SELECTOR() {
    return b.<ClassSelectorTree>nonterminal(LexicalGrammar.CLASS_SELECTOR).is(
      f.classSelector(
        b.token(LexicalGrammar.DOT),
        b.firstOf(
          LESS_INTERPOLATED_IDENTIFIER_NO_WS(),
          IDENTIFIER_NO_WS())));
  }

  @Override
  public TypeSelectorTree TYPE_SELECTOR() {
    return b.<TypeSelectorTree>nonterminal(LexicalGrammar.TYPE_SELECTOR).is(
      f.typeSelector(
        b.optional(NAMESPACE()),
        b.firstOf(
          LESS_INTERPOLATED_IDENTIFIER_NO_WS(),
          LESS_IDENTIFIER_NO_WS_NOR_WHEN())));
  }

  @Override
  public IdSelectorTree ID_SELECTOR() {
    return b.<IdSelectorTree>nonterminal(LexicalGrammar.ID_SELECTOR).is(
      f.idSelector(
        b.token(LexicalGrammar.HASH_SYMBOL_NO_WS),
        b.firstOf(
          LESS_INTERPOLATED_IDENTIFIER_NO_WS(),
          IDENTIFIER_NO_WS())));
  }

  @Override
  public AttributeSelectorTree ATTRIBUTE_SELECTOR() {
    return b.<AttributeSelectorTree>nonterminal(LexicalGrammar.ATTRIBUTE_SELECTOR).is(
      b.firstOf(
        f.attributeSelector(
          b.token(LexicalGrammar.OPEN_BRACKET_NO_WS),
          b.firstOf(
            LESS_INTERPOLATED_IDENTIFIER(),
            IDENTIFIER()),
          b.optional(ATTRIBUTE_MATCHER_EXPRESSION()),
          b.token(LexicalGrammar.CLOSE_BRACKET)),
        f.attributeSelector(
          b.token(LexicalGrammar.OPEN_BRACKET_NO_WS),
          b.token(LexicalGrammar.SPACING),
          NAMESPACE(),
          b.firstOf(
            LESS_INTERPOLATED_IDENTIFIER_NO_WS(),
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
            LESS_INTERPOLATED_IDENTIFIER(),
            IDENTIFIER())),
        b.optional(CASE_INSENSITIVE_FLAG())));
  }

  @Override
  public PseudoIdentifierTree PSEUDO_IDENTIFIER() {
    return b.<PseudoIdentifierTree>nonterminal(LexicalGrammar.PSEUDO_IDENTIFIER).is(
      f.pseudoIdentifier(
        b.token(LexicalGrammar.PSEUDO_PREFIX),
        b.firstOf(
          LESS_INTERPOLATED_IDENTIFIER_NO_WS(),
          IDENTIFIER_NO_WS())));
  }

  public LessParentSelectorTree LESS_PARENT_SELECTOR() {
    return b.<LessParentSelectorTree>nonterminal(LexicalGrammar.LESS_PARENT_SELECTOR).is(
      f.lessParentSelector(b.token(LexicalGrammar.LESS_PARENT_SELECTOR_KEYWORD)));
  }

  public LessParentReferencingSelectorTree LESS_PARENT_REFERENCING_SELECTOR() {
    return b.<LessParentReferencingSelectorTree>nonterminal(LexicalGrammar.LESS_PARENT_REFERENCING_SELECTOR).is(
      f.lessParentReferencingSelector(
        LESS_PARENT_SELECTOR(),
        b.firstOf(
          LESS_INTERPOLATED_IDENTIFIER_NO_WS(),
          IDENTIFIER_NO_WS())));
  }

  public LessVariableDeclarationTree LESS_VARIABLE_DECLARATION() {
    return b.<LessVariableDeclarationTree>nonterminal(LexicalGrammar.LESS_VARIABLE_DECLARATION).is(
      f.lessVariableDeclaration(
        LESS_VARIABLE(),
        b.token(LexicalGrammar.COLON),
        DECLARATION_VALUE(),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public LessVariableTree LESS_VARIABLE() {
    return b.<LessVariableTree>nonterminal(LexicalGrammar.LESS_VARIABLE).is(
      f.lessVariable(
        b.token(LexicalGrammar.LESS_VARIABLE_PREFIX),
        IDENTIFIER_NO_WS()));
  }

  public LessExtendTree LESS_EXTEND() {
    return b.<LessExtendTree>nonterminal(LexicalGrammar.LESS_EXTEND).is(
      f.lessExtend(
        b.token(LexicalGrammar.LESS_EXTEND_PREFIX),
        b.token(LexicalGrammar.OPEN_PARENTHESIS_NO_WS),
        b.oneOrMore(SIMPLE_VALUE()),
        b.token(LexicalGrammar.CLOSE_PARENTHESIS)));
  }

  public LessMixinCallTree LESS_MIXIN_CALL() {
    return b.<LessMixinCallTree>nonterminal(LexicalGrammar.LESS_MIXIN_CALL).is(
      f.lessMixinCall(
        b.optional(LESS_PARENT_SELECTOR_COMBINATOR()),
        b.optional(b.token(LexicalGrammar.SPACING)),
        SELECTOR(),
        b.optional(IMPORTANT_FLAG()),
        b.optional(b.token(LexicalGrammar.SEMICOLON))));
  }

  public LessMixinGuardTree LESS_MIXIN_GUARD() {
    return b.<LessMixinGuardTree>nonterminal(LexicalGrammar.LESS_MIXIN_GUARD).is(
      f.lessMixinGuard(
        b.token(LexicalGrammar.LESS_MIXIN_GUARD_WHEN),
        LESS_MIXIN_GUARD_CONDITION_LIST()));
  }

  public SeparatedList<LessMixinGuardConditionTree, SyntaxToken> LESS_MIXIN_GUARD_CONDITION_LIST() {
    return b.<SeparatedList<LessMixinGuardConditionTree, SyntaxToken>>nonterminal().is(
      f.lessMixinGuardConditionList(
        LESS_MIXIN_GUARD_CONDITION(),
        b.zeroOrMore(
          f.newTuple2(
            b.firstOf(
              b.token(LexicalGrammar.LESS_MIXIN_GUARD_AND),
              b.token(LexicalGrammar.LESS_MIXIN_GUARD_OR)),
            LESS_MIXIN_GUARD_CONDITION()))));
  }

  public LessMixinGuardConditionTree LESS_MIXIN_GUARD_CONDITION() {
    return b.<LessMixinGuardConditionTree>nonterminal(LexicalGrammar.LESS_MIXIN_GUARD_CONDITION).is(
      f.lessMixinGuardCondition(
        b.optional(b.token(LexicalGrammar.LESS_MIXIN_GUARD_NOT)),
        PARENTHESIS_BLOCK()));
  }

  public LessMixinParametersTree LESS_MIXIN_PARAMETERS() {
    return b.<LessMixinParametersTree>nonterminal(LexicalGrammar.LESS_MIXIN_PARAMETERS).is(
      f.lessMixinParameters(
        b.token(LexicalGrammar.OPEN_PARENTHESIS),
        b.optional(LESS_MIXIN_PARAMETER_LIST()),
        b.token(LexicalGrammar.CLOSE_PARENTHESIS)));
  }

  public SeparatedList<LessMixinParameterTree, DelimiterTree> LESS_MIXIN_PARAMETER_LIST() {
    return b.<SeparatedList<LessMixinParameterTree, DelimiterTree>>nonterminal().is(
      f.lessMixinParameterList(
        LESS_MIXIN_PARAMETER(),
        b.zeroOrMore(
          f.newTuple3(
            b.firstOf(
              COMMA_DELIMITER(),
              SEMICOLON_DELIMITER()),
            LESS_MIXIN_PARAMETER())),
        b.optional(
          b.firstOf(
            COMMA_DELIMITER(),
            SEMICOLON_DELIMITER()))));
  }

  public LessMixinParameterTree LESS_MIXIN_PARAMETER() {
    return b.<LessMixinParameterTree>nonterminal(LexicalGrammar.LESS_MIXIN_PARAMETER).is(
      f.lessMixinParameter(
        b.firstOf(
          LESS_VARIABLE_DECLARATION_AS_PARAMETER(),
          LESS_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST())));
  }

  public LessVariableDeclarationTree LESS_VARIABLE_DECLARATION_AS_PARAMETER() {
    return b.<LessVariableDeclarationTree>nonterminal(LexicalGrammar.LESS_VARIABLE_DECLARATION_AS_PARAMETER).is(
      f.lessVariableDeclarationAsParameter(
        LESS_VARIABLE(),
        b.token(LexicalGrammar.COLON),
        LESS_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST()));
  }

  public ValueTree LESS_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST() {
    return b.<ValueTree>nonterminal(LexicalGrammar.LESS_EXPRESSION_WITHOUT_COMMA_SEPARATED_LIST).is(
      f.simpleValueLessExpressionWithoutCommaSeparatedList(
        b.oneOrMore(ANY_WITHOUT_COMMA_SEPARATED_LIST())));
  }

  public IdentifierTree LESS_INTERPOLATED_IDENTIFIER() {
    return b.<IdentifierTree>nonterminal(LexicalGrammar.LESS_INTERPOLATED_IDENTIFIER).is(
      f.lessInterpolatedIdentifier(b.token(LexicalGrammar.LESS_IDENT_INTERPOLATED_IDENTIFIER)));
  }

  public IdentifierTree LESS_INTERPOLATED_IDENTIFIER_NO_WS() {
    return b.<IdentifierTree>nonterminal(LexicalGrammar.LESS_INTERPOLATED_IDENTIFIER_NO_WS).is(
      f.lessInterpolatedIdentifierNoWs(b.token(LexicalGrammar.LESS_IDENT_INTERPOLATED_IDENTIFIER_NO_WS)));
  }

  public IdentifierTree LESS_IDENTIFIER_NO_WS_NOR_WHEN() {
    return b.<IdentifierTree>nonterminal(LexicalGrammar.LESS_IDENTIFIER_NO_WS_NOR_WHEN).is(
      f.lessIdentifierNoWsNorWhen(b.token(LexicalGrammar.LESS_IDENT_IDENTIFIER_NO_WS_NOR_WHEN)));
  }

  public LessEscapingTree LESS_ESCAPING() {
    return b.<LessEscapingTree>nonterminal(LexicalGrammar.LESS_ESCAPING).is(
      f.lessEscaping(
        b.token(LexicalGrammar.LESS_ESCAPING_SYMBOL),
        STRING_NO_WS()));
  }

  public LessOperatorTree LESS_OPERATOR() {
    return b.<LessOperatorTree>nonterminal(LexicalGrammar.LESS_OPERATOR).is(
      f.lessOperator(b.token(LexicalGrammar.LESS_OPERATOR_LITERAL)));
  }

}
