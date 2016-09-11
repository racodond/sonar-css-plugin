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
package org.sonar.css;

import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.build.SonarScanner;
import com.sonar.orchestrator.locator.FileLocation;

import java.io.File;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  CustomRulesTest.class,
  DuplicationsTest.class,
  MetricsTest.class,
  MinifiedTest.class,
  IssuesTest.class,
})
public class Tests {

  @ClassRule
  public static final Orchestrator ORCHESTRATOR = Orchestrator.builderEnv()
    .addPlugin(FileLocation.byWildcardFilename(new File("../../../sonar-css-plugin/target"), "sonar-css-plugin-*-SNAPSHOT.jar"))
    .addPlugin(FileLocation.byWildcardFilename(new File("../plugins/css-custom-rules-plugin/target"), "css-custom-rules-plugin-*-SNAPSHOT.jar"))
    .restoreProfileAtStartup(FileLocation.ofClasspath("/org/sonar/css/profiles/zero-units-only-profile.xml"))
    .restoreProfileAtStartup(FileLocation.ofClasspath("/org/sonar/css/profiles/empty-profile.xml"))
    .restoreProfileAtStartup(FileLocation.ofClasspath("/org/sonar/css/profiles/css-custom-rules-profile.xml"))
    .build();

  public static SonarScanner createSonarScannerBuild() {
    SonarScanner build = SonarScanner.create();
    build.setProjectVersion("1.0");
    build.setSourceEncoding("UTF-8");
    build.setSourceDirs("src");
    return build;
  }

  public static void setProfile(String profileName, String projectKey) {
    ORCHESTRATOR.getServer().associateProjectToQualityProfile(projectKey, "css", profileName);
  }

}
