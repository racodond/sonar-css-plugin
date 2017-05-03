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
package org.sonar.css.parser.less;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.AttributeSelectorTree;
import org.sonar.plugins.css.api.tree.css.ClassSelectorTree;
import org.sonar.plugins.css.api.tree.css.SelectorCombinatorTree;
import org.sonar.plugins.css.api.tree.css.SelectorTree;
import org.sonar.plugins.css.api.tree.less.LessParentSelectorTree;

import static org.fest.assertions.Assertions.assertThat;

public class SelectorTreeTest extends LessTreeTest {

  public SelectorTreeTest() {
    super(LexicalGrammar.SELECTOR);
  }

  @Test
  public void selector() {
    SelectorTree tree;

    checkParsed("[attribute~=abc]");
    checkParsed("div");
    checkParsed(".class");
    checkParsed("#id");
    checkParsed(":active");
    checkParsed("::after");
    checkParsed("[attribute~=abc][abc=\"def\"]");
    checkParsed("div#id");
    checkParsed("div#id.class3");
    checkParsed(".class1.class2.class3");
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
    checkParsed("to");
    checkParsed("from");
    checkParsed("10%");

    tree = checkParsed("[attribute~=abc] [attribute~=abc]");
    assertThat(tree.compoundSelectors()).hasSize(2);
    assertThat(tree.compoundSelectors().get(0).selectors().get(0)).isInstanceOf(AttributeSelectorTree.class);
    assertThat(tree.compoundSelectors().get(1).selectors().get(0)).isInstanceOf(AttributeSelectorTree.class);
    assertThat(tree.compoundSelectorsCombinationList().separators().get(0).type()).isEqualTo(SelectorCombinatorTree.COMBINATOR.DESCENDANT_WS);

    tree = checkParsed("[attribute~=abc] >> [attribute~=abc]");
    assertThat(tree.compoundSelectors()).hasSize(2);
    assertThat(tree.compoundSelectors().get(0).selectors().get(0)).isInstanceOf(AttributeSelectorTree.class);
    assertThat(tree.compoundSelectors().get(1).selectors().get(0)).isInstanceOf(AttributeSelectorTree.class);
    assertThat(tree.compoundSelectorsCombinationList().separators().get(0).type()).isEqualTo(SelectorCombinatorTree.COMBINATOR.DESCENDANT);

    tree = checkParsed("[attribute~=abc] > [attribute~=abc]");
    assertThat(tree.compoundSelectors()).hasSize(2);
    assertThat(tree.compoundSelectors().get(0).selectors().get(0)).isInstanceOf(AttributeSelectorTree.class);
    assertThat(tree.compoundSelectors().get(1).selectors().get(0)).isInstanceOf(AttributeSelectorTree.class);
    assertThat(tree.compoundSelectorsCombinationList().separators().get(0).type()).isEqualTo(SelectorCombinatorTree.COMBINATOR.CHILD);

    tree = checkParsed("[attribute~=abc] + [attribute~=abc]");
    assertThat(tree.compoundSelectors()).hasSize(2);
    assertThat(tree.compoundSelectors().get(0).selectors().get(0)).isInstanceOf(AttributeSelectorTree.class);
    assertThat(tree.compoundSelectors().get(1).selectors().get(0)).isInstanceOf(AttributeSelectorTree.class);
    assertThat(tree.compoundSelectorsCombinationList().separators().get(0).type()).isEqualTo(SelectorCombinatorTree.COMBINATOR.NEXT_SIBLING);

    tree = checkParsed("[attribute~=abc] ~ [attribute~=abc]");
    assertThat(tree.compoundSelectors()).hasSize(2);
    assertThat(tree.compoundSelectors().get(0).selectors().get(0)).isInstanceOf(AttributeSelectorTree.class);
    assertThat(tree.compoundSelectors().get(1).selectors().get(0)).isInstanceOf(AttributeSelectorTree.class);
    assertThat(tree.compoundSelectorsCombinationList().separators().get(0).type()).isEqualTo(SelectorCombinatorTree.COMBINATOR.FOLLOWING_SIBLING);

    tree = checkParsed("[attribute~=abc] [attribute~=abc] > .class");
    assertThat(tree.compoundSelectors()).hasSize(3);
    assertThat(tree.compoundSelectors().get(0).selectors().get(0)).isInstanceOf(AttributeSelectorTree.class);
    assertThat(tree.compoundSelectors().get(1).selectors().get(0)).isInstanceOf(AttributeSelectorTree.class);
    assertThat(tree.compoundSelectors().get(2).selectors().get(0)).isInstanceOf(ClassSelectorTree.class);
    assertThat(tree.compoundSelectorsCombinationList().separators().get(0).type()).isEqualTo(SelectorCombinatorTree.COMBINATOR.DESCENDANT_WS);
    assertThat(tree.compoundSelectorsCombinationList().separators().get(1).type()).isEqualTo(SelectorCombinatorTree.COMBINATOR.CHILD);

    tree = checkParsed("[attribute~=abc] >> [attribute~=abc] + .class");
    assertThat(tree.compoundSelectors()).hasSize(3);

    tree = checkParsed("[attribute~=abc] > [attribute~=abc] + .class");
    assertThat(tree.compoundSelectors()).hasSize(3);

    tree = checkParsed("[attribute~=abc] + [attribute~=abc] + .class");
    assertThat(tree.compoundSelectors()).hasSize(3);

    tree = checkParsed("[attribute~=abc] ~ [attribute~=abc] + .class");
    assertThat(tree.compoundSelectors()).hasSize(3);

    tree = checkParsed("div[attribute~=abc] ~ #id.class[attribute~=abc] + .class.class.class");
    assertThat(tree.compoundSelectors()).hasSize(3);

    tree = checkParsed(".selected * a");
    assertThat(tree.compoundSelectors()).hasSize(3);

    tree = checkParsed("abc /deep/ def >>> jkl");
    assertThat(tree.compoundSelectors()).hasSize(3);
    assertThat(tree.compoundSelectorsCombinationList().separators().get(0).type()).isEqualTo(SelectorCombinatorTree.COMBINATOR.DEEP);
    assertThat(tree.compoundSelectorsCombinationList().separators().get(1).type()).isEqualTo(SelectorCombinatorTree.COMBINATOR.DEEP_ALIAS);

    tree = checkParsed(".selected :extend(div)");
    assertThat(tree.lessExtend()).isNotNull();

    tree = checkParsed(".selected:extend(div)");
    assertThat(tree.lessExtend()).isNotNull();

    tree = checkParsed("&:extend(div)");
    assertThat(tree.compoundSelectors()).hasSize(1);
    assertThat(tree.compoundSelectors().get(0).selectors().get(0)).isInstanceOf(LessParentSelectorTree.class);
    assertThat(tree.lessExtend()).isNotNull();
    assertThat(tree.lessMixinParameters()).isNull();

    checkParsed("&");
    checkParsed("&&");
    checkParsed("& &");
    checkParsed("& + &");
    checkParsed("& > &");
    checkParsed("&ish");
    checkParsed("&-ish");
    checkParsed("&[class=abc]");

    tree = checkParsed("&:extend(div)()");
    assertThat(tree.lessMixinParameters()).isNotNull();

    tree = checkParsed("&:extend(div) ()");
    assertThat(tree.lessMixinParameters()).isNotNull();

    tree = checkParsed("&:extend(div) ( )");
    assertThat(tree.lessMixinParameters()).isNotNull();

    tree = checkParsed("#id(@var: blue; @blalba ; @myvar: blue)");
    assertThat(tree.lessMixinParameters()).isNotNull();
    assertThat(tree.lessMixinParameters().parameters()).hasSize(3);

    tree = checkParsed(".mixin (@a) when (@media = mobile)");
    assertThat(tree.lessMixinParameters()).isNotNull();
    assertThat(tree.lessMixinParameters().parameters()).hasSize(1);
    assertThat(tree.lessMixinGuard()).isNotNull();

    tree = checkParsed("> .mixin (@a)");
    assertThat(tree.lessMixinParameters()).isNotNull();
    assertThat(tree.lessMixinParameters().parameters()).hasSize(1);
    assertThat(tree.lessParentCombinator()).isNotNull();

    tree = checkParsed("button when (@a > 1)");
    assertThat(tree.lessMixinGuard()).isNotNull();

    tree = checkParsed("button when(@a > 1)");
    assertThat(tree.lessMixinGuard()).isNotNull();

    tree = checkParsed("button whena(@a > 1)");
    assertThat(tree.lessMixinGuard()).isNull();
  }

  @Test
  public void notSelector() {
    checkNotParsed(" div > div");
  }

  private SelectorTree checkParsed(String toParse) {
    SelectorTree tree = (SelectorTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.compoundSelectors()).isNotNull();
    return tree;
  }

}
