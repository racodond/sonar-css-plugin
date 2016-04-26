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
package org.sonar.css.ast.visitors;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;

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

    Mockito.verify(highlighting).highlight(0, 6, "h");
    Mockito.verify(highlighting).highlight(24, 29, "c");
    Mockito.verify(highlighting).highlight(39, 48, "cppd");
    Mockito.verify(highlighting).highlight(50, 56, "h");
    Mockito.verify(highlighting).highlight(61, 66, "c");
    Mockito.verify(highlighting).highlight(75, 84, "cppd");
  }

  @Test
  public void test_CR() throws Exception {
    this.eol = "\r";
    File file = temp.newFile();
    Files.write(Files.toString(new File("src/test/resources/syntax_highlight.css"), Charsets.UTF_8).replaceAll("\\r\\n", "\n").replaceAll("\\n", eol), file, Charsets.UTF_8);

    CssAstScanner.scanSingleFile(file, syntaxHighlighterVisitor);

    Mockito.verify(highlighting).highlight(0, 6, "h");
    Mockito.verify(highlighting).highlight(24, 29, "c");
    Mockito.verify(highlighting).highlight(39, 48, "cppd");
    Mockito.verify(highlighting).highlight(50, 56, "h");
    Mockito.verify(highlighting).highlight(61, 66, "c");
    Mockito.verify(highlighting).highlight(75, 84, "cppd");
    Mockito.verify(highlighting).done();
    Mockito.verifyNoMoreInteractions(highlighting);
  }

  @Test
  public void test_CR_LF() throws Exception {
    this.eol = "\r\n";
    File file = temp.newFile();
    Files.write(Files.toString(new File("src/test/resources/syntax_highlight.css"), Charsets.UTF_8).replaceAll("\\r\\n", "\n").replaceAll("\\n", eol), file, Charsets.UTF_8);

    CssAstScanner.scanSingleFile(file, syntaxHighlighterVisitor);

    Mockito.verify(highlighting).highlight(0, 6, "h");
    Mockito.verify(highlighting).highlight(25, 30, "c");
    Mockito.verify(highlighting).highlight(43, 54, "cppd");
    Mockito.verify(highlighting).highlight(58, 64, "h");
    Mockito.verify(highlighting).highlight(70, 75, "c");
    Mockito.verify(highlighting).highlight(86, 95, "cppd");
    Mockito.verify(highlighting).done();
    Mockito.verifyNoMoreInteractions(highlighting);
  }

  @Test
  public void test_BOM_LF() throws Exception {
    this.eol = "\n";
    File file = temp.newFile();
    Files.write(Files.toString(new File("src/test/resources/syntax_highlight_bom.css"), Charsets.UTF_8).replaceAll("\\r\\n", "\n").replaceAll("\\n", eol), file, Charsets.UTF_8);

    CssAstScanner.scanSingleFile(file, syntaxHighlighterVisitor);

    Mockito.verify(highlighting).highlight(1, 7, "h"); /* First character is BOM */
    Mockito.verify(highlighting).highlight(24, 29, "c");
    Mockito.verify(highlighting).highlight(39, 48, "cppd");
    Mockito.verify(highlighting).highlight(50, 56, "h");
    Mockito.verify(highlighting).highlight(61, 66, "c");
    Mockito.verify(highlighting).highlight(75, 84, "cppd");
    Mockito.verify(highlighting).done();
    Mockito.verifyNoMoreInteractions(highlighting);
  }

}
