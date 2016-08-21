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
import org.sonar.plugins.css.api.tree.*;

import static org.fest.assertions.Assertions.assertThat;

public class CompoundSelectorTreeTest extends TreeTest {

  public CompoundSelectorTreeTest() {
    super(CssLexicalGrammar.COMPOUND_SELECTOR);
  }

  @Test
  public void compoundSelector() {
    CompoundSelectorTree tree;

    tree = checkParsed("[attribute~=abc]");
    assertThat(tree.selectors()).hasSize(1);
    assertThat(tree.selectors().get(0)).isInstanceOf(AttributeSelectorTree.class);

    tree = checkParsed("div");
    assertThat(tree.selectors()).hasSize(1);
    assertThat(tree.selectors().get(0)).isInstanceOf(TypeSelectorTree.class);

    tree = checkParsed(".class");
    assertThat(tree.selectors()).hasSize(1);
    assertThat(tree.selectors().get(0)).isInstanceOf(ClassSelectorTree.class);

    tree = checkParsed("#id");
    assertThat(tree.selectors()).hasSize(1);
    assertThat(tree.selectors().get(0)).isInstanceOf(IdSelectorTree.class);

    tree = checkParsed(":active");
    assertThat(tree.selectors()).hasSize(1);
    assertThat(tree.selectors().get(0)).isInstanceOf(PseudoSelectorTree.class);

    tree = checkParsed("::after");
    assertThat(tree.selectors()).hasSize(1);
    assertThat(tree.selectors().get(0)).isInstanceOf(PseudoSelectorTree.class);

    tree = checkParsed("[attribute~=abc][abc=\"def\"]");
    assertThat(tree.selectors()).hasSize(2);
    assertThat(tree.selectors().get(0)).isInstanceOf(AttributeSelectorTree.class);
    assertThat(tree.selectors().get(1)).isInstanceOf(AttributeSelectorTree.class);

    tree = checkParsed("div#id");
    assertThat(tree.selectors()).hasSize(2);
    assertThat(tree.selectors().get(0)).isInstanceOf(TypeSelectorTree.class);
    assertThat(tree.selectors().get(1)).isInstanceOf(IdSelectorTree.class);

    tree = checkParsed("div#id.class3");
    assertThat(tree.selectors()).hasSize(3);
    assertThat(tree.selectors().get(0)).isInstanceOf(TypeSelectorTree.class);
    assertThat(tree.selectors().get(1)).isInstanceOf(IdSelectorTree.class);
    assertThat(tree.selectors().get(2)).isInstanceOf(ClassSelectorTree.class);

    tree = checkParsed(".class1.class2.class3");
    assertThat(tree.selectors()).hasSize(3);
    assertThat(tree.selectors().get(0)).isInstanceOf(ClassSelectorTree.class);
    assertThat(tree.selectors().get(1)).isInstanceOf(ClassSelectorTree.class);
    assertThat(tree.selectors().get(2)).isInstanceOf(ClassSelectorTree.class);

    checkParsed("div.class");
    checkParsed(".class1.class2");
    checkParsed("div:active");
    checkParsed("div::after");
    checkParsed("div:lang(en)");
    checkParsed("div:nth-last-child(2)");
    checkParsed("#id:active");
    checkParsed("#id::after");
    checkParsed("#id:lang(en)");
    checkParsed("#id:nth-last-child(2)");
    checkParsed(".class:active");
    checkParsed(".class::after");
    checkParsed(".class:lang(en)");
    checkParsed(".class:nth-last-child(2)");
  }

  @Test
  public void notCompoundSelector() {
    CompoundSelectorTree tree;

    tree = checkParsed("[attribute~=abc] [abc=\"def\"");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed("div #id");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed("div .class");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed(".class1 .class2");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed(".class1 .class2.class3");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed("div :active");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed("div ::after");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed("div :lang(en)");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed("div :nth-last-child(2)");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed("#id :active");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed("#id ::after");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed("#id :lang(en)");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed("#id :nth-last-child(2)");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed(".class :active");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed(".class ::after");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed(".class :lang(en)");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed(".class :nth-last-child(2)");
    assertThat(tree.selectors()).hasSize(1);

    tree = checkParsed(".class.class2 .class3");
    assertThat(tree.selectors()).hasSize(2);
  }

  private CompoundSelectorTree checkParsed(String toParse) {
    CompoundSelectorTree tree = (CompoundSelectorTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.selectors()).isNotNull();
    return tree;
  }

}
