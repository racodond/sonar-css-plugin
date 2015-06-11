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
  private String errorMessage;

  @Test
  public void all() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/all.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage("Update the invalid value of property \"all\". Expected format: inherit | initial | unset").noMore();
  }

  @Test
  public void align_content() {
    errorMessage = "Update the invalid value of property \"align-content\". Expected format: flex-start | flex-end | center | space-between | space-around | stretch";
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/align-content.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(10).withMessage(errorMessage).noMore();
  }

  @Test
  public void align_items() {
    errorMessage = "Update the invalid value of property \"align-items\". Expected format: flex-start | flex-end | center | baseline | stretch";
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/align-items.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void align_self() {
    errorMessage = "Update the invalid value of property \"align-self\". Expected format: auto | flex-start | flex-end | center | baseline | stretch";
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/align-self.css"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(10).withMessage(errorMessage).noMore();
  }

  @Test
  public void alignment_adjust() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/alignment-adjust.css"), check);
    errorMessage = "Update the invalid value of property \"alignment-adjust\". Expected format: auto | baseline | before-edge | text-before-edge | middle | central | after-edge | text-after-edge | ideographic | alphabetic | hanging | mathematical | <percentage> | <length>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(19).withMessage(errorMessage).next()
      .atLine(20).withMessage(errorMessage).noMore();
  }

  @Test
  public void alignment_baseline() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/alignment-baseline.css"), check);
    errorMessage = "Update the invalid value of property \"alignment-baseline\". Expected format: baseline | use-script | before-edge | text-before-edge | after-edge | text-after-edge | central | middle | ideographic | alphabetic | hanging | mathematical";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(15).withMessage(errorMessage).next()
      .atLine(16).withMessage(errorMessage).next()
      .atLine(17).withMessage(errorMessage).next()
      .atLine(18).withMessage(errorMessage).next()
      .atLine(19).withMessage(errorMessage).next()
      .atLine(20).withMessage(errorMessage).noMore();
  }

  @Test
  public void animation() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation.css"), check);
    errorMessage = "Update the invalid value of property \"animation\". Expected format: <time> || <single-timing-function> || <time> || <single-animation-iteration-count> || <single-animation-direction> || <single-animation-fill-mode> || <single-animation-play-state> || <single-animation-name>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(11).withMessage(errorMessage).next()
      .atLine(12).withMessage(errorMessage).noMore();
  }

  @Test
  public void animation_delay() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation-delay.css"), check);
    errorMessage = "Update the invalid value of property \"animation-delay\". Expected format: <time>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void animation_direction() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation-direction.css"), check);
    errorMessage = "Update the invalid value of property \"animation-direction\". Expected format: normal | reverse | alternate | alternate-reverse";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void animation_duration() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation-duration.css"), check);
    errorMessage = "Update the invalid value of property \"animation-duration\". Expected format: <time>(>=0)";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void animation_fill_mode() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation-fill-mode.css"), check);
    errorMessage = "Update the invalid value of property \"animation-fill-mode\". Expected format: none | forwards | backwards | both";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).noMore();
  }

  @Test
  public void animation_iteration_count() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation-iteration-count.css"), check);
    errorMessage = "Update the invalid value of property \"animation-iteration-count\". Expected format: infinite | <number>(>=0)";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).noMore();
  }

  @Test
  public void animation_name() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation-name.css"), check);
    errorMessage = "Update the invalid value of property \"animation-name\". Expected format: none | <custom-identifier>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(4).withMessage(errorMessage).next()
      .atLine(5).withMessage(errorMessage).noMore();
  }

  @Test
  public void animation_play_state() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation-play-state.css"), check);
    errorMessage = "Update the invalid value of property \"animation-play-state\". Expected format: running | paused";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessage).next()
      .atLine(6).withMessage(errorMessage).noMore();
  }

  @Test
  public void animation_timing_function() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/animation-timing-function.css"), check);
    errorMessage = "Update the invalid value of property \"animation-timing-function\". Expected format: ease | linear | ease-in | ease-out | ease-in-out | step-start | step-end | <function>(steps | cubic-bezier)";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(12).withMessage(errorMessage).next()
      .atLine(13).withMessage(errorMessage).next()
      .atLine(14).withMessage(errorMessage).next()
      .atLine(15).withMessage(errorMessage).noMore();
  }

  @Test
  public void appearance() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/appearance.css"), check);
    errorMessage = "Update the invalid value of property \"appearance\". Expected format: normal | icon | window | desktop | workspace | document | tooltip | dialog | button | push-button | hyperlink | radio-button | checkbox | menu-item | tab | menu | menubar | pull-down-menu | pop-up-menu | list-menu | radio-group | checkbox-group | outline-tree | range | field | combo-box | signature | password";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(32).withMessage(errorMessage).next()
      .atLine(33).withMessage(errorMessage).next()
      .atLine(34).withMessage(errorMessage).noMore();
  }

  @Test
  public void backface_visibility() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/backface-visibility.css"), check);
    errorMessage = "Update the invalid value of property \"backface-visibility\". Expected format: visible | hidden";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessage).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).noMore();
  }

  @Test
  public void background_attachment() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/background-attachment.css"), check);
    errorMessage = "Update the invalid value of property \"background-attachment\". Expected format: scroll | fixed | local [, scroll | fixed | local]*";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void background_clip() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/background-clip.css"), check);
    errorMessage = "Update the invalid value of property \"background-clip\". Expected format: <box> [, <box>]*";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).noMore();
  }

  @Test
  public void background_color() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/background-color.css"), check);
    errorMessage = "Update the invalid value of property \"background-color\". Expected format: <color>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(10).withMessage(errorMessage).next()
      .atLine(11).withMessage(errorMessage).next()
      .atLine(16).withMessage(errorMessage).noMore();
  }

  @Test
  public void background_image() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/background-image.css"), check);
    errorMessage = "Update the invalid value of property \"background-image\". Expected format: none | <image> [, <image>]*";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(18).withMessage(errorMessage).next()
      .atLine(20).withMessage(errorMessage).noMore();
  }

  @Test
  public void background_origin() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/background-origin.css"), check);
    errorMessage = "Update the invalid value of property \"background-origin\". Expected format: <box> [, <box>]*";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).noMore();
  }

  @Test
  public void background_repeat() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/background-repeat.css"), check);
    errorMessage = "Update the invalid value of property \"background-repeat\". Expected format: repeat-x | repeat-y | [repeat | space | round | no-repeat]{1,2} [, repeat-x | repeat-y | [repeat | space | round | no-repeat]{1,2}]*";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(16).withMessage(errorMessage).next()
      .atLine(17).withMessage(errorMessage).next()
      .atLine(18).withMessage(errorMessage).next()
      .atLine(19).withMessage(errorMessage).next()
      .atLine(20).withMessage(errorMessage).next()
      .atLine(21).withMessage(errorMessage).next()
      .atLine(22).withMessage(errorMessage).next()
      .atLine(23).withMessage(errorMessage).next()
      .atLine(24).withMessage(errorMessage).next()
      .atLine(25).withMessage(errorMessage).noMore();
  }

  @Test
  public void background_size() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/background-size.css"), check);
    errorMessage = "Update the invalid value of property \"background-size\". Expected format: [ <length> | <percentage> | auto ]{1,2} | cover | contain [,  [ <length> | <percentage> | auto ]{1,2} | cover | contain ]*";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(19).withMessage(errorMessage).next()
      .atLine(20).withMessage(errorMessage).next()
      .atLine(21).withMessage(errorMessage).next()
      .atLine(22).withMessage(errorMessage).next()
      .atLine(23).withMessage(errorMessage).next()
      .atLine(24).withMessage(errorMessage).next()
      .atLine(25).withMessage(errorMessage).next()
      .atLine(26).withMessage(errorMessage).next()
      .atLine(27).withMessage(errorMessage).next()
      .atLine(28).withMessage(errorMessage).next()
      .atLine(29).withMessage(errorMessage).noMore();
  }

  @Test
  public void baseline_shift() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/baseline-shift.css"), check);
    errorMessage = "Update the invalid value of property \"baseline-shift\". Expected format: sub | super | <length> | <percentage>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(10).withMessage(errorMessage).noMore();
  }

  @Test
  public void border_xxx() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/border-xxx.css"), check);
    errorMessage = "Update the invalid value of property \"border\". Expected format: <border-width> || <border-style> || <color>";
    String errorMessageTop = "Update the invalid value of property \"border-top\". Expected format: <border-width> || <border-style> || <color>";
    String errorMessageRight = "Update the invalid value of property \"border-right\". Expected format: <border-width> || <border-style> || <color>";
    String errorMessageLeft = "Update the invalid value of property \"border-left\". Expected format: <border-width> || <border-style> || <color>";
    String errorMessageBottom = "Update the invalid value of property \"border-bottom\". Expected format: <border-width> || <border-style> || <color>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(9).withMessage(errorMessageTop).next()
      .atLine(10).withMessage(errorMessageTop).next()
      .atLine(11).withMessage(errorMessageTop).next()
      .atLine(22).withMessage(errorMessageRight).next()
      .atLine(23).withMessage(errorMessageRight).next()
      .atLine(24).withMessage(errorMessageRight).next()
      .atLine(35).withMessage(errorMessageLeft).next()
      .atLine(36).withMessage(errorMessageLeft).next()
      .atLine(37).withMessage(errorMessageLeft).next()
      .atLine(48).withMessage(errorMessageBottom).next()
      .atLine(49).withMessage(errorMessageBottom).next()
      .atLine(50).withMessage(errorMessageBottom).next()
      .atLine(66).withMessage(errorMessage).next()
      .atLine(67).withMessage(errorMessage).next()
      .atLine(68).withMessage(errorMessage).noMore();
  }

  @Test
  public void border_collapse() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/border-collapse.css"), check);
    errorMessage = "Update the invalid value of property \"border-collapse\". Expected format: collapse | separate";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessage).next()
      .atLine(6).withMessage(errorMessage).noMore();
  }

  @Test
  public void border_color() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/border-color.css"), check);
    errorMessage = "Update the invalid value of property \"border-color\". Expected format: <color>{1,4}";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(12).withMessage(errorMessage).noMore();
  }

  @Test
  public void border_xxx_color() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/border-xxx-color.css"), check);
    String errorMessageTop = "Update the invalid value of property \"border-top-color\". Expected format: <color>";
    String errorMessageRight = "Update the invalid value of property \"border-right-color\". Expected format: <color>";
    String errorMessageLeft = "Update the invalid value of property \"border-left-color\". Expected format: <color>";
    String errorMessageBottom = "Update the invalid value of property \"border-bottom-color\". Expected format: <color>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessageTop).next()
      .atLine(6).withMessage(errorMessageTop).next()
      .atLine(13).withMessage(errorMessageRight).next()
      .atLine(14).withMessage(errorMessageRight).next()
      .atLine(21).withMessage(errorMessageLeft).next()
      .atLine(22).withMessage(errorMessageLeft).next()
      .atLine(29).withMessage(errorMessageBottom).next()
      .atLine(30).withMessage(errorMessageBottom).noMore();
  }

  @Test
  public void border_spacing() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/border-spacing.css"), check);
    errorMessage = "Update the invalid value of property \"border-spacing\". Expected format: <length>(>=0) <length>(>=0)?";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessage).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).noMore();
  }

  @Test
  public void border_xxx_radius() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/border-xxx-radius.css"), check);
    String errorMessageTopLeft = "Update the invalid value of property \"border-top-left-radius\". Expected format: [ <length>(>=0) | <percentage>(>=0) ]{1,2}";
    String errorMessageTopRight = "Update the invalid value of property \"border-top-right-radius\". Expected format: [ <length>(>=0) | <percentage>(>=0) ]{1,2}";
    String errorMessageBottomLeft = "Update the invalid value of property \"border-bottom-left-radius\". Expected format: [ <length>(>=0) | <percentage>(>=0) ]{1,2}";
    String errorMessageBottomRight = "Update the invalid value of property \"border-bottom-right-radius\". Expected format: [ <length>(>=0) | <percentage>(>=0) ]{1,2}";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessageTopLeft).next()
      .atLine(8).withMessage(errorMessageTopLeft).next()
      .atLine(9).withMessage(errorMessageTopLeft).next()
      .atLine(10).withMessage(errorMessageTopLeft).next()
      .atLine(11).withMessage(errorMessageTopLeft).next()
      .atLine(12).withMessage(errorMessageTopLeft).next()
      .atLine(21).withMessage(errorMessageTopRight).next()
      .atLine(22).withMessage(errorMessageTopRight).next()
      .atLine(23).withMessage(errorMessageTopRight).next()
      .atLine(24).withMessage(errorMessageTopRight).next()
      .atLine(25).withMessage(errorMessageTopRight).next()
      .atLine(26).withMessage(errorMessageTopRight).next()
      .atLine(35).withMessage(errorMessageBottomLeft).next()
      .atLine(36).withMessage(errorMessageBottomLeft).next()
      .atLine(37).withMessage(errorMessageBottomLeft).next()
      .atLine(38).withMessage(errorMessageBottomLeft).next()
      .atLine(39).withMessage(errorMessageBottomLeft).next()
      .atLine(40).withMessage(errorMessageBottomLeft).next()
      .atLine(49).withMessage(errorMessageBottomRight).next()
      .atLine(50).withMessage(errorMessageBottomRight).next()
      .atLine(51).withMessage(errorMessageBottomRight).next()
      .atLine(52).withMessage(errorMessageBottomRight).next()
      .atLine(53).withMessage(errorMessageBottomRight).next()
      .atLine(54).withMessage(errorMessageBottomRight).noMore();
  }

  @Test
  public void border_radius() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/border-radius.css"), check);
    errorMessage = "Update the invalid value of property \"border-radius\". Expected format: [ <length>(>=0) | <percentage>(>=0) ]{1,4} [ / [ <length>(>=0) | <percentage>(>=0) ]{1,4} ]?";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(13).withMessage(errorMessage).next()
      .atLine(14).withMessage(errorMessage).next()
      .atLine(15).withMessage(errorMessage).next()
      .atLine(16).withMessage(errorMessage).next()
      .atLine(17).withMessage(errorMessage).noMore();
  }

  @Test
  public void border_xxx_style() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/border-xxx-style.css"), check);
    String errorMessageTop = "Update the invalid value of property \"border-top-style\". Expected format: none | hidden | dotted | dashed | solid | double | groove | ridge | inset | outset";
    String errorMessageRight = "Update the invalid value of property \"border-right-style\". Expected format: none | hidden | dotted | dashed | solid | double | groove | ridge | inset | outset";
    String errorMessageLeft = "Update the invalid value of property \"border-left-style\". Expected format: none | hidden | dotted | dashed | solid | double | groove | ridge | inset | outset";
    String errorMessageBottom = "Update the invalid value of property \"border-bottom-style\". Expected format: none | hidden | dotted | dashed | solid | double | groove | ridge | inset | outset";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(11).withMessage(errorMessageTop).next()
      .atLine(12).withMessage(errorMessageTop).next()
      .atLine(25).withMessage(errorMessageRight).next()
      .atLine(26).withMessage(errorMessageRight).next()
      .atLine(39).withMessage(errorMessageLeft).next()
      .atLine(40).withMessage(errorMessageLeft).next()
      .atLine(53).withMessage(errorMessageBottom).next()
      .atLine(54).withMessage(errorMessageBottom).noMore();
  }

  @Test
  public void border_style() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/border-style.css"), check);
    errorMessage = "Update the invalid value of property \"border-style\". Expected format: <border-style>{1,4}";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(10).withMessage(errorMessage).noMore();
  }

  @Test
  public void border_xxx_width() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/border-xxx-width.css"), check);
    String errorMessageTop = "Update the invalid value of property \"border-top-width\". Expected format: thin | medium | thick | <length>(>=0)";
    String errorMessageRight = "Update the invalid value of property \"border-right-width\". Expected format: thin | medium | thick | <length>(>=0)";
    String errorMessageLeft = "Update the invalid value of property \"border-left-width\". Expected format: thin | medium | thick | <length>(>=0)";
    String errorMessageBottom = "Update the invalid value of property \"border-bottom-width\". Expected format: thin | medium | thick | <length>(>=0)";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessageTop).next()
      .atLine(8).withMessage(errorMessageTop).next()
      .atLine(17).withMessage(errorMessageRight).next()
      .atLine(18).withMessage(errorMessageRight).next()
      .atLine(27).withMessage(errorMessageLeft).next()
      .atLine(28).withMessage(errorMessageLeft).next()
      .atLine(37).withMessage(errorMessageBottom).next()
      .atLine(38).withMessage(errorMessageBottom).noMore();
  }

  @Test
  public void border_width() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/border-width.css"), check);
    errorMessage = "Update the invalid value of property \"border-width\". Expected format: <border-width>{1,4}";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(11).withMessage(errorMessage).next()
      .atLine(12).withMessage(errorMessage).noMore();
  }

  @Test
  public void bottom() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/bottom.css"), check);
    errorMessage = "Update the invalid value of property \"bottom\". Expected format: auto | <length> | <percentage>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void box_decoration_break() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/box-decoration-break.css"), check);
    errorMessage = "Update the invalid value of property \"box-decoration-break\". Expected format: slice | clone";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessage).next()
      .atLine(6).withMessage(errorMessage).noMore();
  }

  @Test
  public void box_sizing() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/box-sizing.css"), check);
    errorMessage = "Update the invalid value of property \"box-sizing\". Expected format: <box>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).noMore();
  }

  @Test
  public void caret_color() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/caret-color.css"), check);
    errorMessage = "Update the invalid value of property \"caret-color\". Expected format: auto | <color>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(10).withMessage(errorMessage).next()
      .atLine(11).withMessage(errorMessage).next()
      .atLine(16).withMessage(errorMessage).noMore();
  }

  @Test
  public void clear() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/clear.css"), check);
    errorMessage = "Update the invalid value of property \"clear\". Expected format: none | left | right | both";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).noMore();
  }

  @Test
  public void clip() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/clip.css"), check);
    errorMessage = "Update the invalid value of property \"clip\". Expected format: <function>(rect) | auto";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessage).next()
      .atLine(6).withMessage(errorMessage).noMore();
  }

  @Test
  public void color() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/color.css"), check);
    errorMessage = "Update the invalid value of property \"color\". Expected format: <color>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(10).withMessage(errorMessage).next()
      .atLine(11).withMessage(errorMessage).next()
      .atLine(16).withMessage(errorMessage).noMore();
  }

  @Test
  public void counter_xxx() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/counter-xxx.css"), check);
    String errorMessageIncrement = "Update the invalid value of property \"counter-increment\". Expected format: [ <identifier> <integer>? ]+ | none";
    String errorMessageReset = "Update the invalid value of property \"counter-reset\". Expected format: [ <identifier> <integer>? ]+ | none";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(10).withMessage(errorMessageIncrement).next()
      .atLine(11).withMessage(errorMessageIncrement).next()
      .atLine(12).withMessage(errorMessageIncrement).next()
      .atLine(13).withMessage(errorMessageIncrement).next()
      .atLine(25).withMessage(errorMessageReset).next()
      .atLine(26).withMessage(errorMessageReset).next()
      .atLine(27).withMessage(errorMessageReset).next()
      .atLine(28).withMessage(errorMessageReset).noMore();
  }

  @Test
  public void cue_xxx() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/cue-xxx.css"), check);
    String errorMessageAfter = "Update the invalid value of property \"cue-after\". Expected format: <uri> | none";
    String errorMessageBefore = "Update the invalid value of property \"cue-before\". Expected format: <uri> | none";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessageAfter).next()
      .atLine(6).withMessage(errorMessageAfter).next()
      .atLine(13).withMessage(errorMessageBefore).next()
      .atLine(14).withMessage(errorMessageBefore).noMore();
  }

  @Test
  public void cue() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/cue.css"), check);
    errorMessage = "Update the invalid value of property \"cue\". Expected format: <cue-before> || <cue-after>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessage).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(11).withMessage(errorMessage).noMore();
  }

  @Test
  public void cursor() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/cursor.css"), check);
    errorMessage = "Update the invalid value of property \"cursor\". Expected format: [[ <uri> [<x> <y>]?,]* [auto | default | none | context-menu | help | pointer | progress | wait | cell | crosshair | text | vertical-text | alias | copy | move | no-drop | not-allowed | e-resize | n-resize | ne-resize | nw-resize | s-resize | se-resize | sw-resize | w-resize | ew-resize | ns-resize | nesw-resize | nwse-resize | col-resize | row-resize | all-scroll | zoom-in | zoom-out | grab | grabbing]";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(43).withMessage(errorMessage).next()
      .atLine(44).withMessage(errorMessage).next()
      .atLine(45).withMessage(errorMessage).next()
      .atLine(46).withMessage(errorMessage).next()
      .atLine(47).withMessage(errorMessage).next()
      .atLine(48).withMessage(errorMessage).next()
      .atLine(49).withMessage(errorMessage).next()
      .atLine(50).withMessage(errorMessage).next()
      .atLine(51).withMessage(errorMessage).next()
      .atLine(52).withMessage(errorMessage).next()
      .atLine(53).withMessage(errorMessage).noMore();
  }

  @Test
  public void display() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/display.css"), check);
    errorMessage = "Update the invalid value of property \"display\". Expected format: inline | block | list-item | inline-block | table | inline-table |"
      + " table-row-group | table-header-group | table-footer-group | table-row | table-column-group | table-column | table-cell |"
      + " table-caption | none | flex | inline-flex | grid | inline-grid | run-in | contents | ruby | ruby-base | ruby-text | ruby-base-container | ruby-text-container";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(26).withMessage(errorMessage).next()
      .atLine(27).withMessage(errorMessage).noMore();
  }

  @Test
  public void dominant_baseline() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/dominant-baseline.css"), check);
    errorMessage = "Update the invalid value of property \"dominant-baseline\". Expected format: auto | use-script | no-change | reset-size | alphabetic | hanging | ideographic | mathematical | central | middle | text-after-edge | text-before-edge";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(15).withMessage(errorMessage).next()
      .atLine(16).withMessage(errorMessage).next()
      .atLine(17).withMessage(errorMessage).noMore();
  }

  @Test
  public void drop_initial_value() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/drop-initial-value.css"), check);
    errorMessage = "Update the invalid value of property \"drop-initial-value\". Expected format: initial | <integer>(>=0)";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessage).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).noMore();
  }

  @Test
  public void filter() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/filter.css"), check);
    errorMessage = "Update the invalid value of property \"filter\". Expected format: none | [<uri> | <filter-function>]+";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(12).withMessage(errorMessage).next()
      .atLine(13).withMessage(errorMessage).next()
      .atLine(14).withMessage(errorMessage).next()
      .atLine(15).withMessage(errorMessage).next()
      .atLine(16).withMessage(errorMessage).next()
      .atLine(17).withMessage(errorMessage).noMore();
  }

  @Test
  public void flex_basis() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/flex-basis.css"), check);
    errorMessage = "Update the invalid value of property \"flex-basis\". Expected format: content | <width>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(10).withMessage(errorMessage).noMore();
  }

  @Test
  public void flex_direction() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/flex-direction.css"), check);
    errorMessage = "Update the invalid value of property \"flex-direction\". Expected format: row | row-reverse | column | column-reverse";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).noMore();
  }

  @Test
  public void flex_flow() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/flex-flow.css"), check);
    errorMessage = "Update the invalid value of property \"flex-flow\". Expected format: <flex-direction> || <flex-wrap>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(12).withMessage(errorMessage).next()
      .atLine(13).withMessage(errorMessage).next()
      .atLine(14).withMessage(errorMessage).next()
      .atLine(15).withMessage(errorMessage).noMore();
  }

  @Test
  public void flex_grow() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/flex-grow.css"), check);
    errorMessage = "Update the invalid value of property \"flex-grow\". Expected format: <number>(>=0)";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).noMore();
  }

  @Test
  public void flex_shrink() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/flex-shrink.css"), check);
    errorMessage = "Update the invalid value of property \"flex-shrink\". Expected format: <number>(>=0)";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).noMore();
  }

  @Test
  public void flex_wrap() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/flex-wrap.css"), check);
    errorMessage = "Update the invalid value of property \"flex-wrap\". Expected format: nowrap | wrap | wrap-reverse";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).noMore();
  }

  @Test
  public void float_property() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/float.css"), check);
    errorMessage = "Update the invalid value of property \"float\". Expected format: left | right | none";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).noMore();
  }

  @Test
  public void font_family() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/font-family.css"), check);
    errorMessage = "Update the invalid value of property \"font-family\". Expected format: [<family-name> | <generic-family>] [, <family-name>| <generic-family>]*";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).noMore();
  }

  @Test
  public void font_size() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/font-size.css"), check);
    errorMessage = "Update the invalid value of property \"font-size\". Expected format: xx-small | x-small | small | medium | large | x-large | xx-large | larger | smaller | <length>(>=0) | <percentage>(>=0)";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(17).withMessage(errorMessage).next()
      .atLine(18).withMessage(errorMessage).next()
      .atLine(19).withMessage(errorMessage).next()
      .atLine(20).withMessage(errorMessage).noMore();
  }

  @Test
  public void font_size_adjust() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/font-size-adjust.css"), check);
    errorMessage = "Update the invalid value of property \"font-size-adjust\". Expected format: none | <number>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).noMore();
  }

  @Test
  public void font_weight() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/font-weight.css"), check);
    errorMessage = "Update the invalid value of property \"font-weight\". Expected format: normal | bold | bolder | lighter | 100 | 200 | 300 | 400 | 500 | 600 | 700 | 800 | 900";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(16).withMessage(errorMessage).next()
      .atLine(17).withMessage(errorMessage).noMore();
  }

  @Test
  public void font_stretch() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/font-stretch.css"), check);
    errorMessage = "Update the invalid value of property \"font-stretch\". Expected format: normal | ultra-condensed | extra-condensed | condensed | semi-condensed | semi-expanded | expanded | extra-expanded | ultra-expanded";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(12).withMessage(errorMessage).next()
      .atLine(13).withMessage(errorMessage).next()
      .atLine(14).withMessage(errorMessage).noMore();
  }

  @Test
  public void font_style() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/font-style.css"), check);
    errorMessage = "Update the invalid value of property \"font-style\". Expected format: normal | italic | oblique";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).noMore();
  }

  @Test
  public void height() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/height.css"), check);
    errorMessage = "Update the invalid value of property \"height\". Expected format: auto | <length> | <percentage> | fill | max-content | min-content | fit-content";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void hyphens() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/hyphens.css"), check);
    errorMessage = "Update the invalid value of property \"hyphens\". Expected format: none | manual | auto";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).noMore();
  }

  @Test
  public void inline_box_align() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/inline-box-align.css"), check);
    errorMessage = "Update the invalid value of property \"inline-box-align\". Expected format: initial | last | <integer>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).noMore();
  }

  @Test
  public void justify_content() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/justify-content.css"), check);
    errorMessage = "Update the invalid value of property \"justify-content\". Expected format: flex-start | flex-end | center | space-between | space-around";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void left() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/left.css"), check);
    errorMessage = "Update the invalid value of property \"left\". Expected format: auto | <length> | <percentage>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void line_height() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/line-height.css"), check);
    errorMessage = "Update the invalid value of property \"line-height\". Expected format: normal | none | <length>(>=0) | <percentage>(>=0) | <number>(>=0)";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(10).withMessage(errorMessage).next()
      .atLine(11).withMessage(errorMessage).next()
      .atLine(12).withMessage(errorMessage).noMore();
  }

  @Test
  public void line_stacking() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/line-stacking.css"), check);
    errorMessage = "Update the invalid value of property \"line-stacking\". Expected format: <line-stacking-strategy> || <line-stacking-ruby> || <line-stacking-shift>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(10).withMessage(errorMessage).next()
      .atLine(11).withMessage(errorMessage).next()
      .atLine(12).withMessage(errorMessage).noMore();
  }

  @Test
  public void line_stacking_ruby() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/line-stacking-ruby.css"), check);
    errorMessage = "Update the invalid value of property \"line-stacking-ruby\". Expected format: exclude-ruby | include-ruby";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessage).next()
      .atLine(6).withMessage(errorMessage).noMore();
  }

  @Test
  public void line_stacking_shift() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/line-stacking-shift.css"), check);
    errorMessage = "Update the invalid value of property \"line-stacking-shift\". Expected format: consider-shifts | disregard-shifts";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessage).next()
      .atLine(6).withMessage(errorMessage).noMore();
  }

  @Test
  public void line_stacking_strategy() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/line-stacking-strategy.css"), check);
    errorMessage = "Update the invalid value of property \"line-stacking-strategy\". Expected format: inline-line-height | block-line-height | max-height | grid-height";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).noMore();
  }

  @Test
  public void list_style() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/list-style.css"), check);
    errorMessage = "Update the invalid value of property \"list-style\". Expected format: <list-style-type> || <list-style-position> || <list-style-image>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(10).withMessage(errorMessage).noMore();
  }

  @Test
  public void list_style_position() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/list-style-position.css"), check);
    errorMessage = "Update the invalid value of property \"list-style-position\". Expected format: inside | outside";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessage).next()
      .atLine(6).withMessage(errorMessage).noMore();
  }

  @Test
  public void list_style_type() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/list-style-type.css"), check);
    errorMessage = "Update the invalid value of property \"list-style-type\". Expected format: <counter-style> | <string> | none";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).noMore();
  }

  @Test
  public void list_style_image() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/list-style-image.css"), check);
    errorMessage = "Update the invalid value of property \"list-style-image\". Expected format: none | <image>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).noMore();
  }

  @Test
  public void margin() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/margin.css"), check);
    errorMessage = "Update the invalid value of property \"margin\". Expected format: <margin-width>{1,4}";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(13).withMessage(errorMessage).next()
      .atLine(14).withMessage(errorMessage).noMore();
  }

  @Test
  public void margin_xxx() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/margin-xxx.css"), check);
    String errorMessageTop = "Update the invalid value of property \"margin-top\". Expected format: auto | fill | <length> | <percentage>";
    String errorMessageRight = "Update the invalid value of property \"margin-right\". Expected format: auto | fill | <length> | <percentage>";
    String errorMessageLeft = "Update the invalid value of property \"margin-left\". Expected format: auto | fill | <length> | <percentage>";
    String errorMessageBottom = "Update the invalid value of property \"margin-bottom\". Expected format: auto | fill | <length> | <percentage>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessageTop).next()
      .atLine(9).withMessage(errorMessageTop).next()
      .atLine(19).withMessage(errorMessageRight).next()
      .atLine(20).withMessage(errorMessageRight).next()
      .atLine(30).withMessage(errorMessageLeft).next()
      .atLine(31).withMessage(errorMessageLeft).next()
      .atLine(41).withMessage(errorMessageBottom).next()
      .atLine(42).withMessage(errorMessageBottom).noMore();
  }

  @Test
  public void max_height() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/max-height.css"), check);
    errorMessage = "Update the invalid value of property \"max-height\". Expected format: auto | <length> | <percentage> | fill | max-content | min-content | fit-content";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void max_width() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/max-width.css"), check);
    errorMessage = "Update the invalid value of property \"max-width\". Expected format: auto | <length> | <percentage> | fill | max-content | min-content | fit-content";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void min_height() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/min-height.css"), check);
    errorMessage = "Update the invalid value of property \"min-height\". Expected format: auto | <length> | <percentage> | fill | max-content | min-content | fit-content";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void min_width() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/min-width.css"), check);
    errorMessage = "Update the invalid value of property \"min-width\". Expected format: auto | <length> | <percentage> | fill | max-content | min-content | fit-content";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void opacity() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/opacity.css"), check);
    errorMessage = "Update the invalid value of property \"opacity\". Expected format: <number>(0.0,1.0)";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void order() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/order.css"), check);
    errorMessage = "Update the invalid value of property \"order\". Expected format: <integer>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessage).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).noMore();
  }

  @Test
  public void outline_offset() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/outline-offset.css"), check);
    errorMessage = "Update the invalid value of property \"outline-offset\". Expected format: <length>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void outline() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/outline.css"), check);
    errorMessage = "Update the invalid value of property \"outline\". Expected format: <outline-width> || <outline-style> || <outline-color>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(10).withMessage(errorMessage).next()
      .atLine(11).withMessage(errorMessage).noMore();
  }

  @Test
  public void outline_color() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/outline-color.css"), check);
    errorMessage = "Update the invalid value of property \"outline-color\". Expected format: <color> | invert";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessage).next()
      .atLine(6).withMessage(errorMessage).noMore();
  }

  @Test
  public void outline_style() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/outline-style.css"), check);
    errorMessage = "Update the invalid value of property \"outline-style\". Expected format: none | hidden | dotted | dashed | solid | double | groove | ridge | inset | outset | auto";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(11).withMessage(errorMessage).next()
      .atLine(12).withMessage(errorMessage).noMore();
  }

  @Test
  public void outline_width() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/outline-width.css"), check);
    errorMessage = "Update the invalid value of property \"outline-width\". Expected format: thin | medium | thick | <length>(>=0)";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).noMore();
  }

  @Test
  public void overflow() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/overflow.css"), check);
    errorMessage = "Update the invalid value of property \"overflow\". Expected format: [visible | hidden | scroll | auto | no-display | no-content]{1,2}";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(13).withMessage(errorMessage).next()
      .atLine(14).withMessage(errorMessage).noMore();
  }

  @Test
  public void overflow_x() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/overflow-x.css"), check);
    errorMessage = "Update the invalid value of property \"overflow-x\". Expected format: visible | hidden | scroll | auto | no-display | no-content";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void overflow_y() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/overflow-y.css"), check);
    errorMessage = "Update the invalid value of property \"overflow-y\". Expected format: visible | hidden | scroll | auto | no-display | no-content";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void padding() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/padding.css"), check);
    errorMessage = "Update the invalid value of property \"padding\". Expected format: <padding-width>{1,4}";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(4).withMessage(errorMessage).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(12).withMessage(errorMessage).next()
      .atLine(13).withMessage(errorMessage).noMore();
  }

  @Test
  public void padding_xxx() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/padding-xxx.css"), check);
    String errorMessageTop = "Update the invalid value of property \"padding-top\". Expected format: <length>(>=0) | <percentage>(>=0)";
    String errorMessageRight = "Update the invalid value of property \"padding-right\". Expected format: <length>(>=0) | <percentage>(>=0)";
    String errorMessageLeft = "Update the invalid value of property \"padding-left\". Expected format: <length>(>=0) | <percentage>(>=0)";
    String errorMessageBottom = "Update the invalid value of property \"padding-bottom\". Expected format: <length>(>=0) | <percentage>(>=0)";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(4).withMessage(errorMessageTop).next()
      .atLine(6).withMessage(errorMessageTop).next()
      .atLine(7).withMessage(errorMessageTop).next()
      .atLine(8).withMessage(errorMessageTop).next()
      .atLine(14).withMessage(errorMessageRight).next()
      .atLine(16).withMessage(errorMessageRight).next()
      .atLine(17).withMessage(errorMessageRight).next()
      .atLine(18).withMessage(errorMessageRight).next()
      .atLine(24).withMessage(errorMessageLeft).next()
      .atLine(26).withMessage(errorMessageLeft).next()
      .atLine(27).withMessage(errorMessageLeft).next()
      .atLine(28).withMessage(errorMessageLeft).next()
      .atLine(34).withMessage(errorMessageBottom).next()
      .atLine(36).withMessage(errorMessageBottom).next()
      .atLine(37).withMessage(errorMessageBottom).next()
      .atLine(38).withMessage(errorMessageBottom).noMore();
  }

  @Test
  public void pause_xxx() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/pause-xxx.css"), check);
    String errorMessageAfter = "Update the invalid value of property \"pause-after\". Expected format: <time>(>=0) | <percentage>(>=0)";
    String errorMessageBefore = "Update the invalid value of property \"pause-before\". Expected format: <time>(>=0) | <percentage>(>=0)";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(4).withMessage(errorMessageAfter).next()
      .atLine(6).withMessage(errorMessageAfter).next()
      .atLine(7).withMessage(errorMessageAfter).next()
      .atLine(8).withMessage(errorMessageAfter).next()
      .atLine(14).withMessage(errorMessageBefore).next()
      .atLine(16).withMessage(errorMessageBefore).next()
      .atLine(17).withMessage(errorMessageBefore).next()
      .atLine(18).withMessage(errorMessageBefore).noMore();
  }

  @Test
  public void pause() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/pause.css"), check);
    errorMessage = "Update the invalid value of property \"pause\". Expected format: [ <time>(>=0) | <percentage&gt>(>=0) ]{1,2}";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(4).withMessage(errorMessage).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(10).withMessage(errorMessage).next()
      .atLine(11).withMessage(errorMessage).noMore();
  }

  @Test
  public void position() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/position.css"), check);
    errorMessage = "Update the invalid value of property \"position\". Expected format: static | relative | absolute | fixed | sticky";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void resize() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/resize.css"), check);
    errorMessage = "Update the invalid value of property \"resize\". Expected format: none | both | horizontal | vertical";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).noMore();
  }

  @Test
  public void right() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/right.css"), check);
    errorMessage = "Update the invalid value of property \"right\". Expected format: auto | <length> | <percentage>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void shape_image_threshold() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/shape-image-threshold.css"), check);
    errorMessage = "Update the invalid value of property \"shape-image-threshold\". Expected format: <number>(0.0,1.0)";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(10).withMessage(errorMessage).noMore();
  }

  @Test
  public void shape_margin() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/shape-margin.css"), check);
    errorMessage = "Update the invalid value of property \"shape-margin\". Expected format: <length> | <percentage>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(5).withMessage(errorMessage).next()
      .atLine(6).withMessage(errorMessage).noMore();
  }

  @Test
  public void shape_outside() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/shape-outside.css"), check);
    errorMessage = "Update the invalid value of property \"shape-outside\". Expected format: none | [<basic-shape> || <shape-box>] | <image>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(11).withMessage(errorMessage).next()
      .atLine(12).withMessage(errorMessage).next()
      .atLine(13).withMessage(errorMessage).next()
      .atLine(14).withMessage(errorMessage).next()
      .atLine(15).withMessage(errorMessage).next()
      .atLine(16).withMessage(errorMessage).noMore();
  }

  @Test
  public void tab_size() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/tab-size.css"), check);
    errorMessage = "Update the invalid value of property \"tab-size\". Expected format: <length>(>=0) | <integer>(>=0)";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void text_align() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/text-align.css"), check);
    errorMessage = "Update the invalid value of property \"text-align\". Expected format: start | end | left | right | center | justify | match-parent | justify-all";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(11).withMessage(errorMessage).next()
      .atLine(12).withMessage(errorMessage).noMore();
  }

  @Test
  public void text_decoration() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/text-decoration.css"), check);
    errorMessage = "Update the invalid value of property \"text-decoration\". Expected format: none | underline | overline | line-through";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).noMore();
  }

  @Test
  public void text_decoration_color() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/text-decoration-color.css"), check);
    errorMessage = "Update the invalid value of property \"text-decoration-color\". Expected format: <color>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(10).withMessage(errorMessage).next()
      .atLine(11).withMessage(errorMessage).next()
      .atLine(16).withMessage(errorMessage).noMore();
  }

  @Test
  public void text_decoration_line() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/text-decoration-line.css"), check);
    errorMessage = "Update the invalid value of property \"text-decoration-line\". Expected format: none | [ underline || overline || line-through || blink ]";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(11).withMessage(errorMessage).next()
      .atLine(12).withMessage(errorMessage).next()
      .atLine(13).withMessage(errorMessage).next()
      .atLine(14).withMessage(errorMessage).next()
      .atLine(15).withMessage(errorMessage).next()
      .atLine(16).withMessage(errorMessage).next()
      .atLine(17).withMessage(errorMessage).noMore();
  }

  @Test
  public void text_decoration_style() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/text-decoration-style.css"), check);
    errorMessage = "Update the invalid value of property \"text-decoration-style\". Expected format: solid | double | dotted | dashed | wavy";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void text_height() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/text-height.css"), check);
    errorMessage = "Update the invalid value of property \"text-height\". Expected format: auto | font-size | text-size | max-size";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).noMore();
  }

  @Test
  public void text_justify() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/text-justify.css"), check);
    errorMessage = "Update the invalid value of property \"text-justify\". Expected format: auto | none | inter-word | inter-character";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void text_overflow() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/text-overflow.css"), check);
    errorMessage = "Update the invalid value of property \"text-overflow\". Expected format: [clip | ellipsis | <string>]{1,2}";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).next()
      .atLine(10).withMessage(errorMessage).next()
      .atLine(11).withMessage(errorMessage).noMore();
  }

  @Test
  public void text_transform() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/text-transform.css"), check);
    errorMessage = "Update the invalid value of property \"text-transform\". Expected format: capitalize | uppercase | lowercase | full-width | none";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void top() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/top.css"), check);
    errorMessage = "Update the invalid value of property \"top\". Expected format: auto | <length> | <percentage>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void vertical_align() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/vertical-align.css"), check);
    errorMessage = "Update the invalid value of property \"vertical-align\". Expected format: auto | use-script | baseline | sub | super | top | text-top | central | middle | bottom | text-bottom | <percentage> | <length>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(18).withMessage(errorMessage).next()
      .atLine(19).withMessage(errorMessage).noMore();
  }

  @Test
  public void white_space() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/white-space.css"), check);
    errorMessage = "Update the invalid value of property \"white-space\". Expected format: normal | pre | nowrap | pre-wrap | pre-line";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void width() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/width.css"), check);
    errorMessage = "Update the invalid value of property \"width\". Expected format: auto | <length> | <percentage> | fill | max-content | min-content | fit-content";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(8).withMessage(errorMessage).next()
      .atLine(9).withMessage(errorMessage).noMore();
  }

  @Test
  public void z_index() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/z-index.css"), check);
    errorMessage = "Update the invalid value of property \"z-index\". Expected format: auto | <integer>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(7).withMessage(errorMessage).next()
      .atLine(8).withMessage(errorMessage).noMore();
  }

  @Test
  public void zoom() {
    SourceFile file = CssAstScanner.scanSingleFile(new File("src/test/resources/checks/properties/zoom.css"), check);
    errorMessage = "Update the invalid value of property \"zoom\". Expected format: normal | <percentage> | <number>";
    CheckMessagesVerifier.verify(file.getCheckMessages()).next()
      .atLine(6).withMessage(errorMessage).next()
      .atLine(7).withMessage(errorMessage).noMore();
  }

}
