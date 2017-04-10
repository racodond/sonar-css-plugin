/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON
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
package org.sonar.css.parser.css;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.UriTree;

import static org.fest.assertions.Assertions.assertThat;

public class UriTreeTest extends CssTreeTest {

  public UriTreeTest() {
    super(LexicalGrammar.URI);
  }

  @Test
  public void uri() {
    UriTree tree;

    tree = checkParsed("url()");
    assertThat(tree.uriContent()).isNull();

    tree = checkParsed(" url()");
    assertThat(tree.uriContent()).isNull();

    tree = checkParsed(" url(  )");
    assertThat(tree.uriContent()).isNull();

    tree = checkParsed("url(\"http://www.example.com/pinkish.png\")");
    assertThat(tree.uriContent()).isNotNull();

    tree = checkParsed("url(\"yellow\")");
    assertThat(tree.uriContent()).isNotNull();

    tree = checkParsed("URL(\"yellow\")");
    assertThat(tree.uriContent()).isNotNull();

    tree = checkParsed("Url(\"yellow\")");
    assertThat(tree.uriContent()).isNotNull();

    tree = checkParsed("url(base64,abc)");
    assertThat(tree.uriContent()).isNotNull();

    tree = checkParsed("url(\"@{image}/abc.png\")");
    assertThat(tree.uriContent()).isNotNull();
  }

  @Test
  public void notUri() {
    checkNotParsed("urls(\"abc\")");
    checkNotParsed("url(base64, abc)");
  }

  private UriTree checkParsed(String toParse) {
    UriTree tree = (UriTree) parser().parse(toParse);
    assertThat(tree.openParenthesis()).isNotNull();
    assertThat(tree.closeParenthesis()).isNotNull();
    assertThat(tree.urlFunction()).isNotNull();
    return tree;
  }

}
