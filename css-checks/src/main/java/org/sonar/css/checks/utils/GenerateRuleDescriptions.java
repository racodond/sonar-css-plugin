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

import java.io.*;
import java.util.Iterator;
import java.util.Map;

public class GenerateRuleDescriptions {

  private GenerateRuleDescriptions() {
  }

  public static void main(String... args) throws Exception {
    boolean sizeLimit = false;
    if ("sizeLimit".equals(args[0])) {
      sizeLimit = true;
    }
    generateHtmlDescriptionFile("css-checks/target/classes/org/sonar/l10n/css/rules/css/validate-property-value.html", getValidatePropertyValueDescription(sizeLimit));
    generateHtmlDescriptionFile("doc/validators.html", getValidatePropertyValueDescription(false));
    generateHtmlDescriptionFile("css-checks/target/classes/org/sonar/l10n/css/rules/css/obsolete-properties.html", getObsoletePropertiesDescription());
  }

  private static StringBuilder getObsoletePropertiesDescription() {
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
    Iterator it = CssProperties.PROPERTIES.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry) it.next();
      CssProperty property = (CssProperty) pair.getValue();
      if (property.isObsolete()) {
        htmlPage.append("  <li>");
        if (property.getUrl() != null) {
          htmlPage.append("<a target=\"_blank\" href=\"").append(property.getUrl()).append("\">");
        }
        htmlPage.append(property.getName());
        if (property.getUrl() != null) {
          htmlPage.append("</a>");
        }
        htmlPage.append("  </li>\n");
      }
    }
    htmlPage.append("</ul>\n");
    return htmlPage;
  }

  /**
   * @param sizeLimit TODO: Hack to be removed when http://jira.sonarsource.com/browse/SONAR-6632 is fixed
   */
  private static StringBuilder getValidatePropertyValueDescription(boolean sizeLimit) {
    StringBuilder htmlPage = new StringBuilder()
      .append(
        "<p>Browsers ignore invalid property values. Review those invalid property values and either remove them if they are useless or update them.</p>\n")
      .append("<h2>Noncompliant Code Example</h2>\n")
      .append("<pre>\n")
      .append("/* 'word-spacing' validator is 'normal | &lt;length&gt;' */\n")
      .append(".mybox {\n")
      .append("  word-spacing: 10px\n")
      .append("  word-spacing: normal\n")
      .append("  word-spacing: 0\n")
      .append("  word-spacing: inherit\n")
      .append("  word-spacing: 10       /* Noncompliant: Missing length unit */\n")
      .append("  word-spacing: abc      /* Noncompliant: 'abc' value is not allowed */\n")
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
      Iterator it = CssProperties.PROPERTIES.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry pair = (Map.Entry) it.next();
        CssProperty property = (CssProperty) pair.getValue();
        if (!property.isObsolete()) {
          htmlPage.append("  <tr>\n").append("    <td nowrap=\"nowrap\">");
          if (property.getUrl() != null) {
            htmlPage.append("<a target=\"_blank\" href=\"").append(property.getUrl()).append("\">");
          }
          htmlPage.append(property.getName());
          if (property.getUrl() != null) {
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
              .replace("<y>", "&lt;y&gt;")
              );
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
      Writer writer = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(path), "utf-8"));
      writer.write(htmlPage.toString());
      writer.close();
    } catch (IOException e) {
      throw new RuntimeException("Could not generate the HTML description.", e);
    }
  }

}
