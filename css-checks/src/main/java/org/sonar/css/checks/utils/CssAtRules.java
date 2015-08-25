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
package org.sonar.css.checks.utils;

import com.google.common.collect.ImmutableList;

public class CssAtRules {

  public static ImmutableList<String> CSS_AT_RULES = ImmutableList.of(
    "annotation",
    "character-variant",
    "charset",
    "custom-media",
    "document",
    "font-face",
    "font-feature-values",
    "import",
    "keyframes",
    "media",
    "namespace",
    "ornaments",
    "page",
    "styleset",
    "stylistic",
    "supports",
    "swash",
    "viewport"
    );

}
