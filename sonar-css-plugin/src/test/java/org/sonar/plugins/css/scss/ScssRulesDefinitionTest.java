/*
 * SonarQube CSS / SCSS / Less Analyzer
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
package org.sonar.plugins.css.scss;

import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Rule;
import org.sonar.css.checks.CheckList;
import org.sonar.css.checks.scss.ScssVariableNamingConventionCheck;

import static org.fest.assertions.Assertions.assertThat;

public class ScssRulesDefinitionTest {

  @Test
  public void test() {
    ScssRulesDefinition rulesDefinition = new ScssRulesDefinition();
    RulesDefinition.Context context = new RulesDefinition.Context();
    rulesDefinition.define(context);
    RulesDefinition.Repository repository = context.repository("scss");

    assertThat(repository.name()).isEqualTo("SonarQube");
    assertThat(repository.language()).isEqualTo("scss");
    assertThat(repository.rules()).hasSize(62);

    RulesDefinition.Rule rule = repository.rule(ScssVariableNamingConventionCheck.class.getAnnotation(Rule.class).key());
    assertThat(rule).isNotNull();
    assertThat(rule.name()).isEqualTo(ScssVariableNamingConventionCheck.class.getAnnotation(Rule.class).name());
  }

}
