/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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
package org.sonar.css.visitors.highlighter;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.scss.ScssDirectiveTree;
import org.sonar.plugins.css.api.tree.scss.ScssVariableTree;

import java.util.ArrayList;
import java.util.List;

public class ScssSyntaxHighlighterVisitor extends CssSyntaxHighlighterVisitor {

  public ScssSyntaxHighlighterVisitor(SensorContext sensorContext) {
    super(sensorContext);
  }

  @Override
  public List<Tree.Kind> nodesToVisit() {
    List<Tree.Kind> nodesToVisit = super.nodesToVisit();
    nodesToVisit.add(Tree.Kind.SCSS_VARIABLE);
    nodesToVisit.add(Tree.Kind.SCSS_DIRECTIVE);
    return nodesToVisit;
  }

  @Override
  public void visitNode(Tree tree) {
    super.visitNode(tree);

    List<SyntaxToken> tokens = new ArrayList<>();
    TypeOfText code = null;

    if (tree.is(Tree.Kind.SCSS_VARIABLE)) {
      tokens.add(((ScssVariableTree) tree).name().value());
      tokens.add(((ScssVariableTree) tree).prefix());
      code = TypeOfText.CONSTANT;
    } else if (tree.is(Tree.Kind.SCSS_DIRECTIVE)) {
      tokens.add(((ScssDirectiveTree) tree).at());
      tokens.add(((ScssDirectiveTree) tree).name());
      code = TypeOfText.ANNOTATION;
    }

    for (SyntaxToken token : tokens) {
      highlight(token, code);
    }
  }

}
