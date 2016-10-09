/*
 * SonarQube CSS / Less Plugin
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
package org.sonar.plugins.css;

import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.plugins.css.css.CssAnalyzerSensor;
import org.sonar.plugins.css.css.CssLanguage;
import org.sonar.plugins.css.css.CssProfile;
import org.sonar.plugins.css.css.CssRulesDefinition;
import org.sonar.plugins.css.embedded.EmbeddedCssAnalyzerSensor;
import org.sonar.plugins.css.less.LessAnalyzerSensor;
import org.sonar.plugins.css.less.LessLanguage;
import org.sonar.plugins.css.less.LessProfile;
import org.sonar.plugins.css.less.LessRulesDefinition;

@Properties({
  @Property(
    key = Plugin.CSS_FILE_SUFFIXES_KEY,
    category = "CSS",
    defaultValue = Plugin.CSS_FILE_SUFFIXES_DEFAULT_VALUE,
    name = "CSS File Suffixes",
    description = "Comma-separated list of suffixes for CSS files to analyze.",
    global = true, project = true),
  @Property(
    key = Plugin.LESS_FILE_SUFFIXES_KEY,
    category = "Less",
    defaultValue = Plugin.LESS_FILE_SUFFIXES_DEFAULT_VALUE,
    name = "Less File Suffixes",
    description = "Comma-separated list of suffixes for Less files to analyze.",
    global = true, project = true),
  @Property(
    key = Plugin.EMBEDDED_CSS_FILE_SUFFIXES_KEY,
    category = "CSS",
    defaultValue = Plugin.EMBEDDED_CSS_FILE_SUFFIXES_DEFAULT_VALUE,
    name = "Embedded CSS File Suffixes",
    description = "Comma-separated list of suffixes for files containing embedded CSS to analyze.",
    global = true, project = true),
  @Property(
    key = "sonar.cpd.css.minimumTokens",
    defaultValue = "70",
    name = "Minimum number of tokens to start detecting duplication",
    description = "Set a value lower than the default one set in SonarQube (100) because CSS is less verbose than other languages.",
    global = false, project = false),
})
public class Plugin implements org.sonar.api.Plugin {

  public static final String CSS_FILE_SUFFIXES_KEY = "sonar.css.file.suffixes";
  public static final String CSS_FILE_SUFFIXES_DEFAULT_VALUE = "css";

  public static final String EMBEDDED_CSS_FILE_SUFFIXES_KEY = "sonar.css.embedded.file.suffixes";
  public static final String EMBEDDED_CSS_FILE_SUFFIXES_DEFAULT_VALUE = "html,xhtml";

  public static final String LESS_FILE_SUFFIXES_KEY = "sonar.less.file.suffixes";
  public static final String LESS_FILE_SUFFIXES_DEFAULT_VALUE = "less";

  @Override
  public void define(Context context) {
    context.addExtensions(
      CssLanguage.class,
      LessLanguage.class,
      CssAnalyzerSensor.class,
      EmbeddedCssAnalyzerSensor.class,
      LessAnalyzerSensor.class,
      CssProfile.class,
      LessProfile.class,
      CssRulesDefinition.class,
      LessRulesDefinition.class);
  }

}
