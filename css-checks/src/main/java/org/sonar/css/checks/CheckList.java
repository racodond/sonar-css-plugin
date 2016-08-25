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
      BewareOfBoxModelCheck.class,
      BOMCheck.class,
      CaseCheck.class,
      CaseInsensitiveFlagCheck.class,
      CharsetFirstCheck.class,
      CommentRegularExpressionCheck.class,
      CompatibleVendorPrefixesCheck.class,
      DeprecatedIEStaticFilterCheck.class,
      DeprecatedSystemColorCheck.class,
      DisplayPropertyGroupingCheck.class,
      DoNotUseShorthandPropertyCheck.class,
      DuplicateBackgroundImagesCheck.class,
      DuplicatedPropertiesCheck.class,
      EmptyDeclarationCheck.class,
      EmptyRuleCheck.class,
      ExperimentalAtRuleCheck.class,
      ExperimentalFunctionCheck.class,
      ExperimentalIdentifierCheck.class,
      ExperimentalPropertyCheck.class,
      ExperimentalPseudoCheck.class,
      ExperimentalSelectorCombinatorCheck.class,
      FixmeTagCheck.class,
      FontFaceBrowserCompatibilityCheck.class,
      FormattingCheck.class,
      IdInSelectorCheck.class,
      ImportantPositionCheck.class,
      ImportantUsageCheck.class,
      ImportFirstCheck.class,
      ImportNumberCheck.class,
      ImportUsageCheck.class,
      InliningFontFileCheck.class,
      LeadingZeroCheck.class,
      LineLengthCheck.class,
      NoSonarTagPresenceCheck.class,
      NumberOfRulesPerSheetCheck.class,
      ObsoleteFunctionCheck.class,
      ObsoletePropertieCheck.class,
      ObsoletePseudoCheck.class,
      OneDeclarationPerLineCheck.class,
      OverqualifiedElementCheck.class,
      OverspecificSelectorCheck.class,
      ParsingErrorCheck.class,
      PropertyRegularExpressionCheck.class,
      SelectorLikeRegExCheck.class,
      SelectorNamingConventionCheck.class,
      SelectorNumberCheck.class,
      SemicolonDeclarationCheck.class,
      StarHackCheck.class,
      TabCharacterCheck.class,
      TodoTagCheck.class,
      TooManyWebFontsCheck.class,
      TrailingWhitespaceCheck.class,
      UnderscoreHackCheck.class,
      UnitForZeroValueCheck.class,
      UniversalSelectorCheck.class,
      UnknownAtRuleCheck.class,
      UnknownFunctionCheck.class,
      UnknownPropertyCheck.class,
      UnknownPseudoCheck.class,
      UseShorthandPropertyCheck.class,
      ValidatePropertyValueCheck.class,
      VendorPrefixWithStandardCheck.class);
  }

}
