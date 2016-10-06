/*
 * SonarQube CSS / Less Plugin
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
package org.sonar.css.parser.css;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.StyleSheetTree;

import static org.fest.assertions.Assertions.assertThat;

public class StyleSheetTreeTest extends CssTreeTest {

  public StyleSheetTreeTest() {
    super(LexicalGrammar.STYLESHEET);
  }

  @Test
  public void stylesheet() throws Exception {
    StyleSheetTree tree;

    checkParsed("");
    checkParsed(" ");
    checkParsed("  ");
    checkParsed(" /* blabla */ ");
    checkParsed(" /* blabla\nblabla */ ");
    checkParsed(" <!-- blabla --> ");
    checkParsed(" <!-- blabla\nblabla --> ");
    checkParsed("\ufeff");
    checkParsed("\ufeff ");
    checkParsed("\ufeff   ");

    tree = checkParsed(new File("src/test/resources/normalize.css"));
    assertThat(tree.atRules()).hasSize(0);
    assertThat(tree.rulesets()).hasSize(38);
    assertThat(tree.all()).hasSize(38);

    tree = checkParsed(new File("src/test/resources/animate.css"));
    assertThat(tree.atRules()).hasSize(221);
    assertThat(tree.rulesets()).hasSize(60);
    assertThat(tree.all()).hasSize(281);

    checkParsed(new File("src/test/resources/animate.min.css"));
    checkParsed(new File("src/test/resources/demo.autoprefixed.css"));
    checkParsed(new File("src/test/resources/effeckt.autoprefixed.css"));
    checkParsed(new File("src/test/resources/form-elements.css"));
    checkParsed(new File("src/test/resources/starting-with-bom.css"));
  }

  @Test
  public void notStylesheet() {
    checkNotParsed("abc");
  }

  private void checkParsed(String toParse) {
    parser().parse(toParse);
  }

  private StyleSheetTree checkParsed(File file) throws Exception {
    StyleSheetTree tree = (StyleSheetTree) parser().parse(Files.toString(file, Charsets.UTF_8));
    assertThat(tree.all()).isNotNull();
    assertThat(tree.atRules()).isNotNull();
    assertThat(tree.rulesets()).isNotNull();
    assertThat(tree.lessVariableDeclarations()).isEmpty();
    assertThat(tree.lessMixinCalls()).isEmpty();
    return tree;
  }

}
