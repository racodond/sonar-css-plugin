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
import org.sonar.css.checks.validators.property.CounterValidator;
import org.sonar.css.checks.validators.property.CuePropertyValidator;
import org.sonar.css.checks.validators.property.CursorValidator;
import org.sonar.css.checks.validators.property.FilterValidator;
import org.sonar.css.checks.validators.property.FlexFlowValidator;
import org.sonar.css.checks.validators.property.FontFamilyValidator;
import org.sonar.css.checks.validators.property.FontWeightValidator;
import org.sonar.css.checks.validators.property.MarginValidator;
import org.sonar.css.checks.validators.property.OutlineValidator;
import org.sonar.css.checks.validators.property.OverflowPropertyValidator;
import org.sonar.css.checks.validators.property.PaddingValidator;
import org.sonar.css.checks.validators.property.PausePropertyValidator;
import org.sonar.css.checks.validators.property.ShapeOutsideValidator;
import org.sonar.css.checks.validators.property.TextDecorationLineValidator;
import org.sonar.css.checks.validators.property.TextOverflowValidator;
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
import org.sonar.css.checks.validators.property.background.BackgroundImageValidator;
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
import org.sonar.css.checks.validators.property.liststyle.ListStyleImageValidator;
import org.sonar.css.checks.validators.property.liststyle.ListStylePositionValidator;
import org.sonar.css.checks.validators.property.liststyle.ListStyleTypeValidator;
import org.sonar.css.checks.validators.property.liststyle.ListStyleValidator;
import org.sonar.css.checks.validators.valueelement.AutoValidator;
import org.sonar.css.checks.validators.valueelement.BorderStyleValidator;
import org.sonar.css.checks.validators.valueelement.BorderWidthValidator;
import org.sonar.css.checks.validators.valueelement.BoxValidator;
import org.sonar.css.checks.validators.valueelement.ColorValidator;
import org.sonar.css.checks.validators.valueelement.CueValidator;
import org.sonar.css.checks.validators.valueelement.IdentifierValidator;
import org.sonar.css.checks.validators.valueelement.ImageValidator;
import org.sonar.css.checks.validators.valueelement.NoneValidator;
import org.sonar.css.checks.validators.valueelement.OutlineColorValidator;
import org.sonar.css.checks.validators.valueelement.OutlineStyleValidator;
import org.sonar.css.checks.validators.valueelement.OutlineWidthValidator;
import org.sonar.css.checks.validators.valueelement.OverflowValidator;
import org.sonar.css.checks.validators.valueelement.StringValidator;
import org.sonar.css.checks.validators.valueelement.TextDecorationStyleValidator;
import org.sonar.css.checks.validators.valueelement.WidthHeightValidator;
import org.sonar.css.checks.validators.valueelement.dimension.LengthValidator;
import org.sonar.css.checks.validators.valueelement.dimension.TimeValidator;
import org.sonar.css.checks.validators.valueelement.flex.FlexBasisValidator;
import org.sonar.css.checks.validators.valueelement.flex.FlexDirectionValidator;
import org.sonar.css.checks.validators.valueelement.flex.FlexGrowValidator;
import org.sonar.css.checks.validators.valueelement.flex.FlexShrinkValidator;
import org.sonar.css.checks.validators.valueelement.flex.FlexWrapValidator;
import org.sonar.css.checks.validators.valueelement.function.FunctionValidator;
import org.sonar.css.checks.validators.valueelement.numeric.IntegerValidator;
import org.sonar.css.checks.validators.valueelement.numeric.NumberRangeValidator;
import org.sonar.css.checks.validators.valueelement.numeric.NumberValidator;
import org.sonar.css.checks.validators.valueelement.numeric.PercentageValidator;

import java.util.Map;

/**
 * Properties from https://github.com/stubbornella/csslint/blob/c31f1b9c89fa102eb89e96807be9d290110887e5/lib/parserlib.js
 * vendor mapping from: https://github.com/stubbornella/csslint/blob/master/src/rules/compatible-vendor-prefixes.js
 *
 * Work on property validators according to the usage list at https://www.chromestatus.com/metrics/css/popularity
 *
 * @author tkende
 */
public final class CssProperties {

  public static final Map<String, CssProperty> PROPERTIES = new ImmutableMap.Builder<String, CssProperty>()

    // A
    .put("align-content", new CssProperty("align-content")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-align-content")
      .addValidator(new IdentifierValidator(
        ImmutableList.of("flex-start", "flex-end", "center", "space-between", "space-around", "stretch")))
    )

    .put("align-items", new CssProperty("align-items")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-align-items")
      .addValidator(new IdentifierValidator(ImmutableList.of("flex-start", "flex-end", "center", "baseline", "stretch")))
    )

    .put("align-self", new CssProperty("align-self")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-align-self")
      .addValidator(
        new IdentifierValidator(ImmutableList.of("auto", "flex-start", "flex-end", "center", "baseline", "stretch")))
    )

    .put(
      "alignment-adjust",
      new CssProperty("alignment-adjust")
        .setUrl("http://www.w3.org/TR/css3-linebox/#alignment-adjust")
        .addValidator(
          new IdentifierValidator(ImmutableList
            .of("auto", "baseline", "before-edge", "text-before-edge", "middle", "central", "after-edge",
              "text-after-edge",
              "ideographic", "alphabetic", "hanging", "mathematical")))
        .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
        .addValidator(PropertyValueValidatorFactory.getLengthValidator())
    )

    .put("alignment-baseline", new CssProperty("alignment-baseline")
      .setUrl("http://www.w3.org/TR/css3-linebox/#alignment-baseline")
      .addValidator(new IdentifierValidator(ImmutableList.of("baseline", "use-script", "before-edge", "text-before-edge",
        "after-edge", "text-after-edge", "central", "middle", "ideographic", "alphabetic", "hanging", "mathematical")))
    )

    .put("all", new CssProperty("all")
      .setUrl("http://www.w3.org/TR/css3-cascade/#propdef-all")
      .addValidator(new IdentifierValidator(ImmutableList.of("inherit", "initial", "unset")))
    )

    .put("animation", new CssProperty("animation")
      .setUrl("http://www.w3.org/TR/css3-animations/#animation")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationValidator())
    )

    .put("animation-delay", new CssProperty("animation-delay")
      .setUrl("http://www.w3.org/TR/css3-animations/#animation-delay")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationDelayValidator())
    )

    .put("animation-direction", new CssProperty("animation-direction")
      .setUrl("http://www.w3.org/TR/css3-animations/#animation-direction")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationDirectionValidator())
    )

    .put("animation-duration", new CssProperty("animation-duration")
      .setUrl("http://www.w3.org/TR/css3-animations/#animation-duration-property")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationDurationValidator())
    )

    .put("animation-fill-mode", new CssProperty("animation-fill-mode")
      .setUrl("http://dev.w3.org/csswg/css-animations/#propdef-animation-fill-mode")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationFillModeValidator())
    )

    .put("animation-iteration-count", new CssProperty("animation-iteration-count")
      .setUrl("http://dev.w3.org/csswg/css-animations/#propdef-animation-iteration-count")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationIterationCountValidator())
    )

    .put("animation-name", new CssProperty("animation-name")
      .setUrl("http://dev.w3.org/csswg/css-animations/#propdef-animation-name")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationNameValidator())
    )

    .put("animation-play-state", new CssProperty("animation-play-state")
      .setUrl("http://dev.w3.org/csswg/css-animations/#propdef-animation-play-state")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationPlayStateValidator())
    )

    .put("animation-timing-function", new CssProperty("animation-timing-function")
      .setUrl("http://dev.w3.org/csswg/css-animations/#propdef-animation-timing-function")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationTimingFunctionValidator())
    )

    .put("appearance", new CssProperty("appearance")
      .setUrl("http://www.w3.org/TR/2004/CR-css3-ui-20040511/#appearance0")
      .setObsolete(true)
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

    .put("azimuth", new CssProperty("azimuth")
      .setObsolete(true)
    )

    // B
    .put("backface-visibility", new CssProperty("backface-visibility")
      .setUrl("http://dev.w3.org/csswg/css-transforms/#propdef-backface-visibility")
      .addValidator(new IdentifierValidator(ImmutableList.of("visible", "hidden")))
    )

    .put("background", new CssProperty("background")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background")
    )

    .put("background-attachment", new CssProperty("background-attachment")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background-attachment")
      .addValidator(new BackgroundAttachmentValidator())
    )

    .put("background-clip", new CssProperty("background-clip")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background-clip")
      .addValidator(new BackgroundClipValidator())
    )

    .put("background-color", new CssProperty("background-color")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background-color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("background-image", new CssProperty("background-image")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background-image")
      .addValidator(new BackgroundImageValidator())
    )

    .put("background-origin", new CssProperty("background-origin")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background-origin")
      .addValidator(new BackgroundOriginValidator())
    )

    .put("background-position", new CssProperty("background-position")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background-position")
    )

    .put("background-repeat", new CssProperty("background-repeat")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background-repeat")
      .addValidator(new BackgroundRepeatValidator())
    )

    .put("background-size", new CssProperty("background-size")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background-size")
      .addValidator(new BackgroundSizeValidator())
    )

    .put("baseline-shift", new CssProperty("baseline-shift")
      .setUrl("http://dev.w3.org/csswg/css-inline/#propdef-baseline-shift")
      .addValidator(new IdentifierValidator(ImmutableList.of("sub", "super")))
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("behavior", new CssProperty("behavior")
      .setUrl("http://www.w3.org/TR/1999/WD-becss-19990804#behavior")
      .setObsolete(true)
    )

    .put("binding", new CssProperty("binding")
      .setObsolete(true)
    )

    .put("bleed", new CssProperty("bleed")
      .setUrl("http://dev.w3.org/csswg/css-page/#descdef-page-bleed")
      .addValidator(new AutoValidator())
      .addValidator(new LengthValidator(false))
    )

    .put("bookmark-label", new CssProperty("bookmark-label")
      .setUrl("http://www.w3.org/TR/css3-gcpm/#propdef-bookmark-label")
    )

    .put("bookmark-level", new CssProperty("bookmark-level")
      .setUrl("http://www.w3.org/TR/css-gcpm-3/#propdef-bookmark-level")
      .addValidator(new NoneValidator())
      .addValidator(new IntegerValidator(true)) // TODO: 0 is invalid
    )

    .put("bookmark-state", new CssProperty("bookmark-state")
      .setUrl("http://www.w3.org/TR/css-gcpm-3/#propdef-bookmark-state")
      .addValidator(new IdentifierValidator(ImmutableList.of("open", "close")))
    )

    .put("border", new CssProperty("border")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-shorthands")
      .addValidator(new BorderValidator())
    )

    .put("border-block-end", new CssProperty("border-block-end")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-block-end")
      .addValidator(new BorderValidator())
    )

    .put("border-inline-end", new CssProperty("border-inline-end")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-inline-end-style")
      .addValidator(new BorderValidator())
    )

    .put("border-inline-start", new CssProperty("border-inline-start")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-inline-start")
      .addValidator(new BorderValidator())
    )

    .put("border-block-start", new CssProperty("border-block-start")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-block-start")
      .addValidator(new BorderValidator())
    )

    .put("border-block-end-color", new CssProperty("border-block-end-color")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-block-end-color")
      .addValidator(new ColorValidator())
    )

    .put("border-inline-end-color", new CssProperty("border-inline-end-color")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-inline-end-style")
      .addValidator(new ColorValidator())
    )

    .put("border-inline-start-color", new CssProperty("border-inline-start-color")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-inline-start-color")
      .addValidator(new ColorValidator())
    )

    .put("border-block-start-color", new CssProperty("border-block-start-color")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-block-start-color")
      .addValidator(new ColorValidator())
    )

    .put("border-block-end-width", new CssProperty("border-block-end-width")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-block-end-width")
      .addValidator(new BorderWidthValidator())
    )

    .put("border-inline-end-width", new CssProperty("border-inline-end-width")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-inline-end-width")
      .addValidator(new BorderWidthValidator())
    )

    .put("border-inline-start-width", new CssProperty("border-inline-start-width")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-inline-start-width")
      .addValidator(new BorderWidthValidator())
    )

    .put("border-block-start-width", new CssProperty("border-block-start-width")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-block-start-width")
      .addValidator(new BorderWidthValidator())
    )

    .put("border-block-end-style", new CssProperty("border-block-end-style")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-block-end-style")
      .addValidator(new BorderStyleValidator())
    )

    .put("border-inline-end-style", new CssProperty("border-inline-end-style")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-inline-end-style")
      .addValidator(new BorderStyleValidator())
    )

    .put("border-inline-start-style", new CssProperty("border-inline-start-style")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-inline-start-style")
      .addValidator(new BorderStyleValidator())
    )

    .put("border-block-start-style", new CssProperty("border-block-start-style")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-block-start-style")
      .addValidator(new BorderStyleValidator())
    )

    .put("border-bottom", new CssProperty("border-bottom")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-shorthands")
      .addValidator(new BorderValidator())
    )

    .put("border-bottom-color", new CssProperty("border-bottom-color")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("border-bottom-left-radius", new CssProperty("border-bottom-left-radius")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-radius")
      .addValidator(new BorderRadiusValidator())
    )

    .put("border-bottom-right-radius", new CssProperty("border-bottom-right-radius")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-radius")
      .addValidator(new BorderRadiusValidator())
    )

    .put("border-bottom-style", new CssProperty("border-bottom-style")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-style")
      .addValidator(new BorderStyleValidator())
    )

    .put("border-bottom-width", new CssProperty("border-bottom-width")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-width")
      .addValidator(new BorderWidthValidator())
    )

    .put("border-collapse", new CssProperty("border-collapse")
      .setUrl("http://www.w3.org/TR/CSS2/tables.html#borders")
      .addValidator(new IdentifierValidator(ImmutableList.of("collapse", "separate")))
    )

    .put("border-color", new CssProperty("border-color")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-color")
      .addValidator(new BorderColorValidator())
    )

    .put("border-end", new CssProperty("border-end")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-")
    )

    .put("border-end-color", new CssProperty("border-end-color")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-")
    )

    .put("border-end-style", new CssProperty("border-end-style")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-")
    )

    .put("border-end-width", new CssProperty("border-end-width")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-")
    )

    .put("border-image", new CssProperty("border-image")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-image")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-")
    )

    .put("border-image-outset", new CssProperty("border-image-outset")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-image-outset")
    )

    .put("border-image-repeat", new CssProperty("border-image-repeat")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-image-repeat")
    )

    .put("border-image-slice", new CssProperty("border-image-slice")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-image-slice")
    )

    .put("border-image-source", new CssProperty("border-image-source")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-image-source")
      .addValidator(new NoneValidator())
      .addValidator(new ImageValidator())
    )

    .put("border-image-width", new CssProperty("border-image-width")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-image-width")
    )

    .put("border-left", new CssProperty("border-left")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-shorthands")
      .addValidator(new BorderValidator())
    )

    .put("border-left-color", new CssProperty("border-left-color")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("border-left-style", new CssProperty("border-left-style")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-style")
      .addValidator(new BorderStyleValidator())
    )

    .put("border-left-width", new CssProperty("border-left-width")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-width")
      .addValidator(new BorderWidthValidator())
    )

    .put("border-radius", new CssProperty("border-radius")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-radius")
      .addVendor("-webkit-")
      .addValidator(new BorderRadiusPropertyValidator())
    )

    .put("border-right", new CssProperty("border-right")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-shorthands")
      .addValidator(new BorderValidator())
    )

    .put("border-right-color", new CssProperty("border-right-color")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("border-right-style", new CssProperty("border-right-style")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-style")
      .addValidator(new BorderStyleValidator())
    )

    .put("border-right-width", new CssProperty("border-right-width")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-width")
      .addValidator(new BorderWidthValidator())
    )

    .put("border-start", new CssProperty("border-start")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-")
    )

    .put("border-start-color", new CssProperty("border-start-color")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-")
    )

    .put("border-start-style", new CssProperty("border-start-style")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-")
    )

    .put("border-start-width", new CssProperty("border-start-width")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-")
    )

    .put("border-spacing", new CssProperty("border-spacing")
      .setUrl("http://www.w3.org/TR/CSS2/tables.html#separated-borders")
      .addValidator(new BorderSpacingValidator())
    )

    .put("border-style", new CssProperty("border-style")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-style")
      .addValidator(new BorderStylePropertyValidator())
    )

    .put("border-top", new CssProperty("border-top")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-shorthands")
      .addValidator(new BorderValidator())
    )

    .put("border-top-color", new CssProperty("border-top-color")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-color")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("border-top-left-radius", new CssProperty("border-top-left-radius")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-radius")
      .addValidator(new BorderRadiusValidator())
    )

    .put("border-top-right-radius", new CssProperty("border-top-right-radius")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-radius")
      .addValidator(new BorderRadiusValidator())
    )

    .put("border-top-style", new CssProperty("border-top-style")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-style")
      .addValidator(new BorderStyleValidator())
    )

    .put("border-top-width", new CssProperty("border-top-width")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-width")
      .addValidator(new BorderWidthValidator())
    )

    .put("border-width", new CssProperty("border-width")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-width")
      .addValidator(new BorderWidthPropertyValidator())
    )

    .put("bottom", new CssProperty("bottom")
      .setUrl("http://dev.w3.org/csswg/css2/visuren.html#propdef-bottom")
      .addValidator(PropertyValueValidatorFactory.getAutoValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("box-align", new CssProperty("box-align")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .setObsolete(true)
    )

    .put("box-decoration-break", new CssProperty("box-decoration-break")
      .setUrl("http://dev.w3.org/csswg/css-break-3/#propdef-box-decoration-break")
      .addValidator(new IdentifierValidator(ImmutableList.of("slice", "clone")))
    )

    .put("box-direction", new CssProperty("box-direction")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .setObsolete(true)
    )

    .put("box-flex", new CssProperty("box-flex")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .setObsolete(true)
    )

    .put("box-flex-group", new CssProperty("box-flex-group")
      .setObsolete(true)
    )

    .put("box-lines", new CssProperty("box-lines")
      .addVendor("-webkit-")
      .addVendor("-ms-")
      .setObsolete(true)
    )

    .put("box-ordinal-group", new CssProperty("box-ordinal-group")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .setObsolete(true)
    )

    .put("box-orient", new CssProperty("box-orient")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .setObsolete(true)
    )

    .put("box-pack", new CssProperty("box-pack")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .setObsolete(true)
    )

    .put("box-shadow", new CssProperty("box-shadow")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#box-shadow")
      .addVendor("-webkit-")
      .addVendor("-moz-")
    )

    .put("box-sizing", new CssProperty("box-sizing")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-box-sizing")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new BoxValidator())
    )

    .put("break-after", new CssProperty("break-after")
      .setUrl("http://dev.w3.org/csswg/css-break-3/#propdef-break-after")
      .addValidator(
        new IdentifierValidator(ImmutableList
          .of("auto", "avoid", "always", "any", "avoid-page", "page", "left", "right", "recto", "verso", "avoid-column",
            "column",
            "avoid-region", "region")))

    )

    .put(
      "break-before",
      new CssProperty("break-before")
        .setUrl("http://dev.w3.org/csswg/css-break-3/#propdef-break-before")
        .addValidator(
          new IdentifierValidator(ImmutableList
            .of("auto", "avoid", "always", "any", "avoid-page", "page", "left", "right", "recto", "verso", "avoid-column",
              "column",
              "avoid-region", "region")))
    )

    .put("break-inside", new CssProperty("break-inside")
      .setUrl("http://dev.w3.org/csswg/css-break-3/#propdef-break-inside")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "avoid", "avoid-page", "avoid-column", "avoid-region")))
    )

    // C
    .put("caption-side", new CssProperty("caption-side")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#caption-side")
      .addValidator(new IdentifierValidator(
        ImmutableList.of("top", "bottom", "block-start", "block-end", "inline-start", "inline-end")))
    )

    .put("caret-color", new CssProperty("caret-color")
      .setUrl("http://www.w3.org/TR/2015/WD-css3-ui-20150519/#propdef-caret-color")
      .addValidator(PropertyValueValidatorFactory.getAutoValidator())
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("clear", new CssProperty("clear")
      .setUrl("http://www.w3.org/TR/CSS2/visuren.html#flow-control")
      .addValidator(new IdentifierValidator(ImmutableList.of("none", "left", "right", "both")))
    )

    .put("clip", new CssProperty("clip")
      .setUrl("http://www.w3.org/TR/CSS2/visufx.html#propdef-clip")
      .addValidator(new FunctionValidator(ImmutableList.of("rect")))
      .addValidator(PropertyValueValidatorFactory.getAutoValidator())
    )

    .put("clip-path", new CssProperty("clip-path")
      .setUrl("http://www.w3.org/TR/SVG11/masking.html#ClipPathProperty")
    )

    .put("clip-rule", new CssProperty("clip-rule")
      .setUrl("http://www.w3.org/TR/SVG11/masking.html#ClipRuleProperty")
    )

    .put("color", new CssProperty("color")
      .setUrl("http://dev.w3.org/csswg/css-color-3/#color0")
      .addValidator(PropertyValueValidatorFactory.getColorValidator())
    )

    .put("column-count", new CssProperty("column-count")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-count")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(new IntegerValidator(true))
    )

    .put("column-fill", new CssProperty("column-fill")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-fill")
      .addValidator(new IdentifierValidator(ImmutableList.<String>of("auto", "balance", "balance-all")))
    )

    .put("column-gap", new CssProperty("column-gap")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-gap")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(new LengthValidator(true))
    )

    .put("column-rule", new CssProperty("column-rule")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-rule")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
    )

    .put("column-rule-color", new CssProperty("column-rule-color")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-rule-color")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(new ColorValidator())
    )

    .put("column-rule-style", new CssProperty("column-rule-style")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-rule-style")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(new BorderStyleValidator())
    )

    .put("column-rule-width", new CssProperty("column-rule-width")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-rule-width")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(new BorderWidthValidator())
    )

    .put("column-span", new CssProperty("column-span")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-span")
      .addValidator(new IdentifierValidator(ImmutableList.<String>of("all", "none")))
    )

    .put("column-width", new CssProperty("column-width")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-width")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(new AutoValidator())
      .addValidator(new LengthValidator(true))
    )

    .put("columns", new CssProperty("columns")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-columns")
    )

    .put("content", new CssProperty("content")
      .setUrl("http://www.w3.org/TR/CSS2/generate.html#content")
    )

    .put("counter-increment", new CssProperty("counter-increment")
      .setUrl("http://dev.w3.org/csswg/css-lists-3/#propdef-counter-increment")
      .addValidator(new CounterValidator())
    )

    .put("counter-reset", new CssProperty("counter-reset")
      .setUrl("http://dev.w3.org/csswg/css-lists-3/#propdef-counter-reset")
      .addValidator(new CounterValidator())
    )

    .put("counter-set", new CssProperty("counter-set")
      .setUrl("http://dev.w3.org/csswg/css-lists-3/#propdef-counter-set")
      .addValidator(new CounterValidator())
    )

    .put("cue", new CssProperty("cue")
      .setUrl("http://www.w3.org/TR/css3-speech/#cue")
      .addValidator(new CuePropertyValidator())
    )

    .put("cue-after", new CssProperty("cue-after")
      .setUrl("http://www.w3.org/TR/css3-speech/#cue-after")
      .addValidator(new CueValidator())
    )

    .put("cue-before", new CssProperty("cue-before")
      .setUrl("http://www.w3.org/TR/css3-speech/#cue-before")
      .addValidator(new CueValidator())
    )

    .put("cursor", new CssProperty("cursor")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-cursor")
      .addValidator(new CursorValidator())
    )

    // D
    .put("direction", new CssProperty("direction")
      .setUrl("http://dev.w3.org/csswg/css-writing-modes-3/#propdef-direction")
      .addValidator(new IdentifierValidator(ImmutableList.of("ltr", "rtl")))
    )

    .put(
      "display",
      new CssProperty("display")
        .setUrl("http://dev.w3.org/csswg/css-display/#propdef-display")
        .addValidator(
          new IdentifierValidator(
            ImmutableList.of("inline", "block", "list-item", "inline-block", "table", "inline-table", "table-row-group",
              "table-header-group", "table-footer-group", "table-row", "table-column-group", "table-column", "table-cell",
              "table-caption", "none", "flex", "inline-flex", "grid", "inline-grid", "run-in", "contents", "ruby",
              "ruby-base", "ruby-text", "ruby-base-container", "ruby-text-container")))
    )

    .put("dominant-baseline", new CssProperty("dominant-baseline")
      .setUrl("http://dev.w3.org/csswg/css-inline/#propdef-dominant-baseline")
      .addValidator(new IdentifierValidator(ImmutableList
        .of("auto", "use-script", "no-change", "reset-size", "alphabetic", "hanging", "ideographic", "mathematical",
          "central", "middle", "text-after-edge", "text-before-edge")))
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
      .setUrl("http://www.w3.org/TR/CSS21/aural.html#propdef-elevation")
      .addValidator(PropertyValueValidatorFactory.getAngleValidator())
      .addValidator(new IdentifierValidator(ImmutableList.of("below", "level", "above", "higher", "lower")))
    )

    .put("empty-cells", new CssProperty("empty-cells")
      .setUrl("http://www.w3.org/TR/CSS2/tables.html#propdef-empty-cells")
      .addValidator(new IdentifierValidator(ImmutableList.of("show", "hide")))
    )

    // F
    .put("flex-basis", new CssProperty("flex-basis")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-flex-basis")
      .addValidator(new FlexBasisValidator())
    )

    .put("flex-direction", new CssProperty("flex-direction")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-flex-direction")
      .addValidator(new FlexDirectionValidator())
    )

    .put("flex-flow", new CssProperty("flex-flow")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-flex-flow")
      .addValidator(new FlexFlowValidator())
    )

    .put("flex-grow", new CssProperty("flex-grow")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-flex-grow")
      .addValidator(new FlexGrowValidator())
    )

    .put("flex-shrink", new CssProperty("flex-shrink")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-flex-shrink")
      .addValidator(new FlexShrinkValidator())
    )

    .put("flex-wrap", new CssProperty("flex-wrap")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-flex-wrap")
      .addValidator(new FlexWrapValidator())
    )

    .put("filter", new CssProperty("filter")
      .setUrl("http://dev.w3.org/fxtf/filters/#propdef-filter")
      .addValidator(new FilterValidator())
    )

    .put("fit", new CssProperty("fit")

    )

    .put("fit-position", new CssProperty("fit-position")

    )

    .put("float", new CssProperty("float")
      .setUrl("http://www.w3.org/TR/CSS2/visuren.html#propdef-float")
      .addValidator(new IdentifierValidator(ImmutableList.of("left", "right", "none")))
    )

    .put("float-defer", new CssProperty("float-defer")
      .setUrl("http://dev.w3.org/csswg/css-page-floats/#propdef-float-defer")
    )

    .put("float-offset", new CssProperty("float-offset")
      .setUrl("http://dev.w3.org/csswg/css-page-floats/#propdef-float-offset")
    )

    .put("font", new CssProperty("font")
      .setUrl("http://dev.w3.org/csswg/css3-fonts/#font")
    )

    .put("font-family", new CssProperty("font-family")
      .setUrl("http://dev.w3.org/csswg/css3-fonts/#font-family-prop")
      .addValidator(new FontFamilyValidator())
    )

    .put("font-language-override", new CssProperty("font-language-override")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#propdef-font-language-override")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal")))
      .addValidator(new StringValidator())
    )

    .put("font-size", new CssProperty("font-size")
      .setUrl("http://www.w3.org/TR/CSS2/fonts.html#propdef-font-size")
      .addValidator(
        new IdentifierValidator(ImmutableList.of("xx-small", "x-small", "small", "medium", "large", "x-large", "xx-large")))
      .addValidator(new IdentifierValidator(ImmutableList.of("larger", "smaller")))
      .addValidator(PropertyValueValidatorFactory.getPositiveLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPositivePercentageValidator())
    )

    .put("font-size-adjust", new CssProperty("font-size-adjust")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#propdef-font-size-adjust")
      .addValidator(PropertyValueValidatorFactory.getNoneValidator())
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
    )

    .put("font-stretch", new CssProperty("font-stretch")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#propdef-font-stretch")
      .addValidator(new IdentifierValidator(
        ImmutableList.of("normal", "ultra-condensed", "extra-condensed", "condensed", "semi-condensed",
          "semi-expanded", "expanded", "extra-expanded", "ultra-expanded")))
    )

    .put("font-style", new CssProperty("font-style")
      .setUrl("http://dev.w3.org/csswg/css3-fonts/#font-style-prop")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "italic", "oblique")))
    )

    .put("font-variant", new CssProperty("font-variant")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#descdef-font-face-font-variant")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "small-caps")))
    )

    .put("font-weight", new CssProperty("font-weight")
      .setUrl("http://dev.w3.org/csswg/css3-fonts/#font-weight-prop")
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
      .setUrl("http://dev.w3.org/csswg/css-sizing-3/#width-height-keywords")
      .addValidator(new WidthHeightValidator())
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
      .setUrl("http://dev.w3.org/csswg/css-images-3/#propdef-image-orientation")
    )

    .put("image-rendering", new CssProperty("image-rendering")
      .setUrl("http://dev.w3.org/csswg/css-images-3/#propdef-image-rendering")
    )

    .put("image-resolution", new CssProperty("image-resolution")
      .setUrl("http://dev.w3.org/csswg/css-images-3/#propdef-image-resolution")
    )

    .put("inline-box-align", new CssProperty("inline-box-align")
      .setUrl("http://www.w3.org/TR/css3-linebox/#inline-box-align")
      .addValidator(new IdentifierValidator(ImmutableList.of("initial", "last")))
      .addValidator(PropertyValueValidatorFactory.getIntegerValidator())
    )

    // I
    .put("justify-content", new CssProperty("justify-content")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-justify-content")
      .addValidator(
        new IdentifierValidator(ImmutableList.of("flex-start", "flex-end", "center", "space-between", "space-around")))
    )

    // L
    .put("left", new CssProperty("left")
      .setUrl("http://dev.w3.org/csswg/css2/visuren.html#propdef-left")
      .addValidator(PropertyValueValidatorFactory.getAutoValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("letter-spacing", new CssProperty("letter-spacing")
      .setUrl("http://dev.w3.org/csswg/css-text-3/#propdef-letter-spacing")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal")))
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
    )

    .put("line-height", new CssProperty("line-height")
      .setUrl("http://www.w3.org/TR/CSS21/visudet.html#propdef-line-height")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "none")))
      .addValidator(PropertyValueValidatorFactory.getPositiveLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPositivePercentageValidator())
      .addValidator(PropertyValueValidatorFactory.getPositiveNumberValidator())
    )

    .put("line-stacking", new CssProperty("line-stacking")
      .setUrl("http://www.w3.org/TR/css3-linebox/#line-stacking")
      .addValidator(new LineStackingValidator())
    )

    .put("line-stacking-ruby", new CssProperty("line-stacking-ruby")
      .setUrl("http://www.w3.org/TR/css3-linebox/#line-stacking-ruby")
      .addValidator(new LineStackingRubyValidator())
    )

    .put("line-stacking-shift", new CssProperty("line-stacking-shift")
      .setUrl("http://www.w3.org/TR/css3-linebox/#line-stacking-shift")
      .addValidator(new LineStackingShiftValidator())
    )

    .put("line-stacking-strategy", new CssProperty("line-stacking-strategy")
      .setUrl("http://www.w3.org/TR/css3-linebox/#line-stacking-strategy")
      .addValidator(new LineStackingStrategyValidator())
    )

    .put("list-style", new CssProperty("list-style")
      .setUrl("http://dev.w3.org/csswg/css-lists-3/#propdef-list-style")
      .addValidator(new ListStyleValidator())
    )

    .put("list-style-image", new CssProperty("list-style-image")
      .setUrl("http://dev.w3.org/csswg/css-lists-3/#propdef-list-style-image")
      .addValidator(new ListStyleImageValidator())
    )

    .put("list-style-position", new CssProperty("list-style-position")
      .setUrl("http://dev.w3.org/csswg/css-lists-3/#propdef-list-style-position")
      .addValidator(new ListStylePositionValidator())
    )

    .put("list-style-type", new CssProperty("list-style-type")
      .setUrl("http://dev.w3.org/csswg/css-lists-3/#propdef-list-style-type")
      .addValidator(new ListStyleTypeValidator())
    )

    // M
    .put("margin", new CssProperty("margin")
      .setUrl("http://dev.w3.org/csswg/css-box-3/#margin")
      .addValidator(new MarginValidator())
    )

    .put("margin-bottom", new CssProperty("margin-bottom")
      .setUrl("http://dev.w3.org/csswg/css-box-3/#margin-top")
      .addValidator(PropertyValueValidatorFactory.getMarginWidthValidator())
    )

    .put("margin-end", new CssProperty("margin-end")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-")

    )

    .put("margin-left", new CssProperty("margin-left")
      .setUrl("http://dev.w3.org/csswg/css-box-3/#margin-left")
      .addValidator(PropertyValueValidatorFactory.getMarginWidthValidator())
    )

    .put("margin-right", new CssProperty("margin-right")
      .setUrl("http://dev.w3.org/csswg/css-box-3/#margin-right")
      .addValidator(PropertyValueValidatorFactory.getMarginWidthValidator())
    )

    .put("margin-start", new CssProperty("margin-start")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-")
    )

    .put("margin-top", new CssProperty("margin-top")
      .setUrl("http://dev.w3.org/csswg/css-box-3/#margin-start")
      .addValidator(PropertyValueValidatorFactory.getMarginWidthValidator())
    )

    .put("marks", new CssProperty("marks")
      .setUrl("http://dev.w3.org/csswg/css-page-3/#descdef-page-marks")
    )

    .put("marquee", new CssProperty("marquee")
      .setObsolete(true)
    )

    .put("marquee-dir", new CssProperty("marquee-dir")
      .setObsolete(true)
    )

    .put("marquee-direction", new CssProperty("marquee-direction")
      .setObsolete(true)
    )

    .put("marquee-increment", new CssProperty("marquee-increment")
      .setObsolete(true)
    )

    .put("marquee-loop", new CssProperty("marquee-loop")
      .setObsolete(true)
    )

    .put("marquee-play-count", new CssProperty("marquee-play-count")
      .setObsolete(true)
    )

    .put("marquee-repetition", new CssProperty("marquee-repetition")
      .setObsolete(true)
    )

    .put("marquee-speed", new CssProperty("marquee-speed")
      .setObsolete(true)
    )

    .put("marquee-style", new CssProperty("marquee-style")
      .setObsolete(true)
    )

    .put("max-height", new CssProperty("max-height")
      .setUrl("http://dev.w3.org/csswg/css-sizing-3/#width-height-keywords")
      .addValidator(new WidthHeightValidator())
    )

    .put("max-width", new CssProperty("max-width")
      .setUrl("http://dev.w3.org/csswg/css-sizing-3/#width-height-keywords")
      .addValidator(new WidthHeightValidator())
    )

    .put("min-height", new CssProperty("min-height")
      .setUrl("http://dev.w3.org/csswg/css-sizing-3/#width-height-keywords")
      .addValidator(new WidthHeightValidator())
    )

    .put("min-width", new CssProperty("min-width")
      .setUrl("http://dev.w3.org/csswg/css-sizing-3/#width-height-keywords")
      .addValidator(new WidthHeightValidator())
    )

    .put("move-to", new CssProperty("move-to")
      .setObsolete(true)
    )

    // N
    .put("nav-down", new CssProperty("nav-down")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-nav-down")
    )

    .put("nav-index", new CssProperty("nav-index")
      .setObsolete(true)
    )

    .put("nav-left", new CssProperty("nav-left")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-nav-left")
    )

    .put("nav-right", new CssProperty("nav-right")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-nav-right")
    )

    .put("nav-up", new CssProperty("nav-up")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-nav-up")
    )

    // O
    .put("object-fit", new CssProperty("object-fit")
      .setUrl("http://dev.w3.org/csswg/css-images-3/#propdef-object-fit")
      .addValidator(new IdentifierValidator(ImmutableList.of("fill", "contain", "cover", "none", "scale-down")))
    )

    .put("object-position", new CssProperty("object-position")
      .setUrl("http://dev.w3.org/csswg/css-images-3/#propdef-object-position")
    )

    .put("opacity", new CssProperty("opacity")
      .setUrl("http://dev.w3.org/csswg/css-color-3/#opacity")
      .addValidator(new NumberRangeValidator(0, 1))
    )

    .put("order", new CssProperty("order")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-order")
      .addValidator(PropertyValueValidatorFactory.getIntegerValidator())
    )

    .put("orphans", new CssProperty("orphans")
      .setUrl("http://dev.w3.org/csswg/css-break-3/#propdef-orphans")
      .addValidator(PropertyValueValidatorFactory.getIntegerValidator())
    )

    .put("outline", new CssProperty("outline")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-outline")
      .addValidator(new OutlineValidator())
    )

    .put("outline-color", new CssProperty("outline-color")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-outline-color")
      .addValidator(new OutlineColorValidator())
    )

    .put("outline-offset", new CssProperty("outline-offset")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-outline-offset")
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
    )

    .put("outline-style", new CssProperty("outline-style")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-outline-style")
      .addValidator(new OutlineStyleValidator())
    )

    .put("outline-width", new CssProperty("outline-width")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-outline-width")
      .addValidator(new OutlineWidthValidator())
    )

    .put("overflow", new CssProperty("overflow")
      .setUrl("http://dev.w3.org/csswg/css-box-3/#overflow1")
      .addValidator(new OverflowPropertyValidator())
    )

    .put("overflow-style", new CssProperty("overflow-style")
      .setObsolete(true)
    )

    .put("overflow-wrap", new CssProperty("overflow-wrap")
      .setUrl("http://dev.w3.org/csswg/css-text-3/#propdef-overflow-wrap")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "break-word")))
    )

    .put("overflow-x", new CssProperty("overflow-x")
      .setUrl("http://dev.w3.org/csswg/css-box-3/#overflow-x")
      .addValidator(new OverflowValidator())
    )

    .put("overflow-y", new CssProperty("overflow-y")
      .setUrl("http://dev.w3.org/csswg/css-box-3/#overflow-y")
      .addValidator(new OverflowValidator())
    )

    // P
    .put("padding", new CssProperty("padding")
      .setUrl("http://www.w3.org/TR/CSS2/box.html#propdef-padding")
      .addValidator(new PaddingValidator())
    )

    .put("padding-block-end", new CssProperty("padding-block-end")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-padding-block-end")
      .addValidator(PropertyValueValidatorFactory.getPaddingWidthValidator())
    )

    .put("padding-inline-end", new CssProperty("padding-inline-end")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-padding-inline-end")
      .addValidator(PropertyValueValidatorFactory.getPaddingWidthValidator())
    )

    .put("padding-inline-start", new CssProperty("padding-inline-start")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-padding-inline-start")
      .addValidator(PropertyValueValidatorFactory.getPaddingWidthValidator())
    )

    .put("padding-block-start", new CssProperty("padding-block-start")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-padding-block-start")
      .addValidator(PropertyValueValidatorFactory.getPaddingWidthValidator())
    )

    .put("padding-bottom", new CssProperty("padding-bottom")
      .setUrl("http://www.w3.org/TR/CSS2/box.html#propdef-padding-top")
      .addValidator(PropertyValueValidatorFactory.getPaddingWidthValidator())
    )

    .put("padding-end", new CssProperty("padding-end")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-")
    )

    .put("padding-left", new CssProperty("padding-left")
      .setUrl("http://www.w3.org/TR/CSS2/box.html#propdef-padding-left")
      .addValidator(PropertyValueValidatorFactory.getPaddingWidthValidator())
    )

    .put("padding-right", new CssProperty("padding-right")
      .setUrl("http://www.w3.org/TR/CSS2/box.html#propdef-padding-right")
      .addValidator(PropertyValueValidatorFactory.getPaddingWidthValidator())
    )

    .put("padding-start", new CssProperty("padding-start")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-")
    )

    .put("padding-top", new CssProperty("padding-top")
      .setUrl("http://www.w3.org/TR/CSS2/box.html#propdef-padding-top")
      .addValidator(PropertyValueValidatorFactory.getPaddingWidthValidator())
    )

    .put("page", new CssProperty("page")
      .setUrl("http://dev.w3.org/csswg/css-page-3/#propdef-page")
      .addValidator(new AutoValidator())
      .addValidator(new IdentifierValidator())
    )

    .put("page-break-after", new CssProperty("page-break-after")
      .setUrl("http://dev.w3.org/csswg/css2/page.html#propdef-page-break-after")
      .addValidator(PropertyValueValidatorFactory.getPageBreakValidator())
    )

    .put("page-break-before", new CssProperty("page-break-before")
      .setUrl("http://dev.w3.org/csswg/css2/page.html#propdef-page-break-before")
      .addValidator(PropertyValueValidatorFactory.getPageBreakValidator())
    )

    .put("page-break-inside", new CssProperty("page-break-inside")
      .setUrl("http://dev.w3.org/csswg/css2/page.html#propdef-page-break-inside   ")
      .addValidator(new IdentifierValidator(ImmutableList.of("avoid", "auto")))
    )

    .put("page-policy", new CssProperty("page-policy")

    )

    .put("pause", new CssProperty("pause")
      .setUrl("http://www.w3.org/TR/css3-speech/#pause")
      .addValidator(new PausePropertyValidator())
    )

    .put("pause-after", new CssProperty("pause-after")
      .setUrl("http://www.w3.org/TR/css3-speech/#pause-after")
      .addValidator(PropertyValueValidatorFactory.getPauseValidator())
    )

    .put("pause-before", new CssProperty("pause-before")
      .setUrl("http://www.w3.org/TR/css3-speech/#pause-before")
      .addValidator(PropertyValueValidatorFactory.getPauseValidator())
    )

    .put("perspective", new CssProperty("perspective")

    )

    .put("perspective-origin", new CssProperty("perspective-origin")

    )

    .put("phonemes", new CssProperty("phonemes")

    )

    .put("pitch", new CssProperty("pitch")
      .setUrl("http://www.w3.org/TR/CSS21/aural.html#propdef-pitch")
      .addValidator(PropertyValueValidatorFactory.getFrequencyValidator())
      .addValidator(new IdentifierValidator(ImmutableList.of("x-low", "low", "medium", "high", "x-high")))
    )

    .put("pitch-range", new CssProperty("pitch-range")
      .setUrl("http://www.w3.org/TR/CSS21/aural.html#propdef-pitch-range")
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
    )

    .put("play-during", new CssProperty("play-during")

    )

    .put("pointer-events", new CssProperty("pointer-events")

    )

    .put("position", new CssProperty("position")
      .setUrl("http://dev.w3.org/csswg/css-position-3/#position-property")
      .addValidator(new IdentifierValidator(ImmutableList.of("static", "relative", "absolute", "fixed", "sticky")))
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
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-resize")
      .addValidator(new IdentifierValidator(ImmutableList.of("none", "both", "horizontal", "vertical")))
    )

    .put("rest", new CssProperty("rest")
      .setUrl("http://www.w3.org/TR/css3-speech/#rest")
    )

    .put("rest-after", new CssProperty("rest-after")
      .setUrl("http://www.w3.org/TR/css3-speech/#rest-after")
    )

    .put("rest-before", new CssProperty("rest-before")
      .setUrl("http://www.w3.org/TR/css3-speech/#rest-before")
    )

    .put("richness", new CssProperty("richness")
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
    )

    .put("right", new CssProperty("right")
      .setUrl("http://dev.w3.org/csswg/css2/visuren.html#propdef-right")
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
      .setUrl("http://www.w3.org/TR/css3-speech/#speak")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "normal", "none", "spell-out")))
    )

    .put("speak-as", new CssProperty("speak-as")
      .setUrl("http://www.w3.org/TR/css3-speech/#speak-as")
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
      .setUrl("http://dev.w3.org/csswg/css3-text/#text-align-property")
      .addValidator(new IdentifierValidator(
        ImmutableList.of("start", "end", "left", "right", "center", "justify", "match-parent", "justify-all")))
    )

    .put("text-align-all", new CssProperty("text-align-all")
      .setUrl("http://dev.w3.org/csswg/css-text-3/#propdef-text-align-all")
      .addValidator(new IdentifierValidator(ImmutableList.of("start", "end", "left", "right", "center", "justify", "match-parent")))
    )

    .put("text-align-last", new CssProperty("text-align-last")
      .setUrl("http://dev.w3.org/csswg/css-text-3/#propdef-text-align-last")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "start", "end", "left", "right", "center", "justify")))
    )

    .put("text-decoration", new CssProperty("text-decoration")
      .setUrl("http://dev.w3.org/csswg/css-text-decor-3/#propdef-text-decoration")
      .addValidator(new IdentifierValidator(ImmutableList.of("none", "underline", "overline", "line-through")))
    // TODO: Move to CSS3
    // validator
    )

    .put("text-decoration-color", new CssProperty("text-decoration-color")
      .setUrl("http://dev.w3.org/csswg/css-text-decor-3/#propdef-text-decoration-color")
      .addValidator(new ColorValidator())
    )

    .put("text-decoration-line", new CssProperty("text-decoration-line")
      .setUrl("http://dev.w3.org/csswg/css-text-decor-3/#propdef-text-decoration-line")
      .addValidator(new TextDecorationLineValidator())
    )

    .put("text-decoration-style", new CssProperty("text-decoration-style")
      .setUrl("http://dev.w3.org/csswg/css-text-decor-3/#propdef-text-decoration-style")
      .addValidator(new TextDecorationStyleValidator())
    )

    .put("text-emphasis", new CssProperty("text-emphasis")

    )
    .put("text-height", new CssProperty("text-height")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "font-size", "text-size", "max-size")))
    )

    .put("text-indent", new CssProperty("text-indent")
      .setUrl("http://www.w3.org/TR/CSS2/text.html#indentation-prop")
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    .put("text-justify", new CssProperty("text-justify")
      .setUrl("http://dev.w3.org/csswg/css-text-3/#propdef-text-justify")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "none", "inter-word", "inter-character")))
    )

    .put("text-orientation", new CssProperty("text-orientation")
      .setUrl("http://dev.w3.org/csswg/css-writing-modes-3/#propdef-text-orientation")
      .addValidator(new IdentifierValidator(
        ImmutableList.of("mixed", "upright", "sideways-right", "sideways-left", "sideways", "use-glyph-orientation")))
    )

    .put("text-outline", new CssProperty("text-outline")

    )
    .put("text-overflow", new CssProperty("text-overflow")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-text-overflow")
      .addValidator(new TextOverflowValidator())
    )

    .put("text-rendering", new CssProperty("text-rendering")

    )

    .put("text-size-adjust", new CssProperty("text-size-adjust")
      .addVendor("-webkit-")
      .addVendor("-ms-")

    )

    .put("text-shadow", new CssProperty("text-shadow")
      .setUrl("http://dev.w3.org/csswg/css-text-decor-3/#propdef-text-shadow")
    )

    .put("text-transform", new CssProperty("text-transform")
      .setUrl("http://dev.w3.org/csswg/css3-text/#text-transform")
      .addValidator(new IdentifierValidator(ImmutableList.of("capitalize", "uppercase", "lowercase", "full-width", "none")))
    )

    .put("text-wrap", new CssProperty("text-wrap")

    )

    .put("top", new CssProperty("top")
      .setUrl("http://dev.w3.org/csswg/css2/visuren.html#propdef-top")
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
      .setUrl("http://dev.w3.org/csswg/css-writing-modes-3/#propdef-unicode-bidi")
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
      .setUrl("http://www.w3.org/TR/CSS2/visudet.html#propdef-vertical-align")
      .addValidator(new IdentifierValidator(
        ImmutableList
          .of("auto", "use-script", "baseline", "sub", "super", "top", "text-top", "central", "middle",
            "bottom", "text-bottom")))
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
    )

    .put("visibility", new CssProperty("visibility")
      .setUrl("http://www.w3.org/TR/CSS21/visufx.html#propdef-visibility")
      .addValidator(new IdentifierValidator(ImmutableList.of("visible", "hidden", "collapse")))
    )

    .put("voice-balance", new CssProperty("voice-balance")
      .setUrl("http://www.w3.org/TR/css3-speech/#voice-balance")
    )

    .put("voice-duration", new CssProperty("voice-duration")
      .setUrl("http://www.w3.org/TR/css3-speech/#mixing-props-voice-duration")
      .addValidator(new AutoValidator())
      .addValidator(new TimeValidator(true))
    )

    .put("voice-family", new CssProperty("voice-family")
      .setUrl("http://www.w3.org/TR/css3-speech/#voice-family")
    )

    .put("voice-pitch", new CssProperty("voice-pitch")
      .setUrl("http://www.w3.org/TR/css3-speech/#voice-pitch")
    )

    .put("voice-range", new CssProperty("voice-range")
      .setUrl("http://www.w3.org/TR/css3-speech/#voice-range")
    )

    .put("voice-rate", new CssProperty("voice-rate")
      .setUrl("http://www.w3.org/TR/css3-speech/#voice-rate")
    )

    .put("voice-stress", new CssProperty("voice-stress")
      .setUrl("http://www.w3.org/TR/css3-speech/#voice-stress")
    )

    .put("voice-volume", new CssProperty("voice-volume")
      .setUrl("http://www.w3.org/TR/css3-speech/#voice-volume")
    )

    .put("volume", new CssProperty("volume")
      .addValidator(new IdentifierValidator(ImmutableList.of("silent", "x-soft", "soft", "medium", "loud", "x-loud")))
      .addValidator(PropertyValueValidatorFactory.getNumberValidator())
      .addValidator(PropertyValueValidatorFactory.getPercentageValidator())
    )

    // W
    .put("white-space", new CssProperty("white-space")
      .setUrl("http://www.w3.org/TR/CSS2/text.html#propdef-white-space")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "pre", "nowrap", "pre-wrap", "pre-line")))
    )

    .put("white-space-collapse", new CssProperty("white-space-collapse")
    )

    .put("widows", new CssProperty("widows")
      .setUrl("http://dev.w3.org/csswg/css-break-3/#propdef-widows")
      .addValidator(PropertyValueValidatorFactory.getIntegerValidator())
    )

    .put("width", new CssProperty("width")
      .setUrl("http://dev.w3.org/csswg/css-sizing-3/#width-height-keywords")
      .addValidator(new WidthHeightValidator())
    )

    .put("word-break", new CssProperty("word-break")
      .addVendor("-epub-")
      .addVendor("-ms-")
    )

    .put("word-spacing", new CssProperty("word-spacing")
      .setUrl("http://www.w3.org/TR/CSS2/text.html#propdef-word-spacing")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal")))
      .addValidator(PropertyValueValidatorFactory.getLengthValidator())
    )

    .put("word-wrap", new CssProperty("word-wrap")
      .setUrl("http://dev.w3.org/csswg/css-text-3/#propdef-word-wrap")
      .addValidator(new IdentifierValidator(ImmutableList.<String>of("normal", "break-word")))
    )

    .put("writing-mode", new CssProperty("writing-mode")
      .setUrl("http://dev.w3.org/csswg/css-writing-modes-3/#propdef-writing-mode")
      .addVendor("-epub-")
      .addVendor("-ms-")
      .addValidator(new IdentifierValidator(ImmutableList.of("horizontal-tb", "vertical-rl", "vertical-lr")))
    )

    // Z
    .put("z-index", new CssProperty("z-index")
      .setUrl("http://www.w3.org/TR/CSS2/visuren.html#propdef-z-index")
      .addValidator(PropertyValueValidatorFactory.getAutoValidator())
      .addValidator(PropertyValueValidatorFactory.getIntegerValidator())
    )

    .put("zoom", new CssProperty("zoom")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal")))
      .addValidator(new PercentageValidator(false))
      .addValidator(new NumberValidator(false))
    )

    .build();

  public static CssProperty getProperty(String propertyName) {
    return PROPERTIES.get(propertyName.toLowerCase());
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
