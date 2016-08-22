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
package org.sonar.css.checks;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.model.atrule.standard.FontFace;
import org.sonar.css.model.property.standard.Src;
import org.sonar.plugins.css.api.tree.AtRuleTree;
import org.sonar.plugins.css.api.tree.DeclarationsTree;
import org.sonar.plugins.css.api.tree.UriTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

/**
 * See http://bramstein.com/writing/web-font-anti-patterns-inlining.html
 */
@Rule(
  key = "inlining-font-files",
  name = "Font files inlining should not be used",
  priority = Priority.MAJOR,
  tags = {Tags.PERFORMANCE})
@SqaleConstantRemediation("1h")
public class InliningFontFileCheck extends DoubleDispatchVisitorCheck {

  private static final Pattern BASE64_PATTERN = Pattern.compile(".*base64.*");

  @Override
  public void visitAtRule(AtRuleTree tree) {
    if (tree.standardAtRule() instanceof FontFace
      && tree.block() != null
      && tree.block().declarations() != null) {

      getAllUriTrees(tree.block().declarations())
        .stream()
        .forEach(this::checkUriTreeForInliningFont);
    }
    super.visitAtRule(tree);
  }

  private List<UriTree> getAllUriTrees(DeclarationsTree declarations) {
    List<UriTree> uris = new ArrayList<>();

    declarations.propertyDeclarations().stream()
      .filter(d -> d.property().standardProperty() instanceof Src)
      .forEach(d -> uris.addAll(
        d.value().valueElementsOfType(UriTree.class).stream()
          .collect(Collectors.toList())));

    return uris;
  }

  private void checkUriTreeForInliningFont(UriTree tree) {
    if (tree.uriContent().ident() != null && BASE64_PATTERN.matcher(tree.uriContent().ident().text()).matches()) {
      addPreciseIssue(tree.uriContent(), "Remove this inline font file.");
    }
  }

}
