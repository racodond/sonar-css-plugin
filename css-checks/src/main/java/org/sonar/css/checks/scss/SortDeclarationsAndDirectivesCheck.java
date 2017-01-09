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
package org.sonar.css.checks.scss;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.StatementBlockTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.ArrayList;
import java.util.List;

@Rule(
  key = "sort-declarations-and-directives",
  name = "Declarations and directives should be properly sorted",
  priority = Priority.MAJOR,
  tags = {Tags.UNDERSTANDABILITY})
@SqaleConstantRemediation("15min")
@ActivatedByDefault
public class SortDeclarationsAndDirectivesCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitStatementBlock(StatementBlockTree tree) {
    checkVariableDeclarations(tree.content());
    checkExtends(tree.content());
    checkIncludes(tree.content());
    super.visitStatementBlock(tree);
  }

  private void checkVariableDeclarations(List<Tree> content) {
    int last = findLastVariableDeclarations(content);
    List<Tree> notVariableDeclarations = new ArrayList<>();

    if (last != -1) {
      for (int i = 0; i < last; i++) {
        if (!isKindToIgnore(content.get(i)) && !content.get(i).is(Tree.Kind.SCSS_VARIABLE_DECLARATION)) {
          notVariableDeclarations.add(content.get(i));
        }
      }
    }

    if (!notVariableDeclarations.isEmpty()) {
      PreciseIssue issue = addPreciseIssue(content.get(last), "Move this variable declaration before all the other declarations and directives.");
      notVariableDeclarations.stream().forEach(t -> issue.secondary(t, "Move after variable declarations"));
    }
  }

  private int findLastVariableDeclarations(List<Tree> content) {
    int last = -1;
    for (int i = 0; i < content.size(); i++) {
      if (content.get(i).is(Tree.Kind.SCSS_VARIABLE_DECLARATION)) {
        last = i;
      }
    }
    return last;
  }

  private void checkExtends(List<Tree> content) {
    int last = findLastExtend(content);
    List<Tree> notVariableExtends = new ArrayList<>();

    if (last != -1) {
      for (int i = 0; i < last; i++) {
        if (!isKindToIgnore(content.get(i)) && !content.get(i).is(Tree.Kind.SCSS_VARIABLE_DECLARATION, Tree.Kind.SCSS_EXTEND)) {
          notVariableExtends.add(content.get(i));
        }
      }
    }

    if (!notVariableExtends.isEmpty()) {
      PreciseIssue issue = addPreciseIssue(content.get(last), "Move this @extend directive before all property declarations, nested selectors and @include directives.");
      notVariableExtends.stream().forEach(t -> issue.secondary(t, "Move after @extend directive"));
    }
  }

  private int findLastExtend(List<Tree> content) {
    int last = -1;
    for (int i = 0; i < content.size(); i++) {
      if (content.get(i).is(Tree.Kind.SCSS_EXTEND)) {
        last = i;
      }
    }
    return last;
  }

  private void checkIncludes(List<Tree> content) {
    int last = findLastInclude(content);
    List<Tree> notVariableIncludes = new ArrayList<>();

    if (last != -1) {
      for (int i = 0; i < last; i++) {
        if (!isKindToIgnore(content.get(i)) && !content.get(i).is(Tree.Kind.SCSS_VARIABLE_DECLARATION, Tree.Kind.SCSS_EXTEND, Tree.Kind.SCSS_INCLUDE)) {
          notVariableIncludes.add(content.get(i));
        }
      }
    }

    if (!notVariableIncludes.isEmpty()) {
      PreciseIssue issue = addPreciseIssue(content.get(last), "Move this @include directive before all property declarations and nested selectors.");
      notVariableIncludes.stream().forEach(t -> issue.secondary(t, "Move after @include directive"));
    }
  }

  private int findLastInclude(List<Tree> content) {
    int last = -1;
    for (int i = 0; i < content.size(); i++) {
      if (content.get(i).is(Tree.Kind.SCSS_INCLUDE)) {
        last = i;
      }
    }
    return last;
  }

  private boolean isKindToIgnore(Tree tree) {
    return tree.is(
      Tree.Kind.SCSS_MIXIN,
      Tree.Kind.SCSS_IF_ELSE_IF_ELSE,
      Tree.Kind.SCSS_FOR,
      Tree.Kind.SCSS_EACH,
      Tree.Kind.SCSS_WHILE,
      Tree.Kind.SCSS_DEBUG,
      Tree.Kind.SCSS_ERROR,
      Tree.Kind.SCSS_WARN);
  }


}
