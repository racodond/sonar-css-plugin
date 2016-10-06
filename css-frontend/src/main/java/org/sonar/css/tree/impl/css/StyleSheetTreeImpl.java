/*
 * SonarQube CSS / Less Plugin
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.css.tree.impl.TreeListUtils;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.tree.less.LessMixinCallTree;
import org.sonar.plugins.css.api.tree.less.LessVariableDeclarationTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class StyleSheetTreeImpl extends TreeImpl implements StyleSheetTree {

  private final SyntaxToken byteOrderMark;
  private final SyntaxToken eof;
  private final List<Tree> all;
  private final List<StatementTree> statements;
  private List<AtRuleTree> atRules;
  private List<RulesetTree> rulesets;
  private List<EmptyStatementTree> emptyStatements;
  private List<LessVariableDeclarationTree> lessVariableDeclarations;
  private List<LessMixinCallTree> lessMixinCalls;

  public StyleSheetTreeImpl(@Nullable SyntaxToken byteOrderMark, @Nullable List<Tree> all, SyntaxToken eof) {
    this.byteOrderMark = byteOrderMark;
    this.eof = eof;

    if (all != null) {
      this.all = all;
    } else {
      this.all = new ArrayList<>();
    }

    this.atRules = TreeListUtils.allElementsOfType(all, AtRuleTree.class);
    this.rulesets = TreeListUtils.allElementsOfType(all, RulesetTree.class);
    this.statements = TreeListUtils.allElementsOfType(all, StatementTree.class);
    this.lessVariableDeclarations = TreeListUtils.allElementsOfType(all, LessVariableDeclarationTree.class);
    this.lessMixinCalls = TreeListUtils.allElementsOfType(all, LessMixinCallTree.class);
    this.emptyStatements = TreeListUtils.allElementsOfType(all, EmptyStatementTree.class);
  }

  @Override
  public Kind getKind() {
    return Kind.STYLESHEET;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    if (all != null) {
      return Iterators.concat(
        Iterators.singletonIterator(byteOrderMark),
        all.iterator(),
        Iterators.singletonIterator(eof));
    } else {
      return Iterators.forArray(byteOrderMark, eof);
    }
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
  public List<LessVariableDeclarationTree> lessVariableDeclarations() {
    return lessVariableDeclarations;
  }

  @Override
  public List<LessMixinCallTree> lessMixinCalls() {
    return lessMixinCalls;
  }

  @Override
  public List<AtRuleTree> atRules() {
    return atRules;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitStyleSheet(this);
  }

}
