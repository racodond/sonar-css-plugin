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
package org.sonar.css.visitors.highlighter;

import java.util.ArrayList;
import java.util.List;

import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.less.LessVariableTree;

public class LessSyntaxHighlighterVisitor extends CssSyntaxHighlighterVisitor {

  public LessSyntaxHighlighterVisitor(SensorContext sensorContext) {
    super(sensorContext);
  }

  @Override
  public List<Tree.Kind> nodesToVisit() {
    List<Tree.Kind> nodesToVisit = super.nodesToVisit();
    nodesToVisit.add(Tree.Kind.LESS_VARIABLE);
    return nodesToVisit;
  }

  @Override
  public void visitNode(Tree tree) {
    super.visitNode(tree);

    List<SyntaxToken> tokens = new ArrayList<>();
    TypeOfText code = null;

    if (tree.is(Tree.Kind.LESS_VARIABLE)) {
      tokens.add(((LessVariableTree) tree).variable().value());
      tokens.add(((LessVariableTree) tree).variablePrefix());
      code = TypeOfText.CONSTANT;
    }

    for (SyntaxToken token : tokens) {
      highlight(token, code);
    }
  }

}
