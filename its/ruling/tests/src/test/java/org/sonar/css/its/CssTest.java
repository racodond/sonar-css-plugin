/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2017 David RACODON
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
package org.sonar.css.its;

import com.google.common.io.Files;
import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.build.SonarScanner;
import com.sonar.orchestrator.locator.FileLocation;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.sonarsource.analyzer.commons.ProfileGenerator;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

public class CssTest {

  @ClassRule
  public static Orchestrator orchestrator = Orchestrator.builderEnv()
    .addPlugin(FileLocation.byWildcardMavenFilename(new File("../../../sonar-css-plugin/target"), "sonar-css-plugin-*-SNAPSHOT.jar"))
    .setOrchestratorProperty("litsVersion", "0.6")
    .addPlugin("lits")
    .build();

  @Before
  public void setUp() {
    ProfileGenerator.RulesConfiguration rulesConfiguration = new ProfileGenerator.RulesConfiguration();
    Set<String> excludedRules = Collections.emptySet();

    File cssProfile = ProfileGenerator.generateProfile(orchestrator.getServer().getUrl(), "css", "css", rulesConfiguration, excludedRules);
    orchestrator.getServer().restoreProfile(FileLocation.of(cssProfile));

    File lessProfile = ProfileGenerator.generateProfile(orchestrator.getServer().getUrl(), "less", "less", rulesConfiguration, excludedRules);
    orchestrator.getServer().restoreProfile(FileLocation.of(lessProfile));

    File scssProfile = ProfileGenerator.generateProfile(orchestrator.getServer().getUrl(), "scss", "scss", rulesConfiguration, excludedRules);
    orchestrator.getServer().restoreProfile(FileLocation.of(scssProfile));
  }

  @Test
  public void test() throws Exception {
    File litsDifferencesFile = FileLocation.of("target/differences").getFile();
    orchestrator.getServer().provisionProject("project", "project");
    orchestrator.getServer().associateProjectToQualityProfile("project", "css", "rules");
    orchestrator.getServer().associateProjectToQualityProfile("project", "less", "rules");
    orchestrator.getServer().associateProjectToQualityProfile("project", "scss", "rules");
    SonarScanner build = SonarScanner.create(FileLocation.of("../projects").getFile())
      .setProjectKey("project")
      .setSourceDirs("./")
      .setSourceEncoding("UTF-8")
      .setProperty("sonar.import_unknown_files", "true")
      .setProperty("sonar.analysis.mode", "preview")
      .setProperty("sonar.issuesReport.html.enable", "true")
      .setProperty("dump.old", FileLocation.of("src/test/expected").getFile().getAbsolutePath())
      .setProperty("dump.new", FileLocation.of("target/actual").getFile().getAbsolutePath())
      .setProperty("lits.differences", litsDifferencesFile.getAbsolutePath())
      .setProperty("sonar.cpd.skip", "true");
    orchestrator.executeBuild(build);

    assertThat(Files.asCharSource(litsDifferencesFile, StandardCharsets.UTF_8).read()).isEmpty();
  }

}
