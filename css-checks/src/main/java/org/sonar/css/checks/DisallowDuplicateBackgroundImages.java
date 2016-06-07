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

import com.sonar.sslr.api.AstNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.CssCheck;
import org.sonar.css.issue.PreciseIssue;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

/**
 * https://github.com/stubbornella/csslint/wiki/Disallow-duplicate-background-images
 */
@Rule(
  key = "duplicate-background-images",
  name = "Duplicated background images should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.DESIGN, Tags.PERFORMANCE})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class DisallowDuplicateBackgroundImages extends CssCheck {

  Map<String, List<AstNode>> urls;

  @Override
  public void init() {
    subscribeTo(CssGrammar.PROPERTY);
  }

  @Override
  public void visitFile(AstNode astNode) {
    urls = new HashMap<>();
  }

  @Override
  public void visitNode(AstNode propertyNode) {
    if (propertyNode.getTokenValue().startsWith("background")) {
      for (AstNode uriNode : propertyNode.getParent().getFirstChild(CssGrammar.VALUE).getChildren(CssGrammar.URI)) {
        String url = CssChecksUtil.getStringValue(uriNode.getFirstChild(CssGrammar._URI_CONTENT)).replaceAll("['\"]", "");
        if (urls.containsKey(url)) {
          urls.get(url).add(uriNode);
        } else {
          urls.put(url, new ArrayList<AstNode>());
          urls.get(url).add(uriNode);
        }
      }
    }
  }

  @Override
  public void leaveFile(AstNode astNode) {
    for (Map.Entry<String, List<AstNode>> url : urls.entrySet()) {
      if (url.getValue().size() > 1) {
        PreciseIssue issue = addIssue(this, "Keep only one definition of those duplicated background images.", url.getValue().get(0));
        for (int i = 1; i < url.getValue().size(); i++) {
          issue.addSecondaryLocation("Duplicated background image", url.getValue().get(i));
        }
      }
    }
  }

}
