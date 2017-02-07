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
package org.sonar.plugins.css.less;

import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Rule;
import org.sonar.css.checks.CheckList;
import org.sonar.css.checks.less.DeprecatedEscapingFunctionCheck;

import static org.fest.assertions.Assertions.assertThat;

public class LessRulesDefinitionTest {

  @Test
  public void test() {
    LessRulesDefinition rulesDefinition = new LessRulesDefinition();
    RulesDefinition.Context context = new RulesDefinition.Context();
    rulesDefinition.define(context);
    RulesDefinition.Repository repository = context.repository("less");

    assertThat(repository.name()).isEqualTo("SonarQube");
    assertThat(repository.language()).isEqualTo("less");
    assertThat(repository.rules()).hasSize(81);

    RulesDefinition.Rule rule = repository.rule(DeprecatedEscapingFunctionCheck.class.getAnnotation(Rule.class).key());
    assertThat(rule).isNotNull();
    assertThat(rule.name()).isEqualTo(DeprecatedEscapingFunctionCheck.class.getAnnotation(Rule.class).name());
  }

}
