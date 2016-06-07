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

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.sonar.css.model.StandardCssObject;
import org.sonar.css.model.Vendor;
import org.sonar.css.model.atrule.StandardAtRule;
import org.sonar.css.model.atrule.standard.Annotation;
import org.sonar.css.model.function.StandardFunction;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.standard.Border;

public class GenerateRuleDescriptions {

  private static final String TEMPLATE_DIRECTORY = "css-checks/src/main/resources/org/sonar/l10n/css/rules/css/template/";
  private static final String TARGET_DIRECTORY = "css-checks/target/classes/org/sonar/l10n/css/rules/css/";
  private static final String UTF_8 = "UTF-8";

  private GenerateRuleDescriptions() {
  }

  public static void main(String... args) throws Exception {
    boolean sizeLimit = false;
    if ("sizeLimit".equals(args[0])) {
      sizeLimit = true;
    }
    generateHtmlDescriptionFile("doc/validators.html", generateValidatePropertyRuleDescription(false));
    generateHtmlDescriptionFile(TARGET_DIRECTORY + "validate-property-value.html", generateValidatePropertyRuleDescription(sizeLimit));
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
    return description.replace("[[obsoleteProperties]]", generateHtmlList(getStandardProperties(true, false, false)));
  }

  private static String generateObsoleteFunctionsRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(new File(TEMPLATE_DIRECTORY + "obsolete-functions.html"), UTF_8);
    return description.replace("[[obsoleteFunctions]]", generateHtmlList(getStandardFunctions(true, false)));
  }

  private static String generateUnknownPropertiesRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(new File(TEMPLATE_DIRECTORY + "known-properties.html"), UTF_8);
    return description.replace("[[allProperties]]", generateHtmlList(getStandardProperties(false, false, false)));
  }

  private static String generateUnknownAtRulesRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(new File(TEMPLATE_DIRECTORY + "unknown-at-rules.html"), UTF_8);
    return description.replace("[[allAtRules]]", generateHtmlList(getStandardAtRules(false, false)));
  }

  private static String generateUnknownFunctionsRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(new File(TEMPLATE_DIRECTORY + "unknown-functions.html"), UTF_8);
    return description.replace("[[allFunctions]]", generateHtmlList(getStandardFunctions(false, false)));
  }

  private static String generateExperimentalPropertiesRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(
      new File(TEMPLATE_DIRECTORY + "experimental-property-usage.html"), UTF_8);

    description = description.replace("[[vendors]]", generateListOfVendors());
    description = description.replace("[[experimentalProperties]]", generateHtmlList(getStandardProperties(false, true, false)));

    return description;
  }

  private static String generateExperimentalAtRulesRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(
      new File(TEMPLATE_DIRECTORY + "experimental-atrule-usage.html"), UTF_8);

    description = description.replace("[[vendors]]", generateListOfVendors());
    description = description.replace("[[experimentalAtRules]]", generateHtmlList(getStandardAtRules(false, true)));

    return description;
  }

  private static String generateExperimentalFunctionsRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(
      new File(TEMPLATE_DIRECTORY + "experimental-function-usage.html"), UTF_8);

    description = description.replace("[[vendors]]", generateListOfVendors());
    description = description.replace("[[experimentalFunctions]]", generateHtmlList(getStandardFunctions(false, true)));

    return description;
  }

  private static String generateCompatibleVendorPrefixesRuleDescription() throws IOException {
    String description = FileUtils.readFileToString(
      new File(TEMPLATE_DIRECTORY + "compatible-vendor-prefixes.html"), UTF_8);
    return description.replace("[[vendorPrefixedProperties]]", generateHtmlList(getStandardProperties(false, false, true)));
  }

  /**
   * @param sizeLimit TODO: Hack to be removed when http://jira.sonarsource.com/browse/SONAR-6632 is fixed
   */
  private static String generateValidatePropertyRuleDescription(boolean sizeLimit) throws IOException {

    StringBuilder description = new StringBuilder(FileUtils.readFileToString(
      new File(TEMPLATE_DIRECTORY + "validate-property-value.html"), UTF_8));

    if (sizeLimit) {
      description
        .append(
          "<p>See the <a target=\"_blank\" href=\"http://htmlpreview.github.io/?https://github.com/SonarCommunity/sonar-css/blob/master/doc/validators.html\">full list of implemented validators</a>.</p>");
    } else {
      description.append("<table border=1>\n")
        .append("  <thead>\n")
        .append("  <tr align=center>\n")
        .append("    <th>Name</th>\n")
        .append("    <th>Validator</th>\n")
        .append("  </tr>\n")
        .append("  </thead>\n");

      StandardProperty property;
      for (StandardCssObject cssObject : getStandardProperties(false, false, false)) {
        property = (StandardProperty) cssObject;
        description.append("  <tr>\n").append("    <td nowrap=\"nowrap\">");
        if (!property.getLinks().isEmpty()) {
          description.append("<a target=\"_blank\" href=\"").append(property.getLinks().get(0)).append("\">");
        }
        description.append(property.getName());
        if (!property.getLinks().isEmpty()) {
          description.append("</a>");
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
          .append("  </tr>\n");
      }
      description.append("</table>\n");
    }
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

  private static List<StandardCssObject> getStandardProperties(boolean isObsolete, boolean isExperimental, boolean hasVendors) {
    try {
      List<StandardCssObject> properties = new ArrayList<>();
      ImmutableSet<ClassPath.ClassInfo> classInfos = ClassPath.from(Border.class.getClassLoader()).getTopLevelClasses("org.sonar.css.model.property.standard");
      for (ClassPath.ClassInfo classInfo : classInfos) {
        if (!"org.sonar.css.model.property.standard.package-info".equals(classInfo.getName())) {
          properties.add((StandardProperty) Class.forName(classInfo.getName()).newInstance());
        }
      }

      properties
        .stream()
        .sorted((object1, object2) -> object1.getName().compareTo(object2.getName()));

      if (isObsolete) {
        properties
          .stream()
          .filter(p -> p.isObsolete());
      }

      if (isExperimental) {
        properties
          .stream()
          .filter(p -> p.isExperimental());
      }

      if (hasVendors) {
        properties
          .stream()
          .filter(p -> p.hasVendors());
      }

      return properties;
    } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      throw new IllegalStateException("Could not retrieve the list of standard CSS property classes.", e);
    }
  }

  private static List<StandardCssObject> getStandardAtRules(boolean isObsolete, boolean isExperimental) {
    try {
      List<StandardCssObject> atRules = new ArrayList<>();
      ImmutableSet<ClassPath.ClassInfo> classInfos = ClassPath.from(Annotation.class.getClassLoader()).getTopLevelClasses("org.sonar.css.model.atrule.standard");
      for (ClassPath.ClassInfo classInfo : classInfos) {
        if (!"org.sonar.css.model.atrule.standard.package-info".equals(classInfo.getName())) {
          atRules.add((StandardAtRule) Class.forName(classInfo.getName()).newInstance());
        }
      }
      atRules
        .stream()
        .sorted((object1, object2) -> object1.getName().compareTo(object2.getName()));

      if (isObsolete) {
        atRules
          .stream()
          .filter(p -> p.isObsolete());
      }

      if (isExperimental) {
        atRules
          .stream()
          .filter(p -> p.isExperimental());
      }

      return atRules;
    } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      throw new IllegalStateException("Could not retrieve the list of standard CSS at rule classes.", e);
    }
  }

  private static List<StandardCssObject> getStandardFunctions(boolean isObsolete, boolean isExperimental) {
    try {
      List<StandardCssObject> functions = new ArrayList<>();
      ImmutableSet<ClassPath.ClassInfo> classInfos = ClassPath.from(Annotation.class.getClassLoader()).getTopLevelClasses("org.sonar.css.model.function.standard");
      for (ClassPath.ClassInfo classInfo : classInfos) {
        if (!"org.sonar.css.model.function.standard.package-info".equals(classInfo.getName())) {
          functions.add((StandardFunction) Class.forName(classInfo.getName()).newInstance());
        }
      }

      functions
        .stream()
        .sorted((object1, object2) -> object1.getName().compareTo(object2.getName()));

      if (isObsolete) {
        functions
          .stream()
          .filter(p -> p.isObsolete());
      }

      if (isExperimental) {
        functions
          .stream()
          .filter(p -> p.isExperimental());
      }

      return functions;
    } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      throw new IllegalStateException("Could not retrieve the list of standard CSS function classes.", e);
    }
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

  private static String generateHtmlList(List<StandardCssObject> standardCssObjects) {
    StringBuilder html = new StringBuilder("<ul>\n");
    for (StandardCssObject standardCssObject : standardCssObjects) {
      html.append("  <li>");
      if (!standardCssObject.getLinks().isEmpty()) {
        html.append("<a target=\"_blank\" href=\"").append(standardCssObject.getLinks().get(0)).append("\">");
      }
      html.append(standardCssObject.getName());
      if (!standardCssObject.getLinks().isEmpty()) {
        html.append("</a>");
      }
      for (int i = 1; i < standardCssObject.getLinks().size(); i++) {
        html.append("<a target=\"_blank\" href=\"").append(standardCssObject.getLinks().get(i)).append("\"> link#").append(i).append("</a>");
      }
      html.append("  </li>\n");
    }
    html.append("</ul>\n");
    return html.toString();
  }

}
