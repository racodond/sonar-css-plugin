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
package org.sonar.css.checks;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

import org.sonar.css.model.Vendor;
import org.sonar.css.model.atrule.StandardAtRule;
import org.sonar.css.model.atrule.standard.Annotation;
import org.sonar.css.model.function.StandardFunction;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.standard.Border;

public class GenerateRuleDescriptions {

  private GenerateRuleDescriptions() {
  }

  public static void main(String... args) throws Exception {
    boolean sizeLimit = false;
    if ("sizeLimit".equals(args[0])) {
      sizeLimit = true;
    }
    generateHtmlDescriptionFile("doc/validators.html", generateValidatePropertyRuleDescription(false));
    generateHtmlDescriptionFile("css-checks/target/classes/org/sonar/l10n/css/rules/css/validate-property-value.html", generateValidatePropertyRuleDescription(sizeLimit));
    generateHtmlDescriptionFile("css-checks/target/classes/org/sonar/l10n/css/rules/css/obsolete-properties.html", generateObsoletePropertiesRuleDescription());
    generateHtmlDescriptionFile("css-checks/target/classes/org/sonar/l10n/css/rules/css/obsolete-functions.html", generateObsoleteFunctionsRuleDescription());
    generateHtmlDescriptionFile("css-checks/target/classes/org/sonar/l10n/css/rules/css/unknown-at-rules.html", generateUnknownAtRulesRuleDescription());
    generateHtmlDescriptionFile("css-checks/target/classes/org/sonar/l10n/css/rules/css/unknown-functions.html", generateUnknownFunctionsRuleDescription());
    generateHtmlDescriptionFile("css-checks/target/classes/org/sonar/l10n/css/rules/css/experimental-property-usage.html", generateExperimentalPropertiesRuleDescription());
    generateHtmlDescriptionFile("css-checks/target/classes/org/sonar/l10n/css/rules/css/experimental-atrule-usage.html", generateExperimentalAtRulesRuleDescription());
    generateHtmlDescriptionFile("css-checks/target/classes/org/sonar/l10n/css/rules/css/experimental-function-usage.html", generateExperimentalFunctionsRuleDescription());
    generateHtmlDescriptionFile("css-checks/target/classes/org/sonar/l10n/css/rules/css/compatible-vendor-prefixes.html", generateCompatibleVendorPrefixesRuleDescription());
  }

  private static StringBuilder generateObsoletePropertiesRuleDescription() {
    StringBuilder htmlPage = new StringBuilder()
      .append(
        "<p>To make sure that your code will keep working as expected in the future, do not use: <ul><li>Obsolete properties (no longer supported by main vendors or support to be removed by main vendors)</li><li>Properties not on W3C Standards track</li></ul></p>\n")
      .append("<h2>Noncompliant Code Example</h2>\n")
      .append("<pre>\n")
      .append(".mybox {\n")
      .append("  azimuth: 30deg; /* Noncompliant */\n")
      .append("  appearance: normal; /* Noncompliant */\n")
      .append("}\n")
      .append("</pre>\n")
      .append("<h2>Obsolete and Not on Standards Track Properties</h2>\n")
      .append("<ul>\n");

    StandardProperty property;
    for (Map.Entry<String, StandardProperty> entry : getAllStandardProperties().entrySet()) {
      property = entry.getValue();
      if (property.isObsolete()) {
        htmlPage.append("  <li>");
        if (!property.getLinks().isEmpty()) {
          htmlPage.append("<a target=\"_blank\" href=\"").append(property.getLinks().get(0)).append("\">");
        }
        htmlPage.append(property.getName());
        if (!property.getLinks().isEmpty()) {
          htmlPage.append("</a>");
        }
        htmlPage.append("  </li>\n");
      }
    }
    htmlPage.append("</ul>\n");
    return htmlPage;
  }

  private static StringBuilder generateObsoleteFunctionsRuleDescription() {
    StringBuilder htmlPage = new StringBuilder()
        .append(
            "<p>To make sure that your code will keep working as expected in the future, do not use: <ul><li>Obsolete functions (no longer supported by main vendors or support to be removed by main vendors)</li><li>Functions not on W3C Standards track</li></ul></p>\n")
        .append("<h2>Noncompliant Code Example</h2>\n")
        .append("<pre>\n")
        .append(".mybox {\n")
        .append("  font-size: min(10px, 3em); /* Noncompliant */\n")
        .append("  font-size: max(10px, 3em); /* Noncompliant */\n")
        .append("}\n")
        .append("</pre>\n")
        .append("<h2>Obsolete and Not on Standards Track Functions</h2>\n")
        .append("<ul>\n");

    StandardFunction function;
    for (Map.Entry<String, StandardFunction> entry : getAllStandardFunctions().entrySet()) {
      function = entry.getValue();
      if (function.isObsolete()) {
        htmlPage.append("  <li>");
        if (!function.getLinks().isEmpty()) {
          htmlPage.append("<a target=\"_blank\" href=\"").append(function.getLinks().get(0)).append("\">");
        }
        htmlPage.append(function.getName());
        if (!function.getLinks().isEmpty()) {
          htmlPage.append("</a>");
        }
        htmlPage.append("  </li>\n");
      }
    }
    htmlPage.append("</ul>\n");
    return htmlPage;
  }

  private static StringBuilder generateUnknownAtRulesRuleDescription() {
    StringBuilder htmlPage = new StringBuilder()
      .append("<p>The list of supported CSS @-rules is growing and it's very easy to miss a typo in a single @-rule when the name isn't checked.</p>\n")
      .append(
        "<p>This rule checks each @-rule to make sure that it is a known CSS @-rule. All vendor-prefixed @-rules are ignored because vendors may add in their own @-rules at any point in time, and there are no canonical lists of these @-rules.</p>\n")
      .append("<h2>Noncompliant Code Example</h2>\n")
      .append("<pre>\n")
      .append("@abc {\n")
      .append("  property: red;\n")
      .append("}\n")
      .append("</pre>\n")
      .append("<h2>Compliant Solution</h2>\n")
      .append("<pre>\n")
      .append("@import url(\"styles.css\");\n")
      .append("\n")
      .append("@font-face {\n")
      .append("  font-family: 'MyFontFamily';\n")
      .append("  src: url('myfont-webfont.eot?#iefix') format('embedded-opentype');\n")
      .append("}\n")
      .append("\n")
      .append("@-moz-vendoratrule { /* Compliant: the vendor-prefixed rules are not checked against the list of known at rules */\n")
      .append("}\n")
      .append("</pre>\n")
      .append("<h2>List of Known @-rules </h2>\n")
      .append("<ul>\n");

    StandardAtRule atRule;
    for (Map.Entry<String, StandardAtRule> entry : getAllStandardAtRules().entrySet()) {
      atRule = entry.getValue();
      htmlPage.append("  <li>");
      if (!atRule.getLinks().isEmpty()) {
        htmlPage.append("<a target=\"_blank\" href=\"").append(atRule.getLinks().get(0)).append("\">");
      }
      htmlPage.append(atRule.getName());
      if (!atRule.getLinks().isEmpty()) {
        htmlPage.append("</a>");
      }
      htmlPage.append("  </li>\n");
    }
    htmlPage.append("</ul>\n");
    return htmlPage;
  }

  private static StringBuilder generateUnknownFunctionsRuleDescription() {
    StringBuilder htmlPage = new StringBuilder()
      .append("<p>The list of supported CSS functions is growing quite large, and it's very easy to miss a typo in a single function when the function name isn't checked.</p>\n")
      .append(
        "<p>This rule checks each function name to make sure that it is a known CSS function. All vendor-prefixed functions are ignored because vendors may add in their own functions at any point in time, and there are no canonical lists of these functions.</p>\n")
      .append("<h2>Noncompliant Code Example</h2>\n")
      .append("<pre>\n")
      .append(".mybox {\n")
      .append("  color: rgb(255,0,51);\n")
      .append("  color: hello(100); /* Noncompliant: 'hello' isn't a known function */\n")
      .append("}\n")
      .append("</pre>\n")
      .append("<h2>Compliant Solution</h2>\n")
      .append("<pre>\n")
      .append(".mybox {\n")
      .append("  color: rgb(255,0,51); /* Compliant: 'rgb' is a known function */\n")
      .append("  color: -moz-linear-gradient(top, hsl(0, 80%, 70%), #bada55); /* Compliant: '-moz-linear-gradient' is a vendor function */\n")
      .append("}\n")
      .append("</pre>\n")
      .append("<h2>List of Known Functions</h2>\n")
      .append("<ul>\n");

    StandardFunction function;
    for (Map.Entry<String, StandardFunction> entry : getAllStandardFunctions().entrySet()) {
      function = entry.getValue();
      htmlPage.append("  <li>");
      if (!function.getLinks().isEmpty()) {
        htmlPage.append("<a target=\"_blank\" href=\"").append(function.getLinks().get(0)).append("\">");
      }
      htmlPage.append(function.getName());
      if (!function.getLinks().isEmpty()) {
        htmlPage.append("</a>");
      }
      htmlPage.append("  </li>\n");
    }
    htmlPage.append("</ul>\n");
    return htmlPage;
  }

  private static StringBuilder generateExperimentalPropertiesRuleDescription() {
    return new StringBuilder()
      .append(
        "<p>Even though vendor-specific properties are guaranteed not to cause conflicts, it should be recognized that these extensions may also be subject to change at the vendor’s whim, as they don’t form part of the CSS specifications, even though they often mimic the proposed behavior of existing or forthcoming CSS properties. Thus, it is not recommended to use them in production code.</p>\n")
      .append("<p>The rule raises an issue when one of the following prefixes is found:\n")
      .append(generateListOfVendors())
      .append("</p>\n")
      .append("<h2>Noncompliant Code Example</h2>\n")
      .append("<pre>\n")
      .append(".mybox {\n")
      .append("  -moz-border-radius: 5px;  /* Noncompliant */\n")
      .append("  color: green;\n")
      .append("}\n")
      .append("</pre>\n");
  }

  private static StringBuilder generateExperimentalAtRulesRuleDescription() {
    StringBuilder htmlPage = new StringBuilder()
      .append(
        "<p>Even though vendor-specific @-rules are guaranteed not to cause conflicts, it should be recognized that these extensions may also be subject to change at the vendor’s whim, as they don’t form part of the CSS specifications, even though they often mimic the proposed behavior of existing or forthcoming CSS @-rules. Thus, it is not recommended to use them in production code.</p>\n")
      .append("<p>The rule raises an issue when one of the following prefixes is found:\n")
      .append(generateListOfVendors())
      .append("</p>\n")
      .append("<p>This rule also raises an issue each time one of the following experimental @-rules is used:\n")
      .append("<ul>\n");

    StandardAtRule atRule;
    for (Map.Entry<String, StandardAtRule> entry : getAllStandardAtRules().entrySet()) {
      atRule = entry.getValue();
      if (atRule.isExperimental()) {
        htmlPage.append("  <li>");
        if (!atRule.getLinks().isEmpty()) {
          htmlPage.append("<a target=\"_blank\" href=\"").append(atRule.getLinks().get(0)).append("\">");
        }
        htmlPage.append(atRule.getName());
        if (!atRule.getLinks().isEmpty()) {
          htmlPage.append("</a>");
        }
        htmlPage.append("  </li>\n");
      }
    }

    htmlPage.append("</ul>\n")
      .append("</p>\n")
      .append("<h2>Noncompliant Code Example</h2>\n")
      .append("<pre>\n")
      .append("@-moz-vendoratrule {} /* Noncompliant: vendor-prefixed @-rule */\n")
      .append("\n")
      .append("@custom-media --narrow-window (max-width: 30em); /* Noncompliant: experimental @-rule */\n")
      .append("</pre>\n");

    return htmlPage;
  }

  private static StringBuilder generateCompatibleVendorPrefixesRuleDescription() {
    StringBuilder htmlPage = new StringBuilder()
      .append(
        "<p>Experimental CSS properties are typically implemented using vendor prefixes until the final behavior has been established and agreed upon. Most CSS3 properties have vendor-prefixed equivalents for multiple vendors, including Firefox (<code>-moz-</code>), Safari/Chrome (<code>-webkit-</code>), Opera (<code>-o-</code>), and Internet Explorer (<code>-ms-</code>). It's easy to forget to include the vendor prefixed version of a property when there are so many to keep track of.</p>\n")
      .append("<p>The following properties have multiple vendor-prefixed versions:\n")
      .append("<ul>\n");

    StandardProperty property;
    for (Map.Entry<String, StandardProperty> entry : getAllStandardProperties().entrySet()) {
      property = entry.getValue();
      if (property.getVendors().size() > 0) {
        htmlPage.append("  <li>");
        if (!property.getLinks().isEmpty()) {
          htmlPage.append("<a target=\"_blank\" href=\"").append(property.getLinks().get(0)).append("\">");
        }
        htmlPage.append(property.getName());
        if (!property.getLinks().isEmpty()) {
          htmlPage.append("</a>");
        }
        htmlPage.append("  </li>\n");
      }
    }

    htmlPage
      .append("</ul>\n")
      .append("</p>\n")
      .append("<p>This rule is intended to raise an issue when a vendor-prefixed property is missing. Note that only the following main vendors are checked by this rule:\n")
      .append("<ul>\n")
      .append("  <li>Firefox (<code>-moz-</code>)</li>\n")
      .append("  <li>Safari/Chrome (<code>-webkit-</code>)</li>\n")
      .append("  <li>Opera (<code>-o-</code>)</li>\n")
      .append("  <li>Internet Explorer (<code>-ms-</code>)</li>\n")
      .append("</ul>\n")
      .append("Konqueror (<code>khtml-</code>) for example is not.\n")
      .append("</p>\n")
      .append("</ul>\n")
      .append("</p>\n")
      .append("<h2>Noncompliant Code Example</h2>\n")
      .append("<pre>\n")
      .append("/* Noncompliant: Missing -moz-, -ms-, and -o- */\n")
      .append(".mybox {\n")
      .append("  -webkit-transform: translate(50px, 100px);\n")
      .append("}\n")
      .append("</pre>\n")
      .append("\n")
      .append("<h2>Compliant Solution</h2>\n")
      .append("<pre>\n")
      .append(".mybox {\n")
      .append("  -webkit-transform: translate(50px, 100px);\n")
      .append("  -moz-transform: translate(50px, 100px);\n")
      .append("  -ms-transform: translate(50px, 100px);\n")
      .append("  -o-transform: translate(50px, 100px);\n")
      .append("}\n")
      .append("</pre>\n");

    return htmlPage;
  }

  private static StringBuilder generateExperimentalFunctionsRuleDescription() {
    StringBuilder htmlPage = new StringBuilder()
      .append(
        "<p>Even though vendor-specific functions are guaranteed not to cause conflicts, it should be recognized that these extensions may also be subject to change at the vendor’s whim, as they don’t form part of the CSS specifications, even though they often mimic the proposed behavior of existing or forthcoming CSS functions. Thus, it is not recommended to use them in production code.</p>\n")
      .append("<p>The rule raises an issue when one of the following prefixes is found:\n")
      .append(generateListOfVendors())
      .append("</p>\n")
      .append("<p>This rule also raises an issue each time one of the following experimental functions is used:\n")
      .append("<ul>\n");

    StandardFunction function;
    for (Map.Entry<String, StandardFunction> entry : getAllStandardFunctions().entrySet()) {
      function = entry.getValue();
      if (function.isExperimental()) {
        htmlPage.append("  <li>");
        if (!function.getLinks().isEmpty()) {
          htmlPage.append("<a target=\"_blank\" href=\"").append(function.getLinks().get(0)).append("\">");
        }
        htmlPage.append(function.getName());
        if (!function.getLinks().isEmpty()) {
          htmlPage.append("</a>");
        }
        htmlPage.append("  </li>\n");
      }
    }

    htmlPage.append("</ul>\n")
      .append("</p>\n")
      .append("<h2>Noncompliant Code Example</h2>\n")
      .append("<pre>\n")
      .append(".mybox {\n")
      .append("  background-image: -moz-linear-gradient(top, #D7D 0%, #068 100%); /* Noncompliant: vendor-prefixed function */\n")
      .append("}\n")
      .append("\n")
      .append(".mybox {\n")
      .append("  background: #0ac repeating-conic-gradient(at 20%, white 0deg, white 20deg, red 20deg, red 40deg); /* Noncompliant: experimental function */\n")
      .append("}\n")
      .append("</pre>\n");

    return htmlPage;
  }

  /**
   * @param sizeLimit TODO: Hack to be removed when http://jira.sonarsource.com/browse/SONAR-6632 is fixed
   */
  private static StringBuilder generateValidatePropertyRuleDescription(boolean sizeLimit) {
    StringBuilder htmlPage = new StringBuilder()
      .append(
        "<p>Browsers ignore invalid property values. Review those invalid property values and either remove them if they are useless or update them.</p>\n")
      .append("<h2>Noncompliant Code Example</h2>\n")
      .append("<pre>\n")
      .append("/* 'word-spacing' validator is 'normal | &lt;length&gt;' */\n")
      .append(".mybox {\n")
      .append("  word-spacing: 10px;\n")
      .append("  word-spacing: normal;\n")
      .append("  word-spacing: 0;\n")
      .append("  word-spacing: inherit;\n")
      .append("  word-spacing: 10;       /* Noncompliant: Missing length unit */\n")
      .append("  word-spacing: abc;      /* Noncompliant: 'abc' value is not allowed */\n")
      .append("}\n")
      .append("</pre><h2>Implemented Validators</h2>\n")
      .append(
        "<p>Based on <a target=\"_blank\" href=\"http://www.w3.org/Style/CSS/current-work\">W3C CSS specifications</a> Level 1 or 2 or 3 which status is either Completed or Testing or Refining. Note that not all validators are implemented for now.</p>\n");
    if (sizeLimit) {
      htmlPage
        .append(
          "<p>See the <a target=\"_blank\" href=\"http://htmlpreview.github.io/?https://github.com/SonarCommunity/sonar-css/blob/master/doc/validators.html\">full list of implemented validators</a>.</p>");
    } else {
      htmlPage.append("<table border=1>\n")
        .append("  <thead>\n")
        .append("  <tr align=center>\n")
        .append("    <th>Name</th>\n")
        .append("    <th>Validator</th>\n")
        .append("  </tr>\n")
        .append("  </thead>\n");

      StandardProperty property;
      for (Map.Entry<String, StandardProperty> entry : getAllStandardProperties().entrySet()) {
        property = entry.getValue();
        if (!property.isObsolete()) {
          htmlPage.append("  <tr>\n").append("    <td nowrap=\"nowrap\">");
          if (!property.getLinks().isEmpty()) {
            htmlPage.append("<a target=\"_blank\" href=\"").append(property.getLinks().get(0)).append("\">");
          }
          htmlPage.append(property.getName());
          if (!property.getLinks().isEmpty()) {
            htmlPage.append("</a>");
          }
          htmlPage.append("</td>\n")
            .append("    <td>");
          if (property.getValidatorFormat().isEmpty()) {
            htmlPage.append("Not yet implemented");
          } else {
            htmlPage.append(property.getValidatorFormat()
              .replace(" | ",
                " <a target=\"_blank\" href=\"https://developer.mozilla.org/en-US/docs/Web/CSS/Value_definition_syntax#Single_bar\">|</a> ")
              .replace("||",
                "<a target=\"_blank\" href=\"https://developer.mozilla.org/en-US/docs/Web/CSS/Value_definition_syntax#Double_bar\">||</a>")
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
                "&lt;function&gt;</a>")
              .replace("<generic-family>",
                "<a target=\"_blank\" href=\"http://www.w3.org/TR/CSS2/fonts.html#value-def-generic-family\">&lt;generic-family&gt;</a>")
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
                "<a target=\"_blank\" href=\"\">&lt;shape-box&gt;</a>") // TODO: add link when W3C standard is back online
              .replace("<single-timing-function>",
                "<a target=\"_blank\" href=\"http://www.w3.org/TR/2012/WD-css3-transitions-20120403/#transition-timing-function\">&lt;single-timing-function&gt;</a>")
              .replace("<string>",
                "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-values-3/#string-value\">&lt;string&gt;</a>")
              .replace("<time>",
                "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css-values-3/#time-value\">&lt;time&gt;</a>")
              .replace("<width>",
                "<a target=\"_blank\" href=\"http://dev.w3.org/csswg/css2/visudet.html#propdef-width\">&lt;width&gt;</a>")
              .replace("<x>", "&lt;x&gt;")
              .replace("<y>", "&lt;y&gt;"));
          }
          htmlPage.append("</td>\n")
            .append("  </tr>\n");
        }
      }
      htmlPage.append("</table>\n");
    }
    return htmlPage;
  }

  private static void generateHtmlDescriptionFile(String path, StringBuilder htmlPage) {
    try {
      Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
      writer.write(htmlPage.toString());
      writer.close();
    } catch (IOException e) {
      throw new IllegalStateException("Could not generate the HTML description.", e);
    }
  }

  private static Map<String, StandardProperty> getAllStandardProperties() {
    try {
      Map<String, StandardProperty> properties = new TreeMap<>();
      StandardProperty property;
      ImmutableSet<ClassPath.ClassInfo> classInfos = ClassPath.from(Border.class.getClassLoader()).getTopLevelClasses("org.sonar.css.model.property.standard");
      for (ClassPath.ClassInfo classInfo : classInfos) {
        if (!"org.sonar.css.model.property.standard.package-info".equals(classInfo.getName())) {
          property = (StandardProperty) Class.forName(classInfo.getName()).newInstance();
          properties.put(property.getName(), property);
        }
      }
      return properties;
    } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      throw new IllegalStateException("Could not retrieve the list of standard CSS property classes.", e);
    }
  }

  private static Map<String, StandardAtRule> getAllStandardAtRules() {
    try {
      Map<String, StandardAtRule> atRules = new TreeMap<>();
      StandardAtRule atRule;
      ImmutableSet<ClassPath.ClassInfo> classInfos = ClassPath.from(Annotation.class.getClassLoader()).getTopLevelClasses("org.sonar.css.model.atrule.standard");
      for (ClassPath.ClassInfo classInfo : classInfos) {
        if (!"org.sonar.css.model.atrule.standard.package-info".equals(classInfo.getName())) {
          atRule = (StandardAtRule) Class.forName(classInfo.getName()).newInstance();
          atRules.put(atRule.getName(), atRule);
        }
      }
      return atRules;
    } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
      throw new IllegalStateException("Could not retrieve the list of standard CSS at rule classes.", e);
    }
  }

  private static Map<String, StandardFunction> getAllStandardFunctions() {
    try {
      Map<String, StandardFunction> functions = new TreeMap<>();
      StandardFunction function;
      ImmutableSet<ClassPath.ClassInfo> classInfos = ClassPath.from(Annotation.class.getClassLoader()).getTopLevelClasses("org.sonar.css.model.function.standard");
      for (ClassPath.ClassInfo classInfo : classInfos) {
        if (!"org.sonar.css.model.function.standard.package-info".equals(classInfo.getName())) {
          function = (StandardFunction) Class.forName(classInfo.getName()).newInstance();
          functions.put(function.getName(), function);
        }
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

}
