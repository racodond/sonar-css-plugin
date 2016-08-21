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
import java.util.stream.Collectors;
import javax.annotation.Nullable;

import org.sonar.plugins.css.api.tree.*;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class StyleSheetTreeImpl extends CssTree implements StyleSheetTree {

  private final SyntaxToken byteOrderMark;
  private final SyntaxToken eof;
  private final List<StatementTree> statements;
  private List<AtRuleTree> atRules;
  private List<RulesetTree> rulesets;

  public StyleSheetTreeImpl(@Nullable SyntaxToken byteOrderMark, @Nullable List<StatementTree> statements, SyntaxToken eof) {
    this.byteOrderMark = byteOrderMark;
    this.eof = eof;

    if (statements != null) {
      this.statements = statements;
    } else {
      this.statements = new ArrayList<>();
    }

    atRules = buildAtRuleList();
    rulesets = buildRulesetList();
  }

  @Override
  public Kind getKind() {
    return Kind.STYLESHEET;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    if (statements != null) {
      return Iterators.concat(
        Iterators.singletonIterator(byteOrderMark),
        statements.iterator(),
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
  public List<StatementTree> statements() {
    return statements;
  }

  @Override
  public List<RulesetTree> rulesets() {
    return rulesets;
  }

  @Override
  public List<AtRuleTree> atRules() {
    return atRules;
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitStyleSheet(this);
  }

  private List<AtRuleTree> buildAtRuleList() {
    return statements
      .stream()
      .filter(s -> s instanceof AtRuleTree)
      .map(s -> (AtRuleTree) s)
      .collect(Collectors.toList());
  }

  private List<RulesetTree> buildRulesetList() {
    return statements
      .stream()
      .filter(s -> s instanceof RulesetTree)
      .map(s -> (RulesetTree) s)
      .collect(Collectors.toList());
  }

}
