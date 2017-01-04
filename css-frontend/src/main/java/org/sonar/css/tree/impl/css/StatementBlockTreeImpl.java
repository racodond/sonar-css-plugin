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
import org.sonar.plugins.css.api.tree.scss.*;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StatementBlockTreeImpl extends TreeImpl implements StatementBlockTree {

  private final SyntaxToken openCurlyBrace;
  private final List<Tree> content;
  private final SyntaxToken closeCurlyBrace;

  private final List<PropertyDeclarationTree> propertyDeclarations;
  private final List<VariableDeclarationTree> variableDeclarations;
  private final List<DeclarationTree> allDeclarations;
  private final List<RulesetTree> rulesets;
  private final List<AtRuleTree> atRules;
  private final List<EmptyStatementTree> emptyStatements;
  private final List<Tree> allStatements;

  private final List<LessVariableDeclarationTree> lessVariableDeclarations;
  private final List<LessMixinCallTree> lessMixinCalls;

  private final List<ScssVariableDeclarationTree> scssVariableDeclarations;
  private final List<ScssMixinTree> scssMixinDefinitions;
  private final List<ScssIncludeTree> scssMixinIncludes;
  private final List<ScssExtendTree> scssExtends;
  private final List<ScssAtRootTree> scssAtRoots;

  public StatementBlockTreeImpl(StatementBlockTree block) {
    this(block.openCurlyBrace(), block.content(), block.closeCurlyBrace());
  }

  public StatementBlockTreeImpl(SyntaxToken openCurlyBrace, @Nullable List<Tree> content, SyntaxToken closeCurlyBrace) {
    this.openCurlyBrace = openCurlyBrace;
    this.content = content != null ? content : new ArrayList<>();
    this.closeCurlyBrace = closeCurlyBrace;

    propertyDeclarations = TreeListUtils.allElementsOfType(content, PropertyDeclarationTree.class);
    variableDeclarations = TreeListUtils.allElementsOfType(content, VariableDeclarationTree.class);
    rulesets = TreeListUtils.allElementsOfType(content, RulesetTree.class);
    atRules = TreeListUtils.allElementsOfType(content, AtRuleTree.class);
    emptyStatements = TreeListUtils.allElementsOfType(content, EmptyStatementTree.class);

    scssVariableDeclarations = TreeListUtils.allElementsOfType(content, ScssVariableDeclarationTree.class);
    scssMixinDefinitions = TreeListUtils.allElementsOfType(content, ScssMixinTree.class);
    scssMixinIncludes = TreeListUtils.allElementsOfType(content, ScssIncludeTree.class);
    scssExtends = TreeListUtils.allElementsOfType(content, ScssExtendTree.class);
    scssAtRoots = TreeListUtils.allElementsOfType(content, ScssAtRootTree.class);

    lessVariableDeclarations = TreeListUtils.allElementsOfType(content, LessVariableDeclarationTree.class);
    lessMixinCalls = TreeListUtils.allElementsOfType(content, LessMixinCallTree.class);

    allDeclarations = TreeListUtils.allElementsOfType(content, DeclarationTree.class);
    allStatements = TreeListUtils.allElementsOfType(content, Tree.class);
  }

  @Override
  public Kind getKind() {
    return Kind.STATEMENT_BLOCK;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.singletonIterator(openCurlyBrace),
      content != null ? content.iterator() : new ArrayList<Tree>().iterator(),
      Iterators.singletonIterator(closeCurlyBrace));
  }

  @Override
  public SyntaxToken openCurlyBrace() {
    return openCurlyBrace;
  }

  @Override
  public SyntaxToken closeCurlyBrace() {
    return closeCurlyBrace;
  }

  @Override
  public List<PropertyDeclarationTree> propertyDeclarations() {
    return propertyDeclarations;
  }

  @Override
  public List<VariableDeclarationTree> variableDeclarations() {
    return variableDeclarations;
  }

  @Override
  public List<EmptyStatementTree> emptyStatements() {
    return emptyStatements;
  }

  @Override
  public List<DeclarationTree> allDeclarations() {
    return allDeclarations;
  }

  @Override
  public List<AtRuleTree> atRules() {
    return atRules;
  }

  @Override
  public List<RulesetTree> rulesets() {
    return rulesets;
  }

  @Override
  public List<Tree> allStatements() {
    return allStatements;
  }

  @Override
  public List<Tree> content() {
    return content;
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
  public List<ScssExtendTree> scssExtends() {
    return scssExtends;
  }

  @Override
  public List<ScssAtRootTree> scssAtRoots() {
    return scssAtRoots;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitStatementBlock(this);
  }

}
