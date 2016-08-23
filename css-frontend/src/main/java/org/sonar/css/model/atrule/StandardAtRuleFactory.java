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
package org.sonar.css.model.atrule;

import com.google.common.collect.ImmutableSet;

import java.util.*;

import org.sonar.css.model.atrule.standard.*;

public class StandardAtRuleFactory {

  private static final Set<Class> ALL_AT_RULE_CLASSES = ImmutableSet.of(
    Annotation.class,
    BottomCenter.class,
    BottomLeft.class,
    BottomLeftCorner.class,
    BottomRight.class,
    BottomRightCorner.class,
    CharacterVariant.class,
    Charset.class,
    CounterStyle.class,
    CustomMedia.class,
    Document.class,
    FontFace.class,
    FontFeatureValues.class,
    Import.class,
    Keyframes.class,
    LeftBottom.class,
    LeftMiddle.class,
    LeftTop.class,
    Media.class,
    Namespace.class,
    Ornaments.class,
    Page.class,
    RightBottom.class,
    RightMiddle.class,
    RightTop.class,
    Styleset.class,
    Stylistic.class,
    Supports.class,
    Swash.class,
    TopCenter.class,
    TopLeft.class,
    TopLeftCorner.class,
    TopRight.class,
    TopRightCorner.class,
    Viewport.class);

  private static final Map<String, StandardAtRule> ALL = new HashMap<>();

  static {
    try {
      StandardAtRule standardAtRule;
      for (Class clazz : ALL_AT_RULE_CLASSES) {
        standardAtRule = (StandardAtRule) clazz.newInstance();
        ALL.put(standardAtRule.getName(), standardAtRule);
      }
    } catch (InstantiationException | IllegalAccessException e) {
      throw new IllegalStateException("CSS at-rules full list cannot be created.", e);
    }
  }

  private StandardAtRuleFactory() {
  }

  public static StandardAtRule getByName(String atRuleName) {
    StandardAtRule atRule = ALL.get(atRuleName.toLowerCase(Locale.ENGLISH));
    return atRule != null ? atRule : new UnknownAtRule(atRuleName.toLowerCase(Locale.ENGLISH));
  }

  public static List<StandardAtRule> getAll() {
    return new ArrayList<>(ALL.values());
  }

}
