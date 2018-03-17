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

import com.google.common.annotations.VisibleForTesting;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.checks.CheckList;
import org.sonar.css.checks.CheckUtils;
import org.sonar.css.checks.Tags;
import org.sonar.css.model.atrule.standard.FontFace;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.standard.FontFamily;
import org.sonar.css.tree.impl.SeparatedList;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.AtRuleTree;
import org.sonar.plugins.css.api.tree.css.DelimiterTree;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.css.PropertyDeclarationTree;
import org.sonar.plugins.css.api.tree.css.StringTree;
import org.sonar.plugins.css.api.tree.css.ValueCommaSeparatedListTree;
import org.sonar.plugins.css.api.tree.css.ValueTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import javax.annotation.Nullable;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Rule(
  key = "font-family-not-ending-with-generic-font-family",
  name = "font-family properties should end with a generic font family",
  priority = Priority.CRITICAL,
  tags = {Tags.BUG})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class FontFamilyNotEndingWithGenericFontFamilyCheck extends DoubleDispatchVisitorCheck {

  private static final String DEFAULT_EXCLUSIONS = "(?i)^(FontAwesome|Glyphicons Halflings|Ionicons|Genericons)$";
  @RuleProperty(
    key = "Exclusions",
    description = "Regular expression of font families to exclude. See "
      + CheckUtils.LINK_TO_JAVA_REGEX_PATTERN_DOC
      + " for detailed regular expression syntax.",
    defaultValue = DEFAULT_EXCLUSIONS)
  private String exclusions = DEFAULT_EXCLUSIONS;


  @Override
  public void visitPropertyDeclaration(PropertyDeclarationTree tree) {
    if (propertyToBeChecked(tree)) {
      Tree lastTree = getLastTree(tree.value());
      if (!isPotentialGenericFamily(lastTree)) {
        addPreciseIssue(tree.value(), "Add a generic font family at the end of the declaration.");
      }
      super.visitPropertyDeclaration(tree);
    }
  }

  @Nullable
  private Tree getLastTree(ValueTree valueTree) {
    List<Tree> sanitizedValueElements = valueTree.sanitizedValueElements();
    if (!sanitizedValueElements.isEmpty()) {
      Tree lastValueTree = sanitizedValueElements.get(sanitizedValueElements.size() - 1);
      if (lastValueTree.is(Tree.Kind.VALUE_COMMA_SEPARATED_LIST)) {
        return getLastTreeOfValueCommaSeparatedListTree((ValueCommaSeparatedListTree) lastValueTree);
      } else {
        return lastValueTree;
      }
    }
    return null;
  }

  @Nullable
  private Tree getLastTreeOfValueCommaSeparatedListTree(ValueCommaSeparatedListTree tree) {
    SeparatedList<ValueTree, DelimiterTree> listOfValues = tree.values();
    List<Tree> lastValue = listOfValues.get(listOfValues.size() - 1).sanitizedValueElements();
    if (!lastValue.isEmpty()) {
      return lastValue.get(lastValue.size() - 1);
    } else {
      return null;
    }
  }

  private boolean isPotentialGenericFamily(@Nullable Tree tree) {
    if (tree == null) {
      return false;
    }

    if (!tree.is(Tree.Kind.IDENTIFIER, Tree.Kind.STRING, Tree.Kind.LESS_VARIABLE, Tree.Kind.SCSS_VARIABLE, Tree.Kind.VARIABLE, Tree.Kind.FUNCTION)) {
      return false;
    }

    if (tree.is(Tree.Kind.IDENTIFIER)) {
      IdentifierTree identifier = (IdentifierTree) tree;
      if (!identifier.isInterpolated()
        && !FontFamily.GENERIC_FAMILY_NAMES.contains(identifier.text().toLowerCase())
        && !StandardProperty.COMMON_VALUES.contains(identifier.text().toLowerCase())
        && !identifier.text().matches(exclusions)) {
        return false;
      }
    }

    if (tree.is(Tree.Kind.STRING) && !((StringTree) tree).actualText().matches(exclusions)) {
      return false;
    }

    return true;
  }

  private boolean propertyToBeChecked(PropertyDeclarationTree tree) {
    if (!(tree.property().standardProperty() instanceof FontFamily)) {
      return false;
    }

    if (tree.parent() != null
      && tree.parent().parent() != null
      && tree.parent().parent() instanceof AtRuleTree
      && ((AtRuleTree) tree.parent().parent()).standardAtRule() instanceof FontFace) {
      return false;
    }

    return true;
  }

  @VisibleForTesting
  void setExclusions(String exclusions) {
    this.exclusions = exclusions;
  }

  @Override
  public void validateParameters() {
    try {
      Pattern.compile(exclusions);
    } catch (PatternSyntaxException exception) {
      throw new IllegalStateException(paramsErrorMessage(), exception);
    }
  }

  private String paramsErrorMessage() {
    return CheckUtils.paramsErrorMessage(
      this.getClass(),
      CheckList.CSS_REPOSITORY_KEY,
      MessageFormat.format("exclusions parameter \"{0}\" is not a valid regular expression.", exclusions));
  }

}
