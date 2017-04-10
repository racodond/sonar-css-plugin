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
package org.sonar.plugins.css.scss;

import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.AbstractLanguage;
import org.sonar.plugins.css.Plugin;

public class ScssLanguage extends AbstractLanguage {

  public static final String KEY = "scss";
  public static final String NAME = "SCSS";

  private final Settings settings;

  public ScssLanguage(Settings settings) {
    super(KEY, NAME);
    this.settings = settings;
  }

  @Override
  public String[] getFileSuffixes() {
    String[] suffixes = settings.getStringArray(Plugin.SCSS_FILE_SUFFIXES_KEY);
    if (suffixes == null || suffixes.length == 0) {
      suffixes = StringUtils.split(Plugin.SCSS_FILE_SUFFIXES_DEFAULT_VALUE, ",");
    }
    return suffixes;
  }

}
