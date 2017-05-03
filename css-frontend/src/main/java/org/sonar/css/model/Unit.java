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
package org.sonar.css.model;

import com.google.common.collect.ImmutableList;

public class Unit {

  public static final ImmutableList<String> LENGTH_UNITS = ImmutableList.of("em", "ex", "ch", "rem", "vw", "vh", "vmin", "vmax", "cm", "mm", "in", "px", "pt", "pc");
  public static final ImmutableList<String> ANGLE_UNITS = ImmutableList.of("deg", "grad", "rad", "turn");
  public static final ImmutableList<String> FREQUENCY_UNITS = ImmutableList.of("hz", "khz");
  public static final ImmutableList<String> RESOLUTION_UNITS = ImmutableList.of("dpi", "dpcm", "dppx");
  public static final ImmutableList<String> TIME_UNITS = ImmutableList.of("ms", "s");

  private Unit() {
  }

}
