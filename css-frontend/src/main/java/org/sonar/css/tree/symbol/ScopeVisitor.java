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
package org.sonar.css.tree.symbol;

import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.StatementBlockTree;
import org.sonar.plugins.css.api.tree.css.StyleSheetTree;
import org.sonar.plugins.css.api.tree.embedded.FileWithEmbeddedCssTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import java.util.HashMap;
import java.util.Map;

public class ScopeVisitor extends DoubleDispatchVisitor {

  private SymbolModelBuilder symbolModel;
  private Scope currentScope;
  private Map<Tree, Scope> treeScopeMap;

  public Map<Tree, Scope> getTreeScopeMap() {
    return treeScopeMap;
  }

  @Override
  public void visitFileWithEmbeddedCss(FileWithEmbeddedCssTree tree) {
    initScopes();
    newScope(tree);
    super.visitFileWithEmbeddedCss(tree);
    leaveScope();
  }

  @Override
  public void visitStyleSheet(StyleSheetTree tree) {
    if (symbolModel == null) {
      initScopes();
    }
    newScope(tree);
    super.visitStyleSheet(tree);
    leaveScope();
  }

  @Override
  public void visitStatementBlock(StatementBlockTree tree) {
    newScope(tree);
    super.visitStatementBlock(tree);
    leaveScope();
  }

  private void leaveScope() {
    if (currentScope != null) {
      currentScope = currentScope.outer();
    }
  }

  private void newScope(Tree tree) {
    currentScope = new Scope(currentScope, tree);
    treeScopeMap.put(tree, currentScope);
    symbolModel.addScope(currentScope);
  }

  private void initScopes() {
    symbolModel = (SymbolModelBuilder) getContext().getSymbolModel();
    currentScope = null;
    treeScopeMap = new HashMap<>();
  }

}
