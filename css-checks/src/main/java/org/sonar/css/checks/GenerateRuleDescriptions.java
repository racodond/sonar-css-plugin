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

import com.google.common.collect.Lists;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

import org.apache.commons.io.FileUtils;
import org.sonar.css.model.StandardCssObject;
import org.sonar.css.model.Vendor;
import org.sonar.css.model.atrule.StandardAtRuleFactory;
import org.sonar.css.model.function.StandardFunctionFactory;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.StandardPropertyFactory;

public class GenerateRuleDescriptions {

  private static final String TEMPLATE_DIRECTORY = "css-checks/src/main/resources/org/sonar/l10n/css/rules/css/template/";
  private static final String TARGET_DIRECTORY = "css-checks/target/classes/org/sonar/l10n/css/rules/css/";
  private static final String UTF_8 = "UTF-8";

  private enum CssObjectType {
    STANDARD_PROPERTY, STANDARD_ATRULE, STANDARD_FUNCTION
  }

  private GenerateRuleDescriptions() {
  }

  public static void main(String... args) throws Exception {
    generateHtmlDescriptionFile(TARGET_DIRECTORY + "validate-property-value.html", generateValidatePropertyRuleDescription());
    generateHtmlDescriptionFile(TARGET_DIRECTORY + "obsolete-properties.html", generateObsoletePropertiesRuleDescription());
    generateHtmlDescriptionFile(TARGET_DIRECTORY + "obsolete-functions.html", generateObsoleteFunctionsRuleDescription());
    generateHtmlDescriptionFile(TARGET_DIRECTORY + "known-properties.html", generateUnknownPropertiesRuleDescription());
    generateHtmlDescriptionFile(TARGET_DIRECTORY + "unknown-at-rules.html", generateUnknownAtRulesRuleDescription());
    generateHtmlDescriptionFile(TARGET_DIRECTORY + "unknown-functions.html", generateUnknownFunctionsRuleDescription());
    generateHtmlDescriptionFile(TARGET_DIRECTORY + "experimental-property-usage.html", generateExperimentalPropertiesRuleDescription());
    generateHtmlDescriptionFile(TARGET_DIRECTORY + "experimental-atrule-usage.html", generateExperimentalAtRulesRuleDescription());
    generateHtmlDescriptionFile(TARGET_DIRECTORY + "experimental-function-usage.html", generateExperimentalFunctionsRuleDescription());
    generateHtmlDescriptionFile(TARGET_DIRECTORY + "compatible-vendor-prefixes.html", generateCompatibleVendorPrefixesRuleDescription());
  }

  private static String generateObsoletePropertiesRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(new File(TEMPLATE_DIRECTORY + "obsolete-properties.html"), UTF_8);
    return description.replace("[[obsoleteProperties]]", generateHtmlTable(getStandardCssObjects(CssObjectType.STANDARD_PROPERTY, true, null, null)));
  }

  private static String generateObsoleteFunctionsRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(new File(TEMPLATE_DIRECTORY + "obsolete-functions.html"), UTF_8);
    return description.replace("[[obsoleteFunctions]]", generateHtmlTable(getStandardCssObjects(CssObjectType.STANDARD_FUNCTION, true, null, null)));
  }

  private static String generateUnknownPropertiesRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(new File(TEMPLATE_DIRECTORY + "known-properties.html"), UTF_8);
    return description.replace("[[allProperties]]", generateHtmlTable(getStandardCssObjects(CssObjectType.STANDARD_PROPERTY, null, null, null)));
  }

  private static String generateUnknownAtRulesRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(new File(TEMPLATE_DIRECTORY + "unknown-at-rules.html"), UTF_8);
    return description.replace("[[allAtRules]]", generateHtmlTable(getStandardCssObjects(CssObjectType.STANDARD_ATRULE, null, null, null)));
  }

  private static String generateUnknownFunctionsRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(new File(TEMPLATE_DIRECTORY + "unknown-functions.html"), UTF_8);
    return description.replace("[[allFunctions]]", generateHtmlTable(getStandardCssObjects(CssObjectType.STANDARD_FUNCTION, null, null, null)));
  }

  private static String generateExperimentalPropertiesRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(
      new File(TEMPLATE_DIRECTORY + "experimental-property-usage.html"), UTF_8);
    description = description.replace("[[vendors]]", generateListOfVendors());
    return description.replace("[[experimentalProperties]]", generateHtmlTable(getStandardCssObjects(CssObjectType.STANDARD_PROPERTY, null, true, null)));
  }

  private static String generateExperimentalAtRulesRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(
      new File(TEMPLATE_DIRECTORY + "experimental-atrule-usage.html"), UTF_8);
    description = description.replace("[[vendors]]", generateListOfVendors());
    return description.replace("[[experimentalAtRules]]", generateHtmlTable(getStandardCssObjects(CssObjectType.STANDARD_ATRULE, null, true, null)));
  }

  private static String generateExperimentalFunctionsRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(
      new File(TEMPLATE_DIRECTORY + "experimental-function-usage.html"), UTF_8);
    description = description.replace("[[vendors]]", generateListOfVendors());
    return description.replace("[[experimentalFunctions]]", generateHtmlTable(getStandardCssObjects(CssObjectType.STANDARD_FUNCTION, null, true, null)));
  }

  private static String generateCompatibleVendorPrefixesRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(
      new File(TEMPLATE_DIRECTORY + "compatible-vendor-prefixes.html"), UTF_8);
    return description.replace("[[vendorPrefixedProperties]]", generateHtmlTable(getStandardCssObjects(CssObjectType.STANDARD_PROPERTY, null, null, true)));
  }

  private static String generateValidatePropertyRuleDescription() throws IOException {
    StringBuilder description = new StringBuilder(FileUtils.readFileToString(
      new File(TEMPLATE_DIRECTORY + "validate-property-value.html"), UTF_8));

    description.append("<table border=1>\n")
      .append("  <thead>\n")
      .append("  <tr align=center>\n")
      .append("    <th>Name</th>\n")
      .append("    <th>Validator</th>\n")
      .append("    <th>Status</th>\n")
      .append("  </tr>\n")
      .append("  </thead>\n");

    StandardProperty property;
    for (StandardCssObject cssObject : getStandardCssObjects(CssObjectType.STANDARD_PROPERTY, false, null, null)) {
      property = (StandardProperty) cssObject;
      description.append("  <tr>\n").append("    <td nowrap=\"nowrap\">");
      if (!property.getLinks().isEmpty()) {
        description.append("<a target=\"_blank\" href=\"").append(property.getLinks().get(0)).append("\">");
      }
      description.append("<code>").append(property.getName()).append("</code>");
      if (!property.getLinks().isEmpty()) {
        description.append("</a>");
      }
      for (int i = 1; i < property.getLinks().size(); i++) {
        description.append("&nbsp;&nbsp;<a target=\"_blank\" href=\"").append(property.getLinks().get(i)).append("\">#").append(i + 1).append("</a>");
      }
      description.append("</td>\n")
        .append("    <td>");
      if (property.getValidatorFormat().isEmpty()) {
        description.append("Not yet implemented");
      } else {
        description.append(property.getValidatorFormat()
          .replace(" | ",
            " <a target=\"_blank\" href=\"https://developer.mozilla.org/en-US/docs/Web/CSS/Value_definition_syntax#Single_bar\">|</a> ")
          .replace("||",
            "<a target=\"_blank\" href=\"https://developer.mozilla.org/en-US/docs/Web/CSS/Value_definition_syntax#Double_bar\">||</a>")
          .replace("&&",
            "<a target=\"_blank\" href=\"https://developer.mozilla.org/en-US/docs/Web/CSS/Value_definition_syntax#Double_ampersand\">&amp;&amp;</a>")
          .replace("?",
            "<a target=\"_blank\" href=\"https://developer.mozilla.org/en-US/docs/Web/CSS/Value_definition_syntax#Question_mark_()\">?</a>")
          .replace("+",
            "<a target=\"_blank\" href=\"https://developer.mozilla.org/en-US/docs/Web/CSS/Value_definition_syntax#Plus_()\">+</a>")
          .replace("*",
            "<a target=\"_blank\" href=\"https://developer.mozilla.org/en-US/docs/Web/CSS/Value_definition_syntax#Asterisk_(*)\">*</a>")
          .replace("<angle>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-values-3/#angle-value\">&lt;angle&gt;</a>")
          .replace("<basic-shape>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-shapes/#typedef-basic-shape\">&lt;basic-shape&gt;</a>")
          .replace("<border-style>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-backgrounds-3/#border-style\">&lt;border-style&gt;</a>")
          .replace("<border-width>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-backgrounds-3/#border-width\">&lt;border-width&gt;</a>")
          .replace("<box>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-backgrounds-3/#box\">&lt;box&gt;</a>")
          .replace("<color>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-color/#typedef-color\">&lt;color&gt;</a>")
          .replace("<counter-style>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-counter-styles-3/#typedef-counter-style\">&lt;counter-style&gt;</a>")
          .replace("<cue-after>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/css3-speech/#cue-after\">&lt;cue-after&gt;</a>")
          .replace("<cue-before>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/css3-speech/#cue-before\">&lt;cue-before&gt;</a>")
          .replace("<family-name>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/CSS2/fonts.html#value-def-family-name\">&lt;family-name&gt;</a>")
          .replace("<filter-function>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/fxtf/filters/#typedef-filter-function\">&lt;filter-function&gt;</a>")
          .replace("<flex-direction>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-flexbox-1/#propdef-flex-direction\">&lt;flex-direction&gt;</a>")
          .replace("<flex-wrap>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-flexbox-1/#propdef-flex-wrap\">&lt;flex-wrap&gt;</a>")
          .replace("<frequency>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-values-3/#frequency-value\">&lt;frequency&gt;</a>")
          .replace("<function>",
            "&lt;function&gt;")
          .replace("<generic-family>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/CSS2/fonts.html#value-def-generic-family\">&lt;generic-family&gt;</a>")
          .replace("<id>",
            "<a target=\"_blank\" href=\"https://drafts.csswg.org/css-ui/#typedef-id\">&lt;id&gt;</a>")
          .replace("<identifier>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-values-3/#identifier-value\">&lt;identifier&gt;</a>")
          .replace("<image>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-images-3/#funcdef-image\">&lt;image&gt;</a>")
          .replace("<integer>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-values-3/#integer-value\">&lt;integer&gt;</a>")
          .replace("<length>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-values-3/#length-value\">&lt;length&gt;</a>")
          .replace("<line-stacking-ruby>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/css3-linebox/#line-stacking-ruby\">&lt;line-stacking-ruby&gt;</a>")
          .replace("<line-stacking-shift>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/css3-linebox/#line-stacking-shift\">&lt;line-stacking-shift&gt;</a>")
          .replace("<line-stacking-strategy>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/css3-linebox/#line-stacking-strategy\">&lt;line-stacking-strategy&gt;</a>")
          .replace("<list-style-position>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-lists-3/#propdef-list-style-position\">&lt;list-style-image&gt;</a>")
          .replace("<list-style-image>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-lists-3/#propdef-list-style-image\">&lt;list-style-position&gt;</a>")
          .replace("<list-style-type>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-lists-3/#propdef-list-style-type\">&lt;list-style-type&gt;</a>")
          .replace("<margin-width>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/CSS2/box.html#value-def-margin-width\">&lt;margin-width&gt;</a>")
          .replace("<number>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-values-3/#number-value\">&lt;number&gt;</a>")
          .replace("<outline-color>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/CSS2/ui.html#propdef-outline-color\">&lt;outline-color&gt;</a>")
          .replace("<outline-style>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/CSS2/ui.html#propdef-outline-style\">&lt;outline-style&gt;</a>")
          .replace("<outline-width>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/CSS2/ui.html#propdef-outline-width\">&lt;outline-width&gt;</a>")
          .replace("<padding-width>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/CSS2/box.html#value-def-padding-width\">&lt;padding-width&gt;</a>")
          .replace("<pause-after>",
            "<a target=\"_blank\" href=\"https://drafts.csswg.org/css-speech-1/#pause-after\">&lt;pause-after&gt;</a>")
          .replace("<pause-before>",
            "<a target=\"_blank\" href=\"https://drafts.csswg.org/css-speech-1/#pause-before\">&lt;pause-before&gt;</a>")
          .replace("<percentage>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-values-3/#percentage-value\">&lt;percentage&gt;</a>")
          .replace("<resolution>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-values-3/#resolution-value\">&lt;resolution&gt;</a>")
          .replace("<uri>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-values-3/#url-value\">&lt;uri&gt;</a>")
          .replace("<single-animation-name>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/css3-animations/#single-animation-name\">&lt;single-animation-name&gt;</a>")
          .replace("<single-animation-direction>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/css3-animations/#single-animation-direction\">&lt;single-animation-direction&gt;</a>")
          .replace("<single-animation-fill-mode>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/css3-animations/#single-animation-fill-mode\">&lt;single-animation-fill-mode&gt;</a>")
          .replace("<single-animation-iteration-count>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/css3-animations/#single-animation-iteration-count\">&lt;single-animation-iteration-count&gt;</a>")
          .replace("<single-animation-play-state>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/css3-animations/#single-animation-play-state\">&lt;single-animation-play-state&gt;</a>")
          .replace("<shape-box>",
            "<a target=\"_blank\" href=\"https://drafts.csswg.org/css-shapes/#typedef-shape-box\">&lt;shape-box&gt;</a>")
          .replace("<single-timing-function>",
            "<a target=\"_blank\" href=\"http://www.w3.org/TR/2012/WD-css3-transitions-20120403/#transition-timing-function\">&lt;single-timing-function&gt;</a>")
          .replace("<string>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-values-3/#string-value\">&lt;string&gt;</a>")
          .replace("<target-name>",
            "<a target=\"_blank\" href=\"https://drafts.csswg.org/css-ui/#typedef-target-name\">&lt;target-name&gt;</a>")
          .replace("<time>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-values-3/#time-value\">&lt;time&gt;</a>")
          .replace("<transform-function>",
            "<a target=\"_blank\" href=\"https://drafts.csswg.org/css-transforms/#typedef-transform-function\">&lt;transform-function&gt;</a>")
          .replace("<width>",
            "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css2/visudet.html#propdef-width\">&lt;width&gt;</a>")
          .replace("<x>", "&lt;x&gt;")
          .replace("<y>", "&lt;y&gt;"));
      }
      description.append("</td>\n")
        .append("<td align=center>");
      if (property.isExperimental()) {
        description.append("Experimental");
      } else {
        description.append("Standard");
      }
      description.append("</td>\n")
        .append("  </tr>\n");
    }
    description.append("</table>\n");
    return description.toString();
  }

  private static void generateHtmlDescriptionFile(String path, String htmlPage) {
    try {
      Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), UTF_8));
      writer.write(htmlPage);
      writer.close();
    } catch (IOException e) {
      throw new IllegalStateException("Could not generate the HTML description.", e);
    }
  }

  private static List<StandardCssObject> getStandardCssObjects(CssObjectType type, @Nullable Boolean isObsolete, @Nullable Boolean isExperimental, @Nullable Boolean hasVendors) {
    List<StandardCssObject> cssObjects;
    if (CssObjectType.STANDARD_PROPERTY.equals(type)) {
      cssObjects = StandardPropertyFactory.createAll();
    } else if (CssObjectType.STANDARD_ATRULE.equals(type)) {
      cssObjects = StandardAtRuleFactory.createAll();
    } else if (CssObjectType.STANDARD_FUNCTION.equals(type)) {
      cssObjects = StandardFunctionFactory.createAll();
    } else {
      throw new IllegalArgumentException("Unknown Standard CSS object");
    }

    if (isObsolete != null) {
      cssObjects = cssObjects.stream().filter(o -> o.isObsolete() == isObsolete).collect(Collectors.toList());
    }
    if (isExperimental != null) {
      cssObjects = cssObjects.stream().filter(o -> o.isExperimental() == isExperimental).collect(Collectors.toList());
    }
    if (hasVendors != null) {
      cssObjects = cssObjects.stream().filter(o -> o.hasVendors() == hasVendors).collect(Collectors.toList());
    }
    Collections.sort(cssObjects, (f1, f2) -> f1.getName().compareTo(f2.getName()));
    return cssObjects;
  }

  private static String generateListOfVendors() {
    StringBuilder html = new StringBuilder("<ul>\n");
    for (Vendor vendor : Vendor.values()) {
      html.append("<li>")
        .append("<code>")
        .append(vendor.getPrefix())
        .append("</code> ")
        .append(vendor.getDescription())
        .append("</li>\n");
    }
    html.append("</ul>\n");
    return html.toString();
  }

  private static String generateHtmlTable(List<StandardCssObject> standardCssObjects) {
    StringBuilder html = new StringBuilder("<table style=\"border: 0;\">\n");
    List<List<StandardCssObject>> subLists = Lists.partition(standardCssObjects, 3);
    for (List<StandardCssObject> subList : subLists) {
      html.append("<tr>");
      for (StandardCssObject standardCssObject : subList) {
        html.append("<td style=\"border: 0; \">");
        if (!standardCssObject.getLinks().isEmpty()) {
          html.append("<a target=\"_blank\" href=\"").append(standardCssObject.getLinks().get(0)).append("\">");
        }
        html.append("<code>").append(standardCssObject.getName()).append("</code>");
        if (!standardCssObject.getLinks().isEmpty()) {
          html.append("</a>");
        }
        html.append("</code>");
        for (int i = 1; i < standardCssObject.getLinks().size(); i++) {
          html.append("&nbsp;&nbsp;<a target=\"_blank\" href=\"").append(standardCssObject.getLinks().get(i)).append("\">#").append(i + 1).append("</a>");
        }
        html.append("</td>\n");
      }
      html.append("</tr>");
    }
    html.append("</table>\n");
    return html.toString();
  }

}
