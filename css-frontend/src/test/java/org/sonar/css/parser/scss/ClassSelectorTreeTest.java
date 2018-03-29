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
package org.sonar.css.parser.scss;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.ClassSelectorTree;

import static org.fest.assertions.Assertions.assertThat;

public class ClassSelectorTreeTest extends ScssTreeTest {

  public ClassSelectorTreeTest() {
    super(LexicalGrammar.CLASS_SELECTOR);
  }

  @Test
  public void classSelector() {
    checkParsed(".class", "class", false);
    checkParsed(".#{$class}", "#{$class}", true);
    checkParsed(".#{$cla-ss}", "#{$cla-ss}", true);
    checkParsed(".#{$cla_ss}", "#{$cla_ss}", true);
    checkParsed(".#{$cla0ss}", "#{$cla0ss}", true);
    checkParsed(".abc#{$class}", "abc#{$class}", true);
    checkParsed(".abc#{$class}#{$class1}def", "abc#{$class}#{$class1}def", true);
    checkParsed(".abc#{$class}e#{$class1}def", "abc#{$class}e#{$class1}def", true);
    checkParsed(".-abc#{$class}e#{$class1}def", "-abc#{$class}e#{$class1}def", true);
    checkParsed(".--abc#{$class}e#{$class1}def", "--abc#{$class}e#{$class1}def", true);
  }

  @Test
  public void notClassSelector() {
    checkNotParsed(".");
    checkNotParsed(" .");
    checkNotParsed(" .class");
    checkNotParsed("class");
    checkNotParsed(". class");
  }

  private void checkParsed(String toParse, String expectedClassName, boolean isInterpolated) {
    ClassSelectorTree tree = (ClassSelectorTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.dot()).isNotNull();
    assertThat(tree.className()).isNotNull();
    assertThat(tree.className().text()).isEqualTo(expectedClassName);
    assertThat(tree.className().isScssInterpolated()).isEqualTo(isInterpolated);
    assertThat(tree.className().isInterpolated()).isEqualTo(isInterpolated);
  }

}
