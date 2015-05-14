/*
 * SonarQube CSS Plugin
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

import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rules.RuleFinder;
import org.sonar.api.utils.ValidationMessages;
import org.sonar.css.checks.CheckList;
import org.sonar.plugins.css.core.Css;
import org.sonar.squidbridge.annotations.AnnotationBasedProfileBuilder;

public class CssProfile extends ProfileDefinition {

    private final RuleFinder ruleFinder;

    public static final String SONAR_WAY_PROFILE_NAME = "Sonar way";

    public CssProfile(RuleFinder ruleFinder) {
        this.ruleFinder = ruleFinder;
    }

    @Override
    public RulesProfile createProfile(ValidationMessages messages) {
        AnnotationBasedProfileBuilder annotationBasedProfileBuilder = new AnnotationBasedProfileBuilder(ruleFinder);
        return annotationBasedProfileBuilder.build(CheckList.REPOSITORY_KEY, SONAR_WAY_PROFILE_NAME, Css.KEY, CheckList.getChecks(), messages);
    }
}
