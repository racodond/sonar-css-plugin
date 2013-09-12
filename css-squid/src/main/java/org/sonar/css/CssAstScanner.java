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

import org.sonar.api.component.ResourcePerspectives;

import org.sonar.css.ast.visitors.SyntaxHighlighterVisitor;
import com.google.common.base.Charsets;
import com.sonar.sslr.impl.Parser;
import com.sonar.sslr.squid.AstScanner;
import com.sonar.sslr.squid.SquidAstVisitor;
import com.sonar.sslr.squid.SquidAstVisitorContextImpl;
import com.sonar.sslr.squid.metrics.CommentsVisitor;
import com.sonar.sslr.squid.metrics.CounterVisitor;
import com.sonar.sslr.squid.metrics.LinesOfCodeVisitor;
import com.sonar.sslr.squid.metrics.LinesVisitor;
import org.sonar.css.api.CssMetric;
import org.sonar.css.parser.CssGrammar;
import org.sonar.css.parser.CssParser;
import org.sonar.squid.api.SourceCode;
import org.sonar.squid.api.SourceFile;
import org.sonar.squid.api.SourceProject;
import org.sonar.squid.indexer.QueryByType;
import org.sonar.sslr.parser.LexerlessGrammar;

import java.io.File;
import java.util.Collection;

public final class CssAstScanner {

  private CssAstScanner() {
  }

  /**
  * Helper method for testing checks without having to deploy them on a Sonar instance.
  */
  public static SourceFile scanSingleFile(File file, SquidAstVisitor<LexerlessGrammar>... visitors) {
    if (!file.isFile()) {
      throw new IllegalArgumentException("File '" + file + "' not found.");
    }
    AstScanner<LexerlessGrammar> scanner = create(new CssConfiguration(Charsets.UTF_8), visitors);
    scanner.scanFile(file);
    Collection<SourceCode> sources = scanner.getIndex().search(new QueryByType(SourceFile.class));
    if (sources.size() != 1) {
      throw new IllegalStateException("Only one SourceFile was expected whereas " + sources.size() + " has been returned.");
    }
    return (SourceFile) sources.iterator().next();
  }

  public static AstScanner<LexerlessGrammar> create(CssConfiguration conf, SquidAstVisitor<LexerlessGrammar>... visitors) {
    return create(conf, null, visitors);
  }

  public static AstScanner<LexerlessGrammar> create(CssConfiguration conf, ResourcePerspectives resourcePerspectives, SquidAstVisitor<LexerlessGrammar>... visitors) {
    final SquidAstVisitorContextImpl<LexerlessGrammar> context = new SquidAstVisitorContextImpl<LexerlessGrammar>(new SourceProject("Css Project"));
    final Parser<LexerlessGrammar> parser = CssParser.create(conf);

    AstScanner.Builder<LexerlessGrammar> builder = AstScanner.<LexerlessGrammar> builder(context).setBaseParser(parser);

    /* Metrics */
    builder.withMetrics(CssMetric.values());

    /* Comments */
    builder.setCommentAnalyser(new CssCommentAnalyser());

    builder.withSquidAstVisitor(CommentsVisitor.<LexerlessGrammar> builder().withCommentMetric(
        CssMetric.COMMENT_LINES)
        .withBlankCommentMetric(CssMetric.COMMENT_BLANK_LINES).withNoSonar(true)
        .withIgnoreHeaderComment(false).build());

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
    builder.withSquidAstVisitor(CommentsVisitor.<LexerlessGrammar> builder().withCommentMetric(CssMetric.COMMENT_LINES)
        .withNoSonar(true)
        .withIgnoreHeaderComment(conf.getIgnoreHeaderComments())
        .build());

    /* Syntax highlighter*/
    if(resourcePerspectives != null){
      builder.withSquidAstVisitor(new SyntaxHighlighterVisitor(resourcePerspectives, conf.getCharset()));
    }

    /* External visitors (typically Check ones) */
    for (SquidAstVisitor<LexerlessGrammar> visitor : visitors) {
      builder.withSquidAstVisitor(visitor);
    }

    return builder.build();

  }

}
