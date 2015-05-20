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

/**
 * Properties from https://github.com/stubbornella/csslint/blob/c31f1b9c89fa102eb89e96807be9d290110887e5/lib/parserlib.js
 * vendor mapping from: https://github.com/stubbornella/csslint/blob/master/src/rules/compatible-vendor-prefixes.js
 *
 * @author tkende
 */
public final class CssProperties {

  private CssProperties() {
  }

  public static final ImmutableList<String> VENDORS = ImmutableList.of(
    "-ah-",
    "-apple-",
    "-atsc-",
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

  public static final ImmutableList<CssProperty> PROPERTIES = ImmutableList.of(
    // A
    new CssProperty("alignment-adjust"),
    new CssProperty("alignment-baseline"),
    new CssProperty("animation", "webkit", "moz"),
    new CssProperty("animation-delay", "webkit", "moz"),
    new CssProperty("animation-direction", "webkit", "moz"),
    new CssProperty("animation-duration", "webkit", "moz"),
    new CssProperty("animation-fill-mode", "webkit", "moz"),
    new CssProperty("animation-iteration-count", "webkit", "moz"),
    new CssProperty("animation-name", "webkit", "moz"),
    new CssProperty("animation-play-state", "webkit", "moz"),
    new CssProperty("animation-timing-function", "webkit", "moz"),

    /*
     * // vendor prefixed
     * "-moz-animation-delay",
     * "-moz-animation-direction",
     * "-moz-animation-duration",
     * "-moz-animation-iteration-count",
     * "-moz-animation-name",
     * "-moz-animation-play-state",
     * 
     * "-ms-animation-delay",
     * "-ms-animation-direction",
     * "-ms-animation-duration",
     * "-ms-animation-iteration-count",
     * "-ms-animation-name",
     * "-ms-animation-play-state",
     * 
     * "-webkit-animation-delay",
     * "-webkit-animation-direction",
     * "-webkit-animation-duration",
     * "-webkit-animation-iteration-count",
     * "-webkit-animation-name",
     * "-webkit-animation-play-state",
     * 
     * "-o-animation-delay",
     * "-o-animation-direction",
     * "-o-animation-duration",
     * "-o-animation-iteration-count",
     * "-o-animation-name",
     * "-o-animation-play-state",
     */
    new CssProperty("appearance", "webkit", "moz"),
    new CssProperty("azimuth"),

    // B
    new CssProperty("backface-visibility"),
    new CssProperty("background"),
    new CssProperty("background-attachment"),
    new CssProperty("background-clip"),
    new CssProperty("background-color"),
    new CssProperty("background-image"),
    new CssProperty("background-origin"),
    new CssProperty("background-position"),
    new CssProperty("background-repeat"),
    new CssProperty("background-size"),
    new CssProperty("baseline-shift"),
    new CssProperty("behavior"),
    new CssProperty("binding"),
    new CssProperty("bleed"),
    new CssProperty("bookmark-label"),
    new CssProperty("bookmark-level"),
    new CssProperty("bookmark-state"),
    new CssProperty("bookmark-target"),
    new CssProperty("border"),
    new CssProperty("border-bottom"),
    new CssProperty("border-bottom-color"),
    new CssProperty("border-bottom-left-radius"),
    new CssProperty("border-bottom-right-radius"),
    new CssProperty("border-bottom-style"),
    new CssProperty("border-bottom-width"),
    new CssProperty("border-collapse"),
    new CssProperty("border-color"),
    new CssProperty("border-end", "webkit", "moz"),
    new CssProperty("border-end-color", "webkit", "moz"),
    new CssProperty("border-end-style", "webkit", "moz"),
    new CssProperty("border-end-width", "webkit", "moz"),
    new CssProperty("border-image", "webkit", "moz", "o"),
    new CssProperty("border-image-outset"),
    new CssProperty("border-image-repeat"),
    new CssProperty("border-image-slice"),
    new CssProperty("border-image-source"),
    new CssProperty("border-image-width"),
    new CssProperty("border-left"),
    new CssProperty("border-left-color"),
    new CssProperty("border-left-style"),
    new CssProperty("border-left-width"),
    new CssProperty("border-radius", "webkit"),
    new CssProperty("border-right"),
    new CssProperty("border-right-color"),
    new CssProperty("border-right-style"),
    new CssProperty("border-right-width"),
    new CssProperty("border-start", "webkit", "moz"),
    new CssProperty("border-start-color", "webkit", "moz"),
    new CssProperty("border-start-style", "webkit", "moz"),
    new CssProperty("border-start-width", "webkit", "moz"),
    new CssProperty("border-spacing"),
    new CssProperty("border-style"),
    new CssProperty("border-top"),
    new CssProperty("border-top-color"),
    new CssProperty("border-top-left-radius"),
    new CssProperty("border-top-right-radius"),
    new CssProperty("border-top-style"),
    new CssProperty("border-top-width"),
    new CssProperty("border-width"),
    new CssProperty("bottom"),
    new CssProperty("box-align", "webkit", "moz", "ms"),
    new CssProperty("box-decoration-break"),
    new CssProperty("box-direction", "webkit", "moz", "ms"),
    new CssProperty("box-flex", "webkit", "moz", "ms"),
    new CssProperty("box-flex-group"),
    new CssProperty("box-lines", "webkit", "ms"),
    new CssProperty("box-ordinal-group", "webkit", "moz", "ms"),
    new CssProperty("box-orient", "webkit", "moz", "ms"),
    new CssProperty("box-pack", "webkit", "moz", "ms"),
    new CssProperty("box-shadow", "webkit", "moz"),
    new CssProperty("box-sizing", "webkit", "moz"),
    new CssProperty("break-after"),
    new CssProperty("break-before"),
    new CssProperty("break-inside"),

    // C
    new CssProperty("caption-side"),
    new CssProperty("clear"),
    new CssProperty("clip"),
    new CssProperty("color"),
    new CssProperty("color-profile"),
    new CssProperty("column-count", "webkit", "moz", "ms"),
    new CssProperty("column-fill"),
    new CssProperty("column-gap", "webkit", "moz", "ms"),
    new CssProperty("column-rule", "webkit", "moz", "ms"),
    new CssProperty("column-rule-color", "webkit", "moz", "ms"),
    new CssProperty("column-rule-style", "webkit", "moz", "ms"),
    new CssProperty("column-rule-width", "webkit", "moz", "ms"),
    new CssProperty("column-span"),
    new CssProperty("column-width", "webkit", "moz", "ms"),
    new CssProperty("columns"),
    new CssProperty("content"),
    new CssProperty("counter-increment"),
    new CssProperty("counter-reset"),
    new CssProperty("crop"),
    new CssProperty("cue"),
    new CssProperty("cue-after"),
    new CssProperty("cue-before"),
    new CssProperty("cursor"),

    // D
    new CssProperty("direction"),
    new CssProperty("display"),
    new CssProperty("dominant-baseline"),
    new CssProperty("drop-initial-after-adjust"),
    new CssProperty("drop-initial-after-align"),
    new CssProperty("drop-initial-before-adjust"),
    new CssProperty("drop-initial-before-align"),
    new CssProperty("drop-initial-size"),
    new CssProperty("drop-initial-value"),

    // E
    new CssProperty("elevation"),
    new CssProperty("empty-cells"),

    // F
    new CssProperty("filter"),
    new CssProperty("fit"),
    new CssProperty("fit-position"),
    new CssProperty("float"),
    new CssProperty("float-offset"),
    new CssProperty("font"),
    new CssProperty("font-family"),
    new CssProperty("font-size"),
    new CssProperty("font-size-adjust"),
    new CssProperty("font-stretch"),
    new CssProperty("font-style"),
    new CssProperty("font-variant"),
    new CssProperty("font-weight"),

    // G
    new CssProperty("grid-cell-stacking"),
    new CssProperty("grid-column"),
    new CssProperty("grid-columns"),
    new CssProperty("grid-column-align"),
    new CssProperty("grid-column-sizing"),
    new CssProperty("grid-column-span"),
    new CssProperty("grid-flow"),
    new CssProperty("grid-layer"),
    new CssProperty("grid-row"),
    new CssProperty("grid-rows"),
    new CssProperty("grid-row-align"),
    new CssProperty("grid-row-span"),
    new CssProperty("grid-row-sizing"),

    // H
    new CssProperty("hanging-punctuation"),
    new CssProperty("height"),
    new CssProperty("hyphenate-after"),
    new CssProperty("hyphenate-before"),
    new CssProperty("hyphenate-character"),
    new CssProperty("hyphenate-lines"),
    new CssProperty("hyphenate-resource"),
    new CssProperty("hyphens", "epub", "moz"),

    // I
    new CssProperty("icon"),
    new CssProperty("image-orientation"),
    new CssProperty("image-rendering"),
    new CssProperty("image-resolution"),
    new CssProperty("inline-box-align"),

    // L
    new CssProperty("left"),
    new CssProperty("letter-spacing"),
    new CssProperty("line-height"),
    new CssProperty("line-break", "webkit", "ms"),
    new CssProperty("line-stacking"),
    new CssProperty("line-stacking-ruby"),
    new CssProperty("line-stacking-shift"),
    new CssProperty("line-stacking-strategy"),
    new CssProperty("list-style"),
    new CssProperty("list-style-image"),
    new CssProperty("list-style-position"),
    new CssProperty("list-style-type"),

    // M
    new CssProperty("margin"),
    new CssProperty("margin-bottom"),
    new CssProperty("margin-end", "webkit", "moz"),
    new CssProperty("margin-left"),
    new CssProperty("margin-right"),
    new CssProperty("margin-start", "webkit", "moz"),
    new CssProperty("margin-top"),
    new CssProperty("mark"),
    new CssProperty("mark-after"),
    new CssProperty("mark-before"),
    new CssProperty("marks"),
    new CssProperty("marquee-direction"),
    new CssProperty("marquee-play-count"),
    new CssProperty("marquee-speed", "webkit", "wap"),
    new CssProperty("marquee-style", "webkit", "wap"),
    new CssProperty("max-height"),
    new CssProperty("max-width"),
    new CssProperty("min-height"),
    new CssProperty("min-width"),
    new CssProperty("move-to"),

    // N
    new CssProperty("nav-down"),
    new CssProperty("nav-index"),
    new CssProperty("nav-left"),
    new CssProperty("nav-right"),
    new CssProperty("nav-up"),

    // O
    new CssProperty("opacity"),
    new CssProperty("orphans"),
    new CssProperty("outline"),
    new CssProperty("outline-color"),
    new CssProperty("outline-offset"),
    new CssProperty("outline-style"),
    new CssProperty("outline-width"),
    new CssProperty("overflow"),
    new CssProperty("overflow-style"),
    new CssProperty("overflow-x"),
    new CssProperty("overflow-y"),

    // P
    new CssProperty("padding"),
    new CssProperty("padding-bottom"),
    new CssProperty("padding-end", "webkit", "moz"),
    new CssProperty("padding-left"),
    new CssProperty("padding-right"),
    new CssProperty("padding-start", "webkit", "moz"),
    new CssProperty("padding-top"),
    new CssProperty("page"),
    new CssProperty("page-break-after"),
    new CssProperty("page-break-before"),
    new CssProperty("page-break-inside"),
    new CssProperty("page-policy"),
    new CssProperty("pause"),
    new CssProperty("pause-after"),
    new CssProperty("pause-before"),
    new CssProperty("perspective"),
    new CssProperty("perspective-origin"),
    new CssProperty("phonemes"),
    new CssProperty("pitch"),
    new CssProperty("pitch-range"),
    new CssProperty("play-during"),
    new CssProperty("pointer-events"),
    new CssProperty("position"),
    new CssProperty("presentation-level"),
    new CssProperty("punctuation-trim"),

    // Q
    new CssProperty("quotes"),

    // R
    new CssProperty("rendering-intent"),
    new CssProperty("resize"),
    new CssProperty("rest"),
    new CssProperty("rest-after"),
    new CssProperty("rest-before"),
    new CssProperty("richness"),
    new CssProperty("right"),
    new CssProperty("rotation"),
    new CssProperty("rotation-point"),
    new CssProperty("ruby-align"),
    new CssProperty("ruby-overhang"),
    new CssProperty("ruby-position"),
    new CssProperty("ruby-span"),

    // S
    new CssProperty("size"),
    new CssProperty("speak"),
    new CssProperty("speak-header"),
    new CssProperty("speak-numeral"),
    new CssProperty("speak-punctuation"),
    new CssProperty("speech-rate"),
    new CssProperty("src"),
    new CssProperty("stress"),
    new CssProperty("string-set"),

    new CssProperty("table-layout"),
    new CssProperty("tab-size", "moz", "o"),
    new CssProperty("target"),
    new CssProperty("target-name"),
    new CssProperty("target-new"),
    new CssProperty("target-position"),
    new CssProperty("text-align"),
    new CssProperty("text-align-last"),
    new CssProperty("text-decoration"),
    new CssProperty("text-emphasis"),
    new CssProperty("text-height"),
    new CssProperty("text-indent"),
    new CssProperty("text-justify"),
    new CssProperty("text-outline"),
    new CssProperty("text-overflow"),
    new CssProperty("text-rendering"),
    new CssProperty("text-size-adjust", "webkit", "ms"),
    new CssProperty("text-shadow"),
    new CssProperty("text-transform"),
    new CssProperty("text-wrap"),
    new CssProperty("top"),
    new CssProperty("transform", "webkit", "moz", "ms", "o"),
    new CssProperty("transform-origin", "webkit", "moz", "ms", "o"),
    new CssProperty("transform-style"),
    new CssProperty("transition", "webkit", "moz", "o"),
    new CssProperty("transition-delay", "webkit", "moz", "o"),
    new CssProperty("transition-duration", "webkit", "moz", "o"),
    new CssProperty("transition-property", "webkit", "moz", "o"),
    new CssProperty("transition-timing-function", "webkit", "moz", "o"),

    // U
    new CssProperty("unicode-bidi"),
    new CssProperty("user-modify", "webkit", "moz"),
    new CssProperty("user-select", "webkit", "moz", "ms"),

    // V
    new CssProperty("vertical-align"),
    new CssProperty("visibility"),
    new CssProperty("voice-balance"),
    new CssProperty("voice-duration"),
    new CssProperty("voice-family"),
    new CssProperty("voice-pitch"),
    new CssProperty("voice-pitch-range"),
    new CssProperty("voice-rate"),
    new CssProperty("voice-stress"),
    new CssProperty("voice-volume"),
    new CssProperty("volume"),

    // W
    new CssProperty("white-space"),
    new CssProperty("white-space-collapse"),
    new CssProperty("widows"),
    new CssProperty("width"),
    new CssProperty("word-break", "epub", "ms"),
    new CssProperty("word-spacing"),
    new CssProperty("word-wrap"),
    new CssProperty("writing-mode", "epub", "ms"),

    // Z
    new CssProperty("z-index"),
    new CssProperty("zoom")
    );

  public static CssProperty getProperty(String property) {
    for (CssProperty p : PROPERTIES) {
      if (p.isProperty(property)) {
        return p;
      }
    }
    return null;
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

}
