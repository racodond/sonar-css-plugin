/*
 * SonarQube CSS / SCSS / Less Analyzer
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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.sonar.css.checks.common.CaseCheck;
import org.sonar.css.model.StandardCssObject;
import org.sonar.css.model.StandardCssObjectFactory;
import org.sonar.css.model.Vendor;
import org.sonar.css.model.atrule.StandardAtRule;
import org.sonar.css.model.function.StandardFunction;
import org.sonar.css.model.function.StandardFunctionFactory;
import org.sonar.css.model.property.StandardProperty;
import org.sonar.css.model.property.StandardPropertyFactory;
import org.sonar.css.model.pseudo.StandardPseudoComponent;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RuleDescriptionsGenerator {

  private static final String UTF_8 = "UTF-8";

  private static final Map<String, String> CSS_OBJECT_LINKS = ImmutableMap.<String, String>builder()
    .put("||", "https://developer.mozilla.org/en-US/docs/Web/CSS/Value_definition_syntax#Double_bar")
    .put("|", "https://developer.mozilla.org/en-US/docs/Web/CSS/Value_definition_syntax#Single_bar")
    .put("&&", "https://developer.mozilla.org/en-US/docs/Web/CSS/Value_definition_syntax#Double_ampersand")
    .put("?", "https://developer.mozilla.org/en-US/docs/Web/CSS/Value_definition_syntax#Question_mark_()")
    .put("+", "https://developer.mozilla.org/en-US/docs/Web/CSS/Value_definition_syntax#Plus_()")
    .put("*", "https://developer.mozilla.org/en-US/docs/Web/CSS/Value_definition_syntax#Asterisk_(*)")
    .put("[", "https://developer.mozilla.org/en-US/docs/Web/CSS/Value_definition_syntax#Brackets")
    .put("]", "https://developer.mozilla.org/en-US/docs/Web/CSS/Value_definition_syntax#Brackets")
    .put("<angle>", "http://dev.w3.org/csswg/css-values-3/#angle-value")
    .put("<basic-shape>", "http://dev.w3.org/csswg/css-shapes/#typedef-basic-shape")
    .put("<border-style>", "http://dev.w3.org/csswg/css-backgrounds-3/#border-style")
    .put("<border-width>", "http://dev.w3.org/csswg/css-backgrounds-3/#border-width")
    .put("<box>", "http://dev.w3.org/csswg/css-backgrounds-3/#box")
    .put("<color>", "http://dev.w3.org/csswg/css-color/#typedef-color")
    .put("<counter-style>", "http://dev.w3.org/csswg/css-counter-styles-3/#typedef-counter-style")
    .put("<cue-after>", "http://www.w3.org/TR/css3-speech/#cue-after")
    .put("<cue-before>", "http://www.w3.org/TR/css3-speech/#cue-before")
    .put("<family-name>", "https://www.w3.org/TR/CSS22/fonts.html#value-def-family-name")
    .put("<filter-function>", "http://dev.w3.org/fxtf/filters/#typedef-filter-function")
    .put("<flex-direction>", "http://dev.w3.org/csswg/css-flexbox-1/#propdef-flex-direction")
    .put("<flex-wrap>", "http://dev.w3.org/csswg/css-flexbox-1/#propdef-flex-wrap")
    .put("<frequency>", "http://dev.w3.org/csswg/css-values-3/#frequency-value")
    .put("<function>", "https://wiki.csswg.org/ideas/functional-notation")
    .put("<generic-family>", "https://www.w3.org/TR/CSS22/fonts.html#value-def-generic-family")
    .put("<id>", "https://drafts.csswg.org/css-ui/#typedef-id")
    .put("<identifier>", "http://dev.w3.org/csswg/css-values-3/#identifier-value")
    .put("<image>", "https://drafts.csswg.org/css-images-3/#typedef-image")
    .put("<integer>", "http://dev.w3.org/csswg/css-values-3/#integer-value")
    .put("<length>", "http://dev.w3.org/csswg/css-values-3/#length-value")
    .put("<line-stacking-ruby>", "http://www.w3.org/TR/css3-linebox/#line-stacking-ruby")
    .put("<line-stacking-shift>", "http://www.w3.org/TR/css3-linebox/#line-stacking-shift")
    .put("<line-stacking-strategy>", "http://www.w3.org/TR/css3-linebox/#line-stacking-strategy")
    .put("<list-style-position>", "http://dev.w3.org/csswg/css-lists-3/#propdef-list-style-position")
    .put("<list-style-image>", "http://dev.w3.org/csswg/css-lists-3/#propdef-list-style-image")
    .put("<list-style-type>", "http://dev.w3.org/csswg/css-lists-3/#propdef-list-style-type")
    .put("<margin-width>", "http://www.w3.org/TR/CSS2/box.html#value-def-margin-width")
    .put("<number>", "http://dev.w3.org/csswg/css-values-3/#number-value")
    .put("<outline-color>", "http://www.w3.org/TR/CSS2/ui.html#propdef-outline-color")
    .put("<outline-style>", "http://www.w3.org/TR/CSS2/ui.html#propdef-outline-style")
    .put("<outline-width>", "http://www.w3.org/TR/CSS2/ui.html#propdef-outline-width")
    .put("<padding-width>", "http://www.w3.org/TR/CSS2/box.html#value-def-padding-width")
    .put("<pause-after>", "https://drafts.csswg.org/css-speech-1/#pause-after")
    .put("<pause-before>", "https://drafts.csswg.org/css-speech-1/#pause-before")
    .put("<percentage>", "http://dev.w3.org/csswg/css-values-3/#percentage-value")
    .put("<resolution>", "http://dev.w3.org/csswg/css-values-3/#resolution-value")
    .put("<uri>", "http://dev.w3.org/csswg/css-values-3/#url-value")
    .put("<single-animation-name>", "http://www.w3.org/TR/css3-animations/#single-animation-name")
    .put("<single-animation-direction>", "http://www.w3.org/TR/css3-animations/#single-animation-direction")
    .put("<single-animation-fill-mode>", "http://www.w3.org/TR/css3-animations/#single-animation-fill-mode")
    .put("<single-animation-iteration-count>", "http://www.w3.org/TR/css3-animations/#single-animation-iteration-count")
    .put("<single-animation-play-state>", "http://www.w3.org/TR/css3-animations/#single-animation-play-state")
    .put("<shape-box>", "https://drafts.csswg.org/css-shapes/#typedef-shape-box")
    .put("<single-timing-function>", "http://www.w3.org/TR/2012/WD-css3-transitions-20120403/#transition-timing-function")
    .put("<string>", "http://dev.w3.org/csswg/css-values-3/#string-value")
    .put("<target-name>", "https://drafts.csswg.org/css-ui/#typedef-target-name")
    .put("<time>", "http://dev.w3.org/csswg/css-values-3/#time-value")
    .put("<transform-function>", "https://drafts.csswg.org/css-transforms/#typedef-transform-function")
    .put("<width>", "http://dev.w3.org/csswg/css2/visudet.html#propdef-width")
    .build();

  private final Map<String, String> tags = ImmutableMap.<String, String>builder()
    .put("[[allProperties]]", generateHtmlTable(StandardCssObjectFactory.getStandardCssObjects(StandardProperty.class, o -> true)))
    .put("[[allCssFunctions]]", generateHtmlCssFunctionTable(
      StandardFunctionFactory.getAll().stream().filter(StandardFunction::isCss).sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList())))
    .put("[[allLessFunctions]]", generateHtmlLessFunctionTable(
      StandardFunctionFactory.getAll().stream().filter(StandardFunction::isLess).sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList())))
    .put("[[allAtRules]]", generateHtmlTable(StandardCssObjectFactory.getStandardCssObjects(StandardAtRule.class, o -> true)))
    .put("[[allPseudos]]", generateHtmlTable(StandardCssObjectFactory.getStandardCssObjects(StandardPseudoComponent.class, o -> true)))
    .put("[[experimentalProperties]]", generateHtmlTable(StandardCssObjectFactory.getStandardCssObjects(StandardProperty.class, StandardCssObject::isExperimental)))
    .put("[[experimentalCssFunctions]]", generateHtmlCssFunctionTable(
      StandardFunctionFactory.getAll().stream().filter(f -> f.isCss() && f.isExperimental()).sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList())))
    .put("[[experimentalNotLessFunctions]]", generateHtmlCssFunctionTable(
      StandardFunctionFactory.getAll().stream().filter(f -> !f.isLess() && f.isExperimental()).sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
        .collect(Collectors.toList())))
    .put("[[experimentalAtRules]]", generateHtmlTable(StandardCssObjectFactory.getStandardCssObjects(StandardAtRule.class, StandardCssObject::isExperimental)))
    .put("[[experimentalPseudos]]", generateHtmlTable(StandardCssObjectFactory.getStandardCssObjects(StandardPseudoComponent.class, StandardCssObject::isExperimental)))
    .put("[[functionCaseExceptions]]", generateHtmlFunctionCaseExceptionsTable(CaseCheck.FUNCTION_CASE_EXCEPTIONS.stream().sorted((o1, o2) -> o1.toLowerCase().compareTo(o2.toLowerCase())).collect(Collectors.toList())))
    .put("[[obsoleteProperties]]", generateHtmlTable(StandardCssObjectFactory.getStandardCssObjects(StandardProperty.class, StandardCssObject::isObsolete)))
    .put("[[obsoleteCssFunctions]]", generateHtmlCssFunctionTable(
      StandardFunctionFactory.getAll().stream().filter(f -> f.isCss() && f.isObsolete()).sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList())))
    .put("[[obsoleteNotLessFunctions]]", generateHtmlCssFunctionTable(
      StandardFunctionFactory.getAll().stream().filter(f -> !f.isLess() && f.isObsolete()).sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList())))
    .put("[[obsoleteAtRules]]", generateHtmlTable(StandardCssObjectFactory.getStandardCssObjects(StandardAtRule.class, StandardCssObject::isObsolete)))
    .put("[[obsoletePseudos]]", generateHtmlTable(StandardCssObjectFactory.getStandardCssObjects(StandardPseudoComponent.class, StandardCssObject::isObsolete)))
    .put("[[vendors]]", generateListOfVendors())
    .put("[[vendorPrefixedProperties]]", generateVendorPrefixedPropertiesHtmlTable())
    .put("[[propertyValidators]]", generateValidatorsHtmlTable())
    .put("[[shorthandProperties]]", generateShorthandPropertiesHtmlTable())
    .build();

  public void generateHtmlRuleDescription(String templatePath, String outputPath) throws IOException {
    try (OutputStream fileOutputStream = new FileOutputStream(outputPath)) {
      Writer writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, UTF_8));
      writer.write(replaceTags(FileUtils.readFileToString(new File(templatePath), UTF_8)));
      writer.flush();
      writer.close();
    } catch (IOException e) {
      throw new IllegalStateException("Could not generate the HTML description.", e);
    }
  }

  private String generateValidatorsHtmlTable() {
    StringBuilder description = new StringBuilder();

    StandardProperty property;
    for (StandardCssObject cssObject : StandardCssObjectFactory.getStandardCssObjects(StandardProperty.class, o -> !o.isObsolete())) {
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
        description.append(replaceLinks(property.getValidatorFormat()));
      }
      description.append("</td>\n")
        .append("<td align=center>");
      if (property.isExperimental()) {
        description.append("Experimental");
      } else {
        description.append("Supported");
      }
      description
        .append("</td>\n")
        .append("  </tr>\n");
    }
    return description.toString();
  }

  private String generateVendorPrefixedPropertiesHtmlTable() {
    StringBuilder description = new StringBuilder();

    StandardProperty property;
    for (StandardCssObject cssObject : StandardCssObjectFactory.getStandardCssObjects(StandardProperty.class, StandardCssObject::hasVendors)) {
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
      description
        .append("</td>\n")
        .append("<td>\n");
      for (Vendor vendor : property.getVendors().stream().sorted((o1, o2) -> o1.getPrefix().compareTo(o2.getPrefix())).collect(Collectors.toList())) {
        description.append("<code>").append(vendor.getPrefix()).append("</code>").append("&nbsp;&nbsp;");
      }
      description
        .append("</td>\n")
        .append("</tr>\n");
    }
    return description.toString();
  }

  private String generateShorthandPropertiesHtmlTable() {
    StringBuilder description = new StringBuilder();

    List<StandardProperty> shorthandProperties = StandardPropertyFactory.getAll()
      .stream()
      .filter(StandardProperty::isShorthand)
      .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
      .collect(Collectors.toList());

    for (StandardProperty property : shorthandProperties) {
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
      description
        .append("</td>\n")
        .append("<td>\n");
      for (String shorthandFor : property.getShorthandForPropertyNames().stream().sorted().collect(Collectors.toList())) {
        description.append("<code>").append(shorthandFor).append("</code>").append("<br>");
      }
      description
        .append("</td>\n")
        .append("</tr>\n");
    }
    return description.toString();
  }

  private String generateListOfVendors() {
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

  // TODO: refactor generateHtmlCssFunctionTable and generateHtmlLessFunctionTable
  private String generateHtmlCssFunctionTable(List<StandardFunction> standardFunctions) {
    StringBuilder html = new StringBuilder("<table style=\"border: 0;\">\n");
    List<List<StandardFunction>> subLists = Lists.partition(standardFunctions, 3);
    for (List<StandardFunction> subList : subLists) {
      html.append("<tr>");
      for (StandardFunction standardCssFunction : subList) {
        List<String> links = standardCssFunction.getLinks().stream().filter(f -> !f.contains("lesscss.org")).collect(Collectors.toList());
        html.append("<td style=\"border: 0; \">");
        if (!links.isEmpty()) {
          html.append("<a target=\"_blank\" href=\"").append(links.get(0)).append("\">");
        }
        html.append("<code>").append(standardCssFunction.getName()).append("</code>");
        if (!links.isEmpty()) {
          html.append("</a>");
        }
        html.append("</code>");
        for (int i = 1; i < links.size(); i++) {
          html.append("&nbsp;&nbsp;<a target=\"_blank\" href=\"").append(links.get(i)).append("\">#").append(i + 1).append("</a>");
        }
        html.append("</td>\n");
      }
      html.append("</tr>");
    }
    html.append("</table>\n");
    return html.toString();
  }

  private String generateHtmlLessFunctionTable(List<StandardFunction> standardFunctions) {
    StringBuilder html = new StringBuilder("<table style=\"border: 0;\">\n");
    List<List<StandardFunction>> subLists = Lists.partition(standardFunctions, 3);
    for (List<StandardFunction> subList : subLists) {
      html.append("<tr>");
      for (StandardFunction standardCssFunction : subList) {
        List<String> links = standardCssFunction.getLinks().stream().filter(f -> f.contains("lesscss.org")).collect(Collectors.toList());
        html.append("<td style=\"border: 0; \">");
        if (!links.isEmpty()) {
          html.append("<a target=\"_blank\" href=\"").append(links.get(0)).append("\">");
        }
        html.append("<code>").append(standardCssFunction.getName()).append("</code>");
        if (!links.isEmpty()) {
          html.append("</a>");
        }
        html.append("</code>");
        for (int i = 1; i < links.size(); i++) {
          html.append("&nbsp;&nbsp;<a target=\"_blank\" href=\"").append(links.get(i)).append("\">#").append(i + 1).append("</a>");
        }
        html.append("</td>\n");
      }
      html.append("</tr>");
    }
    html.append("</table>\n");
    return html.toString();
  }

  private String generateHtmlFunctionCaseExceptionsTable(List<String> functions) {
    StringBuilder html = new StringBuilder("<table style=\"border: 0;\">\n");
    List<List<String>> subLists = Lists.partition(functions, 3);
    for (List<String> subList : subLists) {
      html.append("<tr>");
      for (String function : subList) {
        html.append("<td style=\"border: 0; \">");
        html.append("<code>").append(function).append("</code>");
        html.append("</td>\n");
      }
      html.append("</tr>");
    }
    html.append("</table>\n");
    return html.toString();
  }

  private String generateHtmlTable(List<StandardCssObject> standardCssObjects) {
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

  private String replaceTags(String rawDescription) {
    String description = rawDescription;
    for (Map.Entry<String, String> tag : tags.entrySet()) {
      description = description.replace(tag.getKey(), tag.getValue());
    }
    return description;
  }

  private String replaceLinks(String rawValidator) {
    String validator = rawValidator;
    for (Map.Entry<String, String> link : CSS_OBJECT_LINKS.entrySet()) {
      validator = validator.replace(
        link.getKey(),
        "<a target=\"_blank\" href=\"" + link.getValue() + "\">" + StringEscapeUtils.escapeHtml(link.getKey()) + "</a>");
    }
    return validator;
  }

}
