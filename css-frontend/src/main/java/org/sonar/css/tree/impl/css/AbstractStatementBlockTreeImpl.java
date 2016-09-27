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
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.tree.less.LessMixinCallTree;
import org.sonar.plugins.css.api.tree.less.LessVariableDeclarationTree;

public abstract class AbstractStatementBlockTreeImpl extends TreeImpl implements StatementBlockTree {

  private final SyntaxToken openCurlyBrace;
  private final List<Tree> content;
  private final List<PropertyDeclarationTree> propertyDeclarations;
  private final List<VariableDeclarationTree> variableDeclarations;
  private final List<LessVariableDeclarationTree> lessVariableDeclarations;
  private final List<LessMixinCallTree> lessMixinCalls;
  private final List<DeclarationTree> allDeclarations;
  private final List<RulesetTree> rulesets;
  private final List<AtRuleTree> atRules;
  private final List<EmptyStatementTree> emptyStatements;
  private final List<Tree> allStatements;
  private final SyntaxToken closeCurlyBrace;

  public AbstractStatementBlockTreeImpl(SyntaxToken openCurlyBrace, @Nullable List<Tree> content, SyntaxToken closeCurlyBrace) {
    this.openCurlyBrace = openCurlyBrace;
    this.content = content;
    this.closeCurlyBrace = closeCurlyBrace;

    propertyDeclarations = new ArrayList<>();
    variableDeclarations = new ArrayList<>();
    lessVariableDeclarations = new ArrayList<>();
    lessMixinCalls = new ArrayList<>();
    allDeclarations = new ArrayList<>();
    rulesets = new ArrayList<>();
    atRules = new ArrayList<>();
    emptyStatements = new ArrayList<>();
    allStatements = new ArrayList<>();

    buildLists(content);
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    if (content != null) {
      return Iterators.concat(
        Iterators.singletonIterator(openCurlyBrace),
        content.iterator(),
        Iterators.singletonIterator(closeCurlyBrace));
    } else {
      return Iterators.forArray(openCurlyBrace, closeCurlyBrace);
    }
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
  public List<LessVariableDeclarationTree> lessVariableDeclarations() {
    return lessVariableDeclarations;
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
  public List<LessMixinCallTree> lessMixinCalls() {
    return lessMixinCalls;
  }

  @Override
  public List<Tree> allStatements() {
    return allStatements;
  }

  @Override
  @Nullable
  public List<Tree> content() {
    return content;
  }

  private void buildLists(@Nullable List<Tree> content) {
    if (content != null) {
      for (Tree tree : content) {
        if (tree instanceof RulesetTree) {
          rulesets.add((RulesetTree) tree);
        } else if (tree instanceof AtRuleTree) {
          atRules.add((AtRuleTree) tree);
        } else if (tree instanceof EmptyStatementTree) {
          emptyStatements.add((EmptyStatementTree) tree);
        } else if (tree instanceof LessMixinCallTree) {
          lessMixinCalls.add((LessMixinCallTree) tree);
        } else if (tree instanceof DeclarationTree) {
          if (tree instanceof PropertyDeclarationTree) {
            propertyDeclarations.add((PropertyDeclarationTree) tree);
          } else if (tree instanceof VariableDeclarationTree) {
            variableDeclarations.add((VariableDeclarationTree) tree);
          } else if (tree instanceof LessVariableDeclarationTree) {
            lessVariableDeclarations.add((LessVariableDeclarationTree) tree);
          }
          allDeclarations.add((DeclarationTree) tree);
        } else {
          throw new IllegalStateException("Unexpected tree: " + tree.getClass());
        }
        allStatements.add(tree);
      }
    }
  }

}
