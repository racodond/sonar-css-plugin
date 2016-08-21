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
package org.sonar.css.checks.verifier;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.sonar.css.parser.CssParserBuilder;
import org.sonar.css.tree.impl.CssTree;
import org.sonar.css.visitors.CssVisitorContext;
import org.sonar.plugins.css.api.CssCheck;
import org.sonar.plugins.css.api.visitors.issue.FileIssue;
import org.sonar.plugins.css.api.visitors.issue.Issue;
import org.sonar.plugins.css.api.visitors.issue.LineIssue;
import org.sonar.plugins.css.api.visitors.issue.PreciseIssue;
import org.sonar.squidbridge.api.CheckMessage;

class TreeCheckTest {

  private TreeCheckTest() {
  }

  public static Collection<CheckMessage> getIssues(String relativePath, CssCheck check, Charset charset) {
    File file = new File(relativePath);

    CssTree CssTree = (CssTree) CssParserBuilder.createParser(charset).parse(file);
    CssVisitorContext context = new CssVisitorContext(CssTree, file);
    List<Issue> issues = check.scanFile(context);

    return getCheckMessages(issues);
  }

  private static Collection<CheckMessage> getCheckMessages(List<Issue> issues) {
    List<CheckMessage> checkMessages = new ArrayList<>();
    for (Issue issue : issues) {
      CheckMessage checkMessage;
      if (issue instanceof FileIssue) {
        FileIssue fileIssue = (FileIssue) issue;
        checkMessage = new CheckMessage(fileIssue.check(), fileIssue.message());

      } else if (issue instanceof LineIssue) {
        LineIssue lineIssue = (LineIssue) issue;
        checkMessage = new CheckMessage(lineIssue.check(), lineIssue.message());
        checkMessage.setLine(lineIssue.line());

      } else {
        PreciseIssue preciseIssue = (PreciseIssue) issue;
        checkMessage = new CheckMessage(preciseIssue.check(), preciseIssue.primaryLocation().message());
        checkMessage.setLine(preciseIssue.primaryLocation().startLine());
      }

      if (issue.cost() != null) {
        checkMessage.setCost(issue.cost());
      }

      checkMessages.add(checkMessage);
    }

    return checkMessages;
  }

}
