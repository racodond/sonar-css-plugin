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
package org.sonar.css;

import com.sonar.sslr.impl.Parser;
import com.sonar.sslr.squid.AstScanner;
import com.sonar.sslr.squid.SquidAstVisitor;
import com.sonar.sslr.squid.SquidAstVisitorContextImpl;
import com.sonar.sslr.squid.metrics.CommentsVisitor;
import com.sonar.sslr.squid.metrics.CounterVisitor;
import com.sonar.sslr.squid.metrics.LinesOfCodeVisitor;
import com.sonar.sslr.squid.metrics.LinesVisitor;
import org.sonar.api.component.ResourcePerspectives;
import org.sonar.api.scan.filesystem.ModuleFileSystem;
import org.sonar.css.api.CssMetric;
import org.sonar.css.ast.visitors.SyntaxHighlighterVisitor;
import org.sonar.css.parser.CssGrammar;
import org.sonar.squid.api.SourceProject;
import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.parser.ParserAdapter;

public final class CssAstScanner {

  private CssAstScanner() {
  }

  public static AstScanner<LexerlessGrammar> create(ModuleFileSystem fileSystem, SquidAstVisitor<LexerlessGrammar>... visitors) {
    return create(fileSystem, null, visitors);
  }

  public static AstScanner<LexerlessGrammar> create(ModuleFileSystem fileSystem, ResourcePerspectives resourcePerspectives,
    SquidAstVisitor<LexerlessGrammar>... visitors) {
    final CssConfiguration conf = new CssConfiguration(fileSystem.sourceCharset());
    final SquidAstVisitorContextImpl<LexerlessGrammar> context = new SquidAstVisitorContextImpl<LexerlessGrammar>(new SourceProject("Css Project"));
    final Parser<LexerlessGrammar> parser = new ParserAdapter<LexerlessGrammar>(fileSystem.sourceCharset(), CssGrammar.createGrammar());

    AstScanner.Builder<LexerlessGrammar> builder = AstScanner.<LexerlessGrammar> builder(context).setBaseParser(parser);

    /* Metrics */
    builder.withMetrics(CssMetric.values());

    /* Comments */
    builder.setCommentAnalyser(new CssCommentAnalyser());

    builder.withSquidAstVisitor(CommentsVisitor.<LexerlessGrammar> builder().withCommentMetric(
      CssMetric.COMMENT_LINES)
      .withNoSonar(true)
      .withIgnoreHeaderComment(conf.getIgnoreHeaderComments()).build());

    /* Files */
    builder.setFilesMetric(CssMetric.FILES);

    /*
     * Statements not in CSS syntax term
     * selectors and declarations at-keywords
     */
    builder.withSquidAstVisitor(CounterVisitor.<LexerlessGrammar> builder()
      .setMetricDef(CssMetric.STATEMENTS)
      .subscribeTo(CssGrammar.atkeyword, CssGrammar.selector, CssGrammar.declaration)
      .build());

    /* Rule sets */
    builder.withSquidAstVisitor(CounterVisitor.<LexerlessGrammar> builder()
      .setMetricDef(CssMetric.RULE_SETS)
      .subscribeTo(CssGrammar.ruleset)
      .build());

    /* At rules */
    builder.withSquidAstVisitor(CounterVisitor.<LexerlessGrammar> builder()
      .setMetricDef(CssMetric.AT_RULES)
      .subscribeTo(CssGrammar.atRule)
      .build());

    /* Metrics */
    builder.withSquidAstVisitor(new LinesVisitor<LexerlessGrammar>(CssMetric.LINES));
    builder.withSquidAstVisitor(new LinesOfCodeVisitor<LexerlessGrammar>(CssMetric.LINES_OF_CODE));


    /* Syntax highlighter */
    if (resourcePerspectives != null && fileSystem != null) {
      builder.withSquidAstVisitor(new SyntaxHighlighterVisitor(resourcePerspectives, fileSystem));
    }

    /* External visitors (typically Check ones) */
    for (SquidAstVisitor<LexerlessGrammar> visitor : visitors) {
      builder.withSquidAstVisitor(visitor);
    }

    return builder.build();
  }
}
