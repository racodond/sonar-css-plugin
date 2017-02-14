/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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
package org.sonar.css.parser.scss;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssInterpolatedIdentifierTreeTest extends ScssTreeTest {

  public ScssInterpolatedIdentifierTreeTest() {
    super(LexicalGrammar.SCSS_INTERPOLATED_IDENTIFIER);
  }

  @Test
  public void scssInterpolatedIdentifier() {
    checkParsed(" -moz-abc#{$class}e#{$class1}def", "-moz-abc#{$class}e#{$class1}def");
    checkParsed(" -moz-abc#{2 + 3}e#{$class1}def", "-moz-abc#{2 + 3}e#{$class1}def");
    checkParsed(" -moz-abc#{ $abc * 2 + 3}e#{$class1}def", "-moz-abc#{ $abc * 2 + 3}e#{$class1}def");
    checkParsed("#{ 2 * 3 + 5 + $abc}", "#{ 2 * 3 + 5 + $abc}");
    checkParsed("abc#{ 2 * 3 + 5 + $abc}def", "abc#{ 2 * 3 + 5 + $abc}def");
  }

  @Test
  public void notScssInterpolatedIdentifier() {
    checkNotParsed(" *");
    checkNotParsed(" abc");
    checkNotParsed(" .");
    checkNotParsed(" .class");
    checkNotParsed(" .class.class");
    checkNotParsed(" 123");
    checkNotParsed(" 123px");
  }

  private void checkParsed(String toParse, String expectedIdent) {
    IdentifierTree tree = (IdentifierTree) parser().parse(toParse);
    assertThat(tree.value()).isNotNull();
    assertThat(tree.text()).isEqualTo(expectedIdent);
    assertThat(tree.isScssInterpolated()).isTrue();
    assertThat(tree.isInterpolated()).isTrue();
  }

  private void checkParsed(String toParse) {
    checkParsed(toParse, toParse);
  }

}
