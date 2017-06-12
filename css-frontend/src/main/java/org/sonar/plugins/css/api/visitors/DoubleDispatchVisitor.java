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
package org.sonar.plugins.css.api.visitors;

import com.google.common.base.Preconditions;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.tree.embedded.CssInStyleTagTree;
import org.sonar.plugins.css.api.tree.embedded.FileWithEmbeddedCssTree;
import org.sonar.plugins.css.api.tree.less.*;
import org.sonar.plugins.css.api.tree.scss.*;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public abstract class DoubleDispatchVisitor implements TreeVisitor {

  private TreeVisitorContext context = null;

  @Override
  public TreeVisitorContext getContext() {
    Preconditions.checkState(context != null, "this#scanTree(context) should be called to initialised the context before accessing it");
    return context;
  }

  @Override
  public final void scanTree(TreeVisitorContext context) {
    this.context = context;
    scan(context.getTopTree());
  }

  protected void scan(@Nullable Tree tree) {
    if (tree != null) {
      tree.accept(this);
    }
  }

  protected void scanChildren(Tree tree) {
    Iterator<Tree> childrenIterator = tree.childrenIterator();

    Tree child;

    while (childrenIterator.hasNext()) {
      child = childrenIterator.next();
      if (child != null) {
        child.accept(this);
      }
    }
  }

  protected <T extends Tree> void scan(List<T> trees) {
    trees.forEach(this::scan);
  }

  public void visitStyleSheet(StyleSheetTree tree) {
    scanChildren(tree);
  }

  public void visitAtRule(AtRuleTree tree) {
    scanChildren(tree);
  }

  public void visitRuleset(RulesetTree tree) {
    scanChildren(tree);
  }

  public void visitStatementBlock(StatementBlockTree tree) {
    scanChildren(tree);
  }

  public void visitParenthesisBlock(ParenthesisBlockTree tree) {
    scanChildren(tree);
  }

  public void visitBracketBlock(BracketBlockTree tree) {
    scanChildren(tree);
  }

  public void visitPropertyDeclaration(PropertyDeclarationTree tree) {
    scanChildren(tree);
  }

  public void visitVariableDeclaration(VariableDeclarationTree tree) {
    scanChildren(tree);
  }

  public void visitEmptyStatement(EmptyStatementTree tree) {
    scanChildren(tree);
  }

  public void visitProperty(PropertyTree tree) {
    scanChildren(tree);
  }

  public void visitValue(ValueTree tree) {
    scanChildren(tree);
  }

  public void visitFunction(FunctionTree tree) {
    scanChildren(tree);
  }

  public void visitParameters(ParametersTree tree) {
    scanChildren(tree);
  }

  public void visitUri(UriTree tree) {
    scanChildren(tree);
  }

  public void visitUriContent(UriContentTree tree) {
    scanChildren(tree);
  }

  public void visitSelectors(SelectorsTree tree) {
    scanChildren(tree);
  }

  public void visitSelector(SelectorTree tree) {
    scanChildren(tree);
  }

  public void visitCompoundSelector(CompoundSelectorTree tree) {
    scanChildren(tree);
  }

  public void visitClassSelector(ClassSelectorTree tree) {
    scanChildren(tree);
  }

  public void visitIdSelector(IdSelectorTree tree) {
    scanChildren(tree);
  }

  public void visitPseudoSelector(PseudoSelectorTree tree) {
    scanChildren(tree);
  }

  public void visitKeyframesSelector(KeyframesSelectorTree tree) {
    scanChildren(tree);
  }

  public void visitTypeSelector(TypeSelectorTree tree) {
    scanChildren(tree);
  }

  public void visitAttributeSelector(AttributeSelectorTree tree) {
    scanChildren(tree);
  }

  public void visitAttributeMatcherExpression(AttributeMatcherExpressionTree tree) {
    scanChildren(tree);
  }

  public void visitAttributeMatcher(AttributeMatcherTree tree) {
    scanChildren(tree);
  }

  public void visitSelectorCombinator(SelectorCombinatorTree tree) {
    scanChildren(tree);
  }

  public void visitPseudoFunction(PseudoFunctionTree tree) {
    scanChildren(tree);
  }

  public void visitPseudoIdentifier(PseudoIdentifierTree tree) {
    scanChildren(tree);
  }

  public void visitNamespace(NamespaceTree tree) {
    scanChildren(tree);
  }

  public void visitImportant(ImportantFlagTree tree) {
    scanChildren(tree);
  }

  public void visitAtKeyword(AtKeywordTree tree) {
    scanChildren(tree);
  }

  public void visitHash(HashTree tree) {
    scanChildren(tree);
  }

  public void visitPercentage(PercentageTree tree) {
    scanChildren(tree);
  }

  public void visitDimension(DimensionTree tree) {
    scanChildren(tree);
  }

  public void visitVariable(VariableTree tree) {
    scanChildren(tree);
  }

  public void visitUnit(UnitTree tree) {
    scanChildren(tree);
  }

  public void visitIdentifier(IdentifierTree tree) {
    scanChildren(tree);
  }

  public void visitString(StringTree tree) {
    scanChildren(tree);
  }

  public void visitUnicodeRange(UnicodeRangeTree tree) {
    scanChildren(tree);
  }

  public void visitNumber(NumberTree tree) {
    scanChildren(tree);
  }

  public void visitCaseInsensitiveFlag(CaseInsensitiveFlagTree tree) {
    scanChildren(tree);
  }

  public void visitValueCommaSeparatedList(ValueCommaSeparatedListTree tree) {
    scanChildren(tree);
  }

  public void visitDelimiter(DelimiterTree tree) {
    scanChildren(tree);
  }

  public void visitToken(SyntaxToken token) {
    for (SyntaxTrivia syntaxTrivia : token.trivias()) {
      syntaxTrivia.accept(this);
    }
  }

  public void visitComment(SyntaxTrivia commentToken) {
    // No sub-tree
  }

  // -------------------------------------------------------------------------
  // Embedded CSS
  // -------------------------------------------------------------------------

  public void visitFileWithEmbeddedCss(FileWithEmbeddedCssTree tree) {
    scanChildren(tree);
  }

  public void visitCssInStyleTag(CssInStyleTagTree tree) {
    scanChildren(tree);
  }

  // -------------------------------------------------------------------------
  // SCSS
  // -------------------------------------------------------------------------

  public void visitScssNestedPropertiesDeclaration(ScssNestedPropertiesDeclarationTree tree) {
    scanChildren(tree);
  }

  public void visitScssVariableDeclaration(ScssVariableDeclarationTree tree) {
    scanChildren(tree);
  }

  public void visitScssVariable(ScssVariableTree tree) {
    scanChildren(tree);
  }

  public void visitScssVariableArgument(ScssVariableArgumentTree tree) {
    scanChildren(tree);
  }

  public void visitScssDefaultFlag(ScssDefaultFlagTree tree) {
    scanChildren(tree);
  }

  public void visitScssGlobalFlag(ScssGlobalFlagTree tree) {
    scanChildren(tree);
  }

  public void visitScssOptionalFlag(ScssOptionalFlagTree tree) {
    scanChildren(tree);
  }

  public void visitScssParentSelector(ScssParentSelectorTree tree) {
    scanChildren(tree);
  }

  public void visitScssFunction(ScssFunctionTree tree) {
    scanChildren(tree);
  }

  public void visitScssMixin(ScssMixinTree tree) {
    scanChildren(tree);
  }

  public void visitScssInclude(ScssIncludeTree tree) {
    scanChildren(tree);
  }

  public void visitScssParameters(ScssParametersTree tree) {
    scanChildren(tree);
  }

  public void visitScssParameter(ScssParameterTree tree) {
    scanChildren(tree);
  }

  public void visitScssExtend(ScssExtendTree tree) {
    scanChildren(tree);
  }

  public void visitScssCondition(ScssConditionTree tree) {
    scanChildren(tree);
  }

  public void visitScssIfElseIfElse(ScssIfElseIfElseTree tree) {
    scanChildren(tree);
  }

  public void visitScssIf(ScssIfTree tree) {
    scanChildren(tree);
  }

  public void visitScssElseIf(ScssElseIfTree tree) {
    scanChildren(tree);
  }

  public void visitScssElse(ScssElseTree tree) {
    scanChildren(tree);
  }

  public void visitScssFor(ScssForTree tree) {
    scanChildren(tree);
  }

  public void visitScssWhile(ScssWhileTree tree) {
    scanChildren(tree);
  }

  public void visitScssEach(ScssEachTree tree) {
    scanChildren(tree);
  }

  public void visitScssContent(ScssContentTree tree) {
    scanChildren(tree);
  }

  public void visitScssDebug(ScssDebugTree tree) {
    scanChildren(tree);
  }

  public void visitScssWarn(ScssWarnTree tree) {
    scanChildren(tree);
  }

  public void visitScssError(ScssErrorTree tree) {
    scanChildren(tree);
  }

  public void visitScssReturn(ScssReturnTree tree) {
    scanChildren(tree);
  }

  public void visitScssAtRoot(ScssAtRootTree tree) {
    scanChildren(tree);
  }

  public void visitScssAtRootParameters(ScssAtRootParametersTree tree) {
    scanChildren(tree);
  }

  public void visitScssParentReferencingSelector(ScssParentReferencingSelectorTree tree) {
    scanChildren(tree);
  }

  public void visitScssPlaceholderSelector(ScssPlaceholderSelectorTree tree) {
    scanChildren(tree);
  }

  public void visitScssMultilineString(ScssMultilineStringTree tree) {
    scanChildren(tree);
  }

  public void visitScssDirective(ScssDirectiveTree tree) {
    scanChildren(tree);
  }

  public void visitScssMap(ScssMapTree tree) {
    scanChildren(tree);
  }

  public void visitScssMapEntry(ScssMapEntryTree tree) {
    scanChildren(tree);
  }

  public void visitScssOperator(ScssOperatorTree tree) {
    scanChildren(tree);
  }


  // -------------------------------------------------------------------------
  // Less
  // -------------------------------------------------------------------------

  public void visitLessVariableDeclaration(LessVariableDeclarationTree tree) {
    scanChildren(tree);
  }

  public void visitLessVariable(LessVariableTree tree) {
    scanChildren(tree);
  }

  public void visitLessExtend(LessExtendTree tree) {
    scanChildren(tree);
  }

  public void visitLessParentSelector(LessParentSelectorTree tree) {
    scanChildren(tree);
  }

  public void visitLessParentReferencingSelector(LessParentReferencingSelectorTree tree) {
    scanChildren(tree);
  }

  public void visitLessMixinCall(LessMixinCallTree tree) {
    scanChildren(tree);
  }

  public void visitLessMixinGuard(LessMixinGuardTree tree) {
    scanChildren(tree);
  }

  public void visitLessMixinGuardCondition(LessMixinGuardConditionTree tree) {
    scanChildren(tree);
  }

  public void visitLessMixinParameters(LessMixinParametersTree tree) {
    scanChildren(tree);
  }

  public void visitLessMixinParameter(LessMixinParameterTree tree) {
    scanChildren(tree);
  }

  public void visitLessEscaping(LessEscapingTree tree) {
    scanChildren(tree);
  }

  public void visitLessOperator(LessOperatorTree tree) {
    scanChildren(tree);
  }

}
