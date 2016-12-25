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
package org.sonar.plugins.css.api;

import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

import static org.fest.assertions.Assertions.assertThat;

public class CustomLessRulesDefinitionTest {

  private static final String REPOSITORY_NAME = "Custom Rule Repository";
  private static final String REPOSITORY_KEY = "CustomRuleRepository";

  private static final String RULE_NAME = "This is my custom rule";
  private static final String RULE_KEY = "MyCustomRule";

  @Test
  public void test() {
    MyCustomLessRulesDefinition rulesDefinition = new MyCustomLessRulesDefinition();
    RulesDefinition.Context context = new RulesDefinition.Context();
    rulesDefinition.define(context);
    RulesDefinition.Repository repository = context.repository(REPOSITORY_KEY);

    assertThat(repository.name()).isEqualTo(REPOSITORY_NAME);
    assertThat(repository.language()).isEqualTo("less");
    assertThat(repository.rules()).hasSize(1);

    RulesDefinition.Rule customRule = repository.rule(RULE_KEY);
    assertThat(customRule).isNotNull();
    assertThat(customRule.key()).isEqualTo(RULE_KEY);
    assertThat(customRule.name()).isEqualTo(RULE_NAME);

    RulesDefinition.Param param = repository.rules().get(0).params().get(0);
    assertThat(param.key()).isEqualTo("customParam");
    assertThat(param.description()).isEqualTo("Custom parameter");
    assertThat(param.defaultValue()).isEqualTo("Default value");
  }

  @Rule(
    key = RULE_KEY,
    name = RULE_NAME,
    description = "desc",
    tags = {"bug"})
  public class MyCustomRule extends DoubleDispatchVisitor {
    @RuleProperty(
      key = "customParam",
      description = "Custom parameter",
      defaultValue = "Default value")
    public String customParam = "value";
  }

  public static class MyCustomLessRulesDefinition extends CustomLessRulesDefinition {

    @Override
    public String repositoryName() {
      System.out.println(REPOSITORY_NAME);
      return REPOSITORY_NAME;
    }

    @Override
    public String repositoryKey() {
      return REPOSITORY_KEY;
    }

    @Override
    public Class[] checkClasses() {
      return new Class[] {CustomLessRulesDefinitionTest.MyCustomRule.class};
    }
  }

}
