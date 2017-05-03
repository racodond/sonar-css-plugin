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
package org.sonar.css.parser.css;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.UriContentTree;

import static org.fest.assertions.Assertions.assertThat;

public class UriContentTreeTest extends CssTreeTest {

  public UriContentTreeTest() {
    super(LexicalGrammar.URI_CONTENT);
  }

  @Test
  public void uriContent() {
    UriContentTree tree;

    tree = checkParsed("''");
    assertThat(tree.string()).isNotNull();
    assertThat(tree.ident()).isNull();
    assertThat(tree.text()).isEqualTo("");

    tree = checkParsed("\"abc\"");
    assertThat(tree.string()).isNotNull();
    assertThat(tree.ident()).isNull();
    assertThat(tree.string().actualText()).isEqualTo("abc");
    assertThat(tree.text()).isEqualTo("abc");

    tree = checkParsed("base64,abc");
    assertThat(tree.string()).isNull();
    assertThat(tree.ident()).isNotNull();
    assertThat(tree.ident().text()).isEqualTo("base64,abc");
    assertThat(tree.text()).isEqualTo("base64,abc");

    tree = checkParsed(" \"abc\"");
    assertThat(tree.string()).isNotNull();
    assertThat(tree.ident()).isNull();
    assertThat(tree.string().actualText()).isEqualTo("abc");
    assertThat(tree.text()).isEqualTo("abc");

    tree = checkParsed(" base64,abc");
    assertThat(tree.string()).isNull();
    assertThat(tree.ident()).isNotNull();
    assertThat(tree.ident().text()).isEqualTo(" base64,abc");
    assertThat(tree.text()).isEqualTo(" base64,abc");
  }

  @Test
  public void notUriContent() {
    checkNotParsed("");
  }

  private UriContentTree checkParsed(String toParse) {
    UriContentTree tree = (UriContentTree) parser().parse(toParse);
    assertThat(tree.text()).isNotNull();
    return tree;
  }

}
