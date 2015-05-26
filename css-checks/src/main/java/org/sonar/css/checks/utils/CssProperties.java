/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.sonar.css.checks.validators.propertyValue.EnumValidator;
import org.sonar.css.checks.validators.propertyValue.InheritValidator;
import org.sonar.css.checks.validators.propertyValue.IntegerValidator;
import org.sonar.css.checks.validators.propertyValue.LengthValidator;
import org.sonar.css.checks.validators.propertyValue.NumberValidator;
import org.sonar.css.checks.validators.propertyValue.PercentageValidator;
import org.sonar.css.checks.validators.propertyValue.UriValidator;

import java.util.Collections;
import java.util.Map;

/**
 * Properties from https://github.com/stubbornella/csslint/blob/c31f1b9c89fa102eb89e96807be9d290110887e5/lib/parserlib.js
 * vendor mapping from: https://github.com/stubbornella/csslint/blob/master/src/rules/compatible-vendor-prefixes.js
 *
 * @author tkende
 */
public final class CssProperties {

  private static final InheritValidator inheritValidator = new InheritValidator();
  private static final IntegerValidator integerValidator = new IntegerValidator();
  private static final LengthValidator lengthValidator = new LengthValidator();
  private static final NumberValidator numberValidator = new NumberValidator();
  private static final PercentageValidator percentageValidator = new PercentageValidator();
  private static final UriValidator uriValidator = new UriValidator();

  public static final ImmutableList<String> VENDORS = ImmutableList.of(
    "-ah-",
    "-apple-",
    "-atsc-",
    "-epub-",
    "-hp-",
    "-icab-",
    "-khtml-",
    "-moz-",
    "-ms-",
    "mso-",
    "-o-",
    "prince-",
    "-rim-",
    "-ro-",
    "-tc-",
    "-webkit-",
    "-wap-"
    );

  public static final Map<String, CssProperty> PROPERTIES = new ImmutableMap.Builder<String, CssProperty>()

    // A
    .put("alignment-adjust", new CssProperty("alignment-adjust", Collections.<String>emptyList(), null))
    .put("alignment-baseline", new CssProperty("alignment-baseline", Collections.<String>emptyList(), null))
    .put("animation", new CssProperty("animation", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("animation-delay", new CssProperty("animation-delay", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("animation-direction", new CssProperty("animation-direction", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("animation-duration", new CssProperty("animation-duration", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("animation-fill-mode", new CssProperty("animation-fill-mode", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("animation-iteration-count", new CssProperty("animation-iteration-count", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("animation-name", new CssProperty("animation-name", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("animation-play-state", new CssProperty("animation-play-state", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("animation-timing-function", new CssProperty("animation-timing-function", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("appearance", new CssProperty("appearance", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("azimuth", new CssProperty("appearance", Collections.<String>emptyList(), null))

    // B
    .put("backface-visibility", new CssProperty("backface-visibility", Collections.<String>emptyList(), null))
    .put("background", new CssProperty("background", Collections.<String>emptyList(), null))
    .put("background-attachment", new CssProperty(
      "background-attachment",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("scroll", "fixed")), inheritValidator))
    )
    .put("background-clip", new CssProperty("background-clip", Collections.<String>emptyList(), null))
    .put("background-color", new CssProperty("background-color", Collections.<String>emptyList(), null))
    .put("background-image", new CssProperty(
      "background-image",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("none")), uriValidator, inheritValidator))
    )
    .put("background-origin", new CssProperty("background-origin", Collections.<String>emptyList(), null))
    .put("background-position", new CssProperty("background-position", Collections.<String>emptyList(), null))
    .put("background-repeat", new CssProperty(
      "background-repeat",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("repeat", "repeat-x", "repeat-y", "no-repeat")), inheritValidator))
    )
    .put("background-size", new CssProperty("background-size", Collections.<String>emptyList(), null))
    .put("baseline-shift", new CssProperty("baseline-shift", Collections.<String>emptyList(), null))
    .put("behavior", new CssProperty("behavior", Collections.<String>emptyList(), null))
    .put("binding", new CssProperty("binding", Collections.<String>emptyList(), null))
    .put("bleed", new CssProperty("bleed", Collections.<String>emptyList(), null))
    .put("bookmark-label", new CssProperty("bookmark-label", Collections.<String>emptyList(), null))
    .put("bookmark-level", new CssProperty("bookmark-level", Collections.<String>emptyList(), null))
    .put("bookmark-state", new CssProperty("bookmark-state", Collections.<String>emptyList(), null))
    .put("bookmark-target", new CssProperty("bookmark-target", Collections.<String>emptyList(), null))
    .put("border", new CssProperty("border", Collections.<String>emptyList(), null))
    .put("border-bottom", new CssProperty("border-bottom", Collections.<String>emptyList(), null))
    .put("border-bottom-color", new CssProperty("border-bottom-color", Collections.<String>emptyList(), null))
    .put("border-bottom-left-radius", new CssProperty("border-bottom-left-radius", Collections.<String>emptyList(), null))
    .put("border-bottom-right-radius", new CssProperty("border-bottom-right-radius", Collections.<String>emptyList(), null))
    .put("border-bottom-style", new CssProperty("border-bottom-style", Collections.<String>emptyList(), null))
    .put("border-bottom-width", new CssProperty("border-bottom-width", Collections.<String>emptyList(), null))
    .put("border-collapse", new CssProperty(
      "border-collapse",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("collapse", "separate")), inheritValidator))
    )
    .put("border-color", new CssProperty("border-color", Collections.<String>emptyList(), null))
    .put("border-end", new CssProperty("border-end", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("border-end-colo", new CssProperty("border-end-colo", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("border-end-style", new CssProperty("border-end-style", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("border-end-width", new CssProperty("border-end-width", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("border-image", new CssProperty("border-image", ImmutableList.of("-webkit-", "-moz-", "-o-"), null))
    .put("border-image-outset", new CssProperty("border-image-outset", Collections.<String>emptyList(), null))
    .put("border-image-repeat", new CssProperty("border-image-repeat", Collections.<String>emptyList(), null))
    .put("border-image-slice", new CssProperty("border-image-slice", Collections.<String>emptyList(), null))
    .put("border-image-source", new CssProperty("border-image-source", Collections.<String>emptyList(), null))
    .put("border-image-width", new CssProperty("border-image-width", Collections.<String>emptyList(), null))
    .put("border-left", new CssProperty("border-left", Collections.<String>emptyList(), null))
    .put("border-left-color", new CssProperty("border-left-color", Collections.<String>emptyList(), null))
    .put("border-left-style", new CssProperty("border-left-style", Collections.<String>emptyList(), null))
    .put("border-left-width", new CssProperty("border-left-width", Collections.<String>emptyList(), null))
    .put("border-radius", new CssProperty("border-radius", ImmutableList.of("-webkit-"), null))
    .put("border-right", new CssProperty("border-right", Collections.<String>emptyList(), null))
    .put("border-right-color", new CssProperty("border-right-color", Collections.<String>emptyList(), null))
    .put("border-right-style", new CssProperty("border-right-style", Collections.<String>emptyList(), null))
    .put("border-right-width", new CssProperty("border-right-width", Collections.<String>emptyList(), null))
    .put("border-start", new CssProperty("border-start", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("border-start-color", new CssProperty("border-start-color", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("border-start-style", new CssProperty("border-start-style", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("border-start-width", new CssProperty("border-start-width", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("border-spacing", new CssProperty("border-spacing", Collections.<String>emptyList(), null))
    .put("border-style", new CssProperty("border-style", Collections.<String>emptyList(), null))
    .put("border-top", new CssProperty("border-top", Collections.<String>emptyList(), null))
    .put("border-top-color", new CssProperty("border-top-color", Collections.<String>emptyList(), null))
    .put("border-top-left-radius", new CssProperty("border-top-left-radius", Collections.<String>emptyList(), null))
    .put("border-top-right-radius", new CssProperty("border-top-right-radius", Collections.<String>emptyList(), null))
    .put("border-top-style", new CssProperty("border-top-style", Collections.<String>emptyList(), null))
    .put("border-top-width", new CssProperty("border-top-width", Collections.<String>emptyList(), null))
    .put("border-width", new CssProperty("border-width", Collections.<String>emptyList(), null))
    .put("bottom", new CssProperty(
      "bottom",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("auto")), lengthValidator, percentageValidator, inheritValidator))
    )
    .put("box-align", new CssProperty("box-align", ImmutableList.of("-webkit-", "-moz-", "-ms-"), null))
    .put("box-decoration-break", new CssProperty("box-decoration-break", Collections.<String>emptyList(), null))
    .put("box-direction", new CssProperty("box-direction", ImmutableList.of("-webkit-", "-moz-", "-ms-"), null))
    .put("box-flex", new CssProperty("box-flex", ImmutableList.of("-webkit-", "-moz-", "-ms-"), null))
    .put("box-flex-group", new CssProperty("box-flex-group", Collections.<String>emptyList(), null))
    .put("box-lines", new CssProperty("box-lines", ImmutableList.of("-webkit-", "-ms-"), null))
    .put("box-ordinal-group", new CssProperty("box-ordinal-group", ImmutableList.of("-webkit-", "-moz-", "-ms-"), null))
    .put("box-orient", new CssProperty("box-orient", ImmutableList.of("-webkit-", "-moz-", "-ms-"), null))
    .put("box-pack", new CssProperty("box-pack", ImmutableList.of("-webkit-", "-moz-", "-ms-"), null))
    .put("box-shadow", new CssProperty("box-shadow", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("box-sizing", new CssProperty("box-sizing", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("break-after", new CssProperty("break-after", Collections.<String>emptyList(), null))
    .put("break-before", new CssProperty("break-before", Collections.<String>emptyList(), null))
    .put("break-inside", new CssProperty("break-inside", Collections.<String>emptyList(), null))

    // C
    .put("caption-side", new CssProperty(
      "caption-side",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("top", "bottom")), inheritValidator))
    )
    .put("clear", new CssProperty(
      "clear",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("none", "left", "right", "both")), inheritValidator))
    )
    .put("clip", new CssProperty("clip", Collections.<String>emptyList(), null))
    .put("color", new CssProperty("color", Collections.<String>emptyList(), null))
    .put("color-profile", new CssProperty("color-profile", Collections.<String>emptyList(), null))
    .put("column-count", new CssProperty("column-count", ImmutableList.of("-webkit-", "-moz-", "-ms-"), null))
    .put("column-fill", new CssProperty("column-fill", Collections.<String>emptyList(), null))
    .put("column-gap", new CssProperty("column-gap", ImmutableList.of("-webkit-", "-moz-", "-ms-"), null))
    .put("column-rule", new CssProperty("column-rule", ImmutableList.of("-webkit-", "-moz-", "-ms-"), null))
    .put("column-rule-color", new CssProperty("column-rule-color", ImmutableList.of("-webkit-", "-moz-", "-ms-"), null))
    .put("column-rule-style", new CssProperty("column-rule-style", ImmutableList.of("-webkit-", "-moz-", "-ms-"), null))
    .put("column-rule-width", new CssProperty("column-rule-width", ImmutableList.of("-webkit-", "-moz-", "-ms-"), null))
    .put("column-span", new CssProperty("column-span", Collections.<String>emptyList(), null))
    .put("column-width", new CssProperty("column-width", ImmutableList.of("-webkit-", "-moz-", "-ms-"), null))
    .put("columns", new CssProperty("columns", Collections.<String>emptyList(), null))
    .put("content", new CssProperty("content", Collections.<String>emptyList(), null))
    .put("counter-increment", new CssProperty("counter-increment", Collections.<String>emptyList(), null))
    .put("counter-reset", new CssProperty("counter-reset", Collections.<String>emptyList(), null))
    .put("crop", new CssProperty("crop", Collections.<String>emptyList(), null))
    .put("cue", new CssProperty(
      "cue",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("none")), uriValidator, inheritValidator))
    )
    .put("cue-after", new CssProperty(
      "cue-after", Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("none")), uriValidator, inheritValidator))
    )
    .put("column", new CssProperty("column", Collections.<String>emptyList(), null))
    .put("cue-before", new CssProperty("cue-before", Collections.<String>emptyList(), null))
    .put("cursor", new CssProperty("cursor", Collections.<String>emptyList(), null))

    // D
    .put("direction", new CssProperty(
      "direction", Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("ltr", "rtl")), inheritValidator))
    )
    .put("display", new CssProperty(
      "display",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList
        .of("inline", "block", "list-item", "inline-block", "table", "inline-table", "table-row-group", "table-header-group",
          "table-footer-group", "table-row", "table-column-group", "table-column", "table-cell", "table-caption", "none")),
        inheritValidator))
    )
    .put("dominant-baseline", new CssProperty("dominant-baseline", Collections.<String>emptyList(), null))
    .put("drop-initial-after-adjust", new CssProperty("drop-initial-after-adjust", Collections.<String>emptyList(), null))
    .put("drop-initial-after-align", new CssProperty("drop-initial-after-align", Collections.<String>emptyList(), null))
    .put("drop-initial-before-adjust", new CssProperty("drop-initial-before-adjust", Collections.<String>emptyList(), null))
    .put("drop-initial-before-align", new CssProperty("drop-initial-before-align", Collections.<String>emptyList(), null))
    .put("drop-initial-size", new CssProperty("drop-initial-size", Collections.<String>emptyList(), null))
    .put("drop-initial-value", new CssProperty("drop-initial-value", Collections.<String>emptyList(), null))

    // E
    .put("elevation", new CssProperty("elevation", Collections.<String>emptyList(), null))
    .put("empty-cells", new CssProperty(
      "empty-cells",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("show", "hide")), inheritValidator))
    )

    // F
    .put("filter", new CssProperty("filter", Collections.<String>emptyList(), null))
    .put("fit", new CssProperty("fit", Collections.<String>emptyList(), null))
    .put("fit-position", new CssProperty("fit-position", Collections.<String>emptyList(), null))
    .put("float", new CssProperty(
      "float",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("left", "right", "none")), inheritValidator))
    )
    .put("float-offset", new CssProperty("float-offset", Collections.<String>emptyList(), null))
    .put("font", new CssProperty("font", Collections.<String>emptyList(), null))
    .put("font-family", new CssProperty("font-family", Collections.<String>emptyList(), null))
    .put("font-size", new CssProperty("font-size", Collections.<String>emptyList(), null))
    .put("font-size-adjust", new CssProperty("font-size-adjust", Collections.<String>emptyList(), null))
    .put("font-stretch", new CssProperty("font-stretch", Collections.<String>emptyList(), null))
    .put("font-style", new CssProperty(
      "font-style",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("normal", "italic", "oblique")), inheritValidator))
    )
    .put("font-variant", new CssProperty(
      "font-variant",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("normal", "small-caps")), inheritValidator))
    )
    .put("font-weight", new CssProperty(
      "font-weight",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList
        .of("normal", "bold", "bolder", "lighter", "100", "200", "300", "400", "500", "600", "700", "800", "900"))))
    )

    // G
    .put("grid-cell-stacking", new CssProperty("grid-cell-stacking", Collections.<String>emptyList(), null))
    .put("grid-column", new CssProperty("grid-column", Collections.<String>emptyList(), null))
    .put("grid-columns", new CssProperty("grid-columns", Collections.<String>emptyList(), null))
    .put("grid-column-align", new CssProperty("grid-column-align", Collections.<String>emptyList(), null))
    .put("grid-column-sizing", new CssProperty("grid-column-sizing", Collections.<String>emptyList(), null))
    .put("grid-column-span", new CssProperty("grid-column-span", Collections.<String>emptyList(), null))
    .put("grid-flow", new CssProperty("grid-flow", Collections.<String>emptyList(), null))
    .put("grid-layer", new CssProperty("grid-layer", Collections.<String>emptyList(), null))
    .put("grid-row", new CssProperty("grid-row", Collections.<String>emptyList(), null))
    .put("grid-rows", new CssProperty("grid-rows", Collections.<String>emptyList(), null))
    .put("grid-row-align", new CssProperty("grid-row-align", Collections.<String>emptyList(), null))
    .put("grid-row-span", new CssProperty("grid-row-span", Collections.<String>emptyList(), null))
    .put("grid-row-sizing", new CssProperty("grid-row-sizing", Collections.<String>emptyList(), null))

    // H
    .put("hanging-punctuation", new CssProperty("hanging-punctuation", Collections.<String>emptyList(), null))
    .put("height", new CssProperty(
      "height",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("auto")), lengthValidator, percentageValidator))
    )
    .put("hyphenate-after", new CssProperty("hyphenate-after", Collections.<String>emptyList(), null))
    .put("hyphenate-before", new CssProperty("hyphenate-before", Collections.<String>emptyList(), null))
    .put("hyphenate-character", new CssProperty("hyphenate-character", Collections.<String>emptyList(), null))
    .put("hyphenate-lines", new CssProperty("hyphenate-lines", Collections.<String>emptyList(), null))
    .put("hyphenate-resource", new CssProperty("hyphenate-resource", Collections.<String>emptyList(), null))
    .put("hyphens", new CssProperty("hyphens", ImmutableList.of("-epub-", "-moz-"), null))

    // I
    .put("icon", new CssProperty("icon", Collections.<String>emptyList(), null))
    .put("image-orientation", new CssProperty("image-orientation", Collections.<String>emptyList(), null))
    .put("image-rendering", new CssProperty("image-rendering", Collections.<String>emptyList(), null))
    .put("inline-box-align", new CssProperty("inline-box-align", Collections.<String>emptyList(), null))

    // L
    .put("left", new CssProperty(
      "left",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("auto")), lengthValidator, percentageValidator))
    )
    .put("letter-spacing", new CssProperty(
      "letter-spacing",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("normal")), lengthValidator))
    )
    .put("line-height", new CssProperty(
      "line-height",
      Collections.<String>emptyList(),
      ImmutableList
        .of(new EnumValidator(ImmutableList.of("normal")), lengthValidator, percentageValidator, numberValidator))
    )
    .put("line-stacking", new CssProperty("line-stacking", Collections.<String>emptyList(), null))
    .put("line-stacking-ruby", new CssProperty("line-stacking-ruby", Collections.<String>emptyList(), null))
    .put("line-stacking-shift", new CssProperty("line-stacking-shift", Collections.<String>emptyList(), null))
    .put("line-stacking-strategy", new CssProperty("line-stacking-strategy", Collections.<String>emptyList(), null))
    .put("list-style", new CssProperty("list-style", Collections.<String>emptyList(), null))
    .put("list-style-image", new CssProperty(
      "list-style-image",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("none")), uriValidator))
    )
    .put("list-style-position", new CssProperty(
      "list-style-position",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("inside", "outside"))))
    )
    .put("list-style-type", new CssProperty(
      "list-style-type",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList
        .of("disc", "circle", "square", "decimal", "decimal-leading-zero", "lower-roman", "upper-roman", "lower-greek",
          "lower-latin", "upper-latin", "armenian", "georgian", "lower-alpha", "upper-alpha", "none"))))
    )

    // M
    .put("margin", new CssProperty("margin", Collections.<String>emptyList(), null))
    .put("margin-bottom", new CssProperty("margin-bottom", Collections.<String>emptyList(), null))
    .put("margin-end", new CssProperty("margin-end", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("margin-left", new CssProperty("margin-left", Collections.<String>emptyList(), null))
    .put("margin-right", new CssProperty("margin-right", Collections.<String>emptyList(), null))
    .put("margin-start", new CssProperty("margin-start", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("margin-top", new CssProperty("margin-top", Collections.<String>emptyList(), null))
    .put("mark", new CssProperty("mark", Collections.<String>emptyList(), null))
    .put("mark-after", new CssProperty("mark-after", Collections.<String>emptyList(), null))
    .put("mark-before", new CssProperty("mark-before", Collections.<String>emptyList(), null))
    .put("marks", new CssProperty("marks", Collections.<String>emptyList(), null))
    .put("marquee-direction", new CssProperty("marquee-direction", Collections.<String>emptyList(), null))
    .put("marquee-play-count", new CssProperty("marquee-play-count", Collections.<String>emptyList(), null))
    .put("marquee-speed", new CssProperty("marquee-speed", ImmutableList.of("-webkit-", "-wap-"), null))
    .put("marquee-style", new CssProperty("marquee-style", ImmutableList.of("-webkit-", "-wap-"), null))
    .put("max-height", new CssProperty(
      "max-height",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("none")), lengthValidator, percentageValidator))
    )
    .put("max-width", new CssProperty(
      "max-width",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("none")), lengthValidator, percentageValidator))
    )
    .put("min-height", new CssProperty(
      "min-height",
      Collections.<String>emptyList(),
      ImmutableList.of(lengthValidator, percentageValidator))
    )
    .put("min-width", new CssProperty(
      "min-width",
      Collections.<String>emptyList(),
      ImmutableList.of(lengthValidator, percentageValidator))
    )
    .put("move-to", new CssProperty("move-to", Collections.<String>emptyList(), null))

    // N
    .put("nav-down", new CssProperty("nav-down", Collections.<String>emptyList(), null))
    .put("nav-index", new CssProperty("nav-index", Collections.<String>emptyList(), null))
    .put("nav-left", new CssProperty("nav-left", Collections.<String>emptyList(), null))
    .put("nav-right", new CssProperty("nav-right", Collections.<String>emptyList(), null))
    .put("nav-up", new CssProperty("nav-up", Collections.<String>emptyList(), null))

    // O
    .put("opacity", new CssProperty("opacity", Collections.<String>emptyList(), null))
    .put("orphans", new CssProperty(
      "orphans",
      Collections.<String>emptyList(),
      ImmutableList.of(integerValidator))
    )
    .put("outline", new CssProperty("outline", Collections.<String>emptyList(), null))
    .put("outline-color", new CssProperty("outline-color", Collections.<String>emptyList(), null))
    .put("outline-offset", new CssProperty("outline-offset", Collections.<String>emptyList(), null))
    .put("outline-style", new CssProperty("outline-style", Collections.<String>emptyList(), null))
    .put("outline-width", new CssProperty("outline-width", Collections.<String>emptyList(), null))
    .put("overflow", new CssProperty(
      "overflow",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("visible", "hidden", "scroll", "auto"))))
    )
    .put("overflow-style", new CssProperty("overflow-style", Collections.<String>emptyList(), null))
    .put("overflow-x", new CssProperty("overflow-x", Collections.<String>emptyList(), null))
    .put("overflow-y", new CssProperty("overflow-y", Collections.<String>emptyList(), null))

    // P
    .put("padding", new CssProperty("padding", Collections.<String>emptyList(), null))
    .put("padding-bottom", new CssProperty("padding-bottom", Collections.<String>emptyList(), null))
    .put("padding-end", new CssProperty("padding-end", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("padding-left", new CssProperty("padding-left", Collections.<String>emptyList(), null))
    .put("padding-right", new CssProperty("padding-right", Collections.<String>emptyList(), null))
    .put("padding-start", new CssProperty("padding-start", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("padding-top", new CssProperty("padding-top", Collections.<String>emptyList(), null))
    .put("page", new CssProperty("page", Collections.<String>emptyList(), null))
    .put("page-break-after", new CssProperty(
      "page-break-after",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("auto", "always", "avoid", "left", "right"))))
    )
    .put("page-break-before", new CssProperty(
      "page-break-before",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("auto", "always", "avoid", "left", "right"))))
    )
    .put("page-break-inside", new CssProperty(
      "page-break-inside",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("avoid", "auto"))))
    )
    .put("page-policy", new CssProperty("page-policy", Collections.<String>emptyList(), null))
    .put("pause", new CssProperty("pause", Collections.<String>emptyList(), null))
    .put("pause-after", new CssProperty("pause-after", Collections.<String>emptyList(), null))
    .put("pause-before", new CssProperty("pause-before", Collections.<String>emptyList(), null))
    .put("perspective", new CssProperty("perspective", Collections.<String>emptyList(), null))
    .put("perspective-origin", new CssProperty("perspective-origin", Collections.<String>emptyList(), null))
    .put("phonemes", new CssProperty("phonemes", Collections.<String>emptyList(), null))
    .put("pitch", new CssProperty("pitch", Collections.<String>emptyList(), null))
    .put("pitch-range", new CssProperty(
      "pitch-range",
      Collections.<String>emptyList(),
      ImmutableList.of(numberValidator))
    )
    .put("play-during", new CssProperty("play-during", Collections.<String>emptyList(), null))
    .put("pointer-events", new CssProperty("pointer-events", Collections.<String>emptyList(), null))
    .put("position", new CssProperty(
      "position",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("static", "relative", "absolute", "fixed"))))
    )
    .put("presentation-level", new CssProperty("presentation-level", Collections.<String>emptyList(), null))
    .put("punctuation-trim", new CssProperty("punctuation-trim", Collections.<String>emptyList(), null))

    // Q
    .put("quotes", new CssProperty("quotes", Collections.<String>emptyList(), null))

    // R
    .put("rendering-intent", new CssProperty("rendering-intent", Collections.<String>emptyList(), null))
    .put("resize", new CssProperty("resize", Collections.<String>emptyList(), null))
    .put("rest", new CssProperty("rest", Collections.<String>emptyList(), null))
    .put("rest-after", new CssProperty("rest-after", Collections.<String>emptyList(), null))
    .put("rest-before", new CssProperty("rest-before", Collections.<String>emptyList(), null))
    .put("richness", new CssProperty(
      "richness",
      Collections.<String>emptyList(),
      ImmutableList.of(numberValidator))
    )
    .put("right", new CssProperty(
      "right",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("auto")), lengthValidator, percentageValidator))
    )
    .put("rotation", new CssProperty("rotation", Collections.<String>emptyList(), null))
    .put("rotation-point", new CssProperty("rotation-point", Collections.<String>emptyList(), null))
    .put("ruby-align", new CssProperty("ruby-align", Collections.<String>emptyList(), null))
    .put("ruby-overhang", new CssProperty("ruby-overhang", Collections.<String>emptyList(), null))
    .put("ruby-position", new CssProperty("ruby-position", Collections.<String>emptyList(), null))
    .put("ruby-span", new CssProperty("ruby-span", Collections.<String>emptyList(), null))

    // S
    .put("size", new CssProperty("size", Collections.<String>emptyList(), null))
    .put("speak", new CssProperty(
      "speak",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("normal", "none", "spell-out"))))
    )
    .put("speak-header", new CssProperty(
      "speak-header",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("once", "always"))))
    )
    .put("speak-numeral", new CssProperty(
      "speak-numeral",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("digits", "continuous"))))
    )
    .put("speak-punctuation", new CssProperty(
      "speak-punctuation",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("code", "none"))))
    )
    .put("speech-rate", new CssProperty(
      "speech-rate",
      Collections.<String>emptyList(),
      ImmutableList
        .of(new EnumValidator(ImmutableList.of("x-slow", "slow", "medium", "fast", "x-fast", "faster", "slower")),
          numberValidator))
    )
    .put("src", new CssProperty("src", Collections.<String>emptyList(), null))
    .put("stress", new CssProperty(
      "stress",
      Collections.<String>emptyList(),
      ImmutableList.of(numberValidator))
    )
    .put("string-set", new CssProperty("string-set", Collections.<String>emptyList(), null))

    // T
    .put("table-layout", new CssProperty(
      "table-layout",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("auto", "fixed"))))
    )
    .put("tab-size", new CssProperty("tab-size", ImmutableList.of("-moz-", "-o-"), null))
    .put("target", new CssProperty("target", Collections.<String>emptyList(), null))
    .put("target-name", new CssProperty("target-name", Collections.<String>emptyList(), null))
    .put("target-new", new CssProperty("target-new", Collections.<String>emptyList(), null))
    .put("target-position", new CssProperty("target-position", Collections.<String>emptyList(), null))
    .put("text-align", new CssProperty(
      "text-align",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("left", "right", "center", "justify"))))
    )
    .put("text-align-last", new CssProperty("text-align-last", Collections.<String>emptyList(), null))
    .put("text-decoration", new CssProperty("text-decoration", Collections.<String>emptyList(), null))
    .put("text-emphasis", new CssProperty("text-emphasis", Collections.<String>emptyList(), null))
    .put("text-height", new CssProperty("text-height", Collections.<String>emptyList(), null))
    .put("text-indent", new CssProperty(
      "text-indent",
      Collections.<String>emptyList(),
      ImmutableList.of(lengthValidator, percentageValidator))
    )
    .put("text-justify", new CssProperty("text-justify", Collections.<String>emptyList(), null))
    .put("text-outline", new CssProperty("text-outline", Collections.<String>emptyList(), null))
    .put("text-overflow", new CssProperty("text-overflow", Collections.<String>emptyList(), null))
    .put("text-rendering", new CssProperty("text-rendering", Collections.<String>emptyList(), null))
    .put("text-size-adjust", new CssProperty("text-size-adjust", ImmutableList.of("-webkit-", "-ms-"), null))
    .put("text-shadow", new CssProperty("text-shadow", Collections.<String>emptyList(), null))
    .put("text-transform", new CssProperty(
      "text-transform",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("capitalize", "uppercase", "lowercase", "none"))))
    )
    .put("text-wrap", new CssProperty("text-wrap", Collections.<String>emptyList(), null))
    .put("top", new CssProperty(
      "top",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("auto")), lengthValidator, percentageValidator))
    )
    .put("transform", new CssProperty(
      "transform",
      ImmutableList.of("-webkit-", "-moz-", "-ms-", "-o-"),
      null)
    )
    .put("transform-origin", new CssProperty("transform-origin", ImmutableList.of("-webkit-", "-moz-", "-ms-", "-o-"), null))
    .put("transform-style", new CssProperty("transform-style", Collections.<String>emptyList(), null))
    .put("transition", new CssProperty("transition", ImmutableList.of("-webkit-", "-moz-", "-o-"), null))
    .put("transition-delay", new CssProperty("transition-delay", ImmutableList.of("-webkit-", "-moz-", "-o-"), null))
    .put("transition-duration", new CssProperty("transition-duration", ImmutableList.of("-webkit-", "-moz-", "-o-"), null))
    .put("transition-property", new CssProperty("transition-property", ImmutableList.of("-webkit-", "-moz-", "-o-"), null))
    .put("transition-timing-function",
      new CssProperty("transition-timing-function", ImmutableList.of("-webkit-", "-moz-", "-o-"), null))

    // U
    .put("unicode-bidi", new CssProperty(
      "unicode-bidi",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("normal", "embed", "bidi-override"))))
    )
    .put("user-modify", new CssProperty("user-modify", ImmutableList.of("-webkit-", "-moz-"), null))
    .put("user-select", new CssProperty("user-select", ImmutableList.of("-webkit-", "-moz-", "-ms-"), null))

    // V
    .put("vertical-align", new CssProperty(
      "vertical-align",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(
        ImmutableList.of("baseline", "sub", "super", "top", "text-top", "middle", "bottom", "text-bottom")),
        percentageValidator, lengthValidator))
    )
    .put("visibility", new CssProperty(
      "visibility",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("visible", "hidden", "collapse"))))
    )
    .put("voice-balance", new CssProperty("voice-balance", Collections.<String>emptyList(), null))
    .put("voice-duration", new CssProperty("voice-duration", Collections.<String>emptyList(), null))
    .put("voice-family", new CssProperty("voice-family", Collections.<String>emptyList(), null))
    .put("voice-pitch", new CssProperty("voice-pitch", Collections.<String>emptyList(), null))
    .put("voice-pitch-range", new CssProperty("voice-pitch-range", Collections.<String>emptyList(), null))
    .put("voice-rate", new CssProperty("voice-rate", Collections.<String>emptyList(), null))
    .put("voice-stress", new CssProperty("voice-stress", Collections.<String>emptyList(), null))
    .put("voice-volume", new CssProperty("voice-volume", Collections.<String>emptyList(), null))
    .put("volume", new CssProperty(
      "volume",
      Collections.<String>emptyList(),
      ImmutableList
        .of(new EnumValidator(ImmutableList.of("silent", "x-soft", "soft", "medium", "loud", "x-loud")),
          numberValidator,
          percentageValidator))
    )

    // W
    .put("white-space", new CssProperty(
      "white-space",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("normal", "pre", "nowrap", "pre-wrap", "pre-line"))))
    )
    .put("white-space-collapse", new CssProperty("white-space-collapse", Collections.<String>emptyList(), null))
    .put("widows", new CssProperty(
      "widows",
      Collections.<String>emptyList(),
      ImmutableList.of(integerValidator))
    )
    .put("width", new CssProperty(
      "width",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("auto")), lengthValidator, percentageValidator))
    )
    .put("word-break", new CssProperty("word-break", ImmutableList.of("-epub-", "-ms-"), null))
    .put("word-spacing", new CssProperty(
      "word-spacing",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("normal")), lengthValidator, inheritValidator))
    )
    .put("word-wrap", new CssProperty("word-wrap", Collections.<String>emptyList(), null))
    .put("writing-mode", new CssProperty(
      "writing-mode",
      ImmutableList.of("-epub-", "-ms-"),
      ImmutableList.of(new EnumValidator(ImmutableList.of("horizontal-tb", "vertical-rl", "vertical-lr"))))
    )

    // Z
    .put("z-index", new CssProperty(
      "z-index",
      Collections.<String>emptyList(),
      ImmutableList.of(new EnumValidator(ImmutableList.of("auto")), integerValidator))
    )
    .put("zoom", new CssProperty("zoom", Collections.<String>emptyList(), null))
    .build();

  public static CssProperty getProperty(String property) {
    return PROPERTIES.get(property);
  }

  public static boolean isVendor(String property) {
    for (String v : VENDORS) {
      if (property.startsWith(v)) {
        return true;
      }
    }
    return false;
  }

  public static String getUnhackedProperty(String property) {
    return property.startsWith("*") || property.startsWith("_") ? property.substring(1) : property;
  }

  public static String getVendorPrefix(String property) {
    for (String v : VENDORS) {
      if (property.startsWith(v)) {
        return v;
      }
    }
    return null;
  }

  public static String getPropertyWithoutVendorPrefix(String property) {
    for (String v : VENDORS) {
      if (property.startsWith(v)) {
        return property.replaceAll("(" + v + ")(.*)", "$2");
      }
    }
    return property;
  }

}
