/*
 * SonarQube CSS / SCSS / Less Analyzer
 * Copyright (C) 2013-2017 David RACODON
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
package org.sonar.css.visitors.cpd;

import com.google.common.collect.ImmutableList;

import java.io.File;
import java.util.List;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.TextRange;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.cpd.NewCpdTokens;
import org.sonar.css.tree.impl.css.InternalSyntaxToken;
import org.sonar.plugins.css.api.tree.css.SyntaxToken;
import org.sonar.plugins.css.api.tree.Tree;
import org.sonar.plugins.css.api.visitors.SubscriptionVisitor;

public class CpdVisitor extends SubscriptionVisitor {

  private final FileSystem fileSystem;
  private final SensorContext sensorContext;
  private InputFile inputFile;
  private NewCpdTokens cpdTokens;

  public CpdVisitor(SensorContext sensorContext) {
    this.sensorContext = sensorContext;
    this.fileSystem = sensorContext.fileSystem();
  }

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(Tree.Kind.TOKEN);
  }

  @Override
  public void visitFile(Tree scriptTree) {
    File file = getContext().getFile();
    inputFile = fileSystem.inputFile(fileSystem.predicates().is(file));
    cpdTokens = sensorContext.newCpdTokens().onFile(inputFile);
  }

  @Override
  public void leaveFile(Tree scriptTree) {
    cpdTokens.save();
  }

  @Override
  public void visitNode(Tree tree) {
    if (((InternalSyntaxToken) tree).isEOF()) {
      return;
    }

    if (((InternalSyntaxToken) tree).isBOM()) {
      return;
    }

    SyntaxToken token = (SyntaxToken) tree;
    TextRange range = inputFile.newRange(token.line(), token.column(), token.endLine(), token.endColumn());
    cpdTokens.addToken(range, token.text());
  }

}
