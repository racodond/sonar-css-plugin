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
package org.sonar.css.checks.less;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.css.tree.symbol.Scope;
import org.sonar.plugins.css.api.symbol.Symbol;
import org.sonar.plugins.css.api.symbol.Usage;
import org.sonar.plugins.css.api.tree.css.StyleSheetTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "multiple-variable-declarations-same-scope",
  name = "Same variable should not be declared multiple times within the same scope",
  priority = Priority.CRITICAL,
  tags = {Tags.BUG})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class MultipleLessVariableDeclarationsSameScopeCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitStyleSheet(StyleSheetTree tree) {

    Comparator<Usage> byLine = Comparator.comparing(u -> u.identifierTree().value().line());
    Comparator<Usage> byColumn = Comparator.comparing(u -> u.identifierTree().value().column());

    for (Scope scope : getContext().getSymbolModel().getScopes()) {
      for (Symbol symbol : scope.getSymbols(Symbol.Kind.LESS_VARIABLE)) {
        List<Usage> usages = symbol.usages()
          .stream()
          .filter(u -> u.is(Usage.Kind.DECLARATION))
          .sorted(byLine.thenComparing(byColumn))
          .collect(Collectors.toList());

        if (usages.size() > 1) {
          createIssue(usages);
        }
      }
    }
  }

  private void createIssue(List<Usage> usages) {
    PreciseIssue issue = addPreciseIssue(usages.get(0).identifierTree(), "Merge those multiple variable declarations within the same scope.");
    for (int i = 1; i < usages.size(); i++) {
      issue.secondary(usages.get(i).identifierTree(), "Same variable declaration");
    }
  }

}
