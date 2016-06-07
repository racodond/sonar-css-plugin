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
import org.sonar.sslr.parser.LexerlessGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class SelectorTest extends TestBase {

  private final LexerlessGrammar b = CssGrammar.createGrammar();

  @Test
  public void should_match_id_selector() {
    assertThat(b.rule(CssGrammar.ID_SELECTOR))
      .matches("#id")
      .matches("#test");
  }

  @Test
  public void should_not_match_id_selector() {
    assertThat(b.rule(CssGrammar.ID_SELECTOR))
      .notMatches("#id ")
      .notMatches("test");
  }

  @Test
  public void should_match_class_selector() {
    assertThat(b.rule(CssGrammar.CLASS_SELECTOR))
      .matches(".class")
      .matches(".test")
      .matches(".class.test");
  }

  @Test
  public void should_not_match_class_selector() {
    assertThat(b.rule(CssGrammar.CLASS_SELECTOR))
      .notMatches(".class ")
      .notMatches("test");
  }

  @Test
  public void should_match_attribute_selector() {
    assertThat(b.rule(CssGrammar.ATTRIBUTE_SELECTOR))
      .matches("[target]")
      .matches("[target=_blank]")
      .matches("[title~=flower]")
      .matches("[lang|=en]")
      .matches("[href^=\"https\"]")
      .matches("[href$=\".pdf\"]")
      .matches("[href*=\"w3schools\"]")
      .matches("[|target]")
      .matches("[|target=_blank]")
      .matches("[|title~=flower]")
      .matches("[|lang|=en]")
      .matches("[|href^=\"https\"]")
      .matches("[|href$=\".pdf\"]")
      .matches("[|href*=\"w3schools\"]")
      .matches("[*|target]")
      .matches("[*|target=_blank]")
      .matches("[*|title~=flower]")
      .matches("[*|lang|=en]")
      .matches("[*|href^=\"https\"]")
      .matches("[*|href$=\".pdf\"]")
      .matches("[*|href*=\"w3schools\"]")
      .matches("[ns|target]")
      .matches("[ns|target=_blank]")
      .matches("[ns|title~=flower]")
      .matches("[ns|lang|=en]")
      .matches("[ns|href^=\"https\"]")
      .matches("[ns|href$=\".pdf\"]")
      .matches("[ns|href*=\"w3schools\"]");
  }

  @Test
  public void should_not_match_attribute_selector() {
    assertThat(b.rule(CssGrammar.ATTRIBUTE_SELECTOR))
      .notMatches("[target] ")
      .notMatches("[* |target]")
      .notMatches("[*| target]");
  }

  @Test
  public void should_match_universal_selector() {
    assertThat(b.rule(CssGrammar.UNIVERSAL_SELECTOR))
      .matches("*")
      .matches("ns|*")
      .matches("*|*")
      .matches("|*");
  }

  @Test
  public void should_not_match_universal_selector() {
    assertThat(b.rule(CssGrammar.UNIVERSAL_SELECTOR))
      .notMatches("ns| *")
      .notMatches("ns |*")
      .notMatches("[*| *")
      .notMatches("[* |*");
  }


  @Test
  public void should_match_type_selector() {
    assertThat(b.rule(CssGrammar.TYPE_SELECTOR))
      .matches("h1")
      .matches("ns|h1")
      .matches("*|h1")
      .matches("|h1");
  }

  @Test
  public void should_not_match_type_selector() {
    assertThat(b.rule(CssGrammar.TYPE_SELECTOR))
      .notMatches("ns| h1")
      .notMatches("ns |h1")
      .notMatches("[*| h1")
      .notMatches("[* |h1");
  }

  @Test
  public void should_match_pseudo() {
    assertThat(b.rule(CssGrammar.PSEUDO))
      .matches(":active")
      .matches("::after")
      .matches(":lang(en)")
      .matches(":nth-last-child(2)");
  }

  @Test
  public void should_not_match_pseudo() {
    assertThat(b.rule(CssGrammar.PSEUDO))
      .notMatches("active")
      .notMatches(":active ")
      .notMatches("::after ");
  }

  @Test
  public void should_match_selector() {
    assertThat(b.rule(CssGrammar.SELECTOR))
      .matches("h6")
      .matches("h1, h2")
      .matches("h4 + h5")
      .matches("h3, h4 + h5")
      .notMatches("h3{")
      .matches(code("p[example=\"public class foo\\",
        "{\\",
        "    private int x;\\",
        "\\",
        "    foo(int x) {\\",
        "        this.x = x;\\",
        "    }\\",
        "\\",
        "}\"]"))
      .matches("h1,\nh2")
      .matches("h2\n,h3")
      .matches("h2 /* comment*/")
      .matches("h2\n /* comment*/")
      .matches("* ")
      .matches("div:active")
      .matches("div::after")
      .matches("div:lang(en)")
      .matches("div:nth-last-child(2)")
      .matches("#id:active")
      .matches("#id::after")
      .matches("#id:lang(en)")
      .matches("#id:nth-last-child(2)")
      .matches(".class:active")
      .matches(".class::after")
      .matches(".class:lang(en)")
      .matches(".class:nth-last-child(2)");
  }

}
