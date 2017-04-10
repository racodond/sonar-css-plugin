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

import org.junit.Test;
import org.sonar.api.config.Settings;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ScssLanguageTest {

  @Test
  public void language_key_and_name() {
    ScssLanguage language = new ScssLanguage(mock(Settings.class));
    assertThat(language.getKey()).isEqualTo("scss");
    assertThat(language.getName()).isEqualTo("SCSS");
  }

  @Test
  public void default_file_suffix() {
    ScssLanguage language = new ScssLanguage(mock(Settings.class));
    assertThat(language.getFileSuffixes()).containsOnly("scss");
  }

  @Test
  public void custom_file_suffixes() {
    Settings settings = new Settings();
    settings.setProperty("sonar.scss.file.suffixes", "scss,scss3");

    ScssLanguage language = new ScssLanguage(settings);
    assertThat(language.getFileSuffixes()).containsOnly("scss", "scss3");
  }

}
