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
package org.sonar.css.parser;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class CssRealCodeTest {
  private LexerlessGrammar b = CssGrammar.createGrammar();

  @Test
  public void realLife1() throws IOException, URISyntaxException {
    assertThat(b.getRootRule()).matches((readFromFile("normalize.css")));
  }

  @Test
  public void realLife2() throws IOException, URISyntaxException {
    assertThat(b.getRootRule()).matches((readFromFile("animate.css")));
  }

  @Test
  public void realLife3() throws IOException, URISyntaxException {
    assertThat(b.getRootRule()).matches((readFromFile("animate.min.css")));
  }

  @Test
  public void realLife4() throws IOException, URISyntaxException {
    assertThat(b.getRootRule()).matches((readFromFile("demo.autoprefixed.css")));
  }

  @Test
  public void realLife5() throws IOException, URISyntaxException {
    assertThat(b.getRootRule()).matches((readFromFile("effeckt.autoprefixed.css")));
  }

  @Test
  public void realLife6() throws IOException, URISyntaxException {
    assertThat(b.getRootRule()).matches((readFromFile("form-elements.css")));
  }

  @Test
  public void should_parse_starting_with_BOM() throws IOException, URISyntaxException {
    assertThat(b.getRootRule()).matches((readFromFile("starting-with-bom.css")));
  }

  private String readFromFile(String fileName) throws IOException, URISyntaxException {
    StringBuilder text = new StringBuilder();
    File file = new File(CssRealCodeTest.class.getClassLoader().getResource(fileName)
      .toURI());
    BufferedReader reader = Files.newReader(file, Charsets.UTF_8);
    String line = null;
    while ((line = reader.readLine()) != null) {
      text.append(line).append("\n");
    }
    return text.toString();
  }

}
