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
package org.sonar.css.parser.less;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.IdSelectorTree;

import static org.fest.assertions.Assertions.assertThat;

public class IdSelectorTreeTest extends LessTreeTest {

  public IdSelectorTreeTest() {
    super(LexicalGrammar.ID_SELECTOR);
  }

  @Test
  public void idSelector() {
    checkParsed("#id", "id", false);
    checkParsed("#id@{abc}-def", "id@{abc}-def", true);
  }

  @Test
  public void notIdSelector() {
    checkNotParsed("#");
    checkNotParsed("id");
    checkNotParsed(" #id");
    checkNotParsed("# id");
    checkNotParsed("##id");
  }

  private void checkParsed(String toParse, String expectedIdentifier, boolean isInterpolatedIndentifier) {
    IdSelectorTree tree = (IdSelectorTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.hashSymbol()).isNotNull();
    assertThat(tree.identifier()).isNotNull();
    assertThat(tree.identifier().text()).isEqualTo(expectedIdentifier);
    assertThat(tree.identifier().isLessInterpolated()).isEqualTo(isInterpolatedIndentifier);
    assertThat(tree.identifier().isInterpolated()).isEqualTo(isInterpolatedIndentifier);
  }

}
