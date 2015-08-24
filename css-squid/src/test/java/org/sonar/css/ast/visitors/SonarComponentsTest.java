/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende and David RACODON
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
package org.sonar.css.ast.visitors;

import org.junit.Test;
import org.mockito.Mockito;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.source.Highlightable;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SonarComponentsTest {

  @Test
  public void inputFileFor() {
    ResourcePerspectives resourcePerspectives = mock(ResourcePerspectives.class);
    InputFile inputFile = mock(InputFile.class);
    File file = mock(File.class);
    FileSystem fs = mock(FileSystem.class);
    when(fs.predicates()).thenReturn(mock(FilePredicates.class));
    when(fs.inputFile(Mockito.any(FilePredicate.class))).thenReturn(inputFile);

    SonarComponents sonarComponents = new SonarComponents(resourcePerspectives, fs);

    assertThat(sonarComponents.inputFileFor(file)).isSameAs(inputFile);
  }

  @Test
  public void highlightableFor() {
    ResourcePerspectives resourcePerspectives = mock(ResourcePerspectives.class);
    InputFile inputFile = mock(InputFile.class);

    SonarComponents sonarComponents = new SonarComponents(resourcePerspectives, mock(FileSystem.class));

    sonarComponents.highlightableFor(inputFile);
    verify(resourcePerspectives).as(Highlightable.class, inputFile);
  }

  @Test
  public void getResourcePerspectives() {
    ResourcePerspectives resourcePerspectives = mock(ResourcePerspectives.class);
    SonarComponents sonarComponents = new SonarComponents(resourcePerspectives, mock(FileSystem.class));
    assertThat(sonarComponents.getResourcePerspectives()).isSameAs(resourcePerspectives);
  }

}
