/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class GenerateRuleDescriptionsBatch {

  private static final String COMMON_TEMPLATE_DIRECTORY = "css-checks/src/main/resources/org/sonar/css/checks/l10n/common/template/";
  private static final String SCSS_TEMPLATE_DIRECTORY = "css-checks/src/main/resources/org/sonar/css/checks/l10n/scss/template/";
  private static final String LESS_TEMPLATE_DIRECTORY = "css-checks/src/main/resources/org/sonar/css/checks/l10n/less/template/";
  private static final String CSS_TEMPLATE_DIRECTORY = "css-checks/src/main/resources/org/sonar/css/checks/l10n/css/template/";

  private static final String COMMON_RESOURCE_DIRECTORY = "css-checks/src/main/resources/org/sonar/css/checks/l10n/common/";
  private static final String CSS_RESSOURCE_DIRECTORY = "css-checks/src/main/resources/org/sonar/css/checks/l10n/css/";
  private static final String SCSS_RESSOURCE_DIRECTORY = "css-checks/src/main/resources/org/sonar/css/checks/l10n/scss/";
  private static final String LESS_RESSOURCE_DIRECTORY = "css-checks/src/main/resources/org/sonar/css/checks/l10n/less/";

  private static final String COMMON_TARGET_DIRECTORY = "css-checks/target/classes/org/sonar/l10n/common/rules/common/";
  private static final String CSS_TARGET_DIRECTORY = "css-checks/target/classes/org/sonar/l10n/css/rules/css/";
  private static final String SCSS_TARGET_DIRECTORY = "css-checks/target/classes/org/sonar/l10n/scss/rules/scss/";
  private static final String LESS_TARGET_DIRECTORY = "css-checks/target/classes/org/sonar/l10n/less/rules/less/";

  private static final RuleDescriptionsGenerator RULE_DESCRIPTIONS_GENERATOR = new RuleDescriptionsGenerator();

  private GenerateRuleDescriptionsBatch() {
  }

  public static void main(String... args) throws Exception {
    if (!new File(COMMON_TARGET_DIRECTORY).mkdirs()) {
      throw new IllegalStateException("Cannot create directory " + COMMON_TARGET_DIRECTORY);
    }
    if (!new File(CSS_TARGET_DIRECTORY).mkdirs()) {
      throw new IllegalStateException("Cannot create directory " + CSS_TARGET_DIRECTORY);
    }
    if (!new File(SCSS_TARGET_DIRECTORY).mkdirs()) {
      throw new IllegalStateException("Cannot create directory " + SCSS_TARGET_DIRECTORY);
    }
    if (!new File(LESS_TARGET_DIRECTORY).mkdirs()) {
      throw new IllegalStateException("Cannot create directory " + LESS_TARGET_DIRECTORY);
    }

    generateRuleDescriptionsFromTemplates(COMMON_TEMPLATE_DIRECTORY, CSS_TARGET_DIRECTORY, "css");
    generateRuleDescriptionsFromTemplates(COMMON_TEMPLATE_DIRECTORY, SCSS_TARGET_DIRECTORY, "scss");
    generateRuleDescriptionsFromTemplates(COMMON_TEMPLATE_DIRECTORY, LESS_TARGET_DIRECTORY, "less");
    generateRuleDescriptionsFromTemplates(SCSS_TEMPLATE_DIRECTORY, SCSS_TARGET_DIRECTORY, "scss");
    generateRuleDescriptionsFromTemplates(LESS_TEMPLATE_DIRECTORY, LESS_TARGET_DIRECTORY, "less");
    generateRuleDescriptionsFromTemplates(CSS_TEMPLATE_DIRECTORY, CSS_TARGET_DIRECTORY, "css");

    FileUtils.copyDirectory(new File(COMMON_RESOURCE_DIRECTORY), new File(CSS_TARGET_DIRECTORY));
    FileUtils.copyDirectory(new File(COMMON_RESOURCE_DIRECTORY), new File(SCSS_TARGET_DIRECTORY));
    FileUtils.copyDirectory(new File(COMMON_RESOURCE_DIRECTORY), new File(LESS_TARGET_DIRECTORY));

    FileUtils.copyDirectory(new File(CSS_RESSOURCE_DIRECTORY), new File(CSS_TARGET_DIRECTORY));
    FileUtils.copyDirectory(new File(SCSS_RESSOURCE_DIRECTORY), new File(SCSS_TARGET_DIRECTORY));
    FileUtils.copyDirectory(new File(LESS_RESSOURCE_DIRECTORY), new File(LESS_TARGET_DIRECTORY));
  }

  private static void generateRuleDescriptionsFromTemplates(String templateDirectoryPath, String targetDirectoryPath, String language) throws IOException {
    File[] files = new File(templateDirectoryPath).listFiles();
    for (File file : files) {
      RULE_DESCRIPTIONS_GENERATOR.generateHtmlRuleDescription(file.getPath(), targetDirectoryPath + file.getName(), language);
    }
  }

}
