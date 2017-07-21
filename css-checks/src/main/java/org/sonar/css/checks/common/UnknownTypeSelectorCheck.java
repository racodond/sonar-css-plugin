/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2017 David RACODON
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
package org.sonar.css.checks.common;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.checks.CheckList;
import org.sonar.css.checks.CheckUtils;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.css.TypeSelectorTree;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Rule(
  key = "unknown-type-selector",
  name = "Unknown type selectors should be removed",
  priority = Priority.CRITICAL,
  tags = {Tags.BUG})
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class UnknownTypeSelectorCheck extends DoubleDispatchVisitorCheck {

  // Extracted from https://html.spec.whatwg.org/multipage/index.html#toc-semantics
  public static final List<String> KNOWN_HTML_TAGS = ImmutableList.of(
    "a", "abbr", "address", "area", "article", "aside", "audio", "b", "base", "bdi", "bdo", "blockquote", "body", "br",
    "button", "canvas", "caption", "cite", "code", "col", "colgroup", "data", "datalist", "dd", "del", "details", "dfn",
    "dialog", "div", "dl", "dt", "em", "embed", "fieldset", "figcaption", "figure", "footer", "form", "h1", "h2", "h3",
    "h4", "h5", "h6", "head", "header", "hgroup", "hr", "html", "i", "iframe", "img", "input", "ins", "kbd", "label",
    "legend", "li", "link", "main", "map", "mark", "maxlength", "menu", "menuitem", "meta", "meter", "minlength", "nav",
    "noscript", "object", "ol", "optgroup", "option", "output", "p", "param", "picture", "pre", "progress", "q", "rp",
    "rt", "ruby", "s", "samp", "script", "section", "select", "slot", "small", "source", "span", "strong", "style",
    "sub", "summary", "sup", "table", "tbody", "td", "template", "textarea", "tfoot", "th", "thead", "time", "title",
    "tr", "track", "u", "ul", "var", "video", "wbr");

  // Extracted from https://www.w3.org/TR/SVG/eltindex.html
  public static final List<String> KNOWN_SVG_TAGS = ImmutableList.of(
    "a", "altGlyph", "altGlyphDef", "altGlyphItem", "animate", "animateColor", "animateMotion", "animateTransform",
    "circle", "clipPath", "color-profile", "cursor", "defs", "desc", "ellipse", "feBlend", "feColorMatrix",
    "feComponentTransfer", "feComposite", "feConvolveMatrix", "feDiffuseLighting", "feDisplacementMap",
    "feDistantLight", "feFlood", "feFuncA", "feFuncB", "feFuncG", "feFuncR", "feGaussianBlur", "feImage", "feMerge",
    "feMergeNode", "feMorphology", "feOffset", "fePointLight", "feSpecularLighting", "feSpotLight", "feTile",
    "feTurbulence", "filter", "font", "font-face", "font-face-format", "font-face-name", "font-face-src",
    "font-face-uri", "foreignObject", "g", "glyph", "glyphRef", "hkern", "image", "line", "linearGradient", "marker",
    "mask", "metadata", "missing-glyph", "mpath", "path", "pattern", "polygon", "polyline", "radialGradient", "rect",
    "script", "set", "stop", "style", "svg", "switch", "symbol", "text", "textPath", "title", "tref", "tspan", "use",
    "view", "vkern");

  private static final String UNIVERSAL_SELECTOR = "*";

  private static final String DEFAULT_EXCLUSIONS = "";
  @RuleProperty(
    key = "Exclusions",
    description = "Regular expression to exclude custom type selectors. See "
      + CheckUtils.LINK_TO_JAVA_REGEX_PATTERN_DOC
      + " for detailed regular expression syntax.",
    defaultValue = DEFAULT_EXCLUSIONS)
  private String exclusions = DEFAULT_EXCLUSIONS;

  @Override
  public void visitTypeSelector(TypeSelectorTree tree) {
    if (!tree.identifier().isInterpolated()
      && !KNOWN_HTML_TAGS.contains(tree.identifier().text().toLowerCase())
      && !KNOWN_SVG_TAGS.contains(tree.identifier().text().toLowerCase())
      && !UNIVERSAL_SELECTOR.equalsIgnoreCase(tree.identifier().text())
      && !isAngularJSMaterialsTypeSelector(tree)
      && !isCustomTypeSelector(tree)) {
      addPreciseIssue(
        tree.identifier(),
        "Remove this usage of the unknown \"" + tree.identifier().text() + "\" type selector.");
    }
    super.visitTypeSelector(tree);
  }

  @VisibleForTesting
  void setExclusions(String exclusions) {
    this.exclusions = exclusions;
  }

  @Override
  public void validateParameters() {
    try {
      Pattern.compile(exclusions);
    } catch (PatternSyntaxException exception) {
      throw new IllegalStateException(paramsErrorMessage(), exception);
    }
  }

  private String paramsErrorMessage() {
    return CheckUtils.paramsErrorMessage(
      this.getClass(),
      CheckList.CSS_REPOSITORY_KEY,
      MessageFormat.format("exclusions parameter \"{0}\" is not a valid regular expression.", exclusions));
  }

  private boolean isAngularJSMaterialsTypeSelector(TypeSelectorTree tree) {
    return tree.identifier().text().startsWith("md-");
  }

  private boolean isCustomTypeSelector(TypeSelectorTree tree) {
    return tree.identifier().text().matches(exclusions);
  }

}
