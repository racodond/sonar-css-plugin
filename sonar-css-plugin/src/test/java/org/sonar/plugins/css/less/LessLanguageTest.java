/*
 * SonarQube CSS / SCSS / Less Analyzer
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
package org.sonar.plugins.css.less;

import org.junit.Test;
import org.sonar.api.config.Settings;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class LessLanguageTest {

  @Test
  public void language_key_and_name() {
    LessLanguage language = new LessLanguage(mock(Settings.class));
    assertThat(language.getKey()).isEqualTo("less");
    assertThat(language.getName()).isEqualTo("Less");
  }

  @Test
  public void default_file_suffix() {
    LessLanguage language = new LessLanguage(mock(Settings.class));
    assertThat(language.getFileSuffixes()).containsOnly("less");
  }

  @Test
  public void custom_file_suffixes() {
    Settings settings = new Settings();
    settings.setProperty("sonar.less.file.suffixes", "less,less3");

    LessLanguage language = new LessLanguage(settings);
    assertThat(language.getFileSuffixes()).containsOnly("less", "less3");
  }

}
