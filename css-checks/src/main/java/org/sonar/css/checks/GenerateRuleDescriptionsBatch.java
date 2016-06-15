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

import java.io.File;

public class GenerateRuleDescriptionsBatch {

  private static final String TEMPLATE_DIRECTORY = "css-checks/src/main/resources/org/sonar/l10n/css/rules/css/template/";
  private static final String TARGET_DIRECTORY = "css-checks/target/classes/org/sonar/l10n/css/rules/css/";

  private GenerateRuleDescriptionsBatch() {
  }

  public static void main(String... args) throws Exception {
    RuleDescriptionsGenerator ruleDescriptionsGenerator = new RuleDescriptionsGenerator();
    File[] files = new File(TEMPLATE_DIRECTORY).listFiles();
    for (File file : files) {
      ruleDescriptionsGenerator.generateHtmlRuleDescription(file.getPath(), TARGET_DIRECTORY + file.getName());
    }
  }

}
