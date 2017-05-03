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

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ColorTest {

  @Test
  public void number_of_svg_colors() {
    assertThat(Color.SVG_COLORS.size()).isEqualTo(147);
  }

  @Test
  public void number_of_css4_colors() {
    assertThat(Color.CSS4_COLORS.size()).isEqualTo(1);
  }

  @Test
  public void number_of_css2_system_colors() {
    assertThat(Color.CSS2_SYSTEM_COLORS.size()).isEqualTo(28);
  }

}
