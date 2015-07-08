/*
 * SonarQube CSS Plugin
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
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.PropertyType;
import org.sonar.api.SonarPlugin;
import org.sonar.css.ast.visitors.SonarComponents;
import org.sonar.plugins.css.core.Css;
import org.sonar.plugins.css.cpd.CssCpdMapping;

@Properties({
  @Property(
    key = CssPlugin.FILE_SUFFIXES_KEY,
    defaultValue = CssPlugin.FILE_SUFFIXES_DEFVALUE,
    name = "File Suffixes",
    description = "Comma-separated list of suffixes for files to analyze. To not filter, leave the list empty.",
    global = true, project = true),
  @Property(
    key = CssPlugin.COMPUTE_COMPLEXITY,
    defaultValue = CssPlugin.COMPUTE_COMPLEXITY_DEFAULT_VALUE + "",
    name = "Compute Complexity",
    description = "Set to 'true' to compute complexity, set to 'false' otherwise.",
    global = true, project = true, type = PropertyType.BOOLEAN)
})
public class CssPlugin extends SonarPlugin {

  public static final String FILE_SUFFIXES_KEY = "sonar.css.file.suffixes";
  public static final String FILE_SUFFIXES_DEFVALUE = "css";

  public static final String COMPUTE_COMPLEXITY = "sonar.css.complexity.compute";
  public static final boolean COMPUTE_COMPLEXITY_DEFAULT_VALUE = true;

  @Override
  public ImmutableList getExtensions() {
    return ImmutableList.of(
      Css.class,

      SonarComponents.class,
      CssSquidSensor.class,

      CssCpdMapping.class,

      CssCommonRulesEngine.class,
      CssCommonRulesDecorator.class,
      CssProfile.class,
      CssRulesDefinition.class);
  }

}
