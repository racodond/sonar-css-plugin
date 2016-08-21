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
package org.sonar.css.parser;

import org.junit.Test;
import org.sonar.plugins.css.api.tree.UriTree;

import static org.fest.assertions.Assertions.assertThat;

public class UriTreeTest extends TreeTest {

  public UriTreeTest() {
    super(CssLexicalGrammar.URI);
  }

  @Test
  public void uri() {
    checkParsed("url()");
    checkParsed(" url()");
    checkParsed("url(\"http://www.example.com/pinkish.png\")");
    checkParsed("url(\"yellow\")");
    checkParsed("URL(\"yellow\")");
    checkParsed("Url(\"yellow\")");
    checkParsed("url(base64,abc)");
  }

  @Test
  public void notUri() {
    checkNotParsed("urls(\"abc\")");
    checkNotParsed("url(base64, abc)");
  }

  private void checkParsed(String toParse) {
    UriTree tree = (UriTree) parser().parse(toParse);
    assertThat(tree.openParenthesis()).isNotNull();
    assertThat(tree.closeParenthesis()).isNotNull();
    assertThat(tree.urlFunction()).isNotNull();
    assertThat(tree.uriContent()).isNotNull();
  }

}
