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

public class CssUnits {

  public static ImmutableList<String> LENGTH_UNITS = ImmutableList.of("em", "ex", "ch", "rem", "vw", "vh", "vmin", "vmax", "cm", "mm", "in", "px", "pt", "pc");
  public static ImmutableList<String> ANGLE_UNITS = ImmutableList.of("deg", "grad", "rad", "turn");
  public static ImmutableList<String> FREQUENCY_UNITS = ImmutableList.of("Hz", "kHz");
  public static ImmutableList<String> RESOLUTION_UNITS = ImmutableList.of("dpi", "dpcm", "dppx");
  public static ImmutableList<String> TIME_UNITS = ImmutableList.of("ms", "s");

}
