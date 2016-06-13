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
package org.sonar.css.issue;

import com.sonar.sslr.api.AstNode;

public class PreciseIssueLocation {

  private final String message;
  private final int startLine;
  private final int startColumn;
  private final int endLine;
  private final int endColumn;

  public PreciseIssueLocation(String message, AstNode astNode, boolean bomOffset) {
    this.message = message;
    this.startLine = astNode.getToken().getLine();
    this.endLine = astNode.getLastToken().getLine();

    if (bomOffset && startLine == 1) {
      this.startColumn = astNode.getToken().getColumn() - 1;
    } else {
      this.startColumn = astNode.getToken().getColumn();
    }

    if (bomOffset && endLine == 1) {
      this.endColumn = astNode.getLastToken().getColumn() + astNode.getLastToken().getValue().length() - 1;
    } else {
      this.endColumn = astNode.getLastToken().getColumn() + astNode.getLastToken().getValue().length();
    }
  }

  public PreciseIssueLocation(String message, int line) {
    this.message = message;
    this.startLine = line;
    this.startColumn = 0;
    this.endLine = 0;
    this.endColumn = 0;
  }

  public PreciseIssueLocation(String message) {
    this.message = message;
    this.startLine = 0;
    this.startColumn = 0;
    this.endLine = 0;
    this.endColumn = 0;
  }

  public String getMessage() {
    return message;
  }

  public int getStartLine() {
    return startLine;
  }

  public int getStartColumn() {
    return startColumn;
  }

  public int getEndLine() {
    return endLine;
  }

  public int getEndColumn() {
    return endColumn;
  }

}
