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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.standard.Background;
import org.sonar.css.model.property.standard.BackgroundImage;
import org.sonar.plugins.css.api.tree.css.PropertyDeclarationTree;
import org.sonar.plugins.css.api.tree.css.StyleSheetTree;
import org.sonar.plugins.css.api.tree.css.UriTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.plugins.css.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "duplicate-background-images",
  name = "Duplicated background images should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.DESIGN, Tags.PERFORMANCE})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class DuplicateBackgroundImagesCheck extends DoubleDispatchVisitorCheck {

  Map<String, List<UriTree>> urls = new HashMap<>();

  @Override
  public void visitStyleSheet(StyleSheetTree tree) {
    urls.clear();
    super.visitStyleSheet(tree);
    addIssues();
  }

  @Override
  public void visitPropertyDeclaration(PropertyDeclarationTree tree) {
    StandardProperty standardProperty = tree.property().standardProperty();
    if (standardProperty instanceof Background || standardProperty instanceof BackgroundImage) {
      tree.value().valueElementsOfType(UriTree.class).stream()
        .filter(t -> t.uriContent() != null && !t.uriContent().text().isEmpty())
        .forEach(t -> {
          String url = t.uriContent().text();
          if (urls.containsKey(url)) {
            urls.get(url).add(t);
          } else {
            urls.put(url, Lists.newArrayList(t));
          }
        });
    }
    super.visitPropertyDeclaration(tree);
  }

  private void addIssues() {
    for (Map.Entry<String, List<UriTree>> url : urls.entrySet()) {
      if (url.getValue().size() > 1) {
        PreciseIssue issue = addPreciseIssue(url.getValue().get(0), "Keep only one definition of those duplicated background images.");
        for (int i = 1; i < url.getValue().size(); i++) {
          issue.secondary(url.getValue().get(i), "Duplicated background image");
        }
      }
    }
  }

}
