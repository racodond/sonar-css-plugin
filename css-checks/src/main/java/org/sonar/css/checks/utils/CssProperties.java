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
import org.sonar.css.checks.validators.property.CuePropertyValidator;
import org.sonar.css.checks.validators.property.FilterValidator;
import org.sonar.css.checks.validators.property.FontWeightValidator;
import org.sonar.css.checks.validators.property.MarginValidator;
import org.sonar.css.checks.validators.property.OutlineValidator;
import org.sonar.css.checks.validators.property.PaddingValidator;
import org.sonar.css.checks.validators.property.PausePropertyValidator;
import org.sonar.css.checks.validators.property.ShapeOutsideValidator;
import org.sonar.css.checks.validators.property.animation.AnimationDelayValidator;
import org.sonar.css.checks.validators.property.animation.AnimationDirectionValidator;
import org.sonar.css.checks.validators.property.animation.AnimationDurationValidator;
import org.sonar.css.checks.validators.property.animation.AnimationFillModeValidator;
import org.sonar.css.checks.validators.property.animation.AnimationIterationCountValidator;
import org.sonar.css.checks.validators.property.animation.AnimationNameValidator;
import org.sonar.css.checks.validators.property.animation.AnimationPlayStateValidator;
import org.sonar.css.checks.validators.property.animation.AnimationTimingFunctionValidator;
import org.sonar.css.checks.validators.property.animation.AnimationValidator;
import org.sonar.css.checks.validators.property.background.BackgroundAttachmentValidator;
import org.sonar.css.checks.validators.property.background.BackgroundClipValidator;
import org.sonar.css.checks.validators.property.background.BackgroundOriginValidator;
import org.sonar.css.checks.validators.property.background.BackgroundRepeatValidator;
import org.sonar.css.checks.validators.property.background.BackgroundSizeValidator;
import org.sonar.css.checks.validators.property.border.BorderColorValidator;
import org.sonar.css.checks.validators.property.border.BorderRadiusPropertyValidator;
import org.sonar.css.checks.validators.property.border.BorderRadiusValidator;
import org.sonar.css.checks.validators.property.border.BorderSpacingValidator;
import org.sonar.css.checks.validators.property.border.BorderStylePropertyValidator;
import org.sonar.css.checks.validators.property.border.BorderValidator;
import org.sonar.css.checks.validators.property.border.BorderWidthPropertyValidator;
import org.sonar.css.checks.validators.property.line.LineStackingRubyValidator;
import org.sonar.css.checks.validators.property.line.LineStackingShiftValidator;
import org.sonar.css.checks.validators.property.line.LineStackingStrategyValidator;
import org.sonar.css.checks.validators.property.line.LineStackingValidator;
import org.sonar.css.checks.validators.valueelement.BorderStyleValidator;
import org.sonar.css.checks.validators.valueelement.BorderWidthValidator;
import org.sonar.css.checks.validators.valueelement.BoxValidator;
import org.sonar.css.checks.validators.valueelement.CueValidator;
import org.sonar.css.checks.validators.valueelement.IdentifierValidator;
import org.sonar.css.checks.validators.valueelement.ImageValidator;
import org.sonar.css.checks.validators.valueelement.OutlineColorValidator;
import org.sonar.css.checks.validators.valueelement.OutlineStyleValidator;
import org.sonar.css.checks.validators.valueelement.OutlineWidthValidator;
import org.sonar.css.checks.validators.valueelement.dimension.LengthValidator;
import org.sonar.css.checks.validators.valueelement.function.FunctionValidator;
import org.sonar.css.checks.validators.valueelement.numeric.NumberRangeValidator;
import org.sonar.css.checks.validators.valueelement.numeric.PercentageValidator;

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
    .put(
      "alignment-adjust",
      new CssProperty("alignment-adjust")
        .addValidator(
          new IdentifierValidator(ImmutableList
            .of("auto", "baseline", "before-edge", "text-before-edge", "middle", "central", "after-edge", "text-after-edge",
              "ideographic",
              "alphabetic", "hanging", "mathematical")))
        .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
        .addValidator(PropertyValueValidatorFactory.getLengthValidator())
    )

    .put("alignment-baseline", new CssProperty("alignment-baseline")
      .addValidator(new IdentifierValidator(ImmutableList.of("baseline", "use-script", "before-edge", "text-before-edge",
        "after-edge", "text-after-edge", "central", "middle", "ideographic", "alphabetic", "hanging", "mathematical")))
    )

    .put("all", new CssProperty("all")
      .addValidator(new IdentifierValidator(ImmutableList.of("inherit", "initial", "unset")))
    )

    .put("animation", new CssProperty("animation")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationValidator())
    )

    .put("animation-delay", new CssProperty("animation-delay")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationDelayValidator())
    )

    .put("animation-direction", new CssProperty("animation-direction")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationDirectionValidator())
    )

    .put("animation-duration", new CssProperty("animation-duration")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationDurationValidator())
    )

    .put("animation-fill-mode", new CssProperty("animation-fill-mode")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationFillModeValidator())
    )

    .put("animation-iteration-count", new CssProperty("animation-iteration-count")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationIterationCountValidator())
    )

    .put("animation-name", new CssProperty("animation-name")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationNameValidator())
    )

    .put("animation-play-state", new CssProperty("animation-play-state")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationPlayStateValidator())
    )

    .put("animation-timing-function", new CssProperty("animation-timing-function")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationTimingFunctionValidator())
    )

    .put("appearance", new CssProperty("appearance")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(
        new IdentifierValidator(ImmutableList
          .of("normal", "icon", "window", "desktop", "workspace", "document", "tooltip", "dialog", "button",
            "push-button",
            "hyperlink", "radio-button", "checkbox", "menu-item", "tab", "menu", "menubar", "pull-down-menu",
            "pop-up-menu",
            "list-menu", "radio-group", "checkbox-group", "outline-tree", "range", "field", "combo-box", "signature",
            "password")))
    )

    .put("azimuth", new CssProperty("azimuth"))

    // B
    .put("backface-visibility", new CssProperty("backface-visibility")
      .addValidator(new IdentifierValidator(ImmutableList.of("visible", "hidden")))
    )

    .put("background", new CssProperty("background")
    )

    .put("background-attachment", new CssProperty("background-attachment")
      .addValidator(new BackgroundAttachmentValidator())
    )

    .put("background-clip", new CssProperty("background-clip")
      .addValidator(new BackgroundClipValidator())
    )

    .put("background-color", new CssProperty("background-color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("background-image", new CssProperty("background-image")
      .addValidator(PropertyValueValidatorFactory.getNoneValidator())
      .addValidator(new ImageValidator())
    )

    .put("background-origin", new CssProperty("background-origin")
      .addValidator(new BackgroundOriginValidator())
    )

    .put("background-position", new CssProperty("background-position")
    )

    .put("background-repeat", new CssProperty("background-repeat")
      .addValidator(new BackgroundRepeatValidator())
    )

    .put("background-size", new CssProperty("background-size")
      .addValidator(new BackgroundSizeValidator())
    )

    .put("baseline-shift", new CssProperty("baseline-shift")
      .addValidator(new IdentifierValidator(ImmutableList.of("baseline", "sub", "super")))
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("behavior", new CssProperty("behavior")

    )

    .put("binding", new CssProperty("binding")

    )

    .put("bleed", new CssProperty("bleed")

    )

    .put("bookmark-label", new CssProperty("bookmark-label")

    )

    .put("bookmark-level", new CssProperty("bookmark-level")

    )

    .put("bookmark-state", new CssProperty("bookmark-state")

    )

    .put("bookmark-target", new CssProperty("bookmark-target")

    )

    .put("border", new CssProperty("border")
      .addValidator(new BorderValidator())
    )

    .put("border-bottom", new CssProperty("border-bottom")
      .addValidator(new BorderValidator())
    )

    .put("border-bottom-color", new CssProperty("border-bottom-color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("border-bottom-left-radius", new CssProperty("border-bottom-left-radius")
      .addValidator(new BorderRadiusValidator())
    )

    .put("border-bottom-right-radius", new CssProperty("border-bottom-right-radius")
      .addValidator(new BorderRadiusValidator())
    )

    .put("border-bottom-style", new CssProperty("border-bottom-style")
      .addValidator(new BorderStyleValidator())
    )

    .put("border-bottom-width", new CssProperty("border-bottom-width")
      .addValidator(new BorderWidthValidator())
    )

    .put("border-collapse", new CssProperty("border-collapse")
      .addValidator(new IdentifierValidator(ImmutableList.of("collapse", "separate")))
    )

    .put("border-color", new CssProperty("border-color")
      .addValidator(new BorderColorValidator())
    )

    .put("border-end", new CssProperty("border-end")
      .addVendor("-webkit-")
      .addVendor("-moz-")

    )

    .put("border-end-color", new CssProperty("border-end-color")
      .addVendor("-webkit-")
      .addVendor("-moz-")

    )

    .put("border-end-style", new CssProperty("border-end-style")
      .addVendor("-webkit-")
      .addVendor("-moz-")

    )

    .put("border-end-width", new CssProperty("border-end-width")
      .addVendor("-webkit-")
      .addVendor("-moz-")

    )

    .put("border-image", new CssProperty("border-image")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-")

    )

    .put("border-image-outset", new CssProperty("border-image-outset")

    )

    .put("border-image-repeat", new CssProperty("border-image-repeat")

    )

    .put("border-image-slice", new CssProperty("border-image-slice")

    )

    .put("border-image-source", new CssProperty("border-image-source")

    )

    .put("border-image-width", new CssProperty("border-image-width")

    )

    .put("border-left", new CssProperty("border-left")
      .addValidator(new BorderValidator())
    )

    .put("border-left-color", new CssProperty("border-left-color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("border-left-style", new CssProperty("border-left-style")
      .addValidator(new BorderStyleValidator())
    )

    .put("border-left-width", new CssProperty("border-left-width")
      .addValidator(new BorderWidthValidator())
    )

    .put("border-radius", new CssProperty("border-radius")
      .addVendor("-webkit-")
      .addValidator(new BorderRadiusPropertyValidator())
    )

    .put("border-right", new CssProperty("border-right")
      .addValidator(new BorderValidator())
    )

    .put("border-right-color", new CssProperty("border-right-color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("border-right-style", new CssProperty("border-right-style")
      .addValidator(new BorderStyleValidator())
    )

    .put("border-right-width", new CssProperty("border-right-width")
      .addValidator(new BorderWidthValidator())
    )

    .put("border-start", new CssProperty("border-start")
      .addVendor("-webkit-")
      .addVendor("-moz-")

    )

    .put("border-start-color", new CssProperty("border-start-color")
      .addVendor("-webkit-")
      .addVendor("-moz-")

    )

    .put("border-start-style", new CssProperty("border-start-style")
      .addVendor("-webkit-")
      .addVendor("-moz-")

    )

    .put("border-start-width", new CssProperty("border-start-width")
      .addVendor("-webkit-")
      .addVendor("-moz-")

    )

    .put("border-spacing", new CssProperty("border-spacing")
      .addValidator(new BorderSpacingValidator())
    )

    .put("border-style", new CssProperty("border-style")
      .addValidator(new BorderStylePropertyValidator())
    )

    .put("border-top", new CssProperty("border-top")
      .addValidator(new BorderValidator())
    )

    .put("border-top-color", new CssProperty("border-top-color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("border-top-left-radius", new CssProperty("border-top-left-radius")
      .addValidator(new BorderRadiusValidator())
    )

    .put("border-top-right-radius", new CssProperty("border-top-right-radius")
      .addValidator(new BorderRadiusValidator())
    )

    .put("border-top-style", new CssProperty("border-top-style")
      .addValidator(new BorderStyleValidator())
    )

    .put("border-top-width", new CssProperty("border-top-width")
      .addValidator(new BorderWidthValidator())
    )

    .put("border-width", new CssProperty("border-width")
      .addValidator(new BorderWidthPropertyValidator())
    )

    .put("bottom", new CssProperty("bottom")
      .addValidator(PropertyValueValidatorFactory.getAutoValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("box-align", new CssProperty("box-align")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")

    )

    .put("box-decoration-break", new CssProperty("box-decoration-break")
      .addValidator(new IdentifierValidator(ImmutableList.of("slice", "clone")))
    )

    .put("box-direction", new CssProperty("box-direction")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")

    )

    .put("box-flex", new CssProperty("box-flex")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")

    )

    .put("box-flex-group", new CssProperty("box-flex-group")

    )

    .put("box-lines", new CssProperty("box-lines")
      .addVendor("-webkit-")
      .addVendor("-ms-")

    )

    .put("box-ordinal-group", new CssProperty("box-ordinal-group")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")

    )

    .put("box-orient", new CssProperty("box-orient")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")

    )

    .put("box-pack", new CssProperty("box-pack")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")

    )

    .put("box-shadow", new CssProperty("box-shadow")
      .addVendor("-webkit-")
      .addVendor("-moz-")

    )

    .put("box-sizing", new CssProperty("box-sizing")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new BoxValidator())
    )

    .put("break-after", new CssProperty("break-after")

    )

    .put("break-before", new CssProperty("break-before")

    )

    .put("break-inside", new CssProperty("break-inside")

    )

    // C
    .put("caption-side", new CssProperty("caption-side")
      .addValidator(new IdentifierValidator(ImmutableList.of("top", "bottom")))
    )

    .put("caret-color", new CssProperty("caret-color")
      .addValidator(PropertyValueValidatorFactory.getAutoValidator())
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("clear", new CssProperty("clear")
      .addValidator(new IdentifierValidator(ImmutableList.of("none", "left", "right", "both")))
    )

    .put("clip", new CssProperty("clip")
      .addValidator(new FunctionValidator(ImmutableList.of("rect")))
      .addValidator(PropertyValueValidatorFactory.getAutoValidator())
    )

    .put("clip-path", new CssProperty("clip-path")
    )

    .put("color", new CssProperty("color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("color-profile", new CssProperty("color-profile")

    )

    .put("column", new CssProperty("column")
    )

    .put("column-count", new CssProperty("column-count")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")

    )

    .put("column-fill", new CssProperty("column-fill")

    )

    .put("column-gap", new CssProperty("column-gap")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")

    )

    .put("column-rule", new CssProperty("column-rule")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")

    )

    .put("column-rule-color", new CssProperty("column-rule-color")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")

    )

    .put("column-rule-style", new CssProperty("column-rule-style")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")

    )

    .put("column-rule-width", new CssProperty("column-rule-width")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")

    )

    .put("column-span", new CssProperty("column-span")

    )

    .put("column-width", new CssProperty("column-width")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")

    )

    .put("columns", new CssProperty("columns")

    )

    .put("content", new CssProperty("content")

    )

    .put("counter-increment", new CssProperty("counter-increment")

    )

    .put("counter-reset", new CssProperty("counter-reset")

    )

    .put("crop", new CssProperty("crop")
    )

    .put("cue", new CssProperty("cue")
      .addValidator(new CuePropertyValidator())
    )

    .put("cue-after", new CssProperty("cue-after")
      .addValidator(new CueValidator())
    )

    .put("cue-before", new CssProperty("cue-before")
      .addValidator(new CueValidator())
    )

    .put("cursor", new CssProperty("cursor")
    )

    // D
    .put("direction", new CssProperty("direction")
      .addValidator(new IdentifierValidator(ImmutableList.of("ltr", "rtl")))
    )

    .put("display", new CssProperty("display")
      .addValidator(new IdentifierValidator(
        ImmutableList.of("inline", "block", "list-item", "inline-block", "table", "inline-table", "table-row-group",
          "table-header-group", "table-footer-group", "table-row", "table-column-group", "table-column", "table-cell",
          "table-caption", "none", "flex", "inline-flex", "grid", "inline-grid")))
    )

    .put("dominant-baseline", new CssProperty("dominant-baseline")
      .addValidator(new IdentifierValidator(ImmutableList
        .of("auto", "use-script", "no-change", "reset-size", "alphabetic", "hanging", "ideographic", "mathematical",
          "central",
          "middle", "text-after-edge", "text-before-edge")))
    )

    .put("drop-initial-after-adjust", new CssProperty("drop-initial-after-adjust")

    )

    .put("drop-initial-after-align", new CssProperty("drop-initial-after-align")

    )

    .put("drop-initial-before-adjust", new CssProperty("drop-initial-before-adjust")

    )

    .put("drop-initial-before-align", new CssProperty("drop-initial-before-align")

    )

    .put("drop-initial-size", new CssProperty("drop-initial-size")
    )

    .put("drop-initial-value", new CssProperty("drop-initial-value")
      .addValidator(new IdentifierValidator(ImmutableList.of("initial")))
      .addValidator(PropertyValueValidatorFactory.getPositiveIntegerValidator())
    )

    // E
    .put("elevation", new CssProperty("elevation")
      .addValidator(PropertyValueValidatorFactory.getAngleValidator())
      .addValidator(new IdentifierValidator(ImmutableList.of("below", "level", "above", "higher", "lower")))
    )

    .put("empty-cells", new CssProperty("empty-cells")
      .addValidator(new IdentifierValidator(ImmutableList.of("show", "hide")))
    )

    // F
    .put("filter", new CssProperty("filter")
      .addValidator(new FilterValidator())
    )

    .put("fit", new CssProperty("fit")

    )

    .put("fit-position", new CssProperty("fit-position")

    )

    .put("float", new CssProperty("float")
      .addValidator(new IdentifierValidator(ImmutableList.of("left", "right", "none")))
    )

    .put("float-offset", new CssProperty("float-offset")

    )

    .put("font", new CssProperty("font")

    )

    .put("font-family", new CssProperty("font-family")

    )

    .put("font-size", new CssProperty("font-size")
      .addValidator(new IdentifierValidator(
        ImmutableList.of("xx-small", "x-small", "small", "medium", "large", "x-large", "xx-large")))
      .addValidator(new IdentifierValidator(ImmutableList.of("larger", "smaller")))
      .addValidator(PropertyValueValidatorFactory.getPositiveLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPositivePercentageValidator())
    )

    .put("font-size-adjust", new CssProperty("font-size-adjust")
      .addValidator(PropertyValueValidatorFactory.getNoneValidator())
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
    )

    .put("font-stretch", new CssProperty("font-stretch")
      .addValidator(new IdentifierValidator(
        ImmutableList.of("normal", "ultra-condensed", "extra-condensed", "condensed", "semi-condensed",
          "semi-expanded", "expanded", "extra-expanded", "ultra-expanded")))
    )

    .put("font-style", new CssProperty("font-style")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "italic", "oblique")))
    )

    .put("font-variant", new CssProperty("font-variant").
      addValidator(new IdentifierValidator(ImmutableList.of("normal", "small-caps")))
    )

    .put("font-weight", new CssProperty("font-weight")
      .addValidator(new FontWeightValidator())
    )

    // G
    .put("grid-cell-stacking", new CssProperty("grid-cell-stacking")

    )

    .put("grid-column", new CssProperty("grid-column")

    )

    .put("grid-columns", new CssProperty("grid-columns")

    )

    .put("grid-column-align", new CssProperty("grid-column-align")

    )

    .put("grid-column-sizing", new CssProperty("grid-column-sizing")

    )

    .put("grid-column-span", new CssProperty("grid-column-span")

    )

    .put("grid-flow", new CssProperty("grid-flow")

    )

    .put("grid-layer", new CssProperty("grid-layer")

    )

    .put("grid-row", new CssProperty("grid-row")

    )

    .put("grid-rows", new CssProperty("grid-rows")

    )

    .put("grid-row-align", new CssProperty("grid-row-align")

    )

    .put("grid-row-span", new CssProperty("grid-row-span")

    )

    .put("grid-row-sizing", new CssProperty("grid-row-sizing")

    )

    // H
    .put("hanging-punctuation", new CssProperty("hanging-punctuation")

    )

    .put("height", new CssProperty("height")
      .addValidator(PropertyValueValidatorFactory.getAutoValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("hyphenate-after", new CssProperty("hyphenate-after")

    )

    .put("hyphenate-before", new CssProperty("hyphenate-before")

    )

    .put("hyphenate-character", new CssProperty("hyphenate-character")

    )

    .put("hyphenate-lines", new CssProperty("hyphenate-lines")

    )

    .put("hyphenate-resource", new CssProperty("hyphenate-resource")

    )

    .put("hyphens", new CssProperty("hyphens")
      .addVendor("-epub-")
      .addVendor("-moz-")
      .addValidator(new IdentifierValidator(ImmutableList.of("none", "manual", "auto")))
    )

    // I
    .put("icon", new CssProperty("icon")

    )

    .put("image-orientation", new CssProperty("image-orientation")

    )

    .put("image-rendering", new CssProperty("image-rendering")

    )

    .put("inline-box-align", new CssProperty("inline-box-align")
      .addValidator(new IdentifierValidator(ImmutableList.of("initial", "last")))
      .addValidator(PropertyValueValidatorFactory.getIntegerValidator())
    )

    // L
    .put("left", new CssProperty("left")
      .addValidator(PropertyValueValidatorFactory.getAutoValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("letter-spacing", new CssProperty("letter-spacing").
      addValidator(new IdentifierValidator(ImmutableList.of("normal")))
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
    )

    .put("line-height", new CssProperty("line-height")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "none")))
      .addValidator(PropertyValueValidatorFactory.getPositiveLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPositivePercentageValidator())
      .addValidator(PropertyValueValidatorFactory.getPositiveNumberValidator())
    )

    .put("line-stacking", new CssProperty("line-stacking")
      .addValidator(new LineStackingValidator())
    )

    .put("line-stacking-ruby", new CssProperty("line-stacking-ruby")
      .addValidator(new LineStackingRubyValidator())
    )

    .put("line-stacking-shift", new CssProperty("line-stacking-shift")
      .addValidator(new LineStackingShiftValidator())
    )

    .put("line-stacking-strategy", new CssProperty("line-stacking-strategy")
      .addValidator(new LineStackingStrategyValidator())
    )

    .put("list-style", new CssProperty("list-style")

    )

    .put("list-style-image", new CssProperty("list-style-image")
      .addValidator(PropertyValueValidatorFactory.getNoneValidator())
      .addValidator(PropertyValueValidatorFactory.getUriValidator())
    )

    .put("list-style-position", new CssProperty("list-style-position")
      .addValidator(new IdentifierValidator(ImmutableList.of("inside", "outside")))
    )

    .put("list-style-type", new CssProperty("list-style-type")
      .addValidator(new IdentifierValidator(ImmutableList
        .of("disc", "circle", "square", "decimal", "decimal-leading-zero", "lower-roman", "upper-roman",
          "lower-greek", "lower-latin", "upper-latin", "armenian", "georgian", "lower-alpha", "upper-alpha", "none")))
    )

    // M
    .put("margin", new CssProperty("margin")
      .addValidator(new MarginValidator())
    )

    .put("margin-bottom", new CssProperty("margin-bottom")
      .addValidator(PropertyValueValidatorFactory.getMarginWidthValidator())
    )

    .put("margin-end", new CssProperty("margin-end")
      .addVendor("-webkit-")
      .addVendor("-moz-")

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

    )

    .put("margin-top", new CssProperty("margin-top")
      .addValidator(PropertyValueValidatorFactory.getMarginWidthValidator())
    )

    .put("mark", new CssProperty("mark")

    )

    .put("mark-after", new CssProperty("mark-after")

    )

    .put("mark-before", new CssProperty("mark-before")

    )

    .put("marks", new CssProperty("marks")

    )

    .put("marquee-direction", new CssProperty("marquee-direction")

    )

    .put("marquee-play-count", new CssProperty("marquee-play-count")

    )

    .put("marquee-speed", new CssProperty("marquee-speed")
      .addVendor("-webkit-")
      .addVendor("-wap-")

    )

    .put("marquee-style", new CssProperty("marquee-style")
      .addVendor("-webkit-")
      .addVendor("-wap-")

    )

    .put("max-height", new CssProperty("max-height")
      .addValidator(PropertyValueValidatorFactory.getNoneValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("max-width", new CssProperty("max-width")
      .addValidator(PropertyValueValidatorFactory.getNoneValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("min-height", new CssProperty("min-height")
      .addValidator(PropertyValueValidatorFactory.getAutoValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("min-width", new CssProperty("min-width")
      .addValidator(PropertyValueValidatorFactory.getAutoValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("move-to", new CssProperty("move-to")

    )

    // N
    .put("nav-down", new CssProperty("nav-down")

    )

    .put("nav-index", new CssProperty("nav-index")

    )

    .put("nav-left", new CssProperty("nav-left")

    )

    .put("nav-right", new CssProperty("nav-right")

    )

    .put("nav-up", new CssProperty("nav-up")

    )

    // O
    .put("opacity", new CssProperty("opacity")
      .addValidator(new NumberRangeValidator(0, 1))
    )

    .put("orphans", new CssProperty("orphans")
      .addValidator(PropertyValueValidatorFactory.getIntegerValidator())
    )

    .put("outline", new CssProperty("outline")
      .addValidator(new OutlineValidator())
    )

    .put("outline-color", new CssProperty("outline-color")
      .addValidator(new OutlineColorValidator())
    )

    .put("outline-offset", new CssProperty("outline-offset")
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
    )

    .put("outline-style", new CssProperty("outline-style")
      .addValidator(new OutlineStyleValidator())
    )

    .put("outline-width", new CssProperty("outline-width")
      .addValidator(new OutlineWidthValidator())
    )

    .put("overflow", new CssProperty("overflow")
      .addValidator(new IdentifierValidator(ImmutableList.of("visible", "hidden", "scroll", "auto")))
    )

    .put("overflow-style", new CssProperty("overflow-style")

    )

    .put("overflow-x", new CssProperty("overflow-x")
      .addValidator(new IdentifierValidator(ImmutableList.of("visible", "hidden", "scroll", "auto")))
    )

    .put("overflow-y", new CssProperty("overflow-y")
      .addValidator(new IdentifierValidator(ImmutableList.of("visible", "hidden", "scroll", "auto")))
    )

    // P
    .put("padding", new CssProperty("padding")
      .addValidator(new PaddingValidator())
    )

    .put("padding-bottom", new CssProperty("padding-bottom")
      .addValidator(PropertyValueValidatorFactory.getPaddingWidthValidator())
    )

    .put("padding-end", new CssProperty("padding-end")
      .addVendor("-webkit-")
      .addVendor("-moz-")

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

    )

    .put("padding-top", new CssProperty("padding-top")
      .addValidator(PropertyValueValidatorFactory.getPaddingWidthValidator())
    )

    .put("page", new CssProperty("page")

    )

    .put("page-break-after", new CssProperty("page-break-after")
      .addValidator(PropertyValueValidatorFactory.getPageBreakValidator())
    )

    .put("page-break-before", new CssProperty("page-break-before")
      .addValidator(PropertyValueValidatorFactory.getPageBreakValidator())
    )

    .put("page-break-inside", new CssProperty("page-break-inside")
      .addValidator(new IdentifierValidator(ImmutableList.of("avoid", "auto")))
    )

    .put("page-policy", new CssProperty("page-policy")

    )

    .put("pause", new CssProperty("pause")
      .addValidator(new PausePropertyValidator())
    )

    .put("pause-after", new CssProperty("pause-after")
      .addValidator(PropertyValueValidatorFactory.getPauseValidator())
    )

    .put("pause-before", new CssProperty("pause-before")
      .addValidator(PropertyValueValidatorFactory.getPauseValidator())
    )

    .put("perspective", new CssProperty("perspective")

    )

    .put("perspective-origin", new CssProperty("perspective-origin")

    )

    .put("phonemes", new CssProperty("phonemes")

    )

    .put("pitch", new CssProperty("pitch")
      .addValidator(PropertyValueValidatorFactory.getFrequencyValidator())
      .addValidator(new IdentifierValidator(ImmutableList.of("x-low", "low", "medium", "high", "x-high")))
    )

    .put("pitch-range", new CssProperty("pitch-range")
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
    )

    .put("play-during", new CssProperty("play-during")

    )

    .put("pointer-events", new CssProperty("pointer-events")

    )

    .put("position", new CssProperty("position")
      .addValidator(new IdentifierValidator(ImmutableList.of("static", "relative", "absolute", "fixed")))
    )

    .put("presentation-level", new CssProperty("presentation-level")

    )

    .put("punctuation-trim", new CssProperty("punctuation-trim")

    )

    // Q
    .put("quotes", new CssProperty("quotes")

    )

    // R
    .put("rendering-intent", new CssProperty("rendering-intent")

    )

    .put("resize", new CssProperty("resize")
      .addValidator(new IdentifierValidator(ImmutableList.of("none", "both", "horizontal", "vertical")))
    )

    .put("rest", new CssProperty("rest")

    )

    .put("rest-after", new CssProperty("rest-after")

    )

    .put("rest-before", new CssProperty("rest-before")

    )

    .put("richness", new CssProperty("richness")
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
    )

    .put("right", new CssProperty("right")
      .addValidator(PropertyValueValidatorFactory.getAutoValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("rotation", new CssProperty("rotation")

    )

    .put("rotation-point", new CssProperty("rotation-point")

    )

    .put("ruby-align", new CssProperty("ruby-align")

    )

    .put("ruby-overhang", new CssProperty("ruby-overhang")

    )
    .put("ruby-position", new CssProperty("ruby-position")

    )
    .put("ruby-span", new CssProperty("ruby-span")

    )

    // S
    .put("shape-image-threshold", new CssProperty("shape-image-threshold")
      .addValidator(new NumberRangeValidator(0.0, 1.0))
    )

    .put("shape-margin", new CssProperty("shape-margin")
      .addValidator(new LengthValidator(false))
      .addValidator(new PercentageValidator(false))
    )

    .put("shape-outside", new CssProperty("shape-outside")
      .addValidator(new ShapeOutsideValidator())
    )

    .put("size", new CssProperty("size")

    )

    .put("speak", new CssProperty("speak")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "none", "spell-out")))
    )

    .put("speak-header", new CssProperty("speak-header")
      .addValidator(new IdentifierValidator(ImmutableList.of("once", "always")))
    )

    .put("speak-numeral", new CssProperty("speak-numeral")
      .addValidator(new IdentifierValidator(ImmutableList.of("digits", "continuous")))
    )

    .put("speak-punctuation", new CssProperty("speak-punctuation")
      .addValidator(new IdentifierValidator(ImmutableList.of("code", "none")))
    )

    .put("speech-rate", new CssProperty("speech-rate")
      .addValidator(
        new IdentifierValidator(
          ImmutableList.of("x-slow", "slow", "medium", "fast", "x-fast", "faster", "slower")))
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
    )

    .put("src", new CssProperty("src")

    )

    .put("stress", new CssProperty("stress")
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
    )
    .put("string-set", new CssProperty("string-set")

    )

    // T
    .put("table-layout", new CssProperty("table-layout")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "fixed")))
    )

    .put("tab-size", new CssProperty("tab-size")
      .addVendor("-moz-")
      .addVendor("-o-")
      .addValidator(PropertyValueValidatorFactory.getPositiveLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPositiveIntegerValidator())
    )

    .put("target", new CssProperty("target")

    )
    .put("target-name", new CssProperty("target-name")

    )
    .put("target-new", new CssProperty("target-new")

    )
    .put("target-position", new CssProperty("target-position")

    )

    .put("text-align", new CssProperty("text-align")
      .addValidator(new IdentifierValidator(ImmutableList.of("left", "right", "center", "justify")))
    )

    .put("text-align-last", new CssProperty("text-align-last")

    )
    .put("text-decoration", new CssProperty("text-decoration")

    )
    .put("text-emphasis", new CssProperty("text-emphasis")

    )
    .put("text-height", new CssProperty("text-height")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "font-size", "text-size", "max-size")))
    )

    .put("text-indent", new CssProperty("text-indent")
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("text-justify", new CssProperty("text-justify")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "none", "inter-word", "inter-character")))
    )
    .put("text-outline", new CssProperty("text-outline")

    )
    .put("text-overflow", new CssProperty("text-overflow")

    )
    .put("text-rendering", new CssProperty("text-rendering")

    )

    .put("text-size-adjust", new CssProperty("text-size-adjust")
      .addVendor("-webkit-")
      .addVendor("-ms-")

    )

    .put("text-shadow", new CssProperty("text-shadow")

    )

    .put("text-transform", new CssProperty("text-transform")
      .addValidator(new IdentifierValidator(ImmutableList.of("capitalize", "uppercase", "lowercase", "none")))
    )

    .put("text-wrap", new CssProperty("text-wrap")

    )

    .put("top", new CssProperty("top")
      .addValidator(PropertyValueValidatorFactory.getAutoValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("transform", new CssProperty("transform")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addVendor("-o-")

    )

    .put("transform-origin", new CssProperty("transform-origin")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addVendor("-o-")

    )

    .put("transform-style", new CssProperty("transform-style")

    )

    .put("transition", new CssProperty("transition")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-")

    )

    .put("transition-delay", new CssProperty("transition-delay")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-")

    )

    .put("transition-duration", new CssProperty("transition-duration")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-")

    )

    .put("transition-property", new CssProperty("transition-property")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-")

    )

    .put("transition-timing-function", new CssProperty("transition-timing-function")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-")

    )

    // U
    .put("unicode-bidi", new CssProperty("unicode-bidi")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "embed", "bidi-override")))
    )

    .put("user-modify", new CssProperty("user-modify")
      .addVendor("-webkit-")
      .addVendor("-moz-")

    )

    .put("user-select", new CssProperty("user-select")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")

    )

    // V
    .put("vertical-align", new CssProperty("vertical-align")
      .addValidator(new IdentifierValidator(
        ImmutableList
          .of("auto", "use-script", "baseline", "sub", "super", "top", "text-top", "central", "middle", "bottom",
            "text-bottom")))
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
    )

    .put("visibility", new CssProperty("visibility")
      .addValidator(new IdentifierValidator(ImmutableList.of("visible", "hidden", "collapse")))
    )

    .put("voice-balance", new CssProperty("voice-balance")

    )

    .put("voice-duration", new CssProperty("voice-duration")

    )

    .put("voice-family", new CssProperty("voice-family")

    )

    .put("voice-pitch", new CssProperty("voice-pitch")

    )

    .put("voice-pitch-range", new CssProperty("voice-pitch-range")

    )

    .put("voice-rate", new CssProperty("voice-rate")

    )

    .put("voice-stress", new CssProperty("voice-stress")

    )

    .put("voice-volume", new CssProperty("voice-volume")

    )

    .put("volume", new CssProperty("volume")
      .addValidator(
        new IdentifierValidator(ImmutableList.of("silent", "x-soft", "soft", "medium", "loud", "x-loud")))
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    // W
    .put("white-space", new CssProperty("white-space")
      .addValidator(
      new IdentifierValidator(ImmutableList.of("normal", "pre", "nowrap", "pre-wrap", "pre-line")))
    )

    .put("white-space-collapse", new CssProperty("white-space-collapse")

    )

    .put("widows", new CssProperty("widows")
      .addValidator(PropertyValueValidatorFactory.getIntegerValidator())
    )

    .put("width", new CssProperty("width")
      .addValidator(PropertyValueValidatorFactory.getAutoValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("word-break", new CssProperty("word-break")
      .addVendor("-epub-")
      .addVendor("-ms-")

    )

    .put("word-spacing", new CssProperty("word-spacing")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal")))
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
    )

    .put("word-wrap", new CssProperty("word-wrap")

    )

    .put("writing-mode", new CssProperty("writing-mode")
      .addVendor("-epub-")
      .addVendor("-ms-")

    )

    // Z
    .put("z-index", new CssProperty("z-index")
      .addValidator(PropertyValueValidatorFactory.getAutoValidator())
      .addValidator(PropertyValueValidatorFactory.getIntegerValidator())
    )

    .put("zoom", new CssProperty("zoom")

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
