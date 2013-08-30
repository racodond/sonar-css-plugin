package org.sonar.css.metrics;

import org.sonar.api.measures.CoreMetrics;

import org.sonar.css.api.CssMetric;

import org.sonar.api.measures.FileLinesContext;

import org.sonar.api.resources.File;

import com.sonar.sslr.api.AstNode;

import com.sonar.sslr.api.Trivia;

import com.sonar.sslr.api.GenericTokenType;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

import org.sonar.api.measures.FileLinesContextFactory;

import org.sonar.api.resources.Project;

import org.sonar.sslr.parser.LexerlessGrammar;

import com.sonar.sslr.api.Token;

import com.sonar.sslr.api.AstAndTokenVisitor;
import com.sonar.sslr.squid.SquidAstVisitor;

public class FileLinesVisitor extends SquidAstVisitor<LexerlessGrammar> implements AstAndTokenVisitor {

  private final Project project;
  private final FileLinesContextFactory fileLinesContextFactory;

  private final Set<Integer> linesOfCode = Sets.newHashSet();
  private final Set<Integer> linesOfComments = Sets.newHashSet();

  public FileLinesVisitor(Project project, FileLinesContextFactory fileLinesContextFactory) {
    this.project = project;
    this.fileLinesContextFactory = fileLinesContextFactory;
  }

  public void visitToken(Token token) {
    if (token.getType().equals(GenericTokenType.EOF)) {
      return;
    }

    linesOfCode.add(token.getLine());
    List<Trivia> trivias = token.getTrivia();
    for (Trivia trivia : trivias) {
      if (trivia.isComment()) {
        linesOfComments.add(trivia.getToken().getLine());
      }
    }
  }

  @Override
  public void leaveFile(AstNode astNode) {
    File sonarFile = File.fromIOFile(getContext().getFile(), project);
    FileLinesContext fileLinesContext = fileLinesContextFactory.createFor(sonarFile);

    int fileLength = getContext().peekSourceCode().getInt(CssMetric.LINES);
    for (int line = 1; line <= fileLength; line++) {
      fileLinesContext.setIntValue(CoreMetrics.NCLOC_DATA_KEY, line, linesOfCode.contains(line) ? 1 : 0);
      fileLinesContext.setIntValue(CoreMetrics.COMMENT_LINES_DATA_KEY, line, linesOfComments.contains(line) ? 1 : 0);
    }
    fileLinesContext.save();

    linesOfCode.clear();
    linesOfComments.clear();
  }

}
