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
import org.sonar.css.checks.validators.PropertyValueValidatorFactory;
import org.sonar.css.checks.validators.base.EnumValidator;

import java.util.Map;

/**
 * Properties from https://github.com/stubbornella/csslint/blob/c31f1b9c89fa102eb89e96807be9d290110887e5/lib/parserlib.js
 * vendor mapping from: https://github.com/stubbornella/csslint/blob/master/src/rules/compatible-vendor-prefixes.js
 *
 * @author tkende
 */
public final class CssProperties {

  public static final Map<String, CssProperty> PROPERTIES = new ImmutableMap.Builder<String, CssProperty>()

    // A
    .put("alignment-adjust", new CssProperty("alignment-adjust")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("alignment-baseline", new CssProperty("alignment-baseline")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("all", new CssProperty("all")
      .addValidator(new EnumValidator(ImmutableList.of("inherit", "initial", "unset")))
    )

    .put("animation", new CssProperty("animation")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("animation-delay", new CssProperty("animation-delay")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("animation-direction", new CssProperty("animation-direction")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("animation-duration", new CssProperty("animation-duration")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("animation-fill-mode", new CssProperty("animation-fill-mode")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("animation-iteration-count", new CssProperty("animation-iteration-count")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("animation-name", new CssProperty("animation-name")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("animation-play-state", new CssProperty("animation-play-state")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("animation-timing-function", new CssProperty("animation-timing-function")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("appearance", new CssProperty("appearance")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("azimuth", new CssProperty("azimuth")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    // B
    .put("backface-visibility", new CssProperty("backface-visibility")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("background", new CssProperty("background")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("background-attachment", new CssProperty("background-attachment")
      .addValidator(new EnumValidator(ImmutableList.of("scroll", "fixed")))
    )

    .put("background-clip", new CssProperty("background-clip")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("background-color", new CssProperty("background-color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("background-image", new CssProperty("background-image")
      .addValidator(new EnumValidator(ImmutableList.of("none")))
      .addValidator(PropertyValueValidatorFactory.getUriValidator())
    )

    .put("background-origin", new CssProperty("background-origin")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("background-position", new CssProperty("background-position")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("background-repeat", new CssProperty("background-repeat")
      .addValidator(new EnumValidator(ImmutableList.of("repeat", "repeat-x", "repeat-y", "no-repeat")))
    )

    .put("background-size", new CssProperty("background-size")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("baseline-shift", new CssProperty("baseline-shift")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("behavior", new CssProperty("behavior")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("binding", new CssProperty("binding")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("bleed", new CssProperty("bleed")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("bookmark-label", new CssProperty("bookmark-label")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("bookmark-level", new CssProperty("bookmark-level")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("bookmark-state", new CssProperty("bookmark-state")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("bookmark-target", new CssProperty("bookmark-target")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border", new CssProperty("border")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-bottom", new CssProperty("border-bottom")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-bottom-color", new CssProperty("border-bottom-color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("border-bottom-left-radius", new CssProperty("border-bottom-left-radius")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-bottom-right-radius", new CssProperty("border-bottom-right-radius")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-bottom-style", new CssProperty("border-bottom-style")
      .addValidator(PropertyValueValidatorFactory.getBorderStyleValidator())
    )

    .put("border-bottom-width", new CssProperty("border-bottom-width")
      .addValidator(PropertyValueValidatorFactory.getBorderWidthValidator())
    )

    .put("border-collapse", new CssProperty("border-collapse")
      .addValidator(new EnumValidator(ImmutableList.of("collapse", "separate")))
    )

    .put("border-color", new CssProperty("border-color")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-end", new CssProperty("border-end")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-end-color", new CssProperty("border-end-color")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-end-style", new CssProperty("border-end-style")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-end-width", new CssProperty("border-end-width")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-image", new CssProperty("border-image")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-image-outset", new CssProperty("border-image-outset")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-image-repeat", new CssProperty("border-image-repeat")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-image-slice", new CssProperty("border-image-slice")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-image-source", new CssProperty("border-image-source")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-image-width", new CssProperty("border-image-width")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-left", new CssProperty("border-left")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-left-color", new CssProperty("border-left-color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("border-left-style", new CssProperty("border-left-style")
      .addValidator(PropertyValueValidatorFactory.getBorderStyleValidator())
    )

    .put("border-left-width", new CssProperty("border-left-width")
      .addValidator(PropertyValueValidatorFactory.getBorderWidthValidator())
    )

    .put("border-radius", new CssProperty("border-radius")
      .addVendor("-webkit-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-right", new CssProperty("border-right")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-right-color", new CssProperty("border-right-color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("border-right-style", new CssProperty("border-right-style")
      .addValidator(PropertyValueValidatorFactory.getBorderStyleValidator())
    )

    .put("border-right-width", new CssProperty("border-right-width")
      .addValidator(PropertyValueValidatorFactory.getBorderWidthValidator())
    )

    .put("border-start", new CssProperty("border-start")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())

    )

    .put("border-start-color", new CssProperty("border-start-color")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-start-style", new CssProperty("border-start-style")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-start-width", new CssProperty("border-start-width")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-spacing", new CssProperty("border-spacing")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-style", new CssProperty("border-style")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-top", new CssProperty("border-top")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-top-color", new CssProperty("border-top-color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("border-top-left-radius", new CssProperty("border-top-left-radius")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-top-right-radius", new CssProperty("border-top-right-radius")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("border-top-style", new CssProperty("border-top-style")
      .addValidator(PropertyValueValidatorFactory.getBorderStyleValidator())
    )

    .put("border-top-width", new CssProperty("border-top-width")
      .addValidator(PropertyValueValidatorFactory.getBorderWidthValidator())
    )

    .put("border-width", new CssProperty("border-width")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("bottom", new CssProperty("bottom")
      .addValidator(PropertyValueValidatorFactory.getAutoValueValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("box-align", new CssProperty("box-align")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("box-decoration-break", new CssProperty("box-decoration-break")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("box-direction", new CssProperty("box-direction")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("box-flex", new CssProperty("box-flex")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("box-flex-group", new CssProperty("box-flex-group")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("box-lines", new CssProperty("box-lines")
      .addVendor("-webkit-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("box-ordinal-group", new CssProperty("box-ordinal-group")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("box-orient", new CssProperty("box-orient")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("box-pack", new CssProperty("box-pack")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("box-shadow", new CssProperty("box-shadow")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("box-sizing", new CssProperty("box-sizing")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("break-after", new CssProperty("break-after")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("break-before", new CssProperty("break-before")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("break-inside", new CssProperty("break-inside")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    // C
    .put("caption-side", new CssProperty("caption-side")
      .addValidator(new EnumValidator(ImmutableList.of("top", "bottom")))
    )

    .put("clear", new CssProperty("clear")
      .addValidator(new EnumValidator(ImmutableList.of("none", "left", "right", "both")))
    )

    .put("clip", new CssProperty("clip")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )
    .put("color", new CssProperty("color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )
    .put("color-profile", new CssProperty("color-profile")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("column-count", new CssProperty("column-count")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("column-fill", new CssProperty("column-fill")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("column-gap", new CssProperty("column-gap")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("column-rule", new CssProperty("column-rule")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("column-rule-color", new CssProperty("column-rule-color")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("column-rule-style", new CssProperty("column-rule-style")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("column-rule-width", new CssProperty("column-rule-width")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("column-span", new CssProperty("column-span")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("column-width", new CssProperty("column-width")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("columns", new CssProperty("columns")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("content", new CssProperty("content")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("counter-increment", new CssProperty("counter-increment")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("counter-reset", new CssProperty("counter-reset")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("crop", new CssProperty("crop")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("cue", new CssProperty("cue")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("cue-after", new CssProperty("cue-after")
      .addValidator(new EnumValidator(ImmutableList.of("none")))
      .addValidator(PropertyValueValidatorFactory.getUriValidator())
    )

    .put("column", new CssProperty("column")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("cue-before", new CssProperty("cue-before")
      .addValidator(new EnumValidator(ImmutableList.of("none")))
      .addValidator(PropertyValueValidatorFactory.getUriValidator())
    )

    .put("cursor", new CssProperty("cursor")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    // D
    .put("direction", new CssProperty("direction")
      .addValidator(new EnumValidator(ImmutableList.of("ltr", "rtl")))
    )

    .put("display", new CssProperty("display")
      .addValidator(new EnumValidator(
        ImmutableList.of("inline", "block", "list-item", "inline-block", "table", "inline-table", "table-row-group",
          "table-header-group",
          "table-footer-group", "table-row", "table-column-group", "table-column", "table-cell",
          "table-caption",
          "none")))
    )

    .put("dominant-baseline", new CssProperty("dominant-baseline")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("drop-initial-after-adjust", new CssProperty("drop-initial-after-adjust")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("drop-initial-after-align", new CssProperty("drop-initial-after-align")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("drop-initial-before-adjust", new CssProperty("drop-initial-before-adjust")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("drop-initial-before-align", new CssProperty("drop-initial-before-align")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("drop-initial-size", new CssProperty("drop-initial-size")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("drop-initial-value", new CssProperty("drop-initial-value")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    // E
    .put("elevation", new CssProperty("elevation")
      .addValidator(PropertyValueValidatorFactory.getAngleValidator())
      .addValidator(new EnumValidator(ImmutableList.of("below", "level", "above", "higher", "lower")))
    )

    .put("empty-cells", new CssProperty("empty-cells")
      .addValidator(new EnumValidator(ImmutableList.of("show", "hide")))
    )

    // F
    .put("filter", new CssProperty("filter")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("fit", new CssProperty("fit")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("fit-position", new CssProperty("fit-position")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("float", new CssProperty("float")
      .addValidator(new EnumValidator(ImmutableList.of("left", "right", "none")))
    )

    .put("float-offset", new CssProperty("float-offset")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("font", new CssProperty("font")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("font-family", new CssProperty("font-family")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("font-size", new CssProperty("font-size")
      .addValidator(PropertyValueValidatorFactory.getFontSizeValidator())
    )

    .put("font-size-adjust", new CssProperty("font-size-adjust")
      .addValidator(new EnumValidator(ImmutableList.of("none")))
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
    )

    .put("font-stretch", new CssProperty("font-stretch")
      .addValidator(new EnumValidator(
        ImmutableList.of("normal", "ultra-condensed", "extra-condensed", "condensed", "semi-condensed",
          "semi-expanded", "expanded", "extra-expanded", "ultra-expanded")))
    )

    .put("font-style", new CssProperty("font-style")
      .addValidator(new EnumValidator(ImmutableList.of("normal", "italic", "oblique")))
    )

    .put("font-variant", new CssProperty("font-variant").
      addValidator(new EnumValidator(ImmutableList.of("normal", "small-caps")))
    )

    .put("font-weight", new CssProperty("font-weight")
      .addValidator(new EnumValidator(ImmutableList
        .of("normal", "bold", "bolder", "lighter", "100", "200", "300", "400", "500", "600", "700", "800",
          "900")))
    )

    // G
    .put("grid-cell-stacking", new CssProperty("grid-cell-stacking")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("grid-column", new CssProperty("grid-column")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("grid-columns", new CssProperty("grid-columns")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("grid-column-align", new CssProperty("grid-column-align")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("grid-column-sizing", new CssProperty("grid-column-sizing")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("grid-column-span", new CssProperty("grid-column-span")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("grid-flow", new CssProperty("grid-flow")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("grid-layer", new CssProperty("grid-layer")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("grid-row", new CssProperty("grid-row")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("grid-rows", new CssProperty("grid-rows")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("grid-row-align", new CssProperty("grid-row-align")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("grid-row-span", new CssProperty("grid-row-span")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("grid-row-sizing", new CssProperty("grid-row-sizing")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    // H
    .put("hanging-punctuation", new CssProperty("hanging-punctuation")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("height", new CssProperty("height")
      .addValidator(PropertyValueValidatorFactory.getAutoValueValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("hyphenate-after", new CssProperty("hyphenate-after")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("hyphenate-before", new CssProperty("hyphenate-before")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("hyphenate-character", new CssProperty("hyphenate-character")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("hyphenate-lines", new CssProperty("hyphenate-lines")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("hyphenate-resource", new CssProperty("hyphenate-resource")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("hyphens", new CssProperty("hyphens")
      .addVendor("-epub-")
      .addVendor("-moz-")
      .addValidator(new EnumValidator(ImmutableList.of("none", "manual", "auto")))
    )

    // I
    .put("icon", new CssProperty("icon")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("image-orientation", new CssProperty("image-orientation")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("image-rendering", new CssProperty("image-rendering")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("inline-box-align", new CssProperty("inline-box-align")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    // L
    .put("left", new CssProperty("left")
      .addValidator(PropertyValueValidatorFactory.getAutoValueValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("letter-spacing", new CssProperty("letter-spacing").
      addValidator(new EnumValidator(ImmutableList.of("normal")))
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
    )

    .put("line-height", new CssProperty("line-height")
      .addValidator(new EnumValidator(ImmutableList.of("normal")))
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
    )

    .put("line-stacking", new CssProperty("line-stacking")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("line-stacking-ruby", new CssProperty("line-stacking-ruby")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("line-stacking-shift", new CssProperty("line-stacking-shift")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("line-stacking-strategy", new CssProperty("line-stacking-strategy")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("list-style", new CssProperty("list-style")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("list-style-image", new CssProperty("list-style-image")
      .addValidator(new EnumValidator(ImmutableList.of("none")))
      .addValidator(PropertyValueValidatorFactory.getUriValidator())
    )

    .put("list-style-position", new CssProperty("list-style-position")
      .addValidator(new EnumValidator(ImmutableList.of("inside", "outside")))
    )

    .put("list-style-type", new CssProperty("list-style-type")
      .addValidator(new EnumValidator(ImmutableList
        .of("disc", "circle", "square", "decimal", "decimal-leading-zero", "lower-roman", "upper-roman",
          "lower-greek",
          "lower-latin", "upper-latin", "armenian", "georgian", "lower-alpha", "upper-alpha", "none")))
    )

    // M
    .put("margin", new CssProperty("margin")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("margin-bottom", new CssProperty("margin-bottom")
      .addValidator(PropertyValueValidatorFactory.getMarginWidthValidator())
    )

    .put("margin-end", new CssProperty("margin-end")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("margin-left", new CssProperty("margin-left")
      .addValidator(PropertyValueValidatorFactory.getMarginWidthValidator())
    )

    .put("margin-right", new CssProperty("margin-right")
      .addValidator(PropertyValueValidatorFactory.getMarginWidthValidator())
    )

    .put("margin-start", new CssProperty("margin-start")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("margin-top", new CssProperty("margin-top")
      .addValidator(PropertyValueValidatorFactory.getMarginWidthValidator())
    )

    .put("mark", new CssProperty("mark")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("mark-after", new CssProperty("mark-after")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("mark-before", new CssProperty("mark-before")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("marks", new CssProperty("marks")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("marquee-direction", new CssProperty("marquee-direction")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("marquee-play-count", new CssProperty("marquee-play-count")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("marquee-speed", new CssProperty("marquee-speed")
      .addVendor("-webkit-")
      .addVendor("-wap-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("marquee-style", new CssProperty("marquee-style")
      .addVendor("-webkit-")
      .addVendor("-wap-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("max-height", new CssProperty("max-height")
      .addValidator(new EnumValidator(ImmutableList.of("none")))
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("max-width", new CssProperty("max-width")
      .addValidator(new EnumValidator(ImmutableList.of("none")))
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("min-height", new CssProperty("min-height")
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("min-width", new CssProperty("min-width")
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("move-to", new CssProperty("move-to")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    // N
    .put("nav-down", new CssProperty("nav-down")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("nav-index", new CssProperty("nav-index")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("nav-left", new CssProperty("nav-left")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("nav-right", new CssProperty("nav-right")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("nav-up", new CssProperty("nav-up")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    // O
    .put("opacity", new CssProperty("opacity")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("orphans", new CssProperty("orphans")
      .addValidator(PropertyValueValidatorFactory.getIntegerValidator())
    )

    .put("outline", new CssProperty("outline")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("outline-color", new CssProperty("outline-color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
      .addValidator(new EnumValidator(ImmutableList.of("invert")))
    )

    .put("outline-offset", new CssProperty("outline-offset")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("outline-style", new CssProperty("outline-style")
      .addValidator(PropertyValueValidatorFactory.getOutlineStyleValidator())
    )

    .put("outline-width", new CssProperty("outline-width")
      .addValidator(PropertyValueValidatorFactory.getBorderWidthValidator())
    )

    .put("overflow", new CssProperty("overflow")
      .addValidator(new EnumValidator(ImmutableList.of("visible", "hidden", "scroll", "auto")))
    )

    .put("overflow-style", new CssProperty("overflow-style")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("overflow-x", new CssProperty("overflow-x")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("overflow-y", new CssProperty("overflow-y")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    // P
    .put("padding", new CssProperty("padding")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("padding-bottom", new CssProperty("padding-bottom")
      .addValidator(PropertyValueValidatorFactory.getPaddingWidthValidator())
    )

    .put("padding-end", new CssProperty("padding-end")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("padding-left", new CssProperty("padding-left")
      .addValidator(PropertyValueValidatorFactory.getPaddingWidthValidator())
    )

    .put("padding-right", new CssProperty("padding-right")
      .addValidator(PropertyValueValidatorFactory.getPaddingWidthValidator())
    )

    .put("padding-start", new CssProperty("padding-start")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("padding-top", new CssProperty("padding-top")
      .addValidator(PropertyValueValidatorFactory.getPaddingWidthValidator())
    )

    .put("page", new CssProperty("page")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("page-break-after", new CssProperty("page-break-after")
      .addValidator(PropertyValueValidatorFactory.getPageBreakValidator())
    )

    .put("page-break-before", new CssProperty("page-break-before")
      .addValidator(PropertyValueValidatorFactory.getPageBreakValidator())
    )

    .put("page-break-inside", new CssProperty("page-break-inside")
      .addValidator(new EnumValidator(ImmutableList.of("avoid", "auto")))
    )

    .put("page-policy", new CssProperty("page-policy")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("pause", new CssProperty("pause")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("pause-after", new CssProperty("pause-after")
      .addValidator(PropertyValueValidatorFactory.getPauseValidator())
    )

    .put("pause-before", new CssProperty("pause-before")
      .addValidator(PropertyValueValidatorFactory.getPauseValidator())
    )

    .put("perspective", new CssProperty("perspective")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("perspective-origin", new CssProperty("perspective-origin")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("phonemes", new CssProperty("phonemes")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("pitch", new CssProperty("pitch")
      .addValidator(PropertyValueValidatorFactory.getPitchValidator())
    )

    .put("pitch-range", new CssProperty("pitch-range")
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
    )

    .put("play-during", new CssProperty("play-during")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("pointer-events", new CssProperty("pointer-events")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("position", new CssProperty("position")
      .addValidator(new EnumValidator(ImmutableList.of("static", "relative", "absolute", "fixed")))
    )

    .put("presentation-level", new CssProperty("presentation-level")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("punctuation-trim", new CssProperty("punctuation-trim")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    // Q
    .put("quotes", new CssProperty("quotes")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    // R
    .put("rendering-intent", new CssProperty("rendering-intent")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("resize", new CssProperty("resize")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("rest", new CssProperty("rest")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("rest-after", new CssProperty("rest-after")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("rest-before", new CssProperty("rest-before")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("richness", new CssProperty("richness")
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
    )

    .put("right", new CssProperty("right")
      .addValidator(PropertyValueValidatorFactory.getAutoValueValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("rotation", new CssProperty("rotation")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("rotation-point", new CssProperty("rotation-point")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("ruby-align", new CssProperty("ruby-align")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("ruby-overhang", new CssProperty("ruby-overhang")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )
    .put("ruby-position", new CssProperty("ruby-position")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )
    .put("ruby-span", new CssProperty("ruby-span")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    // S
    .put("size", new CssProperty("size")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("speak", new CssProperty("speak")
      .addValidator(new EnumValidator(ImmutableList.of("normal", "none", "spell-out")))
    )

    .put("speak-header", new CssProperty("speak-header")
      .addValidator(new EnumValidator(ImmutableList.of("once", "always")))
    )

    .put("speak-numeral", new CssProperty("speak-numeral")
      .addValidator(new EnumValidator(ImmutableList.of("digits", "continuous")))
    )

    .put("speak-punctuation", new CssProperty("speak-punctuation")
      .addValidator(new EnumValidator(ImmutableList.of("code", "none")))
    )

    .put("speech-rate", new CssProperty("speech-rate")
      .addValidator(
        new EnumValidator(ImmutableList.of("x-slow", "slow", "medium", "fast", "x-fast", "faster", "slower")))
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
    )

    .put("src", new CssProperty("src")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("stress", new CssProperty("stress")
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
    )
    .put("string-set", new CssProperty("string-set")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    // T
    .put("table-layout", new CssProperty("table-layout")
      .addValidator(new EnumValidator(ImmutableList.of("auto", "fixed")))
    )

    .put("tab-size", new CssProperty("tab-size")
      .addVendor("-moz-")
      .addVendor("-o-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("target", new CssProperty("target")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )
    .put("target-name", new CssProperty("target-name")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )
    .put("target-new", new CssProperty("target-new")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )
    .put("target-position", new CssProperty("target-position")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("text-align", new CssProperty("text-align")
      .addValidator(new EnumValidator(ImmutableList.of("left", "right", "center", "justify")))
    )

    .put("text-align-last", new CssProperty("text-align-last")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )
    .put("text-decoration", new CssProperty("text-decoration")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )
    .put("text-emphasis", new CssProperty("text-emphasis")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )
    .put("text-height", new CssProperty("text-height")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("text-indent", new CssProperty("text-indent")
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("text-justify", new CssProperty("text-justify")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )
    .put("text-outline", new CssProperty("text-outline")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )
    .put("text-overflow", new CssProperty("text-overflow")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )
    .put("text-rendering", new CssProperty("text-rendering")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("text-size-adjust", new CssProperty("text-size-adjust")
      .addVendor("-webkit-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("text-shadow", new CssProperty("text-shadow")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("text-transform", new CssProperty("text-transform")
      .addValidator(new EnumValidator(ImmutableList.of("capitalize", "uppercase", "lowercase", "none")))
    )

    .put("text-wrap", new CssProperty("text-wrap")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("top", new CssProperty("top")
      .addValidator(PropertyValueValidatorFactory.getAutoValueValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("transform", new CssProperty("transform")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addVendor("-o-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("transform-origin", new CssProperty("transform-origin")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addVendor("-o-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("transform-style", new CssProperty("transform-style")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("transition", new CssProperty("transition")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("transition-delay", new CssProperty("transition-delay")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("transition-duration", new CssProperty("transition-duration")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("transition-property", new CssProperty("transition-property")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("transition-timing-function", new CssProperty("transition-timing-function")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    // U
    .put("unicode-bidi", new CssProperty("unicode-bidi")
      .addValidator(new EnumValidator(ImmutableList.of("normal", "embed", "bidi-override")))
    )

    .put("user-modify", new CssProperty("user-modify")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("user-select", new CssProperty("user-select")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    // V
    .put("vertical-align", new CssProperty("vertical-align")
      .addValidator(new EnumValidator(
        ImmutableList.of("baseline", "sub", "super", "top", "text-top", "middle", "bottom", "text-bottom")))
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
    )

    .put("visibility", new CssProperty("visibility")
      .addValidator(new EnumValidator(ImmutableList.of("visible", "hidden", "collapse")))
    )

    .put("voice-balance", new CssProperty("voice-balance")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("voice-duration", new CssProperty("voice-duration")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("voice-family", new CssProperty("voice-family")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("voice-pitch", new CssProperty("voice-pitch")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("voice-pitch-range", new CssProperty("voice-pitch-range")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("voice-rate", new CssProperty("voice-rate")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("voice-stress", new CssProperty("voice-stress")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("voice-volume", new CssProperty("voice-volume")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("volume", new CssProperty("volume")
      .addValidator(new EnumValidator(ImmutableList.of("silent", "x-soft", "soft", "medium", "loud", "x-loud")))
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    // W
    .put("white-space", new CssProperty("white-space")
      .addValidator(new EnumValidator(ImmutableList.of("normal", "pre", "nowrap", "pre-wrap", "pre-line")))
    )

    .put("white-space-collapse", new CssProperty("white-space-collapse")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("widows", new CssProperty("widows")
      .addValidator(PropertyValueValidatorFactory.getIntegerValidator())
    )

    .put("width", new CssProperty("width")
      .addValidator(PropertyValueValidatorFactory.getAutoValueValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("word-break", new CssProperty("word-break")
      .addVendor("-epub-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())

    )

    .put("word-spacing", new CssProperty("word-spacing")
      .addValidator(new EnumValidator(ImmutableList.of("normal")))
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
    )

    .put("word-wrap", new CssProperty("word-wrap")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .put("writing-mode", new CssProperty("writing-mode")
      .addVendor("-epub-")
      .addVendor("-ms-")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    // Z
    .put("z-index", new CssProperty("z-index")
      .addValidator(PropertyValueValidatorFactory.getAutoValueValidator())
      .addValidator(PropertyValueValidatorFactory.getIntegerValidator())
    )

    .put("zoom", new CssProperty("zoom")
      .addValidator(PropertyValueValidatorFactory.getNotYetImplementedValidator())
    )

    .build();

  public static CssProperty getProperty(String propertyName) {
    return PROPERTIES.get(propertyName);
  }

  public static String getUnhackedPropertyName(String propertyName) {
    return propertyName.startsWith("*") || propertyName.startsWith("_") ? propertyName.substring(1) : propertyName;
  }

  public static String getVendorPrefix(String propertyName) {
    for (String v : Vendors.VENDORS) {
      if (propertyName.startsWith(v)) {
        return v;
      }
    }
    return null;
  }

  public static String getPropertyNameWithoutVendorPrefix(String propertyName) {
    for (String v : Vendors.VENDORS) {
      if (propertyName.startsWith(v)) {
        return propertyName.replaceAll("(" + v + ")(.*)", "$2");
      }
    }
    return propertyName;
  }

}
