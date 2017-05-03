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
package org.sonar.css.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.css.api.CssCheck;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.RulesetTree;

public final class CheckUtils {

  public static final String LINK_TO_JAVA_REGEX_PATTERN_DOC = "http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html";

  private CheckUtils() {
  }

  public static String paramsErrorMessage(Class<? extends CssCheck> clazz, String repository, String message) {
    return "Check " + repository + ":" + clazz.getAnnotation(Rule.class).key()
      + " (" + clazz.getAnnotation(Rule.class).name() + "): "
      + message;
  }

  public static Tree rulesetIssueLocation(RulesetTree tree) {
    return tree.selectors() != null ? tree.selectors() : tree.block().openCurlyBrace();
  }

}
