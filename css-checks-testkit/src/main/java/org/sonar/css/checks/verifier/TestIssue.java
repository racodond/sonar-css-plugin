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
package org.sonar.css.checks.verifier;

import java.util.List;

import org.sonar.css.issue.Issue;

class TestIssue implements Issue {

  private String message;
  private int line;
  private Integer effortToFix = null;
  private Integer startColumn = null;
  private Integer endColumn = null;
  private Integer endLine = null;
  private List<Integer> secondaryLines = null;

  public TestIssue(int line) {
    this.line = line;
  }

  public TestIssue line(int line) {
    this.line = line;
    return this;
  }

  public TestIssue message(String message) {
    this.message = message;
    return this;
  }

  public TestIssue startColumn(int startColumn) {
    this.startColumn = startColumn;
    return this;
  }

  public TestIssue endColumn(int endColumn) {
    this.endColumn = endColumn;
    return this;
  }

  public TestIssue effortToFix(int effortToFix) {
    this.effortToFix = effortToFix;
    return this;
  }

  public TestIssue endLine(int endLine) {
    this.endLine = endLine;
    return this;
  }

  public TestIssue secondary(List<Integer> secondaryLines) {
    this.secondaryLines = secondaryLines;
    return this;
  }

  public int line() {
    return line;
  }

  public Integer startColumn() {
    return startColumn;
  }

  public Integer endLine() {
    return endLine;
  }

  public Integer endColumn() {
    return endColumn;
  }

  public String message() {
    return message;
  }

  public Integer effortToFix() {
    return effortToFix;
  }

  public List<Integer> secondaryLines() {
    return secondaryLines;
  }
}
