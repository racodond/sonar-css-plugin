/*
 * Sonar CSS Plugin
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
package org.sonar.css.checks;

import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * Properties from https://github.com/stubbornella/csslint/blob/c31f1b9c89fa102eb89e96807be9d290110887e5/lib/parserlib.js
 *
 * @author tkende
 *
 */
public final class CssProperties {

  public static List<String> VENDORS = ImmutableList.<String> of(
    "-moz-",
    "-ms-",
    "-webkit-",
    "-o-"
    );

  public static List<String> PROPERTIES = ImmutableList.<String> of(
      // A
      "alignment-adjust",
      "alignment-baseline",
      "animation",
      "animation-delay",
      "animation-direction",
      "animation-duration",
      "animation-iteration-count",
      "animation-name",
      "animation-play-state",
      "animation-timing-function",

      // vendor prefixed
      "-moz-animation-delay",
      "-moz-animation-direction",
      "-moz-animation-duration",
      "-moz-animation-iteration-count",
      "-moz-animation-name",
      "-moz-animation-play-state",

      "-ms-animation-delay",
      "-ms-animation-direction",
      "-ms-animation-duration",
      "-ms-animation-iteration-count",
      "-ms-animation-name",
      "-ms-animation-play-state",

      "-webkit-animation-delay",
      "-webkit-animation-direction",
      "-webkit-animation-duration",
      "-webkit-animation-iteration-count",
      "-webkit-animation-name",
      "-webkit-animation-play-state",

      "-o-animation-delay",
      "-o-animation-direction",
      "-o-animation-duration",
      "-o-animation-iteration-count",
      "-o-animation-name",
      "-o-animation-play-state",

      "appearance",
      "azimuth",

      // B
      "backface-visibility",
      "background",
      "background-attachment",
      "background-clip",
      "background-color",
      "background-image",
      "background-origin",
      "background-position",
      "background-repeat",
      "background-size",
      "baseline-shift",
      "behavior",
      "binding",
      "bleed",
      "bookmark-label",
      "bookmark-level",
      "bookmark-state",
      "bookmark-target",
      "border",
      "border-bottom",
      "border-bottom-color",
      "border-bottom-left-radius",
      "border-bottom-right-radius",
      "border-bottom-style",
      "border-bottom-width",
      "border-collapse",
      "border-color",
      "border-image",
      "border-image-outset",
      "border-image-repeat",
      "border-image-slice",
      "border-image-source",
      "border-image-width",
      "border-left",
      "border-left-color",
      "border-left-style",
      "border-left-width",
      "border-radius",
      "border-right",
      "border-right-color",
      "border-right-style",
      "border-right-width",
      "border-spacing",
      "border-style",
      "border-top",
      "border-top-color",
      "border-top-left-radius",
      "border-top-right-radius",
      "border-top-style",
      "border-top-width",
      "border-width",
      "bottom",
      "box-align",
      "box-decoration-break",
      "box-direction",
      "box-flex",
      "box-flex-group",
      "box-lines",
      "box-ordinal-group",
      "box-orient",
      "box-pack",
      "box-shadow",
      "box-sizing",
      "break-after",
      "break-before",
      "break-inside",

      // C
      "caption-side",
      "clear",
      "clip",
      "color",
      "color-profile",
      "column-count",
      "column-fill",
      "column-gap",
      "column-rule",
      "column-rule-color",
      "column-rule-style",
      "column-rule-width",
      "column-span",
      "column-width",
      "columns",
      "content",
      "counter-increment",
      "counter-reset",
      "crop",
      "cue",
      "cue-after",
      "cue-before",
      "cursor",

      // D
      "direction",
      "display",
      "dominant-baseline",
      "drop-initial-after-adjust",
      "drop-initial-after-align",
      "drop-initial-before-adjust",
      "drop-initial-before-align",
      "drop-initial-size",
      "drop-initial-value",

      // E
      "elevation",
      "empty-cells",

      // F
      "filter",
      "fit",
      "fit-position",
      "float",
      "float-offset",
      "font",
      "font-family",
      "font-size",
      "font-size-adjust",
      "font-stretch",
      "font-style",
      "font-variant",
      "font-weight",

      // G
      "grid-cell-stacking",
      "grid-column",
      "grid-columns",
      "grid-column-align",
      "grid-column-sizing",
      "grid-column-span",
      "grid-flow",
      "grid-layer",
      "grid-row",
      "grid-rows",
      "grid-row-align",
      "grid-row-span",
      "grid-row-sizing",

      // H
      "hanging-punctuation",
      "height",
      "hyphenate-after",
      "hyphenate-before",
      "hyphenate-character",
      "hyphenate-lines",
      "hyphenate-resource",
      "hyphens",

      // I
      "icon",
      "image-orientation",
      "image-rendering",
      "image-resolution",
      "inline-box-align",

      // L
      "left",
      "letter-spacing",
      "line-height",
      "line-break",
      "line-stacking",
      "line-stacking-ruby",
      "line-stacking-shift",
      "line-stacking-strategy",
      "list-style",
      "list-style-image",
      "list-style-position",
      "list-style-type",

      // M
      "margin",
      "margin-bottom",
      "margin-left",
      "margin-right",
      "margin-top",
      "mark",
      "mark-after",
      "mark-before",
      "marks",
      "marquee-direction",
      "marquee-play-count",
      "marquee-speed",
      "marquee-style",
      "max-height",
      "max-width",
      "min-height",
      "min-width",
      "move-to",

      // N
      "nav-down",
      "nav-index",
      "nav-left",
      "nav-right",
      "nav-up",

      // O
      "opacity",
      "orphans",
      "outline",
      "outline-color",
      "outline-offset",
      "outline-style",
      "outline-width",
      "overflow",
      "overflow-style",
      "overflow-x",
      "overflow-y",

      // P
      "padding",
      "padding-bottom",
      "padding-left",
      "padding-right",
      "padding-top",
      "page",
      "page-break-after",
      "page-break-before",
      "page-break-inside",
      "page-policy",
      "pause",
      "pause-after",
      "pause-before",
      "perspective",
      "perspective-origin",
      "phonemes",
      "pitch",
      "pitch-range",
      "play-during",
      "pointer-events",
      "position",
      "presentation-level",
      "punctuation-trim",

      // Q
      "quotes",

      // R
      "rendering-intent",
      "resize",
      "rest",
      "rest-after",
      "rest-before",
      "richness",
      "right",
      "rotation",
      "rotation-point",
      "ruby-align",
      "ruby-overhang",
      "ruby-position",
      "ruby-span",

      // S
      "size",
      "speak",
      "speak-header",
      "speak-numeral",
      "speak-punctuation",
      "speech-rate",
      "src",
      "stress",
      "string-set",

      "table-layout",
      "tab-size",
      "target",
      "target-name",
      "target-new",
      "target-position",
      "text-align",
      "text-align-last",
      "text-decoration",
      "text-emphasis",
      "text-height",
      "text-indent",
      "text-justify",
      "text-outline",
      "text-overflow",
      "text-rendering",
      "text-shadow",
      "text-transform",
      "text-wrap",
      "top",
      "transform",
      "transform-origin",
      "transform-style",
      "transition",
      "transition-delay",
      "transition-duration",
      "transition-property",
      "transition-timing-function",

      // U
      "unicode-bidi",
      "user-modify",
      "user-select",

      // V
      "vertical-align",
      "visibility",
      "voice-balance",
      "voice-duration",
      "voice-family",
      "voice-pitch",
      "voice-pitch-range",
      "voice-rate",
      "voice-stress",
      "voice-volume",
      "volume",

      // W
      "white-space",
      "white-space-collapse",
      "widows",
      "width",
      "word-break",
      "word-spacing",
      "word-wrap",

      // Z
      "z-index",
      "zoom"
      );

}
