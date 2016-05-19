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
package org.sonar.css.checks;

import java.io.File;

import org.junit.Test;
import org.sonar.css.CssAstScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

public class ExperimentalAtRuleUsageCheckTest {

  @Test
  public void should_contain_some_experimental_at_rules_and_raise_some_issues() {
    SourceFile file = CssAstScanner.scanSingleFile(
      new File("src/test/resources/checks/experimentalAtRuleUsage.css"),
      new ExperimentalAtRuleUsageCheck());

    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().atLine(1).withMessage("Remove the usage of this experimental \"vendoratrule\" @-rule.")
      .next().atLine(3).withMessage("Remove the usage of this experimental \"keyframes\" @-rule.")
      .next().atLine(5).withMessage("Remove the usage of this experimental \"counter-style\" @-rule.")
      .next().atLine(11).withMessage("Remove the usage of this experimental \"custom-media\" @-rule.")
      .next().atLine(13).withMessage("Remove the usage of this experimental \"document\" @-rule.")
      .next().atLine(24).withMessage("Remove the usage of this experimental \"font-feature-values\" @-rule.")
      .next().atLine(25).withMessage("Remove the usage of this experimental \"swash\" @-rule.")
      .next().atLine(28).withMessage("Remove the usage of this experimental \"ornaments\" @-rule.")
      .next().atLine(31).withMessage("Remove the usage of this experimental \"annotation\" @-rule.")
      .next().atLine(34).withMessage("Remove the usage of this experimental \"stylistic\" @-rule.")
      .next().atLine(37).withMessage("Remove the usage of this experimental \"styleset\" @-rule.")
      .next().atLine(40).withMessage("Remove the usage of this experimental \"character-variant\" @-rule.")
      .next().atLine(45).withMessage("Remove the usage of this experimental \"keyframes\" @-rule.")
      .next().atLine(47).withMessage("Remove the usage of this experimental \"viewport\" @-rule.")
      .noMore();
  }

}
