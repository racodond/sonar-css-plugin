/*
 * SonarQube CSS Plugin
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
package org.sonar.css.tree.impl;

import com.google.common.collect.Iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

import org.sonar.plugins.css.api.tree.*;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class AtRuleBlockTreeImpl extends CssTree implements AtRuleBlockTree {

  private final SyntaxToken openCurlyBrace;
  private final SyntaxToken closeCurlyBrace;
  private final Object content;
  private DeclarationsTree declarations;
  private final List<AtRuleTree> atRules;
  private final List<RulesetTree> rulesets;
  private final List<StatementTree> statements;

  public AtRuleBlockTreeImpl(SyntaxToken openCurlyBrace, @Nullable Object content, SyntaxToken closeCurlyBrace) {
    this.openCurlyBrace = openCurlyBrace;
    this.closeCurlyBrace = closeCurlyBrace;
    this.content = content;

    declarations = null;
    atRules = new ArrayList<>();
    rulesets = new ArrayList<>();
    statements = new ArrayList<>();

    if (content != null) {
      if (content instanceof DeclarationsTree) {
        declarations = (DeclarationsTree) content;
      } else {
        buildLists((List<StatementTree>) content);
      }
    }
  }

  @Override
  public Kind getKind() {
    return Kind.AT_RULE_BLOCK;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    if (content != null) {
      if (content instanceof DeclarationsTree) {
        return Iterators.forArray(openCurlyBrace, (DeclarationsTree) content, closeCurlyBrace);
      } else {
        return Iterators.concat(
          Iterators.singletonIterator(openCurlyBrace),
          ((List) content).iterator(),
          Iterators.singletonIterator(closeCurlyBrace));
      }
    } else {
      return Iterators.forArray(openCurlyBrace, closeCurlyBrace);
    }
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitAtRuleBlock(this);
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
  @Nullable
  public DeclarationsTree declarations() {
    return declarations;
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
  public List<StatementTree> statements() {
    return statements;
  }

  private void buildLists(List<StatementTree> content) {
    for (StatementTree tree : content) {
      if (tree instanceof AtRuleTree) {
        atRules.add((AtRuleTree) tree);
      } else if (tree instanceof RulesetTree) {
        rulesets.add((RulesetTree) tree);
      }
      statements.add(tree);
    }
  }

}
