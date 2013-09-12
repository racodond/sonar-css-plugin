/*
 * Sonar CSS Plugin
 * Copyright (C) 2013 Tamas Kende
 * kende.tamas@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.css;

import com.google.common.collect.ImmutableList;
import org.sonar.api.Extension;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;
import org.sonar.plugins.css.checks.CssChecksRuleRepository;
import org.sonar.plugins.css.colorizer.CssColorizerFormat;
import org.sonar.plugins.css.core.Css;
import org.sonar.plugins.css.core.CssSourceImporter;
import org.sonar.plugins.css.cpd.CssCpdMapping;

@Properties({
  @Property(
    key = CssPlugin.FILE_SUFFIXES_KEY,
    defaultValue = CssPlugin.FILE_SUFFIXES_DEFVALUE,
    name = "File suffixes",
    description = "Comma-separated list of suffixes for files to analyze. To not filter, leave the list empty.",
    global = true, project = true)
})
public class CssPlugin extends SonarPlugin {

  public static final String FILE_SUFFIXES_KEY = "sonar.css.file.suffixes";
  public static final String FILE_SUFFIXES_DEFVALUE = "css";

  public ImmutableList<Class<? extends Extension>> getExtensions() {
    return ImmutableList.of(
      Css.class,
      CssSourceImporter.class,
     // CssColorizerFormat.class,

      CssCpdMapping.class,

      CssSquidSensor.class,

      CssCommonRulesEngineProvider.class,
      CssChecksRuleRepository.class,
      CssProfile.class);
  }

}
