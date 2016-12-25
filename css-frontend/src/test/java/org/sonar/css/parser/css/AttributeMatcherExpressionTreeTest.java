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
package org.sonar.css.parser.css;

import org.junit.Test;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.AttributeMatcherExpressionTree;
import org.sonar.plugins.css.api.tree.css.AttributeMatcherTree;
import org.sonar.plugins.css.api.tree.css.IdentifierTree;
import org.sonar.plugins.css.api.tree.css.StringTree;

import static org.fest.assertions.Assertions.assertThat;

public class AttributeMatcherExpressionTreeTest extends CssTreeTest {

  public AttributeMatcherExpressionTreeTest() {
    super(LexicalGrammar.ATTRIBUTE_MATCHER_EXPRESSION);
  }

  @Test
  public void attributeMatcherExpression() {
    AttributeMatcherExpressionTree tree;

    tree = checkParsed("~=abc", AttributeMatcherTree.MATCHER.INCLUDE, "~=");
    assertThat(tree.toMatch()).isInstanceOf(IdentifierTree.class);
    assertThat(((IdentifierTree) tree.toMatch()).text()).isEqualTo("abc");

    tree = checkParsed("|=abc", AttributeMatcherTree.MATCHER.DASH, "|=");
    assertThat(tree.toMatch()).isInstanceOf(IdentifierTree.class);
    assertThat(((IdentifierTree) tree.toMatch()).text()).isEqualTo("abc");

    tree = checkParsed("=abc", AttributeMatcherTree.MATCHER.EQUALS, "=");
    assertThat(tree.toMatch()).isInstanceOf(IdentifierTree.class);
    assertThat(((IdentifierTree) tree.toMatch()).text()).isEqualTo("abc");

    tree = checkParsed("*=abc", AttributeMatcherTree.MATCHER.SUBSTRING, "*=");
    assertThat(tree.toMatch()).isInstanceOf(IdentifierTree.class);
    assertThat(((IdentifierTree) tree.toMatch()).text()).isEqualTo("abc");

    tree = checkParsed("^=abc", AttributeMatcherTree.MATCHER.PREFIX, "^=");
    assertThat(tree.toMatch()).isInstanceOf(IdentifierTree.class);
    assertThat(((IdentifierTree) tree.toMatch()).text()).isEqualTo("abc");

    tree = checkParsed("$=abc", AttributeMatcherTree.MATCHER.SUFFIX, "$=");
    assertThat(tree.toMatch()).isInstanceOf(IdentifierTree.class);
    assertThat(((IdentifierTree) tree.toMatch()).text()).isEqualTo("abc");

    tree = checkParsed(" ~= abc", AttributeMatcherTree.MATCHER.INCLUDE, "~=");
    assertThat(tree.toMatch()).isInstanceOf(IdentifierTree.class);
    assertThat(((IdentifierTree) tree.toMatch()).text()).isEqualTo("abc");

    tree = checkParsed(" |= abc", AttributeMatcherTree.MATCHER.DASH, "|=");
    assertThat(tree.toMatch()).isInstanceOf(IdentifierTree.class);
    assertThat(((IdentifierTree) tree.toMatch()).text()).isEqualTo("abc");

    tree = checkParsed(" = abc", AttributeMatcherTree.MATCHER.EQUALS, "=");
    assertThat(tree.toMatch()).isInstanceOf(IdentifierTree.class);
    assertThat(((IdentifierTree) tree.toMatch()).text()).isEqualTo("abc");

    tree = checkParsed(" *= abc", AttributeMatcherTree.MATCHER.SUBSTRING, "*=");
    assertThat(tree.toMatch()).isInstanceOf(IdentifierTree.class);
    assertThat(((IdentifierTree) tree.toMatch()).text()).isEqualTo("abc");

    tree = checkParsed(" ^= abc", AttributeMatcherTree.MATCHER.PREFIX, "^=");
    assertThat(tree.toMatch()).isInstanceOf(IdentifierTree.class);
    assertThat(((IdentifierTree) tree.toMatch()).text()).isEqualTo("abc");

    tree = checkParsed(" $= abc", AttributeMatcherTree.MATCHER.SUFFIX, "$=");
    assertThat(tree.toMatch()).isInstanceOf(IdentifierTree.class);
    assertThat(((IdentifierTree) tree.toMatch()).text()).isEqualTo("abc");

    tree = checkParsed("~=\"abc\"", AttributeMatcherTree.MATCHER.INCLUDE, "~=");
    assertThat(tree.toMatch()).isInstanceOf(StringTree.class);
    assertThat(((StringTree) tree.toMatch()).actualText()).isEqualTo("abc");

    tree = checkParsed("|=\"abc\"", AttributeMatcherTree.MATCHER.DASH, "|=");
    assertThat(tree.toMatch()).isInstanceOf(StringTree.class);
    assertThat(((StringTree) tree.toMatch()).actualText()).isEqualTo("abc");

    tree = checkParsed("=\"abc\"", AttributeMatcherTree.MATCHER.EQUALS, "=");
    assertThat(tree.toMatch()).isInstanceOf(StringTree.class);
    assertThat(((StringTree) tree.toMatch()).actualText()).isEqualTo("abc");

    tree = checkParsed("*=\"abc\"", AttributeMatcherTree.MATCHER.SUBSTRING, "*=");
    assertThat(tree.toMatch()).isInstanceOf(StringTree.class);
    assertThat(((StringTree) tree.toMatch()).actualText()).isEqualTo("abc");

    tree = checkParsed("^=\"abc\"", AttributeMatcherTree.MATCHER.PREFIX, "^=");
    assertThat(tree.toMatch()).isInstanceOf(StringTree.class);
    assertThat(((StringTree) tree.toMatch()).actualText()).isEqualTo("abc");

    tree = checkParsed("$=\"abc\"", AttributeMatcherTree.MATCHER.SUFFIX, "$=");
    assertThat(tree.toMatch()).isInstanceOf(StringTree.class);
    assertThat(((StringTree) tree.toMatch()).actualText()).isEqualTo("abc");

    tree = checkParsed(" ~= \"abc\"", AttributeMatcherTree.MATCHER.INCLUDE, "~=");
    assertThat(tree.toMatch()).isInstanceOf(StringTree.class);
    assertThat(((StringTree) tree.toMatch()).actualText()).isEqualTo("abc");

    tree = checkParsed(" |= \"abc\"", AttributeMatcherTree.MATCHER.DASH, "|=");
    assertThat(tree.toMatch()).isInstanceOf(StringTree.class);
    assertThat(((StringTree) tree.toMatch()).actualText()).isEqualTo("abc");

    tree = checkParsed(" = \"abc\"", AttributeMatcherTree.MATCHER.EQUALS, "=");
    assertThat(tree.toMatch()).isInstanceOf(StringTree.class);
    assertThat(((StringTree) tree.toMatch()).actualText()).isEqualTo("abc");

    tree = checkParsed(" *= \"abc\"", AttributeMatcherTree.MATCHER.SUBSTRING, "*=");
    assertThat(tree.toMatch()).isInstanceOf(StringTree.class);
    assertThat(((StringTree) tree.toMatch()).actualText()).isEqualTo("abc");

    tree = checkParsed(" ^= \"abc\"", AttributeMatcherTree.MATCHER.PREFIX, "^=");
    assertThat(tree.toMatch()).isInstanceOf(StringTree.class);
    assertThat(((StringTree) tree.toMatch()).actualText()).isEqualTo("abc");

    tree = checkParsed(" $= \"abc\"", AttributeMatcherTree.MATCHER.SUFFIX, "$=");
    assertThat(tree.toMatch()).isInstanceOf(StringTree.class);
    assertThat(((StringTree) tree.toMatch()).actualText()).isEqualTo("abc");
    assertThat(tree.caseInsensitiveFlag()).isNull();

    tree = checkParsed(" $= \"abc\" i", AttributeMatcherTree.MATCHER.SUFFIX, "$=");
    assertThat(tree.caseInsensitiveFlag()).isNotNull();

    tree = checkParsed(" $= abc i", AttributeMatcherTree.MATCHER.SUFFIX, "$=");
    assertThat(tree.caseInsensitiveFlag()).isNotNull();
  }

  @Test
  public void notAttributeMatcherExpression() {
    checkNotParsed("=");
    checkNotParsed("=@abc");
  }

  private AttributeMatcherExpressionTree checkParsed(String toParse, AttributeMatcherTree.MATCHER expectedType, String expectedValue) {
    AttributeMatcherExpressionTree tree = (AttributeMatcherExpressionTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.attributeMatcher()).isNotNull();
    assertThat(tree.attributeMatcher().type()).isEqualTo(expectedType);
    assertThat(tree.attributeMatcher().value()).isEqualTo(expectedValue);
    assertThat(tree.toMatch()).isNotNull();
    return tree;
  }

}
