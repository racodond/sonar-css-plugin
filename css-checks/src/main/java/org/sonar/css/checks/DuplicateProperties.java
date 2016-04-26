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

import com.google.common.collect.Lists;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.Token;

import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.sslr.parser.LexerlessGrammar;

@Rule(
  key = "duplicate-properties",
  name = "Duplicated properties should be removed",
  priority = Priority.MAJOR,
  tags = {Tags.PITFALL})
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.LOGIC_RELIABILITY)
@SqaleConstantRemediation("10min")
@ActivatedByDefault
public class DuplicateProperties extends SquidCheck<LexerlessGrammar> {

  List<Declaration> declarations = Lists.newArrayList();

  @Override
  public void init() {
    subscribeTo(CssGrammar.SUP_DECLARATION);
  }

  @Override
  public void visitNode(AstNode supDeclarationNode) {
    declarations = getAllDeclarations(supDeclarationNode);
    Iterator<Declaration> iterator = declarations.iterator();
    while (iterator.hasNext()) {
      Declaration currentDeclaration = iterator.next();
      iterator.remove();
      if (existSamePropertyWithSameValue(currentDeclaration)
        || existSamePropertyWithDifferentValue(currentDeclaration)) {
        getContext().createLineViolation(this, "Remove this duplicated property: \"" + currentDeclaration.getProperty() + "\".", currentDeclaration.getDeclarationNode());
      }
    }
  }

  private boolean existSamePropertyWithSameValue(@Nonnull Declaration currentDeclaration) {
    for (Declaration declaration : declarations) {
      if (declaration.getProperty().equals(currentDeclaration.getProperty())
        && declaration.getValue().equals(currentDeclaration.getValue())) {
        return true;
      }
    }
    return false;
  }

  private boolean existSamePropertyWithDifferentValue(@Nonnull Declaration currentDeclaration) {
    for (Declaration declaration : declarations) {
      if (declaration.getProperty().equals(currentDeclaration.getProperty())
        && !declaration.getValue().equals(currentDeclaration.getValue())
        && !beforeSameProperty(currentDeclaration)) {
        return true;
      }
    }
    return false;
  }

  private boolean beforeSameProperty(@Nonnull Declaration currentDeclaration) {
    if (!declarations.isEmpty()) {
      return currentDeclaration.getProperty().equals(declarations.get(0).getProperty());
    } else {
      return false;
    }
  }

  private List<Declaration> getAllDeclarations(AstNode supDeclarationNode) {
    List<AstNode> declarationNodes = supDeclarationNode.getChildren(CssGrammar.DECLARATION);
    List<Declaration> allDeclarations = Lists.newArrayList();
    for (AstNode declarationNode : declarationNodes) {
      allDeclarations.add(new Declaration(declarationNode));
    }
    return allDeclarations;
  }

  private static class Declaration {
    private AstNode declarationNode;
    private String property;
    private String value;

    public Declaration(AstNode declarationNode) {
      this.declarationNode = declarationNode;
      property = declarationNode.getFirstChild(CssGrammar.PROPERTY).getTokenValue().toLowerCase();
      StringBuilder b = new StringBuilder();
      for (Token token : declarationNode.getFirstChild(CssGrammar.VALUE).getTokens()) {
        b.append(token.getValue());
      }
      value = b.toString().toLowerCase();
    }

    public String getProperty() {
      return property;
    }

    public String getValue() {
      return value;
    }

    public AstNode getDeclarationNode() {
      return declarationNode;
    }
  }

}
