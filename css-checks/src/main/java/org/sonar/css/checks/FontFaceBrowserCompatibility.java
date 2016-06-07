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
package org.sonar.css.checks;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.sonar.sslr.api.AstNode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.css.CssCheck;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

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
public class FontFaceBrowserCompatibility extends CssCheck {

  private static final Logger LOG = LoggerFactory.getLogger(FontFaceBrowserCompatibility.class);

  private static final String BASIC_LEVEL = "basic";
  private static final String DEEP_LEVEL = "deep";
  private static final String DEEPEST_LEVEL = "deepest";

  private static final ImmutableMap<String, ImmutableList<String>> FORMAT = ImmutableMap.of(
    BASIC_LEVEL, ImmutableList.of("woff2", "woff"),
    DEEP_LEVEL, ImmutableList.of("woff2", "woff", "ttf"),
    DEEPEST_LEVEL, ImmutableList.of("eot", "woff2", "woff", "ttf", "svg"));

  private static final Pattern EOT_PATTERN = Pattern.compile(".+\\.eot.*");

  @RuleProperty(
    key = "browserSupportLevel",
    description = "Allowed values: 'basic', 'deep', 'deepest'",
    defaultValue = "" + BASIC_LEVEL)
  private String browserSupportLevel = BASIC_LEVEL;

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
    if (DEEPEST_LEVEL.equals(browserSupportLevel)) {
      if (getSecondToLastSrcPropertyNode(declarations) == null) {
        addIssue(this, "Add an \"src\" property setting the URL for the \".eot\" font file (to support IE9 Compatibility Modes).", atRuleNode.getFirstChild(CssGrammar.AT_KEYWORD));
      } else {
        checkSecondToLastSrcProperty(getSecondToLastSrcPropertyNode(declarations));
      }
    }
    checkLastSrcProperty(getLastSrcPropertyNode(declarations));
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

  private AstNode getSecondToLastSrcPropertyNode(List<AstNode> declarationNodes) {
    AstNode srcSecondToLastPropertyNode = null;
    AstNode srcLastPropertyNode = null;
    for (AstNode declarationNode : declarationNodes) {
      if ("src".equals(declarationNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue())) {
        srcSecondToLastPropertyNode = srcLastPropertyNode;
        srcLastPropertyNode = declarationNode;
      }
    }
    return srcSecondToLastPropertyNode;
  }

  private AstNode getLastSrcPropertyNode(List<AstNode> declarationNodes) {
    AstNode srcDeclarationNode = null;
    for (AstNode declarationNode : declarationNodes) {
      if ("src".equals(declarationNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue())) {
        srcDeclarationNode = declarationNode;
      }
    }
    return srcDeclarationNode;
  }

  private void checkSecondToLastSrcProperty(AstNode srcPropertyDeclarationNode) {
    AstNode propertyNode = srcPropertyDeclarationNode.getFirstChild(CssGrammar.PROPERTY);
    AstNode valueNode = srcPropertyDeclarationNode.getFirstChild(CssGrammar.VALUE);
    if (valueNode.getChildren(CssGrammar.URI).size() != 1) {
      addIssue(
        this,
        "Define one single URL, the URL for the \".eot\" font file in this \"src\" property (to support IE9 Compatibility Modes).",
        propertyNode);
    } else {
      String font = "";
      if (valueNode.getFirstChild(CssGrammar.URI).getFirstChild(CssGrammar._URI_CONTENT).getFirstChild(CssGrammar.STRING) != null) {
        font = valueNode.getFirstChild(CssGrammar.URI).getFirstChild(CssGrammar._URI_CONTENT).getFirstChild(CssGrammar.STRING).getTokenValue();
      }
      if (!EOT_PATTERN.matcher(font).matches()) {
        addIssue(
          this,
          "Set the URL for the \".eot\" file in this \"src\" property (to support IE9 Compatibility Modes).",
          propertyNode);
      } else if (!valueNode.getChildren(CssGrammar.FUNCTION).isEmpty()) {
        addIssue(
          this,
          "Remove additional functions from this \"src\" property (to support IE9 Compatibility Modes).",
          propertyNode);
      }
    }
  }

  private void checkLastSrcProperty(AstNode srcDeclarationNode) {
    List<AstNode> urls = new ArrayList<>();
    for (AstNode uriNode : srcDeclarationNode.getFirstChild(CssGrammar.VALUE).getChildren(CssGrammar.URI)) {
      AstNode uriStringNode = uriNode.getFirstChild(CssGrammar._URI_CONTENT).getFirstChild(CssGrammar.STRING);
      if (uriStringNode != null) {
        urls.add(uriNode);
      }
    }

    if (urls.isEmpty()) {
      addIssue(this, "URL for \"" + FORMAT.get(browserSupportLevel).get(0) + "\" format is expected.", srcDeclarationNode.getFirstChild(CssGrammar.PROPERTY));
      return;
    }

    if (BASIC_LEVEL.equals(browserSupportLevel)) {
      int j = 0;
      if (EOT_PATTERN.matcher(urls.get(0).getFirstChild(CssGrammar._URI_CONTENT).getFirstChild(CssGrammar.STRING).getTokenValue()).matches()) {
        j++;
      }
      for (int i = 0; i < FORMAT.get(BASIC_LEVEL).size(); i++, j++) {
        if (j < urls.size()) {
          if (!Pattern.compile(".*\\." + FORMAT.get(BASIC_LEVEL).get(i) + ".*")
            .matcher(urls.get(j).getFirstChild(CssGrammar._URI_CONTENT).getFirstChild(CssGrammar.STRING).getTokenValue())
            .matches()) {
            addIssue(this, "URL for \"" + FORMAT.get(BASIC_LEVEL).get(i) + "\" format is expected.", urls.get(j));
            break;
          }
        } else {
          addIssue(this, "URL for \"" + FORMAT.get(BASIC_LEVEL).get(i) + "\" format is expected.", srcDeclarationNode.getFirstChild(CssGrammar.PROPERTY));
          break;
        }
      }
    } else if (DEEP_LEVEL.equals(browserSupportLevel)) {
      int j = 0;
      if (EOT_PATTERN.matcher(urls.get(0).getFirstChild(CssGrammar._URI_CONTENT).getFirstChild(CssGrammar.STRING).getTokenValue()).matches()) {
        j++;
      }
      for (int i = 0; i < FORMAT.get(DEEP_LEVEL).size(); i++, j++) {
        if (j < urls.size()) {
          if (!Pattern.compile(".*\\." + FORMAT.get(DEEP_LEVEL).get(i) + ".*")
            .matcher(urls.get(j).getFirstChild(CssGrammar._URI_CONTENT).getFirstChild(CssGrammar.STRING).getTokenValue())
            .matches()) {
            addIssue(this, "URL for \"" + FORMAT.get(DEEP_LEVEL).get(i) + "\" format is expected.", urls.get(j));
            break;
          }
        } else {
          addIssue(this, "URL for \"" + FORMAT.get(DEEP_LEVEL).get(i) + "\" format is expected.", srcDeclarationNode.getFirstChild(CssGrammar.PROPERTY));
          break;
        }
      }
    } else if (DEEPEST_LEVEL.equals(browserSupportLevel)) {
      for (int i = 0; i < FORMAT.get(DEEPEST_LEVEL).size(); i++) {
        if (i < urls.size()) {
          if (!Pattern.compile(".*\\." + FORMAT.get(DEEPEST_LEVEL).get(i) + ".*")
            .matcher(urls.get(i).getFirstChild(CssGrammar._URI_CONTENT).getFirstChild(CssGrammar.STRING).getTokenValue())
            .matches()) {
            addIssue(this, "URL for \"" + FORMAT.get(DEEPEST_LEVEL).get(i) + "\" format is expected.", urls.get(i));
            break;
          }
        } else {
          addIssue(this, "URL for \"" + FORMAT.get(DEEPEST_LEVEL).get(i) + "\" format is expected.", srcDeclarationNode.getFirstChild(CssGrammar.PROPERTY));
          break;
        }
      }
    }
  }

  private boolean isBrowserSupportLevelParameterValid() {
    return BASIC_LEVEL.equals(browserSupportLevel) || DEEP_LEVEL.equals(browserSupportLevel) || DEEPEST_LEVEL.equals(browserSupportLevel);
  }

}
