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
package org.sonar.css.ast.visitors;

import org.sonar.api.BatchExtension;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.source.Highlightable;

import java.io.File;

public class SonarComponents implements BatchExtension {

  private final ResourcePerspectives resourcePerspectives;
  private final Project project;

  public SonarComponents(ResourcePerspectives resourcePerspectives, Project project) {
    this.resourcePerspectives = resourcePerspectives;
    this.project = project;
  }

  public Resource resourceFromIOFile(File file) {
    return org.sonar.api.resources.File.fromIOFile(file, project);
  }

  public Highlightable highlightableFor(File file) {
    return resourcePerspectives.as(Highlightable.class, resourceFromIOFile(file));
  }

  public ResourcePerspectives getResourcePerspectives() {
    return resourcePerspectives;
  }

}
