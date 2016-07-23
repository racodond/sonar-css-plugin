/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013-2016 Tamas Kende and David RACODON
 * mailto: kende.tamas@gmail.com and david.racodon@gmail.com
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
package org.sonar.css;

import com.google.common.base.Charsets;

import java.nio.charset.Charset;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CssConfigurationTest {

  @Test
  public void charset() {
    Charset charset = mock(Charset.class);
    CssConfiguration conf = new CssConfiguration(charset);
    assertThat(conf.getCharset()).isSameAs(charset);
  }

  @Test
  public void ignoreHeaderComments() {
    CssConfiguration conf = new CssConfiguration(Charsets.UTF_8);

    assertThat(conf.ignoreHeaderComments()).isFalse();

    conf.ignoreHeaderComments(true);
    assertThat(conf.ignoreHeaderComments()).isTrue();

    conf.ignoreHeaderComments(false);
    assertThat(conf.ignoreHeaderComments()).isFalse();
  }

}
