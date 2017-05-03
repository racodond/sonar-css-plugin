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
import org.sonar.css.model.Vendor;
import org.sonar.css.model.function.UnknownFunction;
import org.sonar.css.model.function.standard.MicrosoftFilterBlur;
import org.sonar.css.model.function.standard.Min;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.FunctionTree;

import static org.fest.assertions.Assertions.assertThat;

public class FunctionTreeTest extends CssTreeTest {

  public FunctionTreeTest() {
    super(LexicalGrammar.FUNCTION);
  }

  @Test
  public void function() {
    FunctionTree tree;

    tree = checkParsed("abc()");
    assertThat(tree.standardFunction()).isInstanceOf(UnknownFunction.class);
    assertThat(tree.parameters().parameters()).isNull();

    tree = checkParsed(" abc()");
    assertThat(tree.standardFunction()).isInstanceOf(UnknownFunction.class);
    assertThat(tree.parameters().parameters()).isNull();

    tree = checkParsed("abc(param)");
    assertThat(tree.standardFunction()).isInstanceOf(UnknownFunction.class);
    assertThat(tree.parameters()).isNotNull();
    assertThat(tree.parameters().parameters()).hasSize(1);

    tree = checkParsed("abc(param, 4, \"abc\")");
    assertThat(tree.standardFunction()).isInstanceOf(UnknownFunction.class);
    assertThat(tree.parameters().parameters()).isNotNull();
    assertThat(tree.parameters().parameters()).hasSize(3);

    tree = checkParsed("min(4, 5)");
    assertThat(tree.standardFunction()).isInstanceOf(Min.class);
    assertThat(tree.parameters().parameters()).isNotNull();
    assertThat(tree.parameters().parameters()).hasSize(2);

    tree = checkParsed("-webkit-gradient(linear, left top, left bottom, color-stop(0%,#1e5799), color-stop(100%,#7db9e8))");
    assertThat(tree.isVendorPrefixed()).isTrue();
    assertThat(tree.vendor()).isEqualTo(Vendor.WEBKIT);

    tree = checkParsed("progid:DXImageTransform.Microsoft.Blur()");
    assertThat(tree.standardFunction()).isInstanceOf(MicrosoftFilterBlur.class);

    tree = checkParsed("progid:dximagetransform.microsoft.blur()");
    assertThat(tree.standardFunction()).isInstanceOf(MicrosoftFilterBlur.class);
  }

  @Test
  public void notFunction() {
    checkNotParsed("()");
    checkNotParsed("abc");
  }

  private FunctionTree checkParsed(String toParse) {
    FunctionTree tree = (FunctionTree) parser().parse(toParse);
    assertThat(tree).isNotNull();
    assertThat(tree.function()).isNotNull();
    assertThat(tree.standardFunction()).isNotNull();
    assertThat(tree.parameters().openParenthesis()).isNotNull();
    assertThat(tree.parameters().closeParenthesis()).isNotNull();
    return tree;
  }

}
