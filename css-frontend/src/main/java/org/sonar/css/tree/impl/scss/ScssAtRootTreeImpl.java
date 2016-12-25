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
package org.sonar.css.tree.impl.scss;

import com.google.common.collect.Iterators;
import org.sonar.css.tree.impl.TreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.RulesetTree;
import org.sonar.plugins.css.api.tree.css.StatementBlockTree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.scss.ScssAtRootParametersTree;
import org.sonar.plugins.css.api.tree.scss.ScssAtRootTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import javax.annotation.Nullable;
import java.util.Iterator;

public class ScssAtRootTreeImpl extends TreeImpl implements ScssAtRootTree {

  private final SyntaxToken directive;

  @Nullable
  private final ScssAtRootParametersTree parameters;

  @Nullable
  private final RulesetTree ruleset;

  @Nullable
  private final StatementBlockTree block;

  public ScssAtRootTreeImpl(SyntaxToken directive, @Nullable ScssAtRootParametersTree parameters, Tree content) {
    this.directive = directive;
    this.parameters = parameters;

    if (content instanceof RulesetTree) {
      this.ruleset = (RulesetTree) content;
      this.block = null;
    } else if (content instanceof StatementBlockTree) {
      this.ruleset = null;
      this.block = (StatementBlockTree) content;
    } else {
      throw new IllegalStateException("Unknown @at-rool content type: " + content);
    }
  }

  @Override
  public Kind getKind() {
    return Kind.SCSS_AT_ROOT;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(directive, parameters, ruleset, block);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitScssAtRoot(this);
  }

  @Override
  public SyntaxToken directive() {
    return directive;
  }

  @Override
  @Nullable
  public ScssAtRootParametersTree parameters() {
    return parameters;
  }

  @Override
  @Nullable
  public RulesetTree ruleset() {
    return ruleset;
  }

  @Override
  @Nullable
  public StatementBlockTree block() {
    return block;
  }

}
