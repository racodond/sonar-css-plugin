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
package org.sonar.css.tree.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

import org.sonar.plugins.css.api.tree.*;
import org.sonar.plugins.css.api.visitors.DoubleDispatchVisitor;

public class DeclarationsTreeImpl extends CssTree implements DeclarationsTree {

  private final SyntaxList<DeclarationTree> declarationSyntaxList;
  private final List<DeclarationTree> allDeclarations;
  private final List<VariableDeclarationTree> variableDeclarations;
  private final List<PropertyDeclarationTree> propertyDeclarations;
  private final List<SyntaxToken> emptyDeclarations;
  private final List<Tree> allTrees;

  public DeclarationsTreeImpl(@Nullable SyntaxList<DeclarationTree> declarationSyntaxList) {
    this.declarationSyntaxList = declarationSyntaxList;
    this.allDeclarations = buildDeclarationList();
    this.variableDeclarations = buildVariableDeclarationList();
    this.propertyDeclarations = buildPropertyDeclarationList();
    this.emptyDeclarations = buildEmptyDeclarationList();
    this.allTrees = buildAllTreeList();
  }

  @Override
  public Kind getKind() {
    return Kind.DECLARATIONS;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return allTrees.iterator();
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitDeclarations(this);
  }

  @Override
  public List<DeclarationTree> allDeclarations() {
    return allDeclarations;
  }

  @Override
  public List<VariableDeclarationTree> variableDeclarations() {
    return variableDeclarations;
  }

  @Override
  public List<PropertyDeclarationTree> propertyDeclarations() {
    return propertyDeclarations;
  }

  @Override
  public List<SyntaxToken> emptyDeclarations() {
    return emptyDeclarations;
  }

  @Override
  @Nullable
  public SyntaxList<DeclarationTree> declarationSyntaxList() {
    return declarationSyntaxList;
  }

  private List<VariableDeclarationTree> buildVariableDeclarationList() {
    return allDeclarations.stream()
      .filter(tree -> tree instanceof VariableDeclarationTree)
      .map(tree -> (VariableDeclarationTree) tree)
      .collect(Collectors.toList());
  }

  private List<PropertyDeclarationTree> buildPropertyDeclarationList() {
    return allDeclarations.stream().filter(tree -> tree instanceof PropertyDeclarationTree)
      .map(tree -> (PropertyDeclarationTree) tree)
      .collect(Collectors.toList());
  }

  private List<Tree> buildAllTreeList() {
    List<Tree> all = new ArrayList<>();

    if (declarationSyntaxList != null) {
      if (declarationSyntaxList.element() != null) {
        all.add(declarationSyntaxList.element());
      }
      if (declarationSyntaxList.separatorToken() != null) {
        all.add(declarationSyntaxList.separatorToken());
      }

      SyntaxList<Tree> nextSyntaxList = declarationSyntaxList.next();
      while (nextSyntaxList != null) {
        all.add(nextSyntaxList.element());
        if (nextSyntaxList.separatorToken() != null) {
          all.add(nextSyntaxList.separatorToken());
        }
        nextSyntaxList = nextSyntaxList.next();
      }
    }

    return all;
  }

  private List<DeclarationTree> buildDeclarationList() {
    List<DeclarationTree> declarationList = new ArrayList<>();

    if (declarationSyntaxList != null) {

      if (declarationSyntaxList.element() != null) {
        declarationList.add(declarationSyntaxList.element());
      }

      SyntaxList<DeclarationTree> nextSyntaxList = declarationSyntaxList.next();
      while (nextSyntaxList != null) {
        if (nextSyntaxList.element() != null) {
          declarationList.add(nextSyntaxList.element());
        }
        nextSyntaxList = nextSyntaxList.next();
      }
    }

    return declarationList;
  }

  private List<SyntaxToken> buildEmptyDeclarationList() {
    List<SyntaxToken> emptyDeclarationList = new ArrayList<>();

    if (declarationSyntaxList != null) {

      if (declarationSyntaxList.element() == null) {
        emptyDeclarationList.add(declarationSyntaxList.separatorToken());
      }

      SyntaxList<DeclarationTree> nextSyntaxList = declarationSyntaxList.next();
      while (nextSyntaxList != null) {
        if (nextSyntaxList.element() == null) {
          emptyDeclarationList.add(nextSyntaxList.separatorToken());
        }
        nextSyntaxList = nextSyntaxList.next();
      }
    }

    return emptyDeclarationList;
  }

}
