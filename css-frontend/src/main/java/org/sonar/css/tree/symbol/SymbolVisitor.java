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
package org.sonar.css.tree.symbol;

import org.sonar.plugins.css.api.symbol.Symbol;
import org.sonar.plugins.css.api.symbol.Usage;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.StatementBlockTree;
import org.sonar.plugins.css.api.tree.css.StyleSheetTree;
import org.sonar.plugins.css.api.tree.embedded.FileWithEmbeddedCssTree;
import org.sonar.plugins.css.api.tree.less.LessMixinParametersTree;
import org.sonar.plugins.css.api.tree.less.LessVariableTree;
import org.sonar.plugins.css.api.tree.scss.ScssParametersTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import java.util.Map;

public class SymbolVisitor extends DoubleDispatchVisitor {

  private SymbolModelBuilder symbolModel;
  private Scope currentScope;
  private Map<Tree, Scope> treeScopeMap;

  public SymbolVisitor(Map<Tree, Scope> treeScopeMap) {
    this.treeScopeMap = treeScopeMap;
  }

  @Override
  public void visitFileWithEmbeddedCss(FileWithEmbeddedCssTree tree) {
    initScopes();
    enterScope(tree);
    super.visitFileWithEmbeddedCss(tree);
    leaveScope();
  }

  @Override
  public void visitStyleSheet(StyleSheetTree tree) {
    if (symbolModel == null) {
      initScopes();
    }
    enterScope(tree);
    super.visitStyleSheet(tree);
    leaveScope();
  }

  @Override
  public void visitStatementBlock(StatementBlockTree tree) {
    enterScope(tree);
    super.visitStatementBlock(tree);
    leaveScope();
  }

  @Override
  public void visitLessMixinParameters(LessMixinParametersTree tree) {
    enterScope(tree);
    super.visitLessMixinParameters(tree);
    leaveScope();
  }

  private void leaveScope() {
    if (currentScope != null) {
      currentScope = currentScope.outer();
    }
  }

  private void enterScope(Tree tree) {
    currentScope = treeScopeMap.get(tree);
    if (currentScope == null) {
      throw new IllegalStateException("No scope found for the tree");
    }
  }

  @Override
  public void visitLessVariable(LessVariableTree tree) {
    Usage.Kind usage;

    if (tree.parent().is(Tree.Kind.LESS_VARIABLE_DECLARATION, Tree.Kind.LESS_MIXIN_PARAMETER)) {
      usage = Usage.Kind.DECLARATION;
    } else {
      usage = Usage.Kind.READ;
    }

    symbolModel
      .declareSymbol(tree.variableName(), Symbol.Kind.LESS_VARIABLE, currentScope)
      .addUsage(Usage.create(tree.variable(), usage));

    super.visitLessVariable(tree);
  }

  private void initScopes() {
    symbolModel = (SymbolModelBuilder) getContext().getSymbolModel();
    currentScope = null;
  }

}
