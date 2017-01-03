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
package org.sonar.plugins.css.api.tree.css;

import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.less.LessMixinCallTree;
import org.sonar.plugins.css.api.tree.less.LessVariableDeclarationTree;
import org.sonar.plugins.css.api.tree.scss.*;

import javax.annotation.Nullable;
import java.util.List;

public interface StatementBlockTree extends Tree {

  SyntaxToken openCurlyBrace();

  @Nullable
  List<Tree> content();

  SyntaxToken closeCurlyBrace();

  List<PropertyDeclarationTree> propertyDeclarations();

  List<VariableDeclarationTree> variableDeclarations();

  List<EmptyStatementTree> emptyStatements();

  List<DeclarationTree> allDeclarations();

  List<RulesetTree> rulesets();

  List<AtRuleTree> atRules();

  List<Tree> allStatements();

  List<LessVariableDeclarationTree> lessVariableDeclarations();

  List<LessMixinCallTree> lessMixinCalls();

  List<ScssVariableDeclarationTree> scssVariableDeclarations();

  List<ScssMixinTree> scssMixinDefinitions();

  List<ScssIncludeTree> scssMixinIncludes();

  List<ScssExtendTree> scssExtends();

  List<ScssAtRootTree> scssAtRoots();

}
