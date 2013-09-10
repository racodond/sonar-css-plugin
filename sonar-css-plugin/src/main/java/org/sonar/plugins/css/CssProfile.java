/*
 * Sonar CSS Plugin
 * Copyright (C) 2013 Tamas Kende
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
package org.sonar.plugins.css;

import org.sonar.api.profiles.AnnotationProfileParser;
import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.ActiveRule;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.css.checks.CheckList;
import org.sonar.plugins.css.core.Css;

import java.util.List;

public class CssProfile extends ProfileDefinition {

  public static final String PROFILE_NAME = RulesProfile.SONAR_WAY_NAME;
  private final AnnotationProfileParser annotationProfileParser;

  public CssProfile(AnnotationProfileParser annotationProfileParser) {
    this.annotationProfileParser = annotationProfileParser;
  }

  @Override
  public RulesProfile createProfile(ValidationMessages validation) {
    RulesProfile ret = RulesProfile.create(PROFILE_NAME, Css.KEY);

    RulesProfile checks = annotationProfileParser.parse(CheckList.REPOSITORY_KEY,
        CheckList.REPOSITORY_NAME, Css.KEY, CheckList.getChecks(), validation);

    List<ActiveRule> rules = checks.getActiveRules();
    ret.setActiveRules(rules);
    return ret;
  }

}
