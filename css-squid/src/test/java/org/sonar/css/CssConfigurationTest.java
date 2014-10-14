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
package org.sonar.css;

import com.google.common.base.Charsets;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CssConfigurationTest {

  @Test
  public void charset() {
    Charset charset = mock(Charset.class);
    Charset charset2 = mock(Charset.class);

    CssConfiguration conf = new CssConfiguration(charset);

    assertThat(conf.charset()).isSameAs(charset);

    conf.charset(charset2);
    assertThat(conf.charset()).isSameAs(charset2);
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
