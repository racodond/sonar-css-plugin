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

public class CssFunctions {

  public static ImmutableList<String> CSS_FUNCTIONS = ImmutableList.of(
    "annotation",
    "attr",
    "blur",
    "brightness",
    "calc",
    "character-variant",
    "circle",
    "contrast",
    "cubic-bezier",
    "drop-shadow",
    "element",
    "ellipse",
    "format",
    "grayscale",
    "hsl",
    "hsla",
    "hue-rotate",
    "image",
    "inset",
    "invert",
    "linear-gradient",
    "local",
    "matrix",
    "matrix3d",
    "minmax",
    "opacity",
    "ornaments",
    "perspective",
    "polygon",
    "radial-gradient",
    "rect",
    "repeat",
    "repeating-linear-gradient",
    "repeating-radial-gradient",
    "rgb",
    "rgba",
    "rotate",
    "rotatex",
    "rotatey",
    "rotatez",
    "rotate3d",
    "saturate",
    "scale",
    "scalex",
    "scaley",
    "scalez",
    "scale3d",
    "sepia",
    "skew",
    "skewx",
    "skewy",
    "steps",
    "styleset",
    "stylistic",
    "swash",
    "symbols",
    "toggle",
    "translate",
    "translatex",
    "translatey",
    "translatez",
    "translate3d",
    "url",
    "var"
    );

  /**
   * See https://msdn.microsoft.com/en-us/library/ms673539(v=vs.85).aspx
   */
  public static ImmutableList<String> IE_STATIC_FILTERS = ImmutableList.of(
    "alpha",
    "basicimage",
    "blendtrans",
    "blur",
    "chroma",
    "compositor",
    "dropshadow",
    "emboss",
    "engrave",
    "flipH",
    "flipV",
    "glow",
    "gray",
    "icmfilter",
    "invert",
    "light",
    "maskfilter",
    "matrix",
    "motionBlur",
    "redirect",
    "revealTrans",
    "shadow",
    "wave",
    "xray"
    );

  /**
   * See https://msdn.microsoft.com/en-us/library/ms673539(v=vs.85).aspx
   */
  public static ImmutableList<String> IE_STATIC_FILTERS_NOT_IN_CSS_FUNCTIONS = ImmutableList.of(
    "alpha",
    "basicimage",
    "blendtrans",
    // "blur", exists in CSS_FUNCTIONS
    "chroma",
    "compositor",
    "dropshadow",
    "emboss",
    "engrave",
    "flipH",
    "flipV",
    "glow",
    "gray",
    "icmfilter",
    // "invert", exists in CSS_FUNCTIONS
    "light",
    "maskfilter",
    // "matrix", exists in CSS_FUNCTIONS
    "motionBlur",
    "redirect",
    "revealTrans",
    "shadow",
    "wave",
    "xray"
    );

  public static String getFunctionNameWithoutVendorPrefix(String functionName) {
    for (String v : Vendors.VENDORS) {
      if (functionName.startsWith(v)) {
        return functionName.replaceAll("(" + v + ")(.*)", "$2");
      }
    }
    return functionName;
  }

}
