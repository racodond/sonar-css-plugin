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
package org.sonar.css.ast.visitors;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.sonar.sslr.api.*;

import java.nio.charset.Charset;
import java.util.Map;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.source.Highlightable;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.sslr.parser.LexerlessGrammar;

public class SyntaxHighlighterVisitor extends SquidAstVisitor<LexerlessGrammar> implements AstAndTokenVisitor {

  private static final Map<AstNodeType, String> TYPES = ImmutableMap.<AstNodeType, String>builder()
    .put(CssGrammar.STRING, "s")
    .put(CssGrammar.PROPERTY, "c")
    .put(CssGrammar.VARIABLE, "c")
    .put(CssGrammar.CLASS_SELECTOR, "h")
    .put(CssGrammar.ID_SELECTOR, "h")
    .put(CssGrammar.AT_KEYWORD, "p")
    .build();

  private final SonarComponents sonarComponents;
  private final Charset charset;

  private Highlightable.HighlightingBuilder highlighting;
  private SourceFileOffsets offsets;

  public SyntaxHighlighterVisitor(SonarComponents sonarComponents, Charset charset) {
    this.sonarComponents = Preconditions.checkNotNull(sonarComponents);
    this.charset = charset;
  }

  @Override
  public void init() {
    for (AstNodeType type : TYPES.keySet()) {
      subscribeTo(type);
    }
  }

  @Override
  public void visitFile(AstNode astNode) {
    if (astNode == null) {
      // parse error
      return;
    }
    InputFile inputFile = sonarComponents.inputFileFor(getContext().getFile());
    inputFile = Preconditions.checkNotNull(inputFile);
    highlighting = sonarComponents.highlightableFor(inputFile).newHighlighting();
    offsets = new SourceFileOffsets(getContext().getFile(), charset);
  }

  @Override
  public void visitNode(AstNode astNode) {
    highlighting.highlight(
        offsets.startOffset(astNode),
        offsets.endOffset(astNode),
        TYPES.get(astNode.getType())
    );
  }

  @Override
  public void visitToken(Token token) {
    for (Trivia trivia : token.getTrivia()) {
      if (trivia.isComment()) {
        highlighting.highlight(
            offsets.startOffset(trivia.getToken()),
            offsets.endOffset(trivia.getToken()),
            "cppd"
        );
      }
    }
  }

  @Override
  public void leaveFile(AstNode astNode) {
    if (astNode == null) {
      // parse error
      return;
    }
    highlighting.done();
  }

}
