/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende
 * kende.tamas@gmail.com
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.css.checks;

import org.junit.Test;
import org.sonar.css.CssAstScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

import java.io.File;

public class ValidatePropertyValueCheckTest {

  private ValidatePropertyValueCheck check = new ValidatePropertyValueCheck();

  @Test
  public void should_find_some_invalid_values_and_raise_issues() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/validatePropertyValue.css"), check);
    CheckMessagesVerifier
      .verify(file.getCheckMessages())
      .next()
      .atLine(6)
      .withMessage("Update the invalid value of property \"float\". Expected format: left | right | none")
      .next()
      .atLine(21)
      .withMessage("Update the invalid value of property \"word-spacing\". Expected format: normal | <length>")
      .next()
      .atLine(22)
      .withMessage("Update the invalid value of property \"word-spacing\". Expected format: normal | <length>")
      .next()
      .atLine(36)
      .withMessage("Update the invalid value of property \"z-index\". Expected format: auto | <integer>")
      .next()
      .atLine(37)
      .withMessage("Update the invalid value of property \"z-index\". Expected format: auto | <integer>")
      .next()
      .atLine(38)
      .withMessage("Update the invalid value of property \"z-index\". Expected format: auto | <integer>")
      .next()
      .atLine(52)
      .withMessage(
        "Update the invalid value of property \"line-height\". Expected format: normal | <length> | <percentage> | <number>")
      .next()
      .atLine(69)
      .withMessage("Update the invalid value of property \"width\". Expected format: auto | <length> | <percentage>")
      .next()
      .atLine(78)
      .withMessage("Update the invalid value of property \"list-style-image\". Expected format: none | <uri>")
      .next()
      .atLine(79)
      .withMessage("Update the invalid value of property \"list-style-image\". Expected format: none | <uri>")
      .next()
      .atLine(118)
      .withMessage(
        "Update the invalid value of property \"border-top-width\". Expected format: thin | medium | thick | <length> (>=0)")
      .next()
      .atLine(119)
      .withMessage(
        "Update the invalid value of property \"border-top-width\". Expected format: thin | medium | thick | <length> (>=0)")
      .next()
      .atLine(120)
      .withMessage(
        "Update the invalid value of property \"border-top-width\". Expected format: thin | medium | thick | <length> (>=0)")
      .next()
      .atLine(121)
      .withMessage(
        "Update the invalid value of property \"border-top-width\". Expected format: thin | medium | thick | <length> (>=0)")
      .next()
      .atLine(122)
      .withMessage(
        "Update the invalid value of property \"border-top-width\". Expected format: thin | medium | thick | <length> (>=0)")
      .next()
      .atLine(139)
      .withMessage("Update the invalid value of property \"margin-top\". Expected format: auto | <length> | <percentage>")
      .next()
      .atLine(140)
      .withMessage("Update the invalid value of property \"margin-top\". Expected format: auto | <length> | <percentage>")
      .next()
      .atLine(143)
      .withMessage("Update the invalid value of property \"margin-top\". Expected format: auto | <length> | <percentage>")
      .next()
      .atLine(154)
      .withMessage(
        "Update the invalid value of property \"border-right-style\". Expected format: none | hidden | dotted | dashed | solid | double | groove | ridge | inset | outset")
      .next()
      .atLine(155)
      .withMessage(
        "Update the invalid value of property \"border-left-style\". Expected format: none | hidden | dotted | dashed | solid | double | groove | ridge | inset | outset").next()
      .atLine(156)
      .withMessage(
        "Update the invalid value of property \"outline-style\". Expected format: none | dotted | dashed | solid | double | groove | ridge | inset | outset").next()
      .atLine(157)
      .withMessage(
        "Update the invalid value of property \"outline-style\". Expected format: none | dotted | dashed | solid | double | groove | ridge | inset | outset").next()
      .atLine(168).withMessage("Update the invalid value of property \"padding-bottom\". Expected format: <length> (>=0) | <percentage> (>=0)").next()
      .atLine(169).withMessage("Update the invalid value of property \"padding-top\". Expected format: <length> (>=0) | <percentage> (>=0)").next()
      .atLine(179).withMessage("Update the invalid value of property \"pause-after\". Expected format: <time> | <percentage> (>=0)").next()
      .atLine(180).withMessage("Update the invalid value of property \"pause-after\". Expected format: <time> | <percentage> (>=0)").next()
      .atLine(181).withMessage("Update the invalid value of property \"pause-after\". Expected format: <time> | <percentage> (>=0)").next()
      .atLine(190).withMessage("Update the invalid value of property \"pitch\". Expected format: <frequency> | x-low | low | medium | high | x-high").next()
      .atLine(191).withMessage("Update the invalid value of property \"pitch\". Expected format: <frequency> | x-low | low | medium | high | x-high").next()
      .atLine(209).withMessage("Update the invalid value of property \"elevation\". Expected format: <angle> | below | level | above | higher | lower").next()
      .atLine(210).withMessage("Update the invalid value of property \"elevation\". Expected format: <angle> | below | level | above | higher | lower").noMore();
  }

  @Test
  public void font_size() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/font-size.css"), check);
    CheckMessagesVerifier
      .verify(file.getCheckMessages())
      .next()
      .atLine(17)
      .withMessage(
        "Update the invalid value of property \"font-size\". Expected format: xx-small | x-small | small | medium | large | x-large | xx-large | larger | smaller | <length> (>=0) | <percentage> (>=0)")
      .next()
      .atLine(18)
      .withMessage(
        "Update the invalid value of property \"font-size\". Expected format: xx-small | x-small | small | medium | large | x-large | xx-large | larger | smaller | <length> (>=0) | <percentage> (>=0)")
      .next()
      .atLine(19)
      .withMessage(
        "Update the invalid value of property \"font-size\". Expected format: xx-small | x-small | small | medium | large | x-large | xx-large | larger | smaller | <length> (>=0) | <percentage> (>=0)")
      .next()
      .atLine(20)
      .withMessage(
        "Update the invalid value of property \"font-size\". Expected format: xx-small | x-small | small | medium | large | x-large | xx-large | larger | smaller | <length> (>=0) | <percentage> (>=0)")
      .noMore();
  }

  @Test
  public void all() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/all.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage("Update the invalid value of property \"all\". Expected format: inherit | initial | unset").noMore();
  }

  @Test
  public void color() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/color.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(9).withMessage(
        "Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(10).withMessage(
        "Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(11).withMessage(
        "Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(14).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(16).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(17).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(20).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(22).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(23).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(24).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(25).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(28).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(29).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(30).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(31).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(34).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(35).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(36).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(37).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(38).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(39).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").next()
      .atLine(40).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").noMore();
  }

  @Test
  public void font_size_adjust() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/font-size-adjust.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage("Update the invalid value of property \"font-size-adjust\". Expected format: none | <number>").next()
      .atLine(7).withMessage("Update the invalid value of property \"font-size-adjust\". Expected format: none | <number>").noMore();
  }

  @Test
  public void font_stretch() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/font-stretch.css"), check);
    CheckMessagesVerifier
      .verify(file.getCheckMessages())
      .next()
      .atLine(12)
      .withMessage(
        "Update the invalid value of property \"font-stretch\". Expected format: normal | ultra-condensed | extra-condensed | condensed | semi-condensed | semi-expanded | expanded | extra-expanded | ultra-expanded")
      .next()
      .atLine(13)
      .withMessage(
        "Update the invalid value of property \"font-stretch\". Expected format: normal | ultra-condensed | extra-condensed | condensed | semi-condensed | semi-expanded | expanded | extra-expanded | ultra-expanded")
      .next()
      .atLine(14)
      .withMessage(
        "Update the invalid value of property \"font-stretch\". Expected format: normal | ultra-condensed | extra-condensed | condensed | semi-condensed | semi-expanded | expanded | extra-expanded | ultra-expanded")
      .noMore();
  }

  @Test
  public void font_style() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/font-style.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage("Update the invalid value of property \"font-style\". Expected format: normal | italic | oblique").next()
      .atLine(7).withMessage("Update the invalid value of property \"font-style\". Expected format: normal | italic | oblique").noMore();
  }

  @Test
  public void hyphens() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/hyphens.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage("Update the invalid value of property \"hyphens\". Expected format: none | manual | auto").next()
      .atLine(7).withMessage("Update the invalid value of property \"hyphens\". Expected format: none | manual | auto").noMore();
  }
}
