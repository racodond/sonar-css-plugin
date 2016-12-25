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
import org.sonar.plugins.css.api.tree.scss.ScssOperatorTree;

import static org.fest.assertions.Assertions.assertThat;

public class ScssOperatorTreeTest extends ScssTreeTest {

  public ScssOperatorTreeTest() {
    super(LexicalGrammar.SCSS_OPERATOR);
  }

  @Test
  public void scssOperator() {
    checkParsed("/");
    checkParsed("-");
    checkParsed("!=");
    checkParsed("and");
    checkParsed("or");
    checkParsed(" or");
  }

  @Test
  public void notScssOperator() {
    checkNotParsed("=");
    checkNotParsed("a");
  }

  private void checkParsed(String toParse) {
    ScssOperatorTree tree = (ScssOperatorTree) parser().parse(toParse);
    assertThat(tree.value()).isNotNull();
  }

}
