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

import com.google.common.collect.Lists;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.css.model.property.standard.FontFamily;
import org.sonar.css.tree.impl.css.PropertyDeclarationTreeImpl;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Rule(
  key = "duplicated-font-families",
  name = "font-family should not contain duplicated font family names",
  priority = Priority.MINOR,
  tags = {Tags.PITFALL})
@SqaleConstantRemediation("5min")
@ActivatedByDefault
public class DuplicatedFontFamiliesCheck extends DoubleDispatchVisitorCheck {

  @Override
  public void visitPropertyDeclaration(PropertyDeclarationTree tree) {
    if (tree.property().standardProperty() instanceof FontFamily
      && tree.isValid(this.getContext().getLanguage())) {
      checkForIssues(mapByFontFamilyName(tree.value()));
    }
    super.visitPropertyDeclaration(tree);
  }

  private Map<String, List<Tree>> mapByFontFamilyName(ValueTree valueTree) {
    Map<String, List<Tree>> mapByFontFamilyName = new HashMap<>();
    List<Tree> trees = valueTree.sanitizedValueElements();
    if (trees.get(0).is(Tree.Kind.VALUE_COMMA_SEPARATED_LIST)) {
      for (ValueTree element : ((ValueCommaSeparatedListTree) trees.get(0)).values()) {
        addFontFamilyNameToMap(element, mapByFontFamilyName);
      }
    }
    return mapByFontFamilyName;
  }

  private void addFontFamilyNameToMap(ValueTree value, Map<String, List<Tree>> mapByFontFamilyName) {
    if (!containsScssOrLessElements(value)) {
      if (value.sanitizedValueElements().get(0).is(Tree.Kind.STRING)) {
        StringTree stringTree = (StringTree) value.sanitizedValueElements().get(0);
        if (mapByFontFamilyName.get(stringTree.actualText()) != null) {
          mapByFontFamilyName.get(stringTree.actualText()).add(value);
        } else {
          mapByFontFamilyName.put(stringTree.actualText(), Lists.newArrayList(value));
        }
      } else {
        String fontFamilyName = value.valueElementsOfType(IdentifierTree.class).stream().map(IdentifierTree::text).collect(Collectors.joining(" "));
        if (mapByFontFamilyName.get(fontFamilyName) != null) {
          mapByFontFamilyName.get(fontFamilyName).add(value);
        } else {
          mapByFontFamilyName.put(fontFamilyName, Lists.newArrayList(value));
        }
      }
    }
  }

  private boolean containsScssOrLessElements(ValueTree value) {
    for (Tree tree : value.sanitizedValueElements()) {
      if (PropertyDeclarationTreeImpl.isScssOrLessElement(tree)) {
        return true;
      }
    }
    return false;
  }

  private void checkForIssues(Map<String, List<Tree>> mapByFontFamilyName) {
    for (Map.Entry<String, List<Tree>> entry : mapByFontFamilyName.entrySet()) {
      if (entry.getValue().size() > 1) {
        createIssue(entry);
      }
    }
  }

  private void createIssue(Map.Entry<String, List<Tree>> entry) {
    PreciseIssue issue = addPreciseIssue(entry.getValue().get(0), "Remove this duplicated font family.");
    for (int i = 1; i < entry.getValue().size(); i++) {
      issue.secondary(entry.getValue().get(i), "Duplicated font family");
    }
  }

}
