/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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
package org.sonar.css.visitors;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.sonar.plugins.css.api.CssCheck;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.issue.*;

public class Issues {

  private List<Issue> issueList;
  private CssCheck check;

  public Issues(CssCheck check) {
    this.check = check;
    this.issueList = new ArrayList<>();
  }

  public PreciseIssue addPreciseIssue(File file, Tree tree, String message) {
    PreciseIssue issue = new PreciseIssue(check, new IssueLocation(file, tree, message));
    issueList.add(issue);
    return issue;
  }

  public FileIssue addFileIssue(File file, String message) {
    FileIssue issue = new FileIssue(check, file, message);
    issueList.add(issue);
    return issue;
  }

  public LineIssue addLineIssue(File file, int line, String message) {
    LineIssue issue = new LineIssue(check, file, line, message);
    issueList.add(issue);
    return issue;
  }

  public List<Issue> getList() {
    return issueList;
  }

  public void reset() {
    issueList.clear();
  }

}
