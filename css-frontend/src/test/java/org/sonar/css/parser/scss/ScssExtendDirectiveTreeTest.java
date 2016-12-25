/*
 * SonarQube CSS / SCSS / Less Analyzer
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
package org.sonar.css.parser.scss;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;

import static org.fest.assertions.Assertions.assertThat;

public class ScssExtendDirectiveTreeTest extends ScssTreeTest {

  public ScssExtendDirectiveTreeTest() {
    super(LexicalGrammar.SCSS_AT_ROOT_DIRECTIVE);
  }

  @Test
  public void scssAtRootDirective() {
    checkParsed("@at-root");
    checkParsed(" @at-root");
  }

  @Test
  public void notScssAtRootDirective() {
    checkNotParsed("@ at-root");
    checkNotParsed("at-root");
  }

  private void checkParsed(String toParse) {
    SyntaxToken tree = (SyntaxToken) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.text()).isEqualTo("@at-root");
  }

}
