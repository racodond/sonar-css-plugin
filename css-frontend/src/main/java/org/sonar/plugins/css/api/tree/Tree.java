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
package org.sonar.plugins.css.api.tree;

import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.tree.embedded.CssInStyleTagTree;
import org.sonar.plugins.css.api.tree.embedded.FileWithEmbeddedCssTree;
import org.sonar.plugins.css.api.tree.less.*;
import org.sonar.plugins.css.api.tree.scss.*;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;
import org.sonar.sslr.grammar.GrammarRuleKey;

import javax.annotation.Nullable;
import java.util.Iterator;

public interface Tree {

  boolean is(Kind... kind);

  void accept(DoubleDispatchVisitor visitor);

  Iterator<Tree> childrenIterator();

  String treeValue();

  int getLine();

  @Nullable
  Tree parent();

  void setParent(Tree parent);

  boolean hasAncestor(Class<? extends Tree> clazz);

  boolean isLeaf();

  enum Kind implements GrammarRuleKey {

    // CSS
    STYLESHEET(StyleSheetTree.class),
    AT_RULE(AtRuleTree.class),
    RULESET(RulesetTree.class),
    EMPTY_STATEMENT(EmptyStatementTree.class),
    STATEMENT_BLOCK(StatementBlockTree.class),
    PARENTHESIS_BLOCK(ParenthesisBlockTree.class),
    BRACKET_BLOCK(BracketBlockTree.class),
    PROPERTY_DECLARATION(PropertyDeclarationTree.class),
    VARIABLE_DECLARATION(VariableDeclarationTree.class),
    PROPERTY(PropertyTree.class),
    FUNCTION(FunctionTree.class),
    PARAMETERS(ParametersTree.class),
    VALUE(ValueTree.class),
    SELECTORS(SelectorsTree.class),
    SELECTOR(SelectorTree.class),
    SELECTOR_COMBINATOR(SelectorCombinatorTree.class),
    COMPOUND_SELECTOR(CompoundSelectorTree.class),
    TYPE_SELECTOR(TypeSelectorTree.class),
    KEYFRAMES_SELECTOR(KeyframesSelectorTree.class),
    CLASS_SELECTOR(ClassSelectorTree.class),
    ID_SELECTOR(IdSelectorTree.class),
    PSEUDO_SELECTOR(PseudoSelectorTree.class),
    PSEUDO_FUNCTION(PseudoFunctionTree.class),
    PSEUDO_IDENTIFIER(PseudoIdentifierTree.class),
    ATTRIBUTE_SELECTOR(AttributeSelectorTree.class),
    ATTRIBUTE_MATCHER_EXPRESSION(AttributeMatcherExpressionTree.class),
    ATTRIBUTE_MATCHER(AttributeMatcherTree.class),
    NAMESPACE(NamespaceTree.class),
    URI(UriTree.class),
    URI_CONTENT(UriContentTree.class),
    PERCENTAGE(PercentageTree.class),
    UNICODE_RANGE(UnicodeRangeTree.class),
    DIMENSION(DimensionTree.class),
    IMPORTANT_FLAG(ImportantFlagTree.class),
    AT_KEYWORD(AtKeywordTree.class),
    HASH(HashTree.class),
    UNIT(UnitTree.class),
    VARIABLE(StringTree.class),
    STRING(StringTree.class),
    IDENTIFIER(IdentifierTree.class),
    NUMBER(NumberTree.class),
    DELIMITER(DelimiterTree.class),
    VALUE_COMMA_SEPARATED_LIST(ValueCommaSeparatedListTree.class),
    CASE_INSENSITIVE_FLAG(CaseInsensitiveFlagTree.class),
    TOKEN(SyntaxToken.class),
    TRIVIA(SyntaxTrivia.class),
    SPACING(SyntaxSpacing.class),

    // Embedded CSS
    FILE_WITH_EMBEDDED_CSS(FileWithEmbeddedCssTree.class),
    CSS_IN_STYLE_TAG(CssInStyleTagTree.class),

    // SCSS
    SCSS_VARIABLE(ScssVariableTree.class),
    SCSS_VARIABLE_ARGUMENT(ScssVariableArgumentTree.class),
    SCSS_VARIABLE_DECLARATION(ScssVariableDeclarationTree.class),
    SCSS_NESTED_PROPERTIES_DECLARATION(ScssNestedPropertiesDeclarationTree.class),
    SCSS_DEFAULT_FLAG(ScssDefaultFlagTree.class),
    SCSS_GLOBAL_FLAG(ScssGlobalFlagTree.class),
    SCSS_OPTIONAL_FLAG(ScssOptionalFlagTree.class),
    SCSS_PARENT_SELECTOR(ScssParentSelectorTree.class),
    SCSS_PARENT_REFERENCING_SELECTOR(ScssParentReferencingSelectorTree.class),
    SCSS_PLACEHOLDER_SELECTOR(ScssPlaceholderSelectorTree.class),
    SCSS_EXTEND(ScssExtendTree.class),
    SCSS_IF_ELSE_IF_ELSE(ScssIfElseIfElseTree.class),
    SCSS_IF(ScssIfTree.class),
    SCSS_ELSE_IF(ScssElseIfTree.class),
    SCSS_ELSE(ScssElseTree.class),
    SCSS_CONDITION(ScssConditionTree.class),
    SCSS_FOR(ScssForTree.class),
    SCSS_WHILE(ScssWhileTree.class),
    SCSS_EACH(ScssEachTree.class),
    SCSS_CONTENT(ScssContentTree.class),
    SCSS_DEBUG(ScssDebugTree.class),
    SCSS_WARN(ScssWarnTree.class),
    SCSS_ERROR(ScssErrorTree.class),
    SCSS_RETURN(ScssErrorTree.class),
    SCSS_AT_ROOT(ScssAtRootTree.class),
    SCSS_AT_ROOT_PARAMETERS(ScssAtRootTree.class),
    SCSS_FUNCTION(ScssFunctionTree.class),
    SCSS_MIXIN(ScssMixinTree.class),
    SCSS_INCLUDE(ScssIncludeTree.class),
    SCSS_PARAMETERS(ScssParametersTree.class),
    SCSS_PARAMETER(ScssParameterTree.class),
    SCSS_DIRECTIVE(ScssDirectiveTree.class),
    SCSS_MAP(ScssMapEntryTree.class),
    SCSS_MAP_ENTRY(ScssMapTree.class),
    SCSS_OPERATOR(ScssOperatorTree.class),
    SCSS_MULTILINE_STRING(ScssMultilineStringTree.class),

    // Less
    LESS_VARIABLE_DECLARATION(LessVariableDeclarationTree.class),
    LESS_VARIABLE(LessVariableTree.class),
    LESS_EXTEND(LessExtendTree.class),
    LESS_PARENT_SELECTOR(LessParentSelectorTree.class),
    LESS_PARENT_REFERENCING_SELECTOR(LessParentReferencingSelectorTree.class),
    LESS_MIXIN_CALL(LessMixinCallTree.class),
    LESS_MIXIN_GUARD(LessMixinGuardTree.class),
    LESS_MIXIN_GUARD_CONDITION(LessMixinGuardConditionTree.class),
    LESS_MIXIN_PARAMETERS(LessMixinParametersTree.class),
    LESS_MIXIN_PARAMETER(LessMixinParameterTree.class),
    LESS_ESCAPING(LessEscapingTree.class),
    LESS_OPERATOR(LessOperatorTree.class);

    final Class<? extends Tree> associatedInterface;

    Kind(Class<? extends Tree> associatedInterface) {
      this.associatedInterface = associatedInterface;
    }

    public Class<? extends Tree> getAssociatedInterface() {
      return associatedInterface;
    }
  }

}
