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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

import org.sonar.css.checks.validators.ValidatorFactory;
import org.sonar.css.checks.validators.property.*;
import org.sonar.css.checks.validators.property.animation.*;
import org.sonar.css.checks.validators.property.background.*;
import org.sonar.css.checks.validators.property.border.*;
import org.sonar.css.checks.validators.property.line.LineStackingRubyValidator;
import org.sonar.css.checks.validators.property.line.LineStackingShiftValidator;
import org.sonar.css.checks.validators.property.line.LineStackingStrategyValidator;
import org.sonar.css.checks.validators.property.line.LineStackingValidator;
import org.sonar.css.checks.validators.property.liststyle.ListStyleImageValidator;
import org.sonar.css.checks.validators.property.liststyle.ListStylePositionValidator;
import org.sonar.css.checks.validators.property.liststyle.ListStyleTypeValidator;
import org.sonar.css.checks.validators.property.liststyle.ListStyleValidator;
import org.sonar.css.checks.validators.valueelement.*;
import org.sonar.css.checks.validators.valueelement.flex.*;
import org.sonar.css.checks.validators.valueelement.function.FunctionValidator;
import org.sonar.css.checks.validators.valueelement.numeric.NumberRangeValidator;

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
        ImmutableList.of("flex-start", "flex-end", "center", "space-between", "space-around", "stretch"))))

    .put("align-items", new CssProperty("align-items")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-align-items")
      .addValidator(new IdentifierValidator(ImmutableList.of("flex-start", "flex-end", "center", "baseline", "stretch"))))

    .put("align-self", new CssProperty("align-self")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-align-self")
      .addValidator(
        new IdentifierValidator(ImmutableList.of("auto", "flex-start", "flex-end", "center", "baseline", "stretch"))))

    .put(
      "alignment-adjust",
      new CssProperty("alignment-adjust")
        .setUrl("http://www.w3.org/TR/css3-linebox/#alignment-adjust")
        .addValidator(
          new IdentifierValidator(ImmutableList
            .of("auto", "baseline", "before-edge", "text-before-edge", "middle", "central", "after-edge",
              "text-after-edge",
              "ideographic", "alphabetic", "hanging", "mathematical")))
        .addValidator(ValidatorFactory.getPercentageValidator())
        .addValidator(ValidatorFactory.getLengthValidator()))

    .put("alignment-baseline", new CssProperty("alignment-baseline")
      .setUrl("http://www.w3.org/TR/css3-linebox/#alignment-baseline")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "baseline", "use-script", "before-edge", "text-before-edge",
        "after-edge", "text-after-edge", "central", "middle", "ideographic", "alphabetic", "hanging", "mathematical"))))

    .put("all", new CssProperty("all")
      .setUrl("http://www.w3.org/TR/css3-cascade/#propdef-all")
      .addValidator(new IdentifierValidator(ImmutableList.of("inherit", "initial", "unset"))))

    .put("animation", new CssProperty("animation")
      .setUrl("http://www.w3.org/TR/css3-animations/#animation")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationValidator()))

    .put("animation-delay", new CssProperty("animation-delay")
      .setUrl("http://www.w3.org/TR/css3-animations/#animation-delay")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationDelayValidator()))

    .put("animation-direction", new CssProperty("animation-direction")
      .setUrl("http://www.w3.org/TR/css3-animations/#animation-direction")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationDirectionValidator()))

    .put("animation-duration", new CssProperty("animation-duration")
      .setUrl("http://www.w3.org/TR/css3-animations/#animation-duration-property")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationDurationValidator()))

    .put("animation-fill-mode", new CssProperty("animation-fill-mode")
      .setUrl("http://dev.w3.org/csswg/css-animations/#propdef-animation-fill-mode")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationFillModeValidator()))

    .put("animation-iteration-count", new CssProperty("animation-iteration-count")
      .setUrl("http://dev.w3.org/csswg/css-animations/#propdef-animation-iteration-count")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationIterationCountValidator()))

    .put("animation-name", new CssProperty("animation-name")
      .setUrl("http://dev.w3.org/csswg/css-animations/#propdef-animation-name")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationNameValidator()))

    .put("animation-play-state", new CssProperty("animation-play-state")
      .setUrl("http://dev.w3.org/csswg/css-animations/#propdef-animation-play-state")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationPlayStateValidator()))

    .put("animation-timing-function", new CssProperty("animation-timing-function")
      .setUrl("http://dev.w3.org/csswg/css-animations/#propdef-animation-timing-function")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(new AnimationTimingFunctionValidator()))

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
            "password"))))

    .put("azimuth", new CssProperty("azimuth")
      .setObsolete(true))

    // B
    .put("backface-visibility", new CssProperty("backface-visibility")
      .setUrl("http://dev.w3.org/csswg/css-transforms/#propdef-backface-visibility")
      .addValidator(new IdentifierValidator(ImmutableList.of("visible", "hidden"))))

    .put("background", new CssProperty("background")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background"))

    .put("background-attachment", new CssProperty("background-attachment")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background-attachment")
      .addValidator(new BackgroundAttachmentValidator()))

    .put("background-blend-mode", new CssProperty("background-blend-mode")
      .setUrl("http://dev.w3.org/fxtf/compositing-1/#propdef-background-blend-mode")
      .addValidator(new BlendModeValidator()))

    .put("background-clip", new CssProperty("background-clip")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background-clip")
      .addValidator(new BackgroundClipValidator()))

    .put("background-color", new CssProperty("background-color")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background-color")
      .addValidator(ValidatorFactory.getColorValidator()))

    .put("background-image", new CssProperty("background-image")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background-image")
      .addValidator(new BackgroundImageValidator()))

    .put("background-origin", new CssProperty("background-origin")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background-origin")
      .addValidator(new BackgroundOriginValidator()))

    .put("background-position", new CssProperty("background-position")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background-position"))

    .put("background-repeat", new CssProperty("background-repeat")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background-repeat")
      .addValidator(new BackgroundRepeatValidator()))

    .put("background-size", new CssProperty("background-size")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#background-size")
      .addValidator(new BackgroundSizeValidator()))

    .put("baseline-shift", new CssProperty("baseline-shift")
      .setUrl("http://dev.w3.org/csswg/css-inline/#propdef-baseline-shift")
      .addValidator(new IdentifierValidator(ImmutableList.of("baseline", "sub", "super")))
      .addValidator(ValidatorFactory.getLengthValidator())
      .addValidator(ValidatorFactory.getPercentageValidator()))

    .put("behavior", new CssProperty("behavior")
      .setUrl("http://www.w3.org/TR/1999/WD-becss-19990804#behavior")
      .setObsolete(true))

    .put("binding", new CssProperty("binding")
      .setObsolete(true))

    .put("bleed", new CssProperty("bleed")
      .setUrl("http://dev.w3.org/csswg/css-page/#descdef-page-bleed")
      .addValidator(ValidatorFactory.getAutoValidator())
      .addValidator(ValidatorFactory.getLengthValidator()))

    .put("block-size", new CssProperty("block-size")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-block-size")
      .addValidator(ValidatorFactory.getSizeValidator()))

    .put("bookmark-label", new CssProperty("bookmark-label")
      .setUrl("http://www.w3.org/TR/css3-gcpm/#propdef-bookmark-label"))

    .put("bookmark-level", new CssProperty("bookmark-level")
      .setUrl("http://www.w3.org/TR/css-gcpm-3/#propdef-bookmark-level")
      .addValidator(ValidatorFactory.getNoneValidator())
      .addValidator(ValidatorFactory.getPositiveIntegerValidator()) // TODO: 0 is invalid
  )

    .put("bookmark-state", new CssProperty("bookmark-state")
      .setUrl("http://www.w3.org/TR/css-gcpm-3/#propdef-bookmark-state")
      .addValidator(new IdentifierValidator(ImmutableList.of("open", "close"))))

    .put("border", new CssProperty("border")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-shorthands")
      .addValidator(new BorderPropertyValidator()))

    .put("border-block-end", new CssProperty("border-block-end")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-block-end")
      .addValidator(ValidatorFactory.getBorderValidator()))

    .put("border-block-end-color", new CssProperty("border-block-end-color")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-block-end-color")
      .addValidator(ValidatorFactory.getColorValidator()))

    .put("border-block-end-style", new CssProperty("border-block-end-style")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-block-end-style")
      .addValidator(ValidatorFactory.getBorderStyleValidator()))

    .put("border-block-end-width", new CssProperty("border-block-end-width")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-block-end-width")
      .addValidator(ValidatorFactory.getBorderWidthValidator()))

    .put("border-block-start", new CssProperty("border-block-start")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-block-start")
      .addValidator(ValidatorFactory.getBorderValidator()))

    .put("border-block-start-color", new CssProperty("border-block-start-color")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-block-start-color")
      .addValidator(ValidatorFactory.getColorValidator()))

    .put("border-block-start-style", new CssProperty("border-block-start-style")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-block-start-style")
      .addValidator(ValidatorFactory.getBorderStyleValidator()))

    .put("border-block-start-width", new CssProperty("border-block-start-width")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-block-start-width")
      .addValidator(ValidatorFactory.getBorderWidthValidator()))

    .put("border-bottom", new CssProperty("border-bottom")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-shorthands")
      .addValidator(ValidatorFactory.getBorderValidator()))

    .put("border-bottom-color", new CssProperty("border-bottom-color")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-color")
      .addValidator(ValidatorFactory.getColorValidator()))

    .put("border-bottom-left-radius", new CssProperty("border-bottom-left-radius")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-radius")
      .addValidator(ValidatorFactory.getBorderRadiusValidator()))

    .put("border-bottom-right-radius", new CssProperty("border-bottom-right-radius")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-radius")
      .addValidator(ValidatorFactory.getBorderRadiusValidator()))

    .put("border-bottom-style", new CssProperty("border-bottom-style")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-style")
      .addValidator(ValidatorFactory.getBorderStyleValidator()))

    .put("border-bottom-width", new CssProperty("border-bottom-width")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-width")
      .addValidator(ValidatorFactory.getBorderWidthValidator()))

    .put("border-collapse", new CssProperty("border-collapse")
      .setUrl("http://www.w3.org/TR/CSS2/tables.html#borders")
      .addValidator(new IdentifierValidator(ImmutableList.of("collapse", "separate"))))

    .put("border-color", new CssProperty("border-color")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-color")
      .addValidator(new BorderColorValidator()))

    .put("border-end", new CssProperty("border-end")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-"))

    .put("border-end-color", new CssProperty("border-end-color")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-"))

    .put("border-end-style", new CssProperty("border-end-style")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-"))

    .put("border-end-width", new CssProperty("border-end-width")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-"))

    .put("border-image", new CssProperty("border-image")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-image")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-"))

    .put("border-image-outset", new CssProperty("border-image-outset")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-image-outset"))

    .put("border-image-repeat", new CssProperty("border-image-repeat")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-image-repeat"))

    .put("border-image-slice", new CssProperty("border-image-slice")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-image-slice"))

    .put("border-image-source", new CssProperty("border-image-source")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-image-source")
      .addValidator(ValidatorFactory.getNoneValidator())
      .addValidator(ValidatorFactory.getImageValidator()))

    .put("border-image-width", new CssProperty("border-image-width")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-image-width"))

    .put("border-inline-end", new CssProperty("border-inline-end")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-inline-end-style")
      .addValidator(ValidatorFactory.getBorderValidator()))

    .put("border-inline-end-color", new CssProperty("border-inline-end-color")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-inline-end-style")
      .addValidator(ValidatorFactory.getColorValidator()))

    .put("border-inline-end-style", new CssProperty("border-inline-end-style")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-inline-end-style")
      .addValidator(ValidatorFactory.getBorderStyleValidator()))

    .put("border-inline-end-width", new CssProperty("border-inline-end-width")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-inline-end-width")
      .addValidator(ValidatorFactory.getBorderWidthValidator()))

    .put("border-inline-start", new CssProperty("border-inline-start")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-inline-start")
      .addValidator(ValidatorFactory.getBorderValidator()))

    .put("border-inline-start-color", new CssProperty("border-inline-start-color")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-inline-start-color")
      .addValidator(ValidatorFactory.getColorValidator()))

    .put("border-inline-start-style", new CssProperty("border-inline-start-style")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-inline-start-style")
      .addValidator(ValidatorFactory.getBorderStyleValidator()))

    .put("border-inline-start-width", new CssProperty("border-inline-start-width")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-border-inline-start-width")
      .addValidator(ValidatorFactory.getBorderWidthValidator()))

    .put("border-left", new CssProperty("border-left")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-shorthands")
      .addValidator(ValidatorFactory.getBorderValidator()))

    .put("border-left-color", new CssProperty("border-left-color")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-color")
      .addValidator(ValidatorFactory.getColorValidator()))

    .put("border-left-style", new CssProperty("border-left-style")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-style")
      .addValidator(ValidatorFactory.getBorderStyleValidator()))

    .put("border-left-width", new CssProperty("border-left-width")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-width")
      .addValidator(ValidatorFactory.getBorderWidthValidator()))

    .put("border-radius", new CssProperty("border-radius")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-radius")
      .addVendor("-webkit-")
      .addValidator(new BorderRadiusPropertyValidator()))

    .put("border-right", new CssProperty("border-right")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-shorthands")
      .addValidator(ValidatorFactory.getBorderValidator()))

    .put("border-right-color", new CssProperty("border-right-color")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-color")
      .addValidator(ValidatorFactory.getColorValidator()))

    .put("border-right-style", new CssProperty("border-right-style")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-style")
      .addValidator(ValidatorFactory.getBorderStyleValidator()))

    .put("border-right-width", new CssProperty("border-right-width")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-width")
      .addValidator(ValidatorFactory.getBorderWidthValidator()))

    .put("border-start", new CssProperty("border-start")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-"))

    .put("border-start-color", new CssProperty("border-start-color")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-"))

    .put("border-start-style", new CssProperty("border-start-style")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-"))

    .put("border-start-width", new CssProperty("border-start-width")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-"))

    .put("border-spacing", new CssProperty("border-spacing")
      .setUrl("http://www.w3.org/TR/CSS2/tables.html#separated-borders")
      .addValidator(new BorderSpacingValidator()))

    .put("border-style", new CssProperty("border-style")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-style")
      .addValidator(new BorderStylePropertyValidator()))

    .put("border-top", new CssProperty("border-top")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-shorthands")
      .addValidator(ValidatorFactory.getBorderValidator()))

    .put("border-top-color", new CssProperty("border-top-color")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-color")
      .addValidator(ValidatorFactory.getColorValidator()))

    .put("border-top-left-radius", new CssProperty("border-top-left-radius")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-radius")
      .addValidator(ValidatorFactory.getBorderRadiusValidator()))

    .put("border-top-right-radius", new CssProperty("border-top-right-radius")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-radius")
      .addValidator(ValidatorFactory.getBorderRadiusValidator()))

    .put("border-top-style", new CssProperty("border-top-style")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#the-border-style")
      .addValidator(ValidatorFactory.getBorderStyleValidator()))

    .put("border-top-width", new CssProperty("border-top-width")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-width")
      .addValidator(ValidatorFactory.getBorderWidthValidator()))

    .put("border-width", new CssProperty("border-width")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#border-width")
      .addValidator(new BorderWidthPropertyValidator()))

    .put("bottom", new CssProperty("bottom")
      .setUrl("http://dev.w3.org/csswg/css2/visuren.html#propdef-bottom")
      .addValidator(ValidatorFactory.getAutoValidator())
      .addValidator(ValidatorFactory.getLengthValidator())
      .addValidator(ValidatorFactory.getPercentageValidator()))

    .put("box-align", new CssProperty("box-align")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .setObsolete(true))

    .put("box-decoration-break", new CssProperty("box-decoration-break")
      .setUrl("http://dev.w3.org/csswg/css-break-3/#propdef-box-decoration-break")
      .addValidator(new IdentifierValidator(ImmutableList.of("slice", "clone"))))

    .put("box-direction", new CssProperty("box-direction")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .setObsolete(true))

    .put("box-flex", new CssProperty("box-flex")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .setObsolete(true))

    .put("box-flex-group", new CssProperty("box-flex-group")
      .setObsolete(true))

    .put("box-lines", new CssProperty("box-lines")
      .addVendor("-webkit-")
      .addVendor("-ms-")
      .setObsolete(true))

    .put("box-ordinal-group", new CssProperty("box-ordinal-group")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .setObsolete(true))

    .put("box-orient", new CssProperty("box-orient")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .setObsolete(true))

    .put("box-pack", new CssProperty("box-pack")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .setObsolete(true))

    .put("box-shadow", new CssProperty("box-shadow")
      .setUrl("http://dev.w3.org/csswg/css-backgrounds-3/#box-shadow")
      .addVendor("-webkit-")
      .addVendor("-moz-"))

    .put("box-sizing", new CssProperty("box-sizing")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-box-sizing")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addValidator(ValidatorFactory.getBoxValidator()))

    .put("break-after", new CssProperty("break-after")
      .setUrl("http://dev.w3.org/csswg/css-break-3/#propdef-break-after")
      .addValidator(
        new IdentifierValidator(ImmutableList
          .of("auto", "avoid", "always", "any", "avoid-page", "page", "left", "right", "recto", "verso", "avoid-column",
            "column", "avoid-region", "region"))))

    .put(
      "break-before",
      new CssProperty("break-before")
        .setUrl("http://dev.w3.org/csswg/css-break-3/#propdef-break-before")
        .addValidator(
          new IdentifierValidator(ImmutableList
            .of("auto", "avoid", "always", "any", "avoid-page", "page", "left", "right", "recto", "verso", "avoid-column",
              "column",
              "avoid-region", "region"))))

    .put("break-inside", new CssProperty("break-inside")
      .setUrl("http://dev.w3.org/csswg/css-break-3/#propdef-break-inside")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "avoid", "avoid-page", "avoid-column", "avoid-region"))))

    // C
    .put("caption-side", new CssProperty("caption-side")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#caption-side")
      .addValidator(new IdentifierValidator(
        ImmutableList.of("top", "bottom", "block-start", "block-end", "inline-start", "inline-end"))))

    .put("caret-color", new CssProperty("caret-color")
      .setUrl("http://www.w3.org/TR/2015/WD-css3-ui-20150519/#propdef-caret-color")
      .addValidator(ValidatorFactory.getAutoValidator())
      .addValidator(ValidatorFactory.getColorValidator()))

    .put("clear", new CssProperty("clear")
      .setUrl("http://www.w3.org/TR/CSS2/visuren.html#flow-control")
      .addValidator(new IdentifierValidator(ImmutableList.of("none", "left", "right", "both"))))

    .put("clip", new CssProperty("clip")
      .setUrl("http://www.w3.org/TR/CSS2/visufx.html#propdef-clip")
      .addValidator(new FunctionValidator(ImmutableList.of("rect")))
      .addValidator(ValidatorFactory.getAutoValidator()))

    .put("clip-path", new CssProperty("clip-path")
      .setUrl("https://www.w3.org/TR/SVG/masking.html#ClipPathProperty")
      .addValidator(ValidatorFactory.getNoneValidator())
      .addValidator(ValidatorFactory.getUriValidator()))

    .put("clip-rule", new CssProperty("clip-rule")
      .setUrl("https://www.w3.org/TR/SVG/masking.html#ClipRuleProperty")
      .addValidator(new IdentifierValidator(ImmutableList.of("nonzero", "evenodd"))))

    .put("color", new CssProperty("color")
      .setUrl("http://dev.w3.org/csswg/css-color-3/#color0")
      .addValidator(ValidatorFactory.getColorValidator()))

    .put("color-interpolation", new CssProperty("color-interpolation")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#ColorInterpolationProperty")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "srgb", "linearrgb"))))

    .put("color-interpolation-filters", new CssProperty("color-interpolation-filters")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#ColorInterpolationFiltersProperty")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "srgb", "linearrgb"))))

    .put("color-rendering", new CssProperty("color-rendering")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#ColorRenderingProperty")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "optimizespeed", "optimizequality"))))

    .put("column-count", new CssProperty("column-count")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-count")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(ValidatorFactory.getPositiveIntegerValidator()))

    .put("column-fill", new CssProperty("column-fill")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-fill")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "balance", "balance-all"))))

    .put("column-gap", new CssProperty("column-gap")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-gap")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(ValidatorFactory.getPositiveLengthValidator()))

    .put("column-rule", new CssProperty("column-rule")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-rule")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-"))

    .put("column-rule-color", new CssProperty("column-rule-color")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-rule-color")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(ValidatorFactory.getColorValidator()))

    .put("column-rule-style", new CssProperty("column-rule-style")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-rule-style")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(ValidatorFactory.getBorderStyleValidator()))

    .put("column-rule-width", new CssProperty("column-rule-width")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-rule-width")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(ValidatorFactory.getBorderWidthValidator()))

    .put("column-span", new CssProperty("column-span")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-span")
      .addValidator(new IdentifierValidator(ImmutableList.of("all", "none"))))

    .put("column-width", new CssProperty("column-width")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-column-width")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(ValidatorFactory.getAutoValidator())
      .addValidator(ValidatorFactory.getPositiveLengthValidator()))

    .put("columns", new CssProperty("columns")
      .setUrl("http://dev.w3.org/csswg/css-multicol-1/#propdef-columns"))

    .put("content", new CssProperty("content")
      .setUrl("http://www.w3.org/TR/CSS2/generate.html#content"))

    .put("counter-increment", new CssProperty("counter-increment")
      .setUrl("http://dev.w3.org/csswg/css-lists-3/#propdef-counter-increment")
      .addValidator(ValidatorFactory.getCounterValidator()))

    .put("counter-reset", new CssProperty("counter-reset")
      .setUrl("http://dev.w3.org/csswg/css-lists-3/#propdef-counter-reset")
      .addValidator(ValidatorFactory.getCounterValidator()))

    .put("counter-set", new CssProperty("counter-set")
      .setUrl("http://dev.w3.org/csswg/css-lists-3/#propdef-counter-set")
      .addValidator(ValidatorFactory.getCounterValidator()))

    .put("cue", new CssProperty("cue")
      .setUrl("http://www.w3.org/TR/css3-speech/#cue")
      .addValidator(new CuePropertyValidator()))

    .put("cue-after", new CssProperty("cue-after")
      .setUrl("http://www.w3.org/TR/css3-speech/#cue-after")
      .addValidator(ValidatorFactory.getCueValidator()))

    .put("cue-before", new CssProperty("cue-before")
      .setUrl("http://www.w3.org/TR/css3-speech/#cue-before")
      .addValidator(ValidatorFactory.getCueValidator()))

    .put("cursor", new CssProperty("cursor")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-cursor")
      .addValidator(new CursorValidator()))

    // D
    .put("direction", new CssProperty("direction")
      .setUrl("http://dev.w3.org/csswg/css-writing-modes-3/#propdef-direction")
      .addValidator(new IdentifierValidator(ImmutableList.of("ltr", "rtl"))))

    .put("display", new CssProperty("display")
      .setUrl("http://dev.w3.org/csswg/css-display/#propdef-display")
      .addValidator(new IdentifierValidator(ImmutableList.of("inline", "block", "list-item", "inline-block", "table",
        "inline-table", "table-row-group", "table-header-group", "table-footer-group", "table-row",
        "table-column-group", "table-column", "table-cell", "table-caption", "none", "flex", "inline-flex", "grid",
        "inline-grid", "run-in", "contents", "ruby", "ruby-base", "ruby-text", "ruby-base-container",
        "ruby-text-container", "compact", "marker"))))

    .put("dominant-baseline", new CssProperty("dominant-baseline")
      .setUrl("http://dev.w3.org/csswg/css-inline/#propdef-dominant-baseline")
      .addValidator(new IdentifierValidator(ImmutableList
        .of("auto", "use-script", "no-change", "reset-size", "alphabetic", "hanging", "ideographic", "mathematical",
          "central", "middle", "text-after-edge", "text-before-edge"))))

    .put("drop-initial-after-adjust", new CssProperty("drop-initial-after-adjust")
      .setUrl("http://www.w3.org/TR/css3-linebox/#drop-initial-after-adjust")
      .setObsolete(true))

    .put("drop-initial-after-align", new CssProperty("drop-initial-after-align")
      .setUrl("http://www.w3.org/TR/css3-linebox/#drop-initial-after-align")
      .setObsolete(true))

    .put("drop-initial-before-adjust", new CssProperty("drop-initial-before-adjust")
      .setUrl("http://www.w3.org/TR/css3-linebox/#drop-initial-before-adjust")
      .setObsolete(true))

    .put("drop-initial-before-align", new CssProperty("drop-initial-before-align")
      .setUrl("http://www.w3.org/TR/css3-linebox/#drop-initial-before-align")
      .setObsolete(true))

    .put("drop-initial-size", new CssProperty("drop-initial-size")
      .setUrl("http://www.w3.org/TR/css3-linebox/#drop-initial-size")
      .setObsolete(true))

    .put("drop-initial-value", new CssProperty("drop-initial-value")
      .setUrl("http://www.w3.org/TR/css3-linebox/#drop-initial-value")
      .setObsolete(true))

    // E
    .put("elevation", new CssProperty("elevation")
      .setUrl("http://www.w3.org/TR/CSS21/aural.html#propdef-elevation")
      .addValidator(ValidatorFactory.getAngleValidator())
      .addValidator(new IdentifierValidator(ImmutableList.of("below", "level", "above", "higher", "lower"))))

    .put("empty-cells", new CssProperty("empty-cells")
      .setUrl("http://www.w3.org/TR/CSS2/tables.html#propdef-empty-cells")
      .addValidator(new IdentifierValidator(ImmutableList.of("show", "hide"))))

    // TODO Add validator
    .put("enable-background", new CssProperty("enable-background")
      .setUrl("https://www.w3.org/TR/SVG/filters.html#EnableBackgroundProperty"))

    // F
    .put("fill", new CssProperty("fill")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#FillProperty"))

    .put("fill-opacity", new CssProperty("fill-opacity")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#FillOpacityProperty")
      .addValidator(new NumberRangeValidator(0, 1)))

    .put("fill-rule", new CssProperty("fill-rule")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#FillRuleProperty")
      .addValidator(new IdentifierValidator(ImmutableList.of("nonzero", "evenodd"))))

    .put("filter", new CssProperty("filter")
      .setUrl("http://dev.w3.org/fxtf/filters/#propdef-filter")
      .addValidator(new FilterValidator()))

    .put("fit", new CssProperty("fit")
      .setObsolete(true))

    .put("fit-position", new CssProperty("fit-position")
      .setObsolete(true))

    .put("flex", new CssProperty("flex")
      .setUrl("http://www.w3.org/TR/css-flexbox-1/#propdef-flex"))

    .put("flex-basis", new CssProperty("flex-basis")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-flex-basis")
      .addValidator(new FlexBasisValidator()))

    .put("flex-direction", new CssProperty("flex-direction")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-flex-direction")
      .addValidator(new FlexDirectionValidator()))

    .put("flex-flow", new CssProperty("flex-flow")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-flex-flow")
      .addValidator(new FlexFlowValidator()))

    .put("flex-grow", new CssProperty("flex-grow")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-flex-grow")
      .addValidator(new FlexGrowValidator()))

    .put("flex-shrink", new CssProperty("flex-shrink")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-flex-shrink")
      .addValidator(new FlexShrinkValidator()))

    .put("flex-wrap", new CssProperty("flex-wrap")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-flex-wrap")
      .addValidator(new FlexWrapValidator()))

    .put("float", new CssProperty("float")
      .setUrl("http://www.w3.org/TR/CSS2/visuren.html#propdef-float")
      .addValidator(new IdentifierValidator(ImmutableList.of("left", "right", "none"))))

    .put("float-defer", new CssProperty("float-defer")
      .setUrl("http://dev.w3.org/csswg/css-page-floats/#propdef-float-defer"))

    .put("float-offset", new CssProperty("float-offset")
      .setUrl("http://dev.w3.org/csswg/css-page-floats/#propdef-float-offset"))

    .put("flood-color", new CssProperty("flood-color")
      .setUrl("https://www.w3.org/TR/SVG/filters.html#FloodColorProperty")
      .addValidator(ValidatorFactory.getColorValidator()))

    .put("flood-opacity", new CssProperty("flood-opacity")
      .setUrl("https://www.w3.org/TR/SVG/filters.html#FloodOpacityProperty")
      .addValidator(new NumberRangeValidator(0, 1)))

    .put("font", new CssProperty("font")
      .setUrl("http://dev.w3.org/csswg/css3-fonts/#font"))

    .put("font-family", new CssProperty("font-family")
      .setUrl("http://dev.w3.org/csswg/css3-fonts/#font-family-prop")
      .addValidator(new FontFamilyValidator()))

    .put("font-feature-settings", new CssProperty("font-feature-settings")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#descdef-font-face-font-feature-settings"))

    .put("font-kerning", new CssProperty("font-kerning")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#propdef-font-kerning")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "normal", "none"))))

    .put("font-language-override", new CssProperty("font-language-override")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#propdef-font-language-override")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal")))
      .addValidator(ValidatorFactory.getStringValidator()))

    .put("font-size", new CssProperty("font-size")
      .setUrl("http://www.w3.org/TR/CSS2/fonts.html#propdef-font-size")
      .addValidator(
        new IdentifierValidator(ImmutableList.of("xx-small", "x-small", "small", "medium", "large", "x-large", "xx-large")))
      .addValidator(new IdentifierValidator(ImmutableList.of("larger", "smaller")))
      .addValidator(ValidatorFactory.getPositiveLengthValidator())
      .addValidator(ValidatorFactory.getPositivePercentageValidator()))

    .put("font-size-adjust", new CssProperty("font-size-adjust")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#propdef-font-size-adjust")
      .addValidator(ValidatorFactory.getNoneValidator())
      .addValidator(ValidatorFactory.getNumberValidator()))

    .put("font-stretch", new CssProperty("font-stretch")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#propdef-font-stretch")
      .addValidator(new IdentifierValidator(
        ImmutableList.of("normal", "ultra-condensed", "extra-condensed", "condensed", "semi-condensed",
          "semi-expanded", "expanded", "extra-expanded", "ultra-expanded", "wider", "narrower"))))

    .put("font-style", new CssProperty("font-style")
      .setUrl("http://dev.w3.org/csswg/css3-fonts/#font-style-prop")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "italic", "oblique"))))

    .put("font-synthesis", new CssProperty("font-synthesis")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#propdef-font-synthesis"))

    .put("font-variant", new CssProperty("font-variant")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#descdef-font-face-font-variant")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "small-caps"))))

    .put("font-variant-alternates", new CssProperty("font-variant-alternates")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#propdef-font-variant-alternates"))

    .put("font-variant-caps", new CssProperty("font-variant-caps")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#propdef-font-variant-caps"))

    .put("font-variant-east-asian", new CssProperty("font-variant-east-asian")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#propdef-font-variant-east-asian"))

    .put("font-variant-ligatures", new CssProperty("font-variant-ligatures")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#propdef-font-variant-ligatures"))

    .put("font-variant-numeric", new CssProperty("font-variant-numeric")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#propdef-font-variant-numeric"))

    .put("font-variant-position", new CssProperty("font-variant-position")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#propdef-font-variant-position"))

    .put("font-weight", new CssProperty("font-weight")
      .setUrl("http://dev.w3.org/csswg/css3-fonts/#font-weight-prop")
      .addValidator(new FontWeightValidator()))

    // G
    // TODO: Validator should restrict angles to 0, 90, 180, and 270 degrees
    .put("glyph-orientation-horizontal", new CssProperty("glyph-orientation-horizontal")
      .setUrl("https://www.w3.org/TR/SVG/text.html#GlyphOrientationHorizontalProperty")
      .addValidator(ValidatorFactory.getAngleValidator()))

    // TODO: Validator should restrict angles to 0, 90, 180, and 270 degrees
    .put("glyph-orientation-vertical", new CssProperty("glyph-orientation-vertical")
      .setUrl("https://www.w3.org/TR/SVG/text.html#GlyphOrientationVerticalProperty")
      .addValidator(ValidatorFactory.getAutoValidator())
      .addValidator(ValidatorFactory.getAngleValidator()))

    .put("grid", new CssProperty("grid")
      .setUrl("http://www.w3.org/TR/css-grid-1/#propdef-grid"))

    .put("grid-area", new CssProperty("grid-area")
      .setUrl("http://www.w3.org/TR/css-grid-1/#propdef-grid-area"))

    .put("grid-cell-stacking", new CssProperty("grid-cell-stacking")
      .setUrl("http://www.w3.org/TR/2011/WD-css3-grid-layout-20110407/#grid-cell-stacking-property-section")
      .setObsolete(true))

    .put("grid-column", new CssProperty("grid-column")
      .setUrl("http://www.w3.org/TR/css-grid-1/#propdef-grid-column"))

    .put("grid-columns", new CssProperty("grid-columns")
      .setUrl("http://www.w3.org/TR/2011/WD-css3-grid-layout-20110407/#grid-columns-property")
      .setObsolete(true))

    .put("grid-column-align", new CssProperty("grid-column-align")
      .setUrl("http://www.w3.org/TR/2011/WD-css3-grid-layout-20110407/#grid-column-align-property")
      .setObsolete(true))

    .put("grid-column-end", new CssProperty("grid-column-end")
      .setUrl("http://www.w3.org/TR/css-grid-1/#propdef-grid-column-end"))

    .put("grid-column-sizing", new CssProperty("grid-column-sizing")
      .setUrl("http://www.w3.org/TR/2011/WD-css3-grid-layout-20110407/#grid-column-sizing-property")
      .setObsolete(true))

    .put("grid-column-span", new CssProperty("grid-column-span")
      .setUrl("http://www.w3.org/TR/2011/WD-css3-grid-layout-20110407/#grid-column-span-property")
      .setObsolete(true))

    .put("grid-column-start", new CssProperty("grid-column-start")
      .setUrl("http://www.w3.org/TR/css-grid-1/#propdef-grid-column-start"))

    .put("grid-flow", new CssProperty("grid-flow")
      .setUrl("http://www.w3.org/TR/2011/WD-css3-grid-layout-20110407/#grid-flow-property")
      .setObsolete(true))

    .put("grid-layer", new CssProperty("grid-layer")
      .setUrl("http://www.w3.org/TR/2011/WD-css3-grid-layout-20110407/#grid-layer-property")
      .setObsolete(true))

    .put("grid-row", new CssProperty("grid-row")
      .setUrl("http://www.w3.org/TR/css-grid-1/#propdef-grid-row"))

    .put("grid-rows", new CssProperty("grid-rows")
      .setUrl("http://www.w3.org/TR/2011/WD-css3-grid-layout-20110407/#grid-rows-property")
      .setObsolete(true))

    .put("grid-row-align", new CssProperty("grid-row-align")
      .setUrl("http://www.w3.org/TR/2011/WD-css3-grid-layout-20110407/#grid-row-align-property")
      .setObsolete(true))

    .put("grid-row-end", new CssProperty("grid-row-end")
      .setUrl("http://www.w3.org/TR/css-grid-1/#propdef-grid-row-end"))

    .put("grid-row-span", new CssProperty("grid-row-span")
      .setUrl("http://www.w3.org/TR/2011/WD-css3-grid-layout-20110407/#grid-row-span-property")
      .setObsolete(true))

    .put("grid-row-sizing", new CssProperty("grid-row-sizing")
      .setUrl("http://www.w3.org/TR/2011/WD-css3-grid-layout-20110407/#grid-row-sizing-property")
      .setObsolete(true))

    .put("grid-row-start", new CssProperty("grid-row-start")
      .setUrl("http://www.w3.org/TR/css-grid-1/#propdef-grid-row-start"))

    .put("grid-template", new CssProperty("grid-template")
      .setUrl("http://www.w3.org/TR/css-grid-1/#propdef-grid-template"))

    .put("grid-template-areas", new CssProperty("grid-template-areas")
      .setUrl("http://www.w3.org/TR/css-grid-1/#propdef-grid-template-areas"))

    .put("grid-template-columns", new CssProperty("grid-template-columns")
      .setUrl("http://www.w3.org/TR/css-grid-1/#propdef-grid-template-columns"))

    .put("grid-template-rows", new CssProperty("grid-template-rows")
      .setUrl("http://www.w3.org/TR/css-grid-1/#propdef-grid-template-rows"))

    // H
    .put("hanging-punctuation", new CssProperty("hanging-punctuation")
      .setUrl("http://www.w3.org/TR/css3-text/#hanging-punctuation"))

    .put("height", new CssProperty("height")
      .setUrl("http://dev.w3.org/csswg/css-sizing-3/#width-height-keywords")
      .addValidator(ValidatorFactory.getWidthHeightValidator()))

    .put("hyphenate-after", new CssProperty("hyphenate-after")
      .setUrl("http://www.w3.org/TR/2010/WD-css3-gcpm-20100608/#hyphenate-after")
      .setObsolete(true))

    .put("hyphenate-before", new CssProperty("hyphenate-before")
      .setUrl("http://www.w3.org/TR/2010/WD-css3-gcpm-20100608/#hyphenate-before")
      .setObsolete(true))

    .put("hyphenate-character", new CssProperty("hyphenate-character")
      .setUrl("http://www.w3.org/TR/2010/WD-css3-gcpm-20100608/#hyphenate-character")
      .setObsolete(true))

    .put("hyphenate-lines", new CssProperty("hyphenate-lines")
      .setUrl("http://www.w3.org/TR/2010/WD-css3-gcpm-20100608/#hyphenate-lines")
      .setObsolete(true))

    .put("hyphenate-resource", new CssProperty("hyphenate-resource")
      .setUrl("http://www.w3.org/TR/2010/WD-css3-gcpm-20100608/#hyphenate-resource")
      .setObsolete(true))

    .put("hyphens", new CssProperty("hyphens")
      .setUrl("http://dev.w3.org/csswg/css-text-3/#propdef-hyphens")
      .addVendor("-epub-")
      .addVendor("-moz-")
      .addValidator(new IdentifierValidator(ImmutableList.of("none", "manual", "auto"))))

    // I
    .put("image-orientation", new CssProperty("image-orientation")
      .setUrl("http://dev.w3.org/csswg/css-images-3/#propdef-image-orientation"))

    .put("image-rendering", new CssProperty("image-rendering")
      .setUrl("http://dev.w3.org/csswg/css-images-3/#propdef-image-rendering")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "optimizespeed", "optimizequality"))))

    .put("image-resolution", new CssProperty("image-resolution")
      .setUrl("http://dev.w3.org/csswg/css-images-3/#propdef-image-resolution"))

    .put("ime-mode", new CssProperty("ime-mode")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#input-method-editor")
      .setObsolete(true))

    .put("inline-box-align", new CssProperty("inline-box-align")
      .setUrl("http://www.w3.org/TR/css3-linebox/#inline-box-align")
      .addValidator(new IdentifierValidator(ImmutableList.of("initial", "last")))
      .addValidator(ValidatorFactory.getIntegerValidator()))

    .put("inline-size", new CssProperty("inline-size")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-inline-size")
      .addValidator(ValidatorFactory.getSizeValidator()))

    .put("isolation", new CssProperty("isolation")
      .setUrl("http://dev.w3.org/fxtf/compositing-1/#propdef-isolation")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "isolate"))))

    // J
    .put("justify-content", new CssProperty("justify-content")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-justify-content")
      .addValidator(
        new IdentifierValidator(ImmutableList.of("flex-start", "flex-end", "center", "space-between", "space-around"))))

    // K
    .put("kerning", new CssProperty("kerning")
      .setUrl("https://www.w3.org/TR/SVG/text.html#KerningProperty")
      .addValidator(ValidatorFactory.getAutoValidator())
      .addValidator(ValidatorFactory.getLengthValidator()))

    // L
    .put("left", new CssProperty("left")
      .setUrl("http://dev.w3.org/csswg/css2/visuren.html#propdef-left")
      .addValidator(ValidatorFactory.getAutoValidator())
      .addValidator(ValidatorFactory.getLengthValidator())
      .addValidator(ValidatorFactory.getPercentageValidator()))

    .put("letter-spacing", new CssProperty("letter-spacing")
      .setUrl("http://dev.w3.org/csswg/css-text-3/#propdef-letter-spacing")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal")))
      .addValidator(ValidatorFactory.getLengthValidator()))

    .put("lighting-color", new CssProperty("lighting-color")
      .setUrl("https://www.w3.org/TR/SVG/filters.html#LightingColorProperty")
      .addValidator(ValidatorFactory.getColorValidator()))

    .put("line-break", new CssProperty("line-break")
      .setUrl("http://www.w3.org/TR/css3-text/#line-break0")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "loose", "normal", "strict"))))

    .put("line-height", new CssProperty("line-height")
      .setUrl("http://www.w3.org/TR/CSS21/visudet.html#propdef-line-height")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "none")))
      .addValidator(ValidatorFactory.getPositiveLengthValidator())
      .addValidator(ValidatorFactory.getPositivePercentageValidator())
      .addValidator(ValidatorFactory.getPositiveNumberValidator()))

    .put("line-stacking", new CssProperty("line-stacking")
      .setUrl("http://www.w3.org/TR/css3-linebox/#line-stacking")
      .addValidator(new LineStackingValidator()))

    .put("line-stacking-ruby", new CssProperty("line-stacking-ruby")
      .setUrl("http://www.w3.org/TR/css3-linebox/#line-stacking-ruby")
      .addValidator(new LineStackingRubyValidator()))

    .put("line-stacking-shift", new CssProperty("line-stacking-shift")
      .setUrl("http://www.w3.org/TR/css3-linebox/#line-stacking-shift")
      .addValidator(new LineStackingShiftValidator()))

    .put("line-stacking-strategy", new CssProperty("line-stacking-strategy")
      .setUrl("http://www.w3.org/TR/css3-linebox/#line-stacking-strategy")
      .addValidator(new LineStackingStrategyValidator()))

    .put("list-style", new CssProperty("list-style")
      .setUrl("http://dev.w3.org/csswg/css-lists-3/#propdef-list-style")
      .addValidator(new ListStyleValidator()))

    .put("list-style-image", new CssProperty("list-style-image")
      .setUrl("http://dev.w3.org/csswg/css-lists-3/#propdef-list-style-image")
      .addValidator(new ListStyleImageValidator()))

    .put("list-style-position", new CssProperty("list-style-position")
      .setUrl("http://dev.w3.org/csswg/css-lists-3/#propdef-list-style-position")
      .addValidator(new ListStylePositionValidator()))

    .put("list-style-type", new CssProperty("list-style-type")
      .setUrl("http://dev.w3.org/csswg/css-lists-3/#propdef-list-style-type")
      .addValidator(new ListStyleTypeValidator()))

    // M
    .put("margin", new CssProperty("margin")
      .setUrl("http://dev.w3.org/csswg/css-box-3/#margin")
      .addValidator(new MarginValidator()))

    .put("margin-bottom", new CssProperty("margin-bottom")
      .setUrl("http://dev.w3.org/csswg/css-box-3/#margin-top")
      .addValidator(ValidatorFactory.getMarginWidthValidator()))

    .put("margin-end", new CssProperty("margin-end")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-"))

    .put("margin-left", new CssProperty("margin-left")
      .setUrl("http://dev.w3.org/csswg/css-box-3/#margin-left")
      .addValidator(ValidatorFactory.getMarginWidthValidator()))

    .put("margin-right", new CssProperty("margin-right")
      .setUrl("http://dev.w3.org/csswg/css-box-3/#margin-right")
      .addValidator(ValidatorFactory.getMarginWidthValidator()))

    .put("margin-start", new CssProperty("margin-start")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-"))

    .put("margin-top", new CssProperty("margin-top")
      .setUrl("http://dev.w3.org/csswg/css-box-3/#margin-start")
      .addValidator(ValidatorFactory.getMarginWidthValidator()))

    .put("marker", new CssProperty("marker")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#MarkerProperty"))

    .put("marker-end", new CssProperty("marker-end")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#MarkerEndProperty")
      .addValidator(ValidatorFactory.getNoneValidator())
      .addValidator(ValidatorFactory.getUriValidator()))

    .put("marker-mid", new CssProperty("marker-mid")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#MarkerMidProperty")
      .addValidator(ValidatorFactory.getNoneValidator())
      .addValidator(ValidatorFactory.getUriValidator()))

    .put("marker-start", new CssProperty("marker-start")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#MarkerStartProperty")
      .addValidator(ValidatorFactory.getNoneValidator())
      .addValidator(ValidatorFactory.getUriValidator()))

    .put("marks", new CssProperty("marks")
      .setUrl("http://dev.w3.org/csswg/css-page-3/#descdef-page-marks"))

    .put("mask", new CssProperty("mask")
      .setUrl("http://dev.w3.org/fxtf/css-masking-1/#propdef-mask"))

    .put("mask-type", new CssProperty("mask-type")
      .setUrl("http://dev.w3.org/fxtf/css-masking-1/#propdef-mask-type")
      .addValidator(new IdentifierValidator(ImmutableList.of("luminance", "alpha"))))

    .put("marquee", new CssProperty("marquee")
      .setObsolete(true))

    .put("marquee-dir", new CssProperty("marquee-dir")
      .setObsolete(true))

    .put("marquee-direction", new CssProperty("marquee-direction")
      .setObsolete(true))

    .put("marquee-increment", new CssProperty("marquee-increment")
      .setObsolete(true))

    .put("marquee-loop", new CssProperty("marquee-loop")
      .setObsolete(true))

    .put("marquee-play-count", new CssProperty("marquee-play-count")
      .setObsolete(true))

    .put("marquee-repetition", new CssProperty("marquee-repetition")
      .setObsolete(true))

    .put("marquee-speed", new CssProperty("marquee-speed")
      .setObsolete(true))

    .put("marquee-style", new CssProperty("marquee-style")
      .setObsolete(true))

    .put("max-block-size", new CssProperty("max-block-size")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-max-block-size")
      .addValidator(ValidatorFactory.getSizeValidator()))

    .put("max-height", new CssProperty("max-height")
      .setUrl("http://dev.w3.org/csswg/css-sizing-3/#width-height-keywords")
      .addValidator(ValidatorFactory.getMaxWidthHeightValidator()))

    .put("max-inline-size", new CssProperty("max-inline-size")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-max-inline-size")
      .addValidator(ValidatorFactory.getSizeValidator()))

    .put("max-width", new CssProperty("max-width")
      .setUrl("http://dev.w3.org/csswg/css-sizing-3/#width-height-keywords")
      .addValidator(ValidatorFactory.getMaxWidthHeightValidator()))

    .put("max-zoom", new CssProperty("max-zoom")
      .setUrl("http://dev.w3.org/csswg/css-device-adapt/#descdef-viewport-max-zoom")
      .addValidator(new ZoomValidator()))

    .put("min-block-size", new CssProperty("min-block-size")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-min-block-size")
      .addValidator(ValidatorFactory.getSizeValidator()))

    .put("min-height", new CssProperty("min-height")
      .setUrl("http://dev.w3.org/csswg/css-sizing-3/#width-height-keywords")
      .addValidator(ValidatorFactory.getMinWidthHeightValidator()))

    .put("min-inline-size", new CssProperty("min-inline-size")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-min-inline-size")
      .addValidator(ValidatorFactory.getSizeValidator()))

    .put("min-width", new CssProperty("min-width")
      .setUrl("http://dev.w3.org/csswg/css-sizing-3/#width-height-keywords")
      .addValidator(ValidatorFactory.getMinWidthHeightValidator()))

    .put("min-zoom", new CssProperty("min-zoom")
      .setUrl("http://dev.w3.org/csswg/css-device-adapt/#descdef-viewport-min-zoom")
      .addValidator(new ZoomValidator()))

    .put("mix-blend-mode", new CssProperty("mix-blend-mode")
      .setUrl("http://dev.w3.org/fxtf/compositing-1/#propdef-mix-blend-mode")
      .addValidator(new BlendModeValidator()))

    .put("move-to", new CssProperty("move-to")
      .setObsolete(true))

    // N
    .put("nav-down", new CssProperty("nav-down")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-nav-down"))

    .put("nav-index", new CssProperty("nav-index")
      .setObsolete(true))

    .put("nav-left", new CssProperty("nav-left")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-nav-left"))

    .put("nav-right", new CssProperty("nav-right")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-nav-right"))

    .put("nav-up", new CssProperty("nav-up")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-nav-up"))

    // O
    .put("object-fit", new CssProperty("object-fit")
      .setUrl("http://dev.w3.org/csswg/css-images-3/#propdef-object-fit")
      .addValidator(new IdentifierValidator(ImmutableList.of("fill", "contain", "cover", "none", "scale-down"))))

    .put("object-position", new CssProperty("object-position")
      .setUrl("http://dev.w3.org/csswg/css-images-3/#propdef-object-position"))

    .put("opacity", new CssProperty("opacity")
      .setUrl("http://dev.w3.org/csswg/css-color-3/#opacity")
      .addValidator(new NumberRangeValidator(0, 1)))

    .put("order", new CssProperty("order")
      .setUrl("http://dev.w3.org/csswg/css-flexbox-1/#propdef-order")
      .addValidator(ValidatorFactory.getIntegerValidator()))

    .put("orientation", new CssProperty("orientation")
      .setUrl("http://dev.w3.org/csswg/css-device-adapt/#descdef-viewport-orientation")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "portrait", "landscape"))))

    .put("orphans", new CssProperty("orphans")
      .setUrl("http://dev.w3.org/csswg/css-break-3/#propdef-orphans")
      .addValidator(ValidatorFactory.getIntegerValidator()))

    .put("outline", new CssProperty("outline")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-outline")
      .addValidator(new OutlineValidator()))

    .put("outline-color", new CssProperty("outline-color")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-outline-color")
      .addValidator(new OutlineColorValidator()))

    .put("outline-offset", new CssProperty("outline-offset")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-outline-offset")
      .addValidator(ValidatorFactory.getLengthValidator()))

    .put("outline-style", new CssProperty("outline-style")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-outline-style")
      .addValidator(new OutlineStyleValidator()))

    .put("outline-width", new CssProperty("outline-width")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-outline-width")
      .addValidator(new OutlineWidthValidator()))

    .put("overflow", new CssProperty("overflow")
      .setUrl("http://dev.w3.org/csswg/css-box-3/#overflow1")
      .addValidator(new OverflowPropertyValidator()))

    .put("overflow-style", new CssProperty("overflow-style")
      .setObsolete(true))

    .put("overflow-wrap", new CssProperty("overflow-wrap")
      .setUrl("http://dev.w3.org/csswg/css-text-3/#propdef-overflow-wrap")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "break-word"))))

    .put("overflow-x", new CssProperty("overflow-x")
      .setUrl("http://dev.w3.org/csswg/css-box-3/#overflow-x")
      .addValidator(new OverflowValidator()))

    .put("overflow-y", new CssProperty("overflow-y")
      .setUrl("http://dev.w3.org/csswg/css-box-3/#overflow-y")
      .addValidator(new OverflowValidator()))

    // P
    .put("padding", new CssProperty("padding")
      .setUrl("http://www.w3.org/TR/CSS2/box.html#propdef-padding")
      .addValidator(new PaddingValidator()))

    .put("padding-block-end", new CssProperty("padding-block-end")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-padding-block-end")
      .addValidator(ValidatorFactory.getPaddingWidthValidator()))

    .put("padding-block-start", new CssProperty("padding-block-start")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-padding-block-start")
      .addValidator(ValidatorFactory.getPaddingWidthValidator()))

    .put("padding-bottom", new CssProperty("padding-bottom")
      .setUrl("http://www.w3.org/TR/CSS2/box.html#propdef-padding-top")
      .addValidator(ValidatorFactory.getPaddingWidthValidator()))

    .put("padding-end", new CssProperty("padding-end")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-"))

    .put("padding-inline-end", new CssProperty("padding-inline-end")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-padding-inline-end")
      .addValidator(ValidatorFactory.getPaddingWidthValidator()))

    .put("padding-inline-start", new CssProperty("padding-inline-start")
      .setUrl("http://dev.w3.org/csswg/css-logical-props/#propdef-padding-inline-start")
      .addValidator(ValidatorFactory.getPaddingWidthValidator()))

    .put("padding-left", new CssProperty("padding-left")
      .setUrl("http://www.w3.org/TR/CSS2/box.html#propdef-padding-left")
      .addValidator(ValidatorFactory.getPaddingWidthValidator()))

    .put("padding-right", new CssProperty("padding-right")
      .setUrl("http://www.w3.org/TR/CSS2/box.html#propdef-padding-right")
      .addValidator(ValidatorFactory.getPaddingWidthValidator()))

    .put("padding-start", new CssProperty("padding-start")
      .setObsolete(true)
      .addVendor("-webkit-")
      .addVendor("-moz-"))

    .put("padding-top", new CssProperty("padding-top")
      .setUrl("http://www.w3.org/TR/CSS2/box.html#propdef-padding-top")
      .addValidator(ValidatorFactory.getPaddingWidthValidator()))

    .put("page", new CssProperty("page")
      .setUrl("http://dev.w3.org/csswg/css-page-3/#propdef-page")
      .addValidator(ValidatorFactory.getAutoValidator())
      .addValidator(ValidatorFactory.getAnyIdentifierValidator()))

    .put("page-break-after", new CssProperty("page-break-after")
      .setUrl("http://dev.w3.org/csswg/css2/page.html#propdef-page-break-after")
      .addValidator(ValidatorFactory.getPageBreakValidator()))

    .put("page-break-before", new CssProperty("page-break-before")
      .setUrl("http://dev.w3.org/csswg/css2/page.html#propdef-page-break-before")
      .addValidator(ValidatorFactory.getPageBreakValidator()))

    .put("page-break-inside", new CssProperty("page-break-inside")
      .setUrl("http://dev.w3.org/csswg/css2/page.html#propdef-page-break-inside   ")
      .addValidator(new IdentifierValidator(ImmutableList.of("avoid", "auto"))))

    .put("page-policy", new CssProperty("page-policy")
      .setUrl("http://www.w3.org/TR/css3-content/#page-policy")
      .setObsolete(true))

    .put("pause", new CssProperty("pause")
      .setUrl("http://www.w3.org/TR/css3-speech/#pause")
      .addValidator(new PausePropertyValidator()))

    .put("pause-after", new CssProperty("pause-after")
      .setUrl("http://www.w3.org/TR/css3-speech/#pause-after")
      .addValidator(ValidatorFactory.getPauseValidator()))

    .put("pause-before", new CssProperty("pause-before")
      .setUrl("http://www.w3.org/TR/css3-speech/#pause-before")
      .addValidator(ValidatorFactory.getPauseValidator()))

    .put("perspective", new CssProperty("perspective")
      .setUrl("http://dev.w3.org/csswg/css-transforms/#propdef-perspective")
      .addValidator(ValidatorFactory.getNoneValidator())
      .addValidator(ValidatorFactory.getPositiveLengthValidator()))

    .put("perspective-origin", new CssProperty("perspective-origin")
      .setUrl("http://dev.w3.org/csswg/css-transforms/#propdef-perspective-origin"))

    .put("phonemes", new CssProperty("phonemes")
      .setUrl("http://www.w3.org/TR/2003/WD-css3-speech-20030514/#phonetic-props"))

    .put("pitch", new CssProperty("pitch")
      .setUrl("http://www.w3.org/TR/CSS21/aural.html#propdef-pitch")
      .addValidator(ValidatorFactory.getFrequencyValidator())
      .addValidator(new IdentifierValidator(ImmutableList.of("x-low", "low", "medium", "high", "x-high"))))

    .put("pitch-range", new CssProperty("pitch-range")
      .setUrl("http://www.w3.org/TR/CSS21/aural.html#propdef-pitch-range")
      .addValidator(ValidatorFactory.getNumberValidator()))

    .put("play-during", new CssProperty("play-during")
      .setUrl("http://www.w3.org/TR/CSS21/aural.html#mixing-props"))

    .put("pointer-events", new CssProperty("pointer-events")
      .setUrl("https://www.w3.org/TR/SVG/interact.html#PointerEventsProperty")
      .addValidator(new IdentifierValidator(ImmutableList.of("visiblepainted", "visiblefill", "visiblestroke", "visible", "painted", "fill", "stroke", "all", "none"))))

    .put("position", new CssProperty("position")
      .setUrl("http://dev.w3.org/csswg/css-position-3/#position-property")
      .addValidator(new IdentifierValidator(ImmutableList.of("static", "relative", "absolute", "fixed", "sticky"))))

    .put("presentation-level", new CssProperty("presentation-level")
      .setUrl("http://www.w3.org/TR/css3-preslev/#presentation-level-property"))

    .put("punctuation-trim", new CssProperty("punctuation-trim")
      .setUrl("http://www.w3.org/TR/2007/WD-css3-text-20070306/#punctuation-trim")
      .setObsolete(true))

    // Q
    .put("quotes", new CssProperty("quotes")
      .setUrl("http://www.w3.org/TR/CSS2/generate.html#quotes"))

    // R
    .put("resize", new CssProperty("resize")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-resize")
      .addValidator(new IdentifierValidator(ImmutableList.of("none", "both", "horizontal", "vertical"))))

    .put("rest", new CssProperty("rest")
      .setUrl("http://www.w3.org/TR/css3-speech/#rest"))

    .put("rest-after", new CssProperty("rest-after")
      .setUrl("http://www.w3.org/TR/css3-speech/#rest-after"))

    .put("rest-before", new CssProperty("rest-before")
      .setUrl("http://www.w3.org/TR/css3-speech/#rest-before"))

    .put("richness", new CssProperty("richness")
      .setUrl("http://www.w3.org/TR/CSS21/aural.html#propdef-richness")
      .addValidator(ValidatorFactory.getNumberValidator()))

    .put("right", new CssProperty("right")
      .setUrl("http://dev.w3.org/csswg/css2/visuren.html#propdef-right")
      .addValidator(ValidatorFactory.getAutoValidator())
      .addValidator(ValidatorFactory.getLengthValidator())
      .addValidator(ValidatorFactory.getPercentageValidator()))

    .put("rotation", new CssProperty("rotation")
      .setUrl("http://www.w3.org/TR/css3-box/#rotation")
      .setObsolete(true))

    .put("rotation-point", new CssProperty("rotation-point")
      .setUrl("http://www.w3.org/TR/css3-box/#rotation-point")
      .setObsolete(true))

    .put("ruby-align", new CssProperty("ruby-align")
      .setUrl("http://dev.w3.org/csswg/css-ruby/#propdef-ruby-align"))

    .put("ruby-merge", new CssProperty("ruby-merge")
      .setUrl("http://dev.w3.org/csswg/css-ruby/#propdef-ruby-merge"))

    .put("ruby-overhang", new CssProperty("ruby-overhang")
      .setUrl("http://www.w3.org/TR/2003/CR-css3-ruby-20030514/#rubyspan")
      .setObsolete(true))

    .put("ruby-position", new CssProperty("ruby-position")
      .setUrl("http://dev.w3.org/csswg/css-ruby/#propdef-ruby-position"))

    .put("ruby-span", new CssProperty("ruby-span")
      .setUrl("http://www.w3.org/TR/2003/CR-css3-ruby-20030514/#rubyspan")
      .setObsolete(true))

    // S
    .put("scrollbar-3dlight-color", new CssProperty("scrollbar-3dlight-color")
      .setObsolete(true))
    .put("scrollbar-arrow-color", new CssProperty("scrollbar-arrow-color")
      .setObsolete(true))
    .put("scrollbar-base-color", new CssProperty("scrollbar-base-color")
      .setObsolete(true))
    .put("scrollbar-darkshadow-color", new CssProperty("scrollbar-darkshadow-color")
      .setObsolete(true))
    .put("scrollbar-face-color", new CssProperty("scrollbar-face-color")
      .setObsolete(true))
    .put("scrollbar-highlight-color", new CssProperty("scrollbar-highlight-color")
      .setObsolete(true))
    .put("scrollbar-shadow-color", new CssProperty("scrollbar-shadow-color")
      .setObsolete(true))
    .put("scrollbar-track-color", new CssProperty("scrollbar-track-color")
      .setObsolete(true))

    .put("shape-image-threshold", new CssProperty("shape-image-threshold")
      .setUrl("http://dev.w3.org/csswg/css-shapes/#propdef-shape-image-threshold")
      .addValidator(new NumberRangeValidator(0.0, 1.0)))

    .put("shape-margin", new CssProperty("shape-margin")
      .setUrl("http://dev.w3.org/csswg/css-shapes/#propdef-shape-margin")
      .addValidator(ValidatorFactory.getLengthValidator())
      .addValidator(ValidatorFactory.getPercentageValidator()))

    .put("shape-outside", new CssProperty("shape-outside")
      .setUrl("http://dev.w3.org/csswg/css-shapes/#propdef-shape-outside")
      .addValidator(new ShapeOutsideValidator()))

    .put("shape-rendering", new CssProperty("shape-rendering")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#ShapeRenderingProperty")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "optimizespeed", "crispedges", "geometricprecision"))))

    .put("size", new CssProperty("size")
      .setUrl("http://www.w3.org/TR/css3-page/#size"))

    .put("speak", new CssProperty("speak")
      .setUrl("http://www.w3.org/TR/css3-speech/#speak")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "normal", "none", "spell-out"))))

    .put("speak-as", new CssProperty("speak-as")
      .setUrl("http://www.w3.org/TR/css3-speech/#speak-as"))

    .put("speak-header", new CssProperty("speak-header")
      .setUrl("http://www.w3.org/TR/CSS21/aural.html#propdef-speak-header")
      .addValidator(new IdentifierValidator(ImmutableList.of("once", "always"))))

    .put("speak-numeral", new CssProperty("speak-numeral")
      .setUrl("http://www.w3.org/TR/CSS21/aural.html#propdef-speak-numeral")
      .addValidator(new IdentifierValidator(ImmutableList.of("digits", "continuous"))))

    .put("speak-punctuation", new CssProperty("speak-punctuation")
      .setUrl("http://www.w3.org/TR/CSS21/aural.html#propdef-speak-punctuation")
      .addValidator(new IdentifierValidator(ImmutableList.of("code", "none"))))

    .put("speech-rate", new CssProperty("speech-rate")
      .setUrl("http://www.w3.org/TR/CSS21/aural.html#propdef-speech-rate")
      .addValidator(
        new IdentifierValidator(
          ImmutableList.of("x-slow", "slow", "medium", "fast", "x-fast", "faster", "slower")))
      .addValidator(ValidatorFactory.getNumberValidator()))

    .put("src", new CssProperty("src")
      .setUrl("http://www.w3.org/TR/css3-fonts/#descdef-src"))

    .put("stop-color", new CssProperty("stop-color")
      .setUrl("https://www.w3.org/TR/SVG/pservers.html#StopColorProperty")
      .addValidator(ValidatorFactory.getColorValidator()))

    .put("stop-opacity", new CssProperty("stop-opacity")
      .setUrl("https://www.w3.org/TR/SVG/pservers.html#StopOpacityProperty")
      .addValidator(new NumberRangeValidator(0, 1)))

    .put("stress", new CssProperty("stress")
      .setUrl("http://www.w3.org/TR/CSS21/aural.html#propdef-stress")
      .addValidator(ValidatorFactory.getNumberValidator()))

    .put("string-set", new CssProperty("string-set")
      .setUrl("http://www.w3.org/TR/css-gcpm-3/#propdef-string-set"))

    .put("stroke", new CssProperty("stroke")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#StrokeProperty"))

    .put("stroke-dasharray", new CssProperty("stroke-dasharray")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#StrokeDasharrayProperty"))

    .put("stroke-dashoffset", new CssProperty("stroke-dashoffset")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#StrokeDashoffsetProperty")
      .addValidator(ValidatorFactory.getPercentageValidator())
      .addValidator(ValidatorFactory.getLengthValidator()))

    .put("stroke-linecap", new CssProperty("stroke-linecap")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#StrokeLinecapProperty")
      .addValidator(new IdentifierValidator(ImmutableList.of("butt", "round", "square"))))

    .put("stroke-linejoin", new CssProperty("stroke-linejoin")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#StrokeLinejoinProperty")
      .addValidator(new IdentifierValidator(ImmutableList.of("miter", "round", "bevel"))))

    .put("stroke-miterlimit", new CssProperty("stroke-miterlimit")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#StrokeMiterlimitProperty")
      .addValidator(new NumberRangeValidator(1.0)))

    .put("stroke-opacity", new CssProperty("stroke-opacity")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#StrokeOpacityProperty")
      .addValidator(new NumberRangeValidator(0, 1)))

    .put("stroke-width", new CssProperty("stroke-width")
      .setUrl("https://www.w3.org/TR/SVG/painting.html#StrokeWidthProperty")
      .addValidator(ValidatorFactory.getPositiveLengthValidator())
      .addValidator(ValidatorFactory.getPositivePercentageValidator()))

    // T
    .put("table-layout", new CssProperty("table-layout")
      .setUrl("http://www.w3.org/TR/CSS2/tables.html#propdef-table-layout")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "fixed"))))

    .put("tab-size", new CssProperty("tab-size")
      .setUrl("http://dev.w3.org/csswg/css-text-3/#propdef-tab-size")
      .addVendor("-moz-")
      .addVendor("-o-")
      .addValidator(ValidatorFactory.getPositiveLengthValidator())
      .addValidator(ValidatorFactory.getPositiveIntegerValidator()))

    .put("target", new CssProperty("target")
      .setObsolete(true))

    .put("target-name", new CssProperty("target-name")
      .setObsolete(true))

    .put("target-new", new CssProperty("target-new")
      .setObsolete(true))

    .put("target-position", new CssProperty("target-position")
      .setObsolete(true))

    .put("text-align", new CssProperty("text-align")
      .setUrl("http://dev.w3.org/csswg/css3-text/#text-align-property")
      .addValidator(new IdentifierValidator(
        ImmutableList.of("start", "end", "left", "right", "center", "justify", "match-parent", "justify-all"))))

    .put("text-align-all", new CssProperty("text-align-all")
      .setUrl("http://dev.w3.org/csswg/css-text-3/#propdef-text-align-all")
      .addValidator(new IdentifierValidator(ImmutableList.of("start", "end", "left", "right", "center", "justify", "match-parent"))))

    .put("text-align-last", new CssProperty("text-align-last")
      .setUrl("http://dev.w3.org/csswg/css-text-3/#propdef-text-align-last")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "start", "end", "left", "right", "center", "justify"))))

    .put("text-anchor", new CssProperty("text-anchor")
      .setUrl("https://www.w3.org/TR/SVG/text.html#TextAnchorProperty")
      .addValidator(new IdentifierValidator(ImmutableList.of("start", "middle", "end"))))

    .put("text-decoration", new CssProperty("text-decoration")
      .setUrl("http://dev.w3.org/csswg/css-text-decor-3/#propdef-text-decoration")
      .addValidator(new IdentifierValidator(ImmutableList.of("none", "underline", "overline", "line-through", "blink")))
  // TODO: Move to CSS3 validator
  )

    .put("text-decoration-color", new CssProperty("text-decoration-color")
      .setUrl("http://dev.w3.org/csswg/css-text-decor-3/#propdef-text-decoration-color")
      .addValidator(ValidatorFactory.getColorValidator()))

    .put("text-decoration-line", new CssProperty("text-decoration-line")
      .setUrl("http://dev.w3.org/csswg/css-text-decor-3/#propdef-text-decoration-line")
      .addValidator(new TextDecorationLineValidator()))

    .put("text-decoration-style", new CssProperty("text-decoration-style")
      .setUrl("http://dev.w3.org/csswg/css-text-decor-3/#propdef-text-decoration-style")
      .addValidator(new TextDecorationStyleValidator()))

    .put("text-emphasis", new CssProperty("text-emphasis")
      .setUrl("http://www.w3.org/TR/css-text-decor-3/#text-emphasis"))

    .put("text-emphasis-color", new CssProperty("text-emphasis-color")
      .setUrl("http://www.w3.org/TR/css-text-decor-3/#text-emphasis-color"))

    .put("text-emphasis-position", new CssProperty("text-emphasis-position")
      .setUrl("http://www.w3.org/TR/css-text-decor-3/#text-emphasis-position"))

    .put("text-emphasis-style", new CssProperty("text-emphasis-style")
      .setUrl("http://www.w3.org/TR/css-text-decor-3/#text-emphasis-style"))

    .put("text-height", new CssProperty("text-height")
      .setUrl("http://www.w3.org/TR/css3-linebox/#text-height")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "font-size", "text-size", "max-size"))))

    .put("text-indent", new CssProperty("text-indent")
      .setUrl("http://www.w3.org/TR/CSS2/text.html#indentation-prop")
      .addValidator(ValidatorFactory.getLengthValidator())
      .addValidator(ValidatorFactory.getPercentageValidator()))

    .put("text-justify", new CssProperty("text-justify")
      .setUrl("http://dev.w3.org/csswg/css-text-3/#propdef-text-justify")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "none", "inter-word", "inter-character"))))

    .put("text-orientation", new CssProperty("text-orientation")
      .setUrl("http://dev.w3.org/csswg/css-writing-modes-3/#propdef-text-orientation")
      .addValidator(new IdentifierValidator(
        ImmutableList.of("mixed", "upright", "sideways-right", "sideways-left", "sideways", "use-glyph-orientation"))))

    .put("text-outline", new CssProperty("text-outline")
      .setObsolete(true))

    .put("text-overflow", new CssProperty("text-overflow")
      .setUrl("http://dev.w3.org/csswg/css-ui-3/#propdef-text-overflow")
      .addValidator(new TextOverflowValidator()))

    .put("text-rendering", new CssProperty("text-rendering")
      .setUrl("http://www.w3.org/TR/SVG11/painting.html#TextRenderingProperty")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "optimizespeed", "optimizelegibility", "geometricprecision"))))

    .put("text-shadow", new CssProperty("text-shadow")
      .setUrl("http://dev.w3.org/csswg/css-text-decor-3/#propdef-text-shadow"))

    .put("text-size-adjust", new CssProperty("text-size-adjust")
      .setUrl("http://dev.w3.org/csswg/css-size-adjust/#text-size-adjust")
      .addVendor("-webkit-")
      .addVendor("-ms-"))

    .put("text-stroke", new CssProperty("text-stroke")
      .setUrl("http://caniuse.com/#feat=text-stroke")
      .setObsolete(true))

    .put("text-transform", new CssProperty("text-transform")
      .setUrl("http://dev.w3.org/csswg/css3-text/#text-transform")
      .addValidator(new IdentifierValidator(ImmutableList.of("capitalize", "uppercase", "lowercase", "full-width", "none"))))

    .put("text-underline-position", new CssProperty("text-underline-position")
      .setUrl("http://www.w3.org/TR/css-text-decor-3/#text-underline-position"))

    .put("text-wrap", new CssProperty("text-wrap")
      .setObsolete(true))

    .put("top", new CssProperty("top")
      .setUrl("http://dev.w3.org/csswg/css2/visuren.html#propdef-top")
      .addValidator(ValidatorFactory.getAutoValidator())
      .addValidator(ValidatorFactory.getLengthValidator())
      .addValidator(ValidatorFactory.getPercentageValidator()))

    .put("touch-action", new CssProperty("touch-action")
      .setUrl("https://w3c.github.io/pointerevents/#the-touch-action-css-property")
      .addValidator(new TouchActionValidator()))

    .put("transform", new CssProperty("transform")
      .setUrl("http://dev.w3.org/csswg/css-transforms/#propdef-transform")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addVendor("-o-")
      .addValidator(new TransformValidator()))

    .put("transform-box", new CssProperty("transform-box")
      .setUrl("http://dev.w3.org/csswg/css-transforms/#propdef-transform-box")
      .addValidator(new IdentifierValidator(ImmutableList.of("border-box", "fill-box", "view-box"))))

    .put("transform-origin", new CssProperty("transform-origin")
      .setUrl("http://dev.w3.org/csswg/css-transforms/#propdef-transform-origin")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addVendor("-o-"))

    .put("transform-style", new CssProperty("transform-style")
      .setUrl("http://dev.w3.org/csswg/css-transforms/#propdef-transform-style")
      .addValidator(new IdentifierValidator(ImmutableList.of("auto", "flat", "preserve-3d"))))

    .put("transition", new CssProperty("transition")
      .setUrl("http://dev.w3.org/csswg/css-transitions/#propdef-transition")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-"))

    .put("transition-delay", new CssProperty("transition-delay")
      .setUrl("http://dev.w3.org/csswg/css-transitions/#propdef-transition-delay")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-"))

    .put("transition-duration", new CssProperty("transition-duration")
      .setUrl("http://dev.w3.org/csswg/css-transitions/#propdef-transition-duration")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-"))

    .put("transition-property", new CssProperty("transition-property")
      .setUrl("http://dev.w3.org/csswg/css-transitions/#propdef-transition-property")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-"))

    .put("transition-timing-function", new CssProperty("transition-timing-function")
      .setUrl("http://dev.w3.org/csswg/css-transitions/#propdef-transition-timing-function")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-o-"))

    // U
    .put("unicode-bidi", new CssProperty("unicode-bidi")
      .setUrl("http://dev.w3.org/csswg/css-writing-modes-3/#propdef-unicode-bidi")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "embed", "bidi-override"))))

    .put("unicode-range", new CssProperty("unicode-range")
      .setUrl("http://dev.w3.org/csswg/css-fonts-3/#descdef-font-face-unicode-range"))

    .put("user-modify", new CssProperty("user-modify")
      .setUrl("http://www.w3.org/TR/2000/WD-css3-userint-20000216#user-modify")
      .setObsolete(true))

    .put("user-select", new CssProperty("user-select")
      .setUrl("http://caniuse.com/#feat=user-select-none")
      .addVendor("-webkit-")
      .addVendor("-moz-")
      .addVendor("-ms-")
      .addValidator(ValidatorFactory.getNoneValidator()))

    .put("user-zoom", new CssProperty("user-zoom")
      .setUrl("http://dev.w3.org/csswg/css-device-adapt/#descdef-viewport-user-zoom")
      .addValidator(new IdentifierValidator(ImmutableList.of("zoom", "fixed"))))

    // V
    .put("vertical-align", new CssProperty("vertical-align")
      .setUrl("http://www.w3.org/TR/CSS2/visudet.html#propdef-vertical-align")
      .addValidator(new IdentifierValidator(
        ImmutableList
          .of("auto", "use-script", "baseline", "sub", "super", "top", "text-top", "central", "middle",
            "bottom", "text-bottom")))
      .addValidator(ValidatorFactory.getPercentageValidator())
      .addValidator(ValidatorFactory.getLengthValidator()))

    .put("visibility", new CssProperty("visibility")
      .setUrl("http://www.w3.org/TR/CSS21/visufx.html#propdef-visibility")
      .addValidator(new IdentifierValidator(ImmutableList.of("visible", "hidden", "collapse"))))

    .put("voice-balance", new CssProperty("voice-balance")
      .setUrl("http://www.w3.org/TR/css3-speech/#voice-balance"))

    .put("voice-duration", new CssProperty("voice-duration")
      .setUrl("http://www.w3.org/TR/css3-speech/#mixing-props-voice-duration")
      .addValidator(ValidatorFactory.getAutoValidator())
      .addValidator(ValidatorFactory.getPositiveTimeValidator()))

    .put("voice-family", new CssProperty("voice-family")
      .setUrl("http://www.w3.org/TR/css3-speech/#voice-family"))

    .put("voice-pitch", new CssProperty("voice-pitch")
      .setUrl("http://www.w3.org/TR/css3-speech/#voice-pitch"))

    .put("voice-range", new CssProperty("voice-range")
      .setUrl("http://www.w3.org/TR/css3-speech/#voice-range"))

    .put("voice-rate", new CssProperty("voice-rate")
      .setUrl("http://www.w3.org/TR/css3-speech/#voice-rate"))

    .put("voice-stress", new CssProperty("voice-stress")
      .setUrl("http://www.w3.org/TR/css3-speech/#voice-stress"))

    .put("voice-volume", new CssProperty("voice-volume")
      .setUrl("http://www.w3.org/TR/css3-speech/#voice-volume"))

    .put("volume", new CssProperty("volume")
      .setUrl("http://www.w3.org/TR/CSS21/aural.html#propdef-volume")
      .addValidator(new IdentifierValidator(ImmutableList.of("silent", "x-soft", "soft", "medium", "loud", "x-loud")))
      .addValidator(ValidatorFactory.getNumberValidator())
      .addValidator(ValidatorFactory.getPercentageValidator()))

    // W
    .put("white-space", new CssProperty("white-space")
      .setUrl("http://www.w3.org/TR/CSS2/text.html#propdef-white-space")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "pre", "nowrap", "pre-wrap", "pre-line"))))

    .put("white-space-collapse", new CssProperty("white-space-collapse")
      .setUrl("http://www.w3.org/TR/2007/WD-css3-text-20070306/#white-space-collapse")
      .setObsolete(true))

    .put("widows", new CssProperty("widows")
      .setUrl("http://dev.w3.org/csswg/css-break-3/#propdef-widows")
      .addValidator(ValidatorFactory.getIntegerValidator()))

    .put("width", new CssProperty("width")
      .setUrl("http://dev.w3.org/csswg/css-sizing-3/#width-height-keywords")
      .addValidator(ValidatorFactory.getWidthHeightValidator()))

    .put("word-break", new CssProperty("word-break")
      .setUrl("http://www.w3.org/TR/css3-text/#word-break")
      .addVendor("-epub-")
      .addVendor("-ms-")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "keep-all", "break-all"))))

    .put("word-spacing", new CssProperty("word-spacing")
      .setUrl("http://www.w3.org/TR/CSS2/text.html#propdef-word-spacing")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal")))
      .addValidator(ValidatorFactory.getLengthValidator()))

    .put("word-wrap", new CssProperty("word-wrap")
      .setUrl("http://dev.w3.org/csswg/css-text-3/#propdef-word-wrap")
      .addValidator(new IdentifierValidator(ImmutableList.of("normal", "break-word"))))

    .put("writing-mode", new CssProperty("writing-mode")
      .setUrl("http://dev.w3.org/csswg/css-writing-modes-3/#propdef-writing-mode")
      .addVendor("-epub-")
      .addVendor("-ms-")
      .addValidator(new IdentifierValidator(ImmutableList.of("horizontal-tb", "vertical-rl", "vertical-lr", "lr-tb", "rl-tb", "tb-rl", "lr", "rl", "tb"))))

    // Z
    .put("z-index", new CssProperty("z-index")
      .setUrl("http://www.w3.org/TR/CSS2/visuren.html#propdef-z-index")
      .addValidator(ValidatorFactory.getAutoValidator())
      .addValidator(ValidatorFactory.getIntegerValidator()))

    .put("zoom", new CssProperty("zoom")
      .setUrl("http://dev.w3.org/csswg/css-device-adapt/#descdef-viewport-zoom")
      .addValidator(new ZoomValidator()))

    .build();

  private CssProperties() {
  }

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
