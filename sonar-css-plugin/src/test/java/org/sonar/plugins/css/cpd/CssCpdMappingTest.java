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
package org.sonar.plugins.css.cpd;

import com.google.common.base.Charsets;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.plugins.css.CssLanguage;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CssCpdMappingTest {

  private File file;
  private DefaultInputFile inputFile;

  @Rule
  public final TemporaryFolder tempFolder = new TemporaryFolder();

  @Test
  public void test() throws IOException {
    DefaultFileSystem fileSystem = new DefaultFileSystem(tempFolder.getRoot());
    fileSystem.setEncoding(Charsets.UTF_8);
    file = tempFolder.newFile();
    inputFile = new DefaultInputFile("moduleKey", file.getName())
      .setLanguage(CssLanguage.KEY)
      .setType(InputFile.Type.MAIN);
    fileSystem.add(inputFile);

    CssLanguage cssLanguage = mock(CssLanguage.class);

    CssCpdMapping mapping = new CssCpdMapping(cssLanguage, fileSystem);

    assertThat(mapping.getLanguage()).isSameAs(cssLanguage);
    assertThat(mapping.getTokenizer()).isInstanceOf(CssTokenizer.class);
  }

}
