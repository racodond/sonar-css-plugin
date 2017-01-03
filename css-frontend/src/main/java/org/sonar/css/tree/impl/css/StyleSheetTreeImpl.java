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
package org.sonar.css.tree.impl.css;

import com.google.common.collect.Iterators;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.css.tree.impl.TreeListUtils;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.tree.less.LessMixinCallTree;
import org.sonar.plugins.css.api.tree.less.LessVariableDeclarationTree;
import org.sonar.plugins.css.api.tree.scss.ScssAtRootTree;
import org.sonar.plugins.css.api.tree.scss.ScssMixinTree;
import org.sonar.plugins.css.api.tree.scss.ScssIncludeTree;
import org.sonar.plugins.css.api.tree.scss.ScssVariableDeclarationTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StyleSheetTreeImpl extends TreeImpl implements StyleSheetTree {

  private final SyntaxToken byteOrderMark;
  private final SyntaxToken eof;
  private final List<Tree> all;
  private final List<StatementTree> statements;
  private final List<AtRuleTree> atRules;
  private final List<RulesetTree> rulesets;
  private final List<EmptyStatementTree> emptyStatements;

  private final List<LessVariableDeclarationTree> lessVariableDeclarations;
  private final List<LessMixinCallTree> lessMixinCalls;

  private final List<ScssVariableDeclarationTree> scssVariableDeclarations;
  private final List<ScssMixinTree> scssMixinDefinitions;
  private final List<ScssIncludeTree> scssMixinIncludes;
  private final List<ScssAtRootTree> scssAtRoots;

  public StyleSheetTreeImpl(@Nullable SyntaxToken byteOrderMark, @Nullable List<Tree> all, @Nullable SyntaxToken eof) {
    this.byteOrderMark = byteOrderMark;
    this.eof = eof;

    this.all = all != null ? all : new ArrayList<>();

    this.atRules = TreeListUtils.allElementsOfType(all, AtRuleTree.class);
    this.rulesets = TreeListUtils.allElementsOfType(all, RulesetTree.class);
    this.statements = TreeListUtils.allElementsOfType(all, StatementTree.class);
    this.emptyStatements = TreeListUtils.allElementsOfType(all, EmptyStatementTree.class);

    this.lessVariableDeclarations = TreeListUtils.allElementsOfType(all, LessVariableDeclarationTree.class);
    this.lessMixinCalls = TreeListUtils.allElementsOfType(all, LessMixinCallTree.class);

    this.scssVariableDeclarations = TreeListUtils.allElementsOfType(all, ScssVariableDeclarationTree.class);
    this.scssMixinDefinitions = TreeListUtils.allElementsOfType(all, ScssMixinTree.class);
    this.scssMixinIncludes = TreeListUtils.allElementsOfType(all, ScssIncludeTree.class);
    this.scssAtRoots = TreeListUtils.allElementsOfType(all, ScssAtRootTree.class);
  }

  @Override
  public Kind getKind() {
    return Kind.STYLESHEET;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.singletonIterator(byteOrderMark),
      all != null ? all.iterator() : new ArrayList<Tree>().iterator(),
      Iterators.singletonIterator(eof));
  }

  @Override
  public boolean hasByteOrderMark() {
    return byteOrderMark != null;
  }

  @Override
  public List<Tree> all() {
    return all;
  }

  @Override
  public List<StatementTree> statements() {
    return statements;
  }

  @Override
  public List<RulesetTree> rulesets() {
    return rulesets;
  }

  @Override
  public List<EmptyStatementTree> emptyStatements() {
    return emptyStatements;
  }

  @Override
  public List<AtRuleTree> atRules() {
    return atRules;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitStyleSheet(this);
  }

  @Override
  public List<LessVariableDeclarationTree> lessVariableDeclarations() {
    return lessVariableDeclarations;
  }

  @Override
  public List<LessMixinCallTree> lessMixinCalls() {
    return lessMixinCalls;
  }

  @Override
  public List<ScssVariableDeclarationTree> scssVariableDeclarations() {
    return scssVariableDeclarations;
  }

  @Override
  public List<ScssMixinTree> scssMixinDefinitions() {
    return scssMixinDefinitions;
  }

  @Override
  public List<ScssIncludeTree> scssMixinIncludes() {
    return scssMixinIncludes;
  }

  @Override
  public List<ScssAtRootTree> scssAtRoots() {
    return scssAtRoots;
  }

}
