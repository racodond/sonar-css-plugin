/*
 * SonarQube CSS Plugin
 * Copyright (C) 2013 Tamas Kende and David RACODON
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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.AstNode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

/*
 * https://css-tricks.com/snippets/css/using-font-face/
 * http://blog.fontspring.com/2011/02/further-hardening-of-the-bulletproof-syntax/
 */
@Rule(
  key = "font-face-browser-compatibility",
  name = "\"@font-face\" rule should be made compatible with the required browsers",
  priority = Priority.MAJOR,
  tags = {Tags.BROWSER_COMPATIBILITY})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ARCHITECTURE_RELIABILITY)
@SqaleConstantRemediation("20min")
public class FontFaceBrowserCompatibility extends SquidCheck<LexerlessGrammar> {

  private static final String DEFAULT_FORMAT = "basic";

  private static final Logger LOG = LoggerFactory.getLogger(FontFaceBrowserCompatibility.class);

  private static final List<String> BASIC_FORMAT = ImmutableList.of("woff2", "woff");
  private static final List<String> DEEP_FORMAT = ImmutableList.of("woff2", "woff", "ttf");
  private static final List<String> DEEPEST_FORMAT = ImmutableList.of("eot", "woff2", "woff", "ttf", "svg");

  private static final Pattern EOT_PATTERN = Pattern.compile(".+\\.eot.*");

  @RuleProperty(
    key = "browserSupportLevel",
    description = "Allowed values: 'basic', 'deep', 'deepest'",
    defaultValue = "" + DEFAULT_FORMAT)
  private String browserSupportLevel = DEFAULT_FORMAT;

  @Override
  public void init() {
    if (isBrowserSupportLevelParameterValid()) {
      subscribeTo(CssGrammar.AT_RULE);
    } else {
      LOG.error("Rule css:font-face-browser-compatibility: browserSupportLevel parameter value is not valid.\nActual: '" + browserSupportLevel
        + "'\nExpected: 'basic' or 'deep' or 'deepest'\nNo check will be performed against this css:font-face-browser-compatibility rule.");
    }
  }

  @Override
  public void visitNode(AstNode atRuleNode) {
    if (!isFontFaceRuleToBeChecked(atRuleNode)) {
      return;
    }
    List<AstNode> declarations = atRuleNode.getFirstDescendant(CssGrammar.atRuleBlock).getFirstChild(CssGrammar.SUP_DECLARATION).getChildren(CssGrammar.DECLARATION);
    if ("deepest".equals(browserSupportLevel)) {
      if (getSecondToLastSrcPropertyValue(declarations) == null) {
        getContext().createLineViolation(this, "Add an \"src\" property setting the URL for the \".eot\" font file (to support IE9 Compatibility Modes).", atRuleNode);
      } else {
        checkSecondToLastSrcProperty(getSecondToLastSrcPropertyValue(declarations));
      }
    }
    checkLastSrcProperty(getLastSrcPropertyValue(declarations));
  }

  @VisibleForTesting
  public void setBrowserSupportLevel(String browserSupportLevel) {
    this.browserSupportLevel = browserSupportLevel;
  }

  private boolean isFontFaceRuleToBeChecked(AstNode atRuleNode) {
    return "font-face".equals(atRuleNode.getFirstChild(CssGrammar.AT_KEYWORD).getFirstChild(CssGrammar.IDENT).getTokenValue())
      && atRuleNode.getFirstDescendant(CssGrammar.atRuleBlock).getFirstChild(CssGrammar.SUP_DECLARATION) != null
      && doesSrcPropertiesContainUrl(atRuleNode.getFirstDescendant(CssGrammar.atRuleBlock).getFirstChild(CssGrammar.SUP_DECLARATION).getChildren(CssGrammar.DECLARATION));
  }

  private boolean doesSrcPropertiesContainUrl(List<AstNode> declarationNodes) {
    for (AstNode declarationNode : declarationNodes) {
      if ("src".equals(declarationNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue())
        && !declarationNode.getFirstChild(CssGrammar.VALUE).getChildren(CssGrammar.URI).isEmpty()) {
        return true;
      }
    }
    return false;
  }

  private AstNode getSecondToLastSrcPropertyValue(List<AstNode> declarationNodes) {
    AstNode srcSecondToLastPropertyValueNode = null;
    AstNode srcLastPropertyValueNode = null;
    for (AstNode declarationNode : declarationNodes) {
      if ("src".equals(declarationNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue())) {
        srcSecondToLastPropertyValueNode = srcLastPropertyValueNode;
        srcLastPropertyValueNode = declarationNode.getFirstChild(CssGrammar.VALUE);
      }
    }
    return srcSecondToLastPropertyValueNode;
  }

  private AstNode getLastSrcPropertyValue(List<AstNode> declarationNodes) {
    AstNode srcPropertyValueNode = null;
    for (AstNode declarationNode : declarationNodes) {
      if ("src".equals(declarationNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue())) {
        srcPropertyValueNode = declarationNode.getFirstChild(CssGrammar.VALUE);
      }
    }
    return srcPropertyValueNode;
  }

  private void checkSecondToLastSrcProperty(@Nonnull AstNode srcValueNode) {
    if (srcValueNode.getChildren(CssGrammar.URI).size() != 1) {
      getContext().createLineViolation(this, "Define one single URL, the URL for the \".eot\" font file in this \"src\" property (to support IE9 Compatibility Modes).",
        srcValueNode);
    } else {
      String font = srcValueNode.getFirstChild(CssGrammar.URI).getFirstChild(CssGrammar._URI_CONTENT).getFirstChild(CssGrammar.STRING).getTokenValue();
      if (!EOT_PATTERN.matcher(font).matches()) {
        getContext().createLineViolation(this, "Set the URL for the \".eot\" file in this \"src\" property (to support IE9 Compatibility Modes).",
          srcValueNode.getFirstChild(CssGrammar.URI).getFirstChild(CssGrammar._URI_CONTENT).getFirstChild(CssGrammar.STRING));
      } else if (!srcValueNode.getChildren(CssGrammar.FUNCTION).isEmpty()) {
        getContext().createLineViolation(this, "Remove additional functions from this \"src\" property (to support IE9 Compatibility Modes).",
          srcValueNode.getFirstChild(CssGrammar.FUNCTION));
      }
    }
  }

  private void checkLastSrcProperty(@Nonnull AstNode srcValueNode) {
    List<AstNode> urls = new ArrayList<>();
    for (AstNode uriNode : srcValueNode.getChildren(CssGrammar.URI)) {
      AstNode uriStringNode = uriNode.getFirstChild(CssGrammar._URI_CONTENT).getFirstChild(CssGrammar.STRING);
      if (uriStringNode != null) {
        urls.add(uriNode);
      }
    }

    if (urls.isEmpty()) {
      addIssueLastSrcPropertyDoesNotContainUrl(srcValueNode);
      return;
    }

    if ("basic".equals(browserSupportLevel)) {
      int j = 0;
      if (EOT_PATTERN.matcher(urls.get(0).getFirstChild(CssGrammar._URI_CONTENT).getFirstChild(CssGrammar.STRING).getTokenValue()).matches()) {
        j++;
      }
      for (int i = 0; i < BASIC_FORMAT.size(); i++, j++) {
        if (j < urls.size()) {
          if (!Pattern.compile(".*\\." + BASIC_FORMAT.get(i) + ".*").matcher(urls.get(j).getFirstChild(CssGrammar._URI_CONTENT).getFirstChild(CssGrammar.STRING).getTokenValue())
            .matches()) {
            getContext().createLineViolation(this, "URL for \"" + BASIC_FORMAT.get(i) + "\" format is expected.", urls.get(j));
            break;
          }
        } else {
          getContext().createLineViolation(this, "URL for \"" + BASIC_FORMAT.get(i) + "\" format is expected.", srcValueNode);
          break;
        }
      }
    } else if ("deep".equals(browserSupportLevel)) {
      int j = 0;
      if (EOT_PATTERN.matcher(urls.get(0).getFirstChild(CssGrammar._URI_CONTENT).getFirstChild(CssGrammar.STRING).getTokenValue()).matches()) {
        j++;
      }
      for (int i = 0; i < DEEP_FORMAT.size(); i++, j++) {
        if (j < urls.size()) {
          if (!Pattern.compile(".*\\." + DEEP_FORMAT.get(i) + ".*").matcher(urls.get(j).getFirstChild(CssGrammar._URI_CONTENT).getFirstChild(CssGrammar.STRING).getTokenValue())
            .matches()) {
            getContext().createLineViolation(this, "URL for \"" + DEEP_FORMAT.get(i) + "\" format is expected.", urls.get(j));
            break;
          }
        } else {
          getContext().createLineViolation(this, "URL for \"" + DEEP_FORMAT.get(i) + "\" format is expected.", srcValueNode);
          break;
        }
      }
    } else if ("deepest".equals(browserSupportLevel)) {
      for (int i = 0; i < DEEPEST_FORMAT.size(); i++) {
        if (i < urls.size()) {
          if (!Pattern.compile(".*\\." + DEEPEST_FORMAT.get(i) + ".*").matcher(urls.get(i).getFirstChild(CssGrammar._URI_CONTENT).getFirstChild(CssGrammar.STRING).getTokenValue())
            .matches()) {
            getContext().createLineViolation(this, "URL for \"" + DEEPEST_FORMAT.get(i) + "\" format is expected.", urls.get(i));
            break;
          }
        } else {
          getContext().createLineViolation(this, "URL for \"" + DEEPEST_FORMAT.get(i) + "\" format is expected.", srcValueNode);
          break;
        }
      }
    }
  }

  private void addIssueLastSrcPropertyDoesNotContainUrl(@Nonnull AstNode srcValueNode) {
    if ("basic".equals(browserSupportLevel)) {
      getContext().createLineViolation(this, "URL for \"" + BASIC_FORMAT.get(0) + "\" format is expected.", srcValueNode);
    } else if ("deep".equals(browserSupportLevel)) {
      getContext().createLineViolation(this, "URL for \"" + DEEP_FORMAT.get(0) + "\" format is expected.", srcValueNode);
    } else if ("deepest".equals(browserSupportLevel)) {
      getContext().createLineViolation(this, "URL for \"" + DEEPEST_FORMAT.get(0) + "\" format is expected.", srcValueNode);
    }
  }

  private boolean isBrowserSupportLevelParameterValid() {
    return "basic".equals(browserSupportLevel) || "deep".equals(browserSupportLevel) || "deepest".equals(browserSupportLevel);
  }

}
