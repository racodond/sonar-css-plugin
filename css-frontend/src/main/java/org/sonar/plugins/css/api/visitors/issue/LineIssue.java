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
package org.sonar.plugins.css.api.visitors.issue;

import com.google.common.base.Preconditions;

import java.io.File;
import javax.annotation.Nullable;

import org.sonar.plugins.css.api.CssCheck;

public class LineIssue implements Issue {

  private final CssCheck check;
  private final File file;
  private Double cost;
  private final String message;
  private final int line;

  public LineIssue(CssCheck check, File file, int line, String message) {
    Preconditions.checkArgument(line > 0);
    this.check = check;
    this.file = file;
    this.message = message;
    this.line = line;
    this.cost = null;
  }

  public String message() {
    return message;
  }

  public int line() {
    return line;
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

}
