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
import org.sonar.css.model.Vendor;
import org.sonar.css.model.function.UnknownFunction;
import org.sonar.css.model.function.standard.MicrosoftFilterBlur;
import org.sonar.css.model.function.standard.Min;
import org.sonar.css.parser.LexicalGrammar;
import org.sonar.plugins.css.api.tree.css.FunctionTree;
import org.sonar.plugins.css.api.tree.less.LessVariableTree;
import org.sonar.plugins.css.api.tree.scss.ScssVariableTree;

import static org.fest.assertions.Assertions.assertThat;

public class FunctionTreeTest extends ScssTreeTest {

  public FunctionTreeTest() {
    super(LexicalGrammar.FUNCTION);
  }

  @Test
  public void function() {
    FunctionTree tree;

    tree = checkParsed("abc()");
    assertThat(tree.standardFunction()).isInstanceOf(UnknownFunction.class);
    assertThat(tree.parameterElements()).isNull();

    tree = checkParsed(" abc()");
    assertThat(tree.standardFunction()).isInstanceOf(UnknownFunction.class);
    assertThat(tree.parameterElements()).isNull();

    tree = checkParsed("abc(param)");
    assertThat(tree.standardFunction()).isInstanceOf(UnknownFunction.class);
    assertThat(tree.parameterElements()).isNotNull();
    assertThat(tree.parameterElements()).hasSize(1);

    tree = checkParsed("abc(param, 4, \"abc\")");
    assertThat(tree.standardFunction()).isInstanceOf(UnknownFunction.class);
    assertThat(tree.parameterElements()).isNotNull();
    assertThat(tree.parameterElements()).hasSize(5);

    tree = checkParsed("min(4, 5)");
    assertThat(tree.standardFunction()).isInstanceOf(Min.class);
    assertThat(tree.parameterElements()).isNotNull();
    assertThat(tree.parameterElements()).hasSize(3);

    tree = checkParsed("-webkit-gradient(linear, left top, left bottom, color-stop(0%,#1e5799), color-stop(100%,#7db9e8))");
    assertThat(tree.isVendorPrefixed()).isTrue();
    assertThat(tree.vendor()).isEqualTo(Vendor.WEBKIT);

    tree = checkParsed("progid:DXImageTransform.Microsoft.Blur()");
    assertThat(tree.standardFunction()).isInstanceOf(MicrosoftFilterBlur.class);

    tree = checkParsed("progid:dximagetransform.microsoft.blur()");
    assertThat(tree.standardFunction()).isInstanceOf(MicrosoftFilterBlur.class);

    tree = checkParsed("min($myvar, 5)");
    assertThat(tree.standardFunction()).isInstanceOf(Min.class);
    assertThat(tree.parameterElements()).isNotNull();
    assertThat(tree.parameterElements()).hasSize(3);
    assertThat(tree.parameterElements().get(0)).isInstanceOf(ScssVariableTree.class);
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
    assertThat(tree.openParenthesis()).isNotNull();
    assertThat(tree.closeParenthesis()).isNotNull();
    return tree;
  }

}
