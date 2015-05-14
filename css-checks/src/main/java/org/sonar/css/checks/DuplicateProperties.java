/*
 * Sonar CSS Plugin
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

import com.google.common.collect.Lists;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Token;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Cardinality;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.parser.CssGrammar;
import org.sonar.sslr.parser.LexerlessGrammar;

import java.util.List;

/**
 * https://github.com/stubbornella/csslint/wiki/Disallow-duplicate-properties
 *
 * @author tkende
 */
@Rule(
  key = "duplicate-properties",
  name = "Duplicated properties should be removed",
  priority = Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.LOGIC_RELIABILITY)
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class DuplicateProperties extends SquidCheck<LexerlessGrammar> {

  @Override
  public void init() {
    subscribeTo(CssGrammar.SUP_DECLARATION, CssGrammar.DECLARATION);
  }

  List<Declarations> declarations = Lists.newArrayList();

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.getType().equals(CssGrammar.SUP_DECLARATION)) {
      declarations.clear();
    } else {
      String property = astNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue();
      String value = getTokensAsString(astNode.getFirstChild(CssGrammar.VALUE).getTokens());
      List<Declarations> storedDeclarations = findStoredDeclarations(property);
      if (!storedDeclarations.isEmpty() && (hasSameValue(storedDeclarations, value)
        || notAfter(storedDeclarations, astNode.getPreviousSibling().getPreviousSibling()))) {
        getContext().createLineViolation(this, "Duplicated property in the declarations", astNode);
      }
      declarations.add(new Declarations(property, value, astNode));
    }
  }

  private boolean notAfter(List<Declarations> storedDeclarations, AstNode astNode) {
    for (Declarations declaration : storedDeclarations) {
      if (declaration.getNode().equals(astNode)) {
        return false;
      }
    }
    return true;
  }

  private boolean hasSameValue(List<Declarations> storedDeclarations, String value) {
    for (Declarations declaration : storedDeclarations) {
      if (declaration.getValue().equals(value)) {
        return true;
      }
    }
    return false;
  }

  private String getTokensAsString(List<Token> tokens) {
    StringBuilder b = new StringBuilder();
    for (Token token : tokens) {
      b.append(token.getValue());
    }
    return b.toString();
  }

  private List<Declarations> findStoredDeclarations(String property) {
    List<Declarations> ret = Lists.newArrayList();
    for (Declarations decl : declarations) {
      if (decl.getProperty().equals(property)) {
        ret.add(decl);
      }
    }
    return ret;
  }

  private static class Declarations {
    String property;
    String value;
    private AstNode node;

    public Declarations(String property, String value, AstNode node) {
      this.property = property.trim();
      this.value = value.trim();
      this.node = node;
    }

    public String getProperty() {
      return property;
    }

    public String getValue() {
      return value;
    }

    public AstNode getNode() {
      return node;
    }
  }

}
