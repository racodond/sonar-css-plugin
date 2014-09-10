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
package org.sonar.plugins.css.cpd;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.sonar.api.config.Settings;
import org.sonar.api.scan.filesystem.FileQuery;
import org.sonar.api.scan.filesystem.ModuleFileSystem;
import org.sonar.plugins.css.core.Css;

import java.io.File;
import java.util.Arrays;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CssCpdMappingTest {

  private CssCpdMapping mapping;

  @Before
  public void setup() {
    ModuleFileSystem fileSystem = mock(ModuleFileSystem.class);
    when(fileSystem.files(Mockito.any(FileQuery.class))).thenReturn(Arrays.asList(new File("src/test/resources/org/sonar/plugins/css/cssProject/css/boxSizing.css")));
    mapping = new CssCpdMapping(
      new Css(new Settings()), fileSystem);
  }

  @Test
  public void test() {
    assertThat(mapping.getLanguage().getKey()).isEqualTo(Css.KEY);
    assertThat(mapping.getTokenizer()).isNotNull();
  }

}
