/*
 * SonarQube CSS Plugin
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

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.source.Highlightable;
import org.sonar.css.CssAstScanner;

import static org.mockito.Mockito.mock;

public class SyntaxHighlighterVisitorTest {

  @Rule
  public TemporaryFolder temp = new TemporaryFolder();

  private final SonarComponents sonarComponents = Mockito.mock(SonarComponents.class);
  private final Highlightable highlightable = Mockito.mock(Highlightable.class);
  private final Highlightable.HighlightingBuilder highlighting = Mockito.mock(Highlightable.HighlightingBuilder.class);

  private final SyntaxHighlighterVisitor syntaxHighlighterVisitor = new SyntaxHighlighterVisitor(sonarComponents, Charsets.UTF_8);

  private List<String> lines;
  private String eol;

  @Before
  public void setUp() {
    InputFile inputFile = mock(InputFile.class);
    Mockito.when(sonarComponents.inputFileFor(Mockito.any(File.class))).thenReturn(inputFile);
    Mockito.when(sonarComponents.highlightableFor(inputFile)).thenReturn(highlightable);
    Mockito.when(highlightable.newHighlighting()).thenReturn(highlighting);
  }

  @Test
  public void parse_error() throws Exception {
    File file = temp.newFile();
    Files.write("ParseError", file, Charsets.UTF_8);
    CssAstScanner.scanSingleFile(file, syntaxHighlighterVisitor);

    Mockito.verifyZeroInteractions(highlightable);
  }

  // TODO Factorize duplicated methods, but still allow double click on failures to jump to the right line

  @Test
  public void test_LF() throws Exception {
    this.eol = "\n";
    File file = temp.newFile();
    Files.write(Files.toString(new File("src/test/resources/syntax_highlight.css"), Charsets.UTF_8).replaceAll("\\r\\n", "\n").replaceAll("\\n", eol), file, Charsets.UTF_8);

    CssAstScanner.scanSingleFile(file, syntaxHighlighterVisitor);

    lines = Files.readLines(file, Charsets.UTF_8);
    Mockito.verify(highlighting).highlight(offset(1, 1), offset(1, 7), "h");
    Mockito.verify(highlighting).highlight(offset(2, 5), offset(2, 10), "c");
    Mockito.verify(highlighting).highlight(offset(5, 1), offset(7, 3), "cppd");
    Mockito.verify(highlighting).highlight(offset(9, 1), offset(9, 7), "h");
    Mockito.verify(highlighting).highlight(offset(10, 3), offset(10, 8), "c");
    Mockito.verify(highlighting).done();
    Mockito.verifyNoMoreInteractions(highlighting);
  }

  @Test
  public void test_CR_LF() throws Exception {
    this.eol = "\r\n";
    File file = temp.newFile();
    Files.write(Files.toString(new File("src/test/resources/syntax_highlight.css"), Charsets.UTF_8).replaceAll("\\r\\n", "\n").replaceAll("\\n", eol), file, Charsets.UTF_8);

    CssAstScanner.scanSingleFile(file, syntaxHighlighterVisitor);

    lines = Files.readLines(file, Charsets.UTF_8);
    Mockito.verify(highlighting).highlight(offset(1, 1), offset(1, 7), "h");
    Mockito.verify(highlighting).highlight(offset(2, 5), offset(2, 10), "c");
    Mockito.verify(highlighting).highlight(offset(5, 1), offset(7, 3), "cppd");
    Mockito.verify(highlighting).highlight(offset(9, 1), offset(9, 7), "h");
    Mockito.verify(highlighting).highlight(offset(10, 3), offset(10, 8), "c");
    Mockito.verify(highlighting).done();
    Mockito.verifyNoMoreInteractions(highlighting);
  }

  @Test
  public void test_CR() throws Exception {
    this.eol = "\r";
    File file = temp.newFile();
    Files.write(Files.toString(new File("src/test/resources/syntax_highlight.css"), Charsets.UTF_8).replaceAll("\\r\\n", "\n").replaceAll("\\n", eol), file, Charsets.UTF_8);

    CssAstScanner.scanSingleFile(file, syntaxHighlighterVisitor);

    lines = Files.readLines(file, Charsets.UTF_8);
    Mockito.verify(highlighting).highlight(offset(1, 1), offset(1, 7), "h");
    Mockito.verify(highlighting).highlight(offset(2, 5), offset(2, 10), "c");
    Mockito.verify(highlighting).highlight(offset(5, 1), offset(7, 3), "cppd");
    Mockito.verify(highlighting).highlight(offset(9, 1), offset(9, 7), "h");
    Mockito.verify(highlighting).highlight(offset(10, 3), offset(10, 8), "c");
    Mockito.verify(highlighting).done();
    Mockito.verifyNoMoreInteractions(highlighting);
  }

  private int offset(int line, int column) {
    int result = 0;
    for (int i = 0; i < line - 1; i++) {
      result += lines.get(i).length() + eol.length();
    }
    result += column - 1;
    return result;
  }

}
