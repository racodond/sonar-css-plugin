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
  public void all() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/all.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage("Update the invalid value of property \"all\". Expected format: inherit | initial | unset").noMore();
  }

  @Test
  public void alignment_adjust() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/alignment-adjust.css"), check);
    CheckMessagesVerifier
      .verify(file.getCheckMessages())
      .next()
      .atLine(19)
      .withMessage(
        "Update the invalid value of property \"alignment-adjust\". Expected format: auto | baseline | before-edge | text-before-edge | middle | central | after-edge | text-after-edge | ideographic | alphabetic | hanging | mathematical | <percentage> | <length>")
      .next()
      .atLine(20)
      .withMessage(
        "Update the invalid value of property \"alignment-adjust\". Expected format: auto | baseline | before-edge | text-before-edge | middle | central | after-edge | text-after-edge | ideographic | alphabetic | hanging | mathematical | <percentage> | <length>")
      .noMore();
  }

  @Test
  public void alignment_baseline() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/alignment-baseline.css"), check);
    CheckMessagesVerifier
      .verify(file.getCheckMessages())
      .next()
      .atLine(15)
      .withMessage(
        "Update the invalid value of property \"alignment-baseline\". Expected format: baseline | use-script | before-edge | text-before-edge | after-edge | text-after-edge | central | middle | ideographic | alphabetic | hanging | mathematical")
      .next()
      .atLine(16)
      .withMessage(
        "Update the invalid value of property \"alignment-baseline\". Expected format: baseline | use-script | before-edge | text-before-edge | after-edge | text-after-edge | central | middle | ideographic | alphabetic | hanging | mathematical")
      .next()
      .atLine(17)
      .withMessage(
        "Update the invalid value of property \"alignment-baseline\". Expected format: baseline | use-script | before-edge | text-before-edge | after-edge | text-after-edge | central | middle | ideographic | alphabetic | hanging | mathematical")
      .next()
      .atLine(18)
      .withMessage(
        "Update the invalid value of property \"alignment-baseline\". Expected format: baseline | use-script | before-edge | text-before-edge | after-edge | text-after-edge | central | middle | ideographic | alphabetic | hanging | mathematical")
      .next()
      .atLine(19)
      .withMessage(
        "Update the invalid value of property \"alignment-baseline\". Expected format: baseline | use-script | before-edge | text-before-edge | after-edge | text-after-edge | central | middle | ideographic | alphabetic | hanging | mathematical")
      .next()
      .atLine(20)
      .withMessage(
        "Update the invalid value of property \"alignment-baseline\". Expected format: baseline | use-script | before-edge | text-before-edge | after-edge | text-after-edge | central | middle | ideographic | alphabetic | hanging | mathematical")
      .noMore();
  }

  @Test
  public void animation() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation.css"), check);
    CheckMessagesVerifier
      .verify(file.getCheckMessages())
      .next()
      .atLine(11)
      .withMessage(
        "Update the invalid value of property \"animation\". Expected format: <time> || <single-timing-function> || <time> || <single-animation-iteration-count> || <single-animation-direction> || <single-animation-fill-mode> || <single-animation-play-state> || <single-animation-name>")
      .next()
      .atLine(12)
      .withMessage(
        "Update the invalid value of property \"animation\". Expected format: <time> || <single-timing-function> || <time> || <single-animation-iteration-count> || <single-animation-direction> || <single-animation-fill-mode> || <single-animation-play-state> || <single-animation-name>")
      .noMore();
  }

  @Test
  public void animation_delay() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation-delay.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage("Update the invalid value of property \"animation-delay\". Expected format: <time>").next()
      .atLine(8).withMessage("Update the invalid value of property \"animation-delay\". Expected format: <time>").next()
      .atLine(9).withMessage("Update the invalid value of property \"animation-delay\". Expected format: <time>").noMore();
  }

  @Test
  public void animation_direction() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation-direction.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage("Update the invalid value of property \"animation-direction\". Expected format: normal | reverse | alternate | alternate-reverse").next()
      .atLine(8).withMessage("Update the invalid value of property \"animation-direction\". Expected format: normal | reverse | alternate | alternate-reverse").next()
      .atLine(9).withMessage("Update the invalid value of property \"animation-direction\". Expected format: normal | reverse | alternate | alternate-reverse").noMore();
  }

  @Test
  public void animation_duration() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation-duration.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage("Update the invalid value of property \"animation-duration\". Expected format: <time>(>=0)").next()
      .atLine(7).withMessage("Update the invalid value of property \"animation-duration\". Expected format: <time>(>=0)").next()
      .atLine(8).withMessage("Update the invalid value of property \"animation-duration\". Expected format: <time>(>=0)").next()
      .atLine(9).withMessage("Update the invalid value of property \"animation-duration\". Expected format: <time>(>=0)").noMore();
  }

  @Test
  public void animation_fill_mode() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation-fill-mode.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage("Update the invalid value of property \"animation-fill-mode\". Expected format: none | forwards | backwards | both").next()
      .atLine(8).withMessage("Update the invalid value of property \"animation-fill-mode\". Expected format: none | forwards | backwards | both").noMore();
  }

  @Test
  public void animation_iteration_count() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation-iteration-count.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage("Update the invalid value of property \"animation-iteration-count\". Expected format: infinite | <number>(>=0)").next()
      .atLine(8).withMessage("Update the invalid value of property \"animation-iteration-count\". Expected format: infinite | <number>(>=0)").noMore();
  }

  @Test
  public void animation_name() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation-name.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(4).withMessage("Update the invalid value of property \"animation-name\". Expected format: none | <custom-identifier>").next()
      .atLine(5).withMessage("Update the invalid value of property \"animation-name\". Expected format: none | <custom-identifier>").noMore();
  }

  @Test
  public void animation_play_state() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation-play-state.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage("Update the invalid value of property \"animation-play-state\". Expected format: running | paused").next()
      .atLine(6).withMessage("Update the invalid value of property \"animation-play-state\". Expected format: running | paused").noMore();
  }

  @Test
  public void animation_timing_function() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation-timing-function.css"), check);
    CheckMessagesVerifier
      .verify(file.getCheckMessages())
      .next()
      .atLine(12)
      .withMessage(
        "Update the invalid value of property \"animation-timing-function\". Expected format: ease | linear | ease-in | ease-out | ease-in-out | step-start | step-end | <function>(steps | cubic-bezier)")
      .next()
      .atLine(13)
      .withMessage(
        "Update the invalid value of property \"animation-timing-function\". Expected format: ease | linear | ease-in | ease-out | ease-in-out | step-start | step-end | <function>(steps | cubic-bezier)")
      .next()
      .atLine(14)
      .withMessage(
        "Update the invalid value of property \"animation-timing-function\". Expected format: ease | linear | ease-in | ease-out | ease-in-out | step-start | step-end | <function>(steps | cubic-bezier)")
      .next()
      .atLine(15)
      .withMessage(
        "Update the invalid value of property \"animation-timing-function\". Expected format: ease | linear | ease-in | ease-out | ease-in-out | step-start | step-end | <function>(steps | cubic-bezier)")
      .noMore();
  }

  @Test
  public void appearance() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/appearance.css"), check);
    CheckMessagesVerifier
      .verify(file.getCheckMessages())
      .next()
      .atLine(32)
      .withMessage(
        "Update the invalid value of property \"appearance\". Expected format: normal | icon | window | desktop | workspace | document | tooltip | dialog | button | push-button | hyperlink | radio-button | checkbox | menu-item | tab | menu | menubar | pull-down-menu | pop-up-menu | list-menu | radio-group | checkbox-group | outline-tree | range | field | combo-box | signature | password")
      .next()
      .atLine(33)
      .withMessage(
        "Update the invalid value of property \"appearance\". Expected format: normal | icon | window | desktop | workspace | document | tooltip | dialog | button | push-button | hyperlink | radio-button | checkbox | menu-item | tab | menu | menubar | pull-down-menu | pop-up-menu | list-menu | radio-group | checkbox-group | outline-tree | range | field | combo-box | signature | password")
      .next()
      .atLine(34)
      .withMessage(
        "Update the invalid value of property \"appearance\". Expected format: normal | icon | window | desktop | workspace | document | tooltip | dialog | button | push-button | hyperlink | radio-button | checkbox | menu-item | tab | menu | menubar | pull-down-menu | pop-up-menu | list-menu | radio-group | checkbox-group | outline-tree | range | field | combo-box | signature | password")
      .noMore();
  }

  @Test
  public void backface_visibility() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/backface-visibility.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage("Update the invalid value of property \"backface-visibility\". Expected format: visible | hidden").next()
      .atLine(6).withMessage("Update the invalid value of property \"backface-visibility\". Expected format: visible | hidden").next()
      .atLine(7).withMessage("Update the invalid value of property \"backface-visibility\". Expected format: visible | hidden").noMore();
  }

  @Test
  public void background_attachment() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/background-attachment.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage("Update the invalid value of property \"background-attachment\". Expected format: scroll | fixed | local [, scroll | fixed | local]*").next()
      .atLine(9).withMessage("Update the invalid value of property \"background-attachment\". Expected format: scroll | fixed | local [, scroll | fixed | local]*").noMore();
  }

  @Test
  public void background_clip() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/background-clip.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage("Update the invalid value of property \"background-clip\". Expected format: <box> [, <box>]*").next()
      .atLine(7).withMessage("Update the invalid value of property \"background-clip\". Expected format: <box> [, <box>]*").noMore();
  }

  @Test
  public void background_image() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/background-image.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage("Update the invalid value of property \"background-image\". Expected format: none | <uri> [, none | <uri>]*").next()
      .atLine(8).withMessage("Update the invalid value of property \"background-image\". Expected format: none | <uri> [, none | <uri>]*").noMore();
  }

  @Test
  public void background_origin() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/background-origin.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage("Update the invalid value of property \"background-origin\". Expected format: <box> [, <box>]*").next()
      .atLine(7).withMessage("Update the invalid value of property \"background-origin\". Expected format: <box> [, <box>]*").noMore();
  }

  @Test
  public void baseline_shift() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/baseline-shift.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(10).withMessage(
        "Update the invalid value of property \"baseline-shift\". Expected format: baseline | sub | super | <length> | <percentage>").next()
      .atLine(11).withMessage(
        "Update the invalid value of property \"baseline-shift\". Expected format: baseline | sub | super | <length> | <percentage>").noMore();
  }

  @Test
  public void border_color() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/border-color.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage("Update the invalid value of property \"border-color\". Expected format: [ <color> | transparent | currentColor ]{1,4}").next()
      .atLine(12).withMessage("Update the invalid value of property \"border-color\". Expected format: [ <color> | transparent | currentColor ]{1,4}").noMore();
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
      .atLine(16).withMessage("Update the invalid value of property \"color\". Expected format: <color> | transparent | currentColor").noMore();
  }

  @Test
  public void display() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/display.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(20).withMessage(
        "Update the invalid value of property \"display\". Expected format: inline | block | list-item | inline-block | table | inline-table |"
          + " table-row-group | table-header-group | table-footer-group | table-row | table-column-group | table-column | table-cell |"
          + " table-caption | none | flex | inline-flex | grid | inline-grid").next()
      .atLine(21).withMessage(
        "Update the invalid value of property \"display\". Expected format: inline | block | list-item | inline-block | table | inline-table |"
          + " table-row-group | table-header-group | table-footer-group | table-row | table-column-group | table-column | table-cell |"
          + " table-caption | none | flex | inline-flex | grid | inline-grid").noMore();
  }

  @Test
  public void dominant_baseline() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/dominant-baseline.css"), check);
    CheckMessagesVerifier
      .verify(file.getCheckMessages())
      .next()
      .atLine(15)
      .withMessage(
        "Update the invalid value of property \"dominant-baseline\". Expected format: auto | use-script | no-change | reset-size | alphabetic | hanging | ideographic | mathematical | central | middle | text-after-edge | text-before-edge")
      .next()
      .atLine(16)
      .withMessage(
        "Update the invalid value of property \"dominant-baseline\". Expected format: auto | use-script | no-change | reset-size | alphabetic | hanging | ideographic | mathematical | central | middle | text-after-edge | text-before-edge")
      .next()
      .atLine(17)
      .withMessage(
        "Update the invalid value of property \"dominant-baseline\". Expected format: auto | use-script | no-change | reset-size | alphabetic | hanging | ideographic | mathematical | central | middle | text-after-edge | text-before-edge")
      .noMore();
  }

  @Test
  public void drop_initial_value() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/drop-initial-value.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(
        "Update the invalid value of property \"drop-initial-value\". Expected format: initial | <integer>(>=0)").next()
      .atLine(6).withMessage(
        "Update the invalid value of property \"drop-initial-value\". Expected format: initial | <integer>(>=0)").next()
      .atLine(7).withMessage("Update the invalid value of property \"drop-initial-value\". Expected format: initial | <integer>(>=0)").noMore();
  }

  @Test
  public void filter() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/filter.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(12).withMessage("Update the invalid value of property \"filter\". Expected format: none | [<uri> | <filter-function>]+").next()
      .atLine(13).withMessage("Update the invalid value of property \"filter\". Expected format: none | [<uri> | <filter-function>]+").next()
      .atLine(14).withMessage("Update the invalid value of property \"filter\". Expected format: none | [<uri> | <filter-function>]+").next()
      .atLine(15).withMessage("Update the invalid value of property \"filter\". Expected format: none | [<uri> | <filter-function>]+").next()
      .atLine(16).withMessage("Update the invalid value of property \"filter\". Expected format: none | [<uri> | <filter-function>]+").next()
      .atLine(17).withMessage("Update the invalid value of property \"filter\". Expected format: none | [<uri> | <filter-function>]+").noMore();
  }

  @Test
  public void font_size() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/font-size.css"), check);
    CheckMessagesVerifier
      .verify(file.getCheckMessages())
      .next()
      .atLine(17)
      .withMessage(
        "Update the invalid value of property \"font-size\". Expected format: xx-small | x-small | small | medium | large | x-large | xx-large | larger | smaller | <length>(>=0) | <percentage>(>=0)")
      .next()
      .atLine(18)
      .withMessage(
        "Update the invalid value of property \"font-size\". Expected format: xx-small | x-small | small | medium | large | x-large | xx-large | larger | smaller | <length>(>=0) | <percentage>(>=0)")
      .next()
      .atLine(19)
      .withMessage(
        "Update the invalid value of property \"font-size\". Expected format: xx-small | x-small | small | medium | large | x-large | xx-large | larger | smaller | <length>(>=0) | <percentage>(>=0)")
      .next()
      .atLine(20)
      .withMessage(
        "Update the invalid value of property \"font-size\". Expected format: xx-small | x-small | small | medium | large | x-large | xx-large | larger | smaller | <length>(>=0) | <percentage>(>=0)")
      .noMore();
  }

  @Test
  public void font_size_adjust() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/font-size-adjust.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage("Update the invalid value of property \"font-size-adjust\". Expected format: none | <number>").next()
      .atLine(7).withMessage("Update the invalid value of property \"font-size-adjust\". Expected format: none | <number>").noMore();
  }

  @Test
  public void font_weight() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/font-weight.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(16).withMessage(
        "Update the invalid value of property \"font-weight\". Expected format: normal | bold | bolder | lighter | 100 | 200 | 300 | 400 | 500 | 600 | 700 | 800 | 900").next()
      .atLine(17).withMessage(
        "Update the invalid value of property \"font-weight\". Expected format: normal | bold | bolder | lighter | 100 | 200 | 300 | 400 | 500 | 600 | 700 | 800 | 900").noMore();

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

  @Test
  public void inline_box_align() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/inline-box-align.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage("Update the invalid value of property \"inline-box-align\". Expected format: initial | last | <integer>").next()
      .atLine(8).withMessage("Update the invalid value of property \"inline-box-align\". Expected format: initial | last | <integer>").noMore();
  }

  @Test
  public void line_height() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/line-height.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage("Update the invalid value of property \"line-height\". Expected format: normal | none | <length>(>=0) | <percentage>(>=0) | <number>(>=0)").next()
      .atLine(9).withMessage("Update the invalid value of property \"line-height\". Expected format: normal | none | <length>(>=0) | <percentage>(>=0) | <number>(>=0)").next()
      .atLine(10).withMessage("Update the invalid value of property \"line-height\". Expected format: normal | none | <length>(>=0) | <percentage>(>=0) | <number>(>=0)").next()
      .atLine(11).withMessage("Update the invalid value of property \"line-height\". Expected format: normal | none | <length>(>=0) | <percentage>(>=0) | <number>(>=0)").next()
      .atLine(12).withMessage("Update the invalid value of property \"line-height\". Expected format: normal | none | <length>(>=0) | <percentage>(>=0) | <number>(>=0)").noMore();
  }

  @Test
  public void line_stacking() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/line-stacking.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(9).withMessage(
        "Update the invalid value of property \"line-stacking\". Expected format: <line-stacking-strategy> || <line-stacking-ruby> || <line-stacking-shift>").next()
      .atLine(10).withMessage(
        "Update the invalid value of property \"line-stacking\". Expected format: <line-stacking-strategy> || <line-stacking-ruby> || <line-stacking-shift>").next()
      .atLine(11).withMessage(
        "Update the invalid value of property \"line-stacking\". Expected format: <line-stacking-strategy> || <line-stacking-ruby> || <line-stacking-shift>").next()
      .atLine(12).withMessage("Update the invalid value of property \"line-stacking\". Expected format: <line-stacking-strategy> || <line-stacking-ruby> || <line-stacking-shift>")
      .noMore();
  }

  @Test
  public void line_stacking_ruby() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/line-stacking-ruby.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(
        "Update the invalid value of property \"line-stacking-ruby\". Expected format: exclude-ruby | include-ruby").next()
      .atLine(6).withMessage(
        "Update the invalid value of property \"line-stacking-ruby\". Expected format: exclude-ruby | include-ruby").noMore();
  }

  @Test
  public void line_stacking_shift() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/line-stacking-shift.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(
        "Update the invalid value of property \"line-stacking-shift\". Expected format: consider-shifts | disregard-shifts").next()
      .atLine(6).withMessage(
        "Update the invalid value of property \"line-stacking-shift\". Expected format: consider-shifts | disregard-shifts").noMore();
  }

  @Test
  public void line_stacking_strategy() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/line-stacking-strategy.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage("Update the invalid value of property \"line-stacking-strategy\". Expected format: inline-line-height | block-line-height | max-height | grid-height")
      .next()
      .atLine(8).withMessage("Update the invalid value of property \"line-stacking-strategy\". Expected format: inline-line-height | block-line-height | max-height | grid-height")
      .noMore();
  }

  @Test
  public void min_height() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/min-height.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage("Update the invalid value of property \"min-height\". Expected format: auto | <length> | <percentage>").next()
      .atLine(7).withMessage("Update the invalid value of property \"min-height\". Expected format: auto | <length> | <percentage>").noMore();
  }

  @Test
  public void min_width() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/min-width.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage("Update the invalid value of property \"min-width\". Expected format: auto | <length> | <percentage>").next()
      .atLine(7).withMessage("Update the invalid value of property \"min-width\". Expected format: auto | <length> | <percentage>").noMore();
  }

  @Test
  public void opacity() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/opacity.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage("Update the invalid value of property \"opacity\". Expected format: <number>(0.0,1.0)").next()
      .atLine(8).withMessage("Update the invalid value of property \"opacity\". Expected format: <number>(0.0,1.0)").next()
      .atLine(9).withMessage("Update the invalid value of property \"opacity\". Expected format: <number>(0.0,1.0)").noMore();
  }

  @Test
  public void outline_offset() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/outline-offset.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage("Update the invalid value of property \"outline-offset\". Expected format: <length>").next()
      .atLine(9).withMessage("Update the invalid value of property \"outline-offset\". Expected format: <length>").noMore();
  }

  @Test
  public void overflow() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/overflow.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage("Update the invalid value of property \"overflow\". Expected format: visible | hidden | scroll | auto").next()
      .atLine(8).withMessage("Update the invalid value of property \"overflow\". Expected format: visible | hidden | scroll | auto").next()
      .atLine(9).withMessage("Update the invalid value of property \"overflow\". Expected format: visible | hidden | scroll | auto").noMore();
  }

  @Test
  public void overflow_x() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/overflow-x.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage("Update the invalid value of property \"overflow-x\". Expected format: visible | hidden | scroll | auto").next()
      .atLine(8).withMessage("Update the invalid value of property \"overflow-x\". Expected format: visible | hidden | scroll | auto").next()
      .atLine(9).withMessage("Update the invalid value of property \"overflow-x\". Expected format: visible | hidden | scroll | auto").noMore();
  }

  @Test
  public void overflow_y() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/overflow-y.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage("Update the invalid value of property \"overflow-y\". Expected format: visible | hidden | scroll | auto").next()
      .atLine(8).withMessage("Update the invalid value of property \"overflow-y\". Expected format: visible | hidden | scroll | auto").next()
      .atLine(9).withMessage("Update the invalid value of property \"overflow-y\". Expected format: visible | hidden | scroll | auto").noMore();
  }

  @Test
  public void text_height() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/text-height.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage("Update the invalid value of property \"text-height\". Expected format: auto | font-size | text-size | max-size").next()
      .atLine(8).withMessage("Update the invalid value of property \"text-height\". Expected format: auto | font-size | text-size | max-size").noMore();
  }

  @Test
  public void vertical_align() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/vertical-align.css"), check);
    CheckMessagesVerifier
      .verify(file.getCheckMessages())
      .next()
      .atLine(18)
      .withMessage(
        "Update the invalid value of property \"vertical-align\". Expected format: auto | use-script | baseline | sub | super | top | text-top | central | middle | bottom | text-bottom | <percentage> | <length>")
      .next()
      .atLine(19)
      .withMessage(
        "Update the invalid value of property \"vertical-align\". Expected format: auto | use-script | baseline | sub | super | top | text-top | central | middle | bottom | text-bottom | <percentage> | <length>")
      .noMore();
  }
}
