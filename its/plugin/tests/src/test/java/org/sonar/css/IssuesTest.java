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
package org.sonar.css;

import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.build.SonarScanner;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.sonar.wsclient.issue.Issue;
import org.sonar.wsclient.issue.IssueQuery;

import java.io.File;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class IssuesTest {

  @ClassRule
  public static Orchestrator orchestrator = Tests.ORCHESTRATOR;

  private static final String PROJECT_KEY = "issues";

  @BeforeClass
  public static void init() {
    orchestrator.resetData();

    SonarScanner build = Tests.createSonarScannerBuild()
      .setProjectDir(new File("../projects/issues/"))
      .setProjectKey(PROJECT_KEY)
      .setProjectName(PROJECT_KEY);

    orchestrator.getServer().provisionProject(PROJECT_KEY, PROJECT_KEY);
    Tests.setCssProfile("css-zero-units-only-profile", PROJECT_KEY);
    Tests.setLessProfile("less-zero-units-only-profile", PROJECT_KEY);
    Tests.setScssProfile("scss-zero-units-only-profile", PROJECT_KEY);
    orchestrator.executeBuild(build);
  }

  @Test
  public void issues_for_rule_zero_units() {
    List<Issue> issues = orchestrator.getServer().wsClient().issueClient().find(IssueQuery.create()).list();
    assertThat(issues).hasSize(6);
  }

}
