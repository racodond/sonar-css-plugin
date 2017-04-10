/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.checks.CheckList;
import org.sonar.css.checks.CheckUtils;
import org.sonar.css.checks.Tags;
import org.sonar.css.model.property.standard.Src;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*
 * https://css-tricks.com/snippets/css/using-font-face/
 * http://blog.fontspring.com/2011/02/further-hardening-of-the-bulletproof-syntax/
 */
@Rule(
  key = "font-face-browser-compatibility",
  name = "\"@font-face\" rule should be made compatible with the required browsers",
  priority = Priority.MAJOR,
  tags = {Tags.BROWSER_COMPATIBILITY})
@SqaleConstantRemediation("20min")
public class FontFaceBrowserCompatibilityCheck extends DoubleDispatchVisitorCheck {

  private static final String BASIC_LEVEL = "basic";
  private static final String DEEP_LEVEL = "deep";
  private static final String DEEPEST_LEVEL = "deepest";

  private static final ImmutableMap<String, ImmutableList<String>> FORMAT = ImmutableMap.of(
    BASIC_LEVEL, ImmutableList.of("woff2", "woff"),
    DEEP_LEVEL, ImmutableList.of("woff2", "woff", "ttf"),
    DEEPEST_LEVEL, ImmutableList.of("eot", "woff2", "woff", "ttf", "svg"));

  private static final Pattern EOT_PATTERN = Pattern.compile(".+\\.eot.*");

  @RuleProperty(
    key = "browserSupportLevel",
    description = "Allowed values: 'basic', 'deep', 'deepest'",
    defaultValue = "" + BASIC_LEVEL)
  private String browserSupportLevel = BASIC_LEVEL;

  @Override
  public void visitAtRule(AtRuleTree tree) {
    if (!isFontFaceRuleToBeChecked(tree)) {
      return;
    }

    List<PropertyDeclarationTree> declarations = tree.block().propertyDeclarations();

    if (DEEPEST_LEVEL.equals(browserSupportLevel)) {
      if (getSecondToLastSrcPropertyDeclaration(declarations) == null) {
        addPreciseIssue(tree.atKeyword(), "Add an \"src\" property setting the URL for the \".eot\" font file (to support IE9 Compatibility Modes).");
      } else {
        checkSecondToLastSrcPropertyDeclaration(getSecondToLastSrcPropertyDeclaration(declarations));
      }
    }
    checkLastSrcProperty(getLastSrcPropertyDeclaration(declarations));

    super.visitAtRule(tree);
  }

  @VisibleForTesting
  void setBrowserSupportLevel(String browserSupportLevel) {
    this.browserSupportLevel = browserSupportLevel;
  }

  private boolean isFontFaceRuleToBeChecked(AtRuleTree tree) {
    return "font-face".equalsIgnoreCase(tree.atKeyword().keyword().text())
      && tree.block() != null
      && definesScrPropertyWithUrl(tree.block().propertyDeclarations());
  }

  private boolean definesScrPropertyWithUrl(List<PropertyDeclarationTree> declarations) {
    for (PropertyDeclarationTree declaration : declarations)
      if (declaration.property().standardProperty() instanceof Src
        && !getUris(declaration).isEmpty()) {
        return true;
      }
    return false;
  }

  private PropertyDeclarationTree getSecondToLastSrcPropertyDeclaration(List<PropertyDeclarationTree> declarations) {
    PropertyDeclarationTree srcSecondToLastPropertyDeclaration = null;
    PropertyDeclarationTree srcLastPropertyDeclaration = null;

    for (PropertyDeclarationTree declaration : declarations) {
      if (declaration.property().standardProperty() instanceof Src) {
        srcSecondToLastPropertyDeclaration = srcLastPropertyDeclaration;
        srcLastPropertyDeclaration = declaration;
      }
    }
    return srcSecondToLastPropertyDeclaration;
  }

  private PropertyDeclarationTree getLastSrcPropertyDeclaration(List<PropertyDeclarationTree> declarations) {
    PropertyDeclarationTree srcDeclarationNode = null;
    for (PropertyDeclarationTree declaration : declarations) {
      if (declaration.property().standardProperty() instanceof Src) {
        srcDeclarationNode = declaration;
      }
    }
    return srcDeclarationNode;
  }

  private void checkSecondToLastSrcPropertyDeclaration(PropertyDeclarationTree declaration) {
    PropertyTree property = declaration.property();
    ValueTree value = declaration.value();

    if (value.valueElementsOfType(UriTree.class).size() != 1) {
      addPreciseIssue(
        property,
        "Define one single URL, the URL for the \".eot\" font file in this \"src\" property (to support IE9 Compatibility Modes).");

    } else {
      String font = "";
      Optional<UriTree> uri = value.firstValueElementOfType(UriTree.class);
      if (uri.isPresent() && uri.get().uriContent() != null) {
        font = uri.get().uriContent().text();
      }

      if (!EOT_PATTERN.matcher(font).matches()) {
        addPreciseIssue(
          property,
          "Set the URL for the \".eot\" file in this \"src\" property (to support IE9 Compatibility Modes).");

      } else if (!value.valueElementsOfType(FunctionTree.class).isEmpty()) {
        addPreciseIssue(
          property,
          "Remove additional functions from this \"src\" property (to support IE9 Compatibility Modes).");
      }
    }
  }

  private void checkLastSrcProperty(PropertyDeclarationTree declaration) {
    List<UriTree> uris = getUris(declaration);

    if (uris.isEmpty()) {
      addPreciseIssue(declaration.property(), "URL for \"" + FORMAT.get(browserSupportLevel).get(0) + "\" format is expected.");
      return;
    }

    if (BASIC_LEVEL.equals(browserSupportLevel)) {
      int j = 0;
      if (EOT_PATTERN.matcher(uris.get(0).uriContent().text()).matches()) {
        j++;
      }
      for (int i = 0; i < FORMAT.get(BASIC_LEVEL).size(); i++, j++) {
        if (j < uris.size()) {
          if (!Pattern.compile(".*\\." + FORMAT.get(BASIC_LEVEL).get(i) + ".*")
            .matcher(uris.get(j).uriContent().text()).matches()) {
            addPreciseIssue(uris.get(j), "URL for \"" + FORMAT.get(BASIC_LEVEL).get(i) + "\" format is expected.");
            break;
          }
        } else {
          addPreciseIssue(declaration.property(), "URL for \"" + FORMAT.get(BASIC_LEVEL).get(i) + "\" format is expected.");
          break;
        }
      }
    } else if (DEEP_LEVEL.equals(browserSupportLevel)) {
      int j = 0;
      if (EOT_PATTERN.matcher(uris.get(0).uriContent().text()).matches()) {
        j++;
      }
      for (int i = 0; i < FORMAT.get(DEEP_LEVEL).size(); i++, j++) {
        if (j < uris.size()) {
          if (!Pattern.compile(".*\\." + FORMAT.get(DEEP_LEVEL).get(i) + ".*")
            .matcher(uris.get(j).uriContent().text()).matches()) {
            addPreciseIssue(uris.get(j), "URL for \"" + FORMAT.get(DEEP_LEVEL).get(i) + "\" format is expected.");
            break;
          }
        } else {
          addPreciseIssue(declaration.property(), "URL for \"" + FORMAT.get(DEEP_LEVEL).get(i) + "\" format is expected.");
          break;
        }
      }
    } else if (DEEPEST_LEVEL.equals(browserSupportLevel)) {
      for (int i = 0; i < FORMAT.get(DEEPEST_LEVEL).size(); i++) {
        if (i < uris.size()) {
          if (!Pattern.compile(".*\\." + FORMAT.get(DEEPEST_LEVEL).get(i) + ".*")
            .matcher(uris.get(i).uriContent().text()).matches()) {
            addPreciseIssue(uris.get(i), "URL for \"" + FORMAT.get(DEEPEST_LEVEL).get(i) + "\" format is expected.");
            break;
          }
        } else {
          addPreciseIssue(declaration.property(), "URL for \"" + FORMAT.get(DEEPEST_LEVEL).get(i) + "\" format is expected.");
          break;
        }
      }
    }
  }

  private List<UriTree> getUris(PropertyDeclarationTree declaration) {
    List<UriTree> uris = new ArrayList<>();
    if (declaration.value().valueElements().get(0).is(Tree.Kind.VALUE_COMMA_SEPARATED_LIST)) {
      for (ValueTree v : ((ValueCommaSeparatedListTree) declaration.value().valueElements().get(0)).values()) {
        uris.addAll(v.valueElementsOfType(UriTree.class).stream()
          .filter(t -> t.uriContent() != null && !t.uriContent().text().isEmpty())
          .collect(Collectors.toList()));
      }
    } else {
      uris = declaration.value().valueElementsOfType(UriTree.class).stream()
        .filter(t -> t.uriContent() != null && !t.uriContent().text().isEmpty())
        .collect(Collectors.toList());
    }
    return uris;
  }

  @Override
  public void validateParameters() {
    if (!BASIC_LEVEL.equals(browserSupportLevel) && !DEEP_LEVEL.equals(browserSupportLevel) && !DEEPEST_LEVEL.equals(browserSupportLevel)) {
      throw new IllegalStateException(paramsErrorMessage());
    }
  }

  private String paramsErrorMessage() {
    return CheckUtils.paramsErrorMessage(
      this.getClass(),
      CheckList.CSS_REPOSITORY_KEY,
      "parameter value is not valid.\nActual: '" + browserSupportLevel + "'\nExpected: 'basic' or 'deep' or 'deepest'");
  }

}
