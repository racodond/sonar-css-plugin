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
package org.sonar.css.checks;

import com.google.common.collect.ImmutableList;

import java.util.Collection;

public final class CheckList {

  public static final String REPOSITORY_KEY = "css";

  public static final String REPOSITORY_NAME = "SonarQube";

  private CheckList() {
  }

  @SuppressWarnings("rawtypes")
  public static Collection<Class> getChecks() {
    return ImmutableList.<Class>of(
      AllGradientDefinitionsCheck.class,
      AlphabetizeDeclarationsCheck.class,
      BOMCheck.class,
      BewareOfBoxModelCheck.class,
      CaseInsensitiveFlagCheck.class,
      CaseCheck.class,
      CharsetFirstCheck.class,
      CommentRegularExpressionCheck.class,
      CompatibleVendorPrefixesCheck.class,
      FormattingCheck.class,
      DeprecatedSystemColorCheck.class,
      DeprecatedIEStaticFilterCheck.class,
      DuplicateBackgroundImagesCheck.class,
      EmptyRuleCheck.class,
      IdInSelectorCheck.class,
      ImportUsageCheck.class,
      ImportantUsageCheck.class,
      OverqualifiedElementCheck.class,
      OverspecificSelectorCheck.class,
      SelectorLikeRegExCheck.class,
      StarHackCheck.class,
      UnderscoreHackCheck.class,
      UnitForZeroValueCheck.class,
      UniversalSelectorCheck.class,
      DisplayPropertyGroupingCheck.class,
      DoNotUseShorthandPropertyCheck.class,
      DuplicatedPropertiesCheck.class,
      EmptyDeclarationCheck.class,
      ExperimentalFunctionCheck.class,
      ExperimentalAtRuleCheck.class,
      ExperimentalPropertyCheck.class,
      FontFaceBrowserCompatibilityCheck.class,
      FixmeTagCheck.class,
      ImportantPositionCheck.class,
      ImportNumberCheck.class,
      ImportFirstCheck.class,
      InliningFontFileCheck.class,
      UnknownPropertyCheck.class,
      LeadingZeroCheck.class,
      LineLengthCheck.class,
      NumberOfRulesPerSheetCheck.class,
      NoSonarTagPresenceCheck.class,
      ObsoleteFunctionCheck.class,
      ObsoletePropertieCheck.class,
      OneDeclarationPerLineCheck.class,
      ParsingErrorCheck.class,
      PropertyRegularExpressionCheck.class,
      SelectorNumberCheck.class,
      SelectorNamingConventionCheck.class,
      SemicolonDeclarationCheck.class,
      UseShorthandPropertyCheck.class,
      TabCharacterCheck.class,
      TrailingWhitespaceCheck.class,
      TodoTagCheck.class,
      TooManyWebFontsCheck.class,
      UnknownFunctionCheck.class,
      UnknownAtRuleCheck.class,
      ValidatePropertyValueCheck.class,
      VendorPrefixWithStandardCheck.class);
  }
}
