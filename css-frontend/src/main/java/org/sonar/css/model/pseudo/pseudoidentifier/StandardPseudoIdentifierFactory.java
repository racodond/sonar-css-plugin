/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
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
package org.sonar.css.model.pseudo.pseudoidentifier;

import com.google.common.collect.ImmutableSet;

import java.util.*;

import org.sonar.css.model.pseudo.pseudoidentifier.standard.*;
import org.sonar.css.model.pseudo.pseudoidentifier.standard.Optional;

public class StandardPseudoIdentifierFactory {

  private static final Set<Class> ALL_PSEUDO_IDENTIFIER_CLASSES = ImmutableSet.of(
    Active.class,
    After.class,
    AnyLink.class,
    Backdrop.class,
    Before.class,
    Blank.class,
    Checked.class,
    Content.class,
    Current.class,
    Default.class,
    Disabled.class,
    Drop.class,
    Empty.class,
    Enabled.class,
    First.class,
    FirstChild.class,
    FirstLetter.class,
    FirstLine.class,
    FirstOfType.class,
    Focus.class,
    FocusWithin.class,
    Fullscreen.class,
    Future.class,
    GrammarError.class,
    Host.class,
    Hover.class,
    InactiveSelection.class,
    Indeterminate.class,
    InRange.class,
    Invalid.class,
    LastChild.class,
    LastOfType.class,
    Left.class,
    Link.class,
    Marker.class,
    OnlyChild.class,
    OnlyOfType.class,
    Optional.class,
    OutOfRange.class,
    Past.class,
    Paused.class,
    Placeholder.class,
    PlaceholderShown.class,
    Playing.class,
    ReadOnly.class,
    ReadWrite.class,
    Region.class,
    Required.class,
    Right.class,
    Root.class,
    Scope.class,
    Selection.class,
    Shadow.class,
    SpellingError.class,
    Target.class,
    UserInvalid.class,
    Valid.class,
    Visited.class);

  private static final Map<String, StandardPseudoIdentifier> ALL = new HashMap<>();

  static {
    try {
      StandardPseudoIdentifier standardPseudoIdentifier;
      for (Class clazz : ALL_PSEUDO_IDENTIFIER_CLASSES) {
        standardPseudoIdentifier = (StandardPseudoIdentifier) clazz.newInstance();
        ALL.put(standardPseudoIdentifier.getName(), standardPseudoIdentifier);
      }
    } catch (InstantiationException | IllegalAccessException e) {
      throw new IllegalStateException("CSS pseudo-identifier full list cannot be created.", e);
    }
  }

  private StandardPseudoIdentifierFactory() {
  }

  public static StandardPseudoIdentifier getByName(String identifier) {
    StandardPseudoIdentifier standardPseudoIdentifier = ALL.get(identifier.toLowerCase(Locale.ENGLISH));
    return standardPseudoIdentifier != null ? standardPseudoIdentifier : new UnknownPseudoIdentifier(identifier);
  }

  public static List<StandardPseudoIdentifier> getAll() {
    return new ArrayList<>(ALL.values());
  }

}
