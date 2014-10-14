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
package org.sonar.css.ast.visitors;

import org.junit.Test;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.source.Highlightable;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SonarComponentsTest {

  @Test
  public void highlightableFor() {

    ResourcePerspectives resourcePerspectives = mock(ResourcePerspectives.class);
    File file = mock(File.class);
    final Resource resource = mock(Resource.class);

    SonarComponents sonarComponents = new SonarComponents(resourcePerspectives, mock(Project.class)) {

      @Override
      public Resource resourceFromIOFile(File file) {
        return resource;
      }

    };

    sonarComponents.highlightableFor(file);
    verify(resourcePerspectives).as(Highlightable.class, resource);
  }

  @Test
  public void getResourcePerspectives() {
    ResourcePerspectives resourcePerspectives = mock(ResourcePerspectives.class);
    SonarComponents sonarComponents = new SonarComponents(resourcePerspectives, mock(Project.class));
    assertThat(sonarComponents.getResourcePerspectives()).isSameAs(resourcePerspectives);
  }

}
