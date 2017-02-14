/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2016 David RACODON and Tamas Kende
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
package org.sonar.css.checks.common;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.checks.Tags;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.tree.css.*;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitorCheck;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;

@Rule(
  key = "case",
  name = "CSS should be written in lower case",
  priority = Priority.MINOR,
  tags = {Tags.CONVENTION})
@SqaleConstantRemediation("2min")
@ActivatedByDefault
public class CaseCheck extends DoubleDispatchVisitorCheck {

  public static final ImmutableList<String> FUNCTION_CASE_EXCEPTIONS = ImmutableList.of(
    "Alpha", "BasicImage", "BlendTrans", "Blur", "Chroma", "Compositor", "DropShadow", "Emboss", "Engrave", "FlipH",
    "FlipV", "Glow", "Gray", "ICMFilter", "Invert", "Light", "MaskFilter", "Matrix", "MotionBlur", "Redirect",
    "RevealTrans", "Shadow", "Wave", "Xray",
    "progid:DXImageTransform.Microsoft.AlphaImageLoader",
    "progid:DXImageTransform.Microsoft.Barn",
    "progid:DXImageTransform.Microsoft.BasicImage",
    "progid:DXImageTransform.Microsoft.BblendTrans",
    "progid:DXImageTransform.Microsoft.Blinds",
    "progid:DXImageTransform.Microsoft.Blur",
    "progid:DXImageTransform.Microsoft.CheckerBoard",
    "progid:DXImageTransform.Microsoft.Chroma",
    "progid:DXImageTransform.Microsoft.Compositor",
    "progid:DXImageTransform.Microsoft.DropShadow",
    "progid:DXImageTransform.Microsoft.Emboss",
    "progid:DXImageTransform.Microsoft.Engrave",
    "progid:DXImageTransform.Microsoft.Fade",
    "progid:DXImageTransform.Microsoft.FlipH",
    "progid:DXImageTransform.Microsoft.FlipV",
    "progid:DXImageTransform.Microsoft.Glow",
    "progid:DXImageTransform.Microsoft.Gradient",
    "progid:DXImageTransform.Microsoft.GradientWipe",
    "progid:DXImageTransform.Microsoft.Gray",
    "progid:DXImageTransform.Microsoft.ICMFilter",
    "progid:DXImageTransform.Microsoft.Inset",
    "progid:DXImageTransform.Microsoft.Invert",
    "progid:DXImageTransform.Microsoft.Iris",
    "progid:DXImageTransform.Microsoft.Light",
    "progid:DXImageTransform.Microsoft.MaskFilter",
    "progid:DXImageTransform.Microsoft.Matrix",
    "progid:DXImageTransform.Microsoft.Motionblur",
    "progid:DXImageTransform.Microsoft.Pixelate",
    "progid:DXImageTransform.Microsoft.RadialWipe",
    "progid:DXImageTransform.Microsoft.RandomBars",
    "progid:DXImageTransform.Microsoft.RandomDissolve",
    "progid:DXImageTransform.Microsoft.Redirect",
    "progid:DXImageTransform.Microsoft.RevealTrans",
    "progid:DXImageTransform.Microsoft.Shadow",
    "progid:DXImageTransform.Microsoft.Side",
    "progid:DXImageTransform.Microsoft.Spiral",
    "progid:DXImageTransform.Microsoft.Stretch",
    "progid:DXImageTransform.Microsoft.Strips",
    "progid:DXImageTransform.Microsoft.Wave",
    "progid:DXImageTransform.Microsoft.Wheel",
    "progid:DXImageTransform.Microsoft.Xray",
    "progid:DXImageTransform.Microsoft.Zigzag",
    "rotateX", "rotateY", "rotateZ", "scaleX", "scaleY", "scaleZ", "skewX", "skewY", "snapInterval", "snapList",
    "translateX", "translateY", "translateZ");

  @Override
  public void visitProperty(PropertyTree tree) {
    if (containsUpperCaseCharacter(tree.property().text())
      && !tree.property().isInterpolated()) {
      addIssue(tree, "property", tree.property().text());
    }
    super.visitProperty(tree);
  }

  @Override
  public void visitAtKeyword(AtKeywordTree tree) {
    if (containsUpperCaseCharacter(tree.keyword().text())) {
      addIssue(tree, "@-rule keyword", tree.keyword().text());
    }
    super.visitAtKeyword(tree);
  }

  @Override
  public void visitFunction(FunctionTree tree) {
    if (containsUpperCaseCharacter(tree.function().text())
      && !FUNCTION_CASE_EXCEPTIONS.contains(tree.function().text())) {
      addIssue(tree.function(), "function", tree.function().text());
    }
    super.visitFunction(tree);
  }

  @Override
  public void visitPseudoFunction(PseudoFunctionTree tree) {
    if (containsUpperCaseCharacter(tree.function().text())) {
      addIssue(tree.function().value(), "function", tree.function().text());
    }
    super.visitPseudoFunction(tree);
  }

  @Override
  public void visitPseudoIdentifier(PseudoIdentifierTree tree) {
    if (containsUpperCaseCharacter(tree.identifier().text())) {
      addIssue(tree.identifier(), "identifier", tree.identifier().text());
    }
    super.visitPseudoIdentifier(tree);
  }

  @Override
  public void visitTypeSelector(TypeSelectorTree tree) {
    if (containsUpperCaseCharacter(tree.identifier().text())) {
      addIssue(tree, "type selector", tree.identifier().text());
    }
    super.visitTypeSelector(tree);
  }

  @Override
  public void visitUnit(UnitTree tree) {
    if (containsUpperCaseCharacter(tree.text())) {
      addIssue(tree, "unit", tree.text());
    }
    super.visitUnit(tree);
  }

  @Override
  public void visitHash(HashTree tree) {
    if (containsUpperCaseCharacter(tree.value().text())) {
      addIssue(tree, "hexadecimal value", "#" + tree.value().text());
    }
    super.visitHash(tree);
  }

  private void addIssue(Tree tree, String treeType, String value) {
    addPreciseIssue(
      tree,
      "Write " + treeType + " \"" + value + "\" in lowercase.");
  }

  private boolean containsUpperCaseCharacter(String value) {
    return value.matches("^.*[A-Z]+.*$");
  }

}
