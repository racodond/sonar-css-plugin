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
package org.sonar.plugins.css.api.visitors.issue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import org.sonar.plugins.css.api.CssCheck;
import org.sonar.plugins.css.api.tree.Tree;

public class FileIssue implements Issue {

  private final CssCheck check;
  private final File file;
  private Double cost;
  private final String message;
  private final List<IssueLocation> secondaryLocations;

  public FileIssue(CssCheck check, File file, String message) {
    this.check = check;
    this.file = file;
    this.message = message;
    this.secondaryLocations = new ArrayList<>();
    this.cost = null;
  }

  public String message() {
    return message;
  }

  @Override
  public CssCheck check() {
    return check;
  }

  public File file() {
    return file;
  }

  @Nullable
  @Override
  public Double cost() {
    return cost;
  }

  @Override
  public Issue cost(double cost) {
    this.cost = cost;
    return this;
  }

  public List<IssueLocation> secondaryLocations() {
    return secondaryLocations;
  }

  public FileIssue secondary(Tree tree, String message) {
    secondaryLocations.add(new IssueLocation(file, tree, message));
    return this;
  }

  public FileIssue secondary(File file, Tree tree, String message) {
    secondaryLocations.add(new IssueLocation(file, tree, message));
    return this;
  }

}
