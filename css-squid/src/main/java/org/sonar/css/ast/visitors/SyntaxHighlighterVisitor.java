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
package org.sonar.css.ast.visitors;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.sonar.sslr.api.AstAndTokenVisitor;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.AstNodeType;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.Trivia;
import com.sonar.sslr.squid.SquidAstVisitor;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.resources.File;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.source.Highlightable;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squid.api.SourceCode;
import org.sonar.squid.api.SourceFile;
import org.sonar.sslr.parser.LexerlessGrammar;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class SyntaxHighlighterVisitor extends SquidAstVisitor<LexerlessGrammar> implements AstAndTokenVisitor {

  private final ResourcePerspectives perspectives;
  private final Map<AstNodeType, String> types;
  private final Charset charset;

  private Highlightable.HighlightingBuilder highlighting;
  private List<Integer> lineStart;
  private Project project;

  public SyntaxHighlighterVisitor(ResourcePerspectives perspectives, Project project,  Charset charset) {
    this.charset = charset;
    this.perspectives = perspectives;
    this.project = project;

    ImmutableMap.Builder<AstNodeType, String> typesBuilder = ImmutableMap.builder();
    typesBuilder.put(CssGrammar.string, "s");
    typesBuilder.put(CssGrammar.value, "s");
    typesBuilder.put(CssGrammar.property, "k");
    typesBuilder.put(CssGrammar.selector, "k");
    types = typesBuilder.build();
  }

  @Override
  public void init() {
    for (AstNodeType type : types.keySet()) {
      subscribeTo(type);
    }
  }

  @Override
  public void visitFile(AstNode astNode) {
    if (astNode == null) {
      // parse error
      return;
    }

    Resource<?> sonarFile = File.fromIOFile(new java.io.File(peekSourceFile().getKey()), project);
    highlighting = perspectives.as(Highlightable.class, sonarFile).newHighlighting();

    lineStart = Lists.newArrayList();
    final String content;
    try {
      content = Files.toString(getContext().getFile(), charset);
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
    lineStart.add(0);
    for (int i = 0; i < content.length(); i++) {
      if (content.charAt(i) == '\n' || (content.charAt(i) == '\r' && i + 1 < content.length() && content.charAt(i + 1) != '\n')) {
        lineStart.add(i + 1);
      }
    }
  }

  @Override
  public void visitNode(AstNode astNode) {
    highlighting.highlight(astNode.getFromIndex(), astNode.getToIndex(), types.get(astNode.getType()));
  }

  public void visitToken(Token token) {
    for (Trivia trivia : token.getTrivia()) {
      if (trivia.isComment()) {
        Token triviaToken = trivia.getToken();
        int offset = getOffset(triviaToken.getLine(), triviaToken.getColumn());
        highlighting.highlight(offset, offset + triviaToken.getValue().length(), "cppd");
      }
    }
  }

  /**
   * @param line starts from 1
   * @param column starts from 0
   */
  private int getOffset(int line, int column) {
    return lineStart.get(line - 1) + column;
  }

  @Override
  public void leaveFile(AstNode astNode) {
    if (astNode == null) {
      // parse error
      return;
    }

    highlighting.done();
  }

  private final SourceFile peekSourceFile() {
    SourceCode sourceCode = getContext().peekSourceCode();
    if (sourceCode.isType(SourceFile.class)) {
      return (SourceFile) getContext().peekSourceCode();
    }
    return sourceCode.getParent(SourceFile.class);
  }

}
