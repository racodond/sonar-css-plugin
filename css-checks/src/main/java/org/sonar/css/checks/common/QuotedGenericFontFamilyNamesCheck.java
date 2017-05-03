/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2017 David RACODON
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
package org.sonar.css.checks.common;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.css.model.property.standard.Font;
import org.sonar.css.model.property.standard.FontFamily;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.PropertyDeclarationTree;
import org.sonar.plugins.css.api.tree.css.StringTree;
import org.sonar.plugins.css.api.tree.css.ValueCommaSeparatedListTree;
import org.sonar.plugins.css.api.tree.css.ValueTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.ArrayList;
import java.util.List;

@Rule(
  key = "quoted-generic-font-family-names",
  name = "Generic font family names should not be quoted",
  priority = Priority.CRITICAL,
  tags = {Tags.BUG})
@SqaleConstantRemediation("15min")
@ActivatedByDefault
public class QuotedGenericFontFamilyNamesCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitPropertyDeclaration(PropertyDeclarationTree tree) {
    if (tree.property().standardProperty() instanceof FontFamily
      || tree.property().standardProperty() instanceof Font) {
      getAllStrings(tree.value()).stream()
        .filter(t -> FontFamily.GENERIC_FAMILY_NAMES.contains(t.actualText().toLowerCase()))
        .forEach(t -> addPreciseIssue(t, "Unquote this generic font family name."));
    }
    super.visitPropertyDeclaration(tree);
  }

  private List<StringTree> getAllStrings(ValueTree valueTree) {
    List<StringTree> strings = new ArrayList<>();
    for (Tree tree : valueTree.sanitizedValueElements()) {
      if (tree.is(Tree.Kind.VALUE_COMMA_SEPARATED_LIST)) {
        for (ValueTree element : ((ValueCommaSeparatedListTree) tree).values()) {
          strings.addAll(getAllStringsFromCommaSeparatedListElement(element));
        }
      } else if (tree.is(Tree.Kind.STRING)) {
        strings.add((StringTree) tree);
      }
    }
    return strings;
  }

  private List<StringTree> getAllStringsFromCommaSeparatedListElement(ValueTree valueTree) {
    return valueTree.valueElementsOfType(StringTree.class);
  }

}